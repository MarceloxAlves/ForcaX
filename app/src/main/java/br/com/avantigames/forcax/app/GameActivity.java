package br.com.avantigames.forcax.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
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
import br.com.avantigames.forcax.model.Jogador_;
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
    TextView temaTextView;
    TextView jogadorTextView;
    private Boneco boneco;
    Rodada rodada;
    Jogador jogador;

    Box<PalavraFrase> palavraFraseBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textViewList = new ArrayList<>();
        palavraFraseBox = ((App) getApplication()).getBoxStore().boxFor(PalavraFrase.class);
        Box<Jogador> jogadorBox = ((App) getApplication()).getBoxStore().boxFor(Jogador.class);

        long id = getIntent().getLongExtra("id",0);
        if(id > 0){
            jogador =  jogadorBox.get(id);
        }else {
            finish();
        }
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
            rodada.acertou();
            if (rodada.isVencedor()){
                gameWin();
            }
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
               gameOver();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.form_imput, null);
        EditText palavra =  inflate.findViewById(R.id.decricao);

        builder.setView(inflate)
                .setNegativeButton("Cancelar", (dialog, id) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("Adivinhar", (dialog, id) -> {
                     String[] split = palavra.getText().toString().split(" ");
                     if (split.length == palavras.size()){
                         boolean ganhou = true;
                         for (int i = 0; i < palavras.size() ; i++) {
                            if (!PalavraFrase.adivinharPalavra(palavras.get(i).getDescricao(),split[i])){
                                ganhou = false;
                            };
                         }

                         if (ganhou) gameWin();
                         else gameOver();

                     }else
                        gameOver();

                });
        builder.create();
        builder.show();
    }

    private void gameOver(){
        finish();
        startActivity(new Intent(this, GameOverActivity.class));
    }

    private void gameWin(){
        finish();
        Intent intent = new Intent(this, GameWinActivity.class);
        startActivity(intent);
    }

    private void iniciarGame() {
        ocultarBoneco();
        rodada = new Rodada();
        palavras = rodada.iniciarRodada(palavraFraseBox);
        montarPalavras(palavras);
    }

    private void montarPalavras(List<PalavraFrase> palavras) {

        for (int a = 0; a < palavras.size(); a++) {
            LinearLayout linearLayout = getLinearLayout();
            List<TextView> textViews = new ArrayList<>();

            if(palavras.get(a).getTemaToOne().getTarget() != null)
                temaTextView.setText(palavras.get(a).getTemaToOne().getTarget().getDescricao());

            for (int i = 0; i < palavras.get(a).getDescricao().length(); i++) {
                if (palavras.get(a).getDescricao().charAt(i) ==  ' ') {
                    palavrasLayout.addView(linearLayout);
                    linearLayout = getLinearLayout();
                }
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
        temaTextView = findViewById(R.id.tema);
        jogadorTextView = findViewById(R.id.nome_jogador);
        jogadorTextView.setText(jogador.getNome());
        partesBoneco = new ArrayList<>();
        partesBoneco.add(findViewById(R.id.headImage));
        partesBoneco.add(findViewById(R.id.body));
        partesBoneco.add(findViewById(R.id.leftArm));
        partesBoneco.add(findViewById(R.id.rigthArm));
        partesBoneco.add(findViewById(R.id.leftLeg));
        partesBoneco.add(findViewById(R.id.rigthLeg));
        partesBoneco.add(findViewById(R.id.leftFoot));
        partesBoneco.add(findViewById(R.id.rigthFoot));
        partesBoneco.add(findViewById(R.id.rigthFoot));
        boneco = new Boneco();
    }
}
