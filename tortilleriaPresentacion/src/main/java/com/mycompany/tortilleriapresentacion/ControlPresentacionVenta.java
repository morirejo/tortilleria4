/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tortilleriapresentacion;

import BOs.CancelacionBO;
import com.mycompany.tortilleriadtos.DetalleVentaDTO;
import com.mycompany.tortilleriadtos.VentaDTO;
import com.mycompany.tortilleriadtos.VentaLocalDTO;
import com.mycompany.tortillerianegocio.FachadaVenta;
import com.mycompany.tortillerianegocio.GestorCancelacion;
import com.mycompany.tortillerianegocio.IFachadaVentas;
import com.mycompany.tortilleriapresentacion.PantallaCancelacion;
import com.mycompany.tortilleriapresentacion.PantallaMetodoPago;
import com.mycompany.tortilleriapresentacion.PantallaTicket;
import com.mycompany.tortilleriapresentacion.PantallaVenta;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author MoriTejo
 */
public class ControlPresentacionVenta {
    private IFachadaVentas fachada = new FachadaVenta();


    private GestorCancelacion gestorCancelacion = new GestorCancelacion();

    private List<DetalleVentaDTO> carritoActual = new ArrayList<>();
    private double totalActual = 0.0;
    
    private String rol;
    private String usuarioActual = "cajero default";

    public ControlPresentacionVenta(String rol, String usuarioActual) {
        this.rol = rol;
        this.usuarioActual = usuarioActual;
    }

    public ControlPresentacionVenta() {}

    public void agregarProducto(String nombre, String tipoProducto, double kilos) {
        double subtotal = fachada.calcularTotal(kilos);
        DetalleVentaDTO producto = new DetalleVentaDTO(nombre, tipoProducto, kilos, subtotal);
        carritoActual.add(producto);
        totalActual += subtotal;
    }

    public double getTotalActual() { return totalActual; }

    public String getUsuarioActual() {
        return usuarioActual;
    }
    
    public void setUsuarioActual(String usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    
    public List<DetalleVentaDTO> getCarritoActual() { return carritoActual; }

    public void solicitarCobro(double efectivoRecibido, String metodoPago, JFrame pantallaPagoActual) {
        int idGenerico = 0;
        Date fechaVenta = new Date();
        VentaDTO ventaNueva = new VentaLocalDTO(idGenerico, totalActual, fechaVenta, metodoPago, usuarioActual, carritoActual);
        boolean exito = fachada.confirmarVentaLocal(ventaNueva, efectivoRecibido);

        if (exito) {
            double kilosTotales = 0.0;
            for (DetalleVentaDTO producto : carritoActual) kilosTotales += producto.getCantidadKilos();
            double totalDeLaVenta = totalActual;
            double precioPorKg = (kilosTotales > 0) ? (totalDeLaVenta / kilosTotales) : 0;
            carritoActual.clear();
            totalActual = 0.0;
            mostrarPantallaTicket(pantallaPagoActual, kilosTotales, precioPorKg, totalDeLaVenta);
        } else {
            JOptionPane.showMessageDialog(pantallaPagoActual,
                    "Error: El efectivo es insuficiente o hubo un problema.",
                    "Pago Rechazado", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarPantallaVenta(JFrame pantallaActual) {
        if (pantallaActual != null) pantallaActual.dispose();
    new PantallaVenta(this, "EMPLEADO").setVisible(true);
    }

    public void mostrarMetodoPago() {
        new PantallaMetodoPago(this).setVisible(true);
    }

    public void mostrarPantallaTicket(JFrame pantallaActual, double kilos, double precioKg, double total) {
        if (pantallaActual != null) pantallaActual.dispose();
        new PantallaTicket(this, kilos, precioKg, total).setVisible(true);
    }

    public List<VentaDTO> obtenerTodasLasVentas() {
        return fachada.obtenerTodasLasVentas();
    }


    public boolean cancelarVenta(int idVenta, String motivo, VentaDTO snapshot) {
        return gestorCancelacion.cancelarVentaConRegistro(idVenta, motivo, this.usuarioActual, snapshot);
    }

    public boolean cancelarVenta(int idVenta) {
        return fachada.cancelarVenta(idVenta);
    }


    public List<CancelacionBO> obtenerTodasLasCancelaciones() {
        return gestorCancelacion.obtenerTodasLasCancelaciones();
    }

    public void mostrarPantallaCancelacion(JFrame pantallaActual) {
        new PantallaCancelacion(this).setVisible(true);
    }

    public void mostrarHistorialCancelaciones(JFrame pantallaActual) {
        new PantallaHistorialCancelaciones(this).setVisible(true);
    }

    public void mostrarPantallaReportes(JFrame pantallaActual) {
        reporte.ControlPresentacionReporte mediadorReportes = new reporte.ControlPresentacionReporte();
        mediadorReportes.iniciarReporte();
    }

    public void mostrarPantallaCierreCaja(JFrame pantallaActual) {
        cierreCaja.ControlPresentacionCierre mediadorCierre = new cierreCaja.ControlPresentacionCierre();
        double[] totales = mediadorCierre.obtenerTotalesDelDia();
        new cierreCaja.PantallaCierrePrincipal(mediadorCierre, totales).setVisible(true);
    }

    
}