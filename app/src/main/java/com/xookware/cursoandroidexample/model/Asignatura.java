package com.xookware.cursoandroidexample.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * Created by Bismay on 13/6/2019.
 */

public class Asignatura implements Parcelable {

    private int id;
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

    public Asignatura(@Nullable int id, String nombre, String nombreProfesor, int nota, int annio, int semestre) {
        this.id = id;
        this.nombre = nombre;
        this.nombreProfesor = nombreProfesor;
        this.nota = nota;
        this.annio = annio;
        this.semestre = semestre;
    }

    public Asignatura() {

    }

    protected Asignatura(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        nombreProfesor = in.readString();
        nota = in.readInt();
        annio = in.readInt();
        semestre = in.readInt();
    }

    public static final Creator<Asignatura> CREATOR = new Creator<Asignatura>() {
        @Override
        public Asignatura createFromParcel(Parcel in) {
            return new Asignatura(in);
        }

        @Override
        public Asignatura[] newArray(int size) {
            return new Asignatura[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "{id: " + String.valueOf(id) + " , nombre: " + nombre + " , nombre_profesor: " + nombreProfesor + " , nota: " + String.valueOf(nota) + " , año: " + String.valueOf(annio) + " , semestre:" + String.valueOf(semestre) +"}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(nombreProfesor);
        dest.writeInt(nota);
        dest.writeInt(annio);
        dest.writeInt(semestre);
    }
}
