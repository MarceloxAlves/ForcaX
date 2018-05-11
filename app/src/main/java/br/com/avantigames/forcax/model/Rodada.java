package br.com.avantigames.forcax.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.objectbox.Box;

/**
 * Created by Marcelo on 06/05/2018.
 */

public class Rodada implements Serializable {

    private int acertos;
    private int totalLetras;
    private Jogador jogador;
    private Boneco boneco;
    private Box<PalavraFrase> palavraFraseBox;



    public boolean isVencedor(){
        return acertos == totalLetras;
    }

    public Rodada(Jogador jogador, Boneco boneco, Box<PalavraFrase> palavraFraseBox) {
        this.jogador = jogador;
        this.boneco = boneco;
        this.palavraFraseBox = palavraFraseBox;
        totalLetras = 0;

    }

    public Jogador getJogador() {
        return jogador;
    }

    public Boneco getBoneco() {
        return boneco;
    }

    public void acertou(){
        this.acertos++;
    }

    public int getPontos(){
        return 100 + (totalLetras - acertos) * 15;
    }

    public List<PalavraFrase> palavrasEscolhidas(){
        List<PalavraFrase> palavrasEscolhidas = new ArrayList<>();
        List<PalavraFrase> palavraFrases;
        if(sortearTipoTexto() ==  TipoTexto.Palavra.getCodigo()){
            palavraFrases = getPalavraFrases(this.palavraFraseBox, TipoTexto.Palavra);
            for (int i = 0; i < sortearQuantidadePalavras() ; i++) {
                totalLetras += palavraFrases.get(i).getDescricao().length();
                palavrasEscolhidas.add(palavraFrases.get(i));
            }
        }else{
            palavraFrases = getPalavraFrases(this.palavraFraseBox, TipoTexto.Palavra);
             List<PalavraFrase> palavras = palavraFrases.get(new Random().nextInt(palavraFrases.size())).converterFraseEmPalavras();
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
        return  new Random().nextInt(2);
    }


}