package Controllers;

import Connections.JavaConnectionsSql;
import Daos.AdminDAOImpl;
import Daos.BienestarUniversitarioDaoImpl;
import Daos.IAdminDAO;
import Daos.ICarreraDAOImpl;
import Daos.IFacultadDAOImpl;
import Daos.IStudentDaoImpl;
import Daos.IVendedorImplDao;
import Daos.TicketsDAOImpl;
import Exceptions.VendedorNoEncontradoException;
import Models.Admin;
import Models.BienestarUniversitario;
import Models.Carrera;
import Models.Estudiante;
import Models.Facultad;
import Models.Tickets;
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
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Controllers implements ActionListener {

    private JComboBox<String> comboBoxFacultades; // JComboBox para mostrar las facultades
    private ICarreraDAOImpl carreraDAOImpl;
    private Vendedor vendedor;
    private VistaVendedores vistaVendedor;
    private Vista_ventas vista_ventas;
    private DefaultTableModel tablaCarrerasModel;
    private PanelAdmin panelAdmin;
    private TicketsDAOImpl ticketsDAOImpl;
    private DefaultTableModel dtmVendedor; // Modelo de la tabla
    private BienestarU bienestarU;
    private IVendedorImplDao vendedorDAO; // Referencia al DAO
    private Facultad facultad;
    private FacultadView facultadView;

    private DefaultTableModel dtmFacultad; // Modelo de la tabla
    private Vista_programas vista_programas;
    private IFacultadDAOImpl facultadDAO; // Referencia al DAO
    private IStudentDaoImpl iStudentDaoImpl;
    private Vista_Estudiante vista_Estudiante;
    private AdminDAOImpl adminDaoImpl;
    private AdministradorView administradorView;
    private Login login;

    private BienestarUniversitarioDaoImpl bienestarUniversitarioDaoImpl;

    public Controllers(Vendedor vendedor, IVendedorImplDao vendedorDAO, Facultad facultad, FacultadView facultadView, IFacultadDAOImpl facultadDAO, JComboBox<String> txtFacultadComboBox, Vista_programas vista_programas,
            ICarreraDAOImpl carreraDAOImpl, IStudentDaoImpl iStudentDaoImpl, Vista_Estudiante vista_Estudiante, AdministradorView administradorView,
            AdminDAOImpl adminDaoImpl, Login login, Vista_ventas vista_ventas, PanelAdmin panelAdmin, VistaVendedores vistaVendedor, TicketsDAOImpl ticketsDAOImpl, BienestarUniversitarioDaoImpl bienestarUniversitarioDaoImpl, BienestarU bienestarU) {
        // Inicialización de variables
        this.bienestarUniversitarioDaoImpl = bienestarUniversitarioDaoImpl;
        this.vistaVendedor = vistaVendedor;
        this.vista_ventas = vista_ventas;
        this.login = login;
        this.bienestarU = bienestarU;
        this.adminDaoImpl = adminDaoImpl;
        this.vista_Estudiante = vista_Estudiante;
        this.iStudentDaoImpl = iStudentDaoImpl;
        this.facultad = facultad;
        this.carreraDAOImpl = carreraDAOImpl;
        this.facultadView = facultadView;
        this.facultadDAO = facultadDAO;
        this.vendedor = vendedor;
        this.administradorView = administradorView;
        this.vistaVendedor.button_registrar_ven.addActionListener(this);
        this.vistaVendedor.Button_eliminar_vendedor.addActionListener(this);
        this.vendedorDAO = vendedorDAO;
        this.vista_programas = vista_programas;
        this.panelAdmin = panelAdmin;
        this.ticketsDAOImpl = ticketsDAOImpl;

        // Configuración y carga de datos
        cargarYMostrarFacultades();
        mostrarCantidadTotalVentas();
        configurarTabla();
        mostrarEstudiantes();
        inicializarTablaCarreras();
        mostrarTodosLosAdministradores();
        cargarYMostrarCarreras();
        cargarDatosVendedores();
        configurarTablaFacultad();
        cargarYMostrarFacultadess();
       procesarTicketsPorFacultad();
        procesarComprasDeHoy();
        configurarTablaVenta();
procesarCarreras();
        // Asignar listeners a los componentes
         this.vistaVendedor.btnCedulaBuscar.addActionListener(this);
        this.facultadView.retornar.addActionListener(this);
        
        this.administradorView.btntEliminar.addActionListener(this);
        this.vistaVendedor.btnRetornar.addActionListener(this);
        this.vista_programas.regresar.addActionListener(this);
        this.vista_programas.registrarProgama.addActionListener(this);
        this.comboBoxFacultades = txtFacultadComboBox;
        this.vista_programas.txtActualizar.addActionListener(this);
        this.vista_Estudiante.btnRetornar.addActionListener(this);
        this.administradorView.btnBuscarId.addActionListener(this);
        this.administradorView.btnRegistrar.addActionListener(this);
        this.vistaVendedor.btnCedulaBuscar.addActionListener(this);
        this.facultadView.btnActualizar.addActionListener(this);
        this.facultadView.txtxAgregarFacultad.addActionListener(this);
        this.vista_programas.btnBuscarCodigo.addActionListener(this);
        this.vistaVendedor.button_registrar_ven.addActionListener(this);
        this.vistaVendedor.Button_eliminar_vendedor.addActionListener(this);
        this.facultadView.txtBuscarPorCodigo.addActionListener(this);
        this.facultadView.BtnEliminar.addActionListener(this);
        this.vista_programas.btnEliminars.addActionListener(this);
        this.vistaVendedor.buttonAct.addActionListener(this);
        this.vista_Estudiante.btnEliminar.addActionListener(this);
        this.login.Button_login.addActionListener(this);
        this.vista_Estudiante.btnBuscarCedula.addActionListener(this);
        this.administradorView.btnActualizar.addActionListener(this);
        this.vistaVendedor.button_registrar_ven.addActionListener(this);
        this.vistaVendedor.btnRetornar.addActionListener(this);
        this.panelAdmin.btnAdmin.addActionListener(this);
        this.panelAdmin.btnCarreras.addActionListener(this);
        this.panelAdmin.btnFacultades.addActionListener(this);
        this.panelAdmin.btnEstudiante.addActionListener(this);
        this.panelAdmin.btnVendedores.addActionListener(this);
        this.vista_programas.registrarProgama.addActionListener(this);
        this.vista_Estudiante.Button_registro_estudiantes.addActionListener(this);
        this.administradorView.btnRetrun.addActionListener(this);
        this.login.rolLogin.addActionListener(this);
        this.vista_ventas.Button1_vender.addActionListener(this);
        System.out.println("ActionListener added to panelAdministrador.admin");
    }

    public Controllers(Vendedor vendedor, IVendedorImplDao iVendedorImplDao, Facultad facultad, FacultadView facultadView, IFacultadDAOImpl facultadDAO, JComboBox<String> comboBoxFacultades, Vista_programas vista_programas, ICarreraDAOImpl carreraDAOImpl, IStudentDaoImpl iStudentDaoImpl, Vista_Estudiante vista_Estudiante, AdministradorView administradorView, AdminDAOImpl adminDaoImpl, Login login, Vista_ventas vista_ventas, PanelAdmin panelAdmin, VistaVendedores vistaVendedores, TicketsDAOImpl ticketsDAOImpl, Vista_ventas vista_ventas0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action event triggered: " + e.getSource());

        if (e.getSource() == vistaVendedor.button_registrar_ven) {
            System.out.println("Register button clicked");
            registrarVendedor();
        } else if (e.getSource() == vistaVendedor.Button_eliminar_vendedor) {
            System.out.println("Delete button clicked");
            eliminarVendedorPorCedula();
        } 
      
    
        
        else if (e.getSource() == vistaVendedor.btnCedulaBuscar) {
            System.out.println("Search by ID button clicked");
            buscarVendedorPorCedula();
        } else if (e.getSource() == facultadView.txtxAgregarFacultad) {
            System.out.println("Add faculty button clicked");
            agregarFacultad();
        } else if (e.getSource() == facultadView.txtBuscarPorCodigo) {
            System.out.println("Search faculty by ID button clicked");
            buscarFacultadPorId();
        } else if (e.getSource() == facultadView.BtnEliminar) {
            System.out.println("Delete faculty button clicked");
            eliminarFacultad();
        } else if (e.getSource() == facultadView.btnActualizar) {
            System.out.println("Update faculty button clicked");
            actualizarFacultad();
        } else if (e.getSource() == vista_programas.registrarProgama) {
            System.out.println("Register program button clicked");
            registrarPrograma();
        } else if (e.getSource() == vista_programas.btnEliminars) {
            System.out.println("Delete program button clicked");
            eliminarCarrera();
        } else if (e.getSource() == vista_programas.txtActualizar) {
            System.out.println("Update program button clicked");
            actualizarCarrera();
        } else if (e.getSource() == vista_programas.btnBuscarCodigo) {
            System.out.println("Search program by ID button clicked");
            buscarCarreraPorId();
        } else if (e.getSource() == vista_Estudiante.Button_registro_estudiantes) {
            System.out.println("Register student button clicked");
            registrarEstudiante();
        } else if (e.getSource() == vista_Estudiante.btnEliminar) {
            System.out.println("Delete student button clicked");
            eliminarEstudianteSeleccionado();
        } else if (e.getSource() == vistaVendedor.buttonAct) {
            actualizarVendedor();
        } else if (vista_Estudiante.btnBuscarCedula == e.getSource()) {
            buscarEstudiantePorCedula();
        } else if (e.getSource() == administradorView.btnRegistrar) {
            System.out.println("Register admin button clicked");
            try {
                crearAdmin();
            } catch (SQLException ex) {
                Logger.getLogger(Controllers.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == login.Button_login) {
            System.out.println("Login button clicked");
            if (login.rolLogin.getSelectedIndex() == 1) {
                logueoAdmin();
            } else if (login.rolLogin.getSelectedIndex() == 2) {
                logueoVendedor();
            } else if (login.rolLogin.getSelectedIndex() == 3) {

                logueoBienestarUniversitairo();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no se le olvide que debe presionar su rol. Gracias");
            }
        } else if (e.getSource() == panelAdmin.btnAdmin) {
            administradorView.setVisible(true);
            panelAdmin.dispose();

        } else if (e.getSource() == administradorView.btnActualizar) {
            actualizarAdmin();
        } else if (e.getSource() == panelAdmin.btnCarreras) {

            vista_programas.setVisible(true);
            panelAdmin.dispose();

        } else if (e.getSource() == administradorView.btnBuscarId) {
            buscarAdminPorId();
        } else if (e.getSource() == panelAdmin.btnEstudiante) {
            panelAdmin.dispose();
            vista_Estudiante.setVisible(true);

        } else if (e.getSource() == panelAdmin.btnVendedores) {

            panelAdmin.dispose();
            vistaVendedor.setVisible(true);
        } else if (e.getSource() == panelAdmin.btnFacultades) {

            panelAdmin.dispose();
            facultadView.setVisible(true);
        } else if (e.getSource() == vistaVendedor.button_registrar_ven) {
            System.out.println("aaa");
            registrarVendedor();
        } else if (e.getSource() == vistaVendedor.Button_eliminar_vendedor) {
            eliminarVendedorPorCedula();

        } else if (e.getSource() == vistaVendedor.btnRetornar) {
            vistaVendedor.dispose();
            panelAdmin.setVisible(true);
        } else if (e.getSource() == vistaVendedor.btnRetornar) {
            vistaVendedor.dispose();
            panelAdmin.setVisible(true);
        } else if (e.getSource() == vistaVendedor.btnRetornar) {
            vistaVendedor.dispose();
            panelAdmin.setVisible(true);
        } else if (e.getSource() == vistaVendedor.btnRetornar) {
            vistaVendedor.dispose();
            panelAdmin.setVisible(true);
        } else if (e.getSource() == administradorView.btnRetrun) {
            administradorView.dispose();
            panelAdmin.setVisible(true);
        } else if (e.getSource() == vista_Estudiante.btnRetornar) {
            vista_Estudiante.dispose();
            panelAdmin.setVisible(true);
        } else if (e.getSource() == vista_programas.regresar) {
            vista_programas.dispose();
            panelAdmin.setVisible(true);
        } else if (e.getSource() == facultadView.retornar) {
            facultadView.dispose();
            panelAdmin.setVisible(true);
        } else if (e.getSource() == administradorView.btntEliminar) {
            eliminarAdmin();

        } else if (e.getSource() == vista_ventas.Button1_vender) {
            generarCompra();
            

        } 
    }

    private void generarCompra() {
        String identificacionEstudiante = vista_ventas.txt_id_estudiante.getText().trim();

        // Verificar que el campo de identificación no esté vacío
        if (identificacionEstudiante.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes ingresar la identificación del estudiante", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Buscar el ID del estudiante utilizando la identificación
            int idEstudianteInt = iStudentDaoImpl.obtenerIdPorCedula(identificacionEstudiante);

            if (idEstudianteInt == -1) {
                JOptionPane.showMessageDialog(null, "La identificación del estudiante no existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si el estudiante está marcado como eliminado
            if (iStudentDaoImpl.esEstudianteEliminado(idEstudianteInt)) {
                JOptionPane.showMessageDialog(null, "El estudiante está marcado como eliminado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si el estudiante ya compró un ticket hoy
            String fechaActual = LocalDate.now().toString(); // Obtener la fecha actual como String
            if (ticketsDAOImpl.existeVentaTicketPorEstudianteEnFecha(idEstudianteInt, fechaActual)) {
                JOptionPane.showMessageDialog(null, "El estudiante ya ha comprado un ticket hoy", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear el objeto Tickets y asignar el estudiante mediante su ID
            Tickets tickets = new Tickets();
            Estudiante estudiante = new Estudiante();
            estudiante.setId(idEstudianteInt);
            tickets.setEstudiante(estudiante);
            tickets.setFecha(java.sql.Date.valueOf(fechaActual)); // Establecer la fecha actual como java.sql.Date

            // Llamar al DAO para insertar la venta del ticket
            ticketsDAOImpl.crearVentaTicker(tickets);
            procesarComprasDeHoy();

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Venta de ticket registrada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
procesarTicketsPorFacultad();
            mostrarCantidadTotalVentas();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al intentar registrar la venta del ticket", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Limpiar el campo de texto independientemente del resultado
            vista_ventas.txt_id_estudiante.setText("");
        }
    }

    private void mostrarCantidadTotalVentas() {
        try {
            int totalVentas = ticketsDAOImpl.obtenerCantidadTotalVentas();
            bienestarU.txtCantidad.setText(String.valueOf(totalVentas));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener la cantidad total de ventas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logueoVendedor() {
        String nombreUsuario = login.txt_usuario.getText().trim();
        char[] contrasenaChars = login.txt_contrasena.getPassword();
        String contrasena = new String(contrasenaChars);

        try {
            Optional<Vendedor> vendedorOptional = vendedorDAO.verificarUsuario(nombreUsuario, contrasena);

            if (vendedorOptional.isPresent()) {
                // Login exitoso
                JOptionPane.showMessageDialog(null, "Login exitoso");

                login.dispose();
                vista_ventas.setVisible(true);
                login.dispose();
                limpiarCampos(); // Limpia los campos de usuario y contraseña
            } else {
                // Usuario o contraseña incorrectos
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                login.txt_usuario.setText(""); // Limpiar campo de usuario
                login.txt_contrasena.setText(""); // Limpiar campo de contraseña
            }
        } catch (Exception e) {
            // Manejar excepciones de manera adecuada, por ejemplo, mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "Error al intentar iniciar sesión: " + e.getMessage());
        } finally {
            // Limpieza de la contraseña por seguridad
            Arrays.fill(contrasenaChars, ' ');
        }
    }

    


    private void logueoAdmin() {

        String nombreUsuario = login.txt_usuario.getText().trim();
        char[] contrasenaChars = login.txt_contrasena.getPassword();
        String contrasena = new String(contrasenaChars);

        try {
            Optional<Admin> adminOptional = adminDaoImpl.verificarCredenciales(nombreUsuario, contrasena);

            if (adminOptional.isPresent()) {
                panelAdmin.setVisible(true);
                login.dispose();
                // Login exitoso
                JOptionPane.showMessageDialog(null, "Login exitoso");
                limpiarCampos(); // Limpia los campos de usuario y contraseña

                // Aquí podrías realizar la navegación a otra vista o realizar otras acciones
                // Por ejemplo, abrir la ventana principal de la aplicación
                // login.dispose(); // Cerrar la ventana de login si es necesario
            } else {
                // Usuario o contraseña incorrectos
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                login.txt_usuario.setText(""); // Limpiar campo de usuario
                login.txt_contrasena.setText(""); // Limpiar campo de contraseña
            }
        } catch (Exception e) {
            // Manejar excepciones de manera adecuada, por ejemplo, mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "Error al intentar iniciar sesión: " + e.getMessage());
        } finally {
            // Limpieza de la contraseña por seguridad
            Arrays.fill(contrasenaChars, ' ');
        }
    }

    
 public void procesarTicketsPorFacultad() {
     DefaultListModel<String> listModel = new DefaultListModel<>();

    try {
        // Obtener el ResultSet de IDs de facultades, nombres y cantidad de tickets vendidos
        ResultSet rs = bienestarUniversitarioDaoImpl.obtenerFacultadesYTicketsResultSet();

        // Procesar el ResultSet
        if (rs != null) {
            while (rs.next()) {
                int id = rs.getInt("id");  // ID de la facultad
                String nombreFacultad = rs.getString("nombre_facultad");  // Nombre de la facultad
                int cantidadTickets = rs.getInt("cantidad_tickets");  // Cantidad de tickets vendidos

                // Construir la línea para mostrar en el JList
                String item = "Facultad: " + nombreFacultad +   ", Cantidad de Tickets: " + cantidadTickets;

                // Agregar la línea al modelo de lista
                listModel.addElement(item);
            }

            // Cerrar el ResultSet
            rs.close();
        }

        // Asignar el modelo de lista al JList
        bienestarU.listBiene.setModel(listModel);

    } catch (SQLException e) {
        // Manejar la excepción según tus necesidades
        e.printStackTrace();
    }

}

  public void procesarCarreras() {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        try {
            // Obtener el ResultSet de carreras con la cantidad de tickets
            ResultSet rs = bienestarUniversitarioDaoImpl.obtenerCarrerasConTickets();

            // Procesar el ResultSet
            if (rs != null) {
                while (rs.next()) {
                    int carreraId = rs.getInt("carrera_id");  // ID de la carrera
                    String nombreCarrera = rs.getString("nombre_carrera");  // Nombre de la carrera
                    int cantidadTickets = rs.getInt("cantidad_tickets");  // Cantidad de tickets vendidos

                    // Construir la línea para mostrar en el JList
                    String item = "Carrera: " + nombreCarrera + ", Cantidad Tickets: " + cantidadTickets;

                    // Agregar la línea al modelo de lista
                    listModel.addElement(item);
                }

                // Cerrar el ResultSet
                rs.close();
            }

            // Asignar el modelo de lista al JList de carreras
            bienestarU.ListCarrer.setModel(listModel);

        } catch (SQLException e) {
            // Manejar la excepción según tus necesidades
            e.printStackTrace();
        }
    }



    // obtener listado diario
    // 
    private void logueoBienestarUniversitairo() {

        String nombreUsuario = login.txt_usuario.getText().trim();
        char[] contrasenaChars = login.txt_contrasena.getPassword();
        String contrasena = new String(contrasenaChars);

        try {
            Optional<BienestarUniversitario> optional = bienestarUniversitarioDaoImpl.verificarCredenciales(nombreUsuario, contrasena);

            if (optional.isPresent()) {

                login.dispose();
                bienestarU.setVisible(true);
                // Login exitoso
                JOptionPane.showMessageDialog(null, "Login exitoso");
                limpiarCampos(); // Limpia los campos de usuario y contraseña

                // Aquí podrías realizar la navegación a otra vista o realizar otras acciones
                // Por ejemplo, abrir la ventana principal de la aplicación
                // login.dispose(); // Cerrar la ventana de login si es necesario
            } else {
                // Usuario o contraseña incorrectos
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                login.txt_usuario.setText(""); // Limpiar campo de usuario
                login.txt_contrasena.setText(""); // Limpiar campo de contraseña
            }
        } catch (Exception e) {
            // Manejar excepciones de manera adecuada, por ejemplo, mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "Error al intentar iniciar sesión: " + e.getMessage());
        } finally {
            // Limpieza de la contraseña por seguridad
            Arrays.fill(contrasenaChars, ' ');
        }
    }

    public void mostrarTodosLosAdministradores() {
        DefaultTableModel modeloTabla = (DefaultTableModel) administradorView.txtTablaAdministradores.getModel();
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

        try {
            ResultSet rs = adminDaoImpl.obtenerTododoLosAdmins();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombreUsuario = rs.getString("nombreUsuario");
                String contraseña = rs.getString("contrasena");

                // Agregar fila al modelo de la tabla
                modeloTabla.addRow(new Object[]{id, nombreUsuario, contraseña});
            }

            String arreglo[] = {"Id", "Usuario", "Contraseña"};

            modeloTabla.setColumnIdentifiers(arreglo);

            // Actualizar la vista después de agregar los datos
            administradorView.txtTablaAdministradores.setModel(modeloTabla);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener los administradores: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarAdmin() {
        int idAdmin = obtenerIdAdminSeleccionado();

        if (idAdmin == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un administrador para actualizar",
                    "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreUsuario = administradorView.txtUsuario.getText();
        String contrasena = obtenerContrasenaDesdeCampo();

        if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.",
                    "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Admin adminActualizado = new Admin(idAdmin, nombreUsuario, contrasena);

        try {
            adminDaoImpl.actualizarAdmin(adminActualizado);
            JOptionPane.showMessageDialog(null, "Administrador actualizado correctamente",
                    "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
            mostrarTodosLosAdministradores(); // Actualizar la tabla
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el administrador: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerContrasenaDesdeCampo() {
        JPasswordField campoContrasena = administradorView.txtContraseñas;
        char[] contrasenaChars = campoContrasena.getPassword();
        return new String(contrasenaChars);
    }

    public void buscarAdminPorId() {
        String idStr = administradorView.txtId.getText().trim();

        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID válido para buscar.",
                    "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            ResultSet rs = adminDaoImpl.encontrarPorId(id);

            if (rs.next()) {
                int adminId = rs.getInt("id");
                String nombreUsuario = rs.getString("nombreUsuario");
                String contraseña = rs.getString("contrasena");

                String mensaje = "ID: " + adminId + "\nNombre de Usuario: " + nombreUsuario + "\nContraseña: " + contraseña;
                JOptionPane.showMessageDialog(null, mensaje, "Administrador Encontrado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún administrador con ese ID.",
                        "Búsqueda Fallida", JOptionPane.WARNING_MESSAGE);
            }

            // Cerrar ResultSet al terminar de usarlo
            rs.close();

            // Limpiar campos después de buscar por ID
            administradorView.txtId.setText("");
            administradorView.txtUsuario.setText("");
            administradorView.txtContraseñas.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID válido (número entero).",
                    "Formato Inválido", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar administrador por ID: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// Método para limpiar los campos de usuario y contraseña
    private void limpiarCamposs() {
        login.txt_usuario.setText("");
        login.txt_contrasena.setText("");
    }

    public void buscarEstudiantePorCedula() {
        String cedula = vista_Estudiante.txtCedulaEstudent.getText().trim();

        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese una cédula válida para buscar.",
                    "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ResultSet rs = iStudentDaoImpl.buscarEstudiantePorCedula(cedula);

            if (rs.next()) {
                // Si se encontró el estudiante por la cédula, obtener sus datos
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int idCarrera = rs.getInt("id_carrera");
                int semestre = rs.getInt("semestre");

                // Mostrar los datos del estudiante en un JOptionPane
                String mensaje = "ID: " + id + "\nNombre: " + nombre + "\nCarrera (ID): " + idCarrera
                        + "\nSemestre: " + semestre;
                JOptionPane.showMessageDialog(null, mensaje, "Estudiante Encontrado", JOptionPane.INFORMATION_MESSAGE);

                // Actualizar campos de texto en la vista con los datos encontrados
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún estudiante con esa cédula.",
                        "Búsqueda Fallida", JOptionPane.WARNING_MESSAGE);
            }

            // Cerrar ResultSet al terminar de usarlo
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar estudiante por cédula: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void crearAdmin() throws SQLException {
        String nombreUsuario = administradorView.txtUsuario.getText().trim(); // Obtener el nombre de usuario del campo de texto

        // Obtener la contraseña del JPasswordField de manera segura
        char[] contrasenaChars = administradorView.txtContraseñas.getPassword();
        String contrasena = new String(contrasenaChars).trim(); // Convertir char[] a String y trim() para eliminar espacios en blanco

        // Validar que ambos campos no estén vacíos
        if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(administradorView, "Por favor, complete todos los campos.");
            return; // Salir del método si falta información
        }

        int opcion = JOptionPane.showConfirmDialog(administradorView, "Desea crear el administrador?",
                "Confirmación", JOptionPane.YES_NO_OPTION); // Imprimir detalles del error en consola
        // Mostrar mensaje de error detallado
        if (opcion == JOptionPane.YES_OPTION) {
            // Crear un nuevo objeto Admin y establecer los valores
            Admin admin = new Admin();
            admin.setNombreUsuario(nombreUsuario);
            admin.setContrasena(contrasena);

            adminDaoImpl.crearAdmin(admin); // Llamar al método crearAdmin con el objeto Admin creado
        mostrarTodosLosAdministradores();

            JOptionPane.showMessageDialog(administradorView, "Admin creado exitosamente.");

            // Limpiar los campos de texto después de crear el administrador
            administradorView.txtUsuario.setText("");
            administradorView.txtContraseñas.setText("");
        } else {
            JOptionPane.showMessageDialog(administradorView, "Operación cancelada.");
        }
        // Limpiar el contenido del JPasswordField
        java.util.Arrays.fill(contrasenaChars, ' '); // Llena el array con caracteres espacios en blanco
    }

    private void eliminarVendedorPorCedula() {
        String cedula = vistaVendedor.txt_cedula_eliminar.getText().trim();

        // Verificar si el campo está vacío (validación básica)
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(vistaVendedor, "Por favor, ingrese la cédula del vendedor a eliminar.");
            return;
        }

        // Confirmación antes de eliminar
        int opcion = JOptionPane.showConfirmDialog(vistaVendedor,
                "¿Está seguro que desea eliminar al vendedor con cédula " + cedula + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion != JOptionPane.YES_OPTION) {
            return; // El usuario canceló la eliminación
        }

        // Usar el DAO para eliminar el vendedor por cédula
        try {
            vendedorDAO.eliminarVendedorPorCedula(cedula);
            // Mostrar mensaje de éxito si la eliminación fue exitosa
            JOptionPane.showMessageDialog(vistaVendedor, "Vendedor con cédula " + cedula + " eliminado exitosamente");
            cargarDatosVendedores();

            // Limpiar el campo después de eliminar
            vistaVendedor.txt_cedula_eliminar.setText("");
        } catch (SQLException ex) {
            // Mostrar un mensaje genérico de error en caso de excepciones SQL
            JOptionPane.showMessageDialog(vistaVendedor, "Error al eliminar el vendedor",
                    "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Opcional: imprimir el stack trace para debug
        } catch (VendedorNoEncontradoException ex) {
            // Capturar excepción específica cuando el vendedor no se encuentra registrado
            JOptionPane.showMessageDialog(vistaVendedor, "Vendedor con cédula " + cedula + " no está registrado en el sistema",
                    "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
        }
    }

//    private void buscarEstudiantePorCedula() {
//        try {
//            String cedulaStudent = vista_Estudiante.txtCedulaEstudent.getText();
//
//            
//            String idCarrera = String.format(cedulaStudent);
//            // Llamar al método en el DAO para buscar la carrera por ID
//            Estudiante estudiante = iStudentDaoImpl.buscarEstudiantePorCedula(idCarrera);
//
//            // Verificar si se encontró la carrera
//            if (estudiante != null) {
//                // Mostrar información de la carrera encontrada en la GUI (ejemplo)
//                JOptionPane.showMessageDialog(null, "Estudiante encontrado:\n"
//                        + "Nombre: " + carrera.getId() + "\n"
//                        + "Identifiacion: " + carrera.getNombreCarrera() + "\n"
//                        + ": " + carrera.getFacultad().getId(),
//                        "Carrera Encontrada", JOptionPane.INFORMATION_MESSAGE);
//
//                // Limpiar el campo de texto después de mostrar el mensaje
//                vista_Estudiante.txtCedulaEstudent.setText("");
//            } else {
//                JOptionPane.showMessageDialog(null, "No se encontró ninguna carrera con el ID " + idCarrera,
//                        "Carrera no Encontrada", JOptionPane.ERROR_MESSAGE);
//                vista_programas.txtCodigoBuscar.setText("");
//
//            }
//
//        } catch (NumberFormatException ex) {
//            JOptionPane.showMessageDialog(null, "Ingrese un ID de carrera válido (número entero).",
//                    "Error de Entrada", JOptionPane.ERROR_MESSAGE);
//            vista_programas.txtCodigoBuscar.setText("");
//
//        } catch (SQLException ex) {
//            vista_programas.txtCodigoBuscar.setText("");
//
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error al buscar la carrera.", "Error de Base de Datos",
//                    JOptionPane.ERROR_MESSAGE);
//        }
//    }
    public int obtenerIdAdminSeleccionado() {
        int selectedRow = administradorView.txtTablaAdministradores.getSelectedRow();
        if (selectedRow != -1) { // -1 indica que no se ha seleccionado ninguna fila
            // Suponiendo que el ID está en la primera columna de la tabla (columna 0)
            return (int) administradorView.txtTablaAdministradores.getValueAt(selectedRow, 0);
        } else {
            return -1; // Retornar un valor que indique que no se seleccionó ninguna fila
        }
    }

    // Método para eliminar un administrador
    public void eliminarAdmin() {
        // Obtener el ID del administrador seleccionado desde la vista
        int idAdmin = obtenerIdAdminSeleccionado();

        // Confirmación antes de eliminar
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea eliminar al administrador seleccionado?", "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                // Llamar al DAO para eliminar el administrador por su ID
                boolean eliminado = adminDaoImpl.eliminarPorId(idAdmin);
                if (eliminado) {
                    JOptionPane.showMessageDialog(null, "Administrador eliminado correctamente",
                            "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    // Actualizar la vista después de eliminar
                    mostrarTodosLosAdministradores();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar al administrador",
                            "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al eliminar el administrador: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void eliminarEstudianteSeleccionado() {
        try {
            // Obtener la fila seleccionada en la tabla de estudiantes
            int filaSeleccionada = vista_Estudiante.JtableEstudiantes.getSelectedRow();

            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un estudiante de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener la cédula del estudiante seleccionado en la tabla
            String cedulaAEliminar = (String) vista_Estudiante.JtableEstudiantes.getValueAt(filaSeleccionada, 1); // Suponiendo que la cédula está en la segunda columna (index 1)

            if (cedulaAEliminar == null || cedulaAEliminar.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se pudo obtener la cédula del estudiante seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Confirmar eliminación con el usuario
            int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar a este estudiante?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                // Intentar eliminar al estudiante por su cédula
                boolean eliminado = iStudentDaoImpl.eliminarEstudiantePorCedula(cedulaAEliminar);

                if (eliminado) {
                    JOptionPane.showMessageDialog(null, "Estudiante eliminado exitosamente", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    mostrarEstudiantes(); // Método para actualizar la tabla de estudiantes
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró ningún estudiante con la cédula proporcionada", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al intentar eliminar el estudiante", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarDatos() {
        // Limpiar campos de texto y resetear otros componentes si es necesario
        vista_Estudiante.txt_nombre_estudiante.setText("");
        vista_Estudiante.txt_id_estudiante.setText("");
        vista_Estudiante.txtNumeroSemestre.setValue(1); // Puedes establecer aquí el valor por defecto del JSpinner
        vista_Estudiante.ComboBox_programas.setSelectedIndex(0); // Puedes seleccionar el primer ítem por defecto del JComboBox
    }

    private void mostrarEstudiantes() {
        try {
            ResultSet rs = iStudentDaoImpl.obtenerTodasEstudianteConCarrera(); // Obtener estudiantes con el nombre de la carrera

            // Configurar el modelo de la tabla
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre");
            model.addColumn("Identificación");
            model.addColumn("Semestre");
            model.addColumn("Carrera");
            model.addColumn("Estado");

            // Llenar la tabla con los datos de la base de datos
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String identificacion = rs.getString("identificacion");
                int semestre = rs.getInt("semestre");
                String nombreCarrera = rs.getString("nombreCarrera"); // Nombre de la carrera
                int estado = rs.getInt("eliminado"); // Estado del estudiante (0 activo, 1 eliminado)

                String estadoTexto = (estado == 0) ? "Activo" : "Eliminado";

                model.addRow(new Object[]{nombre, identificacion, semestre, nombreCarrera, estadoTexto});
            }

            // Establecer el modelo en la tabla de la vista (suponiendo que vista_Estudiante es tu tabla)
            vista_Estudiante.JtableEstudiantes.setModel(model);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al intentar mostrar los estudiantes", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarEstudiante() {
        try {
            // Obtener los datos ingresados por el usuario desde la vista
            String nombre = vista_Estudiante.txt_nombre_estudiante.getText().trim();
            String identificacion = vista_Estudiante.txt_id_estudiante.getText().trim();
            Integer semestre = (Integer) vista_Estudiante.txtNumeroSemestre.getValue();
            boolean estado = true; // Establecer automáticamente como activo

            // Validar que todos los campos obligatorios estén llenos
            if (nombre.isEmpty() || identificacion.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debes llenar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar el formato de la identificación (cedula)
            if (!validarCedula(identificacion)) {
                JOptionPane.showMessageDialog(null, "El formato de la cédula no es válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que la cédula no esté registrada previamente
            if (cedulaYaRegistrada(identificacion)) {
                JOptionPane.showMessageDialog(null, "La cédula ingresada ya está registrada", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el elemento seleccionado en el JComboBox
            String carreraSeleccionada = (String) vista_Estudiante.ComboBox_programas.getSelectedItem();

            // Verificar que haya una selección válida
            if (carreraSeleccionada != null && !carreraSeleccionada.isEmpty()) {
                // Dividir el elemento seleccionado para obtener el id y el nombre de la carrera
                String[] carreraData = carreraSeleccionada.split(" - ");
                int idCarreraSeleccionada = Integer.parseInt(carreraData[0]);
                String nombreCarrera = carreraData[1];

                // Crear un objeto Carrera con el id seleccionado
                Carrera carrera = new Carrera(idCarreraSeleccionada, nombreCarrera);

                // Crear un objeto Estudiante con los datos obtenidos y la carrera seleccionada
                Estudiante estudiante = new Estudiante(nombre, identificacion, carrera, semestre, estado);

                // Preguntar al usuario si desea registrar al estudiante
                int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas registrar al estudiante?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    // Llamar al método del DAO para crear el estudiante en la base de datos
                    iStudentDaoImpl.crearEstudiante(estudiante);

                    JOptionPane.showMessageDialog(null, "Estudiante registrado exitosamente", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);

                    mostrarEstudiantes();

                    // Limpiar los campos después de registrar
                    limpiarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Registro cancelado", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una carrera", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar el estudiante", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean cedulaYaRegistrada(String cedula) throws SQLException {
        // Consultar si la cédula ya está registrada en la base de datos
        // Retorna true si la cédula ya existe, false si no existe
        ResultSet rs = iStudentDaoImpl.buscarEstudiantePorCedula(cedula);
        return rs.next(); // Retorna true si encuentra algún registro
    }

// Método para validar el formato de la cédula
    private boolean validarCedula(String cedula) {
        // Implementar lógica de validación aquí
        // Ejemplo básico: verificar que tenga una longitud adecuada y contenga solo números
        return cedula.matches("\\d{10}"); // Ejemplo de validación básica para cédula de 10 dígitos
    }

    private void buscarCarreraPorId() {
        try {
            // Obtener el ID de la carrera a buscar desde la GUI
            String idCarreraStr = vista_programas.txtCodigoBuscar.getText();

            // Convertir el ID de String a int
            int idCarrera = Integer.parseInt(idCarreraStr);

            // Llamar al método en el DAO para buscar la carrera por ID
            Carrera carrera = carreraDAOImpl.obtenerCarreraPorId(idCarrera);

            // Verificar si se encontró la carrera
            if (carrera != null) {
                // Mostrar información de la carrera encontrada en la GUI (ejemplo)
                JOptionPane.showMessageDialog(null, "Carrera encontrada:\n"
                        + "ID: " + carrera.getId() + "\n"
                        + "Nombre: " + carrera.getNombreCarrera() + "\n"
                        + "Facultad ID: " + carrera.getFacultad().getId(),
                        "Carrera Encontrada", JOptionPane.INFORMATION_MESSAGE);

                // Limpiar el campo de texto después de mostrar el mensaje
                vista_programas.txtCodigoBuscar.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna carrera con el ID " + idCarrera,
                        "Carrera no Encontrada", JOptionPane.ERROR_MESSAGE);
                vista_programas.txtCodigoBuscar.setText("");

            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de carrera válido (número entero).",
                    "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            vista_programas.txtCodigoBuscar.setText("");

        } catch (SQLException ex) {
            vista_programas.txtCodigoBuscar.setText("");

            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar la carrera.", "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public String obtenerNombreCarrera(int idCarrera) {
        String nombreCarrera = null;
        try {
            // Llamar al método del DAO para obtener el nombre de la carrera
            nombreCarrera = carreraDAOImpl.obtenerNombreCarrera(idCarrera);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el nombre de la carrera.", "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
        }
        return nombreCarrera;
    }

    private void actualizarCarrera() {
        try {
            int filaSeleccionada = vista_programas.tablaCarreras.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una carrera para actualizar.",
                        "Error de Selección", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idCarrera = (int) vista_programas.tablaCarreras.getValueAt(filaSeleccionada, 0);
            Carrera carreraExistente = carreraDAOImpl.obtenerCarreraPorId(idCarrera);

            if (carreraExistente == null) {
                JOptionPane.showMessageDialog(null, "Carrera no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Confirmación de actualización
            int opcion = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro que desea actualizar la carrera con ID " + idCarrera + "?", "Confirmar Actualización",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opcion != JOptionPane.YES_OPTION) {
                return; // El usuario canceló la operación de actualización
            }

            // Solicitar al usuario los datos a actualizar
            String nuevoNombre = JOptionPane.showInputDialog(null,
                    "Ingrese el nuevo nombre para la carrera con ID " + idCarrera + ":",
                    carreraExistente.getNombreCarrera());

            // Verificar si el usuario canceló la entrada o no ingresó un nombre
            if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre de la carrera no puede estar vacío.",
                        "Error de Actualización", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Solicitar al usuario el nuevo ID de la facultad
            String nuevoIdFacultadStr = JOptionPane.showInputDialog(null,
                    "Ingrese el nuevo ID de la facultad para la carrera con ID " + idCarrera + ":",
                    String.valueOf(carreraExistente.getFacultad().getId()));

            // Convertir el nuevo ID de facultad a entero
            int nuevoIdFacultad;
            try {
                nuevoIdFacultad = Integer.parseInt(nuevoIdFacultadStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID de facultad no válido.", "Error de Actualización",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si el ID de facultad existe en la tabla facultad
            if (!existeFacultad(nuevoIdFacultad)) {
                JOptionPane.showMessageDialog(null, "El ID de facultad " + nuevoIdFacultad + " no existe en la tabla facultad.",
                        "Error de Actualización", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear una instancia actualizada de la carrera
            Carrera carreraActualizada = new Carrera();
            carreraActualizada.setId(idCarrera);
            carreraActualizada.setNombreCarrera(nuevoNombre);

            Facultad nuevaFacultad = new Facultad();
            nuevaFacultad.setId(nuevoIdFacultad);
            carreraActualizada.setFacultad(nuevaFacultad);

            // Actualizar la carrera en la base de datos
            carreraDAOImpl.actualizarCarrera(carreraActualizada);

            // Actualizar la tabla de carreras en la vista
            inicializarTablaCarreras();

            JOptionPane.showMessageDialog(null, "Carrera actualizada correctamente.", "Actualización Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar la carrera.", "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarVendedor() {
        // Desactivar temporalmente el botón de actualizar para evitar múltiples clics
        vistaVendedor.buttonAct.setEnabled(false);

        try {
            int filaSeleccionada = vistaVendedor.JtableVendedores.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un vendedor para actualizar.",
                        "Error de Selección", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nombreUsuario = (String) vistaVendedor.JtableVendedores.getValueAt(filaSeleccionada, 0);
            Optional<Vendedor> vendedorExistenteOpt = vendedorDAO.obtenerVendedorPorNombreUsuario(nombreUsuario);

            if (!vendedorExistenteOpt.isPresent()) {
                JOptionPane.showMessageDialog(null, "Vendedor no encontrado en la base de datos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Vendedor vendedorExistente = vendedorExistenteOpt.get();

            // Confirmación de actualización
            int opcion = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro que desea actualizar el vendedor con nombre de usuario " + nombreUsuario + "?",
                    "Confirmar Actualización", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opcion != JOptionPane.YES_OPTION) {
                return; // El usuario canceló la operación de actualización
            }

            // Solicitar al usuario los datos a actualizar
            String nuevoNombreUsuario = JOptionPane.showInputDialog(null,
                    "Ingrese el nuevo nombre de usuario para el vendedor:", vendedorExistente.getNombreUsuario());

            // Verificar si el usuario canceló la entrada o no ingresó un nombre
            if (nuevoNombreUsuario == null || nuevoNombreUsuario.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Se canceló la actualización. El nombre de usuario no puede estar vacío.",
                        "Error de Actualización", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar que el nuevo nombre de usuario no esté repetido
            if (!nuevoNombreUsuario.equals(vendedorExistente.getNombreUsuario())
                    && vendedorDAO.existeNombreUsuario(nuevoNombreUsuario)) {
                JOptionPane.showMessageDialog(null, "El nombre de usuario ingresado ya está registrado.",
                        "Error de Actualización", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Solicitar al usuario la nueva contraseña
            String nuevaContrasena = JOptionPane.showInputDialog(null,
                    "Ingrese la nueva contraseña para el vendedor:", vendedorExistente.getContrasena());

            // Verificar si el usuario canceló la entrada o no ingresó una contraseña
            if (nuevaContrasena == null || nuevaContrasena.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Se canceló la actualización. La contraseña no puede estar vacía.",
                        "Error de Actualización", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validación de longitud y contenido de la contraseña
            if (nuevaContrasena.length() != 5) {
                JOptionPane.showMessageDialog(null, "La contraseña debe tener exactamente 5 caracteres.",
                        "Error de Contraseña", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // La cédula actual no debe cambiar durante la actualización
            String cedulaActual = vendedorExistente.getCedula();

            // Crear una instancia actualizada del vendedor
            Vendedor vendedorActualizado = new Vendedor(nuevoNombreUsuario, nuevaContrasena, cedulaActual);

            // Actualizar el vendedor en la base de datos
            boolean actualizado = vendedorDAO.actualizarVendedor(vendedorActualizado);

            if (actualizado) {
                // Actualizar la tabla de vendedores en la vista
                cargarDatosVendedores();
                JOptionPane.showMessageDialog(null, "El vendedor ha sido actualizado correctamente.", "Actualización Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el vendedor en la base de datos.", "Error de Base de Datos",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println("catch");
        } finally {
            // Reactivar el botón de actualizar
            vistaVendedor.buttonAct.setEnabled(true);
            // Deseleccionar la fila seleccionada
            System.out.println("finally");
            vistaVendedor.JtableVendedores.clearSelection();
        }
    }

// Método para verificar si existe una facultad con el ID dado
    private boolean existeFacultad(int idFacultad) throws SQLException {
        Facultad facultad = facultadDAO.obtenerFacultadPorId(idFacultad); // Asumiendo que tienes un DAO para la entidad Facultad
        return facultad != null;
    }

    private void eliminarCarrera() {
        try {
            int idCarrera = Integer.parseInt(vista_programas.txtCodigo.getText());

            // Verificar si existen estudiantes asociados a esta carrera
            boolean estudiantesAsociados = carreraDAOImpl.existenEstudiantesPorCarrera(idCarrera);

            if (estudiantesAsociados) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar la carrera porque existen estudiantes asociados.", "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                vista_programas.txtCodigo.setText("");
                return; // Salir del método sin intentar eliminar la carrera
            }

            int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro de que desea eliminar la carrera con ID " + idCarrera + "?",
                    "Confirmación de Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = carreraDAOImpl.eliminarCarrera(idCarrera);

                if (eliminado) {
                    JOptionPane.showMessageDialog(null, "Carrera eliminada correctamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    inicializarTablaCarreras();
                    vista_programas.txtCodigo.setText("");// Método para actualizar la vista después de eliminar
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar la carrera. ID no encontrado.", "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                    vista_programas.txtCodigo.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Eliminación cancelada.", "Cancelación", JOptionPane.INFORMATION_MESSAGE);
                vista_programas.txtCodigo.setText("");
            }

            // Limpiar el campo de texto después de la operación
            vista_programas.txtCodigo.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "ID de carrera no válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            vista_programas.txtCodigo.setText(""); // Limpiar el campo de texto en caso de error de formato
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar la carrera.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            vista_programas.txtCodigo.setText(""); // Limpiar el campo de texto en caso de error de base de datos
        }
    }

    private void registrarPrograma() {
        String nombreFacultad = (String) vista_programas.txtFacultad.getSelectedItem().toString().trim();

        try {
            int idFacultad = obtenerIdFacultad(nombreFacultad);

            if (idFacultad == -1) {
                mostrarMensajeError("No se encontró ninguna facultad con ese nombre.");
                return;
            }

            String nombreCarrera = vista_programas.txtNombre.getText().trim();
            if (nombreCarrera.isEmpty()) {
                mostrarMensajeError("Debe ingresar un nombre para la carrera.");
                return;
            }

            if (carreraDAOImpl.existeCarrera(nombreCarrera, idFacultad)) {
                mostrarMensajeError("La carrera ya está registrada para esta facultad.");
                return;
            }

            Carrera carrera = new Carrera();
            carrera.setNombreCarrera(nombreCarrera);
            Facultad facultad = facultadDAO.obtenerFacultadPorId(idFacultad);
            carrera.setFacultad(facultad);

            // Intentar crear la carrera
            carreraDAOImpl.crearCarrera(carrera); // Suponiendo que crearCarrera() no devuelve nada (void)
            inicializarTablaCarreras();
            // Si llegamos aquí, asumimos que la creación fue exitosa
            JOptionPane.showMessageDialog(vista_programas, "Carrera registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista_programas.txtNombre.setText(""); // Limpiar el campo de nombre después del registro

        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarMensajeError("Error al registrar la carrera: " + ex.getMessage());
        }
    }
    
 
  


 
    
    // Método para inicializar o actualizar la tabla de carreras
    public void inicializarTablaCarreras() {
        try {
            ResultSet rs = carreraDAOImpl.obtenerTodasLasCarreras();

            tablaCarrerasModel = new DefaultTableModel();
            tablaCarrerasModel.setColumnIdentifiers(new String[]{"ID", "Nombre", "ID Facultad"});

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombreCarrera = rs.getString("nombreCarrera");
                int idFacultad = rs.getInt("id_facultad");

                tablaCarrerasModel.addRow(new Object[]{id, nombreCarrera, idFacultad});
            }

            vista_programas.tablaCarreras.setModel(tablaCarrerasModel);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista_programas, "Error al obtener las carreras: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(vista_programas, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private int obtenerIdFacultad(String nombreFacultad) throws SQLException {
        return facultadDAO.obtenerIdFacultadPorNombre(nombreFacultad);
    }

    private void cargarYMostrarFacultades() {
        try {
            ResultSet rs = facultadDAO.obtenerTodasLasFacultadesConId();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            while (rs.next()) {
                int idFacultad = rs.getInt("id");

                System.out.println(idFacultad);
                String nombreFacultad = rs.getString("nombre");
                model.addElement(nombreFacultad); // Agregar nombre de la facultad al ComboBox
                // Puedes guardar el ID de la facultad si lo necesitas para asociarlo con la carrera
            }

            vista_programas.txtFacultad.setModel(model); // Asignar el modelo al JComboBox de facultades en la vista
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las facultades", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cargarYMostrarCarreras() {
        try {
            ResultSet rs = carreraDAOImpl.obtenerTodasLasCarreras();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            while (rs.next()) {
                int idCarrera = rs.getInt("id");
                String nombreCarrera = rs.getString("nombreCarrera"); // Ajusta el nombre según la estructura de tu base de datos
                model.addElement(idCarrera + " - " + nombreCarrera); // Agregar id y nombre al ComboBox
            }

            vista_Estudiante.ComboBox_programas.setModel(model); // Asignar el modelo al JComboBox en la vista de estudiantes

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las carreras", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarFacultad() {
        int idFacultad;
        try {
            idFacultad = Integer.parseInt(facultadView.txtIdFacultad.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(facultadView, "Por favor, ingrese un ID válido.",
                    "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
            limpiarCampoCodigoBuscar();
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(facultadView,
                "¿Está seguro de que desea eliminar la facultad con ID " + idFacultad + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                // Crear instancia de la implementación de ICarreraDAO (por ejemplo, CarreraDAOImpl)

                // Verificar si existen carreras asociadas a esta facultad
                if (carreraDAOImpl.existenCarrerasPorFacultad(idFacultad)) {
                    JOptionPane.showMessageDialog(facultadView,
                            "No se puede eliminar la facultad porque existen carreras asociadas.",
                            "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                    limpiarCampoCodigoBuscar();
                    return;
                }

                // Intentar eliminar la facultad
                if (facultadDAO.eliminarFacultad(idFacultad)) {
                    JOptionPane.showMessageDialog(facultadView, "Facultad eliminada correctamente",
                            "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    cargarYMostrarFacultades();
                    inicializarTablaCarreras();
                    cargarYMostrarFacultadesDesdeDB();
                    limpiarCampoCodigoBuscar();
                } else {
                    JOptionPane.showMessageDialog(facultadView,
                            "No se puede eliminar. Facultad no encontrada con ID: " + idFacultad,
                            "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                    limpiarCampoCodigoBuscar();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(facultadView, "Error al eliminar la facultad",
                        "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                limpiarCampoCodigoBuscar();
            }
        } else {
            limpiarCampoCodigoBuscar();
        }
    }

    private void buscarFacultadPorId() {
        int idFacultad;
        try {
            idFacultad = Integer.parseInt(facultadView.txtCodigo.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(facultadView, "Por favor, ingrese un ID válido.",
                    "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
            limpiarCampoCodigoBuscar();
            return;
        }

        // Usar el DAO para buscar la facultad por ID
        try {
            Facultad facultadEncontrada = facultadDAO.obtenerFacultadPorId(idFacultad);
            if (facultadEncontrada != null) {
                mostrarMensajeFacultadEncontrada(facultadEncontrada);
                limpiarCampoCodigoBuscar();
            } else {
                JOptionPane.showMessageDialog(facultadView, "Facultad no encontrada con ID: " + idFacultad,
                        "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
                limpiarCampoCodigoBuscar();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(facultadView, "Error al buscar la facultad",
                    "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
            limpiarCampoCodigoBuscar();
        }
    }

    private void mostrarMensajeFacultadEncontrada(Facultad facultad) {
        String mensaje = "ID: " + facultad.getId() + "\nNombre: " + facultad.getNombre();
        JOptionPane.showMessageDialog(facultadView, mensaje, "Facultad Encontrada", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarCampoCodigoBuscar() {

        facultadView.txtIdFacultad.setText("");
        facultadView.txtCodigo.setText(""); // Limpiar el campo de búsqueda por código
    }

    private void agregarFacultad() {
        String nombreFacultad = facultadView.txtFacultades.getText();

        // Verificar si el campo está vacío (validación básica)
        if (nombreFacultad.isEmpty()) {
            JOptionPane.showMessageDialog(facultadView, "Por favor, ingrese el nombre de la facultad.");
            return;
        }

        // Crear un nuevo objeto Facultad
        Facultad nuevaFacultad = new Facultad();
        nuevaFacultad.setNombre(nombreFacultad);

        // Usar el DAO para guardar la nueva facultad
        try {
            facultadDAO.crearFacultad(nuevaFacultad);
            // Mostrar mensaje de éxito si la inserción fue exitosa
            JOptionPane.showMessageDialog(facultadView, "Facultad agregada con éxito");
            cargarYMostrarFacultadess(); // Actualizar la tabla después de agregar
             // Limpiar el campo después de agregar
            limpiarCampoNombreFacultad();
        } catch (SQLIntegrityConstraintViolationException ex) {
            // Capturar la excepción específica para restricciones únicas
            // Mostrar un JOptionPane con un mensaje de error
            JOptionPane.showMessageDialog(facultadView, "Error: La facultad ya existe en la base de datos",
                    "Error de Inserción", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            // Mostrar un mensaje genérico de error en caso de otras excepciones SQL
            JOptionPane.showMessageDialog(facultadView, "Error al agregar la facultad",
                    "Error de Inserción", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Opcional: imprimir el stack trace para debug
        }
    }

    private void limpiarCampoNombreFacultad() {
        facultadView.txtFacultades.setText(""); // Limpiar el campo de nombre de facultad
    }

    private void configurarTablaFacultad() {
        String[] columnas = {"ID", "Nombre"};
        dtmFacultad = new DefaultTableModel(columnas, 0); // Modelo vacío con las columnas definidas
        facultadView.jTableFacultad.setModel(dtmFacultad); // Asignar el modelo a la tabla en la vista
    }

    private void cargarYMostrarFacultadess() {
        try {
            ResultSet rs = facultadDAO.obtenerTodasLasFacultades();
            mostrarDatosEnTablaFacultad(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(facultadView, "Error al cargar los datos de las facultades", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDatosEnTablaFacultad(ResultSet rs) throws SQLException {
        dtmFacultad.setRowCount(0); // Limpiar la tabla antes de cargar los datos
        while (rs.next()) {
            dtmFacultad.addRow(new Object[]{rs.getInt("id"), rs.getString("nombre")});
        }
    }

    public void actualizarFacultad() {
        int filaSeleccionada = facultadView.jTableFacultad.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idFacultad = (int) dtmFacultad.getValueAt(filaSeleccionada, 0);
            String nombreActual = (String) dtmFacultad.getValueAt(filaSeleccionada, 1);

            // Pedir el nuevo nombre de la facultad mediante un JOptionPane
            String nuevoNombre = JOptionPane.showInputDialog(facultadView,
                    "Ingrese el nuevo nombre para la facultad con ID " + idFacultad + ":",
                    nombreActual);

            // Verificar si el usuario canceló la entrada o no ingresó un nombre
            if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(facultadView, "El nombre de la facultad no puede estar vacío.",
                        "Error de Actualización", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Facultad facultad = new Facultad();
            facultad.setId(idFacultad);
            facultad.setNombre(nuevoNombre);

            try {
                if (facultadDAO.actualizarFacultad(facultad)) {
                    JOptionPane.showMessageDialog(facultadView, "Facultad actualizada correctamente",
                            "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    cargarYMostrarFacultadess(); // Actualizar la tabla después de la actualización
                } else {
                    JOptionPane.showMessageDialog(facultadView, "Error al actualizar la facultad",
                            "Error de Actualización", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(facultadView, "Error al actualizar la facultad",
                        "Error de Actualización", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(facultadView, "Por favor, seleccione una fila para actualizar.",
                    "Error de Actualización", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarYMostrarFacultadesDesdeDB() {
        try {
            ResultSet rs = facultadDAO.obtenerTodasLasFacultades();
            mostrarDatosEnTablaFacultad(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(facultadView, "Error al cargar los datos de las facultades", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarVendedor() {
        String nombreUsuario = vistaVendedor.txt_usuario_vendedor.getText().trim();
        String contrasena = new String(vistaVendedor.txtxPasswordVendedor.getPassword());
        String cedula = vistaVendedor.txt_cedula_vendedor.getText().trim();

        // Validación básica de campos vacíos
        if (nombreUsuario.isEmpty() || contrasena.isEmpty() || cedula.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validación de longitud y contenido de la contraseña
        if (contrasena.length() != 5) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener exactamente 5 caracteres.",
                    "Error de Contraseña", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validación de longitud y contenido de la cédula
        if (cedula.length() != 10 || !cedula.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "La cédula debe tener exactamente 10 caracteres numéricos.",
                    "Error de Cédula", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmación antes de registrar
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea registrar al vendedor?", "Confirmar Registro",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion != JOptionPane.YES_OPTION) {
            return; // El usuario canceló el registro
        }

        // Crear un nuevo objeto Vendedor
        Vendedor nuevoVendedor = new Vendedor(nombreUsuario, contrasena, cedula);

        // Validación adicional: verificar si la cédula ya existe
        try {
            if (vendedorDAO.existeCedula(cedula)) {
                JOptionPane.showMessageDialog(null, "La cédula ingresada ya está registrada para otro vendedor.",
                        "Error de Inserción", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al verificar la cédula en la base de datos.",
                    "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Opcional: imprimir el stack trace para debug
            return;
        }

        // Validación adicional: verificar si el nombre de usuario ya existe
        try {
            if (vendedorDAO.existeNombreUsuario(nombreUsuario)) {
                JOptionPane.showMessageDialog(null, "El nombre de usuario ingresado ya está registrado.",
                        "Error de Inserción", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al verificar el nombre de usuario en la base de datos.",
                    "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Opcional: imprimir el stack trace para debug
            return;
        }

        // Usar el DAO para guardar el nuevo vendedor
        try {
            vendedorDAO.crearVendedor(nuevoVendedor);
            // Mostrar mensaje de éxito si la inserción fue exitosa
            JOptionPane.showMessageDialog(vistaVendedor, "Vendedor registrado con éxito", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            cargarDatosVendedores(); // Actualizar la tabla después de agregar

            // Limpiar los campos después de registrar
            limpiarCampos();
        } catch (SQLIntegrityConstraintViolationException ex) {
            // Capturar la excepción específica para restricciones únicas
            JOptionPane.showMessageDialog(vistaVendedor, "Error: Nombre de usuario o cédula ya existen en la base de datos",
                    "Error de Inserción", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            // Mostrar un mensaje genérico de error en caso de otras excepciones SQL
            JOptionPane.showMessageDialog(vistaVendedor, "Error al registrar el vendedor",
                    "Error de Inserción", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Opcional: imprimir el stack trace para debug
        }
    }

    private void cargarDatosVendedores() {
        try {
            ResultSet rs = vendedorDAO.obtenerTodosLosVendedores();
            mostrarDatosEnTabla(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los datos de los vendedores", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDatosEnTabla(ResultSet rs) throws SQLException {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Nombre de Usuario");
        dtm.addColumn("Contraseña");
        dtm.addColumn("Cédula");

        while (rs.next()) {
            String nombreUsuario = rs.getString("nombreUsuario");
            String contrasena = rs.getString("contrasena");
            String cedula = rs.getString("cedula");
            dtm.addRow(new Object[]{nombreUsuario, contrasena, cedula});
        }

        vistaVendedor.JtableVendedores.setModel(dtm);
    }

    private void buscarVendedorPorCedula() {
        String cedula = vistaVendedor.txtxCedulaBuscar.getText();

        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(vistaVendedor, "Por favor, ingrese la cédula del vendedor a buscar.");
            return;
        }

        try {
            Optional<Vendedor> optionalVendedor = vendedorDAO.buscarVendedorPorCedula(cedula);
            if (optionalVendedor.isPresent()) {
                Vendedor vendedor = optionalVendedor.get();
                mostrarMensajeVendedorEncontrado(vendedor);
                limpiarCampoCedulaBuscar();
            } else {
                JOptionPane.showMessageDialog(vistaVendedor, "Vendedor no encontrado con cédula: " + cedula,
                        "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
                limpiarCampoCedulaBuscar();

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vistaVendedor, "Error al buscar el vendedor",
                    "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
            limpiarCampoCedulaBuscar();

        } catch (VendedorNoEncontradoException ex) {
            JOptionPane.showMessageDialog(vistaVendedor, ex.getMessage(),
                    "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
            limpiarCampoCedulaBuscar();

        }
    }

    private void mostrarMensajeVendedorEncontrado(Vendedor vendedor) {
        String mensaje = "Nombre de Usuario: " + vendedor.getNombreUsuario()
                + "\nContraseña: " + vendedor.getContrasena()
                + "\nCédula: " + vendedor.getCedula();

        JOptionPane.showMessageDialog(vistaVendedor, mensaje, "Vendedor Encontrado",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarCampoCedulaBuscar() {
        vistaVendedor.txtxCedulaBuscar.setText(""); // Establecer el texto del campo de cédula a cadena vacía
    }

    private void limpiarCampos() {
        vistaVendedor.txt_usuario_vendedor.setText("");
        vistaVendedor.txtxPasswordVendedor.setText("");
        vistaVendedor.txt_cedula_vendedor.setText("");
    }

    private void configurarTabla() {
        String[] arreglo = {"Nombre de Usuario", "Contraseña", "Cedula"};
        dtmVendedor = new DefaultTableModel(arreglo, 0); // Crear modelo vacío con los nombres de columnas
        vistaVendedor.JtableVendedores.setModel(dtmVendedor); // Asignar modelo a la tabla en la vista
    }

    public void configurarTablaVenta() {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.setColumnIdentifiers(new Object[]{"Ticket ID", "Fecha", "Estudiante ID", "Nombre Estudiante", "Identificación", "ID Carrera", "Semestre", "Carrera"});
        vista_ventas.TablaVentas.setModel(modeloTabla);
    }

    public void procesarComprasDeHoy() {
        String fechaActual = LocalDate.now().toString(); // Obtener la fecha actual como String

        try {
            ResultSet rs = ticketsDAOImpl.obtenerComprasDeHoy(fechaActual);

            // Limpiar la tabla antes de actualizarla
            DefaultTableModel model = (DefaultTableModel) vista_ventas.TablaVentas.getModel();
            model.setRowCount(0); // Limpiar todas las filas

            // Procesar el ResultSet y actualizar la tabla
            while (rs.next()) {
                int ticketId = rs.getInt("id");
                String fecha = rs.getString("fecha");
                int estudianteId = rs.getInt("estudiante_id");
                String nombreEstudiante = rs.getString("estudiante_nombre");
                String identificacionEstudiante = rs.getString("estudiante_identificacion");
                int idCarreraEstudiante = rs.getInt("estudiante_id_carrera");
                int semestreEstudiante = rs.getInt("estudiante_semestre");
                String nombreCarrera = rs.getString("nombre_carrera");

                // Agregar fila a la tabla
                model.addRow(new Object[]{ticketId, fecha, estudianteId, nombreEstudiante,
                    identificacionEstudiante, idCarreraEstudiante,
                    semestreEstudiante, nombreCarrera});
            }

            // Cerrar el ResultSet al terminar de usarlo
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo de errores
        }
    }
}
