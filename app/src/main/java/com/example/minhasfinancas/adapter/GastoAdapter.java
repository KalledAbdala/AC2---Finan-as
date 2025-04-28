package com.example.minhasfinancas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.minhasfinancas.R;
import com.example.minhasfinancas.model.Gasto;

import java.util.List;

public class GastoAdapter extends RecyclerView.Adapter<GastoAdapter.GastoViewHolder> {

    private List<Gasto> listaGastos;

    public GastoAdapter(List<Gasto> listaGastos) {
        this.listaGastos = listaGastos;
    }

    @Override
    public GastoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gasto, parent, false);
        return new GastoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GastoViewHolder holder, int position) {
        Gasto gasto = listaGastos.get(position);
        holder.tvDescricao.setText(gasto.getDescricao());
        holder.tvValor.setText("R$ " + String.format("%.2f", gasto.getValor()));
        holder.tvCategoria.setText(gasto.getCategoria());
        holder.tvData.setText(gasto.getData());
    }

    @Override
    public int getItemCount() {
        return listaGastos.size();
    }

    public static class GastoViewHolder extends RecyclerView.ViewHolder {

        TextView tvDescricao, tvValor, tvCategoria, tvData;

        public GastoViewHolder(View itemView) {
            super(itemView);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            tvValor = itemView.findViewById(R.id.tvValor);
            tvCategoria = itemView.findViewById(R.id.tvCategoria);
            tvData = itemView.findViewById(R.id.tvData);
        }
    }
}
