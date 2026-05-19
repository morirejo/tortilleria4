/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tortilleriadtos;

import java.util.Date;

/**
 *
 * @author jorge
 */
public class CancelacionDTO {
    private String idCancelacion;   
    private int idVenta;            
    private Date fechaCancelacion;  
    private String motivo;         
    private VentaDTO ventaOriginal; 

    public CancelacionDTO() {}

    public CancelacionDTO(int idVenta, Date fechaCancelacion, String motivo, VentaDTO ventaOriginal) {
        this.idVenta = idVenta;
        this.fechaCancelacion = fechaCancelacion;
        this.motivo = motivo;
        this.ventaOriginal = ventaOriginal;
    }

    public String getIdCancelacion() { return idCancelacion; }
    public void setIdCancelacion(String idCancelacion) { this.idCancelacion = idCancelacion; }

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public Date getFechaCancelacion() { return fechaCancelacion; }
    public void setFechaCancelacion(Date fechaCancelacion) { this.fechaCancelacion = fechaCancelacion; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public VentaDTO getVentaOriginal() { return ventaOriginal; }
    public void setVentaOriginal(VentaDTO ventaOriginal) { this.ventaOriginal = ventaOriginal; }

}
