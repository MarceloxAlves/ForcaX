package br.com.avantigames.forcax.model;

import java.util.Random;

/**
 * Created by Marcelo on 06/05/2018.
 */

public class Rodada {



    private int sortearQuantidadePalavras(){
        return  new Random().nextInt(2) + 1;
    }

    private int sortearTipoTexto(){
        return  new Random().nextInt(TipoTexto.Palavra.getCodigo());
    }
}
