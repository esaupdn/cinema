package com.example.trabalhopdi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ListaFilmes extends AppCompatActivity {

    private SQLiteDatabase bancoDados;
    public ListView listViewDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_filmes);

        listViewDados = (ListView) findViewById(R.id.listViewDados);

        criarBD();
        listarDados();
        //inserirDados();   Método p/ inserir dados manualmente
    }

    public void criarBD(){
        try {
            bancoDados = openOrCreateDatabase("cinema", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS cinema(" + " id INTEGER PRIMARY KEY AUTOINCREMENT" + ", nome VARCHAR)");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarDados(){
        try{
            bancoDados = openOrCreateDatabase("cinema", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id, nome FROM cinema", null);
            ArrayList<String> list = new ArrayList<String>();
            ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, list);

            listViewDados.setAdapter(adapter);
            cursor.moveToFirst();
            while(cursor!=null){
                list.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void inserirDados(){
        try {
            bancoDados = openOrCreateDatabase("cinema", MODE_PRIVATE, null);
            String sql ="INSERT INTO cinema (nome) VALUES (?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1,"Filme 1");
            stmt.executeInsert();

            stmt.bindString(1,"Filme 2");
            stmt.executeInsert();

            stmt.bindString(1,"Filme 3");
            stmt.executeInsert();

            bancoDados.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}