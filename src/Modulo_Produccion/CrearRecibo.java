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
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana que permite crear Recibos, siempre que exista por lo menos una Cosecha, un Tipo de Café Pergamino y un Productor.
 * Un Recibo es un documento no contable que registra el ingreso de materia prima (café pergamino) a la empresa.
 * @author Wilson Xicará
 */
public class CrearRecibo extends javax.swing.JFrame {
    private Connection conexion;
    private JFrame ventanaPadre;
    private boolean hacerVisible;
    private int contadorIDRecibosEnBD;
    private DefaultTableModel modelRecibos, modelCafeExistente;
    private ArrayList<Integer> listaIDCosecha, listaIDProductor, listaIDCafe;
    private Date fechaActual;
    /**
     * Creates new form CrearRecibo
     */
    public CrearRecibo() {
        initComponents();
    }

    public CrearRecibo(Connection conexion, JFrame ventanaPadre) {
        initComponents();
        this.conexion = conexion;
        this.ventanaPadre = ventanaPadre;
        hacerVisible = true;
        listaIDCosecha = new ArrayList<>();
        listaIDProductor = new ArrayList<>();
        listaIDCafe = new ArrayList<>();
        modelRecibos = (DefaultTableModel)tabla_recibos.getModel();
        modelCafeExistente = (DefaultTableModel)tabla_cafe_existente.getModel();
        
        // Obtengo los datos necesarios de las Cosechas, Productores y Tipo de café desde la Base de Datos
        try {
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cConsulta;
            // Obtención del listado de Cosechas
            cConsulta = sentencia.executeQuery("SELECT Id, CONCAT(Nombre, ' (', Fecha_fin, '-', Fecha_inicio, ')') nombreCosecha FROM Cosecha");
            while (cConsulta.next()) {
                listaIDCosecha.add(cConsulta.getInt("Id"));
                recibo_cosecha.addItem(cConsulta.getString("nombreCosecha"));
            } recibo_cosecha.setSelectedIndex(-1);
            // Obtención del listado de Productores
            cConsulta = sentencia.executeQuery("SELECT Id, Nombre FROM Productor");
            while (cConsulta.next()) {
                listaIDProductor.add(cConsulta.getInt("Id"));
                recibo_productor.addItem(cConsulta.getString("Nombre"));
            } recibo_productor.setSelectedIndex(-1);
            // Obtención del listado de Tipos de Café
            cConsulta = sentencia.executeQuery("SELECT * FROM Cafe WHERE Pergamino = 1");
            while (cConsulta.next()) {
                listaIDCafe.add(cConsulta.getInt("Id"));
                recibo_tipo_cafe.addItem(cConsulta.getString("Nombre")+" (Pergamino)");
                modelCafeExistente.addRow(new String[]{""+(tabla_cafe_existente.getRowCount()+1), cConsulta.getString("Nombre"), "Pergamino", ""+cConsulta.getFloat("SaldoBodega")});
            } recibo_tipo_cafe.setSelectedIndex(-1);
            // Verifico que exista por lo menos una Cosecha, un Tipo de Café Pergamino y un Productor
            hacerVisible = !(listaIDCosecha.isEmpty() || listaIDProductor.isEmpty() || listaIDCafe.isEmpty());
            if (!hacerVisible) {
                String mensaje = "No se pueden crear Recibos pues falta lo siguiente:";
                mensaje+= (listaIDCosecha.isEmpty()) ? "\n -> Cosechas" : "";
                mensaje+= (listaIDProductor.isEmpty()) ? "\n -> Productores" : "";
                mensaje+= (listaIDCafe.isEmpty()) ? "\n -> Café tipo Pergamino" : "";
                mensaje+= "\n\nConsulte con el Administrador para proporcionar dichos datos\na la Base de Datos e inténtelo nuevamente.";
                JOptionPane.showMessageDialog(this, mensaje, "Datos faltantes", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtengo el máximo Numero de Recibo correlativo (Codigo) de los dentro de la BD (los nuevos tendrán un número mayor y correlativo)
            cConsulta = sentencia.executeQuery("SELECT COUNT(Id), MAX(Id) FROM Recibo");  // Si no hay registros, MAX(Codigo) será NULL
            cConsulta.next();
            contadorIDRecibosEnBD = (cConsulta.getInt(1)==0) ? 1 : cConsulta.getInt(2)+1;
            recibo_numero.setText(""+contadorIDRecibosEnBD);
            // Obtengo la fecha de hoy (desde la Base de Datos)
            cConsulta = sentencia.executeQuery("SELECT YEAR(NOW()), MONTH(NOW()), DAY(NOW())");
            cConsulta.next();
            fechaActual = new Date(cConsulta.getInt(1)-1900, cConsulta.getInt(2)-1, cConsulta.getInt(3));
            recibo_fecha.setDate(fechaActual);
            cConsulta.close();
            // Otras configuraciones importantes
            recibo_fecha.getJCalendar().setWeekOfYearVisible(false);  // Para no mostrar el número de semana en el Calendario
            this.setLocationRelativeTo(null);   // Para centrar esta ventana sobre la pantalla.
        } catch (SQLException ex) {
            hacerVisible = false;
            JOptionPane.showMessageDialog(this, "No se puede obtener alguno de los listados desde la Base de Datos.\n\n"+ex.getMessage(), "Error al intentar obtener datos", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CrearRecibo.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        recibo_numero = new javax.swing.JTextField();
        recibo_numero_envio = new javax.swing.JTextField();
        recibo_peso_producto = new javax.swing.JTextField();
        recibo_cantidad_sacos_nylon = new javax.swing.JTextField();
        recibo_cosecha = new javax.swing.JComboBox<>();
        recibo_tipo_cafe = new javax.swing.JComboBox<>();
        recibo_productor = new javax.swing.JComboBox<>();
        recibo_fecha = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        recibo_cantidad_sacos_yuta = new javax.swing.JTextField();
        crear_recibo = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabla_recibos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabla_cafe_existente = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear Recibos");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nuevo Recibo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Recibo No.");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Cosecha:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Café");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Productor:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Fecha:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Envío No.");

        recibo_numero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        recibo_numero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        recibo_numero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                recibo_numeroKeyTyped(evt);
            }
        });

