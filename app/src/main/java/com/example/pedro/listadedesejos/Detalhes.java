package com.example.pedro.listadedesejos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class Detalhes extends Listar_Desejo {

    String nome_produto, categoria, preco_Min, preco_Max, loja;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        TextView produto_View = (TextView) findViewById(R.id.detalhes_produto);
        TextView categoria_View = (TextView) findViewById(R.id.detalhes_categoria);
        TextView precoMin_View = (TextView) findViewById(R.id.detalhes_preco_min);
        TextView precoMax_View = (TextView) findViewById(R.id.detalhes_preco_max);
        TextView loja_View = (TextView) findViewById(R.id.detalhes_loja_achar);


        Intent intent = getIntent();

        if (intent != null){
            Bundle params = intent.getExtras();

            if (params != null){
                nome_produto = params.getString("detalhe_produto");
                categoria = params.getString("detalhe_categoria");
                preco_Min = params.getString("detalhe_precoMin");
                preco_Max = params.getString("detalhe_precoMax");
                loja = params.getString("detalhe_loja");

                produto_View.setText(nome_produto);
                categoria_View.setText(categoria);
                precoMin_View.setText(preco_Min);
                precoMax_View.setText(preco_Max);
                loja_View.setText(loja);
            }
        }

        this.getIntent().getExtras();


        ImageButton busca = (ImageButton) findViewById(R.id.buscape_btn);
            busca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = ("http://compare.buscape.com.br/'"+nome_produto+"'");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_detalhes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
