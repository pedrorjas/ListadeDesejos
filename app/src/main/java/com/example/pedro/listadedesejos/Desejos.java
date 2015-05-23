package com.example.pedro.listadedesejos;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Desejos {
    private Banco_Desejos banco_desejos;
    private SQLiteDatabase db;

    public Desejos(Context context){
        banco_desejos = new Banco_Desejos(context);
    }

    public void open() throws SQLException {
        banco_desejos.getWritableDatabase();
    }

    public void close() {
        banco_desejos.close();
    }

    public void inserir(Desejo desejo){
        db = banco_desejos.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Banco_Desejos.COLUNA_PRODUTO, desejo.getProduto());
        cv.put(Banco_Desejos.COLUNA_CATEGORIA, desejo.getCategoria());
        cv.put(Banco_Desejos.COLUNA_PRECO_MIN, desejo.getPrecoMin());
        cv.put(Banco_Desejos.COLUNA__PRECO_MAX, desejo.getPrecoMax());
        cv.put(Banco_Desejos.COLUNA_LOJA, desejo.getLoja());

        db.insert(Banco_Desejos.TABELA_DESEJOS, null, cv);
        db.close();
        return;
    }

    public void delete(Desejo desejo){
        db = banco_desejos.getWritableDatabase();
        long id = desejo.getId();
        db.delete(Banco_Desejos.TABELA_DESEJOS, Banco_Desejos.COLUNA_ID + " =" + id, null);
        return;
    }

    public void alterar_desejo(int id, String produto, String categoria, String precoMin, String precoMax, String loja){

        db = banco_desejos.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put(banco_desejos.COLUNA_ID, id);
        cv.put(Banco_Desejos.COLUNA_PRODUTO, produto);
        cv.put(Banco_Desejos.COLUNA_CATEGORIA, categoria);
        cv.put(Banco_Desejos.COLUNA_PRECO_MIN, precoMin);
        cv.put(Banco_Desejos.COLUNA__PRECO_MAX, precoMax);
        cv.put(Banco_Desejos.COLUNA_LOJA, loja);

        db.update(Banco_Desejos.TABELA_DESEJOS, cv, Banco_Desejos.COLUNA_ID + " =" + id, null);
        db.close();
    }


    public List<Desejo> getALLDesejos(){
        db = banco_desejos.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Banco_Desejos.TABELA_DESEJOS, null);

        List<Desejo> desejos = new ArrayList<>();
        while (cursor.moveToNext()){
            String produto = cursor.getString(cursor.getColumnIndex(Banco_Desejos.COLUNA_PRODUTO));
            String categoria = cursor.getString(cursor.getColumnIndex(Banco_Desejos.COLUNA_CATEGORIA));
            String precoMin = cursor.getString(cursor.getColumnIndex(Banco_Desejos.COLUNA_PRECO_MIN));
            String precoMax = cursor.getString(cursor.getColumnIndex(Banco_Desejos.COLUNA__PRECO_MAX));
            String loja = cursor.getString(cursor.getColumnIndex(Banco_Desejos.COLUNA_LOJA));

            Desejo desejo = new Desejo(produto, categoria, precoMin, precoMax, loja);
            desejos.add(desejo);
        }
        cursor.close();
        db.close();
        return desejos;
    }

    public Boolean VerificaRegistro() {
        try {
            db = banco_desejos.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + Banco_Desejos.TABELA_DESEJOS, null);
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                cursor.close();
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }

    public Cursor getCursor(){
        db = banco_desejos.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Banco_Desejos.TABELA_DESEJOS, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void Menssagem_Alerta(String Titulo, String MensagemAlerta) {
        AlertDialog.Builder Menssagem = new AlertDialog.Builder(null);
        Menssagem.setTitle(Titulo);
        Menssagem.setMessage(MensagemAlerta);
        Menssagem.setNeutralButton("OK", null);
        Menssagem.show();
    }
}
