package br.com.avantigames.forcax.model;

import java.util.ArrayList;
import java.util.List;

public class Boneco {
    private List<Integer> parte = new ArrayList<>();
    private int progresso;

    public boolean isDead(List<String> erros){
        if(parte.size() >= erros.size()){
            return true;
        }else {
            return false;
        }
    }
}
