/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modulo_Finanzas;

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
 * @author Wilson Xicará
 */
public class CrearCheque extends javax.swing.JFrame {
    private Connection conexion;
    private JFrame ventanaPadre;
    private boolean hacerVisible, datosCargados;
    private ArrayList<Integer> listaIDCuentasBancarias, listaNumeroUltimoCheque, listaIDCuentasContables, listaIDCuentasCargadas;
    private ArrayList<String> listaNumeroCuenta, listaNombrePropietario, listaCuentasContables;
    private int idCicloContable;
    private Date fechaActual;
    private DefaultTableModel modelCuentas;
    private float porcentajeAcumulado, subtotales, totalFactura;
    /**
     * Creates new form CrearCheque
     */
    public CrearCheque() {
        initComponents();
    }
    public CrearCheque(Connection conexion, JFrame ventanaPadre) {
        initComponents();
        this.conexion = conexion;
        this.ventanaPadre = ventanaPadre;
        datosCargados = !(hacerVisible = true);
        
        // Obtengo los datos necesarios para crear un cheque, desde la Base de Datos
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cConsulta;
            // Obtengo el listado de Cuentas Bancarias existentes, y la información a mostrar en el Cheque
            cConsulta = sentencia.executeQuery("SELECT * FROM CuentaBancaria");
            listaIDCuentasBancarias = new ArrayList<>();
            listaNumeroCuenta = new ArrayList<>();
            listaNombrePropietario = new ArrayList<>();
            listaNumeroUltimoCheque = new ArrayList<>();
            while (cConsulta.next()) {
                listaIDCuentasBancarias.add(cConsulta.getInt("Id"));
                listaNumeroCuenta.add(cConsulta.getString("NumeroCuenta"));
                listaNombrePropietario.add(cConsulta.getString("NombrePropietario"));
                listaNumeroUltimoCheque.add(cConsulta.getInt("UltimoNumero"));
                cuenta_bancaria.addItem(cConsulta.getString("NombreBanco")+" ("+cConsulta.getString("NumeroCuenta")+")");
            } cuenta_bancaria.setSelectedIndex(-1);
            datosCargados = true;
            // Si no hay cuentas bancarias, no se podrá extender cheques (no se mostrará la ventana)
            if (listaIDCuentasBancarias.isEmpty()) {
                hacerVisible = false;
                JOptionPane.showMessageDialog(this, "No se pueden extender Cheques ya que no existe ninguna Cuenta Bancaria.\n\nConsulte con el Administrador e inténtelo nuevamente.", "Datos faltantes", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Obtengo ID y Nombre de las Cuentas contables a los que se le puede cargar el cheque, siempre que no sea Bancos o una Cuenta de Patrimonio
            cConsulta = sentencia.executeQuery("SELECT Id, Nombre FROM Cuentas WHERE Nombre != 'Bancos' AND Patrimonio = 0 ORDER BY Nombre ASC");
            listaIDCuentasContables = new ArrayList<>();
            listaCuentasContables = new ArrayList<>();
            while (cConsulta.next()) {
                listaIDCuentasContables.add(cConsulta.getInt("Id"));
                listaCuentasContables.add(cConsulta.getString("Nombre"));
            }
            // Obtengo la fecha de hoy (desde la Base de Datos)
            cConsulta = sentencia.executeQuery("SELECT NOW()");
            cConsulta.next();
            fechaActual = cConsulta.getDate(1);
            fecha_cheque.setDate(fechaActual);
            // Otras configuraciones importantes
            listaIDCuentasCargadas = new ArrayList<>();
            modelCuentas = (DefaultTableModel)tabla_cuentas_cargadas.getModel();
            fecha_cheque.getJCalendar().setWeekOfYearVisible(false);  // Para no mostrar el número de semana en el Calendario
            tabla_cuentas_cargadas.setShowHorizontalLines(true);    // Para mostrar las lineas de la tabla
            tabla_cuentas_cargadas.setShowVerticalLines(true);
            this.setLocationRelativeTo(null);   // Para centrar esta ventana sobre la pantalla
        } catch (SQLException ex) {
            hacerVisible = false;
            JOptionPane.showMessageDialog(this, "Error al intentar obtener datos.\n\nDescripción:\n"+ex.getMessage(), "Error en conexión", JOptionPane.ERROR_MESSAGE);
//            Logger.getLogger(CrearCheque.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        numero_cheque = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lugar_cheque = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fecha_cheque = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        receptor_cheque = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cantidad_cheque = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cuenta_bancaria = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        numero_cuenta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        propietario_cuenta = new javax.swing.JTextField();
        agregar_cuenta = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_cuentas_cargadas = new javax.swing.JTable();
        agregar_porcentaje_cuenta = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        porcentaje_cuenta = new javax.swing.JTextField();
        eliminar_cuenta = new javax.swing.JButton();
        crear_cheque = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        cantidad_letras_cheque = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Extender Cheques");

        jLabel1.setText("Cheque No.");

        numero_cheque.setEditable(false);

        jLabel3.setText("Lugar:");

        jLabel4.setText("Fecha:");

        fecha_cheque.setDateFormatString("dd/MM/yyyy");

        jLabel5.setText("Pago a la Orden de:");

        jLabel6.setText("Cantidad: Q.");

        cantidad_cheque.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cantidad_chequeFocusLost(evt);
            }
        });
        cantidad_cheque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cantidad_chequeKeyTyped(evt);
            }
        });

        jLabel7.setText("Cantidad en Letras:");

        jLabel8.setText("Cuenta en Banco:");

        cuenta_bancaria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cuenta_bancariaItemStateChanged(evt);
            }
        });

        jLabel9.setText("Cuenta No.");

        numero_cuenta.setEditable(false);

        jLabel10.setText("Propietario:");

        propietario_cuenta.setEditable(false);

        agregar_cuenta.setText("Agregar Cuenta");
        agregar_cuenta.setEnabled(false);
        agregar_cuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregar_cuentaActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Cuentas a los que se cargará el cheque:"));

        tabla_cuentas_cargadas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabla_cuentas_cargadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, "Total", "0.00%", "Q. 0.00"}
            },
            new String [] {
                "No.", "Cuenta", "Porcentaje", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_cuentas_cargadas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabla_cuentas_cargadas.setRowHeight(25);
        tabla_cuentas_cargadas.getTableHeader().setReorderingAllowed(false);
        tabla_cuentas_cargadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabla_cuentas_cargadasMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_cuentas_cargadas);
        if (tabla_cuentas_cargadas.getColumnModel().getColumnCount() > 0) {
            tabla_cuentas_cargadas.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabla_cuentas_cargadas.getColumnModel().getColumn(1).setPreferredWidth(300);
            tabla_cuentas_cargadas.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabla_cuentas_cargadas.getColumnModel().getColumn(3).setPreferredWidth(125);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        agregar_porcentaje_cuenta.setText("Agregar");
        agregar_porcentaje_cuenta.setEnabled(false);
        agregar_porcentaje_cuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregar_porcentaje_cuentaActionPerformed(evt);
            }
        });

        jLabel11.setText("Porcentaje:");

        porcentaje_cuenta.setEnabled(false);
        porcentaje_cuenta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                porcentaje_cuentaFocusLost(evt);
            }
        });
        porcentaje_cuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                porcentaje_cuentaKeyTyped(evt);
            }
        });

        eliminar_cuenta.setText("Eliminar Cuenta");
        eliminar_cuenta.setEnabled(false);
        eliminar_cuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminar_cuentaActionPerformed(evt);
            }
        });

        crear_cheque.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        crear_cheque.setText("Crear Cheque");
        crear_cheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crear_chequeActionPerformed(evt);
            }
        });

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        cantidad_letras_cheque.setEditable(false);
        cantidad_letras_cheque.setColumns(20);
        cantidad_letras_cheque.setRows(5);
        jScrollPane3.setViewportView(cantidad_letras_cheque);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numero_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lugar_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fecha_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(receptor_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cantidad_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cuenta_bancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(propietario_cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(numero_cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane3))
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(agregar_cuenta)
                                    .addComponent(agregar_porcentaje_cuenta)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(porcentaje_cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(eliminar_cuenta)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(290, 290, 290)
                        .addComponent(crear_cheque)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(numero_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cuenta_bancaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(propietario_cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(numero_cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lugar_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(receptor_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(cantidad_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(fecha_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(agregar_cuenta)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(porcentaje_cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agregar_porcentaje_cuenta)
                        .addGap(40, 40, 40)
                        .addComponent(eliminar_cuenta)
                        .addGap(0, 85, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crear_cheque)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cuenta_bancariaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cuenta_bancariaItemStateChanged
        int index = cuenta_bancaria.getSelectedIndex();
        if (datosCargados && index != -1) {
            cargar_datos_de_cuenta(index);
        } else
            cargar_datos_de_cuenta(-1);
    }//GEN-LAST:event_cuenta_bancariaItemStateChanged

    private void cantidad_chequeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidad_chequeKeyTyped
        char tecla = evt.getKeyChar();
        if (tecla == '.') {
            if (cantidad_cheque.getText().contains("."))
                evt.consume();  // No se permite ingresar un valor como NN..N (con dos puntos consecutivos)
            else if (cantidad_cheque.getText().length() == 0)
                cantidad_cheque.setText("0");   // Si la primera tecla es '.' se convierte en '0.'
        } else if (!Pattern.compile("\\d").matcher(String.valueOf(evt.getKeyChar())).matches())
            evt.consume();
    }//GEN-LAST:event_cantidad_chequeKeyTyped

    private void cantidad_chequeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cantidad_chequeFocusLost
        if (cantidad_cheque.getText().length() == 0)
            cantidad_cheque.setText("0.00");
        else if (cantidad_cheque.getText().indexOf(".") == (cantidad_cheque.getText().length()-1))
            cantidad_cheque.setText(cantidad_cheque.getText()+"00");
        else
            cantidad_cheque.setText(String.format("%.2f", Float.parseFloat(cantidad_cheque.getText())));
        // Al perder el focus, por lo menos la Cantidad Total es de 0.00
        totalFactura = Float.parseFloat(cantidad_cheque.getText());
        actualizar_cantidad_total(totalFactura);    // Actualizo los Subtotales asignados a las cuentas cargadas
        cantidad_letras_cheque.setText(cantidad_en_letras(totalFactura));
        if (!agregar_cuenta.isEnabled())
            agregar_cuenta.setEnabled(true);
    }//GEN-LAST:event_cantidad_chequeFocusLost

    private void agregar_cuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregar_cuentaActionPerformed
        // Se pueden seguir agregando cuentas sí y sólo sí el porcentajeTotal < 100.00
        if (porcentajeAcumulado < 100.00f) {
            // Se muestra la ventana donde se podrá seleccionar una cuenta
            int indexSelec = new SeleccionarCuenta(this, true, listaIDCuentasContables, listaCuentasContables).getIndexSeleccionado();
            if (indexSelec != -1) { // Se ha seleccionado una cuenta. Cargo sus datos
                // Verifico que la Cuenta seleccionada no ha sido cargada con anterioridad
                boolean yaEstaCargado = false;
                int cantidad = listaIDCuentasCargadas.size();
                for(int i=0; i<cantidad; i++) {
                    if (listaIDCuentasContables.get(indexSelec) == listaIDCuentasCargadas.get(i)) {
                        yaEstaCargado = true;
                        break;
                    }
                }
                if (yaEstaCargado)
                    JOptionPane.showMessageDialog(this, "La Cuenta '"+listaCuentasContables.get(indexSelec)+"' ya ha sido cargada", "Aviso", JOptionPane.WARNING_MESSAGE);
                else {
                    // Muestro los datos de la Cuenta seleccionada en la Tabla
                    int contFil = tabla_cuentas_cargadas.getRowCount(), contCol = tabla_cuentas_cargadas.getColumnCount(), fil, col;
                    modelCuentas.addRow(new String[]{});
                    // Escribo el No. y el Nombre de la Cuenta seleccionada
                    tabla_cuentas_cargadas.setValueAt(""+(contFil-1), contFil-2, 0);
                    tabla_cuentas_cargadas.setValueAt(listaCuentasContables.get(indexSelec), contFil-2, 1);
                    // Muevo los datos de la penúltima fila a la última fila
                    for(col=1; col<contCol; col++) {
                        tabla_cuentas_cargadas.setValueAt((String)tabla_cuentas_cargadas.getValueAt(contFil-1, col), contFil, col);
                        tabla_cuentas_cargadas.setValueAt("", contFil-1, col);
                    }
                    contFil++;  // Indicador de que se agregó una nueva fila
                    // Escribo el porcentaje faltante para llegar al 100.00%
                    porcentaje_cuenta.setText(String.format("%.2f", 100.00f - porcentajeAcumulado));
                    listaIDCuentasCargadas.add(listaIDCuentasContables.get(indexSelec));    // Almaceno el ID de la Cuenta cargada
                    agregar_cuenta.setEnabled(false);   // Inhabilito este botón hasta que se haya asigando el porcentaje a la Cuenta
                    agregar_porcentaje_cuenta.setEnabled(true); // Habilito el botón para agregar el Porcentaje asignado a la cuenta
                    porcentaje_cuenta.setEnabled(true);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se puede agregar más cuentas pues ya se\nrepartió el 100% del Total de la Factura", "Cantidad agotada", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_agregar_cuentaActionPerformed

    private void porcentaje_cuentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_porcentaje_cuentaKeyTyped
        char tecla = evt.getKeyChar();
        if (tecla == '.') {
            if (porcentaje_cuenta.getText().contains("."))
                evt.consume();  // No se permite ingresar un valor como NN..N (con dos puntos consecutivos)
            else if (porcentaje_cuenta.getText().length() == 0)
                porcentaje_cuenta.setText("0");   // Si la primera tecla es '.' se convierte en '0.'
        } else if (!Pattern.compile("\\d").matcher(String.valueOf(evt.getKeyChar())).matches())
            evt.consume();
    }//GEN-LAST:event_porcentaje_cuentaKeyTyped

    private void porcentaje_cuentaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_porcentaje_cuentaFocusLost
        if (porcentaje_cuenta.getText().length() == 0)
            porcentaje_cuenta.setText("0.00");
        else if (porcentaje_cuenta.getText().indexOf(".") == (porcentaje_cuenta.getText().length()-1))
            porcentaje_cuenta.setText(porcentaje_cuenta.getText()+"00");
        else
            porcentaje_cuenta.setText(String.format("%.2f", Float.parseFloat(porcentaje_cuenta.getText())));
    }//GEN-LAST:event_porcentaje_cuentaFocusLost

    private void agregar_porcentaje_cuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregar_porcentaje_cuentaActionPerformed
        // Primero verifico que el porcentaje ingresado no supere el 100.00% al ser sumado al porcentajeAcumulado
        float porcentaje = Float.parseFloat(porcentaje_cuenta.getText()), subtotal = (totalFactura*porcentaje)/100f;
        if ((porcentaje+porcentajeAcumulado) <= 100.00f) {
            // Agrego el Porcentaje y el Subtotal asignado a la Cuenta, en la Tabla donde corresponde
            porcentajeAcumulado+= porcentaje;
            tabla_cuentas_cargadas.setValueAt(porcentaje_cuenta.getText()+"%", tabla_cuentas_cargadas.getRowCount()-3, 2);
            tabla_cuentas_cargadas.setValueAt("Q. "+String.format("%.2f", subtotal), tabla_cuentas_cargadas.getRowCount()-3, 3);
            // Actualización del subtotal acumulado
            tabla_cuentas_cargadas.setValueAt(String.format("%.2f", porcentajeAcumulado)+"%", tabla_cuentas_cargadas.getRowCount()-1, 2);
            tabla_cuentas_cargadas.setValueAt("Q. "+String.format("%.2f", (totalFactura*porcentajeAcumulado)/100f), tabla_cuentas_cargadas.getRowCount()-1, 3);
            agregar_cuenta.setEnabled(true);    // Habilito el botón para poder agregar otra cuenta contable
            agregar_porcentaje_cuenta.setEnabled(false);    // Se inhabilita este botón hasta agregar otra cuenta contable
            porcentaje_cuenta.setEnabled(false);
            porcentaje_cuenta.setText("");  // Limpio el campo de porcentaje
        } else {
            JOptionPane.showMessageDialog(this, "El porcentaje asignado a la cuenta hace que el Porcentaje acumulado supere el 100%", "Error en datos", JOptionPane.ERROR_MESSAGE);
            porcentaje_cuenta.setText(String.format("%.2f", 100f-porcentajeAcumulado));
        }
    }//GEN-LAST:event_agregar_porcentaje_cuentaActionPerformed

    private void eliminar_cuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminar_cuentaActionPerformed
        // Verifico que se quiera eliminar una fila que contiene una cuenta, esto es una fila entre [0, tabla.getRowCount()-3]
        int index = tabla_cuentas_cargadas.getSelectedRow();
        if (index!=-1 && index<(tabla_cuentas_cargadas.getRowCount()-2)) {  // Es una fila que puede ser eliminada
            int contFil = tabla_cuentas_cargadas.getRowCount(), contCol = tabla_cuentas_cargadas.getColumnCount(), fil, col;
            // Obtengo los datos de la cuenta que se va a eliminar
            String porcentajeTexto = (String)tabla_cuentas_cargadas.getValueAt(index, 2);
            float porcentaje = Float.parseFloat(porcentajeTexto.substring(0, porcentajeTexto.length()-1));
            // Muevo los registros de las Cuentas Contables que siguen después del que se eliminará
            for(fil=index; fil<(contFil-3); fil++)  // No borra el No. de Orden
                for(col=1; col<contCol; col++)
                    tabla_cuentas_cargadas.setValueAt((String)tabla_cuentas_cargadas.getValueAt(fil+1, col), fil, col);
            for(int f=fil; fil<(contFil-1); fil++)
                for(col=0; col<contCol; col++)  // Borra el último No. de Orden
                    tabla_cuentas_cargadas.setValueAt((String)tabla_cuentas_cargadas.getValueAt(f+1, col), f, col);
            // Ahora elimino la última fila
            modelCuentas.setRowCount(contFil-1);
            contFil = tabla_cuentas_cargadas.getRowCount(); // Obtengo la nueva cantidad de filas
            // Actualizo el valor del Porcentaje y Subtotal acumulados
            porcentajeAcumulado-= porcentaje;
            tabla_cuentas_cargadas.setValueAt(String.format("%.2f", porcentajeAcumulado)+"%", contFil-1, 2);
            tabla_cuentas_cargadas.setValueAt("Q. "+String.format("%.2f", (porcentajeAcumulado*totalFactura)/100f), contFil-1, 3);
            listaIDCuentasCargadas.remove(index);   // Elimino el ID de la Cuenta contable eliminado de la Tabla
        }
    }//GEN-LAST:event_eliminar_cuentaActionPerformed

    private void tabla_cuentas_cargadasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_cuentas_cargadasMousePressed
        int index = tabla_cuentas_cargadas.getSelectedRow();
        eliminar_cuenta.setEnabled((index!=-1 && index<(tabla_cuentas_cargadas.getRowCount()-2)));
    }//GEN-LAST:event_tabla_cuentas_cargadasMousePressed
    /**
     * Acción que permite crear los registros de Cheque, la Partida asociada del cheque y los Detalles de dicha partida, todos
     * en la Base de Datos
     * @param evt 
     */
    private void crear_chequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crear_chequeActionPerformed
        // VALIDACIÓN DE DATOS del Cheque
        String instruccion;
        boolean crearRegistro = true;   // Inicialmente se intentará crear todos los Registros
        try {
            validar_datos_cheque(); // Valido que los datos del Cheque sean correctos
        } catch (ExcepcionDatosIncorrectos ex) {
            crearRegistro = false;  // No se creará el Cheque, y la Partida asociada
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en datos", JOptionPane.ERROR_MESSAGE);
//            Logger.getLogger(CrearCheque.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (crearRegistro) {    // CREACIÓN DEL CHEQUE, SU PARTIDA Y LOS DETALLES DE PARTIDA
            Calendar fecha = fecha_cheque.getCalendar();
            String fechaS = ""+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH);
            int idCuentaBancaria = listaIDCuentasBancarias.get(cuenta_bancaria.getSelectedIndex()), idPartida = 0, idBancos = 0;
            // CREACIÓN DEL CHEQUE en la Base de Datos
            try {
                instruccion = "INSERT INTO Cheque(Numero, Lugar, Fecha, PagarA, Cantidad, CuentaBancaria_Id, CicloContable_Id) VALUES(";
                instruccion+= numero_cheque.getText()+", ";
                instruccion+= "'"+lugar_cheque.getText()+"', ";
                instruccion+= "'"+fechaS+"', ";
                instruccion+= "'"+receptor_cheque.getText()+"', ";
                instruccion+= "'"+cantidad_cheque.getText()+"', ";
                instruccion+= ""+idCuentaBancaria+", "+idCicloContable+")";
                conexion.prepareStatement(instruccion).executeUpdate();
                // Actualización del Número de Cheque en la Cuenta Bancaria al que pertenece, en la Base de Datos
                conexion.prepareStatement("UPDATE CuentaBancaria SET UltimoNumero = "+numero_cheque.getText()+" WHERE Id = "+idCuentaBancaria).executeUpdate();
                // Actualización del Número de Cheque en la Cuenta Bancaria al que pertenece, en el ArrayList
                listaNumeroUltimoCheque.add(cuenta_bancaria.getSelectedIndex(), Integer.parseInt(numero_cheque.getText()));
            } catch (SQLException ex) {
                System.out.println("Error al intentar crear el cheque:\n"+ex.getMessage());
                Logger.getLogger(CrearCheque.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CREACIÓN DE LA PARTIDA del Cheque recién creado
            try {
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet cConsulta;
                // CREACIÓN DE LA PARTIDA asociada al Cheque recién creado
                cConsulta = sentencia.executeQuery("SELECT COUNT(Numero), MAX(Numero) FROM Partida WHERE CicloContable_Id = "+idCicloContable);
                cConsulta.next();   // Obtención del Número de Partida que tendrá la nueva partida a crear
                int numeroPartida = (cConsulta.getInt(1)==0) ? 1 : cConsulta.getInt(2)+1;
                // Creación de la Nueva Partida
                instruccion = "INSERT INTO Partida(CicloContable_Id, Numero, Fecha, Descripcion) VALUES(";
                instruccion+= idCicloContable+", "+numeroPartida+", ";
                instruccion+= "'"+fechaS+"', ";
                instruccion+= "'Realización de Cheque No. "+numero_cheque.getText()+" de la cuenta "+(String)cuenta_bancaria.getSelectedItem()+", con fecha "+fechaS+"')";
                conexion.prepareStatement(instruccion).executeUpdate();  // Creación del Registro de la Partida
                cConsulta = sentencia.executeQuery("SELECT LAST_INSERT_ID()");    // Obtiene el ID del último registro 'insertado'
                cConsulta.next();
                idPartida = cConsulta.getInt(1);    // Obtención del ID de la Partida recién creada
                cConsulta = sentencia.executeQuery("SELECT Id FROM Cuentas WHERE Nombre = 'Bancos'");
                cConsulta.next();
                idBancos = cConsulta.getInt(1);   // Obtención del ID de la Cuenta 'Bancos'
            } catch (SQLException ex) {
                System.out.println("Error al intentar crear la Partida:\n"+ex.getMessage());
                Logger.getLogger(CrearCheque.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CREACIÓN DE LOS DETALLES DE PARTIDA asociada a todas las cuentas a las que se cargó el cheque (mostrados en la tabla)
            int contCuentas = listaIDCuentasCargadas.size();
            for(int cont=0; cont<contCuentas; cont++) {
                try {
                    // Obtención del porcentaje asignado a la cont-ésima cuenta
                    String porcentajeS = (String)tabla_cuentas_cargadas.getValueAt(cont, 2);
                    float porcentaje = Float.parseFloat(porcentajeS.substring(0, porcentajeS.length()-1));
                    // Creación del Detalle para la cont-ésima Cuenta. Todas van en el lado del Debe
                    instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("
                            + ""+idPartida+", "+listaIDCuentasCargadas.get(cont)+", "+String.format("%.2f", totalFactura*porcentaje)+", 1)";
                    conexion.prepareStatement(instruccion).executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Error al intentar crear el Detalle No. "+(cont+1)+" de la partida\n"+ex.getMessage());
                    Logger.getLogger(CrearCheque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Creación del Detalle para la Cuenta Bancos, con Saldo del lado del Haber
            try {
                // Creación del Detalle para la cont-ésima Cuenta. Todas van en el lado del Debe
                instruccion = "INSERT INTO Detalle_Partida(Partida_Id, Cuentas_Id, Valor, Debe) VALUES("
                        + ""+idPartida+", "+idBancos+", "+String.format("%.2f", totalFactura)+", 0)";
                conexion.prepareStatement(instruccion).executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error al intentar crear el Detalle de Bancos:\n"+ex.getMessage());
                Logger.getLogger(CrearCheque.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(this, "Cheque creado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
            limpiar_campos_cheque();    // Preparo los campos para la creación de un nuevo cheque
        }   // Hasta aquí se garantiza la CREACIÓN DEL CHEQUE, LA PARTIDA Y SUS DETALLES del cheque extendido
    }//GEN-LAST:event_crear_chequeActionPerformed

    private void cargar_datos_de_cuenta(int index) {
        if (index == -1) {
            numero_cheque.setText("");
            propietario_cuenta.setText("");
            numero_cuenta.setText("");
        } else {
            numero_cheque.setText(""+(listaNumeroUltimoCheque.get(index)+1));
            propietario_cuenta.setText(listaNombrePropietario.get(index));
            numero_cuenta.setText(listaNumeroCuenta.get(index));
        }
    }
    /**
     * Método que actualiza el valor de la Cantidad del Cheque, así como los valores de los porcentajes asignados a las
     * cuentas cargadas (si los hay).
     * @param nuevaCantidad 
     */
    private void actualizar_cantidad_total(float nuevaCantidad) {
        int contFil = tabla_cuentas_cargadas.getRowCount(), contCol = tabla_cuentas_cargadas.getColumnCount(), fil, col;
        String porcentajeS;
        float porcentaje;
        for (fil=0; fil<(contFil-2); fil++) {   // Actualización de todos los subtotales
            porcentajeS = (String)tabla_cuentas_cargadas.getValueAt(fil, 2);
            porcentaje = Float.parseFloat(porcentajeS.substring(0, porcentajeS.length()-1))/100f;
            tabla_cuentas_cargadas.setValueAt("Q. "+String.format("%.2f", nuevaCantidad*porcentaje), fil, 3);
        }
        // Calculo de la nueva suma de Subtotales
        porcentajeS = (String)tabla_cuentas_cargadas.getValueAt(contFil-1, 2);
        porcentaje = Float.parseFloat(porcentajeS.substring(0, porcentajeS.length()-1))/100f;
        tabla_cuentas_cargadas.setValueAt("Q. "+String.format("%.2f", nuevaCantidad*porcentaje), contFil-1, 3);
    }
    private String cantidad_en_letras(float cantidad) {
        int entero = (int)cantidad;   // Obtengo la parte entera
        String[] partes = cantidad_cheque.getText().split("\\.");  // Aun si numero fuese un entero n, su formato sería n.00
        int decimal = Integer.parseInt(partes[1]);  // Obtengo la parte decimal que está entre [0,99]
        String resultado = "", numeroT;
        String[] unidades = {"", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};
        String[] decenas = {"", "diez", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"};
        String[] centenas = {"", "cien", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"};
        String[] millar = {"", "mil", "millón", "mil", "billón"};
        String[] especiales = {"diez", "once", "doce", "trece", "catorce", "quince", "dieciseis", "diecisiete", "dieciocho", "diecinueve",
            "veinte", "veintiuno", "veintidos", "veintitres", "veinticuatro", "veinticinco", "veintiseis", "veintisiete", "veintiocho", "veintinueve"};
        
        int trinumero = 0, digito;
        for(int grupo=9; grupo>=0; grupo-=3) {   // Inicialmente asumo que cantidad está entre 0 y un valor de 9 dígitos
            trinumero = entero/potencia(10, grupo);
            if (trinumero > 0) {    // Trinumero es un valor entre [0, 99]
                entero-= trinumero*potencia(10, grupo);    // Disminuyo la cantidad, eliminando 'trinumero' del inicio
                if (trinumero > 99) {   // Entra en la clasificaciones de las 'centenas'
                    digito = trinumero/100;
                    resultado+= centenas[digito]+" ";
                    trinumero-= digito*100;
                }
                if (trinumero > 9) {    // Entra en la clasificación de las 'decenas'
                    if (trinumero < 30) {   // Entra en la clasificación de los 'especiales'
                        resultado+= especiales[trinumero-10];
                        trinumero/= 100;
                    } else {
                        digito = trinumero/10;
                        resultado+= decenas[digito]+" ";
                        trinumero-= digito*10;
                    }
                        
                }
                if (trinumero > 0) {
                    digito = trinumero;
                    resultado+= (resultado.length()==0?"":"y ")+unidades[digito]+" ";
                }
                resultado+= ((grupo==6 && trinumero>1) ? "millones" : millar[grupo/3])+" ";
            }
        }
        // Concatenación de la parte decimal como fracción de la forma 'decimal/100'
        resultado+= "con "+decimal+"/100";
        return resultado;
    }
    private int potencia(int base, int exp) {
        if (exp == 0)
            return 1;
        if (exp == 1)
            return base;
        return base*potencia(base, exp-1);
    }
    /**
     * Método que valida que los datos del Cheque a crear estén completos y sean correctos.
     * El cheque se puede crear sí y sólo si:
     * - La Fecha del cheque es válida y se encuentra en un Ciclo Contable creado en la BD. De ser correcto, obtiene el ID de dicho Ciclo
     * - Se cargará el Cheque por lo menos a una cuenta contable, y se haya distribuido el 100% de la Cantidad Total
     * @throws ExcepcionDatosIncorrectos 
     */
    private void validar_datos_cheque() throws ExcepcionDatosIncorrectos {
        if (cuenta_bancaria.getSelectedIndex() == -1)
            throw new ExcepcionDatosIncorrectos("Seleccione la Cuenta Bancaria del Cheque");
        if (lugar_cheque.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("Especifique el Lugar donde se extiende el Cheque");
        if (fecha_cheque.getDate() == null)
            throw new ExcepcionDatosIncorrectos("Especifique la Fecha del Cheque");
        // Obtención del ID del Ciclo Contable al que pertenece la Fecha del Cheque
        Calendar fecha = fecha_cheque.getCalendar();
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cConsulta = sentencia.executeQuery("SELECT CicloContable.Id FROM CicloContable "
                    + "INNER JOIN Anio ON CicloContable.Anio_Id = Anio.Id "
                    + "INNER JOIN Mes ON CicloContable.Mes_Id = Mes.Id "
                    + "WHERE Anio.Anio = "+fecha.get(Calendar.YEAR)+" AND Mes.Id = "+(fecha.get(Calendar.MONTH)+1));
            cConsulta.next();
            idCicloContable = cConsulta.getInt(1);  // Si la fecha no pertenece a un Ciclo existente, caerá en el catch
        } catch (SQLException ex) {
//            Logger.getLogger(CrearCheque.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionDatosIncorrectos("No puede crear el Cheque con la Fecha espcificada.\nSeleccione una fecha pertenezca al año en curso");
        }
        if (receptor_cheque.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("Especifique a quién se le extiende el Cheque");
        if (cantidad_cheque.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("Especifique la Cantidad del Cheque");
        if (Float.parseFloat(cantidad_cheque.getText()) == 0.00f)
            throw new ExcepcionDatosIncorrectos("No puede crear un Cheque de Q. 0.00");
        if (listaIDCuentasCargadas.isEmpty())
            throw new ExcepcionDatosIncorrectos("Debe cargar el Cheque a por lo menos una Cuenta (contable)");
        if (porcentajeAcumulado != 100.00f)
            throw new ExcepcionDatosIncorrectos("No se ha distribuido la Cantidad del Cheque a las Cuentas.\nAgrege más cuentas o modifique el valor de porcentaje de una ya existente.\n\nLleva distribuyendo el "+String.format("%.2f", porcentajeAcumulado)+"% de la Cantidad Total");
    }
    private void limpiar_campos_cheque() {
        cuenta_bancaria.setSelectedIndex(-1);   // Limpio los campos de información de la Cuenta Bancaria
        lugar_cheque.setText("");
        fecha_cheque.setDate(fechaActual);
        receptor_cheque.setText("");
        cantidad_cheque.setText("");
        cantidad_letras_cheque.setText("");
        modelCuentas.setRowCount(0);    // Limpio los registros de la Tabla
        modelCuentas.addRow(new String[]{});    // Agregación de las Filas de Información en la Tabla
        modelCuentas.addRow(new String[]{"", "Total", "0.00%", "Q.0.00"});
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
            java.util.logging.Logger.getLogger(CrearCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrearCheque().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregar_cuenta;
    private javax.swing.JButton agregar_porcentaje_cuenta;
    private javax.swing.JTextField cantidad_cheque;
    private javax.swing.JTextArea cantidad_letras_cheque;
    private javax.swing.JButton crear_cheque;
    private javax.swing.JComboBox<String> cuenta_bancaria;
    private javax.swing.JButton eliminar_cuenta;
    private com.toedter.calendar.JDateChooser fecha_cheque;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField lugar_cheque;
    private javax.swing.JTextField numero_cheque;
    private javax.swing.JTextField numero_cuenta;
    private javax.swing.JTextField porcentaje_cuenta;
    private javax.swing.JTextField propietario_cuenta;
    private javax.swing.JTextField receptor_cheque;
    private javax.swing.JTable tabla_cuentas_cargadas;
    // End of variables declaration//GEN-END:variables
}
