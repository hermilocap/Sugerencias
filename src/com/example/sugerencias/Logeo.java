package com.example.sugerencias;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Logeo extends Activity {
	private  OnClickListener AdminSugerencias = new OnClickListener() {
			
		@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Logeo.this,AdminSugerencias.class);
				startActivity(intent);
		}
		};

		protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login);
	        
	       Button btnEnviar=(Button) findViewById(R.id.btnEnviar);
	        btnEnviar.setOnClickListener(AdminSugerencias);
		}
	
}
