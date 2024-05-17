package com.example.proyectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrearJugador extends Fragment {

    NavController navController;
    Button crearJugador;
    Spinner equipoSpinner, dorsalSpinner;
    List<String> nombresEquipos = new ArrayList<>();
    EditText CreraJugadorNombre, CrearApellido, CrearPosicion;
    FirebaseFirestore db;

    public CrearJugador() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance(); // Inicializa la instancia de Firestore
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_jugador, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        CreraJugadorNombre = view.findViewById(R.id.CreraJugadorNombre);
        CrearApellido = view.findViewById(R.id.CrearApellido);
        CrearPosicion = view.findViewById(R.id.CrearPosicion);

        crearJugador = view.findViewById(R.id.btnCrear);
        crearJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarJugador();
            }
        });

        equipoSpinner = view.findViewById(R.id.AsignarEquipoSpinner);
        dorsalSpinner = view.findViewById(R.id.CrearDorsalSpinner);

        cargarEquiposEnSpinner();

        // Crear un arreglo de strings para las opciones del spinner (00 a 99)
        String[] opciones = new String[100];
        for (int i = 0; i < 100; i++) {
            opciones[i] = String.format("%02d", i);
        }
        // Crear un adaptador para el spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Establecer el adaptador en el spinner
        dorsalSpinner.setAdapter(adapter);

        // Establecer la selección predefinida en "00"
        dorsalSpinner.setSelection(0);

        return view;
    }

    private void cargarEquiposEnSpinner() {
        // Obtener el ID del usuario actual
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = currentUser.getUid();

        // Realizar la consulta para obtener solo los equipos del usuario actual
        db.collection("equipos")
                .whereEqualTo("idAutor", currentUserId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Obtener el nombre del equipo y agregarlo a la lista de nombres de equipos
                            String nombreEquipo = document.getString("nombre");
                            nombresEquipos.add(nombreEquipo);
                        }
                        // Crear un adaptador para el spinner con la lista de nombres de equipos
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombresEquipos);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Establecer el adaptador en el spinner
                        equipoSpinner.setAdapter(adapter);
                    } else {
                        // Manejar errores de obtención de datos
                    }
                });
    }


    private void guardarJugador() {
        String nombre = CreraJugadorNombre.getText().toString().trim();
        String apellido = CrearApellido.getText().toString().trim();
        String posicion = CrearPosicion.getText().toString().trim();

        if (!nombre.isEmpty() && !apellido.isEmpty() && !posicion.isEmpty()) {
            // Obtener el ID del equipo seleccionado
            obtenerEquipoIdSeleccionado(nombre, apellido, posicion);
        } else {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerEquipoIdSeleccionado(String nombre, String apellido, String posicion) {
        // Obtiene el nombre del equipo seleccionado en el Spinner
        String nombreEquipoSeleccionado = equipoSpinner.getSelectedItem().toString();

        // Busca el ID del equipo seleccionado en base al nombre en Firebase Firestore
        db.collection("equipos")
                .whereEqualTo("nombre", nombreEquipoSeleccionado)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Obtiene el ID del equipo seleccionado
                            String equipoId = document.getId();
                            guardarJugadorFirestore(nombre, apellido, posicion, equipoId);
                            return; // Termina el bucle después de encontrar un documento
                        }
                        // Si no se encontró ningún equipo con ese nombre
                        Toast.makeText(requireContext(), "No se ha seleccionado un equipo válido", Toast.LENGTH_SHORT).show();
                    } else {
                        // Manejar errores de obtención de datos
                    }
                });
    }

    private void guardarJugadorFirestore(String nombre, String apellido, String posicion, String equipoId) {
        // Obtener el dorsal seleccionado
        String dorsal = dorsalSpinner.getSelectedItem().toString();

        // Crear un nuevo jugador
        Map<String, Object> jugadorData = new HashMap<>();
        jugadorData.put("nombre", nombre);
        jugadorData.put("apellido", apellido);
        jugadorData.put("posicion", posicion);
        jugadorData.put("equipoId", equipoId);
        jugadorData.put("dorsal", dorsal);

        // Añadir los nuevos campos
        jugadorData.put("puntos", 0);
        jugadorData.put("asistencias", 0);
        jugadorData.put("rebotes", 0);
        jugadorData.put("tapones", 0);
        jugadorData.put("perdidas", 0);
        jugadorData.put("faltas", 0);
        jugadorData.put("robos", 0); // Agregar el campo "robos" con valor inicial 0

        // Guardar los datos del jugador en Firestore
        db.collection("jugadores")
                .add(jugadorData)
                .addOnSuccessListener(documentReference -> {
                    // Obtener el ID del nuevo documento creado
                    String jugadorId = documentReference.getId();
                    // Añadir el ID del jugador al mapa de datos del jugador
                    jugadorData.put("IdJugador", jugadorId);
                    // Actualizar el documento del jugador con el ID del jugador
                    documentReference.set(jugadorData)
                            .addOnSuccessListener(aVoid -> {
                                // Éxito al actualizar el documento con el ID del jugador
                                Toast.makeText(requireContext(), "Jugador creado exitosamente", Toast.LENGTH_SHORT).show();
                                navController.navigateUp(); // Regresar al fragmento anterior
                            })
                            .addOnFailureListener(e -> {
                                // Error al actualizar el documento con el ID del jugador
                                Toast.makeText(requireContext(), "Error al guardar el ID del jugador: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    // Error al crear el documento del jugador
                    Toast.makeText(requireContext(), "Error al crear jugador: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }



}
