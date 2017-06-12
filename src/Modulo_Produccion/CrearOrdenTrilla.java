/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modulo_Produccion;

import Excepciones.ExcepcionDatosIncorrectos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana que permite crear Ordenes de Trilla Recibos, siempre que exista por lo menos una Cosecha y un Tipo de Café Pergamino.
 * Una Orden de Trilla es un documento a través del cual se hace constancia de tomar la materia prima (Café de tipo Pergamino)
 * para someterlo a procesos de tratamiento (específicamente tostar el café). La creación de Órdenes de Trilla se hace en
 * base a existencias de uno o varios Recibos, siempre que estos aún tengan un Saldo > 0.
 * Una Orden de Trilla puede incluir varios recibos, aunque no necesariamente completos (por lo que un Recibo puede estar en
 * varias Órdenes de Trilla).
 * @author Wilson Xicará
 */
public class CrearOrdenTrilla extends javax.swing.JFrame {
    private Connection conexion;
    private JFrame ventanaPadre;
    private boolean hacerVisible;
    private int contadorIDOrdenesEnBD, totalSacos;
    private float totalQuintales;
    private DefaultTableModel modelRecibosSeleccionados, modelOrdenesCreadas;
    private ArrayList<Integer> listaIDCosecha, listaIDCafe, listaIDRecibosSelec;
    private Date fechaActual;
    /**
     * Creates new form CrearOrdenTrilla
     */
    public CrearOrdenTrilla() {
        initComponents();
    }
    public CrearOrdenTrilla(Connection conexion, JFrame ventanaPadre) {
        initComponents();
        this.conexion = conexion;
        this.ventanaPadre = ventanaPadre;
        hacerVisible = true;
        listaIDCosecha = new ArrayList<>();
        listaIDCafe = new ArrayList<>();
        
        // Obtengo los datos necesarios desde la Base de Datos
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cConsulta;
            // Obtengo todas las Cosechas (los ID's los almaceno en listaIDCosechas)
            cConsulta = sentencia.executeQuery("SELECT Id, CONCAT(Nombre, ' (', YEAR(Fecha_Inicio), '-', YEAR(Fecha_Fin), ')') Nombre FROM Cosecha");  // Si no hay registros, MAX(Codigo) será NULL
            while (cConsulta.next()) {
                listaIDCosecha.add(cConsulta.getInt("Id"));
                orden_cosecha.addItem(cConsulta.getString("Nombre"));
            } orden_cosecha.setSelectedIndex(-1);
            // Obtención del listado de Tipos de Café (se almacenan los ID's en listaIDCafe)
            cConsulta = sentencia.executeQuery("SELECT Id, Nombre FROM Cafe WHERE Pergamino = 1");
            while (cConsulta.next()) {
                listaIDCafe.add(cConsulta.getInt("Id"));
                tipo_cafe.addItem(cConsulta.getString("Nombre")+" (Pergamino)");
            } tipo_cafe.setSelectedIndex(-1);
            // Verifico que exista por lo menos una Cosecha y un Tipo de Café Pergamino
            hacerVisible = !(listaIDCosecha.isEmpty() || listaIDCafe.isEmpty());
            if (!hacerVisible) {
                String mensaje = "No se pueden crear Órdenes de Trilla pues falta lo siguiente:";
                mensaje+= (listaIDCosecha.isEmpty()) ? "\n -> Cosechas" : "";
                mensaje+= (listaIDCafe.isEmpty()) ? "\n -> Café tipo Pergamino (entran a través de Recibos)" : "";
                mensaje+= "\n\nConsulte con el Administrador para proporcionar dichos datos\na la Base de Datos e inténtelo nuevamente.";
                JOptionPane.showMessageDialog(this, mensaje, "Datos faltantes", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtengo el máximo Numero de Orden correlativo (Codigo) de los dentro de la BD (los nuevos tendrán un Codigo mayor y correlativo)
            cConsulta = sentencia.executeQuery("SELECT COUNT(Codigo), MAX(Codigo) FROM OrdenTrilla");
            cConsulta.next();
            contadorIDOrdenesEnBD = (cConsulta.getInt(1)==0) ? 1 : cConsulta.getInt(2)+1;
            orden_numero.setText(""+contadorIDOrdenesEnBD);
            // Obtengo la fecha de hoy (desde la Base de Datos)
            cConsulta = sentencia.executeQuery("SELECT YEAR(NOW()), MONTH(NOW()), DAY(NOW())");
            cConsulta.next();
            fechaActual = new Date(cConsulta.getInt(1)-1900, cConsulta.getInt(2)-1, cConsulta.getInt(3));
            orden_fecha.setDate(fechaActual);
            cConsulta.close();
            
            // Otros ajustes importantes
            modelRecibosSeleccionados = (DefaultTableModel)tabla_recibos_seleccionados.getModel();
            modelOrdenesCreadas = (DefaultTableModel)tabla_ordenes_creadas.getModel();
            listaIDRecibosSelec = new ArrayList<>();
            totalSacos = 0;
            totalQuintales = 0.0f;
            orden_fecha.getJCalendar().setWeekOfYearVisible(false);  // Para no mostrar el número de semana en el Calendario
            this.setLocationRelativeTo(null);   // Para centrar esta ventana sobre la pantalla.
        } catch (SQLException ex) {
            hacerVisible = false;
            JOptionPane.showMessageDialog(this, "No se puede obtener algunos datos importantes desde la Base de Datos.\n\n"+ex.getMessage(), "Error al intentar obtener datos", JOptionPane.ERROR_MESSAGE);
//            Logger.getLogger(CrearOrdenTrilla.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        orden_numero = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        orden_fecha = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        cliente_proveedor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tipo_cafe = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        preparacion = new javax.swing.JTextField();
        marca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabla_recibos_seleccionados = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        agregar_recibo = new javax.swing.JButton();
        crear_orden_trilla = new javax.swing.JButton();
        eliminar_recibo = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        total_quintales = new javax.swing.JTextField();
        total_sacos = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        orden_cosecha = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabla_ordenes_creadas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear Orden de Trilla");

        jPanel3.setBackground(new java.awt.Color(0, 204, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ORDEN DE TRILLA", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("No.");

        orden_numero.setEditable(false);
        orden_numero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        orden_numero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Fecha:");

        orden_fecha.setDateFormatString("dd/MM/yyyy");
        orden_fecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Cliente/Proveedor:");

        cliente_proveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Clase de café:");

        tipo_cafe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Preparación:");

        preparacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        marca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Marca:");

        tabla_recibos_seleccionados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tabla_recibos_seleccionados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Recibo No.", "Organización (Productor)", "Sacos Nylon", "Sacos Yuta", "Cantidad quintales"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_recibos_seleccionados.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabla_recibos_seleccionados.setRowHeight(25);
        jScrollPane8.setViewportView(tabla_recibos_seleccionados);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Recibos utilizados:");

        agregar_recibo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        agregar_recibo.setText("Agregar Recibo");
        agregar_recibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregar_reciboActionPerformed(evt);
            }
        });

        crear_orden_trilla.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        crear_orden_trilla.setText("Crear Orden");
        crear_orden_trilla.setEnabled(false);
        crear_orden_trilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crear_orden_trillaActionPerformed(evt);
            }
        });

        eliminar_recibo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        eliminar_recibo.setText("Eliminar Recibo");
        eliminar_recibo.setEnabled(false);
        eliminar_recibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminar_reciboActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Total Materia Prima:  Sacos:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Quintales:");

        total_quintales.setEditable(false);
        total_quintales.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        total_quintales.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total_quintales.setText("0.0");

        total_sacos.setEditable(false);
        total_sacos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        total_sacos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total_sacos.setText("0.0");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Cosecha:");

        orden_cosecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(orden_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cliente_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tipo_cafe, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(preparacion, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(marca, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(orden_cosecha, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addGap(0, 4, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(orden_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(agregar_recibo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminar_recibo))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(crear_orden_trilla)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total_sacos, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total_quintales, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(orden_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(orden_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cliente_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tipo_cafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(preparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(orden_cosecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(agregar_recibo)
                    .addComponent(eliminar_recibo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(total_quintales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total_sacos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(crear_orden_trilla))
        );

        jPanel6.setBackground(new java.awt.Color(153, 153, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Órdenes de Trilla creadas:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tabla_ordenes_creadas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tabla_ordenes_creadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Orden No.", "Fecha", "Cliente/Proveedor", "Preparación", "Marca", "Cosecha", "Café"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_ordenes_creadas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabla_ordenes_creadas.setRowHeight(25);
        jScrollPane9.setViewportView(tabla_ordenes_creadas);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void agregar_reciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregar_reciboActionPerformed
        // Llamo a la ventana que se encargará de buscar y 'devolver' el Recibo seleccionado
        int cantidad = orden_cosecha.getItemCount();
        ArrayList<String> listaCosechas = new ArrayList<>();
        for(int i=0; i<cantidad; i++)
            listaCosechas.add(orden_cosecha.getItemAt(i));
        SeleccionarRecibo buscador = new SeleccionarRecibo(this, true, conexion, listaCosechas, listaIDCosecha);
        buscador.setVisible(true);  // Al cerrar la ventana, ya se tienen los datos del Recibo seleccionado
        int idReciboSelec = buscador.getIdRecibo();
        if (idReciboSelec != -1) { // Se seleccionó alguno de los Recibos
            // Compruebo que el registro seleccionado aún no haya sido agregado a la Tabla de Recibos Seleccionados
            int contFil = tabla_recibos_seleccionados.getRowCount(), numeroRecibo = buscador.getNumeroRecibo(), iter;
            boolean reciboYaAgregado = false;
            for(iter=0; iter<contFil; iter++) {
                if (numeroRecibo == Integer.parseInt((String)tabla_recibos_seleccionados.getValueAt(iter, 1))) {
                    reciboYaAgregado = true;
                    break;
                }
            }
            if (reciboYaAgregado) { // No se puede agregar el Recibo
                tabla_recibos_seleccionados.setRowSelectionInterval(iter, iter);
                JOptionPane.showMessageDialog(this,
                        "No se puede agregar el Recibo No."+numeroRecibo+" pues ya fué seleccionado con anterioridad."
                                + "\nRecuerde que no puede seleccionar una parte de un Recibo, dos veces."
                                + "\nEn último caso, elimine el registro y vuélvalo a agregar completamente.",
                        "Datos repetidos", JOptionPane.ERROR_MESSAGE);
            } else {    // El Recibo puede ser agregado
                // Agrego los datos del Recibo seleccionado a la Tabla de Recibos Seleccionados
                listaIDRecibosSelec.add(idReciboSelec); // Agrego el ID
                modelRecibosSeleccionados.addRow(new String[]{
                    ""+(tabla_recibos_seleccionados.getRowCount()+1),
                    ""+numeroRecibo,
                    buscador.getProductor(),
                    ""+buscador.getCantidadSacosNylon(),
                    ""+buscador.getCantidadSacosYuta(),
                    ""+buscador.getCantidadQuintales()
                });
                // Actualizo las variables de conteo general
                totalSacos+= buscador.getCantidadSacosNylon() + buscador.getCantidadSacosYuta();
                totalQuintales+= buscador.getCantidadQuintales();
                total_sacos.setText(""+totalSacos);
                total_quintales.setText(""+totalQuintales);
                // Hasta aquí se garantiza la agregación (parcial o total) de un Recibo a una Orden de Trilla
                if (!crear_orden_trilla.isEnabled()) {
                    crear_orden_trilla.setEnabled(true);    // Se habilita el botón si no estaba habilitado
                    eliminar_recibo.setEnabled(true);
                }
            }
        }
    }//GEN-LAST:event_agregar_reciboActionPerformed

    private void crear_orden_trillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crear_orden_trillaActionPerformed
        try {
            validar_datos_orden();  // Verificación de que todos los datos sean correctos
            // Creación de la Orden de Trilla en la Base de Datos
            Calendar fecha = orden_fecha.getCalendar();
            String instruccion = "INSERT INTO OrdenTrilla(Fecha, Cliente, Preparacion, Marca, CafeUsado, Cosecha_Id) VALUES(";
            instruccion+= "'"+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH)+"', ";
            instruccion+= "'"+cliente_proveedor.getText()+"', ";
            instruccion+= "'"+preparacion.getText()+"', ";
            instruccion+= "'"+marca.getText()+"', ";
            instruccion+= totalQuintales+", ";
            instruccion+= listaIDCosecha.get(orden_cosecha.getSelectedIndex())+")";
            conexion.prepareStatement(instruccion).executeUpdate(); // Creación de la Orden de Trilla
            // En caso de ocurrir un error de almacenamiento, obtengo el ID de la Orden creada (estos errores se dan en el
            // caso de realizar un DELETE en la BD: se elimina el registro pero el correlativo del ID sigue como si nada).
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cOrden = sentencia.executeQuery("SELECT MAX(Codigo) FROM OrdenTrilla");  // Ya existe como mínimo un registro
            cOrden.next();
            contadorIDOrdenesEnBD = cOrden.getInt(1);   // Obtengo el verdadero ID de la Orden de Trilla recién creada
            
            // Creación de Consumos. Se crean tantos Consumos ccomo Recibos se agregaon a la Orden de Trilla
            int contFilas = tabla_recibos_seleccionados.getRowCount();
            for (int fil=0; fil<contFilas; fil++) {
                // Obtención de datos para la creación del i-ésimo Consumo
                int cantSacosNylon = Integer.parseInt((String)tabla_recibos_seleccionados.getValueAt(fil, 3));
                int cantSacosYuta = Integer.parseInt((String)tabla_recibos_seleccionados.getValueAt(fil, 4));
                float cantQuintales = Float.parseFloat((String)tabla_recibos_seleccionados.getValueAt(fil, 5));
                // Creación del Consumo
                instruccion = "INSERT INTO Consumo(Recibo_Codigo, OrdenTrilla_Codigo, CantidadSacosNylon, CantidadSacosYuta, PesoUtilizado) VALUES(";
                instruccion+= listaIDRecibosSelec.get(fil)+", "+contadorIDOrdenesEnBD+", "+cantSacosNylon+", "+cantSacosYuta+", "+cantQuintales+")";
                conexion.prepareStatement(instruccion).executeUpdate();
                // Disminución del Saldo en el i-ésimo Recibo seleccionado en la Orden de Trilla
                instruccion = "UPDATE Recibo SET Saldo = Saldo - "+cantQuintales+" WHERE Id = "+listaIDRecibosSelec.get(fil);
                conexion.prepareStatement(instruccion).executeUpdate();
            }   // Hasta aquí se garantiza la creación de tantos Consumos como Recibos se seleccionaron en la Orden de Trilla creada
            JOptionPane.showMessageDialog(this, "Orden de Trilla No. "+orden_numero.getText()+" creada con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
            
            // Agregación de la Orden de Trilla creada en la tabla
            modelOrdenesCreadas.addRow(new String[]{
                ""+(tabla_ordenes_creadas.getRowCount()+1),
                ""+contadorIDOrdenesEnBD,
                ""+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH),
                cliente_proveedor.getText(),
                preparacion.getText(),
                marca.getText(),
                (String)orden_cosecha.getSelectedItem(),
                (String)tipo_cafe.getSelectedItem()
            });
            
            // Actualización de variables utilizadas
            contadorIDOrdenesEnBD++;
            totalSacos = 0;
            totalQuintales = 0.0f;
            limpiar_campos_de_orden();
        } catch (ExcepcionDatosIncorrectos ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en datos", JOptionPane.ERROR_MESSAGE);
//            Logger.getLogger(CrearOrdenTrilla.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la Orden de Trilla.\n\nDescripción:\n"+ex.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
//            Logger.getLogger(CrearOrdenTrilla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_crear_orden_trillaActionPerformed

    private void eliminar_reciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminar_reciboActionPerformed
        int rango[] = tabla_recibos_seleccionados.getSelectedRows();
        if (rango.length == 1) {
            // Actualización de la Cantidad de Sacos y la Cantidad de Quintales
            totalSacos-= (Integer.parseInt((String)tabla_recibos_seleccionados.getValueAt(rango[0], 3)) + Integer.parseInt((String)tabla_recibos_seleccionados.getValueAt(rango[0], 4)));
            totalQuintales-= Float.parseFloat((String)tabla_recibos_seleccionados.getValueAt(rango[0], 5));
            total_sacos.setText(""+totalSacos);
            total_quintales.setText(""+totalQuintales);
            
            // Eliminación de la fila correspondiente al Recibo que se quiere eliminar
            int contFil = tabla_recibos_seleccionados.getRowCount(), contCol = tabla_recibos_seleccionados.getColumnCount();
            for (int fil=rango[0]; fil<(contFil-1); fil++) {
                tabla_recibos_seleccionados.setValueAt(fil+1, fil, 0);
                for(int col=1; col<contCol; col++) {
                    tabla_recibos_seleccionados.setValueAt((String)tabla_recibos_seleccionados.getValueAt(fil+1, col), fil, col);
                }
            }
            modelRecibosSeleccionados.setRowCount(modelRecibosSeleccionados.getRowCount()-1);
            // Eliminación del ID del Recibo que se ha eliminado
            listaIDRecibosSelec.remove(rango[0]);
            // Hasta aquí se garantiza la eliminación de uno de los Recibos seleccionados
            if (tabla_recibos_seleccionados.getRowCount() == 0) {
                eliminar_recibo.setEnabled(false);
                crear_orden_trilla.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione sólo un registro a la vez", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_eliminar_reciboActionPerformed

    private void validar_datos_orden() throws ExcepcionDatosIncorrectos {
        if (orden_fecha.getDate() == null)
            throw new ExcepcionDatosIncorrectos("Especifique la Fecha de la Orden");
        if (cliente_proveedor.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("El Cliente/Proveedor no puede ser nulo");
        if (tipo_cafe.getSelectedIndex() == -1)
            throw new ExcepcionDatosIncorrectos("Seleccione el Tipo de Café de la Orden");
        if (preparacion.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("Especifique la Preparación de la Orden");
        if (marca.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("Especifique la Marca de la Orden");
        if (orden_cosecha.getSelectedIndex() == -1)
            throw new ExcepcionDatosIncorrectos("Seleccione la Cosecha asociada a la Orden");
    }
    private void limpiar_campos_de_orden() {
        orden_numero.setText(""+contadorIDOrdenesEnBD);
        orden_fecha.setDate(null);
        cliente_proveedor.setText("");
        orden_cosecha.setSelectedIndex(-1);
        tipo_cafe.setSelectedIndex(-1);
        preparacion.setText("");
        marca.setText("");
        modelRecibosSeleccionados.setRowCount(0);   // Limpio la Tabla de Recibos Seleccionados
        total_sacos.setText("0");
        total_quintales.setText("0.0");
        crear_orden_trilla.setEnabled(false);
        eliminar_recibo.setEnabled(false);
    }
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
            java.util.logging.Logger.getLogger(CrearOrdenTrilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrearOrdenTrilla().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregar_recibo;
    private javax.swing.JTextField cliente_proveedor;
    private javax.swing.JButton crear_orden_trilla;
    private javax.swing.JButton eliminar_recibo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField marca;
    private javax.swing.JComboBox<String> orden_cosecha;
    private com.toedter.calendar.JDateChooser orden_fecha;
    private javax.swing.JTextField orden_numero;
    private javax.swing.JTextField preparacion;
    private javax.swing.JTable tabla_ordenes_creadas;
    private javax.swing.JTable tabla_recibos_seleccionados;
    private javax.swing.JComboBox<String> tipo_cafe;
    private javax.swing.JTextField total_quintales;
    private javax.swing.JTextField total_sacos;
    // End of variables declaration//GEN-END:variables
}
