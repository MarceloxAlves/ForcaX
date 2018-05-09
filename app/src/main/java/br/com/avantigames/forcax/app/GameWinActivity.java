package br.com.avantigames.forcax.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.daasuu.cat.CountAnimationTextView;

import br.com.avantigames.forcax.R;
import br.com.avantigames.forcax.infra.App;
import br.com.avantigames.forcax.model.Jogador;
import io.objectbox.Box;

public class GameWinActivity extends AppCompatActivity {

    Box<Jogador> jogadorBox;
    Jogador jogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_win);
        CountAnimationTextView pontos =  findViewById(R.id.pontos);
        int pontuacao =  getIntent().getIntExtra("pontos",0);
        long id  =  getIntent().getLongExtra("id",0);

        jogadorBox = ((App) getApplication()).getBoxStore().boxFor(Jogador.class);

        if(id > 0){
            jogador =  jogadorBox.get(id);
            jogador.setScore(jogador.getScore()+ pontuacao);
            jogadorBox.put(jogador);
        }else {
            finish();
        }
        pontos.setAnimationDuration(1000)
                .countAnimation(0, pontuacao);

        ImageView image = (ImageView)findViewById(R.id.win);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slider);
        image.startAnimation(animation);
    }

    public void onContinuar(View view) {
        finish();
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("id", jogador.id);
        startActivity(intent);
    }

    public void onRecordes(View view) {
        startActivity(new Intent(this,RecordesActivity.class));
    }

    public void sair(View view) {
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
