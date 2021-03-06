/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modulo_Finanzas;

import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class Polizas extends javax.swing.JFrame {
    Connection base;
    ArrayList<String> Id_año;
    ArrayList<String> Id_partidas;
    ArrayList<String> Id_agregados;
    ArrayList<String> Valores_agregados;
    ArrayList<String> Tipo_agregados;
    String Id_ciclo_contable;
    int num_año;
    int num_mes;
    int num_poliza;
    int max_polizas;
    int seleccionado;
    float Total_debe;
    float Total_haber;
    boolean cuenta_agregada;
    boolean tipo_cuenta;
    String Id_agregar,nombre_agregar;
    /**
     * Creates new form Polizas
     */
    public Polizas() {
        initComponents();
        
    }
    
    /**
     * Creates new form Polizas
     */
    public Polizas(Connection base) {
        try {
            initComponents();
            tipo.add(bt_debe);
            tipo.add(bt_haber);
            this.base = base;
            Id_año = new ArrayList<>();
            Id_agregados = new ArrayList<>();
            Valores_agregados = new ArrayList<>();
            Tipo_agregados = new ArrayList<>();
            num_mes = 1;
            num_año = 0;
            seleccionado = -1;
            Id_agregar = "";
            nombre_agregar = "";
            Id_ciclo_contable= "";
            bt_aceptar.setVisible(false);
            Agregar_cuenta.setVisible(false);
            Statement b = base.createStatement();
            ResultSet consulta = b.executeQuery("SELECT anio.Id, anio.Anio FROM anio");
            while(consulta.next()){
                Id_año.add(consulta.getString(1));
                Tx_año.addItem(consulta.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Polizas.class.getName()).log(Level.SEVERE, null, ex);
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

        tipo = new javax.swing.ButtonGroup();
        Tx_año = new javax.swing.JComboBox<>();
        Tx_mes = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Tx_Numero = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Tx_fecha = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tx_descripcion = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        Partida = new javax.swing.JTable();
        atras = new javax.swing.JButton();
        adelante = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        Debe = new javax.swing.JTextField();
        Haber = new javax.swing.JTextField();
        bt_crear = new javax.swing.JButton();
        Agregar_cuenta = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Tx_nombre_agregar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        bt_debe = new javax.swing.JRadioButton();
        bt_haber = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        Tx_valor_agregar = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        bt_aceptar = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Polizas");
        setAlwaysOnTop(true);

        Tx_año.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Tx_añoItemStateChanged(evt);
            }
        });

        Tx_mes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        Tx_mes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Tx_mesItemStateChanged(evt);
            }
        });

        jLabel1.setText("Año :");

        jLabel2.setText("Mes :");

        jLabel3.setText("No.");

        Tx_Numero.setEnabled(false);
        Tx_Numero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tx_NumeroActionPerformed(evt);
            }
        });

        jLabel4.setText("Fecha :");

        Tx_fecha.setEnabled(false);

        jLabel5.setText("Descripción : ");

        Tx_descripcion.setColumns(20);
        Tx_descripcion.setRows(5);
        Tx_descripcion.setEnabled(false);
        jScrollPane1.setViewportView(Tx_descripcion);

        Partida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta Contable", "Debe", "Haber"
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
        Partida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PartidaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Partida);
        if (Partida.getColumnModel().getColumnCount() > 0) {
            Partida.getColumnModel().getColumn(0).setResizable(false);
            Partida.getColumnModel().getColumn(0).setPreferredWidth(300);
            Partida.getColumnModel().getColumn(1).setResizable(false);
            Partida.getColumnModel().getColumn(2).setResizable(false);
        }

        atras.setText("<");
        atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atrasActionPerformed(evt);
            }
        });

        adelante.setText(">");
        adelante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adelanteActionPerformed(evt);
            }
        });

        jLabel6.setText("Total");

        Debe.setEnabled(false);

        Haber.setEnabled(false);

        bt_crear.setText("Crear");
        bt_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_crearActionPerformed(evt);
            }
        });

        Agregar_cuenta.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Agregar Cuenta"));

        jLabel7.setText("Nombre: ");

        Tx_nombre_agregar.setEnabled(false);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        bt_debe.setText("Debe");
        bt_debe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                bt_debeItemStateChanged(evt);
            }
        });

        bt_haber.setText("Haber");
        bt_haber.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                bt_haberItemStateChanged(evt);
            }
        });

        jLabel8.setText("Valor :");

        Tx_valor_agregar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Tx_valor_agregarKeyTyped(evt);
            }
        });

        jButton2.setText("Agregar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Borrar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        bt_aceptar.setText("Aceptar");
        bt_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_aceptarActionPerformed(evt);
            }
        });

        jButton4.setText("Cancelar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Agregar_cuentaLayout = new javax.swing.GroupLayout(Agregar_cuenta);
        Agregar_cuenta.setLayout(Agregar_cuentaLayout);
        Agregar_cuentaLayout.setHorizontalGroup(
            Agregar_cuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Agregar_cuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Agregar_cuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Agregar_cuentaLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tx_nombre_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(bt_debe))
                    .addGroup(Agregar_cuentaLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tx_valor_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addGap(10, 10, 10)
                .addGroup(Agregar_cuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Agregar_cuentaLayout.createSequentialGroup()
                        .addComponent(bt_aceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addComponent(bt_haber))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Agregar_cuentaLayout.setVerticalGroup(
            Agregar_cuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Agregar_cuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Agregar_cuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Agregar_cuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(Tx_nombre_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addGroup(Agregar_cuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bt_debe)
                        .addComponent(bt_haber)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Agregar_cuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(Tx_valor_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(bt_aceptar)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 10, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tx_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tx_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(atras)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(adelante))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tx_año, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tx_mes, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(bt_crear)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addGap(336, 336, 336)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Debe, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Haber)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Agregar_cuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tx_año, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tx_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(bt_crear))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Tx_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(Tx_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(atras)
                    .addComponent(adelante))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Debe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Haber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Agregar_cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tx_NumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tx_NumeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tx_NumeroActionPerformed

    private void Tx_añoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Tx_añoItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            try {
                num_año = Tx_año.getSelectedIndex();
                Actualizar_polizas();
            } catch (SQLException ex) {
                Logger.getLogger(Polizas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_Tx_añoItemStateChanged

    private void Tx_mesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Tx_mesItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            try {
                num_mes = Tx_mes.getSelectedIndex()+1;
                Actualizar_polizas();
            } catch (SQLException ex) {
                Logger.getLogger(Polizas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_Tx_mesItemStateChanged

    private void atrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atrasActionPerformed
        if(num_poliza+1 == max_polizas){
            adelante.setEnabled(true);
        }
        num_poliza--;
        mostrar_poliza();
        if(num_poliza == 0 ){
            atras.setEnabled(false);
        }
            
        
    }//GEN-LAST:event_atrasActionPerformed

    private void adelanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adelanteActionPerformed
        if(num_poliza == 0){
            atras.setEnabled(true);
        }
        num_poliza++;
        mostrar_poliza();
        
        if(num_poliza+1 == max_polizas ){
            adelante.setEnabled(false);
        }
    }//GEN-LAST:event_adelanteActionPerformed

    private void bt_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_crearActionPerformed
        Tx_año.setEnabled(false);
        Tx_mes.setEnabled(false);
        Tx_nombre_agregar.setText("");
        Tx_valor_agregar.setText("");
        Debe.setText("");
        Haber.setText("");
        Total_debe = 0;
        Total_haber = 0;
        seleccionado =-1;
        Agregar_cuenta.setVisible(true);
        bt_aceptar.setVisible(true);
        bt_crear.setVisible(false);
        Tx_Numero.setText(Integer.toString(max_polizas + 1));
        Tx_fecha.setText("");
        Tx_fecha.setVisible(false);
        Tx_descripcion.setText("");
        Tx_descripcion.setEnabled(true);
        bt_debe.setSelected(true);
        cuenta_agregada = false;
        tipo_cuenta = true;
        DefaultTableModel tabla = (DefaultTableModel) Partida.getModel();
        tabla.setRowCount(0);
    }//GEN-LAST:event_bt_crearActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Buscar_cuenta a = new Buscar_cuenta(this, true, base);
        a.setVisible(true);
       
        Id_agregar = a.getId();
        nombre_agregar = a.getNombre();
        if(nombre_agregar.length() != 0){
            cuenta_agregada = true;
        }
        Tx_nombre_agregar.setText(nombre_agregar);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       if(cuenta_agregada){
           String valor = Tx_valor_agregar.getText().trim();
           if(valor.length() >0){
               if(!Id_agregados.contains(Id_agregar)){
                    DefaultTableModel tabla = (DefaultTableModel) Partida.getModel();
                    String[] agregar = new String[3];
                    Id_agregados.add(Id_agregar);
                    Valores_agregados.add(valor);
                    if(tipo_cuenta){
                       Tipo_agregados.add("1");
                       Total_debe = Total_debe + Float.parseFloat(valor);
                       Debe.setText(Float.toString(Total_debe));
                       agregar[0] = nombre_agregar;
                       agregar[1] = valor;
                       agregar[2] = "";
                    }else{
                       Tipo_agregados.add("0");
                       Total_haber = Total_haber + Float.parseFloat(valor);
                       Haber.setText(Float.toString(Total_haber));
                       agregar[0] = nombre_agregar;
                       agregar[1] = "";
                       agregar[2] = valor;
                    }
                    tabla.addRow(agregar);
               }else{
                    JOptionPane.showMessageDialog(this, "La cuenta ya ha sido agregada", "Error", JOptionPane.ERROR_MESSAGE, null);
               }
           }else{
                JOptionPane.showMessageDialog(this, "Debe ingresar un valor", "Error", JOptionPane.ERROR_MESSAGE, null);
           }
       }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna cuenta", "Error", JOptionPane.ERROR_MESSAGE, null);
       }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void bt_debeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_bt_debeItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            tipo_cuenta = true;
        }
    }//GEN-LAST:event_bt_debeItemStateChanged

    private void bt_haberItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_bt_haberItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            tipo_cuenta = false;
        }
    }//GEN-LAST:event_bt_haberItemStateChanged

    private void PartidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PartidaMouseClicked
        seleccionado = Partida.getSelectedRow();
    }//GEN-LAST:event_PartidaMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(seleccionado!=-1){
            DefaultTableModel tabla = (DefaultTableModel) Partida.getModel();
            if(Tipo_agregados.get(seleccionado).equals("1")){
                Total_debe = Total_debe - Float.parseFloat(Valores_agregados.get(seleccionado));
                Id_agregados.remove(seleccionado);
                Valores_agregados.remove(seleccionado);
                Tipo_agregados.remove(seleccionado);
                tabla.removeRow(seleccionado);
                Debe.setText(Float.toString(Total_debe));
            }else{
                Total_haber = Total_haber - Float.parseFloat(Valores_agregados.get(seleccionado));
                Id_agregados.remove(seleccionado);
                Valores_agregados.remove(seleccionado);
                Tipo_agregados.remove(seleccionado);
                tabla.removeRow(seleccionado);
                Haber.setText(Float.toString(Total_haber));
            }
            seleccionado = -1;
        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna cuenta", "Error", JOptionPane.ERROR_MESSAGE, null);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void Tx_valor_agregarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tx_valor_agregarKeyTyped
        if(evt.getKeyChar() == '|'){
            evt.consume();
            if(Total_debe > Total_haber){
                Tx_valor_agregar.setText(Float.toString(Total_debe-Total_haber));
                bt_haber.setSelected(true);
            }else{
                Tx_valor_agregar.setText(Float.toString(Total_haber-Total_debe));
                bt_debe.setSelected(true);
            }
        } 
        if(evt.getKeyCode()<48 && evt.getKeyChar()>57 && evt.getKeyChar()!= 46 ){
            evt.consume();
        }
    }//GEN-LAST:event_Tx_valor_agregarKeyTyped

    private void bt_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_aceptarActionPerformed
        if(Id_agregados.size()>0){
            if(Total_debe == Total_haber ){
                String Numero = Tx_Numero.getText();
                String Descripcion = Tx_descripcion.getText().trim();
                if(Descripcion.length()>0){
                    try {
                        base.prepareStatement("INSERT INTO partida(partida.CicloContable_Id,partida.Numero,partida.Fecha,partida.Descripcion) VALUES("+Id_ciclo_contable+","+Numero+",NOW(),'"+Descripcion+"');").executeUpdate();
                        ResultSet consulta = base.createStatement().executeQuery("SELECT MAX(partida.Id) FROM partida");
                        consulta.next();
                        String Id_partida = consulta.getString(1);
                        for (int i = 0; i < Id_agregados.size(); i++) {
                            base.prepareStatement("INSERT INTO detalle_partida(Partida_Id,Cuentas_Id,Valor,Debe) VALUES("+Id_partida+","+Id_agregados.get(i)+","+Valores_agregados.get(i)+","+Tipo_agregados.get(i)+");").executeUpdate();
                        }
                        Id_agregados = new ArrayList<>();
                        Valores_agregados = new ArrayList<>();
                        Tipo_agregados = new ArrayList<>();
                        Agregar_cuenta.setVisible(false);
                        bt_crear.setVisible(true);
                        Tx_fecha.setVisible(true);
                        Actualizar_polizas();
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(Polizas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "Debe ingresar una descripción", "Error", JOptionPane.ERROR_MESSAGE, null);
                }
            }else{
                JOptionPane.showMessageDialog(this, "La partida no cuadra", "Error", JOptionPane.ERROR_MESSAGE, null);
            }
        }else{
            JOptionPane.showMessageDialog(this, "No se han agregado cuentas", "Error", JOptionPane.ERROR_MESSAGE, null);
        }
    }//GEN-LAST:event_bt_aceptarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Id_agregados = new ArrayList<>();
        Valores_agregados = new ArrayList<>();
        Tipo_agregados = new ArrayList<>();
        Agregar_cuenta.setVisible(false);
        bt_crear.setVisible(true);
        Tx_fecha.setVisible(true);
        mostrar_poliza();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(Polizas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Polizas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Polizas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Polizas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Polizas().setVisible(true);
            }
        });
    }
    
    public void Actualizar_polizas() throws SQLException{
        max_polizas = 0;
        Statement b = base.createStatement();
        ResultSet consulta = b.executeQuery("SELECT ciclocontable.Id FROM ciclocontable WHERE ciclocontable.Anio_Id = "+Id_año.get(num_año)+" AND ciclocontable.Mes_Id = "+num_mes+";");
        consulta.next();
        Id_ciclo_contable = consulta.getString(1);
        b = base.createStatement();
        consulta = b.executeQuery("SELECT partida.Id FROM partida WHERE partida.CicloContable_Id = "+Id_ciclo_contable+" Order BY partida.Numero;");
        Id_partidas = new ArrayList<>();
        while(consulta.next()){
            Id_partidas.add(consulta.getString(1));
            max_polizas++;
        }
        num_poliza = 0;
        mostrar_poliza();
        atras.setEnabled(false);
        if(max_polizas>1){
            adelante.setEnabled(true);
        }else{
            adelante.setEnabled(false);
        }
    }
    
    public void mostrar_poliza(){
        try {
            float total_debe = 0;
            float total_haber = 0;
            if(Id_partidas.size()>0){
            String poliza = Id_partidas.get(num_poliza);
            Statement b = base.createStatement();
            ResultSet consulta = b.executeQuery("SELECT partida.Numero,partida.Fecha,partida.Descripcion FROM partida WHERE partida.Id = "+poliza+";");
            consulta.next();
            Tx_Numero.setText(consulta.getString(1));
            Tx_fecha.setText(consulta.getString(2));
            Tx_descripcion.setText(consulta.getString(3));
            b = base.createStatement();
            DefaultTableModel tabla = (DefaultTableModel) Partida.getModel();
            tabla.setRowCount(0);
            String[] agregar = new String[3];
            //Añado todas las cuentas que van del lado del debe
            consulta = b.executeQuery("SELECT cuentas.Nombre,detalle_partida.Valor FROM detalle_partida INNER JOIN cuentas ON detalle_partida.Cuentas_Id = cuentas.Id WHERE detalle_partida.Partida_Id = "+poliza+" AND detalle_partida.Debe = 1;");           
            while(consulta.next()){
                agregar[0] = consulta.getString(1);
                agregar[1] = consulta.getString(2);
                agregar[2] = "";
                total_debe =  total_debe +Float.parseFloat(agregar[1]);
                tabla.addRow(agregar);
            } 
            //Añado todas las cuentas que van del lado del haber
            consulta = b.executeQuery("SELECT cuentas.Nombre,detalle_partida.Valor FROM detalle_partida INNER JOIN cuentas ON detalle_partida.Cuentas_Id = cuentas.Id WHERE detalle_partida.Partida_Id = "+poliza+" AND detalle_partida.Debe = 0;");           
            while(consulta.next()){
                agregar[0] = consulta.getString(1);
                agregar[1] = "";
                agregar[2] = consulta.getString(2);
                total_haber =  total_haber +Float.parseFloat(agregar[2]);
                tabla.addRow(agregar);
            }
            Debe.setText(Float.toString(total_debe));
            Haber.setText(Float.toString(total_haber));
            }else{
                Tx_Numero.setText("");
                Tx_fecha.setText("");
                Tx_descripcion.setText("");
                DefaultTableModel tabla = (DefaultTableModel)Partida.getModel();
                tabla.setRowCount(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Polizas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Agregar_cuenta;
    private javax.swing.JTextField Debe;
    private javax.swing.JTextField Haber;
    private javax.swing.JTable Partida;
    private javax.swing.JTextField Tx_Numero;
    private javax.swing.JComboBox<String> Tx_año;
    private javax.swing.JTextArea Tx_descripcion;
    private javax.swing.JTextField Tx_fecha;
    private javax.swing.JComboBox<String> Tx_mes;
    private javax.swing.JTextField Tx_nombre_agregar;
    private javax.swing.JTextField Tx_valor_agregar;
    private javax.swing.JButton adelante;
    private javax.swing.JButton atras;
    private javax.swing.JButton bt_aceptar;
    private javax.swing.JButton bt_crear;
    private javax.swing.JRadioButton bt_debe;
    private javax.swing.JRadioButton bt_haber;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.ButtonGroup tipo;
    // End of variables declaration//GEN-END:variables
}
