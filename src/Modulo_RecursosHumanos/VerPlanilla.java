/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modulo_RecursosHumanos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Wilson Xicará
 */
public class VerPlanilla extends javax.swing.JFrame {
    private Connection conexion;
    private JFrame ventanaPadre;
    private DefaultTableModel modelPlanilla;
    /**
     * Creates new form VerPlanilla
     */
    public VerPlanilla() {
        initComponents();
    }
    public VerPlanilla(Connection conexion, JFrame ventanaPadre) {
        initComponents();
        this.conexion = conexion;
        this.ventanaPadre = ventanaPadre;
        modelPlanilla = (DefaultTableModel) tabla_planilla.getModel();
        this.setLocationRelativeTo(null);   // Para centrar esta ventana sobre la pantalla.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ciclo_mes = new com.toedter.calendar.JMonthChooser();
        ciclo_anio = new com.toedter.calendar.JYearChooser();
        jLabel1 = new javax.swing.JLabel();
        ver_planilla = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_planilla = new javax.swing.JTable();
        etiqueta_ciclo_contable = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Planilla");

        ciclo_mes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        ciclo_anio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Seleccione un Ciclo:");

        ver_planilla.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ver_planilla.setText("Ver Planilla");
        ver_planilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ver_planillaActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total de Empleados:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        tabla_planilla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Empleado", "DPI", "Puesto", "Sueldo Base", "Bonificación", "Horas Extras", "IGSS", "Préstamos", "Sueldo Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_planilla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tabla_planilla);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
        );

        etiqueta_ciclo_contable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ciclo_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ciclo_anio, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ver_planilla)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(etiqueta_ciclo_contable)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ver_planilla)
                        .addComponent(etiqueta_ciclo_contable))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ciclo_mes, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addComponent(ciclo_anio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ver_planillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ver_planillaActionPerformed
        int mes = ciclo_mes.getMonth()+1, anio = ciclo_anio.getYear();
        modelPlanilla.setRowCount(0);
        System.out.println("Ciclo: "+mes+", "+anio);
        // Obtengo los datos necesarios las Planillas
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cConsulta;
            // Obtención del listado de Empleados y lo referente al financiamiento de su Puesto
            cConsulta = sentencia.executeQuery("SELECT CicloContable.Id idCiclo, CONCAT(Mes.Mes, '-', Anio.Anio) Ciclo, Personal.DPI, Personal.Nombre Empleado, Puesto.Nombre_Puesto Puesto, Empleo.SueldoBase, 250 Bonificacion, Pago.IGSS, Pago.Prestamo  FROM Pago "
                    + "INNER JOIN CicloContable ON Pago.CicloContable_Id = CicloContable.Id "
                    + "INNER JOIN Mes ON CicloContable.Mes_Id = Mes.Id "
                    + "INNER JOIN Anio ON CicloContable.Anio_Id = Anio.Id "
                    + "INNER JOIN Empleo ON Pago.Empleo_Id = Empleo.Id "
                    + "INNER JOIN Personal ON Empleo.Personal_Id = Personal.Id "
                    + "INNER JOIN Puesto ON Empleo.Puesto_Id = Puesto.Id "
                    + "WHERE Anio.Anio = "+anio+" AND Mes.Id = "+mes);
            // Obtengo los datos de la Consulta
            while (cConsulta.next()) {
                String[] registro = new String[10];
                registro[0] = ""+(tabla_planilla.getRowCount()+1);
                registro[1] = cConsulta.getString("Empleado");
                registro[2] = cConsulta.getString("DPI");
                registro[3] = cConsulta.getString("Puesto");
                registro[4] = cConsulta.getString("SueldoBase");
                registro[5] = cConsulta.getString("Bonificacion");
                registro[6] = "Horas Extras";
                registro[7] = cConsulta.getString("IGSS");
                registro[8] = cConsulta.getString("Prestamo");
                registro[9] = "Total";
                modelPlanilla.addRow(registro);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se puede obtener alguno de los listados desde la Base de Datos.\n\n"+ex.getMessage(), "Error al intentar obtener datos", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(VerPlanilla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ver_planillaActionPerformed

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
            java.util.logging.Logger.getLogger(VerPlanilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerPlanilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerPlanilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerPlanilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VerPlanilla().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JYearChooser ciclo_anio;
    private com.toedter.calendar.JMonthChooser ciclo_mes;
    private javax.swing.JLabel etiqueta_ciclo_contable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_planilla;
    private javax.swing.JButton ver_planilla;
    // End of variables declaration//GEN-END:variables
}
