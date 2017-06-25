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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Ventana que permite generar la Planilla de TODOS los Empleados activos, en el Ciclo Contable seleccionado. Debido a que la
 * Planilla se realiza de todos los empleados, muestro a los empleados que están activos.
 * @author Wilson Xicará
 */
public class VerPlanilla extends javax.swing.JFrame {
    private Connection conexion;
    private JFrame ventanaPadre;
    private boolean hacerVisible, cambiosGuardados;
    private ArrayList<Integer> listaIDEmpleo, listaIDPagos, listaIDPartidasDePagos;
    private ArrayList<Boolean> listaPagoModificado;
    private int idCicloSelec, idAnioSelec, idMesSelec, numeroPartida;
    private Date fechaActual;
    private DefaultTableModel modelEmpleos;
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
        hacerVisible = true;
        
        // Obtengo los datos necesarios desde la Base de Datos
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cConsulta;
            // Al crear un nuevo año, se crea automáticamente sus 12 meses, pero después se comprueba si el mes-año ya existe o no como ciclo contable
            // Obtengo el listado de Meses
            cConsulta = sentencia.executeQuery("SELECT Mes FROM Mes");
            while (cConsulta.next())
                ciclo_mes.addItem(cConsulta.getString(1));
            // obtengo el listado de Años
            cConsulta = sentencia.executeQuery("SELECT Anio FROM Anio");
            while (cConsulta.next())
                ciclo_anio.addItem(cConsulta.getString(1));
            // Obtengo el listado de todos los Trabajadores (Empleos) que están activos
            String instruccion = "SELECT Empleo.Id idEmpleo, Personal.Nombre, Personal.DPI, Puesto.Nombre_Puesto, Puesto.esAdministracion, Puesto.esProduccion, Puesto.esVentas, Empleo.SueldoBase, 250 Bonificacion FROM Empleo "
                    + "INNER JOIN Personal ON Empleo.Personal_Id = Personal.Id "
                    + "INNER JOIN Puesto ON Empleo.Puesto_Id = Puesto.Id "
                    + "WHERE Empleo.Vigente = 1";
            cConsulta = sentencia.executeQuery(instruccion);
            int contador = 0;
            listaIDEmpleo = new ArrayList<>();
            modelEmpleos = (DefaultTableModel) tabla_empleados.getModel();
            while(cConsulta.next()) {
                listaIDEmpleo.add(cConsulta.getInt("idEmpleo"));
                String sector = "";
                if (cConsulta.getBoolean("esAdministracion")) sector = "Administración";
                else if (cConsulta.getBoolean("esProduccion")) sector = "Producción";
                else if (cConsulta.getBoolean("esVentas")) sector = "Ventas";
                modelEmpleos.addRow(new String[]{
                    ""+(++contador),
                    cConsulta.getString("Nombre"),
                    cConsulta.getString("DPI"),
                    cConsulta.getString("Nombre_Puesto"),
                    sector,
                    String.format("%.2f", cConsulta.getFloat("SueldoBase")),
                    String.format("%.2f", cConsulta.getFloat("Bonificacion")),
                    // Los datos específicos de un ciclo contable estarán como 0.0
                    "0.00",  // La cantidad total de Horas Extra
                    "0.00",  // El Precio del total de Horas Extra
                    "0.00",  // El IGSS, pues está en función del Precio total de Horas Extra
                    "0.00",  // Los Préstamos, que pueden ser editados
                    "0.00"   // El Sueldo Total, que depende de todos los datos anteriores
                });
            }   // Hasta aquí se garantiza la obtención de todos los Empleos activos
            jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total de Empleados: "+listaIDEmpleo.size(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));
            
