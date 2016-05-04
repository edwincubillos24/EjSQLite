package com.edwinacubillos.ejsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Edwin on 03/05/2016.
 */
public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME="UsuariosDB";
    private static final int DATA_VERSION=1;

    String sqlCreate = "CREATE TABLE Usuarios (codigo INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, edad INTEGER)";

    public UsuariosSQLiteHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        db.execSQL(sqlCreate);
    }
}
