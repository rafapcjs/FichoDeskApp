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
public class Vendedor {


     private int id;
    private String nombreUsuario;
    private String contrasena;
    private String cedula;
    
        public Vendedor( String nombreUsuario, String contrasena, String cedula) {
        
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.cedula = cedula;
    }
    
        public Vendedor() {
        this.nombreUsuario = "";
        this.contrasena = "";
        this.cedula = "";
    }


    // Getters y setters
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
 
}
