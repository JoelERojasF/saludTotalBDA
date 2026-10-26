/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.Interfaces;

import DTO.Crear.CrearParametroDTO;
import DTO.ParametroDTO;
import Exception.NegocioException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface IParametroNegocio {
    ParametroDTO registrar(CrearParametroDTO dto) throws NegocioException;

    ParametroDTO actualizar(ParametroDTO dto) throws NegocioException;

    void eliminar(Integer id) throws NegocioException;

    ParametroDTO buscarPorId(Integer id) throws NegocioException;

    List<ParametroDTO> obtenerTodos() throws NegocioException;
}
