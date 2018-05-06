package br.com.avantigames.forcax.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Marcelo on 06/05/2018.
 */

@Entity
public class Tema {
    @Id
    public long id;
    private String descricao;

    public Tema() { }
    public Tema(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
