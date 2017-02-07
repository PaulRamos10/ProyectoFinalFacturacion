/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VENTAS;

import static VENTAS.MenuVentas.escritorio;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import proyectodesarrollo.MetodosProyecto;
import proyectodesarrollo.VerificarCedula;
import proyectodesarrollo.conexion;

/**
 *
 * @author Daysi
 */
public class ventas extends javax.swing.JInternalFrame {

    DefaultTableModel mo;
    DefaultTableModel modelo;
    String[] com = {"SELECCIONAR", "FACTURA", "CONSUMIDOR FINAL"};
    DefaultTableModel model = new DefaultTableModel();
    static double total;
    double sub_total;
    double igv;
    double descueto;
    MetodosProyecto mp = new MetodosProyecto();
    DefaultTableModel mod;

    /**
     * Creates new form ventas
     */
    public ventas() {
        initComponents();

        DefaultComboBoxModel mod = new DefaultComboBoxModel(com);
        fecha();
        ChkComprobante.setModel(mod);
        bloquearBotones();
        seleccion();
        total = 0.0;
        sub_total = 0.0;
        igv = 0.0;
        campos_bloqueados_siempre();
        String[] titulos = {"Código", "Detalle", "Cantidad", "Precio", "Subtotal"};
        modelo = new DefaultTableModel(null, titulos);
        tblVenta.setModel(modelo);
        txtCodigo.setEditable(false);
    }

    public ventas(String nombre, String direccion, String cedula, String telefono) {
        initComponents();
        txtNombre_Apellido.setText(nombre);
        txtDireccion.setText(direccion);
        txtCliente_cedula.setText(cedula);
        txttelefono.setText(telefono);
    }

