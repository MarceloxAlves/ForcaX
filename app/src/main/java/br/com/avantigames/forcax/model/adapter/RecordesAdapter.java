package br.com.avantigames.forcax.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.avantigames.forcax.R;
import br.com.avantigames.forcax.model.Jogador;
import br.com.avantigames.forcax.model.Jogador_;
import io.objectbox.Box;

public class RecordesAdapter extends RecyclerView.Adapter<RecordesAdapter.RecordesViewHolder> {

    private Context context;
    private List<Jogador> jogadorList;
    private Box<Jogador> jogadorBox;

    public RecordesAdapter(Context context, List<Jogador> jogadorList) {
        this.context = context;
        this.jogadorList = jogadorList;
    }

    public RecordesAdapter(Context context, Box<Jogador> jogadorBox) {
        this.context = context;
        this.jogadorBox = jogadorBox;
        this.jogadorList = jogadorBox
                            .query()
                            .orderDesc(Jogador_.score)
                            .build()
                            .find();
    }

    @Override
    public RecordesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);

        View linha = inflater.inflate(R.layout.activity_item_recorde_view_holder, parent, false);

        return new RecordesViewHolder(linha) ;
    }

    @Override
    public void onBindViewHolder(RecordesViewHolder holder, int position) {
        Jogador jogador = this.jogadorList.get(position);

        holder.textNome.setText(jogador.getNome());
        holder.textIndex.setText("" + position + 1);
        holder.textPontuacao.setText(""+jogador.getScore());

    }

    @Override
    public int getItemCount() {
        return jogadorList.size();
    }

    public class RecordesViewHolder extends RecyclerView.ViewHolder{
        TextView textIndex, textNome, textPontuacao;
        public RecordesViewHolder(View itemView) {
            super(itemView);
            textIndex = itemView.findViewById(R.id.index_record);
            textNome = itemView.findViewById(R.id.nome_jogador);
            textPontuacao = itemView.findViewById(R.id.pontuacao_jogador);
        }
    }
}
