/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Models.Admin;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author rafae
 */
public interface IAdminDAO {
  void crearAdmin(Admin admin) throws SQLException;
    
    Optional<Admin> obtenerAdminPorNombreUsuario(String nombreUsuario) throws SQLException;
    
    Optional<Admin> verificarCredenciales(String nombreUsuario, String contrasena);
ResultSet encontrarPorId( int  id )throws SQLException;
boolean eliminarPorId(int id)throws SQLException;
void actualizarAdmin (Admin admin) throws SQLException;

    ResultSet obtenerTododoLosAdmins ()throws SQLException;
}
