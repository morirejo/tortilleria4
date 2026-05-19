/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tortilleriapresentacion;

import BOs.CancelacionBO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jorge
 */
public class PantallaHistorialCancelaciones extends JFrame{
    private final ControlPresentacionVenta mediador;
    private DefaultTableModel modeloTabla;

    public PantallaHistorialCancelaciones(ControlPresentacionVenta mediador) {
        this.mediador = mediador;
        initComponents();
        cargarCancelaciones();
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Historial de Cancelaciones");
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Historial de Cancelaciones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new String[]{"Folio Venta", "Fecha Cancelación", "Motivo", "Total Venta", "Método Pago"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(28);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(650, 320));
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.addActionListener(e -> cargarCancelaciones());

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(Color.WHITE);
        panelBoton.add(btnRefrescar);
        panel.add(panelBoton, BorderLayout.SOUTH);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void cargarCancelaciones() {
        modeloTabla.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<CancelacionBO> cancelaciones = mediador.obtenerTodasLasCancelaciones();

        for (CancelacionBO c : cancelaciones) {
            String fechaStr = c.getFechaCancelacion() != null
                    ? sdf.format(c.getFechaCancelacion()) : "—";
            String motivo = (c.getMotivo() != null && !c.getMotivo().isBlank())
                    ? c.getMotivo() : "—";
            String totalStr = "—";
            String metodoPago = "—";
            if (c.getVentaOriginal() != null) {
                totalStr = String.format("$ %.2f", c.getVentaOriginal().getMontoTotal());
                if (c.getVentaOriginal() instanceof com.mycompany.tortilleriadtos.VentaLocalDTO) {
                    metodoPago = ((com.mycompany.tortilleriadtos.VentaLocalDTO) c.getVentaOriginal()).getMetodoPago();
                }
            }
            modeloTabla.addRow(new Object[]{
                c.getIdVenta(), fechaStr, motivo, totalStr, metodoPago
            });
        }
    }

}
