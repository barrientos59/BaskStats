package com.example.proyectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EstadisticaPartido extends Fragment {

    NavController navController;
    ImageView imageArrowLeft;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_estadistica_partido, container, false);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        imageArrowLeft = rootView.findViewById(R.id.imageArrowleft);
        imageArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp(); // Regresar al fragmento anterior
            }
        });
        return rootView;
    }

}
