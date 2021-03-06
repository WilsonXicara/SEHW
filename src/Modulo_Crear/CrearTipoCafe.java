/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modulo_Crear;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana que permite crear un nuevo Café, ya sea Pergamino u Oro. El Café Pergamino es el que se compra como materia prima,
 * en cuanto el Café Oro es el producto que la empresa vende.
 * @author Wilson Xicará
 */
public class CrearTipoCafe extends javax.swing.JDialog {
    private Connection conexion;
    private DefaultTableModel modelTabla;
    private boolean hacerVisible;
    /**
     * Creates new form CrearTipoCafe
     * @param parent
     * @param modal
     */
    public CrearTipoCafe(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     *
     * @param parent
     * @param modal
     * @param conexion
     */
    public CrearTipoCafe(java.awt.Frame parent, boolean modal, Connection conexion) {
        super(parent, modal);
        initComponents();
        this.conexion = conexion;
        hacerVisible = true;
        
        // Obtengo el listado de todos los cafés almacenados en la Base de Datos
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cCafe = sentencia.executeQuery("SELECT Nombre, Pergamino FROM Cafe");
            modelTabla = (DefaultTableModel) tabla_cafe_existentes.getModel();
            while (cCafe.next())
                modelTabla.addRow(new String[]{
                    ""+(tabla_cafe_existentes.getRowCount()+1),
                    cCafe.getString("Nombre"),
                    (cCafe.getBoolean("Pergamino") ? "Pergamino" : "Oro")
                });
            cCafe.close();
            this.setLocationRelativeTo(null);   // Para centrar esta ventana sobre la pantalla.
        } catch (SQLException ex) {
            hacerVisible = false;
            JOptionPane.showMessageDialog(this, "No se puede obtener el listado de los tipos de café existentes.\n\n"+ex.getMessage(), "Error al obtener datos", JOptionPane.ERROR_MESSAGE);
//            Logger.getLogger(CrearTipoCafe.class.getName()).log(Level.SEVERE, null, ex);
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

        panel_nuevo_cafe = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nombre_cafe = new javax.swing.JTextField();
        crear_cafe = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        check_pergamino = new javax.swing.JRadioButton();
        check_oro = new javax.swing.JRadioButton();
        panel_cafe_existentes = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_cafe_existentes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear nuevo tipo de café");

        panel_nuevo_cafe.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del café:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Nombre del Café:");

        nombre_cafe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        crear_cafe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        crear_cafe.setText("Crear Café");
        crear_cafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crear_cafeActionPerformed(evt);
            }
        });

        salir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Tipo:");

        check_pergamino.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        check_pergamino.setSelected(true);
        check_pergamino.setText("Pergamino");
        check_pergamino.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                check_pergaminoItemStateChanged(evt);
            }
        });

        check_oro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        check_oro.setText("Oro");
        check_oro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                check_oroItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panel_nuevo_cafeLayout = new javax.swing.GroupLayout(panel_nuevo_cafe);
        panel_nuevo_cafe.setLayout(panel_nuevo_cafeLayout);
        panel_nuevo_cafeLayout.setHorizontalGroup(
            panel_nuevo_cafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_nuevo_cafeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_nuevo_cafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_nuevo_cafeLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(crear_cafe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(salir))
                    .addGroup(panel_nuevo_cafeLayout.createSequentialGroup()
                        .addGroup(panel_nuevo_cafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_nuevo_cafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombre_cafe, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_nuevo_cafeLayout.createSequentialGroup()
                                .addComponent(check_pergamino)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(check_oro)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_nuevo_cafeLayout.setVerticalGroup(
            panel_nuevo_cafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_nuevo_cafeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_nuevo_cafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nombre_cafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_nuevo_cafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(check_pergamino)
                    .addComponent(check_oro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(panel_nuevo_cafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(crear_cafe)
                    .addComponent(salir))
                .addContainerGap())
        );

        panel_cafe_existentes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipos de café existentes:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tabla_cafe_existentes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tabla_cafe_existentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Nombre", "Tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_cafe_existentes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabla_cafe_existentes.setRowHeight(25);
        jScrollPane1.setViewportView(tabla_cafe_existentes);
        if (tabla_cafe_existentes.getColumnModel().getColumnCount() > 0) {
            tabla_cafe_existentes.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabla_cafe_existentes.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabla_cafe_existentes.getColumnModel().getColumn(2).setPreferredWidth(90);
        }

        javax.swing.GroupLayout panel_cafe_existentesLayout = new javax.swing.GroupLayout(panel_cafe_existentes);
        panel_cafe_existentes.setLayout(panel_cafe_existentesLayout);
        panel_cafe_existentesLayout.setHorizontalGroup(
            panel_cafe_existentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panel_cafe_existentesLayout.setVerticalGroup(
            panel_cafe_existentesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_cafe_existentes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_nuevo_cafe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_nuevo_cafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_cafe_existentes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void check_pergaminoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_check_pergaminoItemStateChanged
        check_oro.setSelected(!check_pergamino.isSelected());
    }//GEN-LAST:event_check_pergaminoItemStateChanged

    private void check_oroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_check_oroItemStateChanged
        check_pergamino.setSelected(!check_oro.isSelected());
    }//GEN-LAST:event_check_oroItemStateChanged

    private void crear_cafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crear_cafeActionPerformed
        String nuevoCafe = nombre_cafe.getText();
        if (nuevoCafe.length() == 0) {
            JOptionPane.showMessageDialog(this, "Asigne un Nombre al tipo de café", "Datos incompletos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean tipoPergamino = check_pergamino.isSelected();
        // Inicio la búsqueda de coincidencias
        int cantidad = tabla_cafe_existentes.getRowCount(), iterador;
        boolean yaExiste = false;
        for (iterador=0; iterador<cantidad; iterador++) {
            yaExiste = nuevoCafe.equals((String)tabla_cafe_existentes.getValueAt(iterador, 1)) &&
                    tipoPergamino && "Pergamino".equals((String)tabla_cafe_existentes.getValueAt(iterador, 2));
            if (yaExiste)
                break;
        }   // Hasta aquí se garantiza la comparación del nuevo Tipo de Café con los existentes
        if (yaExiste) {
            tabla_cafe_existentes.setRowSelectionInterval(iterador, iterador);
            JOptionPane.showMessageDialog(this, nuevoCafe+" de tipo "+(tipoPergamino?"Pergamino":"Oro")+" ya existe", "Datos repetidos", JOptionPane.ERROR_MESSAGE);
        } else {    // Se puede crear el nuevo tipo de café
            try {
                conexion.prepareStatement("INSERT INTO Cafe(Nombre, Pergamino, Oro) VALUES('"+nuevoCafe+"', "+tipoPergamino+", "+!tipoPergamino+")").executeUpdate();
                JOptionPane.showMessageDialog(this, "El Café '"+nuevoCafe+"' de tipo "+(tipoPergamino?"Pergamino":"Oro")+" ha sido creado exitosamente.", "Cosecha creada", JOptionPane.INFORMATION_MESSAGE);
                // Muestro el Nuevo café en la Tabla
                modelTabla.addRow(new String[]{""+(tabla_cafe_existentes.getRowCount()+1), nuevoCafe, tipoPergamino?"Pergamino":"Oro"});
                nombre_cafe.setText("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error de conexión con la Base de Datos.\nConsulte con el programador.\n\nDescripción:\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                Logger.getLogger(CrearTipoCafe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_crear_cafeActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        this.dispose();
    }//GEN-LAST:event_salirActionPerformed

    public boolean getHacerVisible() { return hacerVisible; }
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrearTipoCafe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CrearTipoCafe dialog = new CrearTipoCafe(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton check_oro;
    private javax.swing.JRadioButton check_pergamino;
    private javax.swing.JButton crear_cafe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nombre_cafe;
    private javax.swing.JPanel panel_cafe_existentes;
    private javax.swing.JPanel panel_nuevo_cafe;
    private javax.swing.JButton salir;
    private javax.swing.JTable tabla_cafe_existentes;
    // End of variables declaration//GEN-END:variables
}
