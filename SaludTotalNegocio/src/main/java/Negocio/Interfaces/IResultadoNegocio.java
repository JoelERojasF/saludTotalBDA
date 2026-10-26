/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.Interfaces;

import DTO.Crear.CrearResultadoDTO;
import DTO.ResultadoDTO;
import Exception.NegocioException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface IResultadoNegocio {
    ResultadoDTO registrar(CrearResultadoDTO dto) throws NegocioException;

    ResultadoDTO actualizar(ResultadoDTO dto) throws NegocioException;

    void eliminar(Integer id) throws NegocioException;

    ResultadoDTO buscarPorId(Integer id) throws NegocioException;

    List<ResultadoDTO> obtenerTodos() throws NegocioException;
}
