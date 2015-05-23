package com.example.pedro.listadedesejos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class Listar_Desejo extends ActionBarActivity{

    Desejos desejosDao;
    Banco_Desejos banco_desejos;
    SQLiteDatabase db;
    Cursor cursor;
    public static final String[] Colunas = {"produto"};
    EditText PRODUTO, CATEGORIA, PRECOMIN, PRECOMAX, LOJA;
    ListView listView;
    static long id;
    ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_desejo);


        Inicializar_Lista_Desejos();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int Posicao, long arg3) {
                id = arg3;
                Setar_Dados_Detalhes(id);
                Intent intent = new Intent(Listar_Desejo.this, Detalhes.class);
                Bundle params = new Bundle();
                params.putString("detalhe_produto", PRODUTO.getText().toString());
                params.putString("detalhe_categoria", CATEGORIA.getText().toString());
                params.putString("detalhe_precoMin", PRECOMIN.getText().toString());
                params.putString("detalhe_precoMax", PRECOMAX.getText().toString());
                params.putString("detalhe_loja", LOJA.getText().toString());
                intent.putExtras(params);
                startActivity(intent);
            }
        });
    }

    /* função seleciona os dados para edição ==========================================*/
    public void Setar_Dados_Detalhes(long Posicao) {
        try{
            db = openOrCreateDatabase(banco_desejos.NOME_BANCO, MODE_PRIVATE, null);
            cursor = db.rawQuery("SELECT * FROM " +banco_desejos.TABELA_DESEJOS+ " WHERE _id = '"+Posicao+"'", null);

            while (cursor.moveToNext()){
                PRODUTO.setText(cursor.getString(cursor.getColumnIndex("produto")));
                CATEGORIA.setText(cursor.getString(cursor.getColumnIndex("categoria")));
                PRECOMIN.setText(cursor.getString(cursor.getColumnIndex("preco_Min")));
                PRECOMAX.setText(cursor.getString(cursor.getColumnIndex("preco_Max")));
                LOJA.setText(cursor.getString(cursor.getColumnIndex("loja")));
            }
        }catch (Exception e){
        }
        finally {
            cursor.close();
            db.close();
        }
    }

    /* função carrega lista de desejos ==============================================================*/
    public void Carrega_Lista_Desejos(){
        ListView listView = (ListView) findViewById(R.id.lv_lista_desejos);
        Cursor cursor = desejosDao.getCursor();
        if (desejosDao.VerificaRegistro()){
            int[] to = new int[]{R.id.tv_detalhes_item};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.detalhes_item, cursor, Colunas, to);
            listView.setAdapter(adapter);
        }else {
            Toast.makeText(getApplicationContext(), "Voce nao possui desejos!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_desejos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share_menu_alt) {
            mShareActionProvider = (ShareActionProvider) item.getActionProvider();
            setShareIntent();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent(){
        if (mShareActionProvider != null){
            //cria uma intent com conteudo dos text view
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, cursor.getColumnIndex(banco_desejos.COLUNA_PRODUTO.toString()));
            //shareIntent.putExtra(Intent.EXTRA_TEXT, PRODUTO.getText());

            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    public void Inicializar_Lista_Desejos(){

        desejosDao = new Desejos(this);

        listView = (ListView) findViewById(R.id.lv_lista_desejos);

        PRODUTO = (EditText) findViewById(R.id.lista_produto);
        CATEGORIA = (EditText) findViewById(R.id.lista_categoria);
        PRECOMIN = (EditText) findViewById(R.id.lista_precoMin);
        PRECOMAX = (EditText) findViewById(R.id.lista_precoMax);
        LOJA = (EditText) findViewById(R.id.lista_loja_Achar);

        Carrega_Lista_Desejos();
    }
}
