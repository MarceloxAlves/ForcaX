package br.com.avantigames.forcax.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.avantigames.forcax.R;
import br.com.avantigames.forcax.infra.App;
import br.com.avantigames.forcax.model.Boneco;
import br.com.avantigames.forcax.model.Jogador;
import br.com.avantigames.forcax.model.PalavraFrase;
import br.com.avantigames.forcax.model.Rodada;
import br.com.avantigames.forcax.model.Tema;
import br.com.avantigames.forcax.model.Tema_;
import br.com.avantigames.forcax.model.TipoTexto;
import io.objectbox.Box;
import io.objectbox.relation.ToOne;

public class GameActivity extends AppCompatActivity {

    LinearLayout palavrasLayout;
    LinearLayout teclado;
    RelativeLayout relativeForcaImg;
    List<ImageView> partesBoneco;
    List<List<TextView>> textViewList;
    List<PalavraFrase> palavras = new ArrayList<>();
    private Boneco boneco;

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
        view.setEnabled(false);

        char letra = ((Button) view).getText().charAt(0);
        boolean acertou =  false;
        for (int i = 0; i < palavras.size(); i++) {
            List<Integer> adivinhar = palavras.get(i).adivinhar(letra);
            if (adivinhar.size() > 0){
                for (Integer a: adivinhar){
                      textViewList.get(i).get(a).setText(String.valueOf(letra));
                    acertou = true;
                }
            }
        }
        if (acertou){
            view.setBackground(getDrawable(R.drawable.btn_acerto));
        }else{
            teclado.setVisibility(View.INVISIBLE);
            view.setBackground(getDrawable(R.drawable.btn_erro));
            mostrarForca();
            mostrarParteBoneco();
            boneco.setErro();
            if(!boneco.isDead()) {
                new android.os.Handler().postDelayed(
                        () -> {
                            teclado.setVisibility(View.VISIBLE);
                            ocultarForca();

                        }, 3000);

            }else{
                teclado.setVisibility(View.INVISIBLE);
                startActivity(new Intent(this, GameOverActivity.class));
            }
        }


    }


    private void mostrarParteBoneco() {
        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fad_in);
        ImageView parteImageView = partesBoneco.get(boneco.getParte());
        parteImageView.setVisibility(View.VISIBLE);
        animFadeIn.reset();
        parteImageView.clearAnimation();
        parteImageView.startAnimation(animFadeIn);
    }

    private void mostrarForca() {
        this.palavrasLayout.setVisibility(View.INVISIBLE);
        this.relativeForcaImg.setVisibility(View.VISIBLE);
    }

    private void ocultarForca() {
        this.palavrasLayout.setVisibility(View.VISIBLE);
        this.relativeForcaImg.setVisibility(View.INVISIBLE);
    }

    public void onDica(View view) {
        // ToDO
    }

    public void onDigitarPalavra(View view) {
    }

    private void iniciarGame() {
        ocultarBoneco();
        Rodada rodada = new Rodada();
        PalavraFrase palavraFrase = new PalavraFrase("RAMBO SAIU CORRENDO E CAGOU",0, "Filme do Rambo" );
        palavras.add(palavraFrase);

        montarPalavras(palavras);
    }

    private void montarPalavras(List<PalavraFrase> palavras) {

        for (int a = 0; a < palavras.size(); a++) {
            LinearLayout linearLayout = getLinearLayout();
            List<TextView> textViews = new ArrayList<>();


            for (int i = 0; i < palavras.get(a).getDescricao().length(); i++) {
                if (palavras.get(a).getDescricao().charAt(i) ==  ' '){
                    palavrasLayout.addView(linearLayout);
                    linearLayout = getLinearLayout();
                };
                TextView textView = getTextViewLetra();
                linearLayout.addView(textView);
                textViews.add(textView);
            }

            textViewList.add(textViews);
            palavrasLayout.addView(linearLayout);
        }
    }

    private TextView getTextViewLetra() {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsText.setMargins(0, 0, 10, 0);
        textView.setLayoutParams(layoutParamsText);
        textView.setText("_");
        textView.setTextSize(50);
        return textView;
    }

    private LinearLayout getLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity =  Gravity.CENTER;
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setPadding(10,0,10,0);
        return linearLayout;
    }

    private void ocultarBoneco(){
        for (ImageView parte: partesBoneco) {
            parte.setVisibility(View.INVISIBLE);
        }

    }

    private void initialize() {
        palavrasLayout = findViewById(R.id.palavras);
        relativeForcaImg = findViewById(R.id.relativeForcaImg);
        teclado = findViewById(R.id.teclado);
        partesBoneco = new ArrayList<>();
        partesBoneco.add(findViewById(R.id.headImage));
        partesBoneco.add(findViewById(R.id.body));
        partesBoneco.add(findViewById(R.id.leftArm));
        partesBoneco.add(findViewById(R.id.rigthArm));
        partesBoneco.add(findViewById(R.id.leftLeg));
        partesBoneco.add(findViewById(R.id.rigthLeg));
        partesBoneco.add(findViewById(R.id.leftFoot));
        partesBoneco.add(findViewById(R.id.rigthFoot));
        boneco = new Boneco();
    }
}
