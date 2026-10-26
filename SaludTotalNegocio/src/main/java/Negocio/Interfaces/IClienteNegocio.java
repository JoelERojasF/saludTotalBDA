package Negocio.Interfaces;

import DTO.ClienteDTO;
import DTO.Crear.CrearClienteDTO;
import Exception.NegocioException;
import java.util.List;


public interface IClienteNegocio {
    
    ClienteDTO registrar(CrearClienteDTO dto) throws NegocioException;

    ClienteDTO actualizar(ClienteDTO dto) throws NegocioException;

    void eliminar(Integer id) throws NegocioException;

    ClienteDTO buscarPorId(Integer id) throws NegocioException;

    List<ClienteDTO> obtenerTodos() throws NegocioException;
    
}
