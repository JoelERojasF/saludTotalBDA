package persistencia.datos;

import Entidades.UsuarioEntidad;


public interface IUsuarioDAO {
    
    UsuarioEntidad guardar(UsuarioEntidad usuario) throws PersistenciaException;

    UsuarioEntidad buscarPorNombre(String nombre) throws PersistenciaException; // null si no existe
    
}
