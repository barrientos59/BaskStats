package com.example.proyectofinal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.Model.Equipo;
import com.example.proyectofinal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EquipoAdapter extends FirestoreRecyclerAdapter<Equipo,EquipoAdapter.ViewHolder> {

    public EquipoAdapter(@NonNull FirestoreRecyclerOptions<Equipo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Equipo equipo) {
        viewHolder.name.setText(equipo.getNombre());


        // Cargar la imagen desde Firebase Storage y establecerla en el ImageView
        if (equipo.getLogo() != null && viewHolder.logo != null) {
            // Cargar la imagen con Glide
            Glide.with(viewHolder.itemView.getContext())
                    .load(equipo.getLogo())
                    .into(viewHolder.logo);
        } else {
            // Si no hay URL de imagen, puedes establecer una imagen de placeholder o dejarla vacía
            viewHolder.logo.setImageResource(R.drawable.wnba);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_team, parent,false);
    return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView logo;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.textViewName);
            logo = itemView.findViewById(R.id.imageViewLogo);
        }
    }

}
