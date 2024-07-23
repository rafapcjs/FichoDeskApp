/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Connections.DriverConnections;
import Connections.JavaConnectionsSql;
import Exceptions.VendedorNoEncontradoException;
import Models.Vendedor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author rafae
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import javax.swing.JOptionPane;

public class IVendedorImplDao implements IVendedorDao {

    @Override
    public boolean existeNombreUsuario(String nombreUsuario) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM vendedor WHERE nombreUsuario = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return false;
    }
    @Override
    public void crearVendedor(Vendedor vendedor) throws SQLException, SQLIntegrityConstraintViolationException {
        String sql = "INSERT INTO vendedor (nombreUsuario, contrasena, cedula) VALUES (?, ?, ?)";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vendedor.getNombreUsuario());
            stmt.setString(2, vendedor.getContrasena());
            stmt.setString(3, vendedor.getCedula());

            stmt.executeUpdate();
            System.out.println("Vendedor creado exitosamente");
        } catch (SQLIntegrityConstraintViolationException e) {
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Optional<Vendedor> buscarVendedorPorCedula(String cedula) throws SQLException, VendedorNoEncontradoException {
        String sql = "SELECT * FROM vendedor WHERE cedula = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombreUsuario = rs.getString("nombreUsuario");
                    String contrasena = rs.getString("contrasena");
                    Vendedor vendedor = new Vendedor(nombreUsuario, contrasena, cedula);
                    return Optional.of(vendedor);
                } else {
                    throw new VendedorNoEncontradoException("Vendedor no encontrado con cédula: " + cedula);
                }
            }
        }
    }

    @Override
    public Optional<Vendedor> verificarUsuario(String nombreUsuario, String contraseña) {
        String sql = "SELECT * FROM vendedor WHERE nombreUsuario = ? AND contrasena = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contraseña);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Vendedor usuario = new Vendedor();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    usuario.setCedula(rs.getString("cedula"));
                    return Optional.of(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void eliminarVendedorPorCedula(String cedula) throws SQLException, VendedorNoEncontradoException {
        if (!existeVendedorPorCedula(cedula)) {
            throw new VendedorNoEncontradoException("Vendedor con cédula " + cedula + " no encontrado en la base de datos");
        }

        String sql = "DELETE FROM vendedor WHERE cedula = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cedula);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Vendedor con cédula " + cedula + " eliminado exitosamente");
            } else {
                System.out.println("No se encontró ningún vendedor con cédula " + cedula);
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private boolean existeVendedorPorCedula(String cedula) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM vendedor WHERE cedula = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return false;
    }

    @Override
    public ResultSet obtenerTodosLosVendedores() throws SQLException {
        String sql = "SELECT nombreUsuario, contrasena, cedula FROM vendedor";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return rs;
    }


   @Override
    public boolean actualizarVendedor(Vendedor vendedor) {
        String sql = "UPDATE vendedor SET nombreUsuario = ?, contrasena = ?, cedula = ? WHERE cedula = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vendedor.getNombreUsuario());
            stmt.setString(2, vendedor.getContrasena());
            stmt.setString(3, vendedor.getCedula());
            stmt.setString(4, vendedor.getCedula());  // La cédula actual para el WHERE

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No se pudo actualizar el vendedor, no se encontró en la base de datos.");
                return false;
            }

            System.out.println("Vendedor actualizado correctamente.");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el vendedor: " + e.getMessage());
            return false;
        }
    }

   @Override
    public boolean existeCedula(String cedula) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM vendedor WHERE cedula = ?";
        try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cedula);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Lanzar la excepción para que sea manejada en el controlador
        }
        return false;
    }

    @Override
public Optional<Vendedor> obtenerVendedorPorNombreUsuario(String nombreUsuario) throws SQLException {
    String sql = "SELECT * FROM vendedor WHERE nombreUsuario = ?";
    try (Connection conn = JavaConnectionsSql.obtenerInstancia().establecerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nombreUsuario);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String contrasena = rs.getString("contrasena");
                String cedula = rs.getString("cedula");
                Vendedor vendedor = new Vendedor(nombreUsuario, contrasena, cedula);
                return Optional.of(vendedor);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Lanzar la excepción para que sea manejada en el controlador
    }
    return Optional.empty();
}

}
