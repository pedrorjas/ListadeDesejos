package com.example.pedro.listadedesejos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


public class Alterar_Desejo extends ActionBarActivity {

    Desejos desejosDao;
    Desejo desejo;
    Banco_Desejos banco_desejos;
    SQLiteDatabase db;
    ListView listView;
    EditText ET_Produto, ET_Categoria, ET_PrecoMin, ET_PrecoMax, ET_Loja;
    TextView TV_ID;
    static long id;
    public static final String[] Colunas = {"produto"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_desejo);

        desejosDao = new Desejos(this);
        desejo = new Desejo(null,null,null,null,null);
        banco_desejos = new Banco_Desejos(this);

        TV_ID = (TextView) findViewById(R.id.TV_id_alt);
        ET_Produto = (EditText) findViewById(R.id.ET_produto_alt);
        ET_Categoria = (EditText) findViewById(R.id.ET_categoria_alt);
        ET_PrecoMin = (EditText) findViewById(R.id.ET_precoMin_alt);
        ET_PrecoMax = (EditText) findViewById(R.id.ET_precoMax_alt);
        ET_Loja = (EditText) findViewById(R.id.ET_loja_achar_alt);
        FormatarText();

        Carrega_Lista_de_Desejos();

        listView = (ListView) findViewById(R.id.LV_desejos_alt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int Posicao, long arg3) {
                id = arg3;
                Setar_Dados(id);
            }
        });
    }

    public void Alterar(){

        int id = Integer.parseInt(TV_ID.getText().toString());
        desejosDao.open();
        desejosDao.alterar_desejo(id, ET_Produto.getText().toString(), ET_Categoria.getText().toString(), ET_PrecoMin.getText().toString(), ET_PrecoMax.getText().toString(), ET_Loja.getText().toString());
        desejosDao.close();

        TV_ID.setText("");
        ET_Produto.setText("");
        ET_Categoria.setText("");
        ET_PrecoMin.setText("");
        ET_PrecoMax.setText("");
        ET_Loja.setText("");

        Carrega_Lista_de_Desejos();
        Toast.makeText(getApplicationContext(), "Desejo alterado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alterar_desejo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.salvar_menu_alt){

            if (desejosDao.VerificaRegistro()){
                if (ET_Produto.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Selecione um desejo para excluir!", Toast.LENGTH_SHORT).show();
                }
                Alterar();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void Setar_Dados(long Posicao) {
        try{
            db = openOrCreateDatabase(Banco_Desejos.NOME_BANCO, MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM " + Banco_Desejos.TABELA_DESEJOS + " WHERE _id = '" + Posicao + "'", null);
            while (cursor.moveToNext()){
                TV_ID.setText(cursor.getString(cursor.getColumnIndex(Banco_Desejos.COLUNA_ID)));
                ET_Produto.setText(cursor.getString(cursor.getColumnIndex(Banco_Desejos.COLUNA_PRODUTO)));
                ET_Categoria.setText(cursor.getString(cursor.getColumnIndex(Banco_Desejos.COLUNA_CATEGORIA)));
                ET_PrecoMin.setText(cursor.getString(cursor.getColumnIndex(Banco_Desejos.COLUNA_PRECO_MIN)));
                ET_PrecoMax.setText(cursor.getString(cursor.getColumnIndex(Banco_Desejos.COLUNA__PRECO_MAX)));
                ET_Loja.setText(cursor.getString(cursor.getColumnIndex(Banco_Desejos.COLUNA_LOJA)));
            }
            cursor.close();
        }catch (Exception e){
        }
        finally {
            db.close();
        }
    }

    public void Carrega_Lista_de_Desejos(){
        ListView listView = (ListView) findViewById(R.id.LV_desejos_alt);
        Cursor cursor = desejosDao.getCursor();
        if (desejosDao.VerificaRegistro()){
            int[] to = new int[]{R.id.tv_detalhes_item};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(Alterar_Desejo.this, R.layout.detalhes_item, cursor, Colunas, to);
            listView.setAdapter(adapter);
        }else {
            Toast.makeText(getApplicationContext(), "Voce nao possui desejos!", Toast.LENGTH_SHORT).show();
        }
    }

    public void FormatarText(){
        /*Formata os TextView na moeda corente============================================*/

        // TextView Add_PrecoMin ==========================================
        ET_PrecoMin.addTextChangedListener(new TextWatcher() {
            boolean isUpdating = false;
            // Pega a formatacao do sistema, se for brasil R$ se EUA US$
            NumberFormat nf = NumberFormat.getCurrencyInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int after) {
                // Evita que o método seja executado varias vezes.
                // Se tirar ele entre em loop
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString();
                // Verifica se j� existe a m�scara no texto
                boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) && (str.indexOf(".") > -1 || str.indexOf(",") > -1));

                // Verificamos se existe m�scara
                if (hasMask) {
                    // Retiramos a m�scara.
                    str = str.replaceAll("[R$]", "").replaceAll("[,]", "").replaceAll("[.]", "");
                    isUpdating = true;
                }

                try {
                    // Transformamos o n�mero que est� escrito no EditText em
                    // monet�rio.
                    str = nf.format(Double.parseDouble(str) / 100);
                    ET_PrecoMin.setText(str);
                    ET_PrecoMin.setSelection(ET_PrecoMin.getText().length());
                } catch (NumberFormatException e) {
                    s = "";
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // N�o utilizamos
            }

            @Override
            public void afterTextChanged(Editable s) {
                // N�o utilizamos
            }
        });

        // TextView Add_PrecoMax ==========================================
        ET_PrecoMax.addTextChangedListener(new TextWatcher() {

            boolean isUpdating = false;
            // Pega a formatacao do sistema, se for brasil R$ se EUA US$
            NumberFormat nf = NumberFormat.getCurrencyInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int after) {
                // Evita que o m�todo seja executado varias vezes.
                // Se tirar ele entre em loop
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString();
                // Verifica se j� existe a m�scara no texto.
                boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) && (str.indexOf(".") > -1 || str.indexOf(",") > -1));
                // Verificamos se existe m�scara
                if (hasMask) {
                    // Retiramos a m�scara.
                    str = str.replaceAll("[R$]", "").replaceAll("[,]", "").replaceAll("[.]", "");
                    isUpdating = true;
                }

                try {
                    // Transformamos o n�mero que est� escrito no EditText em monet�rio.
                    str = nf.format(Double.parseDouble(str) / 100);
                    ET_PrecoMax.setText(str);
                    ET_PrecoMax.setSelection(ET_PrecoMax.getText().length());
                } catch (NumberFormatException e) {
                    s = "";
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // N�o utilizamos
            }

            @Override
            public void afterTextChanged(Editable s) {
                // N�o utilizamos
            }
        });
    }

}
