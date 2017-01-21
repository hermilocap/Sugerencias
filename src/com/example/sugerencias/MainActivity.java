package com.example.sugerencias;

import java.util.Timer;
import java.util.TimerTask;
import DB.SugerenciasDataSource;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
//import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	static String grupo;

	private SugerenciasDataSource dataSource;
	Context context = null;
	// Variables de la notificacion
	NotificationManager nm;
	Notification notif;
	static String ns = Context.NOTIFICATION_SERVICE;

	Timer timer;
	TimerTask timerTask;

	final Handler handler = new Handler();

	static String id;
	static String sug;
	int limite;
	RadioGroup RadioGroup;

	int hour;
	int min;
	int houractual;
	int minactual;
	int tiempo;
	private TimePicker timePicker;

	private OnClickListener AdminSugerencias = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this,
					AdminSugerencias.class);
			startActivity(intent);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dataSource = new SugerenciasDataSource(getApplicationContext());

		dataSource.open();

		Button btnlogin = (Button) findViewById(R.id.btnnueva);
		btnlogin.setOnClickListener(AdminSugerencias);

		RadioGroup rg = (RadioGroup) findViewById(R.id.gruporg);
		rg.clearCheck();
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

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

		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub

				hour = timePicker.getCurrentHour();
				min = timePicker.getCurrentMinute();
				houractual = hour * 3600000;
				minactual = min * 60 * 1000;
				tiempo = houractual + minactual;
				// showTime(hour, min);
			}

		});

		Button btncancelar = (Button) findViewById(R.id.btncancelar);
		btncancelar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// stop the timer, if it's not already null
				if (timer != null) {
					timer.cancel();
					timer = null;
					Toast.makeText(getApplicationContext(),
							"Notificacion cancelado", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		// button
		Button btnConfirmar = (Button) findViewById(R.id.buttonConfirmar);
		btnConfirmar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				limite = dataSource.BuscarGrupoSugerencias(grupo);

				dataSource.crearContador(1, grupo, limite);
				startTimer();
				Toast.makeText(getApplicationContext(),
						"Configuracion guardado correctamente",
						Toast.LENGTH_SHORT).show();
				// TODO Auto-generated method stub
			}

		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		// onResume we start our timer so it can start when the app comes from
		// the background

	}

	public void startTimer() {
		// set a new Timer
		timer = new Timer();

		// initialize the TimerTask's job
		initializeTimerTask();

		// schedule the timer, after the first 5000ms the TimerTask will run
		// every 10000ms
		timer.schedule(timerTask, tiempo, tiempo);

	}

	public void initializeTimerTask() {

		timerTask = new TimerTask() {
			public void run() {

				// use a handler to run a toast that shows the current timestamp
				handler.post(new Runnable() {
					public void run() {

						int id = dataSource.LeeridContador();
						String sugerencia = dataSource.LeerSugerencia(id);

						Notify("sugerencias", sugerencia);

						if (id < limite) {

							dataSource.updatecontador(id, id + 1);
						} else {

							dataSource.updatecontador(id, 1);
						}
					}
				});
			}
		};
	}

	@SuppressWarnings("deprecation")
	private void Notify(String notificationTitle, String notificationMessage) {
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Notification notification = new Notification(R.drawable.n_icon2,
				"Notificacion", System.currentTimeMillis());

		Intent notificationIntent = new Intent(this, Notificacion.class);

		notificationIntent.putExtra("valor", notificationMessage);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(MainActivity.this, notificationTitle,
				notificationMessage, pendingIntent);
		notification.defaults |= Notification.DEFAULT_SOUND;

		notificationManager.notify(9999, notification);
		// notificationIntent.putExtra("valor", notificationMessage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
