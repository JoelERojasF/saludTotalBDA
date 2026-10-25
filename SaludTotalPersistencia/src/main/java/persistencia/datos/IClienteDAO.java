package persistencia.datos;

import Entidades.ClienteEntidad;
import java.util.List;


public interface IClienteDAO {
    ClienteEntidad guardar(ClienteEntidad cliente) throws PersistenciaException;

    ClienteEntidad actualizar(ClienteEntidad cliente) throws PersistenciaException;

    void eliminar(Integer id) throws PersistenciaException;

    ClienteEntidad buscarPorId(Integer id) throws PersistenciaException; // null si no existe

    List<ClienteEntidad> obtenerTodos() throws PersistenciaException;
}
