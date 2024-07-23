/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author rafae
 */
public class Estudiante {
    private int id;
    private String nombre;
    private String identificacion;
    
    private Carrera carrera;
    
    private int semestre;
    private boolean estado;

    // Constructor con todos los campos
    public Estudiante(int id, String nombre, String identificacion, Carrera carrera, int semestre, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.carrera = carrera;
        this.semestre = semestre;
        this.estado = estado;
    }
    public Estudiante(String nombre, String identificacion, Carrera carrera, int semestre, boolean estado) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.carrera = carrera;
        this.semestre = semestre;
        this.estado = estado;
    }

    // Constructor vacío (importante para crear instancias sin inicializar datos)
    public Estudiante() {
        // Este constructor vacío puede ser útil para casos donde necesitas crear instancias sin datos iniciales
    }

    // Getters y setters
    // Implementa los getters y setters para todos los campos según sea necesario
    // Por ejemplo:
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
