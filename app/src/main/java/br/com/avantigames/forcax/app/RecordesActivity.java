package br.com.avantigames.forcax.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.avantigames.forcax.R;
import br.com.avantigames.forcax.infra.App;
import br.com.avantigames.forcax.model.Jogador;
import br.com.avantigames.forcax.model.adapter.RecordesAdapter;
import io.objectbox.Box;

public class RecordesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Box<Jogador> jogadorBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordes);
        jogadorBox = ((App) getApplication()).getBoxStore().boxFor(Jogador.class);
        recyclerView = findViewById(R.id.rv_recordes);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //ToDo::Fazer o sort dos recordes
        recyclerView.setAdapter(new RecordesAdapter(this,jogadorBox));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }
}
