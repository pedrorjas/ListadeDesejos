package com.example.pedro.listadedesejos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botões abaixo
        Button sobre = (Button) findViewById(R.id.aboult_btn);
        sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Aboult.class);
                startActivity(intent);
            }
        });

        Button add_desejos = (Button) findViewById(R.id.add_desejos_btn);
        add_desejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Inserir_Desejo.class);
                startActivity(intent);
            }
        });

        Button alt_desejos = (Button) findViewById(R.id.alt_desejos_btn);
        alt_desejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Alterar_Desejo.class);
                startActivity(intent);
            }
        });

        Button ver_desejos = (Button) findViewById(R.id.ver_desejos_btn);
        ver_desejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Listar_Desejo.class);
                startActivity(intent);
            }
        });

        Button excluir_desejos = (Button) findViewById(R.id.excluir_btn_main);
        excluir_desejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Excluir_Desejo.class);
                startActivity(intent);
            }
        });
        // Botões acima
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


}
