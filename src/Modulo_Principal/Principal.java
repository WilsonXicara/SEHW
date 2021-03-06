/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modulo_Principal;

import Modulo_Inventario.Movimientos;
import Modulo_Inventario.InventarioRecibos;
import Modulo_Produccion.Nota_Rendimiento;
import Modulo_Crear.CrearTipoCafe;
import Modulo_Crear.Crear_Productor;
import Modulo_Crear.Crear_Organizacion;
import Modulo_Crear.CrearCosecha;
import Conexion.ConectarConBD;
import Modulo_Compras.CrearFacturaEspecial;
import Modulo_Finanzas.Buscar_cuenta;
import Modulo_Finanzas.CrearCheque;
import Modulo_Finanzas.CrearCuentaBancaria;
import Modulo_Finanzas.Crear_Cuentas;
import Modulo_Finanzas.Estado_Resultados;
import Modulo_Finanzas.Libro_diario;
import Modulo_Finanzas.Polizas;
import Modulo_Inventario.Cotizacion;
import Modulo_Produccion.CrearOrdenTrilla;
import Modulo_Produccion.CrearRecibo;
import Modulo_RecursosHumanos.ControlHorasExtra;
import Modulo_RecursosHumanos.CrearPuesto;
import Modulo_RecursosHumanos.NuevoPersonal;
import Modulo_RecursosHumanos.VerPlanilla;
import Modulo_Ventas.Factura_Exportacion;
import Modulo_Ventas.Factura_Local;
import java.awt.Frame;
import java.awt.Image;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author USUARIO
 */
