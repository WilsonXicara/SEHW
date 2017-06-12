/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modulo_Compras;

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
 * Ventana que permite crear una Factura Especial, siempre que hayan Recibos que aún no estén facturados. De hecho, lo que
 * se hace es 'actualizar' la Factura Especial asociada al Recibo no facturado ya que antes de crear el Recibo se crea la
 * Factura Especial. Se sugiere el ID de la Factura como su Numero, aunque dicho valor se puede modificar pero no repetir.
 * @author Wilson Xicará
 */
public class CrearFacturaEspecial extends javax.swing.JFrame {
    private Connection conexion;
    private JFrame ventanaPadre;
    private boolean hacerVisible;
    private int idProveedor;
    private ArrayList<Integer> listaIDRecibos, listaIDFacturas;
    private ArrayList<Float> listaSaldoCafe;
    private DefaultTableModel modelRecibos;
    private Date fechaActual;
    /**
     * Creates new form CrearFacturaEspecial
     */
    public CrearFacturaEspecial() {
        initComponents();
    }
    public CrearFacturaEspecial(Connection conexion, JFrame ventanaPadre) {
        initComponents();
        this.conexion = conexion;
        this.ventanaPadre = ventanaPadre;
        hacerVisible = true;
        
        // Obtengo los datos, desde la Base de Datos, necesarios para crear una Factura Especial
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cConsulta;
            // Verifico que exista por lo menos un Recibo en la BD (esté o no facturado)
            cConsulta = sentencia.executeQuery("SELECT COUNT(Id) FROM Recibo");
            cConsulta.next();
            hacerVisible = !(cConsulta.getInt(1) == 0);
            if (!hacerVisible) {
                String mensaje = "No se pueden crear Facturas Especiales pues no se ha creado ningún Recibo.";
                mensaje+= "\n\nConsulte con el Administrador para proporcionar dichos datos\na la Base de Datos e inténtelo nuevamente.";
                JOptionPane.showMessageDialog(this, mensaje, "Datos faltantes", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            listaIDRecibos = new ArrayList<>();
            listaIDFacturas = new ArrayList<>();
            listaSaldoCafe = new ArrayList<>();
            // Obtengo el listado de todos los Recibos que aún no han sido Facturados (y las Facturas asociadas a estos)
            cConsulta = sentencia.executeQuery("SELECT Recibo.Id idRecibo, Recibo.Numero, Recibo.Fecha, CONCAT(Cosecha.Nombre, ' (', Cosecha.Fecha_Inicio, '-', Cosecha.Fecha_fin, ')') nombreCosecha, CONCAT(Cafe.Nombre, ' (Pergamino)') nombreCafe, Recibo.Peso, Recibo.Factura_Especial_Id idFactura FROM Recibo "
                    + "INNER JOIN Cosecha ON Recibo.Cosecha_Id = Cosecha.Id "
                    + "INNER JOIN Cafe ON Recibo.Cafe_Id = Cafe.Id "
                    + "WHERE Facturado = 0");
            int cont = 0;
            modelRecibos = (DefaultTableModel) tabla_recibos.getModel();
            while (cConsulta.next()) {
                listaIDRecibos.add(cConsulta.getInt("idRecibo"));
                listaIDFacturas.add(cConsulta.getInt("idFactura"));
                modelRecibos.addRow(new String[]{
                    ""+(++cont),    // En este operador, primero incrementa y luego imprime
                    cConsulta.getString("Numero"),
                    cConsulta.getString("Fecha"),
                    cConsulta.getString("nombreCosecha"),
                    cConsulta.getString("nombreCafe"),
                    cConsulta.getString("Peso")
                });
            }
            // Obtengo la fecha de hoy (desde la Base de Datos)
            cConsulta = sentencia.executeQuery("SELECT YEAR(NOW()), MONTH(NOW()), DAY(NOW())");
            cConsulta.next();
            fechaActual = new Date(cConsulta.getInt(1)-1900, cConsulta.getInt(2)-1, cConsulta.getInt(3));
            fact_fecha.setDate(fechaActual);
            cConsulta.close();
            
            // Otras configuraciones importantes
            fact_fecha.setDate(fechaActual);
            fact_fecha.getJCalendar().setWeekOfYearVisible(false);  // Para no mostrar el número de semana en el Calendario
            if (listaIDRecibos.isEmpty())
                jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "No hay Recibos por facturar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12)));
            crear_factura.setEnabled(!listaIDRecibos.isEmpty());
            idProveedor = 0;
            this.setLocationRelativeTo(null);   // Para centrar esta ventana sobre la pantalla
        } catch (SQLException ex) {
            hacerVisible = false;
            JOptionPane.showMessageDialog(this, "Error al intentar obtener los datos.\n\nDescripción:\n"+ex.getMessage(), "Error en conexión", JOptionPane.ERROR_MESSAGE);
//            Logger.getLogger(CrearFacturaEspecial.class.getName()).log(Level.SEVERE, null, ex);
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
        fact_serie = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fact_numero = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fact_fecha = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        proveedor_nombre = new javax.swing.JTextField();
        proveedor_nit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        proveedor_direccion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cafe_quintales = new javax.swing.JTextField();
        cafe_costo_unidad = new javax.swing.JTextField();
        cafe_costo_total = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        crear_factura = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_recibos = new javax.swing.JTable();
        fact_cosecha = new javax.swing.JTextField();
        cafe_tipo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        proveedor_cui = new javax.swing.JTextField();
        etiqueta_datos_incorrectos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear nueva Factura Especial");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Serie:");

        fact_serie.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fact_serie.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("No.:");

        fact_numero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fact_numero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        fact_numero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fact_numeroKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Fecha:");

        fact_fecha.setDateFormatString("dd/MM/yyyy");
        fact_fecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Proveedor:");

        proveedor_nombre.setEditable(false);
        proveedor_nombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        proveedor_nit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        proveedor_nit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                proveedor_nitFocusLost(evt);
            }
        });
        proveedor_nit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                proveedor_nitKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("NIT:");

        proveedor_direccion.setEditable(false);
        proveedor_direccion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Dirección:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Cosecha:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Quintales");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Tipo de Café");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Precio por Quintal");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Total");

        cafe_quintales.setEditable(false);
        cafe_quintales.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cafe_quintales.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        cafe_costo_unidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cafe_costo_unidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cafe_costo_unidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cafe_costo_unidadFocusLost(evt);
            }
        });

        cafe_costo_total.setEditable(false);
        cafe_costo_total.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cafe_costo_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Detalle de la factura");

        crear_factura.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        crear_factura.setText("Crear Factura");
        crear_factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crear_facturaActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recibos por facturar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tabla_recibos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Recibo No.", "Fecha", "Cosecha", "Café", "Quintales"
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
        tabla_recibos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabla_recibos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabla_recibosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_recibos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
        );

        fact_cosecha.setEditable(false);
        fact_cosecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fact_cosecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        cafe_tipo.setEditable(false);
        cafe_tipo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cafe_tipo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("CUI:");

        proveedor_cui.setEditable(false);
        proveedor_cui.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        proveedor_cui.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                proveedor_cuiKeyTyped(evt);
            }
        });

        etiqueta_datos_incorrectos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(197, 197, 197)
                .addComponent(crear_factura)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proveedor_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(proveedor_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cafe_quintales, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(147, 147, 147))
                                    .addComponent(cafe_tipo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cafe_costo_unidad))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cafe_costo_total, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proveedor_nit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proveedor_cui, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(etiqueta_datos_incorrectos))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(4, 4, 4)
                                .addComponent(fact_cosecha, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fact_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fact_serie, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fact_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fact_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(fact_serie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(fact_cosecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fact_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiqueta_datos_incorrectos)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(proveedor_nit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(proveedor_cui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(proveedor_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(proveedor_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cafe_quintales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cafe_costo_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cafe_costo_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cafe_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(crear_factura)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Acción que controla que el NIT tenga el siguiente formato: 1234567-8 (7 dígitos, guión, 1 dígito).
     * @param evt 
     */
    private void proveedor_nitKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_proveedor_nitKeyTyped
        char tecla = evt.getKeyChar();
        int cantidad = proveedor_nit.getText().length();
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
        }
    }//GEN-LAST:event_proveedor_nitKeyTyped
    /**
     * Acción que activa la búsqueda de la información del proveedor por su NIT. La búsqueda se realizará sí y sólo sí al
     * perder el Focus el campo de NIT está completo (que tenga 9 caracteres).
     * @param evt 
     */
    private void proveedor_nitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_proveedor_nitFocusLost
        if (proveedor_nit.getText().length() == 9) {
            etiqueta_datos_incorrectos.setText("");
            buscar_nit_proveedor();
            // Si el idProveedor != 0 entonces se encontró un proveedor
            if (idProveedor == 0) {
                proveedor_nombre.setEditable(true);      // Habilito los campos para completar datos
                proveedor_direccion.setEditable(true);
                proveedor_cui.setEditable(true);
            }
        } else
            etiqueta_datos_incorrectos.setText("Numero de NIT incompleto");
    }//GEN-LAST:event_proveedor_nitFocusLost

    private void proveedor_cuiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_proveedor_cuiKeyTyped
        if (!Pattern.compile("\\d").matcher(String.valueOf(evt.getKeyChar())).matches() || proveedor_cui.getText().length() == 13)
            evt.consume();
    }//GEN-LAST:event_proveedor_cuiKeyTyped

    private void cafe_costo_unidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cafe_costo_unidadFocusLost
        float costoUnidad;
        try {
            costoUnidad = Float.parseFloat(cafe_costo_unidad.getText());
            cafe_costo_total.setText(""+(Float.parseFloat(cafe_quintales.getText()) * costoUnidad));
            etiqueta_datos_incorrectos.setText("");
        }
        catch (NumberFormatException ex) {
            cafe_costo_total.setText("");
            etiqueta_datos_incorrectos.setText("Precio por Quintal incorrecto");
        }
    }//GEN-LAST:event_cafe_costo_unidadFocusLost

   /**
     * Acción que carga los datos de la Factura, almacenadas en el Recibo que está relacionado a este.
     * @param evt 
     */
    private void tabla_recibosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_recibosMousePressed
        if (!crear_factura.isEnabled())
            crear_factura.setEnabled(true);
        int index = tabla_recibos.getSelectedRow();
        // Cargo los datos que obtengo del Recibo
        cargar_datos_recibo_en_factura(index);
    }//GEN-LAST:event_tabla_recibosMousePressed
    /**
     * Acción que permite actualizar la Factura. Una factura puede actualizarse sí y sólo si ya se le ha asignado un Proveedor.
     * @param evt 
     */
    private void crear_facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crear_facturaActionPerformed
        try {
            validar_datos();    // Verifico que todos los datos sean correctos
            int indexRecibo = tabla_recibos.getSelectedRow();
            Calendar fecha = fact_fecha.getCalendar();
            String instruccion;
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            
            // Creación del Proveedor, en caso de que aún no exista
            if (idProveedor == 0) {
                instruccion = "INSERT INTO Proveedores(CUI, NIT, Nombre, Direccion) VALUES(";
                instruccion+= "'"+proveedor_cui.getText()+"', ";
                instruccion+= "'"+proveedor_nit.getText()+"', ";
                instruccion+= "'"+proveedor_nombre.getText()+"', ";
                instruccion+= "'"+proveedor_direccion.getText()+"')";
                conexion.prepareStatement(instruccion).executeUpdate();
                // Obtengo el ID del proveedor creado (es el último que se insertó)
                ResultSet cProveedor = sentencia.executeQuery("SELECT MAX(Id) FROM Proveedores");
                cProveedor.next();
                idProveedor = cProveedor.getInt(1);
            }
                
            // Actualización de la Factura asociada al Recibo seleccionado
            instruccion = "UPDATE Factura_Especial SET ";
            instruccion+= "Proveedor_Id = "+idProveedor+", ";
            instruccion+= "Precio_por_quintal = "+cafe_costo_unidad.getText()+", ";
            instruccion+= "Serie = '"+fact_serie.getText()+"', ";
            instruccion+= "Numero = "+Integer.parseInt(fact_numero.getText())+", ";
            instruccion+= "Fecha = '"+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH)+"' ";
            instruccion+= "WHERE Id = "+listaIDFacturas.get(indexRecibo);
            conexion.prepareStatement(instruccion).executeUpdate();
            
            // Actualización del Recibo para indicar que ya fue facturado
            conexion.prepareStatement("UPDATE Recibo SET Facturado = 1 WHERE Id = "+listaIDRecibos.get(indexRecibo)).executeUpdate();
            JOptionPane.showMessageDialog(this, "Recibo No."+(String)tabla_recibos.getValueAt(indexRecibo, 1)+" Facturado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
            
            // Ahora, elimino el Recibo ya facturado de la Tabla de Recibos
            int contFil = tabla_recibos.getRowCount(), contCol = tabla_recibos.getColumnCount(), fil, col;
            for (fil=indexRecibo+1; fil<contFil; fil++)
                for(col=1; col<contCol; col++)
                    tabla_recibos.setValueAt((String)tabla_recibos.getValueAt(fil, col), fil-1, col);
            modelRecibos.setRowCount(contFil-1);
            // Ahora elimino los registros asociados en los ArrayList
            listaIDRecibos.remove(indexRecibo);
            listaIDFacturas.remove(indexRecibo);
            // Hasta aquí se garantiza la Facturación de un Recibo
            
            cargar_datos_recibo_en_factura(-1); // Para limpiar los campos de la Factura
            proveedor_cui.setEditable(false);
            proveedor_nombre.setEditable(false);
            proveedor_direccion.setEditable(false);
            // Limpio los campos de entrada de datos
            fact_serie.setText("");
            fact_fecha.setDate(fechaActual);
            proveedor_nit.setText("");
            proveedor_cui.setText("");
            proveedor_nombre.setText("");
            proveedor_direccion.setText("");
            cafe_costo_unidad.setText("");
            cafe_costo_total.setText("");
            crear_factura.setEnabled(false);
            jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recibos por facturar: "+listaIDRecibos.size(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12)));
        } catch (ExcepcionDatosIncorrectos | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en datos", JOptionPane.ERROR_MESSAGE);
//            Logger.getLogger(CrearFacturaEspecial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_crear_facturaActionPerformed
    /**
     * Evento que controla de que el Número de Factura sea un valor numérico.
     * @param evt 
     */
    private void fact_numeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fact_numeroKeyTyped
        if (!Pattern.compile("\\d").matcher(String.valueOf(evt.getKeyChar())).matches())
            evt.consume();
    }//GEN-LAST:event_fact_numeroKeyTyped

    private void buscar_nit_proveedor() {
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cProveedor = sentencia.executeQuery("SELECT Id, Nombre, Direccion, CUI FROM Proveedores WHERE NIT = '"+proveedor_nit.getText()+"'");
            if (cProveedor.next()) {
                idProveedor = cProveedor.getInt("Id");
                proveedor_nombre.setText(cProveedor.getString("Nombre"));
                proveedor_direccion.setText(cProveedor.getString("Direccion"));
                proveedor_cui.setText(cProveedor.getString("CUI"));
            } else
                idProveedor = 0;
        } catch (SQLException ex) {
//            Logger.getLogger(CrearFacturaEspecial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void cargar_datos_recibo_en_factura(int index) {
        if (index == -1) {  // Para limpiar los campos
            fact_numero.setText("");
            fact_cosecha.setText("");
            cafe_quintales.setText("");
            cafe_tipo.setText("");
        } else {    // Para cargar un Recibo en específico
            fact_numero.setText((String) tabla_recibos.getValueAt(index, 1));
            fact_cosecha.setText((String) tabla_recibos.getValueAt(index, 3));
            cafe_quintales.setText((String) tabla_recibos.getValueAt(index, 5));
            cafe_tipo.setText((String) tabla_recibos.getValueAt(index, 4));
        }
    }
    public void validar_datos() throws ExcepcionDatosIncorrectos, SQLException {
        if (proveedor_nit.getText().length() != 9)
            throw new ExcepcionDatosIncorrectos("El NIT del Proveedor está incompleto");
        if (proveedor_cui.getText().length() != 13)
            throw new ExcepcionDatosIncorrectos("El CUI del Proveedor está incompleto");
        if (proveedor_nombre.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("No ha especificado el Nombre del Proveedor");
        if (proveedor_direccion.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("No ha especificado la Dirección del Proveedor");
        if (fact_serie.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("No ha especificado la Serie de la Factura");
        // En caso de haber cambiado la sugerencia del Número de Factura y este ya existe, se lanza la excepción
        Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        if (sentencia.executeQuery("SELECT Id FROM Factura_Especial WHERE Numero = "+Integer.parseInt(fact_numero.getText())).next()) {
            fact_numero.setText(""+listaIDFacturas.get(tabla_recibos.getSelectedRow()));
            throw new ExcepcionDatosIncorrectos("El Número de Factura ya existe.\nConsidere utilizar el que se le sugiere.");
        }
         if (fact_fecha.getDate() == null)
             throw new ExcepcionDatosIncorrectos("No ha especificado la Fecha de la Factura");
         try { Float.parseFloat(cafe_costo_unidad.getText()); }
         catch(NumberFormatException ex) { throw new ExcepcionDatosIncorrectos("El Precio por Quintal debe ser un valor numérico válido"); }
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
            java.util.logging.Logger.getLogger(CrearFacturaEspecial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrearFacturaEspecial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cafe_costo_total;
    private javax.swing.JTextField cafe_costo_unidad;
    private javax.swing.JTextField cafe_quintales;
    private javax.swing.JTextField cafe_tipo;
    private javax.swing.JButton crear_factura;
    private javax.swing.JLabel etiqueta_datos_incorrectos;
    private javax.swing.JTextField fact_cosecha;
    private com.toedter.calendar.JDateChooser fact_fecha;
    private javax.swing.JTextField fact_numero;
    private javax.swing.JTextField fact_serie;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField proveedor_cui;
    private javax.swing.JTextField proveedor_direccion;
    private javax.swing.JTextField proveedor_nit;
    private javax.swing.JTextField proveedor_nombre;
    private javax.swing.JTable tabla_recibos;
    // End of variables declaration//GEN-END:variables
}
