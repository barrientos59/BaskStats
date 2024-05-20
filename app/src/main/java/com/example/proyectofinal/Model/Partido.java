package com.example.proyectofinal.Model;

import java.util.ArrayList;
import java.util.List;

public class Partido {
    private String idPartido;
    private int puntosLocal;
    private int puntosVisitante;
    private List<Jugador> jugadores;

    public Partido() {
        jugadores = new ArrayList<>();
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

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    @Override
    public String toString() {
        return "Partido{" +
                "idPartido='" + idPartido + '\'' +
                ", puntosLocal=" + puntosLocal +
                ", puntosVisitante=" + puntosVisitante +
                ", jugadores=" + jugadores +
                '}';
    }
}
