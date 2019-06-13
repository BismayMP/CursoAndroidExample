package com.xookware.cursoandroidexample.model;

/**
 * Created by Bismay on 13/6/2019.
 */

public class Asignatura {

    private String nombre;
    private String nombreProfesor;
    private int nota;
    private int annio;
    private int semestre;

    public Asignatura(String nombre, String nombreProfesor, int nota, int annio, int semestre) {
        this.nombre = nombre;
        this.nombreProfesor = nombreProfesor;
        this.nota = nota;
        this.annio = annio;
        this.semestre = semestre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getAnnio() {
        return annio;
    }

    public void setAnnio(int annio) {
        this.annio = annio;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
}
