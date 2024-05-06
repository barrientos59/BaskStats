package com.example.proyectofinal.Model;

public class Equipo {
    private String id;
    private String nombre;
    private String ubicacion;
    private String logo;
    private String idAutor; // Nuevo campo para el ID del usuario


    public Equipo(String id, String nombre, String ubicacion, String idAutor) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.idAutor = idAutor;
    }
    public Equipo() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(String idAutor) {
        this.idAutor = idAutor;
    }
}
