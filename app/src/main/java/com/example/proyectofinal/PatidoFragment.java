package com.example.proyectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Adapter.JugadoresAdapterPartido;
import com.example.proyectofinal.Model.Jugador;
import com.example.proyectofinal.Model.JugadorPartido;
import com.example.proyectofinal.Model.Partido;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PatidoFragment extends Fragment implements JugadoresAdapterPartido.OnItemClickListener {

    private Spinner equipoSpinner, equipoSpinner2;
    private ArrayList<String> nombresEquipos = new ArrayList<>();
    private ArrayList<String> idsEquipos = new ArrayList<>();
    private FirebaseFirestore db;
    private NavController navController;
    private int puntosLocal = 0;
    private int puntosVisitante = 0;
    private TextView textViewPuntosLocal;
    private TextView textViewPuntosVisitante;
    private RecyclerView recyclerViewActual;
    private List<JugadorPartido> jugadoresLocal = new ArrayList<>();
    private List<JugadorPartido> jugadoresVisitante = new ArrayList<>();
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private JugadorPartido jugadorSeleccionado;
    private LinearLayout layoutBotones;
    private Button btnAnotaTl, btnFallaTl, btnAnotaT2, btnFallaT2, btnAnotaT3, btnFallaT3,
            btnAsistencia, btnRebote, btnRobo, btnTap, btnPerdida, btnFalta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_patido, container, false);
        equipoSpinner = rootView.findViewById(R.id.spinner_equipoLocal);
        equipoSpinner2 = rootView.findViewById(R.id.spinner_equipoVisitante);
        db = FirebaseFirestore.getInstance();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        Button btnFinalizar = rootView.findViewById(R.id.btnFinalizar);
        textViewPuntosLocal = rootView.findViewById(R.id.textView_puntosLocal);
        textViewPuntosVisitante = rootView.findViewById(R.id.textView_puntosVisitante);

        // Inicialización de botones y layout
        layoutBotones = rootView.findViewById(R.id.linearLayout_botones_extra);
        btnAnotaTl = rootView.findViewById(R.id.btnAnotaTl);
        btnFallaTl = rootView.findViewById(R.id.btnFallaTl);
        btnAnotaT2 = rootView.findViewById(R.id.btnAnotaT2);
        btnFallaT2 = rootView.findViewById(R.id.btnFallaT2);
        btnAnotaT3 = rootView.findViewById(R.id.btnAnotaT3);
        btnFallaT3 = rootView.findViewById(R.id.btnFallaT3);
        btnAsistencia = rootView.findViewById(R.id.btnAsistencia);
        btnRebote = rootView.findViewById(R.id.btnRebote);
        btnRobo = rootView.findViewById(R.id.btnRobo);
        btnTap = rootView.findViewById(R.id.btnTap);
        btnPerdida = rootView.findViewById(R.id.btnPerdida);
        btnFalta = rootView.findViewById(R.id.btnfalta);

        // Configuración de adaptadores para los Spinners
        adapter1 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombresEquipos);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipoSpinner.setAdapter(adapter1);

        adapter2 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombresEquipos);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipoSpinner2.setAdapter(adapter2);

        // Cargar equipos en los Spinners
        cargarEquiposEnSpinner();

        equipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recyclerViewActual = requireView().findViewById(R.id.recyclerView_jugadoresLocal);
                cargarJugadoresDelEquipo(idsEquipos.get(position), R.id.recyclerView_jugadoresLocal, jugadoresLocal);
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
                recyclerViewActual = requireView().findViewById(R.id.recyclerView_jugadoresVisitante);
                cargarJugadoresDelEquipo(idsEquipos.get(position), R.id.recyclerView_jugadoresVisitante, jugadoresVisitante);
                verificarEquiposDiferentes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se necesita hacer nada aquí
            }
        });

        btnFinalizar.setOnClickListener(v -> {
            guardarPartido();
            navController.navigate(R.id.menuPrincipal); // Navega a MenuFragment
        });

        return rootView;
    }

    private void verificarEquiposDiferentes() {
        String equipoSeleccionado1 = equipoSpinner.getSelectedItem().toString();
        String equipoSeleccionado2 = equipoSpinner2.getSelectedItem().toString();

        if (equipoSeleccionado1.equals(equipoSeleccionado2)) {
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
                        nombresEquipos.clear();
                        idsEquipos.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String idEquipo = document.getId();
                            idsEquipos.add(idEquipo);
                            String nombreEquipo = document.getString("nombre");
                            nombresEquipos.add(nombreEquipo);
                        }
                        adapter1.notifyDataSetChanged();
                        adapter2.notifyDataSetChanged();
                    } else {
                        Toast.makeText(requireContext(), "Error al cargar equipos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cargarJugadoresDelEquipo(String idEquipo, int recyclerViewId, List<JugadorPartido> listaJugadores) {
        db.collection("jugadores")
                .whereEqualTo("equipoId", idEquipo)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listaJugadores.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String idJugador = document.getId();
                            String nombre = document.getString("nombre");
                            String apellido = document.getString("apellido");
                            String dorsal = document.getString("dorsal");
                            String posicion = document.getString("posicion");
                            String equipoId = document.getString("equipoId"); // Obtener el id del equipo
                            JugadorPartido jugador = new JugadorPartido(idJugador, nombre, apellido, dorsal, posicion, equipoId);
                            listaJugadores.add(jugador);
                        }

                        Query query = db.collection("jugadores").whereEqualTo("equipoId", idEquipo);
                        FirestoreRecyclerOptions<Jugador> options = new FirestoreRecyclerOptions.Builder<Jugador>()
                                .setQuery(query, Jugador.class)
                                .build();

                        JugadoresAdapterPartido adapter = new JugadoresAdapterPartido(options, this);
                        RecyclerView recyclerView = requireView().findViewById(recyclerViewId);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                        adapter.startListening();
                    } else {
                        Toast.makeText(requireContext(), "Error al cargar jugadores del equipo", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void guardarPartido() {
        String partidoId = UUID.randomUUID().toString();
        String equipoLocal = equipoSpinner.getSelectedItem().toString();
        String equipoVisitante = equipoSpinner2.getSelectedItem().toString();
        Partido partido = new Partido(partidoId, equipoLocal, equipoVisitante, puntosLocal, puntosVisitante, jugadoresLocal, jugadoresVisitante);

        // Guardar el partido en la colección de partidos
        db.collection("partidos").document(partidoId).set(partido)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Partido guardado con éxito", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al guardar el partido", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onItemClick(Jugador jugador, RecyclerView recyclerView) {
        jugadorSeleccionado = obtenerJugadorPartido(jugador);
        recyclerViewActual = recyclerView;

        // Mostrar botones
        layoutBotones.setVisibility(View.VISIBLE);

        // Configuración de listeners para los botones
        View.OnClickListener ocultarBotonesListener = v -> {
            actualizarEstadisticaJugador(jugadorSeleccionado, v.getTag().toString());
            layoutBotones.setVisibility(View.GONE);
        };

        btnAnotaTl.setTag("tirosAnotadosT1");
        btnAnotaTl.setOnClickListener(ocultarBotonesListener);

        btnFallaTl.setTag("tirosFalladosT1");
        btnFallaTl.setOnClickListener(ocultarBotonesListener);

        btnAnotaT2.setTag("tirosAnotadosT2");
        btnAnotaT2.setOnClickListener(ocultarBotonesListener);

        btnFallaT2.setTag("tirosFalladosT2");
        btnFallaT2.setOnClickListener(ocultarBotonesListener);

        btnAnotaT3.setTag("tirosAnotadosT3");
        btnAnotaT3.setOnClickListener(ocultarBotonesListener);

        btnFallaT3.setTag("tirosFalladosT3");
        btnFallaT3.setOnClickListener(ocultarBotonesListener);

        btnAsistencia.setTag("asistencias");
        btnAsistencia.setOnClickListener(ocultarBotonesListener);

        btnRebote.setTag("rebotes");
        btnRebote.setOnClickListener(ocultarBotonesListener);

        btnRobo.setTag("robos");
        btnRobo.setOnClickListener(ocultarBotonesListener);

        btnTap.setTag("tapones");
        btnTap.setOnClickListener(ocultarBotonesListener);

        btnPerdida.setTag("perdidas");
        btnPerdida.setOnClickListener(ocultarBotonesListener);

        btnFalta.setTag("faltas");
        btnFalta.setOnClickListener(ocultarBotonesListener);
    }

    private JugadorPartido obtenerJugadorPartido(Jugador jugador) {
        for (JugadorPartido jp : jugadoresLocal) {
            if (jp.getIdJugador().equals(jugador.getIdJugador())) {
                return jp;
            }
        }
        for (JugadorPartido jp : jugadoresVisitante) {
            if (jp.getIdJugador().equals(jugador.getIdJugador())) {
                return jp;
            }
        }
        return null;
    }

    private void actualizarEstadisticaJugador(JugadorPartido jugador, String estadistica) {
        String idEquipoLocal = idsEquipos.get(equipoSpinner.getSelectedItemPosition());
        String idEquipoVisitante = idsEquipos.get(equipoSpinner2.getSelectedItemPosition());
        boolean esLocal = jugador.getEquipoId().equals(idEquipoLocal);

        db.collection("jugadores").document(jugador.getIdJugador()).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                JugadorPartido jugadorExistente = documentSnapshot.toObject(JugadorPartido.class);
                if (jugadorExistente != null) {
                    switch (estadistica) {
                        case "tirosAnotadosT1":
                            jugadorExistente.setTirosAnotadosT1(jugadorExistente.getTirosAnotadosT1() + 1);
                            if (esLocal) {
                                puntosLocal += 1;
                            } else {
                                puntosVisitante += 1;
                            }
                            break;
                        case "tirosFalladosT1":
                            jugadorExistente.setTirosFalladosT1(jugadorExistente.getTirosFalladosT1() + 1);
                            break;
                        case "tirosAnotadosT2":
                            jugadorExistente.setTirosAnotadosT2(jugadorExistente.getTirosAnotadosT2() + 1);
                            if (esLocal) {
                                puntosLocal += 2;
                            } else {
                                puntosVisitante += 2;
                            }
                            break;
                        case "tirosFalladosT2":
                            jugadorExistente.setTirosFalladosT2(jugadorExistente.getTirosFalladosT2() + 1);
                            break;
                        case "tirosAnotadosT3":
                            jugadorExistente.setTirosAnotadosT3(jugadorExistente.getTirosAnotadosT3() + 1);
                            if (esLocal) {
                                puntosLocal += 3;
                            } else {
                                puntosVisitante += 3;
                            }
                            break;
                        case "tirosFalladosT3":
                            jugadorExistente.setTirosFalladosT3(jugadorExistente.getTirosFalladosT3() + 1);
                            break;
                        case "asistencias":
                            jugadorExistente.setAsistencias(jugadorExistente.getAsistencias() + 1);
                            break;
                        case "rebotes":
                            jugadorExistente.setRebotes(jugadorExistente.getRebotes() + 1);
                            break;
                        case "robos":
                            jugadorExistente.setRobos(jugadorExistente.getRobos() + 1);
                            break;
                        case "tapones":
                            jugadorExistente.setTapones(jugadorExistente.getTapones() + 1);
                            break;
                        case "perdidas":
                            jugadorExistente.setPerdidas(jugadorExistente.getPerdidas() + 1);
                            break;
                        case "faltas":
                            jugadorExistente.setFaltas(jugadorExistente.getFaltas() + 1);
                            break;
                    }

                    // Calcula los puntos totales del jugador
                    int puntos = jugadorExistente.getTirosAnotadosT1() + (jugadorExistente.getTirosAnotadosT2() * 2) + (jugadorExistente.getTirosAnotadosT3() * 3);
                    jugadorExistente.setPuntos(puntos);

                    // Actualizar Firestore con las nuevas estadísticas del jugador
                    db.collection("jugadores").document(jugadorExistente.getIdJugador())
                            .set(jugadorExistente)
                            .addOnSuccessListener(aVoid -> {
                                // Estadísticas actualizadas correctamente
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Error al actualizar la estadística", Toast.LENGTH_SHORT).show();
                            });

                    textViewPuntosLocal.setText(String.valueOf(puntosLocal));
                    textViewPuntosVisitante.setText(String.valueOf(puntosVisitante));
                }
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Error al obtener estadísticas del jugador", Toast.LENGTH_SHORT).show();
        });
    }



    private void actualizarJugadorEnLista(List<JugadorPartido> listaJugadores, JugadorPartido jugadorActualizado) {
        for (int i = 0; i < listaJugadores.size(); i++) {
            if (listaJugadores.get(i).getIdJugador().equals(jugadorActualizado.getIdJugador())) {
                listaJugadores.set(i, jugadorActualizado);
                break;
            }
        }
    }
}
