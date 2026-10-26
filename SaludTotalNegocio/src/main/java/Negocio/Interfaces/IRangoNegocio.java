/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.Interfaces;

import DTO.Crear.CrearRangoDTO;
import DTO.RangoDTO;
import Exception.NegocioException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface IRangoNegocio {
    RangoDTO registrar(CrearRangoDTO dto) throws NegocioException;

    RangoDTO actualizar(RangoDTO dto) throws NegocioException;

    void eliminar(Integer id) throws NegocioException;

    RangoDTO buscarPorId(Integer id) throws NegocioException;

    List<RangoDTO> obtenerTodos() throws NegocioException;
}
