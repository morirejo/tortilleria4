/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tortilleriapresentacion;

import BOs.CancelacionBO;
import com.mycompany.tortilleriadtos.VentaDTO;
import java.awt.*;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jorge
 */
public class PantallaCancelacion  extends javax.swing.JFrame{
    private final ControlPresentacionVenta mediador;

    private DefaultTableModel modeloVentas;
    private JTable tablaVentas;
    private List<VentaDTO> ventas;

    private DefaultTableModel modeloHistorial;

    public PantallaCancelacion(ControlPresentacionVenta mediador) {
        this.mediador = mediador;
        initComponents();
        cargarVentas();
        cargarHistorial();
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Cancelaciones");
        setResizable(false);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabs.addTab("Cancelar Venta", buildPestañaCancelar());
        tabs.addTab("Historial de Cancelaciones", buildPestañaHistorial());

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(10, 10, 10, 10));
        contenedor.setBackground(Color.WHITE);
        contenedor.add(tabs, BorderLayout.CENTER);

        setContentPane(contenedor);
        setPreferredSize(new Dimension(700, 480));
        pack();
        setLocationRelativeTo(null);
    }


    private JPanel buildPestañaCancelar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Selecciona una venta para cancelar");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);

        modeloVentas = new DefaultTableModel(
                new String[]{"Folio", "Fecha", "Kilos", "Total", "Método Pago"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaVentas = new JTable(modeloVentas);
        tablaVentas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaVentas.setRowHeight(28);
        tablaVentas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tablaVentas);
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnVerTicket = new JButton("Ver Ticket y Cancelar");
        btnVerTicket.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVerTicket.setBackground(new Color(200, 50, 50));
        btnVerTicket.setForeground(Color.WHITE);
        btnVerTicket.setFocusPainted(false);
        btnVerTicket.setBorderPainted(false);
        btnVerTicket.setOpaque(true);
        btnVerTicket.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVerTicket.addActionListener(e -> {
            int fila = tablaVentas.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una venta de la tabla.");
                return;
            }
            VentaDTO ventaSeleccionada = ventas.get(fila);
            new PantallaTicketCancelacion(mediador, ventaSeleccionada, this).setVisible(true);
        });

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(Color.WHITE);
        panelBoton.add(btnVerTicket);
        panel.add(panelBoton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildPestañaHistorial() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Historial de Cancelaciones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);

        modeloHistorial = new DefaultTableModel(
                new String[]{"Folio Venta", "Fecha Cancelación", "Motivo", "Total", "Método Pago"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tablaHistorial = new JTable(modeloHistorial);
        tablaHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaHistorial.setRowHeight(28);
        tablaHistorial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaHistorial.getColumnModel().getColumn(2).setPreferredWidth(180); // Motivo más ancho

        JScrollPane scroll = new JScrollPane(tablaHistorial);
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.addActionListener(e -> cargarHistorial());

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(Color.WHITE);
        panelBoton.add(btnRefrescar);
        panel.add(panelBoton, BorderLayout.SOUTH);

        return panel;
    }
    public void cargarVentas() {
        modeloVentas.setRowCount(0);
        ventas = mediador.obtenerTodasLasVentas();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (VentaDTO v : ventas) {
            double kilos = v.getCarrito() != null
                    ? v.getCarrito().stream().mapToDouble(d -> d.getCantidadKilos()).sum() : 0;
            String metodoPago = "Desconocido";
            if (v instanceof com.mycompany.tortilleriadtos.VentaLocalDTO) {
                metodoPago = ((com.mycompany.tortilleriadtos.VentaLocalDTO) v).getMetodoPago();
            }
            modeloVentas.addRow(new Object[]{
                v.getIdVenta(),
                v.getFechaHora() != null ? sdf.format(v.getFechaHora()) : "—",
                String.format("%.3f KG", kilos),
                String.format("$ %.2f", v.getMontoTotal()),
                metodoPago
            });
        }
    }

    public void cargarHistorial() {
        modeloHistorial.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<CancelacionBO> cancelaciones = mediador.obtenerTodasLasCancelaciones();
        System.out.println("Cancelaciones encontradas: " + cancelaciones.size()); 
        for (CancelacionBO c : cancelaciones) {
            String fechaStr = c.getFechaCancelacion() != null ? sdf.format(c.getFechaCancelacion()) : "—";
            String motivo = (c.getMotivo() != null && !c.getMotivo().isBlank()) ? c.getMotivo() : "—";
            String total = "—";
            String metodo = "—";
            if (c.getVentaOriginal() != null) {
                total = String.format("$ %.2f", c.getVentaOriginal().getMontoTotal());
                if (c.getVentaOriginal() instanceof com.mycompany.tortilleriadtos.VentaLocalDTO) {
                    metodo = ((com.mycompany.tortilleriadtos.VentaLocalDTO) c.getVentaOriginal()).getMetodoPago();
                }
            }
            modeloHistorial.addRow(new Object[]{c.getIdVenta(), fechaStr, motivo, total, metodo});
        }
    }


    public void refrescarTodo() {
        cargarVentas();
        cargarHistorial();
    }

}
