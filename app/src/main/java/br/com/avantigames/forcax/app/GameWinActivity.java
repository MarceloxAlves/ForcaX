package br.com.avantigames.forcax.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import br.com.avantigames.forcax.R;

public class GameWinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_win);
        ImageView image = (ImageView)findViewById(R.id.win);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slider);
        image.startAnimation(animation);
    }

    public void onContinuar(View view) {

    }

    public void onRecordes(View view) {
        finish();
       //TODO
    }

    public void sair(View view) {
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
