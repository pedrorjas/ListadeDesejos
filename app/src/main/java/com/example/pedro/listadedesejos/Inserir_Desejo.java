package com.example.pedro.listadedesejos;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;


public class Inserir_Desejo extends ActionBarActivity {

    Desejos desejosDao;
    Desejo desejo;
    EditText PRODUTO, CATEGORIA, PRECO_MIN, PRECO_MAX, LOJA;
    public static final String[] Colunas = {"produto"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_desejo);

        desejosDao = new Desejos(this);
        desejo = new Desejo(null,null,null,null,null);


        PRODUTO = (EditText) findViewById(R.id.et_produto_insert);
        CATEGORIA = (EditText) findViewById(R.id.et_categoria_insert);
        PRECO_MIN = (EditText) findViewById(R.id.et_preco_min_insert);
        PRECO_MAX = (EditText) findViewById(R.id.et_preco_max_insert);
        LOJA = (EditText) findViewById(R.id.et_loja_achar_insert);
        FormatarText();
    }

    public void Salvar(){

                try {
                    if (PRODUTO.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Preencha o campo desejo!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    desejo.setProduto(PRODUTO.getText().toString());
                    desejo.setCategoria(CATEGORIA.getText().toString());
                    desejo.setPrecoMin(PRECO_MIN.getText().toString());
                    desejo.setPrecoMax(PRECO_MAX.getText().toString());
                    desejo.setLoja(LOJA.getText().toString());

                    desejosDao.open();
                    desejosDao.inserir(desejo);
                    desejosDao.close();

                    PRODUTO.setText("");
                    CATEGORIA.setText("");
                    PRECO_MIN.setText("");
                    PRECO_MAX.setText("");
                    LOJA.setText("");

                    Toast.makeText(getApplicationContext(),"Dados salvos!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    desejosDao.Menssagem_Alerta("Algo deu errado", "Verifique :\n" + e);
                }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inserir_desejo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.salvar_menu_ins) {

                if (PRODUTO.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Selecione um desejo para excluir!", Toast.LENGTH_SHORT).show();
                }
                Salvar();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void FormatarText(){
        /*Formata os TextView na moeda corente============================================*/

        // TextView Add_PrecoMin ==========================================
        PRECO_MIN.addTextChangedListener(new TextWatcher() {
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
                // Verifica se já existe a máscara no texto
                boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) && (str.indexOf(".") > -1 || str.indexOf(",") > -1));

                // Verificamos se existe máscara
                if (hasMask) {
                    // Retiramos a máscara.
                    str = str.replaceAll("[R$]", "").replaceAll("[,]", "").replaceAll("[.]", "");
                    isUpdating = true;
                }

                try {
                    // Transformamos o número que está escrito no EditText em
                    // monetário.
                    str = nf.format(Double.parseDouble(str) / 100);
                    PRECO_MIN.setText(str);
                    PRECO_MIN.setSelection(PRECO_MIN.getText().length());
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
        PRECO_MAX.addTextChangedListener(new TextWatcher() {

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
                    PRECO_MAX.setText(str);
                    PRECO_MAX.setSelection(PRECO_MAX.getText().length());
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
