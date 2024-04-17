package com.example.proyectofinal;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class CrearJugador extends Fragment {

    NavController navController;
    ImageView imageArrowLeft;
    Button crearJugador;

    public CrearJugador() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_jugador, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        imageArrowLeft = view.findViewById(R.id.imageArrowleft);
        imageArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp(); // Regresar al fragmento anterior
            }
        });
        crearJugador = view.findViewById(R.id.btnCrear);
        crearJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 navController.navigate(R.id.listaJugadoresEquipo);
            }
        });

        Spinner dorsalSpinner = view.findViewById(R.id.CrearDorsalSpinner);
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

}