public class Principal extends javax.swing.JFrame {
    private final Connection conexion;
    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        ConectarConBD obtenerConexion = new ConectarConBD();
        conexion = obtenerConexion.getConexion();
        if (conexion == null)
            System.exit(0);
        this.setLocationRelativeTo(null);   // Para centrar esta ventana sobre la pantalla.
        ImageIcon fot = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo.png"));
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_DEFAULT));
        jLabel1.setIcon(icono);
        jLabel1.setVisible(true);
        this.repaint();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        menu = new javax.swing.JMenuBar();
        menu_crear = new javax.swing.JMenu();
        item_crear_cosecha = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        item_crear_tipo_cafe = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menu_pagos = new javax.swing.JMenu();
        item_pagar_cafe = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        item_cotizar_cafe = new javax.swing.JMenuItem();
        menu_recursos_humanos = new javax.swing.JMenu();
        item_nuevo_puesto = new javax.swing.JMenuItem();
        item_nuevo_empleado = new javax.swing.JMenuItem();
        item_asignar_empleo = new javax.swing.JMenuItem();
        item_control_horas_extra = new javax.swing.JMenuItem();
        item_ver_generar_planilla = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        item_crear_cuenta_bancaria = new javax.swing.JMenuItem();
        item_crear_cheque = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();

        jMenuItem4.setText("jMenuItem4");

        jMenuItem6.setText("jMenuItem6");

        jMenuItem12.setText("jMenuItem12");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ECEG: Sistema Empresarial");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo2.png"))); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imagen.png"))); // NOI18N

        menu_crear.setText("Crear");

        item_crear_cosecha.setText("Cosecha");
        item_crear_cosecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_crear_cosechaActionPerformed(evt);
            }
        });
        menu_crear.add(item_crear_cosecha);

        jMenuItem2.setText("Organización");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menu_crear.add(jMenuItem2);

        jMenuItem5.setText("Productor");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        menu_crear.add(jMenuItem5);

        item_crear_tipo_cafe.setText("Tipo de Cafe");
        item_crear_tipo_cafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_crear_tipo_cafeActionPerformed(evt);
            }
        });
        menu_crear.add(item_crear_tipo_cafe);

        menu.add(menu_crear);

        jMenu2.setText("Producción");

        jMenuItem8.setText("Recibo");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuItem9.setText("Orden de Trilla");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        jMenuItem10.setText("Nota de Rendimiento");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem10);

        menu.add(jMenu2);

        jMenu3.setText("Inventario");

        jMenuItem3.setText("Ver Recibos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem1.setText("Movimientos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        menu.add(jMenu3);

        menu_pagos.setText("Pagos");

        item_pagar_cafe.setText("Facturar Recibos");
        item_pagar_cafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_pagar_cafeActionPerformed(evt);
            }
        });
        menu_pagos.add(item_pagar_cafe);

        menu.add(menu_pagos);

        jMenu1.setText("Ventas");

        jMenuItem7.setText("Facturas Exportación");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem11.setText("Facturas Locales");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem11);

        item_cotizar_cafe.setText("Cotizar Café Existente");
        item_cotizar_cafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_cotizar_cafeActionPerformed(evt);
            }
        });
        jMenu1.add(item_cotizar_cafe);

        menu.add(jMenu1);

        menu_recursos_humanos.setText("Recursos Humanos");

        item_nuevo_puesto.setText("Nuevo Puesto");
        item_nuevo_puesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_nuevo_puestoActionPerformed(evt);
            }
        });
        menu_recursos_humanos.add(item_nuevo_puesto);

        item_nuevo_empleado.setText("Nuevo Empleado");
        item_nuevo_empleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_nuevo_empleadoActionPerformed(evt);
            }
        });
        menu_recursos_humanos.add(item_nuevo_empleado);

        item_asignar_empleo.setText("Asignar Empleo");
        menu_recursos_humanos.add(item_asignar_empleo);

        item_control_horas_extra.setText("Control de Horas Extra");
        item_control_horas_extra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_control_horas_extraActionPerformed(evt);
            }
        });
        menu_recursos_humanos.add(item_control_horas_extra);

        item_ver_generar_planilla.setText("Generar Planilla");
        item_ver_generar_planilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_ver_generar_planillaActionPerformed(evt);
            }
        });
        menu_recursos_humanos.add(item_ver_generar_planilla);

        menu.add(menu_recursos_humanos);

        jMenu4.setText("Financiero");

        jMenuItem13.setText("Polizas");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem13);

        jMenuItem17.setText("Libro Diario");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem17);

        jMenuItem14.setText("Balance de Saldos");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuItem15.setText("Estado de Resultados");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem15);

        item_crear_cuenta_bancaria.setText("Nueva Cuenta Bancaria");
        item_crear_cuenta_bancaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_crear_cuenta_bancariaActionPerformed(evt);
            }
        });
        jMenu4.add(item_crear_cuenta_bancaria);

        item_crear_cheque.setText("Extender Cheques");
        item_crear_cheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_crear_chequeActionPerformed(evt);
            }
        });
        jMenu4.add(item_crear_cheque);

        jMenuItem16.setText("Crear Cuenta");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem16);

        menu.add(jMenu4);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void item_crear_cosechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_crear_cosechaActionPerformed
        CrearCosecha nuevaCosecha = new CrearCosecha(this, true, conexion);
        nuevaCosecha.setVisible(nuevaCosecha.getHacerVisible());
    }//GEN-LAST:event_item_crear_cosechaActionPerformed

    private void item_crear_tipo_cafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_crear_tipo_cafeActionPerformed
        CrearTipoCafe nuevoCafe = new CrearTipoCafe(this, true, conexion);
        nuevoCafe.setVisible(nuevoCafe.getHacerVisible());
    }//GEN-LAST:event_item_crear_tipo_cafeActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        CrearRecibo nuevoRecibo = new CrearRecibo(conexion, this);
        nuevoRecibo.setVisible(nuevoRecibo.getHacerVisible());
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        CrearOrdenTrilla nuevaOrden = new CrearOrdenTrilla(conexion, this);
        nuevaOrden.setVisible(nuevaOrden.getHacerVisible());
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        InventarioRecibos nuevoInventarioRecibos = new InventarioRecibos(conexion);
        nuevoInventarioRecibos.setVisible(nuevoInventarioRecibos.getHacerVisible());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            new Movimientos(conexion, this).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new Crear_Organizacion(conexion, this).setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        try {
            new Crear_Productor(conexion, this).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        try {
            new Nota_Rendimiento(conexion, this).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        try {
            new Factura_Exportacion(conexion, this).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        Factura_Local a = new Factura_Local(conexion, this);
        a.setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void item_pagar_cafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_pagar_cafeActionPerformed
        CrearFacturaEspecial nuevaFacturaE = new CrearFacturaEspecial(conexion, this);
        nuevaFacturaE.setVisible(nuevaFacturaE.getHacerVisible());
    }//GEN-LAST:event_item_pagar_cafeActionPerformed

    private void item_cotizar_cafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_cotizar_cafeActionPerformed
        Cotizacion nuevaC = new Cotizacion(conexion, this);
        nuevaC.setVisible(nuevaC.getHacerVisible());
    }//GEN-LAST:event_item_cotizar_cafeActionPerformed

    private void item_nuevo_empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_nuevo_empleadoActionPerformed
        NuevoPersonal nuevo = new NuevoPersonal(conexion, this);
        nuevo.setVisible(true);
    }//GEN-LAST:event_item_nuevo_empleadoActionPerformed

    private void item_nuevo_puestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_nuevo_puestoActionPerformed
        CrearPuesto nuevo = new CrearPuesto(conexion, this);
        nuevo.setVisible(true);
    }//GEN-LAST:event_item_nuevo_puestoActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
             new Polizas(conexion).setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        try {
            String Activo= ""; //total del Activo
            String Pasivo = ""; //total del pasivo
            String Fecha = ""; //Fecha de corte del dia
            String mes = "";
            //Tomo el total de activo
            ResultSet consulta = conexion.createStatement().executeQuery("SELECT SUM(cuentas.Saldo) FROM cuentas WHERE cuentas.Activo = 1;");
            consulta.next();
            Activo = consulta.getString(1);
            consulta = conexion.createStatement().executeQuery("SELECT SUM(cuentas.Saldo) FROM cuentas WHERE cuentas.Pasivo = 1;");
            consulta.next();
            Pasivo = consulta.getString(1);
            consulta = conexion.createStatement().executeQuery("SELECT YEAR(NOW()),MONTH(NOW()),DAY(NOW())");
            consulta.next();
            String[] meses = {"", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Juilo", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
            Fecha = consulta.getString(3)+" de "+meses[consulta.getInt(2)]+" de "+consulta.getString(1);
            Float capital = Float.parseFloat(Activo)-Float.parseFloat(Pasivo);
            conexion.createStatement().executeUpdate("UPDATE cuentas SET cuentas.Saldo = "+capital+" WHERE cuentas.Id = 48");
            Map parametros  = new HashMap();
            parametros.put("Total_Activo", Activo);
            parametros.put("Total_Pasivo", Pasivo);
            parametros.put("Fecha", Fecha);
            
            JasperReport reporte = JasperCompileManager.compileReport("src\\Reportes\\Balance de Saldos.jrxml");
            JasperPrint a = JasperFillManager.fillReport(reporte, parametros,conexion);
            
            JasperViewer.viewReport(a,false);
        } catch (Exception e) {
            System.out.println(e);
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        new Estado_Resultados(conexion).setVisible(true);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void item_control_horas_extraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_control_horas_extraActionPerformed
        ControlHorasExtra control = new ControlHorasExtra(conexion, this);
        control.setVisible(control.getHacerVisible());
    }//GEN-LAST:event_item_control_horas_extraActionPerformed
    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        new Crear_Cuentas(conexion).setVisible(true);
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        new Libro_diario(conexion).setVisible(true);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void item_ver_generar_planillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_ver_generar_planillaActionPerformed
        VerPlanilla planilla = new VerPlanilla(conexion, this);
        planilla.setVisible(planilla.getHacerVisible());
    }//GEN-LAST:event_item_ver_generar_planillaActionPerformed

    private void item_crear_cuenta_bancariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_crear_cuenta_bancariaActionPerformed
        CrearCuentaBancaria nuevaC = new CrearCuentaBancaria(conexion, this);
        nuevaC.setVisible(nuevaC.getHacerVisible());
    }//GEN-LAST:event_item_crear_cuenta_bancariaActionPerformed

    private void item_crear_chequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_crear_chequeActionPerformed
        CrearCheque nuevoC = new CrearCheque(conexion, this);
        nuevoC.setVisible(nuevoC.getHacerVisible());
    }//GEN-LAST:event_item_crear_chequeActionPerformed

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem item_asignar_empleo;
    private javax.swing.JMenuItem item_control_horas_extra;
    private javax.swing.JMenuItem item_cotizar_cafe;
    private javax.swing.JMenuItem item_crear_cheque;
    private javax.swing.JMenuItem item_crear_cosecha;
    private javax.swing.JMenuItem item_crear_cuenta_bancaria;
    private javax.swing.JMenuItem item_crear_tipo_cafe;
    private javax.swing.JMenuItem item_nuevo_empleado;
    private javax.swing.JMenuItem item_nuevo_puesto;
    private javax.swing.JMenuItem item_pagar_cafe;
    private javax.swing.JMenuItem item_ver_generar_planilla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu menu_crear;
    private javax.swing.JMenu menu_pagos;
    private javax.swing.JMenu menu_recursos_humanos;
    // End of variables declaration//GEN-END:variables
}
