package com.example.proyectofinal;

import static com.example.proyectofinal.R.id.btnmenucrear;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class MenuPrincipal extends Fragment {
    private AppBarConfiguration mAppBarConfiguration;

    public MenuPrincipal() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_principal, container, false);
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = view.findViewById(R.id.nav_view);

        // Define los ID de los fragments de destino para los que se debe mostrar el botón de navegación hacia atrás en el AppBar
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.profileFragment, R.id.signOutFragment)
                .setOpenableLayout(drawerLayout)
                .build();

        // Configura el NavController con el AppBarConfiguration
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(requireActivity(), navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ImageButton btnOpenDrawer = view.findViewById(R.id.btnOpenDrawer);
        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        Button VerEstadisticas = view.findViewById(R.id.btnVerEstadisticas);
        VerEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.verEstadisticaFragment);
            }
        });
        Button btnMenucrear = view.findViewById(R.id.btnmenucrear);
        btnMenucrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.menuCrear);
            }
        });
        Button partido = view.findViewById(R.id.btnCrearPartido);
        partido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Navigation.findNavController(view).navigate(R.id.patidoFragment);
            }
        });
        Button VerEquipos = view.findViewById(R.id.btnVerEquipos);
        VerEquipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.verEquipos);
            }
        });


        Button noticias = view.findViewById(R.id.btnNoticias);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.noticias);
            }
        });

        return view;
    }
}