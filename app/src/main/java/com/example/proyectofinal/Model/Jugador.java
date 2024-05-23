package com.example.proyectofinal.Model;

public class Jugador {
    private String idJugador;
    private String nombre;
    private String apellido;
    private String dorsal;
    private String posicion;
    private String equipoId;

    private int puntos;
    private int tirosAnotadosT1;
    private int tirosFalladosT1;
    private int tirosAnotadosT2;
    private int tirosFalladosT2;
    private int tirosAnotadosT3;
    private int tirosFalladosT3;
    private int asistencias;
    private int rebotes;
    private int robos;
    private int tapones;
    private int perdidas;
    private int faltas;

    public Jugador() {
    }

    public Jugador(String idJugador, String nombre, String apellido, String dorsal, String posicion, String equipoId) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dorsal = dorsal;
        this.posicion = posicion;
        this.equipoId = equipoId;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDorsal() {
        return dorsal;
    }

    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(String equipoId) {
        this.equipoId = equipoId;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getTirosAnotadosT1() {
        return tirosAnotadosT1;
    }

    public void setTirosAnotadosT1(int tirosAnotadosT1) {
        this.tirosAnotadosT1 = tirosAnotadosT1;
    }

    public int getTirosFalladosT1() {
        return tirosFalladosT1;
    }

    public void setTirosFalladosT1(int tirosFalladosT1) {
        this.tirosFalladosT1 = tirosFalladosT1;
    }

    public int getTirosAnotadosT2() {
        return tirosAnotadosT2;
    }

    public void setTirosAnotadosT2(int tirosAnotadosT2) {
        this.tirosAnotadosT2 = tirosAnotadosT2;
    }

    public int getTirosFalladosT2() {
        return tirosFalladosT2;
    }

    public void setTirosFalladosT2(int tirosFalladosT2) {
        this.tirosFalladosT2 = tirosFalladosT2;
    }

    public int getTirosAnotadosT3() {
        return tirosAnotadosT3;
    }

    public void setTirosAnotadosT3(int tirosAnotadosT3) {
        this.tirosAnotadosT3 = tirosAnotadosT3;
    }

    public int getTirosFalladosT3() {
        return tirosFalladosT3;
    }

    public void setTirosFalladosT3(int tirosFalladosT3) {
        this.tirosFalladosT3 = tirosFalladosT3;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getRebotes() {
        return rebotes;
    }

    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

    public int getRobos() {
        return robos;
    }

    public void setRobos(int robos) {
        this.robos = robos;
    }

    public int getTapones() {
        return tapones;
    }

    public void setTapones(int tapones) {
        this.tapones = tapones;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    // Métodos para incrementar estadísticas

    public void incrementarTirosAnotadosT1() {
        this.tirosAnotadosT1++;
    }

    public void incrementarTirosFalladosT1() {
        this.tirosFalladosT1++;
    }

    public void incrementarTirosAnotadosT2() {
        this.tirosAnotadosT2++;
    }

    public void incrementarTirosFalladosT2() {
        this.tirosFalladosT2++;
    }

    public void incrementarTirosAnotadosT3() {
        this.tirosAnotadosT3++;
    }

    public void incrementarTirosFalladosT3() {
        this.tirosFalladosT3++;
    }

    public void incrementarAsistencias() {
        this.asistencias++;
    }

    public void incrementarRebotes() {
        this.rebotes++;
    }

    public void incrementarRobos() {
        this.robos++;
    }

    public void incrementarTapones() {
        this.tapones++;
    }

    public void incrementarPerdidas() {
        this.perdidas++;
    }

    public void incrementarFaltas() {
        this.faltas++;
    }

    // Método para actualizar estadísticas
    public void actualizarEstadisticas(Jugador jugador) {
        this.puntos += jugador.getPuntos();
        this.tirosAnotadosT1 += jugador.getTirosAnotadosT1();
        this.tirosFalladosT1 += jugador.getTirosFalladosT1();
        this.tirosAnotadosT2 += jugador.getTirosAnotadosT2();
        this.tirosFalladosT2 += jugador.getTirosFalladosT2();
        this.tirosAnotadosT3 += jugador.getTirosAnotadosT3();
        this.tirosFalladosT3 += jugador.getTirosFalladosT3();
        this.asistencias += jugador.getAsistencias();
        this.rebotes += jugador.getRebotes();
        this.robos += jugador.getRobos();
        this.tapones += jugador.getTapones();
        this.perdidas += jugador.getPerdidas();
        this.faltas += jugador.getFaltas();
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "idJugador='" + idJugador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dorsal='" + dorsal + '\'' +
                ", posicion='" + posicion + '\'' +
                ", equipoId='" + equipoId + '\'' +
                '}';
    }
}
