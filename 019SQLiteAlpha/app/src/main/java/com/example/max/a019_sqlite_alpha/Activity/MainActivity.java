package com.example.max.a019_sqlite_alpha.Activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.max.a019_sqlite_alpha.Dao.AdminSQLiteOpenHelper;
import com.example.max.a019_sqlite_alpha.R;

public class MainActivity extends AppCompatActivity {
    private EditText codigo, descripcion, precio;
    private Button bAlta, bCodigo, bDescripcion, bBaja, bModificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**VINCULACION*/
        codigo=findViewById(R.id.editIngCodigo);
        descripcion=findViewById(R.id.editIngDescripcion);
        precio=findViewById(R.id.editIngPrecio);
        bAlta=findViewById(R.id.buttAlta);
        bCodigo=findViewById(R.id.buttConCodigo);
        bDescripcion=findViewById(R.id.buttConDescripcion);
        bBaja=findViewById(R.id.buttBaja);
        bModificacion=findViewById(R.id.buttModificacion);
    }
    public void alta(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        /**Lo primero que hacemos en este método es crear un objeto de la clase AdminSQLiteOpenHelper
         * y le pasamos al constructor this (referencia del Activity actual),
         * "administracion" (es el nombre de la base de datos que crearemos en el caso que no exista)
         * luego pasamos null y un uno indicando que es la primer versión de la base de datos */

        SQLiteDatabase bd = admin.getWritableDatabase();
        /**Creamos un objeto de la clase SQLiteDataBase llamando al método getWritableDatabase
         * (la base de datos se abre en modo lectura y escritura)*/

        String cod = codigo.getText().toString();
        String descri = descripcion.getText().toString();
        String pre = precio.getText().toString();
        /**CONTENTVALUES
         * Esta clase se utiliza para almacenar un conjunto de valores que ContentResolver puede procesar */
        ContentValues cValuesRegistro = new ContentValues();
        /**Creamos un objeto de la clase ContentValues y mediante el método put inicializamos todos tos campos a cargar.*/

        cValuesRegistro.put("codigo",cod);
        cValuesRegistro.put("descripcion",descri);
        cValuesRegistro.put("precio",pre);

        bd.insert("Articulos",null, cValuesRegistro);
        /**Llamamos al método insert de la clase SQLiteDatabase pasando en el primer parámetro el nombre de la tabla,
         * como segundo parámetro un null y por último el objeto de la clase ContentValues ya inicializado
         * (este método es el que provoca que se inserte una nueva fila en la tabla Articulos
         * en la base de datos llamada Administracion)*/

        bd.close();
        codigo.setText("");
        descripcion.setText("");
        precio.setText("");
        Toast.makeText(this,"Se cargaron los datos del Articulo",Toast.LENGTH_LONG).show();
    }


}
