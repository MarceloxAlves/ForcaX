package br.com.avantigames.forcax.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.avantigames.forcax.R;
import br.com.avantigames.forcax.infra.App;
import br.com.avantigames.forcax.model.Jogador;
import br.com.avantigames.forcax.model.PalavraFrase;
import br.com.avantigames.forcax.model.Rodada;
import io.objectbox.Box;

public class GameActivity extends AppCompatActivity {

    LinearLayout palavrasLayout;
    RelativeLayout relativeForcaImg;
    ImageView head;
    ImageView body;
    ImageView leftArm;
    ImageView rigthArm;
    ImageView rigthLeg;
    ImageView leftLeg;
    ImageView rigthFoot;
    ImageView leftFoot;
    List<TextView> textViewList;

    Box<PalavraFrase> palavraFraseBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textViewList = new ArrayList<>();
        palavraFraseBox = ((App) getApplication()).getBoxStore().boxFor(PalavraFrase.class);

        initialize();
        iniciarGame();
    }




    public void onAdivinhar(View view) {

    }

    public void onDica(View view) {

    }

    public void onDigitarPalavra(View view) {
    }

    private void iniciarGame() {
        ocultarBoneco();
        Rodada rodada = new Rodada();
        montarPalavras(rodada.iniciarRodada(palavraFraseBox));
    }

    private void montarPalavras(List<String> palavras) {
        for (int a = 0; a < palavras.size(); a++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity =  Gravity.CENTER;
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setPadding(10,0,10,0);

            for (int i = 0; i < palavras.get(a).length(); i++) {
                TextView textView = new TextView(this);
                LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParamsText.setMargins(0, 0, 10, 0);
                textView.setLayoutParams(layoutParamsText);
                textView.setText("_");
                textView.setTextSize(50);
                linearLayout.addView(textView);
                textViewList.add(textView);
            }
            palavrasLayout.addView(linearLayout);
        }
    }

    private void ocultarBoneco(){
        head.setVisibility(View.INVISIBLE);
        body.setVisibility(View.INVISIBLE);
        leftArm.setVisibility(View.INVISIBLE);
        rigthArm.setVisibility(View.INVISIBLE);
        leftFoot.setVisibility(View.INVISIBLE);
        rigthFoot.setVisibility(View.INVISIBLE);
        leftLeg.setVisibility(View.INVISIBLE);
        rigthLeg.setVisibility(View.INVISIBLE);
    }


    private void initialize() {
        palavrasLayout = findViewById(R.id.palavras);
        relativeForcaImg = findViewById(R.id.relativeForcaImg);

        head        = findViewById(R.id.headImage);
        body        = findViewById(R.id.body);
        leftArm     = findViewById(R.id.leftArm);
        rigthArm    = findViewById(R.id.rigthArm);
        leftFoot    = findViewById(R.id.leftFoot);
        rigthFoot   = findViewById(R.id.rigthFoot);
        leftLeg     = findViewById(R.id.leftLeg);
        rigthLeg    = findViewById(R.id.rigthLeg);
    }
}
