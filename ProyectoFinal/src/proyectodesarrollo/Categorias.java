/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectodesarrollo;

import VENTAS.MenuVentas;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Esparqui
 */
public class Categorias extends javax.swing.JInternalFrame {

    DefaultTableModel mod;

    /**
     * Creates new form Categorias
     */
    public Categorias() {
        initComponents();
        cargarTablaAlquiler();
        btnEliminar.setEnabled(false);
        bloquearEtiquetas();
    }

    public void bloquearEtiquetas(){
    lblCantidad.setVisible(false);
    lblCategoria.setVisible(false);
    lblCodigo.setVisible(false);
    lblMarca.setVisible(false);
    lblNombre.setVisible(false);
    lblPVP.setVisible(false);
    lblPresentacion.setVisible(false);
    lblStock.setVisible(false);
    lblUnidades.setVisible(false);
    }
    public void limpiarComponentes() {
        txtDetalle.setText("");
        txtNombreCategoria.setText("");
        txtDetalle.setEditable(true);
        txtNombreCategoria.setEditable(true);
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(true);
    }

    public void bloquearComponentes() {
        txtDetalle.setEditable(false);
        txtNombreCategoria.setEditable(false);
        btnGuardar.setEnabled(false);
    }

    public void clickTabla() {
        cargarCampos();
        btnEliminar.setEnabled(true);
        btnGuardar.setEnabled(false);
        txtDetalle.setEditable(false);
        txtNombreCategoria.setEditable(false);

    }

    public void guardarCategoria() {
        if (verificarExisteCategoria(txtNombreCategoria.getText()) == true) {
            JOptionPane.showMessageDialog(rootPane, "El nombre de categoría de producto ya existe");
        } else if (txtNombreCategoria.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No se señalo una categoría de producto");
        } else if (txtDetalle.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No se señalo un detalle para la categoría");

        } else {
            try {
                conexion cc = new conexion();
                Connection cn = cc.conectar();

                String NOM_CAT, DESC_CAT;

                DESC_CAT = txtDetalle.getText();
                NOM_CAT = txtNombreCategoria.getText();

                String sql = "";
                sql = "insert into CATEGORIAS (NOM_CAT, DESC_CAT) values (?,?)";

                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setString(1, NOM_CAT);
                psd.setString(2, DESC_CAT);

                int x = psd.executeUpdate();

                if (x > 0) {
                    JOptionPane.showMessageDialog(null, "Categoría insertada con éxito!");
                }
                cn.close();
                limpiarComponentes();
                cargarTablaAlquiler();
            } catch (SQLException ex) {
                Logger.getLogger(AdministrarProductos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public boolean verificarExisteCategoria(String nombrecat) {
        boolean existe = false;
        try {
            String sql = "";
            sql = "SELECT NOM_CAT FROM CATEGORIAS";
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);

            while (rs.next()) {
                String nombre = rs.getString("NOM_CAT");
                if (nombre.equals(nombrecat)) {
                    existe = true;
                }
            }
            cn.close();
            return existe;
        } catch (SQLException ex) {
            return existe;
        }

    }

    public void eliminarCategoria() {
        if (txtNombreCategoria.getText().equals("DEFAULT")) {
            JOptionPane.showMessageDialog(this, "La categoria default no puede ser eliminada");
        } else {
            if (tblCategorias.getSelectedRow() != (-1)) {
                int eleccion = JOptionPane.showOptionDialog(rootPane, "Desea eliminar la categoria de producto? ", "Mensaje de Confirmacion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, "Aceptar");
                if (eleccion == JOptionPane.YES_OPTION) {
                    try {
                        conexion cc = new conexion();
                        Connection cn = cc.conectar();

                        //**************************************
                        String sql = "";
                        sql = "UPDATE PRODUCTOS SET "
                                + " NOM_CAT_P = '" + "DEFAULT" + "' WHERE NOM_CAT_P = '" + txtNombreCategoria.getText() + "'";

                        PreparedStatement psd = cn.prepareStatement(sql);
                        int n = psd.executeUpdate();
                        if (n > 0) {
                        }
                        //**************************************

                        sql = "DELETE FROM CATEGORIAS WHERE NOM_CAT = '" + txtNombreCategoria.getText() + "'";
                        PreparedStatement pst = cn.prepareStatement(sql);
                        n = pst.executeUpdate();
                        if (n > 0) {
                            JOptionPane.showMessageDialog(rootPane, "Producto eliminado exitosamente!");
                            limpiarComponentes();
                            cargarTablaAlquiler();
                        }
                    } catch (SQLException ex) {
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se seleccionó una columna para su eliminación!");
            }

        }

    }

    public void cargarTablaAlquiler() {
        try {
            String[] titulos = {"Nombre", "Descripción"};
            String[] registros = new String[2];
            mod = new DefaultTableModel(null, titulos);
            tblCategorias.setModel(mod);
            String sql = "";
            sql = "SELECT * FROM CATEGORIAS";

            conexion cc = new conexion();
            Connection cn = cc.conectar();
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("NOM_CAT").trim();
                registros[1] = rs.getString("DESC_CAT");
                mod.addRow(registros);
            }
            tblCategorias.setModel(mod);
            cn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "si salto la excepcion" + ex);
        }
    }

    public void cargarCampos() {
        tblCategorias.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tblCategorias.getSelectedRow() != -1) {
                    int fila = tblCategorias.getSelectedRow();
                    txtNombreCategoria.setText(tblCategorias.getValueAt(fila, 0).toString());
                    txtDetalle.setText(tblCategorias.getValueAt(fila, 1).toString());
                }
            }
        });
    }

