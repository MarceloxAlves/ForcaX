package br.com.avantigames.forcax.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.objectbox.Box;

/**
 * Created by Marcelo on 06/05/2018.
 */

public class Rodada {

    public List<String> iniciarRodada(Box<PalavraFrase> palavraFraseBox){
        List<String> palavras = new ArrayList<>();
        List<PalavraFrase> palavraFrases;
        if(sortearTipoTexto() ==  TipoTexto.Palavra.getCodigo()){
            palavraFrases = getPalavraFrases(palavraFraseBox, TipoTexto.Palavra);
            for (int i = 0; i < sortearQuantidadePalavras() ; i++) {
                palavras.add(palavraFrases.get(new Random().nextInt(palavraFrases.size()-1)).getDescricao());
            }
        }else{
             palavraFrases = getPalavraFrases(palavraFraseBox, TipoTexto.Palavra);
             String[] palavrasDaFrase = palavraFrases.get(new Random().nextInt(palavraFrases.size() - 1)).getDescricao().split(" ");
             for (int i = 0; i < palavrasDaFrase.length ; i++) {
                palavras.add(palavrasDaFrase[i]);
            }
        }

        return palavras;
    }

    private List<PalavraFrase> getPalavraFrases(Box<PalavraFrase> palavraFraseBox,TipoTexto tipoTexto) {
        List<PalavraFrase> palavraFrases;
        palavraFrases = palavraFraseBox.query()
               .equal(PalavraFrase_.tipoTexto, tipoTexto.getCodigo())
               .build()
               .find();
        return palavraFrases;
    }

    private int sortearQuantidadePalavras(){
        return  new Random().nextInt(2) + 1;
    }

    private int sortearTipoTexto(){
        return  new Random().nextInt(TipoTexto.Palavra.getCodigo());
    }
}
