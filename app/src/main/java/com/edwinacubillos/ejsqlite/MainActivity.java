package com.edwinacubillos.ejsqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Button bIngresar, bActualizar, bConsultar, bBorrar;
    EditText eCodigo, eNombre, eEdad;
    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bIngresar = (Button) findViewById(R.id.bInsertar);
        bActualizar = (Button) findViewById(R.id.bActualizar);
        bConsultar = (Button) findViewById(R.id.bBuscar);
        bBorrar = (Button) findViewById(R.id.bEliminar);
        eCodigo = (EditText) findViewById(R.id.eCodigo);
        eNombre = (EditText) findViewById(R.id.eNombre);
        eEdad = (EditText) findViewById(R.id.eEdad);
        resultado = (TextView) findViewById(R.id.resultado);

        UsuariosSQLiteHelper usuario = new UsuariosSQLiteHelper(this);
        db = usuario.getWritableDatabase();

        ver_Tabla();

        bIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = eNombre.getText().toString();
                String edad = eEdad.getText().toString();

                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("nombre",nombre);
                nuevoRegistro.put("edad",edad);
                db.insert("Usuarios",null,nuevoRegistro);
                ver_Tabla();

            }
        });

        bActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = eNombre.getText().toString();
                String edad = eEdad.getText().toString();
                String codigo = eCodigo.getText().toString();

                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("nombre",nombre);
                nuevoRegistro.put("edad",edad);

                db.update("Usuarios",nuevoRegistro,"codigo="+codigo,null);
                ver_Tabla();
            }
        });

        bBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = eCodigo.getText().toString();
                db.delete("Usuarios","codigo="+codigo,null);
                ver_Tabla();

            }
        });

        bConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eNombre.getText().toString();
                String year = eEdad.getText().toString();
                String id = eCodigo.getText().toString();

                String[] campos = new String[]{"codigo","nombre","edad"};
                String[] args = new String[]{id};

                Cursor c=db.query("Usuarios",campos,"codigo=?",args,null,null,null);
                if (c.moveToFirst()){
                    resultado.setText("");

                    do{
                        String codigo = c.getString(0);
                        String nombre = c.getString(1);
                        int edad = c.getInt(2);
                        resultado.append(" "+codigo+" - "+nombre+" - "+edad+"\n");
                    }while (c.moveToNext());
                }
               // ver_Tabla();
            }
        });
    }

    protected void ver_Tabla(){
        Cursor c =db.rawQuery("SELECT codigo, nombre, edad FROM Usuarios", null);
        resultado.setText("");
        if (c.moveToFirst()){
            do{
                String codigo = c.getString(0);
                String nombre = c.getString(1);
                int edad = c.getInt(2);
                resultado.append(" "+codigo+" - "+nombre+" - "+edad+"\n");
            }while (c.moveToNext());
        }
    }
}
