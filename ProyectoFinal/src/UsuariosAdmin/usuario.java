/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UsuariosAdmin;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import proyectodesarrollo.conexion;

/**
 *
 * @author vane
 */
public class usuario extends javax.swing.JInternalFrame {
    conexion c= new conexion();
    Connection co=c.conectar();
    DefaultTableModel modelo;

    /**
     * Creates new form usuario
     */
    public usuario() {
        initComponents();
        cmberfil.addItem("Seleccione");
        cmberfil.addItem("BODEGUERO");
        cmberfil.addItem("CAJERO");
        cmberfil.addItem("ADMINISTRADOR");
   //     cmberfil.addItem("");
        lblnumero.setVisible(false);
        lblLetra.setVisible(false);
        lblLetra1.setVisible(false);
      //  cajero();
        cargarTabla("");
        txtCed.requestFocus();
        btnGuar.setEnabled(true);
        btnAct.setEnabled(false);
        btnCancelar.setEnabled(false);
    }

    
   public void cedulaInco(){
        String n1="1111111111"; 
       String n2="2222222222";
       String n3="3333333333";
         String n4="4444444444";
         String n5="5555555555";
         String n6="6666666666";
         String n7="7777777777";
         String n8="8888888888";
         String n9="9999999999";
        String n10="1212121212";
       String n11="0000000000";
       if(txtCed.getText().equals(n1)|| txtCed.getText().equals(n2)|| txtCed.getText().equals(n3)|| txtCed.getText().equals(n4)
                 || txtCed.getText().equals(n5)|| txtCed.getText().equals(n6)|| txtCed.getText().equals(n7)|| txtCed.getText().equals(n8)
                 || txtCed.getText().equals(n9)|| txtCed.getText().equals(n10)|| txtCed.getText().equals(n11)){
             JOptionPane.showMessageDialog(null,"cedula incorrecta");
             txtCed.setText("");
            txtCed.requestFocus();
         }
   }
         
    

