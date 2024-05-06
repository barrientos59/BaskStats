package com.example.proyectofinal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.proyectofinal.Model.Equipo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class CrearEquipo extends Fragment {
    private static final int PICK_IMAGE = 1;

    NavController navController;
    Button btnCrearEquipo;
    EditText etNombreCrearEquipo;
    EditText etUbiCrearEquipo;
    Button btnSeleccionarLogo;
    ImageView imageViewLogo;

    FirebaseFirestore db; // Initialize Firestore instance

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_equipo, container, false);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        btnCrearEquipo = view.findViewById(R.id.btnCrear);
        etNombreCrearEquipo = view.findViewById(R.id.NombreCrearEquipo);
        etUbiCrearEquipo = view.findViewById(R.id.UbiCrearEquipo);
        btnSeleccionarLogo = view.findViewById(R.id.btnSeleccionarLogo);
        imageViewLogo = view.findViewById(R.id.imageViewLogo);

        // Initialize Firestore
        if (db == null) {
            db = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            db.setFirestoreSettings(settings);
        }

        btnCrearEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreEquipo = etNombreCrearEquipo.getText().toString().trim();
                String ubicacionEquipo = etUbiCrearEquipo.getText().toString().trim();

                if (!nombreEquipo.isEmpty() && !ubicacionEquipo.isEmpty()) {
                    // Get current user ID with null check
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String idAutor = currentUser != null ? currentUser.getUid() : null;

                    // Generate a unique team ID
                    String equipoId = db.collection("equipos").document().getId();

                    // Create an Equipo object with the generated ID
                    Equipo equipo = new Equipo(equipoId, nombreEquipo, ubicacionEquipo, idAutor);

                    // Save the team to Firestore
                    db.collection("equipos").document(equipoId).set(equipo)
                            .addOnSuccessListener(documentReference -> {
                                // Team creation successful
                                Toast.makeText(requireContext(), "Equipo creado", Toast.LENGTH_SHORT).show();
                                navController.navigate(R.id.menuCrear);
                            })
                            .addOnFailureListener(e -> {
                                // Team creation failed
                                Toast.makeText(requireContext(), "Error al crear equipo", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSeleccionarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        return view;
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                imageViewLogo.setImageURI(imageUri);
                imageViewLogo.setVisibility(View.VISIBLE);
            }
        }
    }
}
