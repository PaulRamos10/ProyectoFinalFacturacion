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
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import proyectodesarrollo.conexion;

/**
 *
 * @author vane
 */
public class frmcajeros extends javax.swing.JInternalFrame {
conexion c= new conexion();
        Connection co=c.conectar();
        DefaultTableModel modelo;
    /**
     * Creates new form frmcajeros
     */
    public frmcajeros() {
        initComponents();
                cargarTabla("");
  //  botonesGuardar();
    lblNumero.setVisible(false);
   
    lblLetra.setVisible(false);
    lblLetra1.setVisible(false);
    btnModi.setEnabled(false);

         
    }
  /*    public void botonesGuardar(){
        btnGuardar.setEnabled(true);
        btnactualizar.setEnabled(false);
        btnelimimar.setEnabled(false);
        btncancelar.setEnabled(true);
        btnsallir.setEnabled(true);
    }
    
    public void salir(){
        menuIngreso a=new menuIngreso();
        a.show();
        
                
    }
    public void cancelar(){
        txtced.setText("");
        txtnom.setText("");
        txtape.setText("");
        txtdir.setText("");
        txtced.requestFocus();
  
    }
    
    public void botonesTabla(){
        btnGuardar.setEnabled(false);
        btnactualizar.setEnabled(true);
        btnelimimar.setEnabled(true);
        btncancelar.setEnabled(true);
        btnsallir.setEnabled(true);
    }  
    
    */
    
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
       if(txtced.getText().equals(n1)|| txtced.getText().equals(n2)|| txtced.getText().equals(n3)|| txtced.getText().equals(n4)
                 || txtced.getText().equals(n5)|| txtced.getText().equals(n6)|| txtced.getText().equals(n7)|| txtced.getText().equals(n8)
                 || txtced.getText().equals(n9)|| txtced.getText().equals(n10)|| txtced.getText().equals(n11)){
             JOptionPane.showMessageDialog(null,"cedula incorrecta");
             txtced.setText("");
            txtced.requestFocus();
         }
   }
     
        public void guardar() {
        if (txtced.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la cedula");
            txtced.requestFocus();
        } else if (txtnom.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre");
            txtnom.requestFocus();
        } else if (txtape.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el apellido");
            txtape.requestFocus();
        } else if (txtdir.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la dirección");
            txtdir.requestFocus();

        } else {
            try {

                String CAJ_CED, CAJ_NOM, CAJ_APE, CAJ_DIR;
                int BOD_SUE;
                String sql = "insert into cajeros(CED_CAJ,NOM_CAJ,APE_CAJ,DIR_CAJ) values(?,?,?,?)";
         
                CAJ_CED = txtced.getText().trim();
                CAJ_NOM = txtnom.getText().trim();
                CAJ_APE = txtape.getText().trim();
                CAJ_DIR = txtdir.getText().trim();
                PreparedStatement p = co.prepareStatement(sql);
                p.setString(1, CAJ_CED);
                p.setString(2, CAJ_NOM);
                p.setString(3, CAJ_APE);
                p.setString(4, CAJ_DIR);
             
                int n = p.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "se inserto correctamente ");
                    txtced.setText("");
                    txtnom.setText("");
                    txtape.setText("");
                    txtdir.setText("");
                    txtced.requestFocus();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }
        

         public void cargarTabla(String cedCajero){
        String[] titulos={"CEDULA","NOMBRE","APELLIDO","DIRECCION"};
        String[] registros= new String[4];
        modelo= new DefaultTableModel(null,titulos);
        try {
            String sql="select*from cajeros where CED_CAJ LIKE'%"+cedCajero+"%'";
            PreparedStatement p= co.prepareStatement(sql);
            ResultSet re=p.executeQuery();
            while(re.next()){
                registros[0]=re.getString("CED_CAJ").trim();
                registros[1]=re.getString("NOM_CAJ").trim();
                registros[2]=re.getString("APE_CAJ").trim();
                registros[3]=re.getString("DIR_CAJ").trim();
                
                
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
                  txtced.setText(tabla.getValueAt(fila, 0).toString().trim());
                  txtnom.setText(tabla.getValueAt(fila, 1).toString().trim());
                  txtape.setText(tabla.getValueAt(fila, 2).toString().trim());
                  txtdir.setText(tabla.getValueAt(fila, 3).toString().trim());
                  
                  
                  txtced.setEnabled(false);
                  
                                }
            }
        });
}
    
          public void actualizar(){
    
             String sql="";
             sql="update cajeros set NOM_CAJ='"+txtnom.getText()+
                     "',APE_CAJ='"+txtape.getText()+
                     "',DIR_CAJ='"+txtdir.getText()
                     +"'where CED_CAJ='"+txtced.getText()+"'";
            
             System.out.println(sql);
             
     
        try {
            PreparedStatement psd=co.prepareStatement(sql);
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "se actualizo correctamente");
                txtnom.setText("");
                txtape.setText("");
                txtdir.setText("");
                               
                      
            }
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null, ex);
        
     }  
}

