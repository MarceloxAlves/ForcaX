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

    public List<PalavraFrase> iniciarRodada(Box<PalavraFrase> palavraFraseBox){
        List<PalavraFrase> palavrasEscolhidas = new ArrayList<>();
        List<PalavraFrase> palavraFrases;
        if(sortearTipoTexto() ==  TipoTexto.Palavra.getCodigo()){
            palavraFrases = getPalavraFrases(palavraFraseBox, TipoTexto.Palavra);
            for (int i = 0; i < sortearQuantidadePalavras() ; i++) {
                palavrasEscolhidas.add(palavraFrases.get(i));
            }
        }else{
             palavraFrases = getPalavraFrases(palavraFraseBox, TipoTexto.Palavra);
            palavrasEscolhidas.add(palavraFrases.get(new Random().nextInt(palavraFrases.size() - 1)));

        }

        return palavrasEscolhidas;
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
