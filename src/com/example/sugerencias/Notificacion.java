package com.example.sugerencias;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Notificacion extends Activity {

	TextView notifcacion;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);
		String valor = getIntent().getExtras().getString("valor");
		notifcacion = (TextView) findViewById(R.id.txtnotifcacion);

		notifcacion.setText(valor);

	}
}
