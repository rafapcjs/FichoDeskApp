/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;
import Models.Facultad;
import java.sql.ResultSet;
import java.sql.SQLException;
 import java.util.List;
/**
 *""/
 * @author rafae
 */
public interface IFacultadDAO {
       void crearFacultad(Facultad facultad) throws SQLException;
    ResultSet obtenerTodasLasFacultades() throws SQLException;
    boolean actualizarFacultad(Facultad facultad) throws SQLException;
    boolean eliminarFacultad(int id) throws SQLException; // Método para eliminar facultad
        Facultad obtenerFacultadPorId(int id) throws SQLException;
    ResultSet obtenerTodasLasFacultadesConId() throws SQLException;
        int obtenerIdFacultadPorNombre(String nombreFacultad) throws SQLException;


}
