package br.com.avantigames.forcax.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Jogador {

    @Id
    public long id;
    private String nome;
    private int score;

    public Jogador(String nome) {
        this.nome = nome;
    }

    public Jogador() {
    }

    public void pontuar(){
        //ToDo
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
