package Negocio;

import DTO.CrearUsuarioDTO;
import DTO.UsuarioDTO;


public interface IUsuarioNegocio {
    
    UsuarioDTO registrarUsuario(CrearUsuarioDTO dto) throws NegocioException;

    UsuarioDTO iniciarSesion(String nombre, String password) throws NegocioException;
    
}
