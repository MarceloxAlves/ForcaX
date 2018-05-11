package br.com.avantigames.forcax.app;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.com.avantigames.forcax.R;
import br.com.avantigames.forcax.infra.App;
import br.com.avantigames.forcax.model.PalavraFrase;
import br.com.avantigames.forcax.model.adapter.PalavrasAdapter;
import io.objectbox.Box;

public class ListarPalavrasActivity extends AppCompatActivity {

    private Box<PalavraFrase> palavraFraseBox;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_palavras);
        setupViews();
        palavraFraseBox = ((App) getApplication()).getBoxStore().boxFor(PalavraFrase.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(new PalavrasAdapter(this,palavraFraseBox));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void setupViews() {
        recyclerView =  findViewById(R.id.rv_palavras);
        floatingActionButton = findViewById(R.id.floatin_btn);
    }

    public void addPalavra(View view) {
        startActivity(new Intent(this,FormularioActivity.class));
    }
}
