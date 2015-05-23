package com.example.pedro.listadedesejos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class Excluir_Desejo extends ActionBarActivity {

    Desejos desejosDao;
    Desejo desejo;
    Banco_Desejos banco_desejos;
    SQLiteDatabase db;
    TextView ID, PRODUTO, CATEGORIA, PRECOMIN, PRECOMAX, LOJA;
    ListView listview_excluir;
    static long id;
    public static final String[] Colunas = {"produto"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_desejo);

        desejosDao = new Desejos(this);
        desejo = new Desejo(null, null, null, null, null);

        ID = (TextView) findViewById(R.id.tv_detalhes_id);
        PRODUTO = (TextView) findViewById(R.id.excluir_produto);
        CATEGORIA = (TextView) findViewById(R.id.excluir_categoria);
        PRECOMIN = (TextView) findViewById(R.id.excluir_precoMin);
        PRECOMAX = (TextView) findViewById(R.id.excluir_precoMax);
        LOJA = (TextView) findViewById(R.id.excluir_loja_achar);

        Carrega_Lista_Desejos();

        listview_excluir = (ListView) findViewById(R.id.lv_desejos_excluir);
        listview_excluir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) {
                id = arg3;
                Setar_Dados(id);
            }
        });
    }

    /* função seleciona os dados para exclusão ==========================================*/
    public void Setar_Dados(long Posicao) {
        try{
            db = openOrCreateDatabase(banco_desejos.NOME_BANCO, MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM " + banco_desejos.TABELA_DESEJOS + " WHERE _id = '" + Posicao + "'", null);
            while (cursor.moveToNext()){
                ID.setText(cursor.getString(cursor.getColumnIndex(banco_desejos.COLUNA_ID)));
                PRODUTO.setText(cursor.getString(cursor.getColumnIndex(banco_desejos.COLUNA_PRODUTO)));
                CATEGORIA.setText(cursor.getString(cursor.getColumnIndex(banco_desejos.COLUNA_CATEGORIA)));
                PRECOMIN.setText(cursor.getString(cursor.getColumnIndex(banco_desejos.COLUNA_PRECO_MIN)));
                PRECOMAX.setText(cursor.getString(cursor.getColumnIndex(banco_desejos.COLUNA__PRECO_MAX)));
                LOJA.setText(cursor.getString(cursor.getColumnIndex(banco_desejos.COLUNA_LOJA)));
            }
            cursor.close();
        }catch (Exception e){
        }
        finally {
            db.close();
        }

    }

    /* metodo carrega lista de desejos ==================================================*/
    public void Carrega_Lista_Desejos(){
        ListView listview = (ListView) findViewById(R.id.lv_desejos_excluir);
        Cursor cursor = desejosDao.getCursor();
        if (desejosDao.VerificaRegistro()) {
            int[] to = new int[]{R.id.tv_detalhes_item};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(Excluir_Desejo.this, R.layout.detalhes_item, cursor, Colunas, to);
            listview.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(),"Você não possui desejos!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_excluir_desejos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings){
            return true;
        }

        if (id == R.id.excluir_menu){

            if (desejosDao.VerificaRegistro()){

                if (PRODUTO.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Selecione um desejo para excluir!", Toast.LENGTH_SHORT).show();
                } else {

                    String _id = ID.getText().toString();
                    long id_delete = Long.parseLong(_id);
                    desejo.setId(id_delete);

                    desejosDao.delete(desejo);

                    ID.setText("");
                    PRODUTO.setText("");
                    CATEGORIA.setText("");
                    PRECOMIN.setText("");
                    PRECOMAX.setText("");
                    LOJA.setText("");

                    Carrega_Lista_Desejos();
                    Toast.makeText(getApplicationContext(), "Desejo excluído com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