/*public void eliminar(){
    if(JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar registro","eliminar",JOptionPane.YES_NO_CANCEL_OPTION)==JOptionPane.YES_OPTION){
   
             String sql="";
             sql="delete from cajeros where CED_CAJ='"+txtced.getText()+"'";
             //JOptionPane.showMessageDialog(null, sql);
             System.out.println(sql);
             
        try {
            PreparedStatement psd=co.prepareStatement(sql);
            int n=psd.executeUpdate();
            if(n>0){
              //  JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar","Eliminar",JOptionPane.YES_NO_CANCEL_OPTION);
                
            }
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null, ex);
        }
}
}
*/
    
   
    
   
  
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtced = new javax.swing.JTextField();
        txtape = new javax.swing.JTextField();
        txtnom = new javax.swing.JTextField();
        txtdir = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        lblNumero = new javax.swing.JLabel();
        lblLetra = new javax.swing.JLabel();
        lblLetra1 = new javax.swing.JLabel();
        btnModi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(51, 0, 51));

        tabla.setForeground(new java.awt.Color(153, 0, 153));
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
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaMouseReleased(evt);
            }
        });
        tabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        jLabel5.setForeground(new java.awt.Color(153, 0, 153));
        jLabel5.setText("Buscar:");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 0, 153));
        jLabel6.setText("CAJEROS");

        jLabel1.setForeground(new java.awt.Color(153, 0, 153));
        jLabel1.setText("Cedula:");

        jLabel2.setForeground(new java.awt.Color(153, 0, 153));
        jLabel2.setText("Nombre:");

        jLabel3.setForeground(new java.awt.Color(153, 0, 153));
        jLabel3.setText("Apellido:");

        jLabel4.setForeground(new java.awt.Color(153, 0, 153));
        jLabel4.setText("Direccion:");

        txtced.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtcedMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtcedMouseReleased(evt);
            }
        });
        txtced.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcedKeyTyped(evt);
            }
        });

        txtape.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtapeMouseClicked(evt);
            }
        });
        txtape.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtapeKeyTyped(evt);
            }
        });

        txtnom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtnomMouseClicked(evt);
            }
        });
        txtnom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnomKeyTyped(evt);
            }
        });

        txtdir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtdirMouseClicked(evt);
            }
        });
        txtdir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdirKeyTyped(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        lblNumero.setText("cedula incorrecta");

        lblLetra.setText("Solo letras");

        lblLetra1.setText("Solo letras");

        btnModi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        btnModi.setText("Actualizar");
        btnModi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(182, 182, 182)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtape, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtdir, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtnom, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                        .addComponent(txtced)))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(lblNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblLetra, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblLetra1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtced, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNumero))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLetra))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblLetra1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtdir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tablaMouseReleased

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tablaMouseClicked

    private void tablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tablaKeyPressed

    private void tablaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMousePressed
        // TODO add your handling code here:
     cargartxtModificar();
     btnGuardar.setEnabled(false);
     btnModi.setEnabled(true);
  //    botonesTabla();
  
         
    }//GEN-LAST:event_tablaMousePressed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        // TODO add your handling code here:
        cargarTabla(txtBuscar.getText());
        
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardar();
        cargarTabla("");
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtcedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcedKeyTyped
char c=evt.getKeyChar(); 
          /*if(Character.isLetter(c)) { 
            lblNumero.setVisible(true);
             evt.consume(); 
            }else{
              lblNumero.setVisible(false);
             }        
        int a=9;
          if(txtced.getText().length()>a){
              evt.consume();
          }*/

 
      if(((c<'0')||(c>'9'))&&(c!=KeyEvent.VK_BACK_SPACE)||(txtced.getText().length()==10)){
        //lblNumero.setVisible(false);
          evt.consume();
     
      }
          
      
          
    }//GEN-LAST:event_txtcedKeyTyped

    private void txtnomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar(); 
         if(((c<'A')||(c>'Z')) &&(c!=KeyEvent.VK_BACK_SPACE) ||(txtnom.getText().length()>=30)){
        
          evt.consume();
       }
 
    }//GEN-LAST:event_txtnomKeyTyped

    private void txtapeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapeKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar(); 
           if(((c<'A')||(c>'Z')) &&(c!=KeyEvent.VK_BACK_SPACE) ||(txtape.getText().length()>=30)){
        
          evt.consume();
       }
    }//GEN-LAST:event_txtapeKeyTyped

    private void txtcedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcedMouseClicked
        // TODO add your handling code here:
        lblNumero.setVisible(false);
        cedulaInco();
    }//GEN-LAST:event_txtcedMouseClicked

    private void txtnomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtnomMouseClicked
        // TODO add your handling code here:
        cedulaInco();
        lblNumero.setVisible(false);
    }//GEN-LAST:event_txtnomMouseClicked

    private void txtapeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtapeMouseClicked
        // TODO add your handling code here:
        lblLetra.setVisible(false);
    }//GEN-LAST:event_txtapeMouseClicked

    private void txtdirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtdirMouseClicked
        // TODO add your handling code here:
        lblLetra1.setVisible(false);
    }//GEN-LAST:event_txtdirMouseClicked

    private void txtcedMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcedMouseReleased
        // TODO add your handling code here:
        cedulaInco();
    }//GEN-LAST:event_txtcedMouseReleased

    private void btnModiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModiActionPerformed
        // TODO add your handling code here:
        actualizar();
        cargarTabla("");
        btnGuardar.setEnabled(true);
        btnModi.setEnabled(false);
        txtced.setText("");
        txtced.setEnabled(true);
    }//GEN-LAST:event_btnModiActionPerformed

    private void txtdirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdirKeyTyped
        // TODO add your handling code here:
         char c=evt.getKeyChar(); 
           if(((c<'A')||(c>'Z')) && ((c<'0')||(c>'9')) &&(c!=KeyEvent.VK_BACK_SPACE)  ||(txtdir.getText().length()>=30)){
        
          evt.consume();
          
          
      } 
          
           
           
          
    }//GEN-LAST:event_txtdirKeyTyped

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
            java.util.logging.Logger.getLogger(frmcajeros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmcajeros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmcajeros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmcajeros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmcajeros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLetra;
    private javax.swing.JLabel lblLetra1;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtape;
    private javax.swing.JTextField txtced;
    private javax.swing.JTextField txtdir;
    private javax.swing.JTextField txtnom;
    // End of variables declaration//GEN-END:variables
}
