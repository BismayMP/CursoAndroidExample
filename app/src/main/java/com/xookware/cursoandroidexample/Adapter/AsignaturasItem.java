package com.xookware.cursoandroidexample.Adapter;

import com.xookware.cursoandroidexample.model.Asignatura;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Bismay on 13/6/2019.
 */

public class AsignaturasItem {
    private Asignatura object;
    private String nombre;
    private String profesor;

    public AsignaturasItem() {
    }

    public Asignatura getObject() {
        return object;
    }

    public void setObject(Asignatura object) {
        this.object = object;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public static List<AsignaturasItem> getObjectList(LinkedList<Asignatura> list)
    {
        List<AsignaturasItem> dataList = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            AsignaturasItem card = new AsignaturasItem();
            //agregar las validaciones para tipos de carpetas
            card.setNombre(list.get(i).getNombre());
            card.setProfesor(list.get(i).getNombreProfesor());
            card.setObject(list.get(i));
            dataList.add(card);
        }
        return dataList;
    }
}
