/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author rafae
 */import java.util.ArrayList;
import java.util.List;

public class Facultad {
    private int id;
    private String nombre;
    private List<Carrera> carreras; // Relación con Carrera

    public Facultad(int id, String nombre, List<Carrera> carreras) {
        this.id = id;
        this.nombre = nombre;
        this.carreras = carreras;
    }

    
    public Facultad() {
        this.id = 0;
        this.nombre = "";
        this.carreras = new ArrayList<>(); // Inicializar la lista de carreras
    }

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

    public List<Carrera> getCarreras() {
        return carreras;
    }

    public void agregarCarrera(Carrera carrera) {
        this.carreras.add(carrera);
        carrera.setFacultad(this); // Establecer la relación bidireccional
    }

    public void eliminarCarrera(Carrera carrera) {
        this.carreras.remove(carrera);
        carrera.setFacultad(null); // Romper la relación bidireccional
    }
}
