/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Connections.DriverConnections;
import Controllers.Controllers;
import Daos.AdminDAOImpl;
import Daos.BienestarUniversitarioDaoImpl;
import Daos.ICarreraDAOImpl;
import Daos.IFacultadDAOImpl;
import Daos.IStudentDaoImpl;
import Daos.IVendedorImplDao;
import Daos.TicketsDAOImpl;
import Models.Admin;
import Models.Carrera;
import Models.Facultad;
import Models.Vendedor;
import Views.AdministradorView;
import Views.BienestarU;
import Views.FacultadView;
import Views.Login;
import Views.PanelAdmin;
import Views.VistaVendedores;
import Views.Vista_Estudiante;
import Views.Vista_programas;
import Views.Vista_ventas;
import java.sql.SQLException;
import javax.swing.JComboBox;

public class main {

    public static void main(String[] args) throws SQLException {
        Vendedor vendedor = new Vendedor();
        ICarreraDAOImpl carreraDAOImpl = new ICarreraDAOImpl();
        IVendedorImplDao iVendedorImplDao = new IVendedorImplDao();
        Facultad facultad = new Facultad();
        IFacultadDAOImpl facultadDAO = new IFacultadDAOImpl();
        FacultadView facultadView = new FacultadView();
        JComboBox<String> comboBoxFacultades = new JComboBox<>(); // Crear el JComboBox de facultades
        Vista_programas vista_programas = new Vista_programas();
        IStudentDaoImpl iStudentDaoImpl = new IStudentDaoImpl();
        AdministradorView administradorView = new AdministradorView();
        Vista_Estudiante vista_Estudiante = new Vista_Estudiante();
        AdminDAOImpl adminDaoImpl = new AdminDAOImpl();
        Vista_ventas vista_ventas = new Vista_ventas();
        VistaVendedores vistaVendedores = new VistaVendedores();
        PanelAdmin panelAdmin = new PanelAdmin();
        Login login = new Login();
        TicketsDAOImpl ticketsDAOImpl = new TicketsDAOImpl();
        BienestarUniversitarioDaoImpl bienestarUniversitarioDaoImpl = new BienestarUniversitarioDaoImpl();
        BienestarU bienestarU= new  BienestarU();
        // Constructor del controlador con parámetros consistentes
        Controllers controllers = new Controllers(
                vendedor,
                iVendedorImplDao,
                facultad,
                facultadView,
                facultadDAO,
                comboBoxFacultades,
                vista_programas,
                carreraDAOImpl,
                iStudentDaoImpl,
                vista_Estudiante,
                administradorView,
                adminDaoImpl,
                login, vista_ventas, panelAdmin, vistaVendedores, ticketsDAOImpl, bienestarUniversitarioDaoImpl
        
        ,bienestarU
        );

        login.setVisible(true);
        controllers.procesarComprasDeHoy();
// Otros pasos necesarios para configurar la vista y mostrarla
    }
}
