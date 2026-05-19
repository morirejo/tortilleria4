/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cierreCaja;


/**
 *
 * @author MoriTejo
 */
public class GestorCaja implements IGestorCaja {
    private ICajaDAO dao = new CajaDAO();

    @Override
    public CorteCajaDTO prepararResumen(double efectivoFisico) {
        double[] totales = dao.obtenerVentasSegmentadas();
        return new CorteCajaDTO(totales[0], totales[1], totales[2], efectivoFisico, "En proceso");
    }
    
    @Override
    public double[] obtenerTotalesSistema() {
        return dao.obtenerVentasSegmentadas();
    }

    @Override
    public boolean procesarCierre(CorteCajaDTO corteDTO) {
        CorteCajaBO corteBO = new CorteCajaBO(
            corteDTO.getVentasEfectivo(), 
            corteDTO.getVentasCredito(), 
            corteDTO.getVentasDebito(), 
            corteDTO.getEfectivoContado(),
            corteDTO.getUsuario()   
        );
        if (corteBO.getEfectivoContado() < 0) {
            return false; 
        }
        CorteCajaDTO dtoParaGuardar = new CorteCajaDTO(
            corteBO.getVentasEfectivo(), 
            corteBO.getVentasCredito(), 
            corteBO.getVentasDebito(), 
            corteBO.getEfectivoContado(),
            corteBO.getUsuario() 
        );
        return dao.guardarCorte(dtoParaGuardar);
    }
}