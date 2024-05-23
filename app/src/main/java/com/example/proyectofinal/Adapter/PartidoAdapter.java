package com.example.proyectofinal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Model.Partido;
import com.example.proyectofinal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PartidoAdapter extends FirestoreRecyclerAdapter<Partido, PartidoAdapter.PartidoViewHolder> {

    public PartidoAdapter(@NonNull FirestoreRecyclerOptions<Partido> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PartidoViewHolder holder, int position, @NonNull Partido model) {
        holder.textViewEquipoLocal.setText(model.getJugadoresLocal().isEmpty() ? "N/A" : model.getJugadoresLocal().get(0).getEquipoId());
        holder.textViewEquipoVisitante.setText(model.getJugadoresVisitante().isEmpty() ? "N/A" : model.getJugadoresVisitante().get(0).getEquipoId());
        holder.textViewPuntosLocal.setText(String.valueOf(model.getPuntosLocal()));
        holder.textViewPuntosVisitante.setText(String.valueOf(model.getPuntosVisitante()));
    }

    @NonNull
    @Override
    public PartidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partido, parent, false);
        return new PartidoViewHolder(view);
    }

    static class PartidoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewEquipoLocal;
        TextView textViewEquipoVisitante;
        TextView textViewPuntosLocal;
        TextView textViewPuntosVisitante;

        public PartidoViewHolder(View itemView) {
            super(itemView);
            textViewEquipoLocal = itemView.findViewById(R.id.textView_equipoLocal);
            textViewEquipoVisitante = itemView.findViewById(R.id.textView_equipoVisitante);
            textViewPuntosLocal = itemView.findViewById(R.id.textView_puntosLocal);
            textViewPuntosVisitante = itemView.findViewById(R.id.textView_puntosVisitante);
        }
    }
}
