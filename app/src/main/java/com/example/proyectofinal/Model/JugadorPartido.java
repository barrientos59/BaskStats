package com.example.proyectofinal.Model;

public class JugadorPartido {
    private String idJugador;
    private String nombre;
    private String apellido;
    private String dorsal;
    private String posicion;
    private String equipoId;

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
    private int puntos;

    public JugadorPartido() {
    }

    public JugadorPartido(String idJugador, String nombre, String apellido, String dorsal, String posicion, String equipoId) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dorsal = dorsal;
        this.posicion = posicion;
        this.equipoId = equipoId;
    }
    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
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

    @Override
    public String toString() {
        return "JugadorPartido{" +
                "idJugador='" + idJugador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dorsal='" + dorsal + '\'' +
                ", posicion='" + posicion + '\'' +
                ", equipoId='" + equipoId + '\'' +
                '}';
    }
}