    public void enviarProductos() {
        AdministrarProductos c = new AdministrarProductos();
        MenuVentas.escritorio.removeAll();
        MenuVentas.escritorio.add(c);
        c.txtCodigo.setText(lblCodigo.getText());
        c.txtCantidad.setText(lblCantidad.getText());
        c.cbCategoria.setSelectedItem(lblCategoria.getText());
        c.txtMarca.setText(lblMarca.getText());
        c.txtNombre.setText(lblNombre.getText());
        c.txtPVPUnidad.setText(lblPVP.getText());
        c.txtPresentacion.setText(lblPresentacion.getText());
        c.cbPresentacion.setSelectedItem(lblUnidades.getText());
        c.txtStock.setText(lblStock.getText());
        c.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombreCategoria = new javax.swing.JTextField();
        txtDetalle = new javax.swing.JTextField();
        jpEdicion = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCategorias = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jpAñadirCategoria = new javax.swing.JPanel();
        btnSalir1 = new javax.swing.JButton();
        btnGuadar1 = new javax.swing.JButton();
        lblCodigo = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblMarca = new javax.swing.JLabel();
        lblCategoria = new javax.swing.JLabel();
        lblPresentacion = new javax.swing.JLabel();
        lblUnidades = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        lblPVP = new javax.swing.JLabel();

        jLabel11.setText("jLabel11");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administrar Categorias");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(112, 48, 160));
        jLabel1.setText("Nombre Categoria:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(112, 48, 160));
        jLabel2.setText("Detalle:");

        txtNombreCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreCategoriaActionPerformed(evt);
            }
        });
        txtNombreCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreCategoriaKeyTyped(evt);
            }
        });

        txtDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDetalleKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNombreCategoria)
                    .addComponent(txtDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombreCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblCategorias.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblCategorias.setForeground(new java.awt.Color(112, 48, 160));
        tblCategorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblCategorias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCategoriasMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCategoriasMousePressed(evt);
            }
        });
        tblCategorias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblCategoriasKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblCategoriasKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tblCategorias);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpEdicionLayout = new javax.swing.GroupLayout(jpEdicion);
        jpEdicion.setLayout(jpEdicionLayout);
        jpEdicionLayout.setHorizontalGroup(
            jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEdicionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addGap(62, 62, 62)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar)))
                .addContainerGap())
        );
        jpEdicionLayout.setVerticalGroup(
            jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEdicionLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalir)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(109, 109, 109))
        );

        btnSalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir.png"))); // NOI18N
        btnSalir1.setText("Salir");
        btnSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir1ActionPerformed(evt);
            }
        });

        btnGuadar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        btnGuadar1.setText("Guardar");
        btnGuadar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuadar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpAñadirCategoriaLayout = new javax.swing.GroupLayout(jpAñadirCategoria);
        jpAñadirCategoria.setLayout(jpAñadirCategoriaLayout);
        jpAñadirCategoriaLayout.setHorizontalGroup(
            jpAñadirCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAñadirCategoriaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSalir1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 292, Short.MAX_VALUE)
                .addComponent(btnGuadar1)
                .addContainerGap())
        );
        jpAñadirCategoriaLayout.setVerticalGroup(
            jpAñadirCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAñadirCategoriaLayout.createSequentialGroup()
                .addGroup(jpAñadirCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuadar1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpEdicion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jpAñadirCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCantidad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMarca)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCategoria))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPresentacion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblUnidades)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblStock)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPVP)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpAñadirCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(lblCantidad)
                    .addComponent(lblNombre)
                    .addComponent(lblMarca)
                    .addComponent(lblCategoria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPresentacion)
                    .addComponent(lblUnidades)
                    .addComponent(lblStock)
                    .addComponent(lblPVP))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreCategoriaKeyTyped
        char c;
        String temp;
        c = evt.getKeyChar();
        temp = String.valueOf(c).toUpperCase();
        int x = temp.codePointAt(0);
        if ((!Character.isLetter(c) && (c != KeyEvent.VK_SPACE) || (txtNombreCategoria.getText().length() >= 30))) {
            evt.consume();
        } else {
            if (c == KeyEvent.VK_SPACE) {
                evt.setKeyChar('_');
            } else {
                evt.setKeyChar(temp.charAt(0));
            }
        }
    }//GEN-LAST:event_txtNombreCategoriaKeyTyped

    private void txtDetalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDetalleKeyTyped
        char c;
        String temp;
        c = evt.getKeyChar();
        temp = String.valueOf(c).toUpperCase();
        int x = temp.codePointAt(0);
        if ((!Character.isLetter(c) && (c != KeyEvent.VK_SPACE) || (txtDetalle.getText().length() >= 50))) {
            evt.consume();
        } else {
            if (c == KeyEvent.VK_SPACE) {
                evt.setKeyChar('_');
            } else {
                evt.setKeyChar(temp.charAt(0));
            }
        }
    }//GEN-LAST:event_txtDetalleKeyTyped

    private void tblCategoriasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblCategoriasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblCategoriasKeyPressed

    private void tblCategoriasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblCategoriasKeyTyped

    }//GEN-LAST:event_tblCategoriasKeyTyped

    private void tblCategoriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCategoriasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblCategoriasMouseClicked

    private void tblCategoriasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCategoriasMousePressed
        clickTabla();
    }//GEN-LAST:event_tblCategoriasMousePressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarComponentes();
        cargarTablaAlquiler();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        enviarProductos();
        this.dispose();
    }//GEN-LAST:event_btnSalir1ActionPerformed

    private void btnGuadar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuadar1ActionPerformed
        guardarCategoria();
        enviarProductos();
        this.dispose();
    }//GEN-LAST:event_btnGuadar1ActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarCategoria();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtNombreCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreCategoriaActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarCategoria();        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(Categorias.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Categorias.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Categorias.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Categorias.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Categorias().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuadar1;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JPanel jpAñadirCategoria;
    public javax.swing.JPanel jpEdicion;
    public javax.swing.JLabel lblCantidad;
    public javax.swing.JLabel lblCategoria;
    public javax.swing.JLabel lblCodigo;
    public javax.swing.JLabel lblMarca;
    public javax.swing.JLabel lblNombre;
    public javax.swing.JLabel lblPVP;
    public javax.swing.JLabel lblPresentacion;
    public javax.swing.JLabel lblStock;
    public javax.swing.JLabel lblUnidades;
    public javax.swing.JTable tblCategorias;
    private javax.swing.JTextField txtDetalle;
    private javax.swing.JTextField txtNombreCategoria;
    // End of variables declaration//GEN-END:variables
}
