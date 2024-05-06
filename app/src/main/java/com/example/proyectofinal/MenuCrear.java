package com.example.proyectofinal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuCrear extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_menu_crear, container, false);


        Button btnCrearEquipo = view.findViewById(R.id.btnCrearEquipo);
        btnCrearEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.crearEquipo);
            }
        });
        Button btnCrearjugador = view.findViewById(R.id.btnCrearjugador);
        btnCrearjugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.crearJugador);
            }
        });






        return view;
    }
}