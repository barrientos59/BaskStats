package com.example.proyectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Adapter.JugadoresAdapterPartido;
import com.example.proyectofinal.Model.Jugador;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class PatidoFragment extends Fragment {

    private Spinner equipoSpinner, equipoSpinner2;
    private ArrayList<String> nombresEquipos = new ArrayList<>();
    private ArrayList<String> idsEquipos = new ArrayList<>();
    private ArrayAdapter<String> adapter1, adapter2;
    private FirebaseFirestore db;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_patido, container, false);
        equipoSpinner = rootView.findViewById(R.id.spinner_equipoLocal);
        equipoSpinner2 = rootView.findViewById(R.id.spinner_equipoVisitante);
        db = FirebaseFirestore.getInstance();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        // Configuración de adaptadores para los Spinners
        adapter1 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombresEquipos);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipoSpinner.setAdapter(adapter1);

        adapter2 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombresEquipos);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipoSpinner2.setAdapter(adapter2);

        // Cargar equipos en los Spinners
        cargarEquiposEnSpinner();

        // Configuración de listeners para los Spinners
        equipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargarJugadoresDelEquipo(idsEquipos.get(position), R.id.recyclerView_jugadoresLocal);
                verificarEquiposDiferentes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se necesita hacer nada aquí
            }
        });

        equipoSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargarJugadoresDelEquipo(idsEquipos.get(position), R.id.recyclerView_jugadoresVisitante);
                verificarEquiposDiferentes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se necesita hacer nada aquí
            }
        });

        return rootView;
    }

    private void verificarEquiposDiferentes() {
        String equipoSeleccionado1 = equipoSpinner.getSelectedItem().toString();
        String equipoSeleccionado2 = equipoSpinner2.getSelectedItem().toString();

        if (equipoSeleccionado1.equals(equipoSeleccionado2)) {
            // Si los equipos son iguales, selecciona un equipo diferente en el segundo Spinner
            int nuevaSeleccion = (equipoSpinner2.getSelectedItemPosition() + 1) % nombresEquipos.size();
            equipoSpinner2.setSelection(nuevaSeleccion);
        }
    }

    private void cargarEquiposEnSpinner() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = currentUser.getUid();

        db.collection("equipos")
                .whereEqualTo("idAutor", currentUserId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        nombresEquipos.clear(); // Limpiar la lista antes de agregar los nuevos nombres de equipos
                        idsEquipos.clear(); // Limpiar la lista de IDs de equipos antes de agregar los nuevos IDs
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String idEquipo = document.getId(); // Obtenemos el ID del equipo
                            idsEquipos.add(idEquipo);
                            String nombreEquipo = document.getString("nombre"); // Obtener el nombre del equipo
                            nombresEquipos.add(nombreEquipo);
                        }
                        adapter1.notifyDataSetChanged();
                        adapter2.notifyDataSetChanged();
                    } else {
                        Toast.makeText(requireContext(), "Error al cargar equipos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Método para cargar los jugadores del equipo seleccionado desde Firebase y mostrarlos en el RecyclerView
    private void cargarJugadoresDelEquipo(String idEquipo, int recyclerViewId) {
        db.collection("jugadores")
                .whereEqualTo("equipoId", idEquipo) // Usamos el ID del equipo
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Jugador> jugadores = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String nombre = document.getString("nombre");
                            String apellido = document.getString("apellido");
                            String dorsal = document.getString("dorsal");
                            String posicion = document.getString("posicion");
                            Jugador jugador = new Jugador(nombre, apellido, dorsal, posicion);
                            jugadores.add(jugador);
                        }

                        // Construir FirestoreRecyclerOptions con la consulta ya realizada
                        Query query = db.collection("jugadores").whereEqualTo("equipoId", idEquipo);
                        FirestoreRecyclerOptions<Jugador> options = new FirestoreRecyclerOptions.Builder<Jugador>()
                                .setQuery(query, Jugador.class)
                                .build();

                        // Crear el adaptador utilizando las opciones
                        JugadoresAdapterPartido adapter = new JugadoresAdapterPartido(options);
                        RecyclerView recyclerView = requireView().findViewById(recyclerViewId);

                        // Establecer el adaptador en el RecyclerView
                        recyclerView.setAdapter(adapter);

                        // Establecer un LayoutManager en el RecyclerView (por ejemplo, LinearLayoutManager)
                        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

                        // Opcional: Si es necesario, puedes añadir opciones adicionales al adaptador
                         adapter.startListening();
                    } else {
                        Toast.makeText(requireContext(), "Error al cargar jugadores del equipo", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
