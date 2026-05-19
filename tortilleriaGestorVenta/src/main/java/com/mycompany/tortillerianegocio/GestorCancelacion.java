/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tortillerianegocio;

import BOs.CancelacionBO;
import com.mycompany.tortilleriadatos.CancelacionDAO;
import com.mycompany.tortilleriadatos.ICancelacionDAO;
import com.mycompany.tortilleriadatos.IVentaDAO;
import com.mycompany.tortilleriadatos.VentaDAO;
import com.mycompany.tortilleriadtos.CancelacionDTO;
import com.mycompany.tortilleriadtos.VentaDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jorge
 */
public class GestorCancelacion {
    private final ICancelacionDAO cancelacionDAO;
    private final IVentaDAO ventaDAO;

    public GestorCancelacion() {
        this.cancelacionDAO = new CancelacionDAO();
        this.ventaDAO = new VentaDAO();
        System.out.println("GestorCancelacion creado, DAO: " + cancelacionDAO.getClass().getName());
    }

  
    public boolean cancelarVentaConRegistro(int idVenta, String motivo, String usuario, VentaDTO snapshot) {
        CancelacionDTO registro = new CancelacionDTO(idVenta, new Date(), motivo, usuario, snapshot);
        boolean registrado = cancelacionDAO.registrarCancelacion(registro);
        if (!registrado) {
            System.err.println("No se pudo registrar la cancelación del folio " + idVenta);
            return false;
        }
        boolean eliminado = ventaDAO.cancelarVenta(idVenta);
        if (!eliminado) {
            System.err.println("Se registró la cancelación pero no se pudo eliminar la venta " + idVenta);
            return false;
        }
        return true;
    }

   
    public List<CancelacionBO> obtenerTodasLasCancelaciones() {
        System.out.println("Llamando al DAO...");
        //esta cosa no jala
        
        List<CancelacionDTO> dtos = cancelacionDAO.obtenerTodasLasCancelaciones();
        List<CancelacionBO> bos = new ArrayList<>();
        for (CancelacionDTO dto : dtos) {
            CancelacionBO bo = new CancelacionBO(
                    dto.getIdVenta(),
                    dto.getFechaCancelacion(),
                    dto.getMotivo(),
                    dto.getVentaOriginal());
            bo.setIdCancelacion(dto.getIdCancelacion());
            bos.add(bo);
        }
        return bos;
    }

}
