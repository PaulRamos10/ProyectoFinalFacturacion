/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectodesarrollo;

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
public class Proveedores extends javax.swing.JInternalFrame {

    DefaultTableModel modTabla;

    /**
     * Creates new form Proveedores
     */
    public Proveedores() {
        initComponents();
        cargarTablaProveedor("");;
        componentesInicio();
    }

    public void componentesInicio() {
        btnEditar.setEnabled(false);
        btnActualizar.setEnabled(false);
        txtCelular.setEditable(true);
        txtDireccion.setEditable(true);
        txtNombre.setEditable(true);
        txtTelefono.setEditable(true);
        btnGuardar.setEnabled(true);
    }

    public void activarComponentes() {
        btnActualizar.setEnabled(true);
        txtCelular.setEditable(true);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
    }

    public void limpiarComponentes() {
        txtCelular.setText("");
        txtDireccion.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
    }

    public void bloquearComponentes() {
        txtCelular.setEditable(false);
        txtDireccion.setEditable(false);
        txtNombre.setEditable(false);
        txtTelefono.setEditable(false);
    }

    public void clickCancelar() {
        limpiarComponentes();
        componentesInicio();
    }

    public void clicktabla() {
        cargarCampos();
        btnActualizar.setEnabled(false);
        btnEditar.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnEditar.setEnabled(true);
        txtNombre.setEditable(false);
        txtCelular.setEditable(false);
        txtTelefono.setEditable(false);
        txtDireccion.setEditable(false);
    }

