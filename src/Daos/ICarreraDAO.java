/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Models.Carrera;
import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rafae
 */
public interface ICarreraDAO {
    void  crearCarrera(Carrera carrera) throws SQLException;
    Carrera obtenerCarreraPorId(int id) throws SQLException;
    ResultSet obtenerTodasLasCarreras() throws SQLException;
        boolean existeCarrera(String nombreCarrera, int idFacultad) throws SQLException;
    boolean existeFacultad(int idFacultad) throws SQLException;
    Carrera buscarCarreraPorId(int id) throws SQLException;

    void actualizarCarrera(Carrera carrera) throws SQLException;
    boolean eliminarCarrera(int idCarrera) throws SQLException;
    boolean existenCarrerasPorFacultad(int idFacultad) throws SQLException;
    String obtenerNombreCarrera(int idCarrera) throws SQLException;
    boolean existenEstudiantesPorCarrera(int idCarrera) throws SQLException;
    
}
