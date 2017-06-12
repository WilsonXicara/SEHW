/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author pc
 */
public class ConectarConBD {
    private Connection conexion;
    private final String nombreBD, usuarioBD, contraseñaBD;

    public ConectarConBD() {
        nombreBD = "ecegua";
        usuarioBD = "eceguaxela";
        contraseñaBD = "usuario";
        conectar_con_bd();
    }
    private void conectar_con_bd() {
        try {
            String miIP = InetAddress.getLocalHost().getHostAddress();
            Class.forName("org.gjt.mm.mysql.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://" + miIP + "/" + nombreBD, usuarioBD, contraseñaBD);
        } catch (ClassNotFoundException | SQLException | UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con la Base de Datos.\n\n"+ex.getMessage(), "Erro de conexión", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ConectarConBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConexion() { return conexion; }
}
