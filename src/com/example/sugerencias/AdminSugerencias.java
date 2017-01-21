package com.example.sugerencias;

import java.util.List;

import DB.SugerenciasDataSource;
import Logica.Sugerencias;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class AdminSugerencias extends Activity implements OnItemClickListener {

	private int requestCode = 1;
	private ListView lvSugerencia;
	private EditText txtbuscador;
	private SugerenciasDataSource dataSource;

	private OnClickListener NuevaSugerencias = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(AdminSugerencias.this,
					NuevaSugerencias.class);
			startActivity(intent);
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminsugerencias);

		Button btnEnviar = (Button) findViewById(R.id.btnNuevo);
		btnEnviar.setOnClickListener(NuevaSugerencias);

		// Instanciamos NotasDataSource para
		// poder realizar acciones con la base de datos
		dataSource = new SugerenciasDataSource(this);
		dataSource.open();

		// Instanciamos los elementos
		lvSugerencia = (ListView) findViewById(R.id.lvsugerencia);
		txtbuscador = (EditText) findViewById(R.id.svbuscador);

		// Cargamos la lista de notas disponibles
		List<Sugerencias> listaSugerencia = dataSource.getAllSugerencias();
		final ArrayAdapter<Sugerencias> adapter = new ArrayAdapter<Sugerencias>(
				this, android.R.layout.simple_list_item_1, listaSugerencia);
		adapter.notifyDataSetChanged();
		// Establecemos el adapter
		lvSugerencia.setAdapter(adapter);

		txtbuscador.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				adapter.getFilter().filter(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		// Establecemos un Listener para el evento de pulsación
		lvSugerencia.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(final AdapterView<?> adapterView, View view,
			final int position, long id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setTitle("Borrar Sugerencia")
				.setMessage("¿Desea borrar esta sugerencia?")
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								Sugerencias sugerencia = (Sugerencias) adapterView
										.getItemAtPosition(position);
								dataSource.borrarSug(sugerencia);

								// Refrescamos la lista
								refrescarLista();
							}
						})

				.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								return;
							}
						});
		builder.show();
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.d("Result", "Se ejecuta onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == this.requestCode && resultCode == RESULT_OK) {
			// Actualizar el Adapter
			dataSource.open();
			refrescarLista();
		}
	}

	public void refrescarLista() {
		List<Sugerencias> lista = dataSource.getAllSugerencias();
		ArrayAdapter<Sugerencias> adapter = new ArrayAdapter<Sugerencias>(this,
				android.R.layout.simple_list_item_1, lista);
		lvSugerencia.setAdapter(adapter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		dataSource.close();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		dataSource.open();
		super.onResume();
	}

}
