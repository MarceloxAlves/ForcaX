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

    private int acertos;
    private int totalLetras;


    public boolean isVencedor(){
        return acertos == totalLetras;
    }

    public void acertou(){
        this.acertos++;
    }

    public List<PalavraFrase> iniciarRodada(Box<PalavraFrase> palavraFraseBox){
        List<PalavraFrase> palavrasEscolhidas = new ArrayList<>();
        List<PalavraFrase> palavraFrases;
        if(sortearTipoTexto() ==  TipoTexto.Palavra.getCodigo()){
            palavraFrases = getPalavraFrases(palavraFraseBox, TipoTexto.Palavra);
            for (int i = 0; i < sortearQuantidadePalavras() ; i++) {
                totalLetras += palavraFrases.get(i).getDescricao().length();
                palavrasEscolhidas.add(palavraFrases.get(i));
            }
        }else{
            palavraFrases = getPalavraFrases(palavraFraseBox, TipoTexto.Palavra);
             List<PalavraFrase> palavras = palavraFrases.get(new Random().nextInt(palavraFrases.size() - 1)).converterFraseEmPalavras();
            for (PalavraFrase palavra: palavras) {
                totalLetras += palavra.getDescricao().length();
            }
            palavrasEscolhidas = palavras;
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