/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import com.mycompany.tortilleriadtos.VentaDTO;
import java.util.Date;

/**
 *
 * @author jorge
 */
public class CancelacionBO {
    private String idCancelacion;
    private int idVenta;
    private Date fechaCancelacion;
    private String motivo;
    private VentaDTO ventaOriginal; 

    public CancelacionBO() {}

    public CancelacionBO(int idVenta, Date fechaCancelacion, String motivo, VentaDTO ventaOriginal) {
        this.idVenta = idVenta;
        this.fechaCancelacion = fechaCancelacion;
        this.motivo = motivo;
        this.ventaOriginal = ventaOriginal;
    }

    public String getIdCancelacion() { return idCancelacion; }
    public void setIdCancelacion(String id) { this.idCancelacion = id; }

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public Date getFechaCancelacion() { return fechaCancelacion; }
    public void setFechaCancelacion(Date fecha) { this.fechaCancelacion = fecha; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public VentaDTO getVentaOriginal() { return ventaOriginal; }
    public void setVentaOriginal(VentaDTO v) { this.ventaOriginal = v; }

}
