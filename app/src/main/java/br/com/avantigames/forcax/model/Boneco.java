package br.com.avantigames.forcax.model;

import java.util.ArrayList;
import java.util.List;

public class Boneco {
    private int bonecoHP;

    public Boneco() {
        this.bonecoHP = 8;
    }

    public boolean isDead(){
        if(bonecoHP == 0){
            return true;
        }else {
            return false;
        }
    }

    public void setErro(){
        this.bonecoHP--;
    }

    public int getBonecoHP(){
        return this.bonecoHP;
    }
}
