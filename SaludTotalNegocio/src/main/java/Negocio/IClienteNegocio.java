package Negocio;

import DTO.ClienteDTO;
import DTO.CrearClienteDTO;
import java.util.List;


public interface IClienteNegocio {
    
    ClienteDTO registrarCliente(CrearClienteDTO dto) throws NegocioException;

    ClienteDTO actualizarCliente(ClienteDTO dto) throws NegocioException;

    void eliminarCliente(Integer id) throws NegocioException;

    ClienteDTO buscarPorId(Integer id) throws NegocioException;

    List<ClienteDTO> obtenerTodos() throws NegocioException;
    
}
