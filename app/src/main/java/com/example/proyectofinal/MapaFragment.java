package com.example.proyectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapaFragment extends Fragment {
    private MapView mapView;
    private Marker marker;
    private GeoPoint selectedLocation; // Variable para almacenar la ubicación seleccionada por el usuario

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);

        mapView = rootView.findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        mapView.getController().setZoom(15.0);

        GeoPoint initialLocation = new GeoPoint(41.4505, 2.2081);
        mapView.getController().setCenter(initialLocation);

        // Agrega un marcador para indicar la ubicación inicial
        marker = new Marker(mapView);
        marker.setPosition(initialLocation);
        mapView.getOverlays().add(marker);
        mapView.invalidate(); // Actualiza el mapa para mostrar el marcador

        // Permite al usuario mover el marcador a una nueva ubicación
        mapView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Obtén la ubicación donde el usuario hizo clic largo en el mapa
                selectedLocation = (GeoPoint) mapView.getProjection().fromPixels((int) v.getX(), (int) v.getY());

                // Elimina el marcador actual y crea uno nuevo en la nueva ubicación
                mapView.getOverlays().remove(marker);
                marker = new Marker(mapView);
                marker.setPosition(selectedLocation);
                mapView.getOverlays().add(marker);
                mapView.invalidate();

                // Muestra un mensaje indicando que la nueva ubicación se ha guardado
                Toast.makeText(requireContext(), "Ubicación seleccionada guardada", Toast.LENGTH_SHORT).show();

                return true; // Indica que se ha manejado el evento de clic largo
            }
        });

        return rootView;
    }

    // Método para obtener la ubicación seleccionada
    public GeoPoint getSelectedLocation() {
        return selectedLocation;
    }
}
