package br.com.avantigames.forcax.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.avantigames.forcax.R;
import br.com.avantigames.forcax.infra.App;
import br.com.avantigames.forcax.model.Jogador;
import br.com.avantigames.forcax.model.Jogador_;
import io.objectbox.Box;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onIniciarGame(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.form_jogador, null);
        EditText nomeJogador =  inflate.findViewById(R.id.nome_jogador);

        builder.setView(inflate)
                .setNegativeButton("Cancelar", (dialog, id) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("Jogar", (dialog, id) -> {
                    Box<Jogador> jogadorBox = ((App) getApplication()).getBoxStore().boxFor(Jogador.class);
                    List<Jogador> jogadores = jogadorBox.query().equal(Jogador_.nome, nomeJogador.getText().toString()).build().find();
                    Jogador jogador;
                    if (jogadores.size() > 0) jogador = jogadores.get(0);
                    else{
                        jogador = new Jogador(nomeJogador.getText().toString());
                    }
                    long codigo  = jogadorBox.put(jogador);
                    Intent intent = new Intent(this,GameActivity.class);
                    intent.putExtra("id",codigo);
                    startActivity(intent);

                });
        builder.create();
        builder.show();
    }

    public void adiciornarFrase(View view) {
        startActivity(new Intent(this,ListarPalavrasActivity.class));
    }

    public void mostrarSobre(View view) {
        startActivity(new Intent(this,AboutActivity.class));

    }

    public void mostrarRecordes(View view) {
        startActivity(new Intent(this,RecordesActivity.class));

    }
}