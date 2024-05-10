package com.example.proyectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Adapter.JugadorAdapter;
import com.example.proyectofinal.Model.Jugador;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListaJugadoresEquipo extends Fragment {

    NavController navController;
    RecyclerView recyclerListone;
    JugadorAdapter jugadorAdapter;
    FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_jugadores, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        recyclerListone = view.findViewById(R.id.recyclerListone);
        recyclerListone.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Inicializar Firestore
        firestore = FirebaseFirestore.getInstance();

        // Obtener el ID del equipo seleccionado desde el fragmento anterior
        String equipoIdSeleccionado = getArguments().getString("equipoId");

        // Realizar la consulta para obtener la lista de jugadores del equipo seleccionado
        Query query = firestore.collection("jugadores").whereEqualTo("equipoId", equipoIdSeleccionado);

        FirestoreRecyclerOptions<Jugador> options = new FirestoreRecyclerOptions.Builder<Jugador>()
                .setQuery(query, Jugador.class)
                .build();

        // Crear y establecer el adaptador para el RecyclerView
        jugadorAdapter = new JugadorAdapter(options);
        recyclerListone.setAdapter(jugadorAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        jugadorAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        jugadorAdapter.stopListening();
    }
}
