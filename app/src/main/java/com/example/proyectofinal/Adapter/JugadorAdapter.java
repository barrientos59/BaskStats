package com.example.proyectofinal.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Model.Jugador;
import com.example.proyectofinal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class JugadorAdapter extends FirestoreRecyclerAdapter<Jugador, JugadorAdapter.ViewHolder> {

    public JugadorAdapter(@NonNull FirestoreRecyclerOptions<Jugador> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Jugador jugador) {
        // Log the data
        Log.d("JugadorAdapter", "Jugador: " + jugador.getNombre() + ", Puntos: " + jugador.getPuntos() +
                ", Asistencias: " + jugador.getAsistencias() + ", Rebotes: " + jugador.getRebotes());

        viewHolder.textViewPlayerName.setText(jugador.getDorsal());
        viewHolder.textViewPoints.setText("Puntos: " + jugador.getPuntos());
        viewHolder.textViewAssists.setText("Asistencias: " + jugador.getAsistencias());
        viewHolder.textViewRebounds.setText("Rebotes: " + jugador.getRebotes());
        viewHolder.textViewRobos.setText("Robos: " + jugador.getRobos());
        viewHolder.textViewBlocks.setText("Tapones: " + jugador.getTapones());
        viewHolder.textViewTurnovers.setText("Pérdidas: " + jugador.getPerdidas());
        viewHolder.textViewFouls.setText("Faltas: " + jugador.getFaltas());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_player, parent, false);
        return new ViewHolder(v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPlayer;
        TextView textViewPlayerName, textViewPoints, textViewAssists, textViewRebounds, textViewRobos, textViewBlocks, textViewTurnovers, textViewFouls;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPlayer = itemView.findViewById(R.id.imageViewPlayer);
            textViewPlayerName = itemView.findViewById(R.id.textViewPlayerName);
            textViewPoints = itemView.findViewById(R.id.textViewPoints);
            textViewAssists = itemView.findViewById(R.id.textViewAssists);
            textViewRebounds = itemView.findViewById(R.id.textViewRebounds);
            textViewRobos = itemView.findViewById(R.id.textViewRobos); // Agregar referencia a la vista de robos
            textViewBlocks = itemView.findViewById(R.id.textViewBlocks);
            textViewTurnovers = itemView.findViewById(R.id.textViewTurnovers);
            textViewFouls = itemView.findViewById(R.id.textViewFouls);
        }
    }
}
