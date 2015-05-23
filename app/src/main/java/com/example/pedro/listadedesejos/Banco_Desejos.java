package com.example.pedro.listadedesejos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco_Desejos extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "dbDesejos";
    private static final int VERSAO_BANCO = 1;

    public static final String TABELA_DESEJOS = "desejos";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_PRODUTO = "produto";
    public static final String COLUNA_CATEGORIA = "categoria";
    public static final String COLUNA_PRECO_MIN = "preco_Min";
    public static final String COLUNA__PRECO_MAX = "preco_Max";
    public static final String COLUNA_LOJA = "loja";

    public Banco_Desejos(Context context){
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                " CREATE TABLE " + TABELA_DESEJOS + "(" +
                        COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUNA_PRODUTO + " TEXT, " +
                        COLUNA_CATEGORIA + " TEXT, " +
                        COLUNA_PRECO_MIN + " TEXT," +
                        COLUNA__PRECO_MAX + " TEXT," +
                        COLUNA_LOJA + " TEXT);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // para próximas versões
    }
}
