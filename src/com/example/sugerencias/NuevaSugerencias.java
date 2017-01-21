package com.example.sugerencias;

import DB.SugerenciasDataSource;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class NuevaSugerencias extends Activity {
	public static int resultCode = 10;

	private Button btnAgregar;
	private EditText txtnombreSugerencia;
	private SugerenciasDataSource dataSource;
	private RadioGroup rdgGrupo;
	static String grupo;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nuevasugerencia);

		dataSource = new SugerenciasDataSource(this);
		dataSource.open();
		txtnombreSugerencia = (EditText) findViewById(R.id.txtnombreSugerencia);
		rdgGrupo = (RadioGroup) findViewById(R.id.rdgGrupo);
		rdgGrupo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.radio1) {
					grupo = "Ejercicios";

				} else if (checkedId == R.id.radio2) {
					grupo = "Alimentacion";

				} else if (checkedId == R.id.radio3) {
					grupo = "Prevencion";
				}
			}

		});

		btnAgregar = (Button) findViewById(R.id.btnAgregar);
		btnAgregar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String textnombreSugerencia = txtnombreSugerencia.getText()
						.toString();
				String radioGrupo = grupo;

				if (textnombreSugerencia.length() != 0) {
					dataSource
							.crearSugerencia(textnombreSugerencia, radioGrupo);
					setResult(RESULT_OK);
					finish();
					Toast.makeText(getApplicationContext(),
							"Sugerencia guardado correctamente",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"No ha introducido texto", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		dataSource.close();
		super.onPause();
	}

}
