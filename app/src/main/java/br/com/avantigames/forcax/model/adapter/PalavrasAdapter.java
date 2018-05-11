package br.com.avantigames.forcax.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.avantigames.forcax.R;
import br.com.avantigames.forcax.model.Jogador;
import br.com.avantigames.forcax.model.PalavraFrase;
import io.objectbox.Box;

public class PalavrasAdapter extends RecyclerView.Adapter<PalavrasAdapter.PalavraViewHolder> {

    private Context context;
    private List<PalavraFrase> palavraFrasesList;
    private Box<PalavraFrase> palavraFraseBox;

    public PalavrasAdapter(Context context, List<PalavraFrase> palavraFrasesList) {
        this.context = context;
        this.palavraFrasesList = palavraFrasesList;
    }

    public PalavrasAdapter(Context context, Box<PalavraFrase> palavraFraseBox) {
        this.context = context;
        this.palavraFraseBox = palavraFraseBox;
        this.palavraFrasesList = palavraFraseBox.getAll();
    }

    @Override
    public PalavraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View linha = inflater.inflate(R.layout.activity_item_palavra_view_holder, parent, false);
        return new PalavrasAdapter.PalavraViewHolder(linha) ;
    }

    @Override
    public void onBindViewHolder(PalavraViewHolder holder, int position) {
        PalavraFrase palavraFrase = palavraFrasesList.get(position);
        holder.dicaText.setText(palavraFrase.getDica());
        holder.palavraText.setText(palavraFrase.getDescricao());
        holder.temaText.setText(palavraFrase.getTemaToOne().getTarget().getDescricao());

        holder.itemView.setOnClickListener((View view) ->{
            PopupMenu popupMenu = new PopupMenu(context,view);
            popupMenu.getMenuInflater().inflate(R.menu.delete,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener((MenuItem ->{
                if(MenuItem.getItemId() == R.id.delete_palavra){
                    palavraFraseBox.remove(palavraFrase.id);
                    palavraFrasesList.remove(palavraFrase);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                }
                return false;
            }));
            popupMenu.show();
        });

    }


    @Override
    public int getItemCount() {
        return palavraFrasesList.size();
    }

    public class PalavraViewHolder extends RecyclerView.ViewHolder{
        TextView palavraText, dicaText, temaText;
        public PalavraViewHolder(View itemView) {
            super(itemView);
            palavraText = itemView.findViewById(R.id.palavra_frase_show);
            dicaText = itemView.findViewById(R.id.dica_show);
            temaText = itemView.findViewById(R.id.tema_show);
        }
    }

}
