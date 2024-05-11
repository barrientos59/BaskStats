package com.example.proyectofinal.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.Model.Equipo;
import com.example.proyectofinal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EquipoAdapter extends FirestoreRecyclerAdapter<Equipo, EquipoAdapter.EquipoViewHolder> {

    private Context mContext;

    public EquipoAdapter(@NonNull FirestoreRecyclerOptions<Equipo> options, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull EquipoViewHolder holder, int position, @NonNull Equipo model) {
        holder.textViewName.setText(model.getNombre());

        if (model.getLogo() != null && holder.imageViewLogo != null) {
            Glide.with(holder.itemView.getContext())
                    .load(model.getLogo())
                    .into(holder.imageViewLogo);
        } else {
            holder.imageViewLogo.setImageResource(R.drawable.wnba);
        }

        // Agregar OnClickListener para cada elemento del RecyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String equipoId = getSnapshots().getSnapshot(position).getId();
                Bundle bundle = new Bundle();
                bundle.putString("equipoId", equipoId);
                Navigation.findNavController(view).navigate(R.id.listaJugadoresEquipo, bundle);
            }
        });
    }

    @NonNull
    @Override
    public EquipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_team, parent, false);
        return new EquipoViewHolder(view);
    }

    static class EquipoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewLogo;
        TextView textViewName;

        public EquipoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewLogo = itemView.findViewById(R.id.imageViewLogo);
            textViewName = itemView.findViewById(R.id.textViewName);
        }
    }
}
