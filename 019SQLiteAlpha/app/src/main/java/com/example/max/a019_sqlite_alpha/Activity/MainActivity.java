package com.example.max.a019_sqlite_alpha.Activity;

import android.content.ContentValues;
import android.database.Cursor;
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
    public void consultaPorCodigo(View v){
        AdminSQLiteOpenHelper Admin = new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        /**crear un objeto de la clase AdminSQLiteOpenHelper */

        SQLiteDatabase db = Admin.getWritableDatabase();
        /**obtener una referencia de la base de datos llamando al método getWritableDatabase*/

        String cod = codigo.getText().toString();
        Cursor fila = db.rawQuery("SELECT Descripcion,Precio FROM Articulos WHERE Codigo="+cod,null);
        /**Definimos una variable de la clase Cursor y la inicializamos con el valor devuelto por el método llamado rawQuery.
         La clase Cursor almacena en este caso una fila o cero filas*/

        if (fila.moveToFirst()){
            /**El método moveToFirst() de la clase Cursor, retorna true en caso de exista un articulo con el codigo ingresado,
             * en caso contrario retorna cero. */

            descripcion.setText(fila.getString(0));
            /**Para recuperar los datos que queremos consultar llamamos
             * al método getString y le pasamos la posición del campo a recuperar*/

            precio.setText(fila.getString(1));
        } else
            Toast.makeText(this,"No existe un articulo con dicho codigo",Toast.LENGTH_LONG).show();
        db.close();
    }
    public void consultaPorDescripcion(View v){
        AdminSQLiteOpenHelper Admin = new AdminSQLiteOpenHelper(this,"Administracion",null,1);

        SQLiteDatabase db = Admin.getWritableDatabase();

        String descri = descripcion.getText().toString();
        Cursor fila = db.rawQuery("SELECT Codigo,Precio FROM Articulos WHERE Descripcion='"+ descri +"'",null);
        /**en el where de la clausula SQL hemos dispuesto comillas simples entre el contenido
         *  de la variable descri.Esto es obligatorio para los campos de tipo text*/
        if (fila.moveToFirst()){
            codigo.setText(fila.getString(0));
            precio.setText(fila.getString(1));
        } else
            Toast.makeText(this,"No existe un articulo con dicha descripcion",Toast.LENGTH_LONG).show();
        db.close();
    }
    public void bajaPorCodigo(View v){
        AdminSQLiteOpenHelper Admin = new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        SQLiteDatabase db = Admin.getWritableDatabase();

        String cod =codigo.getText().toString();
        int cant = db.delete("Articulos","Codigo="+ cod,null);
        db.close();
        codigo.setText("");
        descripcion.setText("");
        precio.setText("");

        if (cant==1){
            Toast.makeText(this,"Se borro el articulo con dicho codigo",Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this,"No existe un articulo con dicho codigo",Toast.LENGTH_LONG).show();
    }
    public void modificacion(View v){
        AdminSQLiteOpenHelper Admin= new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        SQLiteDatabase db = Admin.getWritableDatabase();

        String cod = codigo.getText().toString();
        String descri = descripcion.getText().toString();
        String pre = precio.getText().toString();

        ContentValues registroCV = new ContentValues();
        registroCV.put("codigo",cod);
        registroCV.put("descripcion",descri);
        registroCV.put("precio",pre);

        int cant = db.update("Articulos",registroCV,"Codigo="+ cod,null);
        db.close();
        if (cant==1) {
            Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this,"No existe un articulo con el codigo ingresadfo",Toast.LENGTH_LONG).show();
    }
}
