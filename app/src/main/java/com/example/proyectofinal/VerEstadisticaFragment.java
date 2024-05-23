package com.example.proyectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Adapter.PartidoAdapter;
import com.example.proyectofinal.Model.Partido;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class VerEstadisticaFragment extends Fragment {

    private FirebaseFirestore db;
    private PartidoAdapter partidoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ver_estadistica, container, false);

        db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewEstadisticas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = db.collection("partidos");
        FirestoreRecyclerOptions<Partido> options = new FirestoreRecyclerOptions.Builder<Partido>()
                .setQuery(query, Partido.class)
                .build();

        partidoAdapter = new PartidoAdapter(options);
        recyclerView.setAdapter(partidoAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        partidoAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        partidoAdapter.stopListening();
    }
}