  public void guardar() {
      if(txtCed.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la cedula");
            txtCed.requestFocus();
              
          
      }else              
        if (txtNom.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre");
            txtNom.requestFocus();
        } else if (txtApe.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el apellido");
            txtApe.requestFocus();
        
        } else if (txtCla.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la clave");
            txtCla.requestFocus();

        } 
        
        else {
            try {

                String CEDULA, NOMBRE, APELLIDO,CLAVE, PERFIL;
          
                String sql = "insert into usuarios(CEDULA,NOMBRE,APELLIDO,CLAVE,PERFIL) values(?,?,?,?,?)";
                
                    CEDULA = txtCed.getText().trim();
                
                NOMBRE = txtNom.getText().trim();
                APELLIDO = txtApe.getText().trim();
                CLAVE = txtCla.getText().trim();
                PERFIL = cmberfil.getSelectedItem().toString();

                PreparedStatement p = co.prepareStatement(sql);
                p.setString(1, CEDULA);
                p.setString(2, NOMBRE);
                p.setString(3, APELLIDO);
                p.setString(4, CLAVE);
                p.setString(5, PERFIL);
               

                int n = p.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "se inserto correctamente");
                    txtCed.setText("");
                    txtNom.setText("");
                    txtApe.setText("");
                    txtCla.setText("");
                    cmberfil.setSelectedIndex(0);
                
                    txtCed.requestFocus();
                    
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }
  
   public void cargarTabla(String cedusu){
        String[] titulos={"CEDULA","NOMBRE","APELLIDO","CLAVE","PERFIL"};
        String[] registros= new String[5];
        modelo= new DefaultTableModel(null,titulos);
        try {
            String sql="select*from usuarios where CEDULA LIKE'%"+cedusu+"%'";
            PreparedStatement p= co.prepareStatement(sql);
            ResultSet re=p.executeQuery();
            while(re.next()){
                registros[0]=re.getString("CEDUlA").trim();
                registros[1]=re.getString("NOMBRE").trim();
                registros[2]=re.getString("APELLIDO").trim();
                registros[3]=re.getString("CLAVE").trim();
                registros[4]=re.getString("PERFIL").trim();
                
                
                
                modelo.addRow(registros);
            }
            tabla.setModel(modelo);
        } catch (Exception e) {
        }
        
    }
   
   public void cargartxtModificar(){
    tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override//sobrecarga cuando cambia constantenmente se le ppone 
            public void valueChanged(ListSelectionEvent lse) {
              if(tabla.getSelectedRow()!=-1){
                  int fila=tabla.getSelectedRow();//me devuelve el numero de filas
                  txtCed.setText(tabla.getValueAt(fila, 0).toString().trim());
                  txtNom.setText(tabla.getValueAt(fila, 1).toString().trim());
                  txtApe.setText(tabla.getValueAt(fila, 2).toString().trim());
                  txtCla.setText(tabla.getValueAt(fila, 3).toString().trim());
                  cmberfil.setSelectedItem(tabla.getValueAt(fila, 4).toString().trim());
                  
                  
                  txtCed.setEnabled(false);
                  
                                }
            }
        });
}
   
     public void actualizar(){
    
             String sql="";
             sql="update usuarios set NOMBRE='"+txtNom.getText()+
                     "',APELLIDO='"+txtApe.getText()+
                     "',CLAVE='"+txtCla.getText()+
                     "',PERFIL='"+cmberfil.getSelectedItem()
                     +"'where CEDULA='"+txtCed.getText()+"'";
            
             System.out.println(sql);
             
     
        try {
            PreparedStatement psd=co.prepareStatement(sql);
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "se actualizo correctamente");
                txtNom.setText("");
                txtApe.setText("");
                txtCla.setText("");
                cmberfil.setSelectedIndex(0);
                               
                      
            }
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null, ex);
        
     }  
}
   
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        txtApe = new javax.swing.JTextField();
        txtCla = new javax.swing.JTextField();
        btnGuar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        cmberfil = new javax.swing.JComboBox<>();
        lblnumero = new javax.swing.JLabel();
        lblLetra = new javax.swing.JLabel();
        lblLetra1 = new javax.swing.JLabel();
        txtCed = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        btnAct = new javax.swing.JButton();
        txtbuscar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 153));
        jLabel1.setText("USUARIOS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, 21));

        jLabel2.setForeground(new java.awt.Color(153, 0, 153));
        jLabel2.setText("Cedula:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel3.setForeground(new java.awt.Color(153, 0, 153));
        jLabel3.setText("Nombre:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jLabel4.setForeground(new java.awt.Color(153, 0, 153));
        jLabel4.setText("Apellido:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel5.setForeground(new java.awt.Color(153, 0, 153));
        jLabel5.setText("Clave:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 40, -1));

        jLabel6.setForeground(new java.awt.Color(153, 0, 153));
        jLabel6.setText("Perfil:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        txtNom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNomMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtNomMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtNomMousePressed(evt);
            }
        });
        txtNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomActionPerformed(evt);
            }
        });
        txtNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomKeyTyped(evt);
            }
        });
        jPanel1.add(txtNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 110, -1));

        txtApe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtApeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtApeMousePressed(evt);
            }
        });
        txtApe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApeKeyTyped(evt);
            }
        });
        jPanel1.add(txtApe, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 110, -1));

        txtCla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtClaMouseClicked(evt);
            }
        });
        txtCla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtClaKeyTyped(evt);
            }
        });
        jPanel1.add(txtCla, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 110, -1));

        btnGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        btnGuar.setText("Guardar");
        btnGuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 120, -1));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 120, -1));

        jPanel1.add(cmberfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 110, -1));

        lblnumero.setText("cedula incorrecta");
        jPanel1.add(lblnumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 92, -1));

        lblLetra.setText("Solo letras");
        jPanel1.add(lblLetra, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 81, 92, -1));

        lblLetra1.setText("Solo letras");
        jPanel1.add(lblLetra1, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 107, 92, -1));

        txtCed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCedMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtCedMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCedMousePressed(evt);
            }
        });
        txtCed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedActionPerformed(evt);
            }
        });
        txtCed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedKeyTyped(evt);
            }
        });
        jPanel1.add(txtCed, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 110, -1));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, -1, 230));

        jLabel7.setForeground(new java.awt.Color(153, 0, 153));
        jLabel7.setText("Buscar:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        btnAct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        btnAct.setText("Actualizar");
        btnAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActActionPerformed(evt);
            }
        });
        jPanel1.add(btnAct, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, 120, 30));

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtbuscarKeyTyped(evt);
            }
        });
        jPanel1.add(txtbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, 110, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuarActionPerformed
        // TODO add your handling code here:
        guardar();
        cargarTabla("");
    }//GEN-LAST:event_btnGuarActionPerformed

    private void txtNomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomKeyTyped
        // TODO add your handling code here:
        
         char c=evt.getKeyChar(); 
    
       if(((c<'A')||(c>'Z')) &&(c!=KeyEvent.VK_BACK_SPACE) ||(txtNom.getText().length()>=30)){
        
          evt.consume();
       }
      
    }//GEN-LAST:event_txtNomKeyTyped

    private void txtApeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApeKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar(); 
  
        if(((c<'A')||(c>'Z')) &&(c!=KeyEvent.VK_BACK_SPACE) ||(txtApe.getText().length()>=30)){
        
          evt.consume();
       }
    }//GEN-LAST:event_txtApeKeyTyped

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        txtCed.setText("");
        txtNom.setText("");
        txtApe.setText("");
        txtCla.setText("");
        cmberfil.setSelectedIndex(0);
        txtCed.setEnabled(true);
        txtCed.requestFocus();
        btnGuar.setEnabled(true);
        btnAct.setEnabled(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtCedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedKeyTyped
        // TODO add your handling code here:
      //  cedulaIncorrecta();
        char c=evt.getKeyChar(); 
    
      
     
      if(((c<'0')||(c>'9'))&&(c!=KeyEvent.VK_BACK_SPACE)||(txtCed.getText().length()==10)){
        
          evt.consume();
          lblnumero.setVisible(false);
          
      }
          
      }
{            
          
         
         
        
        
    }//GEN-LAST:event_txtCedKeyTyped

    private void txtCedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCedMousePressed
        // TODO add your handling code here:
        lblnumero.setVisible(false);
    }//GEN-LAST:event_txtCedMousePressed

    private void txtNomMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNomMousePressed
        // TODO add your handling code here:
        lblLetra.setVisible(false);
    }//GEN-LAST:event_txtNomMousePressed

    private void txtApeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtApeMousePressed
        // TODO add your handling code here:
        lblLetra1.setVisible(false);
    }//GEN-LAST:event_txtApeMousePressed

    private void txtCedMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCedMouseEntered
        // TODO add your handling code here:
        lblnumero.setVisible(false);
    }//GEN-LAST:event_txtCedMouseEntered

    private void txtNomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNomMouseClicked
        // TODO add your handling code here:
        lblnumero.setVisible(false);
        cedulaInco();
        
         
          
      
    }//GEN-LAST:event_txtNomMouseClicked

    private void txtCedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCedMouseClicked
        // TODO add your handling code here:
        lblnumero.setVisible(false);
        
    }//GEN-LAST:event_txtCedMouseClicked

    private void txtApeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtApeMouseClicked
        // TODO add your handling code here:
        lblLetra.setVisible(false);
    }//GEN-LAST:event_txtApeMouseClicked

    private void txtClaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtClaMouseClicked
        // TODO add your handling code here:
        lblLetra1.setVisible(false);
    }//GEN-LAST:event_txtClaMouseClicked

    private void txtNomMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNomMouseEntered
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtNomMouseEntered

    private void txtCedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedActionPerformed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_txtCedActionPerformed

    private void txtNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtNomActionPerformed

    private void btnActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActActionPerformed
        // TODO add your handling code here:
        actualizar();
        cargarTabla("");
    }//GEN-LAST:event_btnActActionPerformed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaMouseClicked

    private void tablaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMousePressed
        // TODO add your handling code here:
        cargartxtModificar();
        btnGuar.setEnabled(false);
        btnAct.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_tablaMousePressed

    private void txtbuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyTyped
        // TODO add your handling code here:
        cargarTabla(txtbuscar.getText());
    }//GEN-LAST:event_txtbuscarKeyTyped

    private void txtClaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClaKeyTyped
        // TODO add your handling code here:
         char c=evt.getKeyChar(); 
           if(((c<'A')||(c>'Z')) && ((c<'0')||(c>'9')) &&(c!=KeyEvent.VK_BACK_SPACE)){
        
          evt.consume();
          
          
      } 
    }//GEN-LAST:event_txtClaKeyTyped

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
            java.util.logging.Logger.getLogger(usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new usuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAct;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuar;
    private javax.swing.JComboBox<String> cmberfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLetra;
    private javax.swing.JLabel lblLetra1;
    private javax.swing.JLabel lblnumero;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtApe;
    private javax.swing.JTextField txtCed;
    private javax.swing.JTextField txtCla;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
}
