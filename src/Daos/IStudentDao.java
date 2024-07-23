/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Daos;

import Models.Carrera;
import Models.Estudiante;
import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author rafae
 */
public interface IStudentDao {
   void  crearEstudiante(Estudiante estudiante) throws SQLException;
    ResultSet obtenerTodasEstudiante() throws SQLException;
     Estudiante buscarEstudiantePorIdentificacion(String identificacion) throws SQLException;
         ResultSet buscarEstudiantePorCedula(String cedula) throws SQLException;
    boolean esEstudianteEliminado(int id) throws SQLException;

    ResultSet obtenerTodasLasCarreras() throws SQLException;
    ResultSet obtenerTodasEstudianteConCarrera() throws SQLException;
     boolean eliminarEstudiantePorCedula(String cedula) throws SQLException;

    boolean existeIdentificacion(String identificacion) throws SQLException;

    boolean actualizarEstudiante(Estudiante estudiante) throws SQLException;
    
    boolean eliminarEstudiante(int id) throws SQLException;    
    int obtenerIdPorCedula(String cedula) throws SQLException;
    
}