    public boolean verificarProveedor(String nombre) {
        boolean existe = false;
        try {
            String sql = "";
            sql = "SELECT NOM_PROV FROM PROVEEDORES";
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);

            while (rs.next()) {
                String nombrebase = rs.getString("NOM_PROV");
                if (nombrebase.equals(nombre)) {
                    existe = true;
                }
            }
            cn.close();
            return existe;
        } catch (SQLException ex) {
            return existe;
        }
    }

    public void guardarProveedor() {
        if (verificarProveedor(txtNombre.getText()) == true) {
            JOptionPane.showMessageDialog(rootPane, "El proveedor señalado ya existe en la lista");
        } else if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No se señaló el nombre del proveedor");
        } else if (txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No se señalo una dirección");
        } else if (txtTelefono.getText().isEmpty() || txtTelefono.getText().length() < 10) {
            JOptionPane.showMessageDialog(rootPane, "El numero de telefono ingresado es incorrecto");
        } else {

            try {
                conexion cc = new conexion();
                Connection cn = cc.conectar();
                String NOM_PROV, DIR_PROV, TELF_PROV, CEL_PROV;

                NOM_PROV = txtNombre.getText();
                DIR_PROV = txtDireccion.getText();
                TELF_PROV = txtTelefono.getText();
                if (txtCelular.getText().isEmpty() || txtCelular.getText().length() < 10) {
                    CEL_PROV = "0980000000";
                } else {
                    CEL_PROV = txtCelular.getText();
                }

                String sql = "";
                sql = "INSERT INTO PROVEEDORES(NOM_PROV,DIR_PROV,TELF_PROV,CEL_PROV)values (?,?,?,?)";
                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setString(1, NOM_PROV);
                psd.setString(2, DIR_PROV);
                psd.setString(3, TELF_PROV);
                psd.setString(4, CEL_PROV);
                int x = psd.executeUpdate();
                if (x > 0) {
                    JOptionPane.showMessageDialog(null, "Proveedor agregado exitosamente!");
                }
                cn.close();
                cargarTablaProveedor("");
                limpiarComponentes();
                componentesInicio();

            } catch (SQLException ex) {
                Logger.getLogger(AdministrarProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

//    public void eliminarProveedor() {
//        int eleccion = JOptionPane.showOptionDialog(rootPane, "¿Está seguro de eliminar el proveedor? ", "Mensaje de Confirmacion",
//                JOptionPane.YES_NO_OPTION,
//                JOptionPane.QUESTION_MESSAGE, null, null, "Aceptar");
//        if (eleccion == JOptionPane.YES_OPTION) {
//            try {
//                conexion cc = new conexion();
//                Connection cn = cc.conectar();
//                int n;
//                String sql = "";
//                sql = "DELETE FROM PROVEEDORES WHERE NOM_PROV = '" + txtNombre.getText() + "'";
//                PreparedStatement psd = cn.prepareStatement(sql);
//                n = psd.executeUpdate();
//                cargarTablaProveedor("");
//                limpiarComponentes();
//                componentesInicio();
//            } catch (SQLException ex) {
//                Logger.getLogger(AdministrarProductos.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//
//    }
    public void cargarTablaProveedor(String nombre) {
        try {
            String[] titulos = {"Nombre", "Dirección", "Teléfono", "Celular"};
            String[] registros = new String[4];
            modTabla = new DefaultTableModel(null, titulos);
            tblProveedores.setModel(modTabla);

            String sql = "";
            sql = "SELECT * FROM PROVEEDORES WHERE NOM_PROV LIKE '%" + nombre + "%'";

            conexion cc = new conexion();
            Connection cn = cc.conectar();
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);

            while (rs.next()) {
                registros[0] = rs.getString("NOM_PROV");
                registros[1] = rs.getString("DIR_PROV");
                registros[2] = rs.getString("TELF_PROV");
                registros[3] = rs.getString("CEL_PROV");
                modTabla.addRow(registros);
            }
            tblProveedores.setModel(modTabla);
            cn.close();
        } catch (Exception ex) {
        }
    }

    public void cargarCampos() {
        tblProveedores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tblProveedores.getSelectedRow() != -1) {
                    int fila = tblProveedores.getSelectedRow();
                    txtNombre.setText(tblProveedores.getValueAt(fila, 0).toString());
                    txtDireccion.setText(tblProveedores.getValueAt(fila, 1).toString());
                    txtTelefono.setText(tblProveedores.getValueAt(fila, 2).toString());
                    txtCelular.setText(tblProveedores.getValueAt(fila, 3).toString());
                }
            }
        });
    }

    public void actualizarProveedor() {
        if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No se señaló el nombre del proveedor");
        } else if (txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No se señalo una dirección");
        } else if (txtTelefono.getText().isEmpty() || txtTelefono.getText().length() < 10) {
            JOptionPane.showMessageDialog(rootPane, "El número de telefono ingresado es incorrecto");
        } else {
            try {
                conexion cc = new conexion();
                Connection cn = cc.conectar();
                String NOM_PROV, DIR_PROV, TELF_PROV, CEL_PROV;
                NOM_PROV = txtNombre.getText();
                DIR_PROV = txtDireccion.getText();
                TELF_PROV = txtTelefono.getText();
                if (txtCelular.getText().isEmpty() || txtCelular.getText().length() < 10) {
                    CEL_PROV = "0980000000";
                } else {
                    CEL_PROV = txtCelular.getText();
                }
                String sql = "";
                sql = "UPDATE PROVEEDORES SET "
                        + " DIR_PROV = '" + DIR_PROV + "',"
                        + " TELF_PROV = '" + TELF_PROV + "',"
                        + " CEL_PROV = '" + CEL_PROV + "' WHERE NOM_PROV = '" + NOM_PROV + "'";

                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Proveedor actualizado exitosamente");
                }
                cn.close();
                cargarTablaProveedor("");
                limpiarComponentes();
                componentesInicio();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        jpEdicion = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProveedores = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administrar Proveedores");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(112, 48, 160));
        jLabel1.setText("Nombre:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(112, 48, 160));
        jLabel2.setText("Direccion:");

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

        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(112, 48, 160));
        jLabel3.setText("Telefono:");

        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(112, 48, 160));
        jLabel4.setText("Celular:");

        txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelularKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCelular, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                    .addComponent(txtNombre)
                    .addComponent(txtDireccion))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblProveedores.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblProveedores.setForeground(new java.awt.Color(112, 48, 160));
        tblProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProveedoresMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblProveedoresMousePressed(evt);
            }
        });
        tblProveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblProveedoresKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblProveedoresKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tblProveedores);

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

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setToolTipText("Actualizar informacion de producto");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(112, 48, 160));
        jLabel5.setText("Buscar");

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setToolTipText("Activar edición");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpEdicionLayout = new javax.swing.GroupLayout(jpEdicion);
        jpEdicion.setLayout(jpEdicionLayout);
        jpEdicionLayout.setHorizontalGroup(
            jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEdicionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar))
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpEdicionLayout.setVerticalGroup(
            jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEdicionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalir)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 90, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char c;
        String temp;
        c = evt.getKeyChar();
        temp = String.valueOf(c).toUpperCase();
        int x = temp.codePointAt(0);
        if ((!Character.isLetter(c) && (c != KeyEvent.VK_SPACE) || (txtNombre.getText().length() >= 30))) {
            evt.consume();
        } else {
            if (c == KeyEvent.VK_SPACE) {
                evt.setKeyChar('_');
            } else {
                evt.setKeyChar(temp.charAt(0));
            }
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        char c;
        String temp;
        c = evt.getKeyChar();
        temp = String.valueOf(c).toUpperCase();
        int x = temp.codePointAt(0);
        if ((!Character.isLetter(c) && (c != KeyEvent.VK_SPACE) || (txtDireccion.getText().length() >= 30))) {
            evt.consume();
        } else {
            if (c == KeyEvent.VK_SPACE) {
                evt.setKeyChar('_');
            } else {
                evt.setKeyChar(temp.charAt(0));
            }
        }
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void tblProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProveedoresMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProveedoresMouseClicked

    private void tblProveedoresMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProveedoresMousePressed
        clicktabla();
    }//GEN-LAST:event_tblProveedoresMousePressed

    private void tblProveedoresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProveedoresKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProveedoresKeyPressed

    private void tblProveedoresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProveedoresKeyTyped

    }//GEN-LAST:event_tblProveedoresKeyTyped

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        clickCancelar();//        limpiarComponentes();
        //     cargarTablaAlquiler();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarProveedor();//        guardarCategoria();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        actualizarProveedor();        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        limpiarComponentes();
        cargarTablaProveedor(txtBuscar.getText());
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        activarComponentes();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        char c = evt.getKeyChar();

        if ((((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) || (txtTelefono.getText().length() >= 10))) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyTyped
        char c = evt.getKeyChar();

        if ((((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) || (txtTelefono.getText().length() >= 10))) {
            evt.consume();
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularKeyTyped

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
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Proveedores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JPanel jpEdicion;
    public javax.swing.JTable tblProveedores;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
