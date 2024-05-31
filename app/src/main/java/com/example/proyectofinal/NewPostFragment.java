package com.example.proyectofinal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.collection.BuildConfig;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class NewPostFragment extends Fragment {

    Button publishButton;
    EditText postConentEditText;
    NavController navController;
    Uri mediaUri;
    String mediaTipo;

    public AppViewModel appViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        publishButton = view.findViewById(R.id.publishButton);
        postConentEditText = view.findViewById(R.id.postContentEditText);
        navController = Navigation.findNavController(view);

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicar();
            }
        });
        appViewModel = new
                ViewModelProvider(requireActivity()).get(AppViewModel.class);

        view.findViewById(R.id.imagen_galeria).setOnClickListener(v ->
                seleccionarImagen());
        appViewModel.mediaSeleccionado.observe(getViewLifecycleOwner(), media ->
        {
            this.mediaUri = media.uri;
            this.mediaTipo = media.tipo;
            Glide.with(this).load(media.uri).into((ImageView)view.findViewById(R.id.previsualizacion));
        });
    }

    private void publicar() {
        String postContent = postConentEditText.getText().toString();
        if(TextUtils.isEmpty(postContent)){
            postConentEditText.setError("Required");
            return;
        }
        publishButton.setEnabled(false);
        if (mediaTipo == null) {
            guardarEnFirestore(postContent, null);
        }
        else
        {
            pujaIguardarEnFirestore(postContent);
        }
    }
    private void guardarEnFirestore(String postContent, String mediaUrl) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name;
        if (user.getDisplayName()==null){
            name= user.getEmail();
        }else {
            name=user.getDisplayName();
        }
        Post post = new Post(user.getUid(), name,user.getUid(),
                (user.getPhotoUrl() != null ? user.getPhotoUrl().toString() :
                        "R.drawable.user"), postContent, System.currentTimeMillis(), mediaUrl, mediaTipo);

        FirebaseFirestore.getInstance().collection("posts")
                .add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        navController.popBackStack();
                        appViewModel.setMediaSeleccionado( null, null);
                    }
                });
    }
    private void pujaIguardarEnFirestore(final String postText) {
        FirebaseStorage.getInstance().getReference(mediaTipo + "/" +
                        UUID.randomUUID())
                .putFile(mediaUri)
                .continueWithTask(task ->
                        task.getResult().getStorage().getDownloadUrl())
                .addOnSuccessListener(url -> guardarEnFirestore(postText,
                        url.toString()));
    }

    private final ActivityResultLauncher<String> galeria =
            registerForActivityResult(new ActivityResultContracts.GetContent(),
                    uri -> {
                        appViewModel.setMediaSeleccionado(uri, mediaTipo);
                    });


    private void seleccionarImagen() {
        mediaTipo = "image";
        galeria.launch("image/*");
    }
    private void seleccionarVideo() {
        mediaTipo = "video";
        galeria.launch("video/*");
    }
    private void seleccionarAudio() {
        mediaTipo = "audio";
        galeria.launch("audio/*");
    }

}