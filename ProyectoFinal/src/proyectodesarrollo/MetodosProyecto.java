/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectodesarrollo;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Esparqui
 */
public class MetodosProyecto {

    //Verifica el ultimo digito de un codigo de barras de tipo GTIN14
//H o l a   M u n d o
//0 1 2 3 4 5 6 7 8 9
//    
//String sCadena = "Hola Mundo";
//String sSubCadena = sCadena.substring(5,10);
//System.out.println(sSubCadena);
    
    public double redondearDecimales(double valor, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valor;
        parteEntera = Math.floor(resultado);
        resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
        return resultado;
    }
    
    public void calcularMarca(String codigo){
        String codigoempresa;
        if(codigo.length() == 14){
            codigoempresa = codigo.substring(3,7);
        }else if(codigo.length() == 13){
            codigoempresa = codigo.substring(4,8);
        }else if(codigo.length() == 12){
            codigoempresa = codigo.substring(3,7);
        }else {
        }
        
    }
    
    
    public void cargarCombo(JComboBox combobox, String tabla, String columna) {
        try {
            DefaultComboBoxModel mod = new DefaultComboBoxModel();

            String sql = "";
            sql = "SELECT " + columna + " FROM " + tabla;
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);

            mod.addElement("");
            while (rs.next()) {
                String dato = rs.getString(columna);
                mod.addElement(dato);
                combobox.setModel(mod);
            }
            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MetodosProyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void DatosGTIN14(String codigo) {
        //si el primer digito es 1 este representa una docena de ese producto, los valores cambian de 1-8 8 represeta 96 unidades 
        //Si el primer digito es 9 quiere decir que el contenido e variable.
        ArrayList<String> cadena = new ArrayList();
        int unidades;
        String GTIN13 = codigo.substring(1, 13);
        int digverificador = verificadorCodBarrasGTIN14GTIN13(codigo);

        int fin = codigo.length();
        int k = 1;
        while (k <= fin) {
            cadena.add(String.valueOf(codigo.charAt(k)));
            k++;
        }
        unidades = Integer.valueOf(cadena.get(0)) * 12;
    }

    public int verificadorCodBarrasGTIN14GTIN13(String codigo) {
        try {
            int digito = 0;
            int sumapares = 0;
            int sumaimpares = 0;
            ArrayList cadena = new ArrayList();
            int fin = codigo.length();
            int k = 0;
            cadena.add(k);

            while (k < fin) {
                cadena.add(codigo.charAt(k));
                k++;
            }
            k = 0;
            do {
                if (k % 2 == 0) {
                    sumapares += Integer.valueOf(cadena.get(k).toString());
//                System.out.println("Par = " + Integer.valueOf(cadena.get(k).toString()));
                } else {
                    sumaimpares += Integer.valueOf(cadena.get(k).toString());
//                System.out.println("Impar = " + Integer.valueOf(cadena.get(k).toString()));
                }
                k++;
            } while (k < cadena.size() - 1);
//        System.out.println("Suma pares = " + sumapares);
//        System.out.println("Suma impares = " + sumaimpares);
            char aux;

            int mult = 3 * sumapares + sumaimpares;
            if (mult >= 100) {
                aux = String.valueOf(mult).charAt(2);
            } else {
                aux = String.valueOf(mult).charAt(1);
            }
//        System.out.println("producto = " + mult);
//        System.out.println(aux);
            while (aux != '0') {
                mult += 1;
                if (mult >= 100) {
                    aux = String.valueOf(mult).charAt(2);
                } else {
                    aux = String.valueOf(mult).charAt(1);
                }
//            System.out.println(aux);
                digito += 1;
            }
            return digito;
        } catch (Exception e) {
            return 99;
        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int verificadorCodBarrasGTIN8(String codigo) {
        try {
            int digito = 0;
            int sumapares = 0;
            int sumaimpares = 0;

            ArrayList cadena = new ArrayList();
            int fin = codigo.length();
            int k = 0;
            cadena.add(k);

            while (k < fin) {
                cadena.add(codigo.charAt(k));
                k++;
            }
            k = 0;
            do {
                if (k % 2 == 0) {
                    sumaimpares += Integer.valueOf(cadena.get(k).toString());
//                System.out.println("Par = " + Integer.valueOf(cadena.get(k).toString()));
                } else {
                    sumapares += Integer.valueOf(cadena.get(k).toString());
//                System.out.println("Impar = " + Integer.valueOf(cadena.get(k).toString()));
                }
                k++;
            } while (k < cadena.size() - 1);
//        System.out.println("Suma pares = " + sumapares);
//        System.out.println("Suma impares = " + sumaimpares);
            char aux;

            int mult = 3 * sumapares + sumaimpares;
            if (mult >= 100) {
                aux = String.valueOf(mult).charAt(2);
            } else {
                aux = String.valueOf(mult).charAt(1);
            }
//        System.out.println("producto = " + mult);
//        System.out.println(aux);
            while (aux != '0') {
                mult += 1;
                if (mult >= 100) {
                    aux = String.valueOf(mult).charAt(2);
                } else {
                    aux = String.valueOf(mult).charAt(1);
                }
//            System.out.println(aux);
                digito += 1;
            }
            return digito;

        } catch (Exception e) {
            return 99;
        }
    }

    /////////////////////////////////////////////////////////
    public int verificadorCodBarrasGTIN12(String codigo) {
        try {
            int digito = 0;
            int sumapares = 0;
            int sumaimpares = 0;

            ArrayList cadena = new ArrayList();
            int fin = codigo.length();
            int k = 0;
            cadena.add(k);

            while (k < fin) {
                cadena.add(codigo.charAt(k));
                k++;
            }
            k = 0;
            do {
                if (k % 2 == 0) {
                    sumaimpares += Integer.valueOf(cadena.get(k).toString());
//                System.out.println("Par = " + Integer.valueOf(cadena.get(k).toString()));
                } else {
                    sumapares += Integer.valueOf(cadena.get(k).toString());
//                System.out.println("Impar = " + Integer.valueOf(cadena.get(k).toString()));
                }
                k++;
            } while (k < cadena.size() - 1);
//        System.out.println("Suma pares = " + sumapares);
//        System.out.println("Suma impares = " + sumaimpares);
            char aux;

            int mult = 3 * sumapares + sumaimpares;
            if (mult >= 100) {
                aux = String.valueOf(mult).charAt(2);
            } else {
                aux = String.valueOf(mult).charAt(1);
            }
            while (aux != '0') {
                mult += 1;
                if (mult >= 100) {
                    aux = String.valueOf(mult).charAt(2);
                } else {
                    aux = String.valueOf(mult).charAt(1);
                }
                digito += 1;
            }
            return digito;

        } catch (Exception e) {
            return 99;
        }
    }
    //////////////////////////////////////////////////////

    public String transformarGTIN14aGTIN13(String codigo) {
        String GTIN13 = codigo.substring(1, 13);
        int digito13 = verificadorCodBarrasGTIN14GTIN13(GTIN13 + "0");
        String codnew = GTIN13 + String.valueOf(digito13);
        return codnew;

    }
}
