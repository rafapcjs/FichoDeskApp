/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Exceptions.VendedorNoEncontradoException;
import Models.Vendedor;
import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

/**
 *
 * @author rafae
 */public interface IVendedorDao {
    void crearVendedor(Vendedor vendedor) throws SQLException, SQLIntegrityConstraintViolationException;

    void eliminarVendedorPorCedula(String cedula) throws SQLException, VendedorNoEncontradoException;
    boolean existeCedula(String cedula) throws SQLException;
    boolean existeNombreUsuario(String nombreUsuario) throws SQLException;

    ResultSet obtenerTodosLosVendedores() throws SQLException;
    Optional<Vendedor> obtenerVendedorPorNombreUsuario(String nombreUsuario) throws SQLException;


    Optional<Vendedor> buscarVendedorPorCedula(String cedula) throws SQLException, VendedorNoEncontradoException;

    Optional<Vendedor> verificarUsuario(String nombreUsuario, String contraseña);

boolean actualizarVendedor(Vendedor vendedor);
}
