package com.xookware.cursoandroidexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xookware.cursoandroidexample.model.Asignatura;

import java.util.LinkedList;

/**
 * Created by Bismay on 13/6/2019.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "curso";

    // Declaras el nombre de la tabla en una constante para que luego sea mas facil trabajar y evitar errores
    private static final String TABLE_CURSO = "curso";

    /*Declaras el nombre de los campos en constantes para que luego sea mas facil trabajar y evitar errores
    se usa String porque aqui solo declaramos el nombre de un campo en la tabla no su valor
    */
    private static final String ID = "id";
    private static final String NOMBRE = "nombre";
    private static final String NOMBRE_PROFESOR ="nombre_profesor";
    private static final String NOTA = "nota";
    private static final String ANNIO = "annio";
    private static final String SEMESTRE = "semestre";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creando tabla(s)
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*Aqui lo que se hace simplemente es ejecutar un query y esto porque android te permite usar
        * clases para el manejo de bases de datos*/
        String CREATE_CARTABON_TABLE = "CREATE TABLE " + TABLE_CURSO + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOMBRE + " TEXT NOT NULL," + NOMBRE_PROFESOR + " TEXT,"
                + NOTA + " TEXT," + ANNIO + " INTEGER," + SEMESTRE + " INTEGER );";
        db.execSQL(CREATE_CARTABON_TABLE);

        /*la tabla "curso" de la bd "curso" quedaria:
        * columna 0 = id
        * columna 1 = nombre
        * columna 2 = nombre_profesor
        * columna 3 = nota
        * columna 4 = annio
        * columna 5 = semestre*/
    }

    // Upgradiando base de datos local
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Elimina las tablas anteriores si existen
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURSO);
        // Ejecuta el metodo onCreate();
        onCreate(db);
    }

    /**
     * CRUD operations
     */

    // Insertar
    public void insertar(Asignatura asignatura) {
        SQLiteDatabase db = this.getWritableDatabase();
//el id no se añade porque lo declaré autoincrementado
        ContentValues values = new ContentValues();
        values.put(NOMBRE, asignatura.getNombre());
        values.put(NOMBRE_PROFESOR, asignatura.getNombreProfesor());
        values.put(NOTA, asignatura.getNota());
        values.put(ANNIO, asignatura.getAnnio());
        values.put(SEMESTRE, asignatura.getSemestre());
        // Insertando nueva fila
        db.insert(TABLE_CURSO, null, values);
        db.close(); // cerrando la conexion con la base de datos
    }

    // GET
    //Este metodo sirve para recuperar un dato de una tabla en mi base de datos
    public Asignatura get(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Asignatura asignatura = null;

        Cursor cursor = db.query(TABLE_CURSO, new String[]{ID, NOMBRE, NOMBRE_PROFESOR, NOTA, ANNIO, SEMESTRE}, ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        asignatura = new Asignatura(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                cursor.getInt(4), cursor.getInt(5));  // return lecture
        return asignatura;
    }

    // GET ALL
    //Este metodo sirve para recuperar todos los datos de una tabla de mi base de datos mi base de datos
    public LinkedList<Asignatura> getAlL() {
        LinkedList<Asignatura> list = new LinkedList<Asignatura>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CURSO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // añadiendo todos los rows de la tabla a la lista
        if (cursor.moveToFirst()) {
            do {
                Asignatura asignatura = new Asignatura();

                asignatura.setId(cursor.getInt(0));
                asignatura.setNombre(cursor.getString(1));
                asignatura.setNombreProfesor(cursor.getString(2));
                asignatura.setNota(cursor.getInt(3));
                asignatura.setAnnio(cursor.getInt(4));
                asignatura.setSemestre(cursor.getInt(5));
                list.add(asignatura);
            } while (cursor.moveToNext());
        }

        // return lecture's list
        return list;
    }

    // Actualizar un dato
    public int update(Asignatura asignatura) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ID, asignatura.getId());
        values.put(NOMBRE, asignatura.getNombre());
        values.put(NOMBRE_PROFESOR, asignatura.getNombreProfesor());
        values.put(NOTA, asignatura.getNota());
        values.put(ANNIO, asignatura.getAnnio());
        values.put(SEMESTRE, asignatura.getSemestre());

        return db.update(TABLE_CURSO, values, ID + "=?;",
                new String[]{String.valueOf(asignatura.getId())});
    }

    // Eliminar
    public void delete(Asignatura asignatura) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CURSO, ID + " = ?",
                new String[]{String.valueOf(asignatura.getId())});
        db.close();
    }


    // Getting Count
    public int getCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CURSO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}