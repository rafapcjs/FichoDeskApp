/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Connections.JavaConnectionsSql;
import Models.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

public class AdminDAOImpl implements IAdminDAO {

    @Override
    public void crearAdmin(Admin admin) throws SQLException {
        String sql = "INSERT INTO admin (nombreUsuario, contrasena) VALUES (?, ?)";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getNombreUsuario());
            stmt.setString(2, admin.getContrasena());

            stmt.executeUpdate();
            System.out.println("Admin creado exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Optional<Admin> obtenerAdminPorNombreUsuario(String nombreUsuario) throws SQLException {
        String sql = "SELECT * FROM admin WHERE nombreUsuario = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String contrasena = rs.getString("contrasena");
                    Admin admin = new Admin(id, nombreUsuario, contrasena);
                    return Optional.of(admin);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Admin> verificarCredenciales(String nombreUsuario, String contrasena) {
        String sql = "SELECT * FROM admin WHERE nombreUsuario = ? AND contrasena = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasena);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    Admin admin = new Admin(id, nombreUsuario, contrasena);
                    return Optional.of(admin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
public ResultSet encontrarPorId(int id) throws SQLException {
    String sql = "SELECT id, nombreUsuario, contrasena FROM admin WHERE id = ?";
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        return rs;  // No cerrar el ResultSet aquí
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    } finally {
        // No cerrar los recursos aquí, hacerlo en quien llama a este método
        // Cerrar conn y stmt se debe hacer fuera de este método
    }
}



    @Override
    public boolean eliminarPorId(int id) throws SQLException {
        String sql = "DELETE FROM admin WHERE id = ?";
        Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    }

    @Override
    public void actualizarAdmin(Admin admin) throws SQLException {
        String sql = "UPDATE admin SET nombreUsuario = ?, contrasena = ? WHERE id = ?";
        Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, admin.getNombreUsuario());
        stmt.setString(2, admin.getContrasena());
        stmt.setInt(3, admin.getId());
        stmt.executeUpdate();
    }

    @Override
    public ResultSet obtenerTododoLosAdmins() throws SQLException {
        String sql = "SELECT * FROM admin";
        Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
        PreparedStatement stmt = conn.prepareStatement(sql);
        return stmt.executeQuery();
    }
}