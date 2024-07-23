/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author rafae
 */public class Carrera {
    private int id;
    private String nombreCarrera;
    private Facultad facultad; // Relación con Facultad

       public Carrera(int id, String nombreCarrera) {
        this.id = id;
        this.nombreCarrera = nombreCarrera;
    }
    public Carrera( ) {
        this.id=0;
        this.nombreCarrera = "";
        this.facultad = null;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

 

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }
}
