package DB;

import java.util.ArrayList;
import java.util.List;

import DB.MySQLiteOpenHelper.TablaContadorSugerencias;
import DB.MySQLiteOpenHelper.TablaFiltrarSugerencias;
import DB.MySQLiteOpenHelper.TablaSugerencia;
import Logica.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SugerenciasDataSource {
	private SQLiteDatabase db;
	private MySQLiteOpenHelper dbHelper;

	private String[] columnas = { TablaSugerencia.COLUMNA_ID,
			TablaSugerencia.COLUMNA_SUG, TablaSugerencia.COLUMNA_GRUPO };

	public SugerenciasDataSource(Context context) {
		dbHelper = MySQLiteOpenHelper.getInstance(context);
	}

	public void open() {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void crearSugerencia(String sugerencia, String grupo) {
		ContentValues values = new ContentValues();
		values.put(TablaSugerencia.COLUMNA_SUG, sugerencia);
		values.put(TablaSugerencia.COLUMNA_GRUPO, grupo);

		db.insert(TablaSugerencia.TABLA_SUGERENCIAS, null, values);
	}

	public void crearFiltrar(String sugerencia) {

		ContentValues values = new ContentValues();
		values.put(TablaFiltrarSugerencias.COLUMNA_FSUGERENCIA, sugerencia);

		db.insert(TablaFiltrarSugerencias.TABLA_FILTRAR, null, values);
	}

	public Integer BuscarGrupoSugerencias(String grupo) {
		List<Sugerencias> lista = new ArrayList<Sugerencias>();

		Cursor cursor = db.rawQuery("select*from Sugerencias where GRUPO='"
				+ grupo + "'", null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Sugerencias nuevaSug = cursorToSugerencias(cursor);
			lista.add(nuevaSug);
			cursor.moveToNext();
		}
		cursor.close();

		for (Sugerencias sugerencia : lista) {
			crearFiltrar(sugerencia.getSugerencia());
		}

		return lista.size();
	}

	public void crearContador(int id, String grupo, int limite) {
		ContentValues values = new ContentValues();
		values.put(TablaContadorSugerencias.COLUMNA_ID, id);
		values.put(TablaContadorSugerencias.COLUMNA_CGRUPO, grupo);
		values.put(TablaContadorSugerencias.COLUMNA_LIMITE, limite);
		db.insert(TablaContadorSugerencias.TABLA_CONTADOR, null, values);
	}

	public List<Sugerencias> getAllSugerencias() {
		List<Sugerencias> lista = new ArrayList<Sugerencias>();

		Cursor cursor = db.query(TablaSugerencia.TABLA_SUGERENCIAS, columnas,
				null, null, null, null, null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Sugerencias nuevaSug = cursorToSugerencias(cursor);
			lista.add(nuevaSug);
			cursor.moveToNext();
		}
		cursor.close();
		return lista;
	}

	public void borrarSug(Sugerencias sugerencia) {
		int id = (int) sugerencia.getId();
		db.delete(TablaSugerencia.TABLA_SUGERENCIAS, TablaSugerencia.COLUMNA_ID
				+ " = " + id, null);
	}
	private Sugerencias cursorToSugerencias(Cursor cursor) {
		Sugerencias sugerencia = new Sugerencias();
		sugerencia.setId(cursor.getInt(0));
		sugerencia.setSugerencia(cursor.getString(1));
		sugerencia.setGrupo(cursor.getString(2));
		return sugerencia;
	}

	Sugerencias sugerenciaaux;

	public boolean buscar(int id, String grupo) {
		boolean existe = false;
		Cursor fila = db.rawQuery("select*from Sugerencias where _id ='" + id
				+ "' and GRUPO='" + grupo + "'", null);
		if (fila.moveToFirst()) {
			sugerenciaaux = new Sugerencias();
			sugerenciaaux.setId(Integer.parseInt(fila.getString(0).toString()));
			sugerenciaaux.setSugerencia(fila.getString(1).toString());
			sugerenciaaux.setGrupo(fila.getString(2).toString());
			existe = true;
		}

		return existe;
	}
	public int LeeridContador() {
		int contador = 1;
		Cursor fila = db.rawQuery("select contador from ContadorSugerencias",
				null);
		if (fila.moveToFirst()) {
			contador = fila.getInt(0);

		}
		return contador;
	}
	public String LeerSugerencia(int id) {
		String sugerencia = "";
		Cursor fila = db.rawQuery("select*from FiltrarSugerencias where id='"
				+ id + "'", null);
		if (fila.moveToFirst()) {
			sugerencia = String.valueOf(fila.getString(1).toString());

		}
		return sugerencia;
	}
	public String LeerGrupo() {
		String grupo = "";
		Cursor fila = db.rawQuery("select*from ContadorSugerencias", null);
		if (fila.moveToFirst()) {
			grupo = fila.getString(1).toString();

		}
		return grupo;
	}
	public void updatecontador(int contador, int contadorin) {
		ContentValues valores = new ContentValues();
		valores.put("contador", contadorin);

		db.update("ContadorSugerencias", valores, "contador=" + contador, null);
	}
	public void updatgrupocontador(String grupo) {
		ContentValues valores = new ContentValues();
		valores.put("Grupo", grupo);

		db.update("ContadorSugerencias", valores, "Grupo=" + grupo, null);
	}

	public void borrarDatosFiltar() {
		db.delete(TablaFiltrarSugerencias.TABLA_FILTRAR, null, null);
	}

	public void borrarDatosContador() {
		db.delete(TablaContadorSugerencias.TABLA_CONTADOR, null, null);
	}

	public Sugerencias devolver() {
		return sugerenciaaux;
	}

}
