package com.example.proyectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Adapter.JugadorAdapter;
import com.example.proyectofinal.Model.Jugador;
import com.example.proyectofinal.Model.Equipo;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.DocumentSnapshot;
import com.bumptech.glide.Glide;

public class ListaJugadoresEquipo extends Fragment {

    NavController navController;
    RecyclerView recyclerListone;
    JugadorAdapter jugadorAdapter;
    FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        // Obtener información del equipo
        DocumentReference equipoRef = firestore.collection("equipos").document(equipoIdSeleccionado);
        equipoRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Equipo equipo = documentSnapshot.toObject(Equipo.class);
                    if (equipo != null) {
                        // Establecer el logo y el nombre del equipo en las vistas correspondientes
                        ImageView logoImageView = view.findViewById(R.id.imageViewLogo);
                        TextView nombreEquipoTextView = view.findViewById(R.id.textViewName);
                        // Aquí estableces el logo y el nombre del equipo en las vistas
                        nombreEquipoTextView.setText(equipo.getNombre());
                        // Utiliza Glide u otra biblioteca para cargar la imagen del logo
                        Glide.with(requireContext()).load(equipo.getLogo()).into(logoImageView);

                        // Crear y establecer el adaptador para el RecyclerView
                        jugadorAdapter = new JugadorAdapter(options);
                        recyclerListone.setAdapter(jugadorAdapter);

                        // Llamar a startListening() aquí después de que jugadorAdapter se haya inicializado correctamente
                        jugadorAdapter.startListening();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // No es necesario llamar a startListening() aquí ya que se ha llamado dentro del callback onSuccess()
    }

    @Override
    public void onStop() {
        super.onStop();
        if (jugadorAdapter != null) {
            jugadorAdapter.stopListening();
        }
    }
}
