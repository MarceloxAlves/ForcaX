package br.com.avantigames.forcax.model;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public PalavraFrase(String descricao, int tipoTexto, String dica) {
        this.descricao = descricao;
        this.tipoTexto = tipoTexto;
        //this.temaToOne = temaToOne;
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

    public String getDica() {
        return dica;
    }

    public void setDica(String dica) {
        this.dica = dica;
    }

    public List<Integer> adivinhar(char letra){
        List<Integer> posicoes = new ArrayList<>();
         this.descricao.replace(" ","");
         Collator collator = Collator.getInstance(new Locale("pt","BR"));
         collator.setStrength(Collator.PRIMARY);
            for (int i = 0; i < this.descricao.length() ; i++) {
                if(collator.compare(String.valueOf(descricao.charAt(i)), String.valueOf(letra)) == 0){
                    posicoes.add(i);
                }
             }

             return posicoes;
    }

    public static boolean adivinharPalavra(String palavra1, String palavra2){
         Collator collator = Collator.getInstance(new Locale("pt","BR"));
         collator.setStrength(Collator.PRIMARY);
              return  collator.compare(palavra1, palavra2) == 0;
    }

    public List<PalavraFrase> converterFraseEmPalavras(){
        String[] words = this.descricao.split(" ");
        List<PalavraFrase> palavraFrases = new ArrayList<>();
        for (int i = 0; i < words.length ; i++) {
            PalavraFrase palavra = new PalavraFrase(words[i],TipoTexto.Palavra.getCodigo(),this.dica);
            if(i==0)
                palavra.getTemaToOne().setTarget(this.getTemaToOne().getTarget());
            else palavra.getTemaToOne().setTarget(new Tema(""));

            palavraFrases.add(palavra);
        }
        return palavraFrases;
    }

}
