package br.com.avantigames.forcax.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.avantigames.forcax.R;
import br.com.avantigames.forcax.infra.App;
import br.com.avantigames.forcax.model.Jogador;
import io.objectbox.Box;

public class GameOverActivity extends AppCompatActivity {

    private Box<Jogador> jogadorBox;
    Jogador jogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        long id  =  getIntent().getLongExtra("id",0);

        jogadorBox = ((App) getApplication()).getBoxStore().boxFor(Jogador.class);

        if(id > 0){
            jogador =  jogadorBox.get(id);
            jogador.setScore(jogador.getScore() -  20);
            jogadorBox.put(jogador);
        }else {
            finish();
        }
    }

    public void tentarNovamente(View view) {
        finish();
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("id", jogador.id);
        startActivity(intent);
    }

    public void sair(View view) {
        finish();
    }
}
