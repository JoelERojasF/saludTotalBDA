/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.Interfaces;

import DTO.Crear.CrearEstudioDTO;
import DTO.EstudioDTO;
import Exception.NegocioException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface IEstudioNegocio {
    EstudioDTO registrar(CrearEstudioDTO dto) throws NegocioException;

    EstudioDTO actualizar(EstudioDTO dto) throws NegocioException;

    void eliminar(Integer id) throws NegocioException;

    EstudioDTO buscarPorId(Integer id) throws NegocioException;

    List<EstudioDTO> obtenerTodos() throws NegocioException;
}