        recibo_numero_envio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        recibo_numero_envio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                recibo_numero_envioKeyTyped(evt);
            }
        });

        recibo_peso_producto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        recibo_peso_producto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        recibo_peso_producto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                recibo_peso_productoFocusLost(evt);
            }
        });
        recibo_peso_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                recibo_peso_productoKeyTyped(evt);
            }
        });

        recibo_cantidad_sacos_nylon.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        recibo_cantidad_sacos_nylon.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        recibo_cantidad_sacos_nylon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                recibo_cantidad_sacos_nylonKeyTyped(evt);
            }
        });

        recibo_cosecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        recibo_tipo_cafe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        recibo_productor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        recibo_fecha.setDateFormatString("dd/MM/yyyy");
        recibo_fecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Peso (en Quintales)");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Sacos Nylon");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Sacos Yuta");

        recibo_cantidad_sacos_yuta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        recibo_cantidad_sacos_yuta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        recibo_cantidad_sacos_yuta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                recibo_cantidad_sacos_yutaKeyTyped(evt);
            }
        });

        crear_recibo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        crear_recibo.setText("Crear Recibo");
        crear_recibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crear_reciboActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Detalle del Recibo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recibo_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recibo_cosecha, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recibo_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recibo_productor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recibo_numero_envio, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(recibo_tipo_cafe, 0, 200, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(recibo_cantidad_sacos_nylon)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(recibo_cantidad_sacos_yuta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(recibo_peso_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(crear_recibo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recibo_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(recibo_cosecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(recibo_productor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(recibo_numero_envio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(recibo_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recibo_tipo_cafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recibo_cantidad_sacos_nylon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recibo_cantidad_sacos_yuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recibo_peso_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(crear_recibo))
        );

        jPanel7.setBackground(new java.awt.Color(153, 153, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recibos creados recientemente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tabla_recibos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tabla_recibos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Recibo", "Cosecha", "Café", "Productor", "Fecha", "Número de envío", "Peso total", "Sacos de Nylon", "Sacos de Yuta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_recibos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabla_recibos.setRowHeight(25);
        jScrollPane4.setViewportView(tabla_recibos);
        if (tabla_recibos.getColumnModel().getColumnCount() > 0) {
            tabla_recibos.getColumnModel().getColumn(0).setPreferredWidth(75);
            tabla_recibos.getColumnModel().getColumn(1).setPreferredWidth(165);
            tabla_recibos.getColumnModel().getColumn(2).setPreferredWidth(135);
            tabla_recibos.getColumnModel().getColumn(3).setPreferredWidth(125);
            tabla_recibos.getColumnModel().getColumn(4).setPreferredWidth(85);
            tabla_recibos.getColumnModel().getColumn(5).setPreferredWidth(110);
            tabla_recibos.getColumnModel().getColumn(6).setPreferredWidth(90);
            tabla_recibos.getColumnModel().getColumn(7).setPreferredWidth(100);
            tabla_recibos.getColumnModel().getColumn(8).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Café existente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tabla_cafe_existente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tabla_cafe_existente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Nombre", "Tipo", "Saldo en Bodega"
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
        tabla_cafe_existente.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabla_cafe_existente.setRowHeight(25);
        jScrollPane5.setViewportView(tabla_cafe_existente);
        if (tabla_cafe_existente.getColumnModel().getColumnCount() > 0) {
            tabla_cafe_existente.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabla_cafe_existente.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla_cafe_existente.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabla_cafe_existente.getColumnModel().getColumn(3).setPreferredWidth(200);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Evento que muestra la sugerencia del No. de Recibo o verifica que se ingresen sólo dígitos en dicho campo.
     * @param evt 
     */
    private void recibo_numeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_recibo_numeroKeyTyped
        char tecla = evt.getKeyChar();
        if (tecla == '\n') {
            evt.consume();
            recibo_numero.setText(""+contadorIDRecibosEnBD);
        } else if (!Pattern.compile("\\d").matcher(String.valueOf(tecla)).matches())
            evt.consume();
    }//GEN-LAST:event_recibo_numeroKeyTyped
    /**
     * Eventos que controlan que se ingresen sólo dígitos (y un valor entero) en los campos especificados.
     * @param evt 
     */
    private void recibo_numero_envioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_recibo_numero_envioKeyTyped
        if (!Pattern.compile("\\d").matcher(String.valueOf(evt.getKeyChar())).matches())
            evt.consume();
    }//GEN-LAST:event_recibo_numero_envioKeyTyped
    private void recibo_cantidad_sacos_nylonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_recibo_cantidad_sacos_nylonKeyTyped
        if (!Pattern.compile("\\d").matcher(String.valueOf(evt.getKeyChar())).matches())
            evt.consume();
    }//GEN-LAST:event_recibo_cantidad_sacos_nylonKeyTyped
    private void recibo_cantidad_sacos_yutaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_recibo_cantidad_sacos_yutaKeyTyped
        if (!Pattern.compile("\\d").matcher(String.valueOf(evt.getKeyChar())).matches())
            evt.consume();
    }//GEN-LAST:event_recibo_cantidad_sacos_yutaKeyTyped
    /**
     * Evento que controla que el Peso en quintales del producto sea una cantidad decimal correcta.
     * @param evt 
     */
    private void recibo_peso_productoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_recibo_peso_productoKeyTyped
        char tecla = evt.getKeyChar();
        if ('.' == tecla) {
            if (recibo_peso_producto.getText().length() == 0) {
                recibo_peso_producto.setText("0");  // En caso de escribir '.' pasa a ser '0.'
            } else if (recibo_peso_producto.getText().contains("."))
                evt.consume();  // Una cantidad no puede tener dos puntos decimales
        } else if (!Pattern.compile("\\d").matcher(String.valueOf(evt.getKeyChar())).matches())
            evt.consume();  // No se aceptan caracteres no dígitos
    }//GEN-LAST:event_recibo_peso_productoKeyTyped
    /**
     * Evento que controla el caso en que se escriba '[numeros].' para convertirlo en '[numeros].0'
     * @param evt 
     */
    private void recibo_peso_productoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_recibo_peso_productoFocusLost
        if (recibo_peso_producto.getText().indexOf(".") == (recibo_peso_producto.getText().length() - 1))
            recibo_peso_producto.setText(recibo_peso_producto.getText()+"0");
    }//GEN-LAST:event_recibo_peso_productoFocusLost
    /**
     * Acción que permite crear un nuevo Recibo en la Base de Datos, así como las modificaciones que esto implica.
     * @param evt 
     */
    private void crear_reciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crear_reciboActionPerformed
        try {
            validar_datos();    // Valida que el No. de Recibo no se repita, y que los datos estén completos
            Calendar fecha = recibo_fecha.getCalendar();
            // Creación de la Factura asociada al Recibo que se creará
            int numeroPartida = 0;
            conexion.prepareStatement("INSERT INTO Factura_Especial(Numero) VALUES("+numeroPartida+")").executeUpdate();
            // Obtengo el ID de la Factura creada (se sabe que es el último)
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet cConsulta = sentencia.executeQuery("SELECT MAX(Id) FROM Factura_Especial");
            cConsulta.next();
            int idFactura = cConsulta.getInt(1);

            // Creación del nuevo Recibo en la Base de Datos
            String instruccion = "INSERT INTO Recibo(Cosecha_Id, Productor_Id, Cafe_Id, Numero, Fecha, NumEnvio, Peso, SacosNylon, SacosYuta, Saldo, Factura_Especial_Id) VALUES(";
            instruccion+= listaIDCosecha.get(recibo_cosecha.getSelectedIndex())+", ";
            instruccion+= listaIDProductor.get(recibo_productor.getSelectedIndex())+", ";
            instruccion+= listaIDCafe.get(recibo_tipo_cafe.getSelectedIndex())+", ";
            instruccion+= Integer.parseInt(recibo_numero.getText())+", ";
            instruccion+= "'"+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH)+"', ";
            instruccion+= recibo_numero_envio.getText()+", ";
            instruccion+= recibo_peso_producto.getText()+", ";
            instruccion+= recibo_cantidad_sacos_nylon.getText()+", ";
            instruccion+= recibo_cantidad_sacos_yuta.getText()+", ";
            instruccion+= recibo_peso_producto.getText()+", ";
            instruccion+= idFactura+")";    // Por defecto, un Recibo se crea como Facturado = false
            conexion.prepareStatement(instruccion).executeUpdate();

            // Actualización del Saldo en Bodega del Café (en la Base de Datos) del que se hace el Recibo
            instruccion = "UPDATE Cafe SET SaldoBodega = SaldoBodega + "+Float.parseFloat(recibo_peso_producto.getText())+" WHERE Id = "+listaIDCafe.get(recibo_tipo_cafe.getSelectedIndex());
            conexion.prepareStatement(instruccion).executeUpdate();

            JOptionPane.showMessageDialog(this, "Recibo No."+recibo_numero.getText()+" creado con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);

            // Muestro el nuevo registro en la Tabla
            modelRecibos.addRow(new String[]{
                recibo_numero.getText(),
                (String)recibo_cosecha.getSelectedItem(),
                (String)recibo_tipo_cafe.getSelectedItem(),
                (String)recibo_productor.getSelectedItem(),
                ""+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH),
                recibo_numero_envio.getText(),
                recibo_peso_producto.getText(),
                recibo_cantidad_sacos_nylon.getText(),
                recibo_cantidad_sacos_yuta.getText()
            });
            contadorIDRecibosEnBD++;

        // Actualizo el saldo del café seleccionado (en la Tabla de Café Existente)
        tabla_cafe_existente.setValueAt(
            ""+(Float.parseFloat((String)tabla_cafe_existente.getValueAt(recibo_tipo_cafe.getSelectedIndex(), 3)) + Float.parseFloat(recibo_peso_producto.getText())),
            recibo_tipo_cafe.getSelectedIndex(), 3);

        limpiar_campos();   // limpio los campos para poder crear un nuevo recibo
        } catch (ExcepcionDatosIncorrectos ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en datos", JOptionPane.ERROR_MESSAGE);
//            Logger.getLogger(CrearRecibo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se puede crear el nuevo Recibo.\n"+ex.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CrearRecibo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_crear_reciboActionPerformed

    private void validar_datos() throws ExcepcionDatosIncorrectos, SQLException {
        // Se sugiere que el No. de Recibo sea su ID. Debido a que dicho valor puede ser modificado en esta ventana, evalúo
        // que dicho No. aún no exista en la Base de Datos. De preferencia, no cambiar este valor pues complica las cosas
        // Para los campos numéricos, hay eventos que controlan el ingreso de sólo dígitos
        if (recibo_numero.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("Especifique el No. del Recibo.\n\nPosicione el cursor en el campo No. y presione Enter para ver la sugerencia.");
        Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet cConsulta = sentencia.executeQuery("SELECT COUNT(Numero) FROM Recibo WHERE Numero = "+Integer.parseInt(recibo_numero.getText()));
        cConsulta.next();
        if (cConsulta.getInt(1) != 0)
            throw new ExcepcionDatosIncorrectos("Ya existe un Recibo con el mismo No.\nSe recomienda utilizar los valores que se cargan por defecto para evitar estos problemas.");
        if (recibo_cosecha.getSelectedIndex() == -1)
            throw new ExcepcionDatosIncorrectos("Seleccione una Cosecha");
        if (recibo_productor.getSelectedIndex() == -1)
            throw new ExcepcionDatosIncorrectos("Seleccione un Productor");
        if (recibo_tipo_cafe.getSelectedIndex() == -1)
            throw new ExcepcionDatosIncorrectos("Seleccione un Tipo de Café");
        if (recibo_fecha.getDate() == null) // Sería raro que caiga en este caso, ya que por defecto se carga la fecha actual del servidor
            throw new ExcepcionDatosIncorrectos("Especifique la Fecha del recibo");
        if (recibo_numero_envio.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("Especifique el Número de Envío");
        if (recibo_cantidad_sacos_nylon.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("Especifique la Cantidad de Sacos de Nylon (incluso si fuese cero)");
        if (recibo_cantidad_sacos_yuta.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("Especifique la Cantidad de Sacos de Yuta (incluso si fuese cero)");
        if (recibo_peso_producto.getText().length() == 0)
            throw new ExcepcionDatosIncorrectos("Especifique el Peso en quintales del producto");
    }
    private void limpiar_campos() {
        recibo_numero.setText(""+contadorIDRecibosEnBD);
        recibo_cosecha.setSelectedIndex(-1);
        recibo_tipo_cafe.setSelectedIndex(-1);
        recibo_productor.setSelectedIndex(-1);
        recibo_fecha.setDate(fechaActual);
        recibo_numero_envio.setText("");
        recibo_cantidad_sacos_nylon.setText("");
        recibo_cantidad_sacos_yuta.setText("");
        recibo_peso_producto.setText("");
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
            java.util.logging.Logger.getLogger(CrearRecibo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrearRecibo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton crear_recibo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField recibo_cantidad_sacos_nylon;
    private javax.swing.JTextField recibo_cantidad_sacos_yuta;
    private javax.swing.JComboBox<String> recibo_cosecha;
    private com.toedter.calendar.JDateChooser recibo_fecha;
    private javax.swing.JTextField recibo_numero;
    private javax.swing.JTextField recibo_numero_envio;
    private javax.swing.JTextField recibo_peso_producto;
    private javax.swing.JComboBox<String> recibo_productor;
    private javax.swing.JComboBox<String> recibo_tipo_cafe;
    private javax.swing.JTable tabla_cafe_existente;
    private javax.swing.JTable tabla_recibos;
    // End of variables declaration//GEN-END:variables
}
