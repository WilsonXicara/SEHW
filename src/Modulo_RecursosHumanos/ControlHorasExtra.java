/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modulo_RecursosHumanos;

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
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pc
 */
public class ControlHorasExtra extends javax.swing.JFrame {
    private Connection conexion;
    private JFrame ventanaPadre;
    private boolean hacerVisible, datosCargados;
    private ArrayList<Integer> listaIDPuestos, listaIDCiclos, listaIDEmpleo;
    private Date fechaActual;
    private DefaultTableModel modelEmpleos, modelHorasExtas;
    /**
     * Creates new form ControlEmpleos
     */
    public ControlHorasExtra() {
        initComponents();
    }
    public ControlHorasExtra(Connection conexion, JFrame ventanaPadre) {
        initComponents();
        this.conexion = conexion;
        this.ventanaPadre = ventanaPadre;
        datosCargados = !(hacerVisible = true); // Inicialmente no se han cargado los datos, y se intentará mostrar esta ventana
        listaIDPuestos = new ArrayList<>();
        listaIDCiclos = new ArrayList<>();
        
        // Obtengo los datos necesarios desde la Base de Datos
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cConsulta;
            // Obtención del listado de Puestos
            cConsulta = sentencia.executeQuery("SELECT Id, Nombre_Puesto FROM Puesto");
            while (cConsulta.next()) {
                listaIDPuestos.add(cConsulta.getInt("Id"));
                puesto.addItem(cConsulta.getString("Nombre_Puesto"));
            } puesto.addItem("TODOS");  // En caso de que se quieran mostrar a todos los trabajadores activos
            // Obtención del listado de todos los Ciclos Contables existentes (compuestos de mes y año)
            cConsulta = sentencia.executeQuery("SELECT CicloContable.Id idCiclo, CONCAT(Anio.Anio, ' - ', Mes.Mes) Ciclo FROM CicloContable "
                    + "INNER JOIN Anio ON CicloContable.Anio_Id = Anio.Id "
                    + "INNER JOIN Mes ON CicloContable.Mes_Id = Mes.Id");
            while (cConsulta.next()) {
                listaIDCiclos.add(cConsulta.getInt("idCiclo"));
                ciclo_contable.addItem(cConsulta.getString("Ciclo"));
            }
            datosCargados = true;
            // En caso de no existir algún Puesto o algún Ciclo contable, no se mostrará esta ventana
            if (listaIDPuestos.isEmpty() || listaIDCiclos.isEmpty()) {
                hacerVisible = false;
                String mensaje = "No se puede mostrar el Control de Empleados pues falta lo siguiente:";
                mensaje+= (listaIDPuestos.isEmpty()) ? "\n -> Puestos laborales" : "";
                mensaje+= (listaIDCiclos.isEmpty()) ? "\n -> Ciclos contables" : "";
                mensaje+= "\n\nConsulte con el Administrador para proporcionar dichos datos\na la Base de Datos e inténtelo nuevamente.";
                JOptionPane.showMessageDialog(this, mensaje, "Datos faltantes", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Obtención la Fecha de hoy (desde la Base de Datos)
            cConsulta = sentencia.executeQuery("SELECT YEAR(NOW()), MONTH(NOW()), DAY(NOW())");
            cConsulta.next();
            fechaActual = new Date(cConsulta.getInt(1)-1900, cConsulta.getInt(2)-1, cConsulta.getInt(3));
            fecha_horas_extras.setDate(fechaActual);
            cConsulta.close();
            // Otras configuraciones importantes
            listaIDEmpleo = new ArrayList<>();
            modelEmpleos = (DefaultTableModel)tabla_empleos.getModel();
            modelHorasExtas = (DefaultTableModel)tabla_horas_extras.getModel();
            fecha_horas_extras.getJCalendar().setWeekOfYearVisible(false);  // Para no mostrar el número de semana en el Calendario
            this.setLocationRelativeTo(null);   // Para centrar esta ventana sobre la pantalla.
        } catch (SQLException ex) {
            hacerVisible = false;
            JOptionPane.showMessageDialog(this, "No se puede obtener alguno de los listados desde la Base de Datos.\n\n"+ex.getMessage(), "Error al intentar obtener datos", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ControlHorasExtra.class.getName()).log(Level.SEVERE, null, ex);
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
        ciclo_contable = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        puesto = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_empleos = new javax.swing.JTable();
        etiqueta_tabla_trabajadores = new javax.swing.JLabel();
        etiqueta_tabla_horas_extra = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_horas_extras = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        fecha_horas_extras = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        hora_inicio = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        hora_fin = new javax.swing.JTextField();
        mostrar_trabajadores = new javax.swing.JButton();
        agregar_nueva_hora_extra = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        horas_extra = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar horas extras"));

        ciclo_contable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ciclo_contableItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Ciclo contable:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Puesto:");

        tabla_empleos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Nombre", "Puesto", "Fecha Inicio", "Fecha Fin"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_empleos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabla_empleos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabla_empleosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_empleos);

        etiqueta_tabla_trabajadores.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiqueta_tabla_trabajadores.setText("Trabajadores vigentes:");

        etiqueta_tabla_horas_extra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiqueta_tabla_horas_extra.setText("Registro de horas extras:");

        tabla_horas_extras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Fecha", "Hora Inicio", "Hora Fin", "Horas Extras"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_horas_extras.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(tabla_horas_extras);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Fecha:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Hora Inicio:");

        hora_inicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                hora_inicioFocusLost(evt);
            }
        });
        hora_inicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hora_inicioKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Hora Fin:");

        hora_fin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                hora_finFocusLost(evt);
            }
        });
        hora_fin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hora_finKeyTyped(evt);
            }
        });

        mostrar_trabajadores.setText("Mostrar trabajadores");
        mostrar_trabajadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrar_trabajadoresActionPerformed(evt);
            }
        });

        agregar_nueva_hora_extra.setText("Agregar Horas extras");
        agregar_nueva_hora_extra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregar_nueva_hora_extraActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Horas Extras:");

        horas_extra.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ciclo_contable, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(puesto, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(mostrar_trabajadores)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiqueta_tabla_trabajadores))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(etiqueta_tabla_horas_extra)
                        .addContainerGap())
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fecha_horas_extras, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                    .addComponent(hora_inicio))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(agregar_nueva_hora_extra)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(hora_fin, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(horas_extra, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ciclo_contable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(puesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mostrar_trabajadores))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiqueta_tabla_trabajadores)
                    .addComponent(etiqueta_tabla_horas_extra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(fecha_horas_extras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hora_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hora_fin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(horas_extra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(agregar_nueva_hora_extra)
                        .addGap(0, 50, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Acción que muestra todos los trabajadores activos asociados al Puesto seleccionado.
     * Al seleccionar un trabajador, se mostrará las horas extras que ha tenido en el Ciclo Contable seleccionado
     * @param evt 
     */
    private void mostrar_trabajadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrar_trabajadoresActionPerformed
        // Borro los registro de la búsqueda anterior
        modelEmpleos.setRowCount(0);
        listaIDEmpleo.clear();
        int indexPuesto = puesto.getSelectedIndex();    // Este JComboBox nunca tendrá .getSelectedIndex() == -1
        String instruccion = "SELECT Empleo.Id idEmpleo, Personal.Nombre, Puesto.Nombre_Puesto, Empleo.FechaInicio, Empleo.FechaFin FROM Empleo "
                + "INNER JOIN Personal ON Empleo.Personal_Id = Personal.Id "
                + "INNER JOIN Puesto ON Empleo.Puesto_Id = Puesto.Id "
                + "WHERE Empleo.Vigente = 1";
        if (indexPuesto != (puesto.getItemCount()-1))
            instruccion+= " AND Puesto.Id = "+listaIDPuestos.get(indexPuesto);
        // Obtengo el listado de todos los trabajadores activos que concuerdan con la búsqueda
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cConsulta = sentencia.executeQuery(instruccion);
            int contador = 0;
            while(cConsulta.next()) {
                listaIDEmpleo.add(cConsulta.getInt("idEmpleo"));
                modelEmpleos.addRow(new String[]{
                    ""+(++contador),
                    cConsulta.getString("Nombre"),
                    cConsulta.getString("Nombre_Puesto"),
                    cConsulta.getString("FechaInicio"),
                    cConsulta.getString("FechaFin")
                });
            }   // Hasta aquí se garantiza la obtención de todos los trabajadores activos
            etiqueta_tabla_trabajadores.setText("Trabajadores vigentes: "+contador);
            limpiar_horas_extras();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al intentar obtener los registros desde la Base de Datos.\n\nDescripción:\n"+ex, "Error de conexión", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ControlHorasExtra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mostrar_trabajadoresActionPerformed
    /**
     * Acción que permite mostrar todas las Horas Extras del Trabajador seleccionado, correspondientes al Ciclo Contable
     * que se tiene seleccionado. Tener en cuenta que lo que seleccionan son Empleos.
     * @param evt 
     */
    private void tabla_empleosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_empleosMousePressed
        // Borro los datos ingresados en los campos de agregación de una nueva Hora Extra
        fecha_horas_extras.setDate(fechaActual);
        hora_inicio.setText("");
        hora_fin.setText("");
        horas_extra.setText("");
        // Al hacer una selección (que no necesariamente es igual al anterior), se obtienen nuevos datos
        modelHorasExtas.setRowCount(0);
        int indexEmpleo = tabla_empleos.getSelectedRow();
        int indexCiclo = ciclo_contable.getSelectedIndex();
        String instruccion = "SELECT Fecha, HoraInicio, HoraFin FROM HorasExtras "
                + "WHERE CicloContable_Id = "+listaIDCiclos.get(indexCiclo)+" AND Empleo_Id = "+listaIDEmpleo.get(indexEmpleo);
        // Obtengo el listado de todas las Horas Extras en el Ciclo Contable y del Trabajador seleccionado
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cConsulta = sentencia.executeQuery(instruccion);
            int contador = 0;
            while(cConsulta.next()) {
                modelHorasExtas.addRow(new String[]{
                    ""+(++contador),
                    cConsulta.getString("Fecha"),
                    cConsulta.getString("HoraInicio"),
                    cConsulta.getString("HoraFin"),
                    ""+obtener_horas_extras(cConsulta.getString("HoraInicio"), cConsulta.getString("HoraFin"), 8)
                });
            }   // Hasta aquí se garantiza la obtención de todos las Horas Extras en el Ciclo del Trabajador seleccionado
            etiqueta_tabla_horas_extra.setText("Horas extra del trabajador No. "+(indexEmpleo+1)+": "+contador);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al intentar obtener el listado de Horas Extra.\n\nDescripción:\n"+ex.getMessage(), "Error en conexión", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ControlHorasExtra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabla_empleosMousePressed

    private void ciclo_contableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ciclo_contableItemStateChanged
        if (datosCargados)
            limpiar_horas_extras();
    }//GEN-LAST:event_ciclo_contableItemStateChanged
    /**
     * Evento utilizado para controlar que la hora ingresada tenga el formato HH:MM:SS (dos dígitos por cada uno)
     * @param evt 
     */
    private void hora_inicioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hora_inicioKeyTyped
        char tecla = evt.getKeyChar();
        int cantidad = hora_inicio.getText().length();
        /*
        
        
        if (cantidad < 6) {
            if (!Pattern.compile("\\d").matcher(String.valueOf(tecla)).matches())
                evt.consume();
        } else if (cantidad == 7) {
            if (tecla != '-')
                evt.consume();
        } else if (cantidad < 9) {
            if (!Pattern.compile("\\d").matcher(String.valueOf(tecla)).matches())
                evt.consume();
        } else {
            evt.consume();
        }*/
    }//GEN-LAST:event_hora_inicioKeyTyped
    
    private void hora_finKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hora_finKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_hora_finKeyTyped

    private void hora_inicioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hora_inicioFocusLost
        // Evento para cuando hora_inicio pierde el Focus
    }//GEN-LAST:event_hora_inicioFocusLost

    private void hora_finFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hora_finFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_hora_finFocusLost
    /**
     * Acción que permite asignar una nueva hora extra al Trabajador en el Ciclo Contable seleccionados
     * @param evt 
     */
    private void agregar_nueva_hora_extraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregar_nueva_hora_extraActionPerformed
        try {
            validar_datos_horas_extra();    // Verifico que los datos sean correctos
            Calendar fecha = fecha_horas_extras.getCalendar();
            float horasExtra = obtener_horas_extras(hora_inicio.getText(), hora_fin.getText(), 8);
            String mensaje = (horasExtra == 0.0f ? "Al parecer el horario no genera Horas Extra.\nSe guardará el registro, aunque es irrelevante." :
                    "El horario ingresado genera "+horasExtra+" horas extra.") + "\n\nDesea continuar?";
            // Puede que no se registra horas extra o sólo se pide confirmación
            int opcion = JOptionPane.showOptionDialog(this, mensaje, "Aviso", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (opcion == JOptionPane.YES_OPTION) {
                String instruccion = "INSERT INTO HorasExtras(CicloContable_Id, Empleo_Id, Fecha, HoraInicio, HoraFin, HorasExtra) VALUES(";
                instruccion+= listaIDCiclos.get(ciclo_contable.getSelectedIndex())+", ";
                instruccion+= listaIDEmpleo.get(tabla_empleos.getSelectedRow())+", ";
                instruccion+= "'"+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH)+"', ";
                instruccion+= "'"+hora_inicio.getText()+"', '"+hora_fin.getText()+"', ";
                instruccion+= horasExtra+")";
                conexion.prepareStatement(instruccion).executeUpdate();

                // Agrego los nuevos datos a la tabla de horas extra
                modelHorasExtas.addRow(new String[]{
                    ""+(tabla_horas_extras.getRowCount()+1),
                    ""+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH),
                    hora_inicio.getText(),
                    hora_fin.getText(),
                    ""+horasExtra
                });
                etiqueta_tabla_horas_extra.setText("Horas extra del trabajador No. "+(tabla_empleos.getSelectedRow()+1)+": "+tabla_horas_extras.getRowCount());
                // Limpio los campos donde se ingresó la hora extra agregada
                fecha_horas_extras.setDate(fechaActual);
                hora_inicio.setText("");
                hora_fin.setText("");
                horas_extra.setText("");
            }
        } catch (ExcepcionDatosIncorrectos ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en datos", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ControlHorasExtra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al intentar guardar el registro.\n\nDescripción:\n"+ex.getMessage(), "Error en conexión", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ControlHorasExtra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_agregar_nueva_hora_extraActionPerformed

    private void validar_datos_horas_extra() throws ExcepcionDatosIncorrectos {
        if (fecha_horas_extras.getDate() == null)
            throw new ExcepcionDatosIncorrectos("Especifique la Fecha del registro de horario");
        if (hora_inicio.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("Especifique la Hora de Entrada");
        if (hora_fin.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("Especifique la Hora de Salida");
    }
    private void limpiar_horas_extras() {
        // Elimino la selección de cualquiera de las filas de la Tabla Empleos (siempre que existan filas)
        if (tabla_empleos.getRowCount() > 0) {
            tabla_empleos.removeRowSelectionInterval(0, tabla_empleos.getRowCount()-1);
        }
        // Limpio los datos mostrados en la Tabla de Horas Extras, al igual que los campos de una nueva Hora Extra
        etiqueta_tabla_horas_extra.setText("Registro de Horas Extra:");
        modelHorasExtas.setRowCount(0);
        fecha_horas_extras.setDate(fechaActual);
        hora_inicio.setText("");
        hora_fin.setText("");
        horas_extra.setText("");
    }
    /**
     * Función que devuelve la cantidad de horas extras de un trabajador, en base a la horaInicio y horaFin pasados como
     * parámetros. Los String de horas deben tener el formato HH:MM:SS (horas, minutos, segundos).
     * Ejemplo: sea horaFin = "18:48:21" y horaInicio = "07:32:45"
     * - Un día tiene 86400 segundos por lo que:
     *   - horaInicio = (7*3600 + 32*60 + 45) = 27165 segundos
     *   - horaFin = (18*3600 + 48*60 + 21) = 67701 segundos
     *   - horaFin - horaInicio = 40536 segundos = 11.26 horas
     * - Tomando como base las 8 horas laborales se tiene que 11.26 - 8 = 3.26 horas extras
     * @param horaInicio
     * @param horaFin
     * @param horasLaborales
     * @return 
     */
    private float obtener_horas_extras(String horaInicio, String horaFin, int horasLaborales) {
        String[] cpHoraI = horaInicio.split(":"), cpHoraF = horaFin.split(":");     // Ambos arreglos tendrán 3 posiciones
        int segundosInicio = 3600*Integer.parseInt(cpHoraI[0]) + 60*Integer.parseInt(cpHoraI[1]) + Integer.parseInt(cpHoraI[2]);
        int segundosFin = 3600*Integer.parseInt(cpHoraF[0]) + 60*Integer.parseInt(cpHoraF[1]) + Integer.parseInt(cpHoraF[2]);
        float horasExtra = (float)(segundosFin-segundosInicio)/3600 - (float)horasLaborales;
        return (horasExtra > 0.0f) ? horasExtra : 0.0f;
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
            java.util.logging.Logger.getLogger(ControlHorasExtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ControlHorasExtra().setVisible(true);
            }
        });
    }
    private void otroMetodo() {/*
        // Para limpiar el JComboBox que muestra los meses, y sobreescribirlo
        ((JComboBox)mes.getComboBox()).removeAllItems();
        for(int i=0; i<24; i++)
            ((JComboBox)mes.getComboBox()).addItem("Mes número "+(i+1));
        // Para limpiar los años que se muestran, y agregar los que quiero
//        anio.removeAll();
        anio.setDayChooser(new JDayChooser(false));
        anio.setStartYear(2015);
        anio.setEndYear(2020);
//        for(int i=2010; i<2020; i++)
//            anio.setValue(i);
//        anio.repaint();
        System.out.println("Rango de años: ["+anio.getStartYear()+","+anio.getEndYear()+"]");
        
//        System.out.println("Hola = "+((JComboBox)mes.getComboBox()).getSelectedItem());*/
    }
    
    private class AnioContable {
        private int id, anio;
        private ArrayList<MesContable> meses;
        public AnioContable() {
            id = anio = 0;
            meses = new ArrayList<>();
        }
        public AnioContable(int id, int anio) {
            this.id = id;
            this.anio = anio;
            meses = new ArrayList<>();
        }
        public void setID(int id) { this.id = id; }
        public void setAnio(int anio) { this.anio = anio; }
        public void addMes(MesContable mes) { meses.add(mes); }
        public void addMeses(ArrayList<MesContable> meses) { this.meses = meses; }
        public int getID() { return id; }
        public int getAnio() { return anio; }
        public MesContable getMes(int index) { return meses.get(index); }
        public ArrayList<MesContable> getMeses() { return meses; }
    }
    private class MesContable {
        private int id;
        private String mes;
        public MesContable() {
            id = 0;
            mes = "";
        }
        public MesContable(int id, String mes) {
            this.id = id;
            this.mes = mes;
        }
        public void setID(int id) { this.id = id; }
        public void setMes(String mes) { this.mes = mes; }
        public int getID() { return id; }
        public String getMes() { return mes; }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregar_nueva_hora_extra;
    private javax.swing.JComboBox<String> ciclo_contable;
    private javax.swing.JLabel etiqueta_tabla_horas_extra;
    private javax.swing.JLabel etiqueta_tabla_trabajadores;
    private com.toedter.calendar.JDateChooser fecha_horas_extras;
    private javax.swing.JTextField hora_fin;
    private javax.swing.JTextField hora_inicio;
    private javax.swing.JTextField horas_extra;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton mostrar_trabajadores;
    private javax.swing.JComboBox<String> puesto;
    private javax.swing.JTable tabla_empleos;
    private javax.swing.JTable tabla_horas_extras;
    // End of variables declaration//GEN-END:variables
}
