/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.Interfaces;

import DTO.Crear.CrearTipoMuestraDTO;
import DTO.TipoMuestraDTO;
import Exception.NegocioException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface ITipoMuestraNegocio {
    TipoMuestraDTO registrar(CrearTipoMuestraDTO dto) throws NegocioException;

    TipoMuestraDTO actualizar(TipoMuestraDTO dto) throws NegocioException;

    void eliminar(Integer id) throws NegocioException;

    TipoMuestraDTO buscarPorId(Integer id) throws NegocioException;

    List<TipoMuestraDTO> obtenerTodos() throws NegocioException;
}
