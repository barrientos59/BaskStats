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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class CrearEquipo extends Fragment {
    // Constantes
    private static final int PICK_IMAGE = 1;
    private static final String STORAGE_PATH = "logoequip/";

    // Variables
    private Uri imageUri;
    private StorageReference storageReference;

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

        // Inicializar Firebase Storage
        storageReference = FirebaseStorage.getInstance().getReference();

        btnCrearEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreEquipo = etNombreCrearEquipo.getText().toString().trim();
                String ubicacionEquipo = etUbiCrearEquipo.getText().toString().trim();

                if (!nombreEquipo.isEmpty() && !ubicacionEquipo.isEmpty()) {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String idAutor = currentUser != null ? currentUser.getUid() : null;
                    String equipoId = db.collection("equipos").document().getId();
                    Equipo equipo = new Equipo(equipoId, nombreEquipo, ubicacionEquipo, idAutor);

                    // Verificar si hay una imagen seleccionada
                    if (imageUri != null) {
                        // Subir imagen a Firebase Storage y luego guardar el equipo en Firestore
                        uploadImageToStorage(equipo);
                    } else {
                        // Si no hay una imagen seleccionada, guarda el equipo en Firestore directamente
                        saveEquipoToFirestore(equipo);
                    }
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
            imageUri = data.getData();
            if (imageUri != null) {
                imageViewLogo.setImageURI(imageUri);
                imageViewLogo.setVisibility(View.VISIBLE);
            }
        }
    }

    private void uploadImageToStorage(final Equipo equipo) {
        if (imageUri != null) {
            StorageReference filePath = storageReference.child(STORAGE_PATH + UUID.randomUUID().toString());
            filePath.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Imagen cargada exitosamente
                        filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                            // URL de descarga obtenida
                            equipo.setLogo(uri.toString()); // Guardar la URL de la imagen en el objeto Equipo
                            saveEquipoToFirestore(equipo); // Guardar el equipo en Firestore
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Error al cargar la imagen
                        Toast.makeText(requireContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void saveEquipoToFirestore(Equipo equipo) {
        // Guardar el equipo en Firestore
        db.collection("equipos").document(equipo.getId()).set(equipo)
                .addOnSuccessListener(documentReference -> {
                    // Equipo guardado exitosamente
                    Toast.makeText(requireContext(), "Equipo creado", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.menuCrear);
                })
                .addOnFailureListener(e -> {
                    // Error al guardar el equipo
                    Toast.makeText(requireContext(), "Error al crear equipo", Toast.LENGTH_SHORT).show();
                });
    }
}
