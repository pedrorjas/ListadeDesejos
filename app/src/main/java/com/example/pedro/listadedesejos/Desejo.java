package com.example.pedro.listadedesejos;

public class Desejo{
    public long id;
    public String produto;
    public String categoria;
    public String precoMin;
    public String precoMax;
    public String loja;

    public Desejo(long id, String produto, String categoria, String precoMin, String precoMax, String loja){
        this.id = id;
        this.produto = produto;
        this.categoria = categoria;
        this.precoMin = precoMin;
        this.precoMax = precoMax;
        this.loja = loja;
    }

    public Desejo(String produto, String categoria, String precoMin, String precoMax, String loja){
        this(0, produto, categoria, precoMin, precoMax, loja);
    }

    @Override
    public String toString(){
        return produto;
    }
    public long tolong(){
        return id;
    }

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }

    public String getProduto(){
        return produto;
    }
    public void setProduto(String produto){
        this.produto = produto;
    }

    public String getCategoria(){
        return categoria;
    }
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    public String getPrecoMax(){
        return precoMax;
    }
    public void setPrecoMax(String precoMax){
        this.precoMax = precoMax;
    }

    public String getPrecoMin(){
        return precoMin;
    }
    public void setPrecoMin(String precoMin){
        this.precoMin = precoMin;
    }

    public String getLoja(){
        return loja;
    }
    public void setLoja(String loja){
        this.loja = loja;
    }
}
