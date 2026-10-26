package Daos.Interfaces;

import exception.PersistenciaException;
import Entidades.UsuarioEntidad;


public interface IUsuarioDAO {
    
    UsuarioEntidad guardar(UsuarioEntidad usuario) throws PersistenciaException;

    UsuarioEntidad buscarPorNombre(String nombre) throws PersistenciaException;
    
}
