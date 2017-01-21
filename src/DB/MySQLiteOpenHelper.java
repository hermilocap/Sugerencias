package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	private static MySQLiteOpenHelper mOpenHelper = null;

	private static final String DATABASE_NAME = "Sugerenciasdb.db";
	private static final int DATABASE_VERSION = 1;

	public static class TablaSugerencia {
		public static String TABLA_SUGERENCIAS = "Sugerencias";
		public static String COLUMNA_ID = "_id";
		public static String COLUMNA_SUG = "sugerencia";
		public static String COLUMNA_GRUPO = "GRUPO";
	}

	public static class TablaFiltrarSugerencias {
		public static String TABLA_FILTRAR = "FiltrarSugerencias";
		public static String COLUMNA_ID = "id";
		public static String COLUMNA_FSUGERENCIA = "sugerencia";

	}

	public static class TablaContadorSugerencias {
		public static String TABLA_CONTADOR = "ContadorSugerencias";
		public static String COLUMNA_ID = "contador";
		public static String COLUMNA_CGRUPO = "Grupo";
		public static String COLUMNA_LIMITE = "Limite";

	}

	private static final String DATABASE_CREATE = "create table "
			+ TablaSugerencia.TABLA_SUGERENCIAS + "("
			+ TablaSugerencia.COLUMNA_ID
			+ " integer primary key autoincrement, "
			+ TablaSugerencia.COLUMNA_SUG + " text not null,"
			+ TablaSugerencia.COLUMNA_GRUPO + " text not null);";

	private static final String FILTRAR_CREATE = "create table "
			+ TablaFiltrarSugerencias.TABLA_FILTRAR + "("
			+ TablaFiltrarSugerencias.COLUMNA_ID
			+ " integer primary key autoincrement, "
			+ TablaFiltrarSugerencias.COLUMNA_FSUGERENCIA + " text not null);";

	private static final String CONTADOR_CREATE = "create table "
			+ TablaContadorSugerencias.TABLA_CONTADOR + "("
			+ TablaContadorSugerencias.COLUMNA_ID + " integer not null, "
			+ TablaContadorSugerencias.COLUMNA_CGRUPO + " text not null,"
			+ TablaContadorSugerencias.COLUMNA_LIMITE + " integer not null);";

	public static MySQLiteOpenHelper getInstance(Context context) {
		if (mOpenHelper == null) {
			mOpenHelper = new MySQLiteOpenHelper(
					context.getApplicationContext());
		}

		return mOpenHelper;
	}

	public MySQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
		db.execSQL(FILTRAR_CREATE);
		db.execSQL(CONTADOR_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("delete table if exists "
				+ TablaSugerencia.TABLA_SUGERENCIAS);
		db.execSQL("delete table if exists "
				+ TablaFiltrarSugerencias.TABLA_FILTRAR);
		db.execSQL("delete table if exists "
				+ TablaContadorSugerencias.TABLA_CONTADOR);

		onCreate(db);
	}

}
