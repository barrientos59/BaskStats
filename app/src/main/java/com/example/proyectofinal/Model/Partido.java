package com.example.proyectofinal.Model;

import java.util.List;

public class Partido {
    private String id;
    private String equipoLocalId;
    private String equipoVisitanteId;
    private int puntosLocal;
    private int puntosVisitante;
    private List<Jugador> jugadoresLocal;
    private List<Jugador> jugadoresVisitante;

    // Constructor
    public Partido(String id, String equipoLocalId, String equipoVisitanteId, int puntosLocal, int puntosVisitante,
                   List<Jugador> jugadoresLocal, List<Jugador> jugadoresVisitante) {
        this.id = id;
        this.equipoLocalId = equipoLocalId;
        this.equipoVisitanteId = equipoVisitanteId;
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
        this.jugadoresLocal = jugadoresLocal;
        this.jugadoresVisitante = jugadoresVisitante;
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipoLocalId() {
        return equipoLocalId;
    }

    public void setEquipoLocalId(String equipoLocalId) {
        this.equipoLocalId = equipoLocalId;
    }

    public String getEquipoVisitanteId() {
        return equipoVisitanteId;
    }

    public void setEquipoVisitanteId(String equipoVisitanteId) {
        this.equipoVisitanteId = equipoVisitanteId;
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

    public List<Jugador> getJugadoresLocal() {
        return jugadoresLocal;
    }

    public void setJugadoresLocal(List<Jugador> jugadoresLocal) {
        this.jugadoresLocal = jugadoresLocal;
    }

    public List<Jugador> getJugadoresVisitante() {
        return jugadoresVisitante;
    }

    public void setJugadoresVisitante(List<Jugador> jugadoresVisitante) {
        this.jugadoresVisitante = jugadoresVisitante;
    }
}
