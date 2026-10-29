/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.Interfaces;

import DTO.Crear.CrearSolicitudDTO;
import DTO.SolicitudDTO;
import Exception.NegocioException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface ISolicitudNegocio {
    SolicitudDTO registrar(CrearSolicitudDTO dto) throws NegocioException;

    SolicitudDTO actualizar(SolicitudDTO dto) throws NegocioException;

    void cancelarSolicitud(Integer id) throws NegocioException;

    SolicitudDTO buscarPorId(Integer id) throws NegocioException;

    List<SolicitudDTO> obtenerTodos() throws NegocioException;
    
    public List<SolicitudDTO> obtenerTodosActivos() throws NegocioException;
    
    public List<SolicitudDTO> obtenerTodosPendientes() throws NegocioException;
}
