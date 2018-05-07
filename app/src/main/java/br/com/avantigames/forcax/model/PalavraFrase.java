package br.com.avantigames.forcax.model;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by Marcelo on 06/05/2018.
 */

@Entity
public class PalavraFrase {

    @Id
    public long id;
    private String descricao;
    private int tipoTexto;
    private ToOne<Tema> temaToOne;
    private String dica;

    public PalavraFrase() {

    }

    public PalavraFrase(String descricao, int tipoTexto, ToOne<Tema> temaToOne, String dica) {
        this.descricao = descricao;
        this.tipoTexto = tipoTexto;
        this.temaToOne = temaToOne;
        this.dica = dica;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipoTexto() {
        return tipoTexto;
    }

    public void setTipoTexto(int tipoTexto) {
        this.tipoTexto = tipoTexto;
    }

    public ToOne<Tema> getTemaToOne() {
        return temaToOne;
    }

    public void setTemaToOne(ToOne<Tema> temaToOne) {
        this.temaToOne = temaToOne;
    }

    public String getDica() {
        return dica;
    }

    public void setDica(String dica) {
        this.dica = dica;
    }

    public List<Integer> adivinhar(char letra){
        List<Integer> posicoes = new ArrayList<>();
            for (int i = 0; i < this.descricao.length() ; i++) {
                if(descricao.charAt(i) == letra){
                    posicoes.add(i);
                }
             }

             return posicoes;
    }

}
