/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectodesarrollo;

import VENTAS.MenuVentas;
import com.sun.org.apache.bcel.internal.classfile.PMGClass;
import java.awt.Color;
import static java.awt.Color.red;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Esparqui
 */
public class AdministrarProductos extends javax.swing.JInternalFrame {

    DefaultTableModel modTabla;
    MetodosProyecto mp = new MetodosProyecto();

    
    
    public AdministrarProductos() {
        initComponents();
        mp.cargarCombo(cbCategoria, "CATEGORIAS", "NOM_CAT");
        mp.cargarCombo(cbPresentacion, "UNIDADES_MEDIDA", "COD_UNI");
        mp.cargarCombo(cbProveedor, "PROVEEDORES", "NOM_PROV");
        txtCodigo.setSelectionStart(0);
        lblCantidad.setText("cantidad");
        lblDe.setVisible(false);
        lblUnidades.setVisible(false);
        lblUnidadesFinal.setVisible(false);
        botonesInicio(false);
    }

    public void componentesProductoExistente() {
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        txtCantidad.setEditable(true);
        btnAñadirStock.setEnabled(true);
    }

    public void activarProductoCuandoExiste() {
        if (verificarExistenciaProducto() == true) {
            componentesProductoExistente();
        }
    }

    public void limpiarComponentes() {
        DefaultTableModel md = new DefaultTableModel();
        cbProveedor.setSelectedIndex(0);
        txtPrecioUnitario.setText("");
        lblDe.setVisible(false);
        lblUnidades.setVisible(false);
        lblUnidadesFinal.setVisible(false);
        txtNombre.setText("");
        txtMarca.setText("");
        cbCategoria.setSelectedIndex(0);
        cbPresentacion.setSelectedIndex(0);
        txtCodigo.setText("");
        txtCodigo.setBackground(Color.white);
        txtPresentacion.setText("");
        txtStock.setText("");
        txtPVPUnidad.setText("");
        rbNo.setSelected(false);
        rbSi.setSelected(false);
        txtCantidad.setText("");
        txtCodigo.setEditable(true);
        String[] titulos = {"Proveedor", "Precio Producto"};
        md = new DefaultTableModel(null, titulos);
        tblProveedores.setModel(md);
    }

    public void bloquearComponentes() {
        txtNombre.setEditable(false);
        txtMarca.setEditable(false);
        cbCategoria.setEnabled(false);
        cbPresentacion.setEnabled(false);
        txtCodigo.setEditable(false);
        txtPresentacion.setEditable(false);
        txtStock.setEditable(false);
        txtPVPUnidad.setEditable(false);
        rbNo.setEnabled(false);
        rbSi.setEnabled(false);
        btnCategoria.setEnabled(false);
    }

    public void desbloquearComponentes() {

        txtNombre.setEditable(true);
        txtMarca.setEditable(true);
        cbCategoria.setEnabled(true);
        cbPresentacion.setEnabled(true);
        txtCodigo.setEditable(true);
        txtPresentacion.setEditable(true);
        txtStock.setEditable(true);
        txtPVPUnidad.setEditable(true);
        rbNo.setEnabled(true);
        rbSi.setEnabled(true);

        btnCategoria.setEnabled(true);
        botonesInicio(true);
    }

    public void botonesInicio(boolean valor) {
        btnActualizar.setEnabled(valor);
        btnEliminar.setEnabled(valor);
    }

    public void botonEditar() {
        if (verificarExistenciaProducto() == true) {
            desbloquearComponentes();
            txtCantidad.setEditable(false);
            txtCodigo.setEditable(false);
            btnGuardar.setEnabled(false);
            btnAñadirStock.setEnabled(false);
            txtStock.setEditable(false);
            btnActualizar.setEnabled(true);
            btnEliminar.setEnabled(true);
        } else {
            limpiarComponentes();
            botonesInicio(false);
        }
    }

    public boolean verificarProductoReal() {
        boolean esreal = false;
        try {
            if (txtCodigo.getText().length() == 14) {
                int digito = mp.verificadorCodBarrasGTIN14GTIN13(txtCodigo.getText());
                char dig = txtCodigo.getText().charAt(13);
                String di = String.valueOf(dig);

                if (digito == Integer.valueOf(di)) {
                    esreal = true;
                }
            } else if (txtCodigo.getText().length() == 13) {
                int digito = mp.verificadorCodBarrasGTIN14GTIN13(txtCodigo.getText());
                char dig = txtCodigo.getText().charAt(12);
                String di = String.valueOf(dig);
                if (digito == Integer.valueOf(di)) {
                    esreal = true;
                }
            } else if (txtCodigo.getText().length() == 8) {
                int digito = mp.verificadorCodBarrasGTIN8(txtCodigo.getText());
                char dig = txtCodigo.getText().charAt(7);
                String di = String.valueOf(dig);
                if (digito == Integer.valueOf(di)) {
                    esreal = true;
                }
            } else if (txtCodigo.getText().length() == 12) {
                int digito = mp.verificadorCodBarrasGTIN12(txtCodigo.getText());
                char dig = txtCodigo.getText().charAt(11);
                String di = String.valueOf(dig);
                if (digito == Integer.valueOf(di)) {
                    esreal = true;
                }
            }
            return esreal;
        } catch (Exception e) {
            return esreal;
        }
    }

