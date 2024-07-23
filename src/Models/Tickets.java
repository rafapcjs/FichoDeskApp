/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Date;

/**
 *
 * @author rafae
 */
public class Tickets {
 private int id ;
 private Estudiante estudiante;
 
 private Date fecha; 

    public Tickets(Estudiante estudiante, Date fecha) {
        this.estudiante = estudiante;
        this.fecha = fecha;
    }

    public Tickets() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
 
    
    
 
}
