/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cierreCaja;

import com.mycompany.tortilleriapresentacion.PantallaConteoEfectivo;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author MoriTejo
 */
public class ControlPresentacionCierre {
    private FachadaCaja fachada = new FachadaCaja();
    private CorteCajaDTO corteTemporal;
    private String usuarioActual = "admin default";
    
    public void setUsuarioActual(String usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String getUsuarioActual() {
        return usuarioActual;
    }
    

    public void iniciarCierre() {
        double[] totales = fachada.obtenerTotalesDia();
        new PantallaCierrePrincipal(this, totales).setVisible(true);
    }

    public void irAConteoEfectivo(JFrame actual) {
        actual.dispose(); 
        new PantallaConteoEfectivo(this).setVisible(true);
    }

    public void procesarConteo(double efectivoFisico, JFrame actual) {
        this.corteTemporal = fachada.calcularCierre(efectivoFisico); 
        actual.dispose();
        new PantallaRevisionCorte(this, corteTemporal).setVisible(true);
    }

    public void irAReporteIncidente(JFrame actual) {
        actual.dispose();
        new PantallaReporteIncidente(this).setVisible(true);
    }

    public void finalizarCorte(JFrame actual) {
        this.corteTemporal.setUsuario(usuarioActual); 
        boolean exito = fachada.procesarCierre(this.corteTemporal);
        if (exito) {
            actual.dispose();
            new PantallaTurnoCerrado(this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(actual, "Error al guardar el corte en la base de datos.");
        }
    }
    
    public double[] obtenerTotalesDelDia() {
        return fachada.obtenerTotalesDia();
    }
}