            // Obtención la Fecha de hoy (desde la Base de Datos)
            cConsulta = sentencia.executeQuery("SELECT NOW()");
            cConsulta.next();
            fecha_pagos.setDate(fechaActual = cConsulta.getDate(1));
            fecha_pagos.getJCalendar().setWeekOfYearVisible(false);  // Para no mostrar el número de semana en el Calendario
            cConsulta.close();
            // Otras configuraciones importantes
            cambiosGuardados = true;    // Indicador de cuándo se ha guardado los cambios realizados en los Pagos
            idCicloSelec = idAnioSelec = idMesSelec = numeroPartida = 0;   // Enteros que tendrán el ID del Ciclo Contable Seleccionado, y el Número de la Partida que se creará
            listaIDPagos = new ArrayList<>();
            listaIDPartidasDePagos = new ArrayList<>();
            listaPagoModificado = new ArrayList<>();
            this.setLocationRelativeTo(null);   // Para centrar esta ventana sobre la pantalla.
        } catch (SQLException ex) {
            hacerVisible = false;
            JOptionPane.showMessageDialog(this, "No se puede obtener alguno de los listados desde la Base de Datos.\n\n"+ex.getMessage(), "Error al intentar obtener datos", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(VerPlanilla.class.getName()).log(Level.SEVERE, null, ex);
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

        generar_pagos = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_empleados = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        ciclo_anio = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        fecha_pagos = new com.toedter.calendar.JDateChooser();
        mostrar_pagos = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        prestamo = new javax.swing.JTextField();
        editar_prestamo = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        sueldo_sin_prestamo = new javax.swing.JTextField();
        etiqueta_aviso = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ciclo_mes = new javax.swing.JComboBox<>();
        menu = new javax.swing.JMenuBar();
        menu_crear = new javax.swing.JMenu();
        item_generar_planilla = new javax.swing.JMenuItem();
        item_eliminar_planilla = new javax.swing.JMenuItem();
        item_ver_planilla = new javax.swing.JMenuItem();
        item_generar_constancias = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Planilla");

        generar_pagos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        generar_pagos.setText("Generar/Actualizar Pagos");
        generar_pagos.setEnabled(false);
        generar_pagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generar_pagosActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total de Empleados:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        tabla_empleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Empleado", "DPI", "Puesto", "Sector", "Sueldo Base (Q.)", "Bonificación (Q.)", "Horas Extra", "Total Horas Extra (Q.)", "IGSS (Q.)", "Préstamos (Q.)", "Sueldo Total (Q.)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_empleados.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabla_empleados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabla_empleados.getTableHeader().setReorderingAllowed(false);
        tabla_empleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabla_empleadosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_empleados);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Año:");

        ciclo_anio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ciclo_anioItemStateChanged(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Fecha actual:");

        mostrar_pagos.setText("Ver Pagos del Mes");
        mostrar_pagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrar_pagosActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Préstamo: Q.");

        prestamo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                prestamoFocusLost(evt);
            }
        });
        prestamo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                prestamoKeyTyped(evt);
            }
        });

        editar_prestamo.setText("Editar Préstamo");
        editar_prestamo.setEnabled(false);
        editar_prestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editar_prestamoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Sueldo sin Préstamo: Q.");

        sueldo_sin_prestamo.setEditable(false);

        etiqueta_aviso.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Ciclo contable:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Mes:");

        ciclo_mes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ciclo_mesItemStateChanged(evt);
            }
        });

        menu_crear.setText("Crear");

        item_generar_planilla.setText("Generar Planilla");
        item_generar_planilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_generar_planillaActionPerformed(evt);
            }
        });
        menu_crear.add(item_generar_planilla);

        item_eliminar_planilla.setText("Eliminar Planilla");
        item_eliminar_planilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_eliminar_planillaActionPerformed(evt);
            }
        });
        menu_crear.add(item_eliminar_planilla);

        item_ver_planilla.setText("Ver Planilla");
        item_ver_planilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_ver_planillaActionPerformed(evt);
            }
        });
        menu_crear.add(item_ver_planilla);

        item_generar_constancias.setText("Generar Constancias");
        item_generar_constancias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_generar_constanciasActionPerformed(evt);
            }
        });
        menu_crear.add(item_generar_constancias);

        menu.add(menu_crear);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fecha_pagos, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(generar_pagos))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(sueldo_sin_prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(editar_prestamo))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etiqueta_aviso)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ciclo_anio, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ciclo_mes, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mostrar_pagos)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fecha_pagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel3))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ciclo_anio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(ciclo_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mostrar_pagos))
                .addGap(3, 3, 3)
                .addComponent(etiqueta_aviso)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sueldo_sin_prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editar_prestamo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(generar_pagos)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Evento que notifica al usuario si el Ciclo Contable que ha seleccionado está o no activo.
     * Se podrán editar Préstamos sí y sólo si el Ciclo Contable está Activo.
     * @param evt 
     */
    private void ciclo_anioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ciclo_anioItemStateChanged
        /*int index = ciclo_anio.getSelectedIndex();
        if (index != -1)
            etiqueta_aviso.setText(listaCicloActivo.get(index) ? "" : "El Ciclo Contable seleccionado ya no está Activo. No podrá editar los Préstamos");
        */
    }//GEN-LAST:event_ciclo_anioItemStateChanged
    /**
     * Acción que muestra los datos de los Pagos asociados a los Empleos, en el Ciclo Contable seleccionado, siempre que
     * estos ya existan (se haya generado la Planilla del Ciclo anteriormente).
     * @param evt 
     */
    private void mostrar_pagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrar_pagosActionPerformed
        if ("Ver Pagos del Mes".equals(mostrar_pagos.getText())) {    // Se realizará una búsqueda
            mostrar_pagos.setText("Nueva búsqueda");
            ciclo_mes.setEnabled(false);    // Esto para evitar generar Planilla de un Ciclo Contable cambiado
            ciclo_anio.setEnabled(false);
            
            // Ahora obtengo la información de las Horas Extra e IGSS de todos los Empleos mostrados
            buscar_pagos_por_ciclo();
            cambiosGuardados = true;    // Como sólo se mostraron datos, no hay cambios en los datos
            
            generar_pagos.setEnabled(true);  // Se puede Generar Pagos sí y solo sí ya se ha fijado el Ciclo Contable
            editar_prestamo.setEnabled(false);  // Se puede editar un Préstamo hasta que se seleccione un registro
            prestamo.setText("");   // Limpio este campo, donde se podrá editar los Préstamos
            sueldo_sin_prestamo.setText("");
        } else {    // Se habilita la opción para realizar una nueva búsqueda
            mostrar_pagos.setText("Ver Pagos del Mes");
            ciclo_anio.setEnabled(true);
            ciclo_mes.setEnabled(true);
            generar_pagos.setEnabled(false); // Estos botónes se habilitarán hasta que ya no se pueda cambiar el Ciclo Contable
        }
    }//GEN-LAST:event_mostrar_pagosActionPerformed
    /**
     * Evento que controla cuando se pierde el Focus.
     * Si el texto es '' se convierte a '0.00'
     * Si el texto es 'nnn.' se convierte a 'nnn.00'
     * Si el texto es 'nnn.abc' se convierte a 'nnn.ad' (donde ad es la aproximación de abc)
     * @param evt 
     */
    private void prestamoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_prestamoFocusLost
        if (prestamo.getText().length() == 0)
            prestamo.setText("0.00");
        else if (prestamo.getText().indexOf(".") == (prestamo.getText().length()-1))
            prestamo.setText(prestamo.getText()+"00");
        else
            prestamo.setText(String.format("%.2f", Float.parseFloat(prestamo.getText())));
    }//GEN-LAST:event_prestamoFocusLost
    /**
     * Evento que controla que el préstamo tenga el formato correcto.
     * @param evt 
     */
    private void prestamoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prestamoKeyTyped
        char tecla = evt.getKeyChar();
        if (tecla == '.') {
            if (prestamo.getText().contains("."))
                evt.consume();  // No se permite ingresar un valor como NN..N (con dos puntos consecutivos)
            else if (prestamo.getText().length() == 0)
                prestamo.setText("0");   // Si la primera tecla es '.' se convierte en '0.'
        } else if (!Pattern.compile("\\d").matcher(String.valueOf(evt.getKeyChar())).matches())
            evt.consume();
    }//GEN-LAST:event_prestamoKeyTyped

    private void tabla_empleadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_empleadosMousePressed
        // Se carga la Cantidad del Préstamo que ha realizado el Empleado seleccionado
        int indexEmpleo = tabla_empleados.getSelectedRow();
        prestamo.setText((String)tabla_empleados.getValueAt(tabla_empleados.getSelectedRow(), 10));
        float sueldoSinPrestamo = Float.parseFloat((String)tabla_empleados.getValueAt(indexEmpleo, 10)) + Float.parseFloat((String)tabla_empleados.getValueAt(indexEmpleo, 11));
        sueldo_sin_prestamo.setText(String.format("%.2f", sueldoSinPrestamo));
        if (!editar_prestamo.isEnabled())
            editar_prestamo.setEnabled(true);   // Al seleccionar un empleo, se puede editar el Préstamo que tenga
    }//GEN-LAST:event_tabla_empleadosMousePressed

    private void editar_prestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editar_prestamoActionPerformed
        int index = tabla_empleados.getSelectedRow();   // Se asume que se tiene seleccionado un registro Empleado
        // Verifico que el Préstamo no sea mayor al Sueldo Total (para ello, sumo el Préstamo anterior)
        float sueldoAnterior = Float.parseFloat(sueldo_sin_prestamo.getText());
        if (sueldoAnterior > Float.parseFloat(prestamo.getText())) {    // Es un Préstamo válido. Actualizo dicho Préstamo
            // El nuevo Sueldo Total disminuye o aumenta según aumente o disminuye el nuevo Préstamo respecto al anterior
            // Regreso el Préstamo anterior al Sueldo Anterior para calcular los respectivos nuevos
            float nuevoSueldoTotal = sueldoAnterior - Float.parseFloat(prestamo.getText());
            tabla_empleados.setValueAt(prestamo.getText(), index, 10);   // Actualizo el Préstamo en la Tabla
            tabla_empleados.setValueAt(String.format("%.2f", nuevoSueldoTotal), index, 11); // Actualizo el nuevo Sueldo Total en la Tabla
            prestamo.setText("");
            sueldo_sin_prestamo.setText("");
            editar_prestamo.setEnabled(false);  // Inhabilito este botón hasta seleccionar un nuevo registro
            cambiosGuardados = false;   // Al modificar un registro, no se guarda automáticamente
            listaPagoModificado.add(index, true);   // Indicador de que este pago ya fué modificado
        } else    // El Préstamo es mayor al Sueldo Total. No puede realizarse
            JOptionPane.showMessageDialog(this, "No puede realizar un Préstamo que supere la cantidad del Sueldo Total", "Error en datos", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_editar_prestamoActionPerformed
    /**
     * Esta acción permite crear (si aún no existen) o actualizar (si ya existen) los Pagos en la Base de Datos, asociados
     * al Ciclo Contable y los Empleos mostrados en la Tabla.
     * @param evt 
     */
    private void generar_pagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generar_pagosActionPerformed
        // En idCicloSelec está el ID del Ciclo. Se crearán o actualizarán los Pagos de todos los Empleados mostrados.
        // De preferencia, la Partida debe ser creada hasta cuando se enté seguro de que ya no se modificarán Pagos
        int cantidad = listaIDEmpleo.size();
        Calendar fecha = fecha_pagos.getCalendar();
        String fechaS = ""+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH);
        String mensaje = "Se generarán los Pagos con la siguiente información:"
                + "\n -> Todos los Empleos"
                + "\n -> Ciclo Contable:                                 '"+(String)ciclo_mes.getSelectedItem()+" "+(String)ciclo_anio.getSelectedItem()+"'"
                + "\n -> Fecha de última actualización de Pagos:     '"+fecha.get(Calendar.DAY_OF_MONTH)+"/"+(fecha.get(Calendar.MONTH)+1)+"/"+fecha.get(Calendar.YEAR)+"'"
                + "\nTomando como base los datos mostrados en la Tabla."
                + "\n\nDesea continuar?";
        int opcion = JOptionPane.showOptionDialog(this, mensaje, "Aviso", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (opcion == JOptionPane.YES_OPTION) {
            // Creación o Actualización de todos los Pagos mostrados, en el Ciclo Contable seleccionado
            for(int cont=0; cont<cantidad; cont++) {    // Inicio la creación o actualización de los Pagos en el Ciclo seleccionado
                int idEmpleo = listaIDEmpleo.get(cont);
                try {
                    String instruccion;
                    // Verifico si se creará o actualizará el Pago del cont-ésimo Empleo en el Ciclo Contable seleccionado
                    if (listaIDPagos.get(cont) != -1) {  // Se actualizará el Préstamo y la Fecha del Pago
                        instruccion = "UPDATE Pago SET Prestamo = "+(String)tabla_empleados.getValueAt(cont, 10)+", Fecha = '"+fechaS+"' WHERE Id = "+listaIDPagos.get(cont);
                    } else {    // Se creará el Pago
                        instruccion = "INSERT INTO Pago(Empleo_Id, CicloContable_Id, Fecha, CantidadHorasExtras, IGSS, Prestamo, PrecioHoraExtra) VALUES(";
                        instruccion+= idEmpleo+", "+idCicloSelec+", ";
                        instruccion+= "'"+fechaS+"', ";
                        instruccion+= (String)tabla_empleados.getValueAt(cont, 7)+", ";
                        instruccion+= (String)tabla_empleados.getValueAt(cont, 9)+", ";
                        instruccion+= (String)tabla_empleados.getValueAt(cont, 10)+", ";
                        instruccion+= String.format("%.2f", ((Float.parseFloat((String)tabla_empleados.getValueAt(cont, 5))/(30*8))*1.5f))+")";
                    }
                    conexion.prepareStatement(instruccion).executeUpdate();
                    if (listaIDPagos.get(cont) == -1) { // Obtengo el ID del Pago recién creado
                        ResultSet cConsulta = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
                                .executeQuery("SELECT LAST_INSERT_ID()");
                        cConsulta.next();
                        listaIDPagos.add(cont, cConsulta.getInt(1));    // Obtiene el ID del último registro 'insertado'
                    }
                    // No modifico el marcador de modificado de los Pagos pues me servirá a la hora de crear o actualizar la Partida
                    // Hasta aquí se garantiza la creación o actualización del Pago del cont-ésimo Empleo en el Ciclo Contable seleccionado
                    
                } catch (SQLException ex) {
                    System.out.println("Error al intentar crear el Pago del Empleado.Id = "+listaIDEmpleo.get(cont));
//                    Logger.getLogger(VerPlanilla.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            cambiosGuardados = true;    // Indicador de que ya se guardaron los cambios
            JOptionPane.showMessageDialog(this, "Se han actualizado los datos exitosamente.\nAhora ya puede Ver la Planilla.", "Datos actualizados", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_generar_pagosActionPerformed

    private void ciclo_mesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ciclo_mesItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ciclo_mesItemStateChanged
    /**
     * Acción que permite generar la Planilla de los Pagos asociados a los Empleos mostrados, en el Ciclo Contable seleccionado.
     * En realidad, la Planilla consiste en ver la forma en la que se estructura el Pago al Empleado por lo que esta acción
     * permite crear la Partida asociada a los Sueldos de los Empleos mostrados.
     * @param evt 
     */
    private void item_generar_planillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_generar_planillaActionPerformed
        String mensaje = "Creación de la Partida asociada a todos los Pagos mostrados en la Tabla.";
        if (cambiosGuardados) {
            mensaje+= "\n\nActualizar los Pagos de una Partida ya existente puede demorar más tiempo.";
        } else {
            mensaje+= "\n\nNo se han guardado los cambios de los Pagos, por lo que pueden perderse."
                    + "\nDé clic en el botón 'Generar/Actualizar Pagos' para guardar dichos cambios.";
        }
        mensaje+= "\n\nDesea continuar?";
        int opcion = JOptionPane.showOptionDialog(this, mensaje, "Aviso", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (opcion == JOptionPane.YES_OPTION) {     // Se creará la Partida, con los datos mostrados actualmente
            int cantidad = listaIDEmpleo.size(), idPartida = 0;
            Calendar fecha = fecha_pagos.getCalendar();
            String fechaS = ""+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH);
            
            // CREACIÓN DE UNA NUEVA PARTIDA PARA LOS PAGOS QUE NO HAN SIDO RELACIONADOS A UNA
            // Obtención del ID de las cuentas implicadas en la creación de la Partida (si se creará alguna)
            int idSueldosAdmin, idSueldosProd, idSueldosVentas, idBonosAdmin, idBonosProd, idBonosVentas, idCuotaPatAdmin, idCuotaPatProd, idCuotaPatVentas, idCuotaPatPorPagar, idCuotaLabIGSS, idAnticipoSueldos, idSueldoPorPagar;
            idSueldosAdmin = idSueldosProd = idSueldosVentas = idBonosAdmin = idBonosProd = idBonosVentas = idCuotaPatAdmin = idCuotaPatProd = idCuotaPatVentas = idCuotaPatPorPagar = idCuotaLabIGSS = idAnticipoSueldos = idSueldoPorPagar = 0;
            try {
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet cConsulta;
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Sueldos Administración'");
                cConsulta.next();
                idSueldosAdmin = cConsulta.getInt(1);   // Obtención del ID de la Cuenta 'Sueldos Administración'
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Sueldos Producción'");
                cConsulta.next();
                idSueldosProd = cConsulta.getInt(1);    // Obtención del ID de la Cuenta 'Sueldos Producción'
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Sueldos Ventas'");
                cConsulta.next();
                idSueldosVentas = cConsulta.getInt(1);  // Obtención del ID de la Cuenta 'Sueldos Ventas'
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Bonificación Incentivo Administración'");
                cConsulta.next();
                idBonosAdmin = cConsulta.getInt(1); // Obtención del ID de la Cuenta 'Bonificación Incentivo Administración'
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Bonificación Incentivo Producción'");
                cConsulta.next();
                idBonosProd = cConsulta.getInt(1);  // Obtención del ID de la Cuenta 'Bonificación Incentivo Producción'
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Bonificación Incentivo Ventas'");
                cConsulta.next();
                idBonosVentas = cConsulta.getInt(1);    // Obtención del ID de la Cuenta 'Bonificación Incentivo Ventas'
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Cuota Patronal Administración'");
                cConsulta.next();
                idCuotaPatAdmin = cConsulta.getInt(1);  // Obtención del ID de la Cuenta 'Cuota Patronal Administración'
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Cuota Patronal Producción'");
                cConsulta.next();
                idCuotaPatProd = cConsulta.getInt(1);   // Obtención del ID de la Cuenta 'Cuota Patronal Producción'
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Cuota Patronal Ventas'");
                cConsulta.next();
                idCuotaPatVentas = cConsulta.getInt(1); // Obtención del ID de la Cuenta 'Cuota Patronal Ventas'
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Cuota Patronal por Pagar'");
                cConsulta.next();
                idCuotaPatPorPagar = cConsulta.getInt(1);   // Obtención del ID de la Cuenta 'Cuota Patronal por Pagar'
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Cuota Laboral IGSS'");
                cConsulta.next();
                idCuotaLabIGSS = cConsulta.getInt(1);   // Obtención del ID de la Cuenta 'Cuota Laboral IGSS'
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Anticipo sobre Sueldos'");
                cConsulta.next();
                idAnticipoSueldos = cConsulta.getInt(1);    // Obtención del ID de la Cuenta 'Anticipo sobre Sueldos'
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Sueldos por Pagar'");
                cConsulta.next();
                idSueldoPorPagar = cConsulta.getInt(1); // Obtención del ID de la Cuenta 'Sueldos por Pagar'
            } catch (SQLException ex) {
                System.out.println("Error al extraer el ID de una de las Cuentas:\n"+ex.getMessage());
                Logger.getLogger(VerPlanilla.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Obtención de sumatorias totales de valores importantes para los Pagos que no tienen Partida
            float sueldosAdmin, sueldosProd, sueldosVentas, bonosAdmin, bonosProd, bonosVentas, cuotaPatAdmin, cuotaPatProd, cuotaPatVentas;
            float cuotaPatPorPagar, cuotaLabIGSS, anticipoSueldos, sueldoPorPagar;
            sueldosAdmin = sueldosProd = sueldosVentas = bonosAdmin = bonosProd = bonosVentas = cuotaPatAdmin = cuotaPatProd = cuotaPatVentas = 0f;
            cuotaPatPorPagar = cuotaLabIGSS = anticipoSueldos = sueldoPorPagar = 0f;
            int contPagosSinPartida = 0;
            for(int cont=0; cont<cantidad; cont++) {
                if (listaIDPartidasDePagos.get(cont) == -1) {   // El Pago no tiene partida. Acumulo datos
                    // Acumulación de sueldos y otros registros que servirán para los Detalles de la Partida
                    String sector = (String)tabla_empleados.getValueAt(cont, 4);
                    // Acumulación de Sueldos (Sueldo Base + Total Horas Extra) y Cuota Patronal
                    float sueldoBaseMasHorasE = Float.parseFloat((String)tabla_empleados.getValueAt(cont, 5)) + Float.parseFloat((String)tabla_empleados.getValueAt(cont, 8));
                    float cuotaPatronal = sueldoBaseMasHorasE*0.1267f;
                    if ("Administración".equals(sector)) {
                        sueldosAdmin+= sueldoBaseMasHorasE;
                        bonosAdmin+= Float.parseFloat((String)tabla_empleados.getValueAt(cont, 6));
                        cuotaPatAdmin+= cuotaPatronal;
                    } else if ("Producción".equals(sector)) {
                        sueldosProd+= sueldoBaseMasHorasE;
                        bonosProd+= Float.parseFloat((String)tabla_empleados.getValueAt(cont, 6));
                        cuotaPatProd+= cuotaPatronal;
                    } else if ("Ventas".equals(sector)) {
                        sueldosVentas+= sueldoBaseMasHorasE;
                        bonosVentas+= Float.parseFloat((String)tabla_empleados.getValueAt(cont, 6));
                        cuotaPatVentas+= cuotaPatronal;
                    }
                    cuotaPatPorPagar+= cuotaPatronal;   // Acumulación de la Cuota Patronal por Pagar
                    cuotaLabIGSS+= sueldoBaseMasHorasE*0.0483f; // Acumulación de la Cuota Laboral IGSS
                    anticipoSueldos+= Float.parseFloat((String)tabla_empleados.getValueAt(cont, 10));   // Acumulación de los Anticipos sobre Sueldos (Préstamos)
                    sueldoPorPagar+= Float.parseFloat((String)tabla_empleados.getValueAt(cont, 11));    // Acumulación del Sueldo por Pagar
                    contPagosSinPartida++;
                }
            }   // Hasta aquí se garantiza la acumulación de los detalles de Pagos que no tiene Partida
            
            // CREACIÓN DE UNA NUEVA PARTIDA, en caso de que haya al menos un Pago que no tenga Partida (que no esté registrado)
            if (contPagosSinPartida > 0) {  // Creación de la Partida asociada a los Pagos sin partida
                try {
                    mensaje = "INSERT INTO Partida(CicloContable_Id, Numero, Fecha, Descripción) VALUES(";
                    mensaje+= idCicloSelec+", "+numeroPartida+", ";
                    mensaje+= "'"+fechaS+"', ";
                    mensaje+= "'Pago de Sueldos, Ciclo "+(String)ciclo_mes.getSelectedItem()+" "+(String)ciclo_anio.getSelectedItem()+"')";
                    conexion.prepareStatement(mensaje).executeUpdate();  // Creación del Registro de la Partida
                    Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                    ResultSet cConsulta = sentencia.executeQuery("SELECT LAST_INSERT_ID()");    // Obtiene el ID del último registro 'insertado'
                    cConsulta.next();
                    idPartida = cConsulta.getInt(1);    // Obtención del ID de la Partida recién creada
                    // Creación de los Detalles de la Partida recién creada
                    String instruccion = "";
                    if (sueldosAdmin > 0f) {
                        // Creación del Detalle para 'Sueldos Administración'
                        instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idSueldosAdmin+", "+String.format("%.2f", sueldosAdmin)+", 1)";
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (sueldosProd > 0f) {
                        // Creación del Detalle para 'Sueldos Producción'
                        instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idSueldosProd+", "+String.format("%.2f", sueldosProd)+", 1)";
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (sueldosVentas > 0f) {
                        // Creación del Detalle para 'Sueldos Ventas'
                        instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idSueldosVentas+", "+String.format("%.2f", sueldosVentas)+", 1)";
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (bonosAdmin > 0f) {
                        // Creación del Detalle para 'Bonificación Incentivo Administración'
                        instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idBonosAdmin+", "+String.format("%.2f", bonosAdmin)+", 1)";
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (bonosProd > 0f) {
                        // Creación del Detalle para 'Bonificación Incentivo Producción'
                        instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idBonosProd+", "+String.format("%.2f", bonosProd)+", 1)";
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (bonosVentas > 0f) {
                        // Creación del Detalle para 'Bonificación Incentivo Ventas'
                        instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idBonosVentas+", "+String.format("%.2f", bonosVentas)+", 1)";
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (cuotaPatAdmin > 0f) {
                        // Creación del Detalle para 'Cuota Patronal Administración'
                        instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idCuotaPatAdmin+", "+String.format("%.2f", cuotaPatAdmin)+", 1)";
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (cuotaPatProd > 0f) {
                        // Creación del Detalle para 'Cuota Patronal Producción'
                        instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idCuotaPatProd+", "+String.format("%.2f", cuotaPatProd)+", 1)";
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (cuotaPatVentas > 0f) {
                        // Creación del Detalle para 'Cuota Patronal Ventas'
                        instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idCuotaPatVentas+", "+String.format("%.2f", cuotaPatVentas)+", 1)";
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    // Creación del Detalle para 'Cuota Patronal por Pagar'
                    instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idCuotaPatPorPagar+", "+String.format("%.2f", cuotaPatPorPagar)+", 0)";
                    conexion.prepareStatement(instruccion).executeUpdate();
                    // Creación del Detalle para 'Cuota Laboral IGSS'
                    instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idCuotaLabIGSS+", "+String.format("%.2f", cuotaLabIGSS)+", 0)";
                    conexion.prepareStatement(instruccion).executeUpdate();
                    // Creación del Detalle para 'Anticipo sobre Sueldos'
                    instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idAnticipoSueldos+", "+String.format("%.2f", anticipoSueldos)+", 0)";
                    conexion.prepareStatement(instruccion).executeUpdate();
                    // Creación del Detalle para 'Sueldos por Pagar'
                    instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("+idPartida+", "+idSueldoPorPagar+", "+String.format("%.2f", sueldoPorPagar)+", 0)";
                    conexion.prepareStatement(instruccion).executeUpdate();
                    // En la Base de Datos, hay un TRIGGER encargado de actualizar el Saldo de las Cuentas asociadas a todos
                    // los Detalles de la Partida Creada
                } catch (SQLException ex) {
                    idPartida = -1; // En caso de que ocurra un error (que sería muy raro)
                    System.out.println("Error de conexión:\n"+ex.getMessage());
//                    Logger.getLogger(VerPlanilla.class.getName()).log(Level.SEVERE, null, ex);
                }
                // Ahora, actualizo el IdPartida de todos los Pagos con idPartida == -1
                for(int i=0; i<cantidad; i++)
                    if (listaIDPartidasDePagos.get(i) == -1)
                        listaIDPartidasDePagos.add(i, idPartida);
            }   // HASTA AQUÍ SE GARANTIZA LA CREACIÓN DE TODOS LOS DETALLES DE LA PARTIDA
            
            // ACTUALIZACIÓN DE UNA PARTIDA, en caso de que haya al menos un Pago que tenga Partida
            // Al agregar una nueva Hora Extra se actualiza dicho valor en el Pago, más no se refleja en la Partida hasta
            // que se crea o actualiza la Partida. Por consiguiente, debo actualizar las Horas Extra y los Préstamos de
            // todos los Pagos que ya tienen una Partida (asumiendo de que se agregó un horario extra o se modificó el Préstamo)
            // Los Detalle_Partida a actualizar son los que tenga un Detalle_Partida.Partida_Id != idPartida
            int idPartidaActualizar = -1;   // Si se queda con -1, TODOS los pagos se agregar a una Partida nueva
            for(int cont=0; cont<cantidad; cont++) {    // Actualizo el Detalle_Partida de los Pagos que fueron modificados
                if (listaIDPartidasDePagos.get(cont) != idPartida) {    // Este pago pertenece a una Partida creada con anterioridad
                    if (idPartidaActualizar == -1)
                        idPartidaActualizar = listaIDPartidasDePagos.get(cont);
                    // Acumulación de sueldos y otros registros que servirán para actualizar los Detalles de la Partida
                    String sector = (String)tabla_empleados.getValueAt(cont, 4);
                    // Acumulación de Sueldos (Sueldo Base + Total Horas Extra) y Cuota Patronal
                    float sueldoBaseMasHorasE = Float.parseFloat((String)tabla_empleados.getValueAt(cont, 5)) + Float.parseFloat((String)tabla_empleados.getValueAt(cont, 8));
                    float cuotaPatronal = sueldoBaseMasHorasE*0.1267f;
                    if ("Administración".equals(sector)) {
                        sueldosAdmin+= sueldoBaseMasHorasE;
                        bonosAdmin+= Float.parseFloat((String)tabla_empleados.getValueAt(cont, 6));
                        cuotaPatAdmin+= cuotaPatronal;
                    } else if ("Producción".equals(sector)) {
                        sueldosProd+= sueldoBaseMasHorasE;
                        bonosProd+= Float.parseFloat((String)tabla_empleados.getValueAt(cont, 6));
                        cuotaPatProd+= cuotaPatronal;
                    } else if ("Ventas".equals(sector)) {
                        sueldosVentas+= sueldoBaseMasHorasE;
                        bonosVentas+= Float.parseFloat((String)tabla_empleados.getValueAt(cont, 6));
                        cuotaPatVentas+= cuotaPatronal;
                    }
                    cuotaPatPorPagar+= cuotaPatronal;   // Acumulación de la Cuota Patronal por Pagar
                    cuotaLabIGSS+= sueldoBaseMasHorasE*0.0483f; // Acumulación de la Cuota Laboral IGSS
                    anticipoSueldos+= Float.parseFloat((String)tabla_empleados.getValueAt(cont, 10));   // Acumulación de los Anticipos sobre Sueldos (Préstamos)
                    sueldoPorPagar+= Float.parseFloat((String)tabla_empleados.getValueAt(cont, 11));    // Acumulación del Sueldo por Pagar
                    contPagosSinPartida++;
                }
            }
            // Actualización de los detalles de la Partida relacionada a los Pagos que ya tienen Partida
            // Si idPartidaActualizar != -1 se actualizará los detalles de la Partida con Id = idPartidaActualizar
            if (idPartidaActualizar != -1) {
                try {
                    String instruccion = "";
                    if (sueldosAdmin > 0f) {
                        // Actualización del Detalle para 'Sueldos Administración'
                        instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", sueldosAdmin)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idSueldosAdmin;
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (sueldosProd > 0f) {
                        // Actualización del Detalle para 'Sueldos Producción'
                        instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", sueldosProd)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idSueldosProd;
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (sueldosVentas > 0f) {
                        // Actualización del Detalle para 'Sueldos Ventas'
                        instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", sueldosVentas)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idSueldosVentas;
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (bonosAdmin > 0f) {
                        // Actualización del Detalle para 'Bonificación Incentivo Administración'
                        instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", bonosAdmin)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idBonosAdmin;
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (bonosProd > 0f) {
                        // Actualización del Detalle para 'Bonificación Incentivo Producción'
                        instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", bonosProd)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idBonosProd;
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (bonosVentas > 0f) {
                        // Actualización del Detalle para 'Bonificación Incentivo Ventas'
                        instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", bonosVentas)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idBonosVentas;
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (cuotaPatAdmin > 0f) {
                        // Actualización del Detalle para 'Cuota Patronal Administración'
                        instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", cuotaPatAdmin)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idCuotaPatAdmin;
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (cuotaPatProd > 0f) {
                        // Actualización del Detalle para 'Cuota Patronal Producción'
                        instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", cuotaPatProd)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idCuotaPatProd;
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    if (cuotaPatVentas > 0f) {
                        // Actualización del Detalle para 'Cuota Patronal Ventas'
                        instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", cuotaPatVentas)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idCuotaPatVentas;
                        conexion.prepareStatement(instruccion).executeUpdate();
                    }
                    // Actualización del Detalle para 'Cuota Patronal por Pagar'
                    instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", cuotaPatPorPagar)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idCuotaPatPorPagar;
                    conexion.prepareStatement(instruccion).executeUpdate();
                    // Actualización del Detalle para 'Cuota Laboral IGSS'
                    instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", cuotaLabIGSS)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idCuotaLabIGSS;
                    conexion.prepareStatement(instruccion).executeUpdate();
                    // Actualización del Detalle para 'Anticipo sobre Sueldos'
                    instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", anticipoSueldos)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idAnticipoSueldos;
                    conexion.prepareStatement(instruccion).executeUpdate();
                    // Actualización del Detalle para 'Sueldos por Pagar'
                    instruccion = "UPDATE Detalle_Partida SET Valor = "+String.format("%.2f", sueldoPorPagar)+" WHERE Partida_Id = "+idPartidaActualizar+" AND Cuentas_Id = "+idSueldoPorPagar;
                    conexion.prepareStatement(instruccion).executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Error al intentar actualizar los Detalles de la Partida con Id = "+idPartidaActualizar+":\n"+ex.getMessage());
//                    Logger.getLogger(VerPlanilla.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   // Hasta aquí se garantiza la actualización de los Detalles de Partida que estén relacionados a los Pagos mostrados
            JOptionPane.showMessageDialog(this, "Se han actualizado los datos exitosamente.\nAhora ya puede Ver la Planilla.", "Datos actualizados", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_item_generar_planillaActionPerformed
    /**
     * Acción que permite eliminar la Planilla de los Pagos asociados a los Empleos mostrados, en el Ciclo Contable seleccionado.
     * Para ello, 
     * @param evt 
     */
    private void item_eliminar_planillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_eliminar_planillaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_item_eliminar_planillaActionPerformed

    private void item_ver_planillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_ver_planillaActionPerformed
        try {
            String Id_ciclo_contable= ""+idCicloSelec; //Id de la relacion entre año y mes
            String[] infoCiclo = ((String)ciclo_anio.getSelectedItem()).split(" - ");
            String Mes = infoCiclo[1]; //Mes de la planilla
            String Año = infoCiclo[0]; //Año de la planilla
            Map parametros  = new HashMap();
            parametros.put("NumMes", Id_ciclo_contable);
            parametros.put("Mes", Mes);
            parametros.put("Año", Año);
            
            JasperReport reporte = JasperCompileManager.compileReport("src\\Reportes\\Planilla.jrxml");
            JasperPrint a = JasperFillManager.fillReport(reporte, parametros,conexion);
            
            JasperViewer.viewReport(a,false);
        } catch (JRException ex) {
            Logger.getLogger(VerPlanilla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_item_ver_planillaActionPerformed

    private void item_generar_constanciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_generar_constanciasActionPerformed
        try {
            String Id_ciclo_contable= ""+idCicloSelec; //Id de la relacion entre año y mes
            String[] infoCiclo = ((String)ciclo_anio.getSelectedItem()).split(" - ");
            
            String Mes = infoCiclo[1]; //Mes de la planilla
            String Año = infoCiclo[0]; //Año de la planilla
            Map parametros  = new HashMap();
            parametros.put("CicloEscolar", Id_ciclo_contable);
            parametros.put("Mes", Mes);
            parametros.put("Año", Año);
            
            JasperReport reporte = JasperCompileManager.compileReport("src\\Reportes\\Constancias.jrxml");
            JasperPrint a = JasperFillManager.fillReport(reporte, parametros,conexion);
            
            JasperViewer.viewReport(a,false);
        } catch (JRException ex) {
            Logger.getLogger(VerPlanilla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_item_generar_constanciasActionPerformed

    /**
     * Este método buscará, extraerá y calculará los precios de las Horas Extras, el IGSS y el Sueldo Total de los Empleos
     * (almacenados en listaIDEmpleo), en el Ciclo Contable que esté seleccionado.
     */
    private void buscar_pagos_por_ciclo() {
        // Para obtener el detalle de los pagos hay dos opciones:
        // 1 - Los Pagos aún no se han creado. En este caso, los costos los obtengo mediante consultas y cálculos
        // 2 - Los Pagos ya se han creado. En este caso, sólo extraigo la información correspondiente
        listaIDPagos.clear();   // Elimino los pagos de la Búsqueda anterior
        listaIDPartidasDePagos.clear(); // Elimino el ID de las Partidas a los que pertenecen los Pagos anteriores
        listaPagoModificado.clear();    // Elimino todos los indicadores de que si los Pagos ha sido o no modificados
        
        // Obtención de datos que servirán como referencia en la creación de Pagos y Partidas
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cConsulta;
            // Obtención del ID del Ciclo Contable compuesto del Mes y Año seleccionado. Lo siguiente se hace en caso de que
            // en las tablas de Anio y Mes en la BD el ID de ambas tablas pierda su orden correlativo en algún punto (aunque
            // sería por problemas serios, pero de esta forma no arrojaría error a la hora de consultar la BD)
            cConsulta = sentencia.executeQuery("SELECT CicloContable.Id, Anio.Id, Mes.Id FROM CicloContable "
                    + "INNER JOIN Anio ON CicloContable.Anio_Id = Anio.Id "
                    + "INNER JOIN Mes ON CicloContable.Mes_Id = Mes.Id "
                    + "WHERE Anio.Anio = "+(String)ciclo_anio.getSelectedItem()+" AND Mes.Mes = '"+(String)ciclo_mes.getSelectedItem()+"'");
            cConsulta.next();
            idCicloSelec = cConsulta.getInt(1);
            idAnioSelec = cConsulta.getInt(2);
            idMesSelec = cConsulta.getInt(3);
            // Obtención del Número de Partida que tendrá la nueva que posiblemente se vaya a crear
            cConsulta = sentencia.executeQuery("SELECT COUNT(Numero), MAX(Numero) FROM Partida WHERE CicloContable_Id = "+idCicloSelec);
            cConsulta.next();
            numeroPartida = (cConsulta.getInt(1)==0) ? 1 : cConsulta.getInt(2)+1;
        } catch (SQLException ex) {
            idCicloSelec = idAnioSelec = idMesSelec = numeroPartida = -1;  // En caso de ocurrir un error, no habrá algún Ciclo Contable seleccionado (error raro)
            System.out.println("Error al obtener el ID del CicloContable:\n"+ex.getMessage());
//            Logger.getLogger(VerPlanilla.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Obtención de los Pagos asociados a los Empleos, en el Ciclo Contable seleccionado
        int cantidad = listaIDEmpleo.size();
        for(int cont=0; cont<cantidad; cont++) {    // Muestro el Pago de todos los Empleos, en el Ciclo seleccionado
            // Obtengo los datos de Pago del cont-ésimo Empleo en el Ciclo seleccionado. El cont-ésimo Pago ya está en una
            // Partida sí y sólo si ya existe
            try {
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet cConsulta;
                float sueldoBase, cantidadHorasExtra, precioHoraExtra, totalHorasExtra, totalPrestamo, igss;
                sueldoBase = Float.parseFloat((String)tabla_empleados.getValueAt(cont, 5));   // Obtengo el Sueldo Base
                // Intento obtener el Pago
                cConsulta = sentencia.executeQuery("SELECT Pago.Id idPago, CantidadHorasExtras, PrecioHoraExtra, IGSS, Prestamo, Partida_Id FROM Pago "
                        + "WHERE Empleo_Id = "+listaIDEmpleo.get(cont)+" AND CicloContable_Id = "+idCicloSelec);
                if (cConsulta.next()) { // El Pago del cont-ésimo Empleo ya existe. Obtengo sus datos
                    listaIDPagos.add(cConsulta.getInt("idPago"));
                    cantidadHorasExtra = cConsulta.getFloat("CantidadHorasExtras");
                    precioHoraExtra = cConsulta.getFloat("PrecioHoraExtra");
                    totalHorasExtra = cantidadHorasExtra * precioHoraExtra; // Cálculo del Costo Total de las Horas Extra
                    igss = cConsulta.getFloat("IGSS");
                    totalPrestamo = cConsulta.getFloat("Prestamo");
                    listaIDPartidasDePagos.add(cConsulta.getInt("Partida_Id")); // Si ya se generó la Partida, obtengo su ID en caso de actualizar el Pago
                } else {    // El Pago aún no ha sido creado. Calculo los Datos
                    listaIDPagos.add(-1);   // Indicador de que el Pago aún no ha sido creado
                    listaIDPartidasDePagos.add(-1);  // Indicador de que no está relacionado a ninguna Partida
                    cConsulta = sentencia.executeQuery("SELECT COUNT(HorasExtra), ROUND(SUM(HorasExtra),2) FROM HorasExtras "
                            + "WHERE Empleo_Id = "+listaIDEmpleo.get(cont)+" AND CicloContable.Id = "+idCicloSelec);  // Esta consulta sólo devuelve dos celdas
                    cConsulta.next();
                    cantidadHorasExtra = (cConsulta.getInt(1)==0) ? 0.0f : cConsulta.getFloat(2);   // Cálculo de la Cantidad de Horas Extra
                    precioHoraExtra = (sueldoBase/(30*8))*1.5f; // Cálculo del Precio por Hora Extra
                    totalHorasExtra = cantidadHorasExtra * precioHoraExtra; // Cálculo del Costo Total de las Horas Extra
                    igss = (sueldoBase + totalHorasExtra)*0.0483f;  //
                    totalPrestamo = 0.0f;    // Si el pago es nuevo, no tiene Préstamo
                }
                listaPagoModificado.add(false); // En cualquier caso, inicialmente no se ha modificado ningún pago
                // Ahora inserto el detalle del Pago (que existe o no) en la tabla
                tabla_empleados.setValueAt(String.format("%.2f", cantidadHorasExtra), cont, 7); // Inserto la Cantidad de Horas Extra
                tabla_empleados.setValueAt(String.format("%.2f", totalHorasExtra), cont, 8); // Inserto el Precio Total las de Horas Extra
                tabla_empleados.setValueAt(String.format("%.2f", igss), cont, 9);   // Inserto el IGSS
                tabla_empleados.setValueAt(String.format("%.2f", totalPrestamo), cont, 10);  // Inserto el Total de Préstamo
                tabla_empleados.setValueAt(String.format("%.2f", (sueldoBase + 250f + totalHorasExtra - igss - totalPrestamo)), cont, 11);  // Inserto el Sueldo Total
            } catch (SQLException ex) {
                System.out.println("Error al obtener los registros del Empleo.Id = "+listaIDEmpleo.get(cont));
//                Logger.getLogger(VerPlanilla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
    private class Anio {
        private int ID, anio;
        public Anio() { ID = anio = 0; }
        public Anio(int id, int anio) {
            ID = id;
            this.anio = anio;
        }
    }
    private class Mes {
        private int ID;
        private String mes;
        public Mes() {
            ID = 0;
            mes = "";
        }
        public Mes(int id, String mes) {
            ID = id;
            this.mes = mes;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ciclo_anio;
    private javax.swing.JComboBox<String> ciclo_mes;
    private javax.swing.JButton editar_prestamo;
    private javax.swing.JLabel etiqueta_aviso;
    private com.toedter.calendar.JDateChooser fecha_pagos;
    private javax.swing.JButton generar_pagos;
    private javax.swing.JMenuItem item_eliminar_planilla;
    private javax.swing.JMenuItem item_generar_constancias;
    private javax.swing.JMenuItem item_generar_planilla;
    private javax.swing.JMenuItem item_ver_planilla;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu menu_crear;
    private javax.swing.JButton mostrar_pagos;
    private javax.swing.JTextField prestamo;
    private javax.swing.JTextField sueldo_sin_prestamo;
    private javax.swing.JTable tabla_empleados;
    // End of variables declaration//GEN-END:variables
}