    public void guardarProducto() {
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No se inserto un código de barras");
        } else if (verificarProductoReal() == false) {
            JOptionPane.showMessageDialog(rootPane, "El codigo de producto no pertenece a un producto real");
        } else if (verificarExistenciaProducto() == true) {
            JOptionPane.showMessageDialog(rootPane, "El codigo de producto ya se encuentra registrado");
        } else if (txtNombre.getText().length() < 6) {
            JOptionPane.showMessageDialog(rootPane, "No se agrego un nombre al producto");
        } else if (txtMarca.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No se agrego una marca al producto");
        } else if (txtPresentacion.getText().length() > 50) {
            JOptionPane.showMessageDialog(rootPane, "No se selecciono la presentación del producto");
        } else if (cbCategoria.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "No se eligio una categoría del producto");
        } else if (cbPresentacion.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Elija la unidad de presentación");
        } else if (txtPVPUnidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Señale el PVP unitario");
        } else if ((rbSi.isSelected() == false) && (rbNo.isSelected() == false)) {
            JOptionPane.showMessageDialog(rootPane, "Selecione una opción para asignación de IVA");
        } else {
            try {
                conexion cc = new conexion();
                Connection cn = cc.conectar();

                String COD_PRO, NOM_PRO, MAR_PRO, NOM_CAT_P, COD_UNI_PRES;
                float PVP, PRES_PRO, IVA_PRO;
                int STOCK_PRO;

                if (txtCodigo.getText().length() == 14) {
                    String GTIN13 = txtCodigo.getText().substring(1, 13);
                    int digito13 = mp.verificadorCodBarrasGTIN14GTIN13(GTIN13 + "0");
                    String codnew = GTIN13 + String.valueOf(digito13);
                    COD_PRO = codnew;
                } else {
                    COD_PRO = txtCodigo.getText();
                }

                if (rbSi.isSelected()) {
                    IVA_PRO = 14;
                } else {
                    IVA_PRO = 0;
                }

                if (txtStock.getText().isEmpty()) {
                    STOCK_PRO = 0;
                } else {
                    STOCK_PRO = Integer.valueOf(txtStock.getText());
                }

                NOM_PRO = txtNombre.getText();
                MAR_PRO = txtMarca.getText();
                NOM_CAT_P = cbCategoria.getSelectedItem().toString();
                PRES_PRO = Float.valueOf(txtPresentacion.getText());
                COD_UNI_PRES = cbPresentacion.getSelectedItem().toString();
                PVP = Float.valueOf(txtPVPUnidad.getText());

                String sql = "";
                sql = "INSERT INTO PRODUCTOS(COD_PRO,NOM_PRO,MAR_PRO,NOM_CAT_P,PRES_PRO,COD_UNI_PRES,PVP,IVA_PRO,STOCK_PRO)values (?,?,?,?,?,?,?,?,?)";

                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setString(1, COD_PRO);
                psd.setString(2, NOM_PRO);
                psd.setString(3, MAR_PRO);
                psd.setString(4, NOM_CAT_P);
                psd.setFloat(5, PRES_PRO);
                psd.setString(6, COD_UNI_PRES);
                psd.setFloat(7, PVP);
                psd.setFloat(8, IVA_PRO);
                psd.setInt(9, STOCK_PRO);

                int x = psd.executeUpdate();
                if (x > 0) {
                    JOptionPane.showMessageDialog(null, "Producto Insertado con éxito!");
                }
                cn.close();
                limpiarComponentes();
            } catch (SQLException ex) {
                Logger.getLogger(AdministrarProductos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void eliminarProducto() {
        int eleccion = JOptionPane.showOptionDialog(rootPane, "Desea eliminar el producto? ", "Mensaje de Confirmacion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, "Aceptar");
        if (eleccion == JOptionPane.YES_OPTION) {
            if (verificarExistenciaProducto() == false) {
                JOptionPane.showMessageDialog(this, "El producto ingresado no existe o es incorrecto");
            } else {

                try {
                    conexion cc = new conexion();
                    Connection cn = cc.conectar();
                    int n;
                    String sql = "";
                    String codigo;
                    if (txtCodigo.getText().length() == 14) {
                        codigo = mp.transformarGTIN14aGTIN13(txtCodigo.getText());
                    } else {
                        codigo = txtCodigo.getText();
                    }
                    sql = "DELETE FROM DETALLE_PROV_PRO WHERE COD_PRO_P = '" + codigo + "'";
                    PreparedStatement psd = cn.prepareStatement(sql);
                    n = psd.executeUpdate();

                    sql = "DELETE FROM PRODUCTOS WHERE COD_PRO = '" + codigo + "'";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    n = pst.executeUpdate();

                    if (n > 0) {
                        JOptionPane.showMessageDialog(rootPane, "Producto eliminado exitosamente!");
                        limpiarComponentes();
                    }
                    limpiarComponentes();
                } catch (SQLException ex) {
                    Logger.getLogger(AdministrarProductos.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void actualizarProducto() {
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No se inserto un código de barras");
        } else if (verificarProductoReal() == false) {
            JOptionPane.showMessageDialog(rootPane, "El código de producto no pertenece a un producto real");
        } else if (txtNombre.getText().length() < 6) {
            JOptionPane.showMessageDialog(rootPane, "No se agregó un nombre al producto");
        } else if (txtMarca.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No se agregó una marca al producto");
        } else if (txtPresentacion.getText().length() > 50) {
            JOptionPane.showMessageDialog(rootPane, "No se selecciono la presentación del producto");
        } else if (cbCategoria.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "No se eligió una categoría del producto");
        } else if (cbPresentacion.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Elija la unidad de presentación");
        } else if (txtPVPUnidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Señale el PVP unitario");
        } else if ((rbSi.isSelected() == false) && (rbNo.isSelected() == false)) {
            JOptionPane.showMessageDialog(rootPane, "Selecione una opción para asignación de IVA");
        } else {
            try {
                conexion cc = new conexion();
                Connection cn = cc.conectar();

                String COD_PRO, NOM_PRO, MAR_PRO, NOM_CAT_P, COD_UNI_PRES;
                float PVP, PRES_PRO, IVA_PRO;

                if (rbSi.isSelected()) {
                    IVA_PRO = 14;
                } else {
                    IVA_PRO = 0;
                }

                if (txtCodigo.getText().length() == 14) {
                    COD_PRO = mp.transformarGTIN14aGTIN13(txtCodigo.getText());
                } else {
                    COD_PRO = txtCodigo.getText();
                }

                NOM_PRO = txtNombre.getText();
                MAR_PRO = txtMarca.getText();
                NOM_CAT_P = cbCategoria.getSelectedItem().toString();
                PRES_PRO = Float.valueOf(txtPresentacion.getText());
                COD_UNI_PRES = cbPresentacion.getSelectedItem().toString();
                PVP = Float.valueOf(txtPVPUnidad.getText());

                String sql = "";
                sql = "UPDATE PRODUCTOS SET "
                        + "NOM_PRO = '" + NOM_PRO + "',"
                        + " MAR_PRO = '" + MAR_PRO + "',"
                        + " NOM_CAT_P = '" + NOM_CAT_P + "',"
                        + " PRES_PRO = " + PRES_PRO + ","
                        + "COD_UNI_PRES = '" + COD_UNI_PRES + "',"
                        + "PVP = " + PVP + ","
                        + "IVA_PRO = " + IVA_PRO
                        + " WHERE COD_PRO = '" + COD_PRO + "'";

                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Producto actualizado exitosamente!");
                }
                cn.close();
                limpiarComponentes();
            } catch (SQLException ex) {
                Logger.getLogger(AdministrarProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean verificarExistenciaProducto() {
        boolean existe = false;
        String codigo = "";
        try {
            String sql = "";
            if (txtCodigo.getText().length() == 14) {
                int digito = mp.verificadorCodBarrasGTIN14GTIN13(txtCodigo.getText());
                char dig = txtCodigo.getText().charAt(13);
                String di = String.valueOf(dig);

                if (digito == Integer.valueOf(di)) {
                    String GTIN13 = txtCodigo.getText().substring(1, 13);
                    int digito13 = mp.verificadorCodBarrasGTIN14GTIN13(GTIN13 + "0");
                    String codnew = GTIN13 + String.valueOf(digito13);
                    codigo = codnew;
                }
            } else if (txtCodigo.getText().length() == 13) {
                int digito = mp.verificadorCodBarrasGTIN14GTIN13(txtCodigo.getText());
                char dig = txtCodigo.getText().charAt(12);
                String di = String.valueOf(dig);
                if (digito == Integer.valueOf(di)) {
                    codigo = txtCodigo.getText();
                }
            } else if (txtCodigo.getText().length() == 12) {
                int digito = mp.verificadorCodBarrasGTIN12(txtCodigo.getText());
                char dig = txtCodigo.getText().charAt(11);
                String di = String.valueOf(dig);
                if (digito == Integer.valueOf(di)) {
                    codigo = txtCodigo.getText();
                }
            } else if (txtCodigo.getText().length() == 8) {
                int digito = mp.verificadorCodBarrasGTIN8(txtCodigo.getText());
                char dig = txtCodigo.getText().charAt(7);
                String di = String.valueOf(dig);
                if (digito == Integer.valueOf(di)) {
                    codigo = txtCodigo.getText();
                }
            }
            sql = "SELECT COD_PRO FROM PRODUCTOS";
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                String producto = rs.getString("COD_PRO");

                if (producto.equals(codigo)) {
                    existe = true;
                }
            }
            cn.close();
            return existe;
        } catch (Exception ex) {
            return existe;
        }
    }

    public void guardarProveedor() {
        if (verificarProveedor() == true) {
            JOptionPane.showMessageDialog(rootPane, "El proveedor señalado ya se encuentra en la lista de proveedores para este producto");
        } else if (txtPrecioUnitario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No se eligio un precio de proveedor");
        } else if (cbProveedor.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione el proveedor a asignar al producto");
        } else if (verificarExistenciaProducto() == false) {
            JOptionPane.showMessageDialog(rootPane, "El código de producto señalado es incorrecto");
            txtPrecioUnitario.setText("");
            cbProveedor.setSelectedIndex(0);
        } else {

            try {
                conexion cc = new conexion();
                Connection cn = cc.conectar();
                String COD_PRO_P, NOM_PROV_P;
                float PRECIO;

                if (txtCodigo.getText().length() == 14) {
                    String GTIN13 = txtCodigo.getText().substring(1, 13);
                    int digito13 = mp.verificadorCodBarrasGTIN14GTIN13(GTIN13 + "0");
                    String codnew = GTIN13 + String.valueOf(digito13);
                    COD_PRO_P = codnew;
                } else {
                    COD_PRO_P = txtCodigo.getText();
                }
                NOM_PROV_P = String.valueOf(cbProveedor.getSelectedItem());
                PRECIO = Float.valueOf(txtPrecioUnitario.getText());

                String sql = "";
                sql = "INSERT INTO DETALLE_PROV_PRO(COD_PRO_P ,NOM_PROV_P, PRECIO)values (?,?,?)";
                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setString(1, COD_PRO_P);
                psd.setString(2, NOM_PROV_P);
                psd.setFloat(3, PRECIO);

                int x = psd.executeUpdate();
                if (x > 0) {
                    JOptionPane.showMessageDialog(null, "Proveedor agregado exitosamente!");
                }
                cn.close();
                cargarTablaProveedor(txtCodigo.getText());
            } catch (SQLException ex) {
                Logger.getLogger(AdministrarProductos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void quitarProveedor() {
        if (verificarExistenciaProducto() == false) {
            JOptionPane.showMessageDialog(this, "El producto ingresado no existe o es incorrecto");
        } else if (tblProveedores.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "No se selecciono una proveedor de la tabla");
        } else {
            int eleccion = JOptionPane.showOptionDialog(rootPane, "Desea eliminar el proveedor para este producto? ", "Mensaje de Confirmacion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, "Aceptar");
            if (eleccion == JOptionPane.YES_OPTION) {

                try {
                    conexion cc = new conexion();
                    Connection cn = cc.conectar();
                    int n;
                    String sql = "";
                    String codigo;
                    if (txtCodigo.getText().length() == 14) {
                        codigo = mp.transformarGTIN14aGTIN13(txtCodigo.getText());
                    } else {
                        codigo = txtCodigo.getText();
                    }

                    String proveedor;
                    proveedor = String.valueOf(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 0));
                    sql = "DELETE FROM DETALLE_PROV_PRO WHERE COD_PRO_P = '" + codigo + "' AND NOM_PROV_P = '" + proveedor + "'";
                    PreparedStatement psd = cn.prepareStatement(sql);
                    n = psd.executeUpdate();
                    cargarTablaProveedor(codigo);
                } catch (SQLException ex) {
                    Logger.getLogger(AdministrarProductos.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void cargarTablaProveedor(String codigo) {

        try {
            String[] titulos = {"Proveedor", "Precio Producto"};
            String[] registros = new String[2];
            modTabla = new DefaultTableModel(null, titulos);
            tblProveedores.setModel(modTabla);

            String sql = "";
            sql = "SELECT * FROM DETALLE_PROV_PRO WHERE COD_PRO_P = '" + codigo + "'";

            conexion cc = new conexion();
            Connection cn = cc.conectar();
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);

            while (rs.next()) {
                registros[0] = rs.getString("NOM_PROV_P");
//                registros[1] =  rs.getString("PRECIO");
                registros[1] = String.valueOf(mp.redondearDecimales(rs.getDouble("PRECIO"), 2));
                modTabla.addRow(registros);
            }
            tblProveedores.setModel(modTabla);
            cn.close();
        } catch (Exception ex) {
        }
    }

    public boolean verificarProveedor() {
        boolean existe = false;
        try {
            DefaultComboBoxModel mod = new DefaultComboBoxModel();
            ArrayList<String> base = new ArrayList();
            String COD_PRO;

            if (txtCodigo.getText().length() == 14) {
                String GTIN13 = txtCodigo.getText().substring(1, 13);
                int digito13 = mp.verificadorCodBarrasGTIN14GTIN13(GTIN13 + "0");
                String codnew = GTIN13 + String.valueOf(digito13);
                COD_PRO = codnew;
            } else {
                COD_PRO = txtCodigo.getText();
            }

            String sql = "";
            sql = "SELECT NOM_PROV_P FROM DETALLE_PROV_PRO WHERE COD_PRO_P = '" + COD_PRO + "'";
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);

            while (rs.next()) {
                String nombre = rs.getString("NOM_PROV_P");
                base.add(nombre);
            }
            cn.close();

            //*****************************************************
            String proveedor = String.valueOf(cbProveedor.getSelectedItem());
            int i = 0;
            while (i < base.size()) {

                if (base.get(i).equals(proveedor)) {
                    existe = true;
                    i = base.size();
                }
                i++;
            }
            return existe;
        } catch (SQLException ex) {
            return existe;
            //Logger.getLogger(MetodosProyecto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarCamposProducto(String codigo) {

        try {
            DefaultComboBoxModel mod = new DefaultComboBoxModel();
            String sql = "";
            sql = "SELECT * FROM PRODUCTOS WHERE COD_PRO = '" + codigo + "'";
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);

            mod.addElement("");

            while (rs.next()) {
                txtNombre.setText(rs.getString("NOM_PRO"));
                txtMarca.setText(rs.getString("MAR_PRO"));
                cbCategoria.setSelectedItem(rs.getString("NOM_CAT_P"));
                cbPresentacion.setSelectedItem(rs.getString("COD_UNI_PRES"));
                txtPresentacion.setText(rs.getString("PRES_PRO"));
                txtStock.setText(rs.getString("STOCK_PRO"));

                txtPVPUnidad.setText(String.valueOf(mp.redondearDecimales(rs.getDouble("PVP"), 2)));
                if (rs.getString("IVA_PRO").equals("0")) {
                    rbNo.setSelected(true);
                } else {
                    rbSi.setSelected(true);
                }
            }
            cn.close();
            if (verificarExistenciaProducto() == true) {
                bloquearComponentes();
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdministrarProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarCategoria() {
        Categorias c = new Categorias();
        MenuVentas.escritorio.removeAll();
        MenuVentas.escritorio.add(c);
        c.jpEdicion.setVisible(false);
        c.lblCodigo.setText(txtCodigo.getText());
        c.lblCantidad.setText(txtCantidad.getText());
        c.lblCategoria.setText(cbCategoria.getSelectedItem().toString());
        c.lblMarca.setText(txtMarca.getText());
        c.lblNombre.setText(txtNombre.getText());
        c.lblPVP.setText(txtPVPUnidad.getText());
        c.lblPresentacion.setText(txtPresentacion.getText());
        c.lblUnidades.setText(cbPresentacion.getSelectedItem().toString());
        c.lblStock.setText(txtStock.getText());
        c.setVisible(true);
        this.dispose();
    }

    public void verificacionCodigos() {
        try {
            if (txtCodigo.getText().length() == 14) {
                int digito = mp.verificadorCodBarrasGTIN14GTIN13(txtCodigo.getText());
                char dig = txtCodigo.getText().charAt(13);
                String di = String.valueOf(dig);

                if (digito == Integer.valueOf(di)) {
                    String GTIN13 = txtCodigo.getText().substring(1, 13);
                    int digito13 = mp.verificadorCodBarrasGTIN14GTIN13(GTIN13 + "0");
                    String codnew = GTIN13 + String.valueOf(digito13);
                    cargarCamposProducto(codnew);

                    cargarTablaProveedor(txtCodigo.getText());
                    txtCodigo.setBackground(Color.green);

                    char primerdigito = txtCodigo.getText().charAt(0);
                    String primero = String.valueOf(primerdigito);
                    int prim = Integer.valueOf(primero);

                    if (prim < 9) {
                        int cant = prim * 12;
                        //txtCantidad.setText(String.valueOf(cant));
                        lblDe.setVisible(true);
                        lblCantidad.setText("Nº Cajas");
                        lblUnidades.setText(String.valueOf(cant));
                        lblUnidades.setVisible(true);
                        lblUnidadesFinal.setVisible(true);
                    } else {
                        lblCantidad.setText("Nº Unidades");
                    }
                } else {
                    txtCodigo.setBackground(Color.red);
                }
            } else if (txtCodigo.getText().length() == 13) {
                int digito = mp.verificadorCodBarrasGTIN14GTIN13(txtCodigo.getText());
                char dig = txtCodigo.getText().charAt(12);
                String di = String.valueOf(dig);
                if (digito == Integer.valueOf(di)) {
                    cargarCamposProducto(txtCodigo.getText());
                    cargarTablaProveedor(txtCodigo.getText());

                    txtCodigo.setBackground(Color.green);
                    lblCantidad.setText("Nº Unidades");
                } else {
                    txtCodigo.setBackground(Color.red);
                }
            } else if (txtCodigo.getText().length() == 8) {
                int digito = mp.verificadorCodBarrasGTIN8(txtCodigo.getText());
                char dig = txtCodigo.getText().charAt(7);
                String di = String.valueOf(dig);
                if (digito == Integer.valueOf(di)) {
                    cargarCamposProducto(txtCodigo.getText());
                    cargarTablaProveedor(txtCodigo.getText());
                    txtCodigo.setBackground(Color.green);
                    lblCantidad.setText("Nº Unidades");

                } else {
                    txtCodigo.setBackground(Color.red);
                }
            } else if (txtCodigo.getText().length() == 12) {
                int digito = mp.verificadorCodBarrasGTIN12(txtCodigo.getText());
                char dig = txtCodigo.getText().charAt(11);
                String di = String.valueOf(dig);
                if (digito == Integer.valueOf(di)) {
                    cargarCamposProducto(txtCodigo.getText());
                    cargarTablaProveedor(txtCodigo.getText());
                    txtCodigo.setBackground(Color.green);
                    lblCantidad.setText("Nº Unidades");

                } else {
                    txtCodigo.setBackground(Color.red);
                }
            }
        } catch (Exception e) {
        }
    }

    public void actualizarStock() {
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El código ingresado es incorrecto! ");
        } else if (verificarExistenciaProducto() == false) {
            JOptionPane.showMessageDialog(this, "El código señalado no existe");
        } else if (txtCantidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se señalo una cantidad correcta de stock a ingresar");
        } else {
            try {
                String codigo;
                if (txtCodigo.getText().length() == 14) {
                    String GTIN13 = txtCodigo.getText().substring(1, 13);
                    int digito13 = mp.verificadorCodBarrasGTIN14GTIN13(GTIN13 + "0");
                    String codnew = GTIN13 + String.valueOf(digito13);
                    codigo = codnew;
                } else {
                    codigo = txtCodigo.getText();
                }
                int stock;
                if (lblUnidades.isVisible()) {
                    stock = Integer.valueOf(txtCantidad.getText()) * Integer.valueOf(lblUnidades.getText());
                } else {
                    stock = Integer.valueOf(txtCantidad.getText());
                }
                String sql = "";

                sql = "SELECT STOCK_PRO FROM PRODUCTOS WHERE COD_PRO = '" + codigo + "'";
                conexion cc = new conexion();
                Connection cn = cc.conectar();
                Statement pst = cn.createStatement();
                ResultSet rs = pst.executeQuery(sql);
                String stockini = "";

                while (rs.next()) {
                    stockini = rs.getString("STOCK_PRO");
                }
                ////*************************************
                sql = "UPDATE PRODUCTOS SET STOCK_PRO = " + (stock + Integer.valueOf(stockini))
                        + " WHERE COD_PRO = '" + codigo + "'";

                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Stock actualizado correctamente");
                }
                cn.close();
                cargarCamposProducto(codigo);
                txtCantidad.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(AdministrarProductos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgBotonesIva = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        lblCantidad = new javax.swing.JLabel();
        lblDe = new javax.swing.JLabel();
        lblUnidades = new javax.swing.JLabel();
        lblUnidadesFinal = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnAñadirStock = new javax.swing.JButton();
        btnVerProductos = new javax.swing.JButton();
        jpDetalleProducto = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        cbCategoria = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPresentacion = new javax.swing.JTextField();
        cbPresentacion = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        btnCategoria = new javax.swing.JButton();
        jpPrecioProducto = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtPVPUnidad = new javax.swing.JTextField();
        rbSi = new javax.swing.JRadioButton();
        rbNo = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jpProveedores = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProveedores = new javax.swing.JTable();
        cbProveedor = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtPrecioUnitario = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnAgregar1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        ntmSalir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administrar Productos");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Código Producto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(112, 48, 160))); // NOI18N

        jLabel1.setText("Cod Prod :");

        txtCodigo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        lblCantidad.setForeground(new java.awt.Color(112, 48, 160));

        lblDe.setForeground(new java.awt.Color(112, 48, 160));
        lblDe.setText("de");

        lblUnidades.setForeground(new java.awt.Color(112, 48, 160));

        lblUnidadesFinal.setForeground(new java.awt.Color(112, 48, 160));
        lblUnidadesFinal.setText("Unidades");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/codigo.png"))); // NOI18N

        btnAñadirStock.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAñadirStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/almacen.png"))); // NOI18N
        btnAñadirStock.setText("Añadir ");
        btnAñadirStock.setToolTipText("Añadir unidades al stock");
        btnAñadirStock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAñadirStock.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        btnAñadirStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirStockActionPerformed(evt);
            }
        });

        btnVerProductos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnVerProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/VerProductos.png"))); // NOI18N
        btnVerProductos.setText("Ver Productos");
        btnVerProductos.setToolTipText("Ver o Elegir producto");
        btnVerProductos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVerProductos.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        btnVerProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerProductosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUnidadesFinal)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(btnAñadirStock, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVerProductos)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUnidadesFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAñadirStock, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnVerProductos)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jpDetalleProducto.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle Producto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(112, 48, 160))); // NOI18N

        jLabel2.setText("Nombre:");

        jLabel3.setText("Marca:");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarcaKeyTyped(evt);
            }
        });

        cbCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbCategoriaKeyTyped(evt);
            }
        });

        jLabel4.setText("Categoria:");

        jLabel5.setText("Presentación:");

        txtPresentacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPresentacionKeyTyped(evt);
            }
        });

        cbPresentacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbPresentacionKeyTyped(evt);
            }
        });

        jLabel14.setText("Stock:");

        txtStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockKeyTyped(evt);
            }
        });

        btnCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mas.png"))); // NOI18N
        btnCategoria.setToolTipText("Crear nueva categoría");
        btnCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpDetalleProductoLayout = new javax.swing.GroupLayout(jpDetalleProducto);
        jpDetalleProducto.setLayout(jpDetalleProductoLayout);
        jpDetalleProductoLayout.setHorizontalGroup(
            jpDetalleProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDetalleProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDetalleProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDetalleProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDetalleProductoLayout.createSequentialGroup()
                        .addGroup(jpDetalleProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDetalleProductoLayout.createSequentialGroup()
                                .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jpDetalleProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpDetalleProductoLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel14))
                            .addGroup(jpDetalleProductoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(12, 12, 12)
                        .addGroup(jpDetalleProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtStock)
                            .addGroup(jpDetalleProductoLayout.createSequentialGroup()
                                .addComponent(txtPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbPresentacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(1, 1, 1))))
                    .addComponent(txtNombre)))
        );
        jpDetalleProductoLayout.setVerticalGroup(
            jpDetalleProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDetalleProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDetalleProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDetalleProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDetalleProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCategoria)
                    .addGroup(jpDetalleProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel14)
                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jpPrecioProducto.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Precio Producto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(112, 48, 160))); // NOI18N

        jLabel11.setText("IVA:");

        jLabel12.setText("PVP Unitario:");

        txtPVPUnidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPVPUnidadKeyTyped(evt);
            }
        });

        bgBotonesIva.add(rbSi);
        rbSi.setText("14%");
        rbSi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rbSiKeyTyped(evt);
            }
        });

        bgBotonesIva.add(rbNo);
        rbNo.setText("0%");
        rbNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rbNoKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("$");

        javax.swing.GroupLayout jpPrecioProductoLayout = new javax.swing.GroupLayout(jpPrecioProducto);
        jpPrecioProducto.setLayout(jpPrecioProductoLayout);
        jpPrecioProductoLayout.setHorizontalGroup(
            jpPrecioProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrecioProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPVPUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbSi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbNo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpPrecioProductoLayout.setVerticalGroup(
            jpPrecioProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrecioProductoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpPrecioProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPVPUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(rbSi)
                    .addComponent(rbNo)
                    .addComponent(jLabel6)))
        );

        jpProveedores.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proveedores", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(112, 48, 160))); // NOI18N

        tblProveedores.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblProveedores.setForeground(new java.awt.Color(112, 48, 160));
        tblProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {}
            },
            new String [] {

            }
        ));
        tblProveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblProveedoresKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(tblProveedores);

        cbProveedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbProveedorItemStateChanged(evt);
            }
        });
        cbProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbProveedorKeyTyped(evt);
            }
        });

        jLabel7.setText("Elegir:");

        jLabel8.setText("Precio Unitario:");

        txtPrecioUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioUnitarioKeyTyped(evt);
            }
        });

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addproveedor.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnAgregar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/dropproveedor.png"))); // NOI18N
        btnAgregar1.setText("Quitar");
        btnAgregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpProveedoresLayout = new javax.swing.GroupLayout(jpProveedores);
        jpProveedores.setLayout(jpProveedoresLayout);
        jpProveedoresLayout.setHorizontalGroup(
            jpProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProveedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpProveedoresLayout.createSequentialGroup()
                        .addComponent(txtPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 148, Short.MAX_VALUE))
                    .addComponent(cbProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpProveedoresLayout.createSequentialGroup()
                .addComponent(btnAgregar1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregar))
        );
        jpProveedoresLayout.setVerticalGroup(
            jpProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpProveedoresLayout.createSequentialGroup()
                .addGroup(jpProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnAgregar1)))
        );

        jPanel7.setBackground(new java.awt.Color(153, 153, 255));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        ntmSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir.png"))); // NOI18N
        ntmSalir.setText("Salir");
        ntmSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ntmSalirActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("Guardar nuevo producto");
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setToolTipText("Eliminar producto existente");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setToolTipText("Actualizar informacion de producto");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setToolTipText("Activar edición");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ntmSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ntmSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpDetalleProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpPrecioProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpDetalleProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarComponentes();
        desbloquearComponentes();
        botonesInicio(false);
        jpDetalleProducto.setVisible(true);
        jpPrecioProducto.setVisible(true);
        jpProveedores.setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void ntmSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ntmSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_ntmSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarProducto();        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarProducto();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        actualizarProducto();        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnVerProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerProductosActionPerformed
        ListadoProductos lp = new ListadoProductos();
        MenuVentas.escritorio.removeAll();
        MenuVentas.escritorio.add(lp);
        lp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVerProductosActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        botonEditar();
    }//GEN-LAST:event_btnEditarActionPerformed

    //String codig = "";
    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }

        if ((((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_ENTER)) || (txtCodigo.getText().length() >= 14)) {
            evt.consume();
        } else {
            if (txtCodigo.getText().length() == 8 || txtCodigo.getText().length() == 12 || txtCodigo.getText().length() == 13 || txtCodigo.getText().length() == 14) {
                verificacionCodigos();
                activarProductoCuandoExiste();
                if ((c == KeyEvent.VK_ENTER) && txtCodigo.getBackground() == red) {
                    limpiarComponentes();
                } else {
                }
            } else if (txtCodigo.getText().length() > 8 && (txtCodigo.getBackground() == red)) {
                limpiarComponentes();
            } else {
            }
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        guardarProveedor();        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void cbProveedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbProveedorItemStateChanged
        txtPrecioUnitario.setText("");        // TODO add your handling code here:
    }//GEN-LAST:event_cbProveedorItemStateChanged

    private void txtPVPUnidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPVPUnidadKeyTyped

        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != '.')) {
            evt.consume();
        } else {
            if (c == '.' && txtPVPUnidad.getText().contains(".")) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtPVPUnidadKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char c;
        String temp;
        c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }
        temp = String.valueOf(c).toUpperCase();
        int x = temp.codePointAt(0);
        if ((!Character.isLetter(c) && (c != KeyEvent.VK_SPACE) || (txtNombre.getText().length() >= 30)) && (((c < '0') || (c > '9')))) {
            evt.consume();
        } else {
            if (c == KeyEvent.VK_SPACE) {
                if (txtNombre.getText().contains("_")) {
                    evt.consume();
                } else {
                    evt.setKeyChar('_');
                }

            } else {
                evt.setKeyChar(temp.charAt(0));
            }
        }
    }//GEN-LAST:event_txtNombreKeyTyped


    private void txtMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarcaKeyTyped
        char c;
        String temp;
        c = evt.getKeyChar();
        temp = String.valueOf(c).toUpperCase();
        int x = temp.codePointAt(0);
        if (!Character.isLetter(c) && (c != KeyEvent.VK_SPACE) || (txtMarca.getText().length() >= 30)) {
            evt.consume();
        } else {
            if (c == KeyEvent.VK_SPACE) {
                if (txtMarca.getText().contains("_")) {
                    evt.consume();
                } else {
                    evt.setKeyChar('_');
                }
            } else {
                evt.setKeyChar(temp.charAt(0));
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarcaKeyTyped

    private void txtPrecioUnitarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioUnitarioKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != '.')) {
            evt.consume();
        } else {
            if (c == '.' && txtPrecioUnitario.getText().contains(".")) {
                evt.consume();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioUnitarioKeyTyped

    private void txtPresentacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPresentacionKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != '.')) {
            evt.consume();
        } else {
            if (c == '.' && txtPresentacion.getText().contains(".")) {
                evt.consume();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtPresentacionKeyTyped

    private void txtStockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }

        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtStockKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        if (txtCodigo.getText().length() == 8 || txtCodigo.getText().length() == 12 || txtCodigo.getText().length() == 13 || txtCodigo.getText().length() == 14) {
            verificacionCodigos();
        } else {
            txtCodigo.setBackground(Color.white);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void btnAñadirStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirStockActionPerformed
        actualizarStock();       // TODO add your handling code here:
    }//GEN-LAST:event_btnAñadirStockActionPerformed

    private void btnAgregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar1ActionPerformed
        quitarProveedor();        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregar1ActionPerformed

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped

    }//GEN-LAST:event_formKeyTyped

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void cbProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbProveedorKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }
    }//GEN-LAST:event_cbProveedorKeyTyped

    private void cbPresentacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPresentacionKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cbPresentacionKeyTyped

    private void cbCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbCategoriaKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cbCategoriaKeyTyped

    private void tblProveedoresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProveedoresKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }
    }//GEN-LAST:event_tblProveedoresKeyTyped

    private void rbSiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbSiKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_rbSiKeyTyped

    private void rbNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbNoKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            limpiarComponentes();
            desbloquearComponentes();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_rbNoKeyTyped

    private void btnCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriaActionPerformed
        enviarCategoria();
    }//GEN-LAST:event_btnCategoriaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdministrarProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdministrarProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdministrarProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdministrarProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdministrarProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgBotonesIva;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregar1;
    private javax.swing.JButton btnAñadirStock;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCategoria;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnVerProductos;
    public javax.swing.JComboBox<String> cbCategoria;
    public javax.swing.JComboBox<String> cbPresentacion;
    public javax.swing.JComboBox<String> cbProveedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel jpDetalleProducto;
    private javax.swing.JPanel jpPrecioProducto;
    private javax.swing.JPanel jpProveedores;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblDe;
    private javax.swing.JLabel lblUnidades;
    private javax.swing.JLabel lblUnidadesFinal;
    private javax.swing.JButton ntmSalir;
    public javax.swing.JRadioButton rbNo;
    public javax.swing.JRadioButton rbSi;
    public javax.swing.JTable tblProveedores;
    public javax.swing.JTextField txtCantidad;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtMarca;
    public javax.swing.JTextField txtNombre;
    public javax.swing.JTextField txtPVPUnidad;
    private javax.swing.JTextField txtPrecioUnitario;
    public javax.swing.JTextField txtPresentacion;
    public javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
