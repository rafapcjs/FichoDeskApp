/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Models.Admin;
import Models.BienestarUniversitario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author rafae
 */
public interface BienestarUniversitarioDao {
    // operaciones

    void crearBienestarUniversitario(BienestarUniversitario bienestarUniversitario) throws SQLException;

    Optional<BienestarUniversitario> verificarCredenciales(String nombreUsuario, String contrasena);

   ResultSet obtenerFacultadesYTicketsResultSet ()throws SQLException;
   ResultSet obtenerCarrerasConTickets() throws SQLException;
}
