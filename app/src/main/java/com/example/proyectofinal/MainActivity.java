package com.example.proyectofinal;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar NavController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    @Override
    public void onBackPressed() {
        // Verificar si hay un fragmento en la pila de navegación
        if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() == R.id.menuPrincipal) {
            // Si estamos en el fragmento de inicio de sesión, cerrar la actividad
            super.onBackPressed();
        } else {
            // Si no estamos en el fragmento de inicio de sesión, navegar hacia atrás en la pila de navegación
            navController.navigateUp();
        }
    }

}
