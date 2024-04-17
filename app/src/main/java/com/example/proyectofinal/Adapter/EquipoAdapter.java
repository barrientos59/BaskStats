package com.example.proyectofinal.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.proyectofinal.Model.Equipo;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EquipoAdapter extends FirestoreRecyclerAdapter<Equipo,EquipoAdapter.ViewHolder> {

    public class ViewHolder{
        
    }
    public EquipoAdapter(@NonNull FirestoreRecyclerOptions<Equipo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EquipoAdapter.ViewHolder viewHolder, int i, @NonNull Equipo equipo) {

    }

    @NonNull
    @Override
    public EquipoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }
}
