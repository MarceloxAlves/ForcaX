package br.com.avantigames.forcax.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Marcelo on 06/05/2018.
 */

@Entity
public class PalavraFrase {

    @Id
    public long id;
    private String descricao;
    private int tipoTexto;

    public PalavraFrase() {
    }

    public PalavraFrase(String descricao, int tipoTexto) {
        this.descricao = descricao;
        this.tipoTexto = tipoTexto;
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

    public boolean adivinhar(char letra){
     return false;
    }
}
