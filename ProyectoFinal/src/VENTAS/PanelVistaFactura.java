/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VENTAS;

import static VENTAS.MenuVentas.escritorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyectodesarrollo.conexion;
import static VENTAS.ventas.tblVenta;

/**
 *
 * @author Daysi
 */
public class PanelVistaFactura extends javax.swing.JInternalFrame {

    private JOptionPane op;
    DefaultTableModel ModeloTablaFactura;

    /**
     * Creates new form PanelVistaFactura
     */
    public PanelVistaFactura() {
        initComponents();
        cargartodasfacturas();
        this.setLocation(25, 15);
        jDateChooser1.setEnabled(false);
    }

    static void cargartodasfacturas() {
        DefaultTableModel tabla = new DefaultTableModel();
        String[] titulos = {"NUMERO", "FECHA EMISION", "CAJERO", "CLIENTE", "DESCUENTO", "SUBTOTAL", "IVA", "TOTAL", "ESTADO"};
        tabla.setColumnIdentifiers(titulos);
        tblFacturas.setModel(tabla);
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String consulta = "SELECT * FROM venta";
        String[] Datos = new String[9];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                Datos[0] = rs.getString("VEN_NUM");
                Datos[1] = rs.getString("VEN_FEC_HOR");
                Datos[2] = rs.getString("VEN_CAJ_CED");
                Datos[3] = rs.getString("VEN_CLI_CED");
                Datos[4] = rs.getString("VEN_DES");
                Datos[5] = rs.getString("VEN_SUBT");
                Datos[6] = rs.getString("VEN_TOT");
                Datos[7] = rs.getString("VEN_IVA");
                Datos[8] = rs.getString("VEN_EST");

                tabla.addRow(Datos);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    /**
     * /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        Eliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtnumero = new javax.swing.JTextField();
        btnbuscador = new javax.swing.JButton();
        rdbnnumero = new javax.swing.JRadioButton();
        rdbbnfecha = new javax.swing.JRadioButton();
        rdbntodos = new javax.swing.JRadioButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFacturas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        Eliminar.setText("Anular Factura");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });
        jPopupMenu2.add(Eliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Buscar por:");

        btnbuscador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar.png"))); // NOI18N
        btnbuscador.setToolTipText("Buscar en Registro");
        btnbuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscadorActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbnnumero);
        rdbnnumero.setText("Nº Factura");
        rdbnnumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbnnumeroActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbbnfecha);
        rdbbnfecha.setText("Fecha");
        rdbbnfecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbbnfechaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbntodos);
        rdbntodos.setText("Mostrar Todos");
        rdbntodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbntodosActionPerformed(evt);
            }
        });

        tblFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblFacturas.setComponentPopupMenu(jPopupMenu2);
        jScrollPane1.setViewportView(tblFacturas);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir.png"))); // NOI18N
        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnumero))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdbnnumero, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdbbnfecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdbntodos, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnbuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 10, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnbuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdbnnumero)
                        .addComponent(rdbbnfecha))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(rdbntodos)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdbnnumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbnnumeroActionPerformed
        if (rdbnnumero.isSelected() == true) {
            txtnumero.setEnabled(true);
            txtnumero.requestFocus();
            jDateChooser1.setDate(null);
            jDateChooser1.setEnabled(false);
        }
    }//GEN-LAST:event_rdbnnumeroActionPerformed

    private void rdbbnfechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbbnfechaActionPerformed
        if (rdbbnfecha.isSelected() == true) {
            jDateChooser1.setEnabled(true);
            txtnumero.setEnabled(false);
            txtnumero.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_rdbbnfechaActionPerformed

    private void rdbntodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbntodosActionPerformed
        if (rdbntodos.isSelected() == true) {
            jDateChooser1.setEnabled(false);
            jDateChooser1.setDate(null);
            txtnumero.setText("");
            txtnumero.setEnabled(false);
            cargartodasfacturas();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rdbntodosActionPerformed

    private void btnbuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscadorActionPerformed
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String nu = txtnumero.getText();
        Double num = Double.valueOf(nu);

        String consulta = "";
        if (rdbnnumero.isSelected() == true) {
            consulta = "SELECT * FROM VENTA WHERE VEN_NUM = " + num;
        }
        if (rdbbnfecha.isSelected() == true) {
            Date fecha = jDateChooser1.getDate();
            SimpleDateFormat formatofecha = new SimpleDateFormat("dd/MM/yyyy");
            String fec = formatofecha.format(fecha);
            consulta = "SELECT * FROM VENTA WHERE VEN_FEC_HOR='" + fec + "'";
        }
        if (rdbntodos.isSelected() == true) {
            consulta = "SELECT * FROM venta ";
        }
        DefaultTableModel tabla = new DefaultTableModel();
        String[] titulos = {"NUMERO", "FECHA EMISION", "CAJERO", "CLIENTE", "DESCUENTO", "SUBTOTAL", "TOTAL", "IVA", "ESTADO"};
        tabla.setColumnIdentifiers(titulos);
        this.tblFacturas.setModel(tabla);
        String[] Datos = new String[9];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                Datos[0] = rs.getString("VEN_NUM");
                Datos[1] = rs.getString("VEN_FEC_HOR");
                Datos[2] = rs.getString("VEN_CAJ_CED");
                Datos[3] = rs.getString("VEN_CLI_CED");
                Datos[4] = rs.getString("VEN_DES");
                Datos[5] = rs.getString("VEN_SUBT");
                Datos[6] = rs.getString("VEN_TOT");
                Datos[7] = rs.getString("VEN_IVA");
                Datos[8] = rs.getString("VEN_EST");
                tabla.addRow(Datos);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }//GEN-LAST:event_btnbuscadorActionPerformed
    public void eliminarFactura() {
        int filasele = tblFacturas.getSelectedRow();
        conexion cn = new conexion();
        Connection cc = cn.conectar();
        if (filasele == -1) {
            JOptionPane.showMessageDialog(null, "No Seleciono ninguna fila");
        } else {
            Detalle_Factura detalle = new Detalle_Factura();
            escritorio.add(detalle);
            detalle.toFront();
            detalle.setVisible(true);
            String numfac = tblFacturas.getValueAt(filasele, 0).toString();
            String fecha = tblFacturas.getValueAt(filasele, 1).toString();
            String cajero = tblFacturas.getValueAt(filasele, 2).toString();
            String cliente = tblFacturas.getValueAt(filasele, 3).toString();
            String descuento = tblFacturas.getValueAt(filasele, 4).toString();
            String subtotal = tblFacturas.getValueAt(filasele, 5).toString();
            String total = tblFacturas.getValueAt(filasele, 6).toString();
            String iva = tblFacturas.getValueAt(filasele, 7).toString();
            Detalle_Factura.txtNumero_Venta.setText(numfac);
            Detalle_Factura.txtFecha.setText(fecha);
            Detalle_Factura.txtCajero.setText(cajero);
            Detalle_Factura.txtCliente.setText(cliente);
            Detalle_Factura.txtDescuento.setText(descuento);
            Detalle_Factura.txtSubtotal.setText(subtotal);
            Detalle_Factura.txtTotal.setText(total);
            Detalle_Factura.txtIva.setText(iva);

            DefaultTableModel model = (DefaultTableModel) Detalle_Factura.tblDetalle_Factura.getModel();
            String ver = "SELECT * FROM detalle_venta WHERE VEN_NUM='" + numfac + "'";
            String[] datos = new String[5];
            try {
                Statement st = cc.createStatement();
                ResultSet rs = st.executeQuery(ver);
                while (rs.next()) {
                    datos[0] = rs.getString("VEN_CANT");
                    datos[1] = rs.getString("VEN_PRO_COD");
                    datos[2] = rs.getString("VEN_DESC");
                    datos[3] = rs.getString("VEN_NUM");
                    model.addRow(datos);
                }
                Detalle_Factura.tblDetalle_Factura.setModel(model);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "se inserto correctamente");
            }
        }
    }
    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        eliminarFactura();// TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_EliminarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(PanelVistaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PanelVistaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PanelVistaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PanelVistaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PanelVistaFactura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JButton btnbuscador;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdbbnfecha;
    private javax.swing.JRadioButton rdbnnumero;
    private javax.swing.JRadioButton rdbntodos;
    public static javax.swing.JTable tblFacturas;
    private javax.swing.JTextField txtnumero;
    // End of variables declaration//GEN-END:variables
}
