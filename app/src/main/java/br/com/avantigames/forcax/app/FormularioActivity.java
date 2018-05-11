package br.com.avantigames.forcax.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import br.com.avantigames.forcax.R;
import br.com.avantigames.forcax.infra.App;
import br.com.avantigames.forcax.model.Jogador;
import br.com.avantigames.forcax.model.Jogador_;
import br.com.avantigames.forcax.model.PalavraFrase;
import br.com.avantigames.forcax.model.Tema;
import br.com.avantigames.forcax.model.Tema_;
import br.com.avantigames.forcax.model.TipoTexto;
import io.objectbox.Box;

public class FormularioActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RadioButton fraseRb,palavraRb;
    private EditText editPalavraFrase, editDica;
    private Box<PalavraFrase> palavraFraseBox;
    private Box<Tema> temaBox;
    private Spinner spinnerTema;
    private PalavraFrase nPalavraFrase;
    private List<Tema> temaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        setupViews();
        spinnerTema.setOnItemSelectedListener(this);
        palavraFraseBox = ((App)getApplication()).getBoxStore().boxFor(PalavraFrase.class);
        temaBox = ((App)getApplication()).getBoxStore().boxFor(Tema.class);
        nPalavraFrase = new PalavraFrase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencherTema();
    }

    private void setupViews() {
        fraseRb = findViewById(R.id.frase_btn);
        palavraRb = findViewById(R.id.palvra_btn);
        editPalavraFrase = findViewById(R.id.palavra_frase);
        editDica = findViewById(R.id.dica);
        spinnerTema = findViewById(R.id.tema_spinner);
    }

    private void preencherTema(){
        temaList = temaBox.query().order(Tema_.descricao).build().find();
        ArrayAdapter<Tema> arrayAdapter = new ArrayAdapter<Tema>(
                this, android.R.layout.simple_spinner_item, temaList);

        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        spinnerTema.setAdapter(arrayAdapter);
    }


    public void salvar(View view) {
        if (!editDica.equals("") && !editPalavraFrase.equals("")){
            nPalavraFrase.setDica(editDica.getText().toString());
            if(fraseRb.isChecked()){
                nPalavraFrase.setTipoTexto(TipoTexto.Frase.getCodigo());
            }else if(palavraRb.isChecked()){
                nPalavraFrase.setTipoTexto(TipoTexto.Palavra.getCodigo());
            }
            if (palavraRb.isChecked()){
                nPalavraFrase.setDescricao(editPalavraFrase.getText().toString().substring(0));
            }else {
                nPalavraFrase.setDescricao(editPalavraFrase.getText().toString());
            }
        }else{
            Toast.makeText(this,"Preencha os campos!!",Toast.LENGTH_LONG).show();
            return;
        }
        palavraFraseBox.put(nPalavraFrase);
        startActivity(new Intent(this,ListarPalavrasActivity.class));
        finish();


    }


    public void adicinarTema(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.activity_adicionar_tema, null);
        EditText descricaoTema =  inflate.findViewById(R.id.tema);
        builder.setView(inflate)
                .setNegativeButton("Cancelar", (dialog, id) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("Adicionar", (dialog, id) -> {
                    if (!descricaoTema.getText().equals("")){
                        temaBox.put(new Tema(descricaoTema.getText().toString()));
                        this.preencherTema();
                    }else{
                        Toast.makeText(this,"preencha o campo!!",Toast.LENGTH_SHORT).show();
                    }

                });
        builder.create();
        builder.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        nPalavraFrase.getTemaToOne().setTarget(temaList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