    static void cajeros(String var2) {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "select * from cajeros where CED_CAJ like '%" + var2 + "%' ";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            String nombre, cedula, apellido;
            while (rs.next()) {
                cedula = rs.getString("CED_CAJ");
                nombre = rs.getString("NOM_CAJ");
                apellido = rs.getString("APE_CAJ");
                txtCedula_Cajero.setText(cedula);
                txtNombre_Cajero.setText(nombre + " " + apellido);
            }
            cn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "El producto no existe");
        }
    }

    public void campos_bloqueados_siempre() {
        txtCliente_cedula.setEditable(false);
        txtNombre_Apellido.setEditable(false);
        txtDireccion.setEditable(false);
        txttelefono.setEditable(false);
        txtNumeroVenta.setEditable(false);
        txtSubtotal_Venta.setEditable(false);
        txtTotalDescuento.setEditable(false);
        txtTotalPagar.setEditable(false);
        txtIVA.setEditable(false);
        txtCedula_Cajero.setEditable(false);
        txtNombre_Cajero.setEditable(false);

    }

    public void Limpiar() {
        txtIVA.setText("");
        txtDineroRecibido1.setText("");
        txtTotalDescuento.setText("");
        txtTotalPagar.setText("");
        txtSubtotal_Venta.setText("");
        tblVenta.setEnabled(false);
        txtCodigo.setEditable(true);
    }

    private void Limpiar_Datos_Tabla_Ventas() {
        for (int i = 0; i < tblVenta.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }

    public void limpiar_Datos_cliente() {
        txtCliente_cedula.setText("");
        txtDireccion.setText("");
        txtNombre_Apellido.setText("");
        txttelefono.setText("");
    }

    public void ConsumidorFinal() {
        txtCliente_cedula.setText("0000000000");
        txtNombre_Apellido.setText("consumidor final");
        txtDireccion.setText("xxxxxxxxxxx");
        txttelefono.setText("0000000000");
    }

    public void fecha() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtFecha.setEnabled(false);
        txtFecha.setDate(date);
    }

    public void Comprobante() {
        if (ChkComprobante.getSelectedIndex() == 0) {
            limpiar_Datos_cliente();
        } else if (ChkComprobante.getSelectedIndex() == 1) {
            Agregar_Cliente_Venta n = new Agregar_Cliente_Venta();
            n.setVisible(true);
            limpiar_Datos_cliente();
        } else {
            ConsumidorFinal();
        }
    }

    public void bloquearBotones() {
        btnAgregarCliente.setEnabled(false);
        btnGenerarVenta.setEnabled(false);
        ChkComprobante.setEnabled(false);
        txtDescuento.setEditable(false);
        btnCancelarFactura.setEnabled(false);
    }

    public void desbloquearBotones() {
        btnCancelarFactura.setEnabled(true);
        btnAgregarCliente.setEnabled(true);
        btnGenerarVenta.setEnabled(true);
        ChkComprobante.setEnabled(true);
        txtDescuento.setEditable(true);
        btnNuevo.setEnabled(false);
    }

    public void nuevo() {
        seleccion();
        fecha();
        limpiar_Datos_cliente();
        Limpiar();
        desbloquearBotones();
        txtCodigo.setEditable(true);
        txtCodigo.setSelectionStart(0);
    }

    public void seleccion() {
        try {
            conexion cn = new conexion();
            Connection cc = cn.conectar();
            String sql = "SELECT MAX(ven_num)as s FROM venta";
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            String r;
            int rr;
            while (rs.next()) {
                if (rs.getString("s") == null) {
                    txtNumeroVenta.setText(String.valueOf(1));
                } else {
                    r = rs.getString("s");
                    rr = Integer.valueOf(r) + 1;
                    txtNumeroVenta.setText(String.valueOf(rr));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void detallefactura() {
        for (int i = 0; i < tblVenta.getRowCount(); i++) {
            conexion cn = new conexion();
            Connection cc = cn.conectar();
            String InsertarSQL = "insert into detalle_venta(VEN_CANT,VEN_PRO_COD,VEN_DESC,VEN_NUM)values(?,?,?,?)";
            Double numfac = Double.valueOf(txtNumeroVenta.getText());
            String codpro = tblVenta.getValueAt(i, 0).toString();
            String despro = tblVenta.getValueAt(i, 1).toString();
            double cantpro = Double.valueOf(tblVenta.getValueAt(i, 2).toString());
            try {
                PreparedStatement pst = cc.prepareStatement(InsertarSQL);
                pst.setDouble(1, cantpro);
                pst.setString(2, codpro);
                pst.setString(3, despro);
                pst.setDouble(4, numfac);
                pst.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(ventas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public void eliminar() {
        try {
            int filaS = tblVenta.getSelectedRow();
            if (filaS >= 0) {
                modelo.removeRow(filaS);
            }
        } catch (Exception e) {
        }
    }

    String comparar(String cod) {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String cant = "";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM productos WHERE cod_pro='" + cod + "'");
            while (rs.next()) {
                cant = rs.getString(9);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cant;
    }

    public void recalcular() {
        double precio = 0.0, cantidad = 0.0, imp = 0.0;
        String pre, can;
        for (int i = 0; i < tblVenta.getRowCount(); i++) {
            pre = tblVenta.getValueAt(i, 3).toString();
            can = tblVenta.getValueAt(i, 2).toString();
            precio = Double.parseDouble(pre);
            cantidad = Integer.parseInt(can);
            imp = precio * cantidad;
            tblVenta.setValueAt(Math.rint(imp * 100) / 100, i, 4);

        }
    }

    public void descontarstock(String codi, String can) {
        int des = Integer.parseInt(can);
        String cap = "";
        int desfinal;
        String consul = "SELECT * FROM productos WHERE  cod_pro='" + codi + "'";
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consul);
            while (rs.next()) {
                cap = rs.getString(9);
            }
        } catch (Exception e) {
        }
        desfinal = Integer.parseInt(cap) - des;
        String modi = "UPDATE productos SET Stock_pro='" + desfinal + "' WHERE cod_pro = '" + codi + "'";
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void factura() {
        conexion cn = new conexion();
        Connection cc = cn.conectar();
        String sql = "insert into venta (VEN_NUM,VEN_CAJ_CED, VEN_CLI_CED, VEN_DES, VEN_SUBT, VEN_TOT, VEN_IVA, VEN_EST, VEN_FEC_HOR)"
                + "values(?,?,?,?,?,?,?,?,?)";
        System.out.println(sql);
        try {
            PreparedStatement psd = cc.prepareStatement(sql);
            String anula = "ACTIVA";
            String fecha = new SimpleDateFormat("yyyy-MM-dd").format(txtFecha.getDate());

            psd.setDouble(1, Double.valueOf(txtNumeroVenta.getText()));

            psd.setString(2, txtCedula_Cajero.getText());
            psd.setString(3, txtCliente_cedula.getText());
            psd.setDouble(4, Double.valueOf(txtTotalDescuento.getText()));
            psd.setDouble(5, Double.valueOf(txtSubtotal_Venta.getText()));
            psd.setDouble(6, Double.valueOf(txtTotalPagar.getText()));
            psd.setDouble(7, Double.valueOf(txtIVA.getText()));
            psd.setString(8, anula);
            psd.setString(9, fecha);

            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(this,"se inserto correctamente");
                btnNuevo.setEnabled(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ventas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public double redondearDecimales(double valor, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valor;
        parteEntera = Math.floor(resultado);
        resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
        return resultado;
    }

    void calcular2() {
        double iva = 0.0;
        double total1 = 0.0;
        double totalPagar = 0.0;
        double sumatoria1 = 0.0;
        int cantidad;

        int totalrow = tblVenta.getRowCount();
        totalrow -= 1;
        for (int i = 0; i <= (totalrow); i++) {
            double sumatoria = Double.valueOf(String.valueOf(tblVenta.getValueAt(i, 4)));
            sumatoria1 += sumatoria;
        }
        double descuento = 0.0;

        String des = txtDescuento.getText();
        if (des.equals("") || Double.valueOf(des) < 0 || Double.valueOf(des) > 100) {
            JOptionPane.showMessageDialog(null, "debe ingresar algun valor al descuento o  el descuento no puede ser negativo o el descuento no puede ser superior al 100%");
            txtDescuento.requestFocus();
        } else {
            if (Double.valueOf(des) >= 0 && Double.valueOf(des) < 100) {
                descuento = sumatoria1 * (Double.valueOf(des) / 100);
                total1 = sumatoria1 - descuento;
            } else {
                descuento = 0;
                total1 = sumatoria1 - descuento;
            }
            iva = total1 * 0.14;

            totalPagar = total1 + iva;
            txtSubtotal_Venta.setText(String.valueOf(redondearDecimales(sumatoria1, 2)));
            txtTotalDescuento.setText(String.valueOf(redondearDecimales(descuento, 2)));
            txtTotalPagar.setText(String.valueOf(redondearDecimales(totalPagar, 2)));
            txtIVA.setText(String.valueOf(redondearDecimales(iva, 2)));
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

        jPanel9 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNumeroVenta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCedula_Cajero = new javax.swing.JTextField();
        txtNombre_Cajero = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        PanelCliente = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtNombre_Apellido = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txttelefono = new javax.swing.JTextField();
        txtCliente_cedula = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVenta = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSubtotal_Venta = new javax.swing.JTextField();
        txtTotalDescuento = new javax.swing.JTextField();
        txtIVA = new javax.swing.JTextField();
        txtTotalPagar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnGenerarVenta = new javax.swing.JButton();
        btnCancelarFactura = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnSalirVentas = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        btnAgregarCliente = new javax.swing.JButton();
        lblCajero = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ChkComprobante = new javax.swing.JComboBox();
        txtCodigo = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtDineroRecibido1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtVuelto1 = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo.jpg"))); // NOI18N

        jLabel1.setText("Nº DE VENTA");

        txtNumeroVenta.setEditable(false);

        jLabel3.setText("Cajero responsable");

        jLabel7.setText("Fecha Venta");

        txtCedula_Cajero.setEditable(false);

        txtNombre_Cajero.setEditable(false);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(153, 0, 153));
        jLabel17.setText("DE: DESARROLLO 1 GRUPO Nº4");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(153, 0, 153));
        jLabel18.setText("Edificio dos FISEI      Telf:789654");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(153, 0, 153));
        jLabel19.setText("AMBATO-ECUADOR");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 0, 153));
        jLabel20.setText("RUC:0504071150001");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumeroVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCedula_Cajero, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre_Cajero, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNumeroVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(txtFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtCedula_Cajero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNombre_Cajero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel26.setText("Cliente:");

        jLabel27.setText("Direccion:");

        jLabel28.setText("Telefono:");

        txtCliente_cedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliente_cedulaActionPerformed(evt);
            }
        });

        jLabel6.setText("Responsable:");

        tblVenta.setForeground(new java.awt.Color(112, 48, 160));
        tblVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblVentaKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tblVenta);

        jLabel11.setText("Subtotal");

        jLabel14.setText("IVA %14");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("TOTAL $");

        jLabel13.setText(" Total Descuento");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTotalDescuento, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSubtotal_Venta, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIVA)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addComponent(txtTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSubtotal_Venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtTotalDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(txtIVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Guardar.png"))); // NOI18N
        btnGenerarVenta.setText("Guardar");
        btnGenerarVenta.setToolTipText("Guardar");
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        btnCancelarFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        btnCancelarFactura.setText("Cancelar");
        btnCancelarFactura.setToolTipText("Cancelar Factura");
        btnCancelarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarFacturaActionPerformed(evt);
            }
        });

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/stock.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setToolTipText("Emitir Nueva Factura");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnSalirVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Salir.png"))); // NOI18N
        btnSalirVentas.setText("Salir ");
        btnSalirVentas.setToolTipText("Avandonar Ventas");
        btnSalirVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirVentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSalirVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelarFactura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGenerarVenta)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnCancelarFactura)
                    .addComponent(btnGenerarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAgregarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addproveedor.png"))); // NOI18N
        btnAgregarCliente.setText("Agregar");
        btnAgregarCliente.setToolTipText("Agregar  Nuevo Cliente");
        btnAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(btnAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel9.setText("Descuento %:");

        txtDescuento.setText("0");

        jLabel21.setText("Codigo Producto:");

        jLabel5.setText("Comprobante");

        ChkComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkComprobanteActionPerformed(evt);
            }
        });

        txtCodigo.setBackground(new java.awt.Color(153, 255, 0));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        jLabel8.setText("Recibido:");

        txtDineroRecibido1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDineroRecibido1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDineroRecibido1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDineroRecibido1KeyTyped(evt);
            }
        });

        jLabel22.setText("Cambio :");

        txtVuelto1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDineroRecibido1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVuelto1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDineroRecibido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(txtVuelto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelClienteLayout = new javax.swing.GroupLayout(PanelCliente);
        PanelCliente.setLayout(PanelClienteLayout);
        PanelClienteLayout.setHorizontalGroup(
            PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelClienteLayout.createSequentialGroup()
                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelClienteLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelClienteLayout.createSequentialGroup()
                                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(54, 54, 54)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(420, 420, 420)
                                .addComponent(lblCajero, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelClienteLayout.createSequentialGroup()
                                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelClienteLayout.createSequentialGroup()
                                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel21))
                                        .addGap(220, 220, 220))
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelClienteLayout.createSequentialGroup()
                                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(PanelClienteLayout.createSequentialGroup()
                                                .addComponent(jLabel26)
                                                .addGap(58, 58, 58))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelClienteLayout.createSequentialGroup()
                                                .addComponent(jLabel27)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNombre_Apellido)
                                            .addComponent(txtDireccion)
                                            .addComponent(ChkComprobante, 0, 208, Short.MAX_VALUE))))
                                .addGap(26, 26, 26)
                                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCliente_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PanelClienteLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminar)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        PanelClienteLayout.setVerticalGroup(
            PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelClienteLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelClienteLayout.createSequentialGroup()
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ChkComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCliente_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)))
                        .addGap(5, 5, 5)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre_Apellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelClienteLayout.createSequentialGroup()
                                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21)))
                            .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))))
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelClienteLayout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addComponent(lblCajero, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelClienteLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelClienteLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminar)
                                .addGap(22, 22, 22)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelClienteLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        VerificarCedula vf = new VerificarCedula();
        try {
            if ((txtCliente_cedula.getText().equals("")) || (txtSubtotal_Venta.getText().equals(""))) {
                JOptionPane.showMessageDialog(this, "No ingreso cliente,productos o realice operacion");

            } else {
                String capcod = "", capcan = "";
                for (int i = 0; i < ventas.tblVenta.getRowCount(); i++) {
                    capcod = ventas.tblVenta.getValueAt(i, 0).toString();
                    capcan = ventas.tblVenta.getValueAt(i, 2).toString();
                    descontarstock(capcod, capcan);
                }
                factura();
                detallefactura();
                Limpiar();
                bloquearBotones();
                limpiar_Datos_cliente();
                Limpiar_Datos_Tabla_Ventas();
                DefaultTableModel modelo = (DefaultTableModel) tblVenta.getModel();
                int a = tblVenta.getRowCount() - 1;
                int i;
                for (i = a; i >= 0; i--) {
                    modelo.removeRow(i);
                }
                btnNuevo.setEnabled(false);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    public boolean verificarExistenciaProducto() {
        boolean existe = false;
        String codigo = "";
        try {
            String sql = "";
            if (txtCodigo.getText().length() == 14) {
                String GTIN13 = txtCodigo.getText().substring(1, 13);
                int digito13 = mp.verificadorCodBarrasGTIN14GTIN13(GTIN13 + "0");
                String codnew = GTIN13 + String.valueOf(digito13);
                codigo = codnew;
            } else if (txtCodigo.getText().length() == 13) {
                codigo = txtCodigo.getText();
            } else if (txtCodigo.getText().length() == 12) {
                codigo = txtCodigo.getText();
            } else if (txtCodigo.getText().length() == 8) {
                codigo = txtCodigo.getText();
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

    public int getStock(String codigo) {
        String cod = "";
        String stock = "";
        int stocknuevo = 0;
        try {
            if (codigo.length() == 14) {
                String GTIN13 = codigo.substring(1, 13);
                int digito13 = mp.verificadorCodBarrasGTIN14GTIN13(GTIN13 + "0");
                String codnew = GTIN13 + String.valueOf(digito13);
                cod = codnew;
            } else if (codigo.length() == 13) {
                cod = codigo;
            } else if (codigo.length() == 12) {
                cod = codigo;
            } else if (codigo.length() == 8) {
                cod = codigo;
            }
            String sql = "";
            sql = "SELECT STOCK_PRO FROM PRODUCTOS WHERE COD_PRO = '" + cod + "'";
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                stock = rs.getString("STOCK_PRO");
            }
            stocknuevo = Integer.valueOf(stock);
            cn.close();
            return stocknuevo;
        } catch (Exception ex) {
            return stocknuevo;
        }
    }

    void calcularSubtotalTotalIva() {
        try {
            double iva = 0.0;
            double total1 = 0.0;
            double totalPagar = 0.0;
            double sumatoria1 = 0.0;
            int cantidad;
            int totalrow = tblVenta.getRowCount();
            totalrow -= 1;
            for (int i = 0; i <= (totalrow); i++) {
                double sumatoria = Double.valueOf(String.valueOf(tblVenta.getValueAt(i, 4)));
                sumatoria1 += sumatoria;
            }
            double descuento = 0.0;

            String des = txtDescuento.getText();
            if (des.equals("") || Double.valueOf(des) < 0 || Double.valueOf(des) > 100) {
                JOptionPane.showMessageDialog(null, "debe ingresar algun valor al descuento o  el descuento no puede ser negativo o el descuento no puede ser superior al 100%");
                txtDescuento.requestFocus();
            } else {
                if (Double.valueOf(des) >= 0 && Double.valueOf(des) < 100) {
                    descuento = sumatoria1 * (Double.valueOf(des) / 100);
                    total1 = sumatoria1 - descuento;
                } else {
                    descuento = 0;
                    total1 = sumatoria1 - descuento;
                }
                iva = total1 * 0.14;

                totalPagar = total1 + iva;
                txtSubtotal_Venta.setText(String.valueOf(mp.redondearDecimales(sumatoria1, 2)));
                txtTotalDescuento.setText(String.valueOf(mp.redondearDecimales(descuento, 2)));
                txtTotalPagar.setText(String.valueOf(mp.redondearDecimales(totalPagar, 2)));
                txtIVA.setText(String.valueOf(mp.redondearDecimales(iva, 2)));
            }
        } catch (Exception e) {
        }
    }

    public boolean verificarExistenciaProductoTabla(String codigo) {
        String cod = "";
        if (txtCodigo.getText().length() == 14) {
            String GTIN13 = txtCodigo.getText().substring(1, 13);
            int digito13 = mp.verificadorCodBarrasGTIN14GTIN13(GTIN13 + "0");
            String codnew = GTIN13 + String.valueOf(digito13);
            cod = codnew;
        } else if (codigo.length() == 13) {
            cod = codigo;
        } else if (txtCodigo.getText().length() == 12) {
            cod = codigo;
        } else if (txtCodigo.getText().length() == 8) {
            cod = codigo;
        }
        boolean existe = false;
        int k = 0;
        int fila = tblVenta.getRowCount();
        while (k < fila) {
            String codigotabla = String.valueOf(tblVenta.getValueAt(k, 0));
            if (cod.equals(codigotabla)) {
                existe = true;
            }
            k++;
        }
        return existe;
    }

    public boolean verificarExistenciaProductoEnBase() {
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

    public void CargarProductoEnTabla() {
        if (verificarExistenciaProductoEnBase() == true) {
            try {
                String cantidad = JOptionPane.showInputDialog("Numero de productos");
                int cant;
                try {
                    cant = Integer.valueOf(cantidad);
                    if (cant < 0) {
                        cant = 0;
                    }
                } catch (Exception e) {
                    cant = 0;
                }
                String[] registros = new String[5];
                String cod = "";
                if (txtCodigo.getText().length() == 14) {
                    String GTIN13 = txtCodigo.getText().substring(1, 13);
                    int digito13 = mp.verificadorCodBarrasGTIN14GTIN13(GTIN13 + "0");
                    String codnew = GTIN13 + String.valueOf(digito13);
                    cod = codnew;
                } else if (txtCodigo.getText().length() == 13) {
                    cod = txtCodigo.getText();
                } else if (txtCodigo.getText().length() == 12) {
                    cod = txtCodigo.getText();
                } else if (txtCodigo.getText().length() == 8) {
                    cod = txtCodigo.getText();
                }

                if (cant != 0) {
                    String sql = "";
                    sql = "SELECT * FROM PRODUCTOS WHERE COD_PRO = '" + cod + "'";
                    conexion cc = new conexion();
                    Connection cn = cc.conectar();
                    Statement psd = cn.createStatement();
                    ResultSet rs = psd.executeQuery(sql);
                    while (rs.next()) {
                        registros[0] = rs.getString("COD_PRO");
                        registros[1] = rs.getString("NOM_PRO");
                        registros[3] = String.valueOf(mp.redondearDecimales(rs.getDouble("PVP"), 2));
                    }
                    cn.close();
                    int k = 0;
                    int fila = tblVenta.getRowCount();
                    
                    while (k < fila) {
                        String codigotabla = String.valueOf(tblVenta.getValueAt(k, 0));
                        if (cod.equals(codigotabla)) {
                            int cantnew = Integer.valueOf(String.valueOf(tblVenta.getValueAt(k, 2)));
                            cant = cant + cantnew;
                            modelo.removeRow(k);
                        }
                        k++;
                    }
                    int stock = getStock(cod);
                    if (cant > stock) {
                        cant = stock;
                        JOptionPane.showMessageDialog(this, "El stock es menor a la cantidad solicitada se asignara solo el stock disponible");
                    }
                    registros[2] = String.valueOf(cant);
                    registros[4] = String.valueOf(mp.redondearDecimales((cant * Float.valueOf(registros[3])), 2));

                    modelo.addRow(registros);
                    tblVenta.setModel(modelo);
                    txtCodigo.setText("");
                    calcularSubtotalTotalIva();
                } else {
                    txtCodigo.setText("");
                }

            } catch (SQLException ex) {
            }
        }
    }

    public void calcularVuelto() {
        if (!txtDineroRecibido1.getText().isEmpty()) {
            try {
                if (Double.valueOf(txtDineroRecibido1.getText()) >= Double.valueOf(txtTotalPagar.getText())) {
                    double dinero = Double.valueOf(txtDineroRecibido1.getText());
                    double calculo = dinero - Float.valueOf(txtTotalPagar.getText());
                    double calculotrunc = mp.redondearDecimales(calculo, 2);
                    txtVuelto1.setText("");
                    txtVuelto1.setBackground(Color.green);
                    txtVuelto1.setText(String.valueOf(calculotrunc));
                } else {
                    txtVuelto1.setText("");
                    txtVuelto1.setBackground(Color.white);
                }
            } catch (Exception e) {
            }
        } else {
            txtVuelto1.setText("");
            txtVuelto1.setBackground(Color.white);
        }
    }

    private void ChkComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkComprobanteActionPerformed
        Comprobante();        // TODO add your handling code here:
    }//GEN-LAST:event_ChkComprobanteActionPerformed
    public int ventana;
    private void btnAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarClienteActionPerformed
        if (ventana == 0) {
            IngresoClinetesFactura nuevo = new IngresoClinetesFactura();
            escritorio.add(nuevo);
            nuevo.setVisible(true);

            ventana = 1;
        }// TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarClienteActionPerformed

    private void btnSalirVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirVentasActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirVentasActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevo();      // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarFacturaActionPerformed
        Limpiar();
        bloquearBotones();
        limpiar_Datos_cliente();
        seleccion();
        Limpiar_Datos_Tabla_Ventas();
        btnNuevo.setEnabled(true);
    }//GEN-LAST:event_btnCancelarFacturaActionPerformed

    private void txtCliente_cedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliente_cedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliente_cedulaActionPerformed


    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ESCAPE) {
            txtCodigo.setText("");
            btnNuevo.setEnabled(true);
            Limpiar();
            txtCodigo.setSelectionStart(0);
            txtCodigo.setText("");
        }

        if ((((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_ENTER)) || (txtCodigo.getText().length() >= 14)) {
            evt.consume();
        } else if ((txtCodigo.getText().length() > 8)) {//&& (c == KeyEvent.VK_ENTER)
            CargarProductoEnTabla();
            //            if (verificarExistenciaProducto() == true) {
            //                CargarProductoEnTabla();
            //            }
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtDineroRecibido1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDineroRecibido1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDineroRecibido1KeyPressed

    private void txtDineroRecibido1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDineroRecibido1KeyReleased
        calcularVuelto();        // TODO add your handling code here:
    }//GEN-LAST:event_txtDineroRecibido1KeyReleased

    private void txtDineroRecibido1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDineroRecibido1KeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != '.')) {
            evt.consume();
        } else {
            if (c == '.' && txtDineroRecibido1.getText().contains(".")) {
                evt.consume();
            } else {
                calcularVuelto();
            }
        }       // TODO add your handling code here:
    }//GEN-LAST:event_txtDineroRecibido1KeyTyped

    private void tblVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVentaKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_DELETE) {
            eliminar();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tblVentaKeyTyped

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox ChkComprobante;
    private javax.swing.JPanel PanelCliente;
    private javax.swing.JButton btnAgregarCliente;
    private javax.swing.JButton btnCancelarFactura;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalirVentas;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCajero;
    public static javax.swing.JTable tblVenta;
    public static javax.swing.JTextField txtCedula_Cajero;
    public static javax.swing.JTextField txtCliente_cedula;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtDineroRecibido1;
    public static javax.swing.JTextField txtDireccion;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtIVA;
    public static javax.swing.JTextField txtNombre_Apellido;
    public static javax.swing.JTextField txtNombre_Cajero;
    private javax.swing.JTextField txtNumeroVenta;
    private javax.swing.JTextField txtSubtotal_Venta;
    private javax.swing.JTextField txtTotalDescuento;
    private javax.swing.JTextField txtTotalPagar;
    private javax.swing.JTextField txtVuelto1;
    public static javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables
}
