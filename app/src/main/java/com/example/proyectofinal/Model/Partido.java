package com.example.proyectofinal.Model;

import java.util.ArrayList;
import java.util.List;

public class Partido {
    private String idPartido;
    private int puntosLocal;
    private int puntosVisitante;
    private List<JugadorPartido> jugadoresLocal;
    private List<JugadorPartido> jugadoresVisitante;

    public Partido() {
        jugadoresLocal = new ArrayList<>();
        jugadoresVisitante = new ArrayList<>();
    }

    public Partido(String idPartido, int puntosLocal, int puntosVisitante, List<JugadorPartido> jugadoresLocal, List<JugadorPartido> jugadoresVisitante) {
        this.idPartido = idPartido;
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
        this.jugadoresLocal = jugadoresLocal;
        this.jugadoresVisitante = jugadoresVisitante;
    }

    public String getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
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

    @Override
    public String toString() {
        return "Partido{" +
                "idPartido='" + idPartido + '\'' +
                ", puntosLocal=" + puntosLocal +
                ", puntosVisitante=" + puntosVisitante +
                ", jugadoresLocal=" + jugadoresLocal +
                ", jugadoresVisitante=" + jugadoresVisitante +
                '}';
    }
}
