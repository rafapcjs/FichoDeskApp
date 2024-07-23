/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Daos;

import Models.Tickets;
import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rafae
 */
public interface ITicktsDao {
    boolean existeVentaTicketPorEstudianteEnFecha(int idEstudiante, String fecha) throws SQLException; 
    void crearVentaTicker(Tickets tickets) throws SQLException;
    ResultSet obtenerComprasDeHoy(String fechaActual) throws SQLException;
        int obtenerCantidadTotalVentas() throws SQLException;
    int obtenerCantidadVentasPorFacultad(int idFacultad) throws SQLException;

}