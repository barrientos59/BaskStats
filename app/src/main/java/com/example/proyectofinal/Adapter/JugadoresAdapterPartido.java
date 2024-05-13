package com.example.proyectofinal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Model.Jugador;
import com.example.proyectofinal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class JugadoresAdapterPartido extends FirestoreRecyclerAdapter<Jugador, JugadoresAdapterPartido.JugadorViewHolder> {

    public JugadoresAdapterPartido(@NonNull FirestoreRecyclerOptions<Jugador> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull JugadorViewHolder holder, int position, @NonNull Jugador model) {
        holder.txtDorsal.setText(model.getDorsal());
    }

    @NonNull
    @Override
    public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jugador, parent, false);
        return new JugadorViewHolder(view);
    }

    public static class JugadorViewHolder extends RecyclerView.ViewHolder {
        TextView txtDorsal;

        public JugadorViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDorsal = itemView.findViewById(R.id.textViewPlayerDorsal); // Asegúrate de que sea textViewPlayerDorsal
        }
    }
}

