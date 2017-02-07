/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectodesarrollo;

import java.sql.*;
import javax.swing.JOptionPane;

public class conexion {

    //Connexion MYSQL
//    Connection conexion = null;
//
//    public Connection conectar() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            conexion = DriverManager.getConnection("jdbc:mysql://localhost/SUPERMERCADO", "root", "");
//            JOptionPane.showMessageDialog(null,"conexión OK");
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//        return conexion;
//    }
//    Conexion POSTGRESQL

    private Connection conex = null;

    public Connection conectar() {
        if (conex != null) {
            return conex;
        }
        //String url = "Jdbc:postgresql://localhost:5432/";
        String url = "jdbc:postgresql://192.168.1.9:5432/ds1";
        try {
            Class.forName("org.postgresql.Driver");
            conex = DriverManager.getConnection(url, "postgres", "123456");
            if (conex != null) {
                JOptionPane.showMessageDialog(null,"Conexion ok");
            }
            return conex;
            
            
        } catch (Exception e) {
            //System.out.println("Problema al establecer la Conexión a la base de datos 1 ");
        }
        return conex;
    }  
}
