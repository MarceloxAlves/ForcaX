package br.com.avantigames.forcax.model;

/**
 * Created by Marcelo on 06/05/2018.
 */

public enum TipoTexto {
    Frase(0), Palavra(1);
    private final int codigo;

    TipoTexto(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
