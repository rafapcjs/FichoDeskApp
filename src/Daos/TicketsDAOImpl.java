/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Daos;

import Connections.JavaConnectionsSql;
import Models.Tickets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author User
 */
public class TicketsDAOImpl implements ITicktsDao{
     @Override
    public int obtenerCantidadVentasPorFacultad(int idFacultad) throws SQLException {
        String query = "SELECT COUNT(*) " +
                       "FROM tickets t " +
                       "INNER JOIN estudiantes e ON t.estudiante_id = e.id " +
                       "INNER JOIN carrera c ON e.id_carrera = c.id " +
                       "WHERE c.id_facultad = ?";
        
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idFacultad);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
     @Override
    public boolean existeVentaTicketPorEstudianteEnFecha(int idEstudiante, String fecha) throws SQLException {
        String query = "SELECT COUNT(*) FROM tickets WHERE estudiante_id = ? AND fecha = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idEstudiante);
            stmt.setString(2, fecha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }


@Override
    public void crearVentaTicker(Tickets tickets) throws SQLException {
        String query = "INSERT INTO tickets (estudiante_id, fecha) VALUES (?, ?)";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, tickets.getEstudiante().getId());
            stmt.setDate(2, new java.sql.Date(tickets.getFecha().getTime())); // Convertir java.util.Date a java.sql.Date
            stmt.executeUpdate();
        }
    }
    
    
@Override
public ResultSet obtenerComprasDeHoy(String fechaActual) throws SQLException {
    String query = "SELECT t.id, t.fecha, e.id AS estudiante_id, e.nombre AS estudiante_nombre, " +
                   "e.identificacion AS estudiante_identificacion, e.id_carrera AS estudiante_id_carrera, " +
                   "e.semestre AS estudiante_semestre, c.nombreCarrera AS nombre_carrera " +
                   "FROM tickets t " +
                   "INNER JOIN estudiantes e ON t.estudiante_id = e.id " +
                   "INNER JOIN carrera c ON e.id_carrera = c.id " +
                   "WHERE t.fecha = ?";
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
        stmt = conn.prepareStatement(query);
        stmt.setString(1, fechaActual);
        rs = stmt.executeQuery();
        return rs;
    } catch (SQLException ex) {
        ex.printStackTrace();
        throw ex;
    }
}

  @Override
    public int obtenerCantidadTotalVentas() throws SQLException {
        String query = "SELECT COUNT(*) FROM tickets";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

 
}