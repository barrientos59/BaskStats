package com.example.proyectofinal.Model;



import java.util.List;

public class Partido {
    private String idPartido;
    private String equipoLocal;
    private String equipoVisitante;
    private int puntosLocal;
    private int puntosVisitante;
    private List<JugadorPartido> jugadoresLocal;
    private List<JugadorPartido> jugadoresVisitante;
    private String idAutor;

    public Partido() {
        // Constructor vacío necesario para Firestore
    }

    public Partido(String idPartido, String equipoLocal, String equipoVisitante, int puntosLocal, int puntosVisitante, List<JugadorPartido> jugadoresLocal, List<JugadorPartido> jugadoresVisitante, String idAutor) {
        this.idPartido = idPartido;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
        this.jugadoresLocal = jugadoresLocal;
        this.jugadoresVisitante = jugadoresVisitante;
        this.idAutor = idAutor; // Inicializar el nuevo campo
    }

    // Getters y setters
    public String getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    public String getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(String equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(String equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public int getPuntosLocal() {
        return puntosLocal;
    }

    public void setPuntosLocal(int puntosLocal) {
        this.puntosLocal = puntosLocal;
    }

    public int getPuntosVisitante() {
        return puntosVisitante;
    }

    public void setPuntosVisitante(int puntosVisitante) {
        this.puntosVisitante = puntosVisitante;
    }

    public List<JugadorPartido> getJugadoresLocal() {
        return jugadoresLocal;
    }

    public void setJugadoresLocal(List<JugadorPartido> jugadoresLocal) {
        this.jugadoresLocal = jugadoresLocal;
    }

    public List<JugadorPartido> getJugadoresVisitante() {
        return jugadoresVisitante;
    }

    public void setJugadoresVisitante(List<JugadorPartido> jugadoresVisitante) {
        this.jugadoresVisitante = jugadoresVisitante;
    }

    public String getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(String idAutor) {
        this.idAutor = idAutor;
    }
}
