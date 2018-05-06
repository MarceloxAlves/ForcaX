package br.com.avantigames.forcax.model;

public class Jogador {
    public long id;
    private String nome;
    private int score;

    public Jogador(String nome, long id, int score) {
        this.nome = nome;
        this.id = id;
        this.score = score;
    }

    public void pontuar(){
        //ToDo
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
