/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tortilleriadatos;

import com.mycompany.tortilleriadtos.CancelacionDTO;
import java.util.List;

/**
 *
 * @author jorge
 */
public interface ICancelacionDAO {
    boolean registrarCancelacion(CancelacionDTO cancelacion);
    List<CancelacionDTO> obtenerTodasLasCancelaciones();
}
