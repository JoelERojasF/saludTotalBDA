package Negocio.Interfaces;

import DTO.Crear.CrearUsuarioDTO;
import DTO.UsuarioDTO;
import Exception.NegocioException;


public interface IUsuarioNegocio {
    
    UsuarioDTO registrarUsuario(CrearUsuarioDTO dto) throws NegocioException;

    UsuarioDTO iniciarSesion(String nombre, String password) throws NegocioException;
    
}
