/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cierreCaja;

import java.util.Date;

/**
 *
 * @author MoriTejo
 */
public class CorteCajaBO {
    private double ventasEfectivo;
    private double ventasCredito;
    private double ventasDebito;
    private double totalSistema;
    private double efectivoContado;
    private double diferencia;
    private Date fecha;
    private String usuario;

    public CorteCajaBO(double ventasEfectivo, double ventasCredito, double ventasDebito, double efectivoContado, String usuario) {
        this.ventasEfectivo = ventasEfectivo;
        this.ventasCredito = ventasCredito;
        this.ventasDebito = ventasDebito;
        this.totalSistema = ventasEfectivo + ventasCredito + ventasDebito;
        this.efectivoContado = efectivoContado;
        this.diferencia = efectivoContado - ventasEfectivo;
        this.fecha = new Date();
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public double getVentasEfectivo() {
        return ventasEfectivo;
    }

    public void setVentasEfectivo(double ventasEfectivo) {
        this.ventasEfectivo = ventasEfectivo;
    }

    public double getVentasCredito() {
        return ventasCredito;
    }

    public void setVentasCredito(double ventasCredito) {
        this.ventasCredito = ventasCredito;
    }

    public double getVentasDebito() {
        return ventasDebito;
    }

    public void setVentasDebito(double ventasDebito) {
        this.ventasDebito = ventasDebito;
    }

    public double getTotalSistema() {
        return totalSistema;
    }

    public void setTotalSistema(double totalSistema) {
        this.totalSistema = totalSistema;
    }

    public double getEfectivoContado() {
        return efectivoContado;
    }

    public void setEfectivoContado(double efectivoContado) {
        this.efectivoContado = efectivoContado;
    }

    public double getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(double diferencia) {
        this.diferencia = diferencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}
