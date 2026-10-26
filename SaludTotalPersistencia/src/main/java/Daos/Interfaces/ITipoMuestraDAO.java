/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Daos.Interfaces;

import Entidades.TipoMuestraEntidad;
import exception.PersistenciaException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface ITipoMuestraDAO {
    TipoMuestraEntidad guardar(TipoMuestraEntidad tipoMuestra) throws PersistenciaException;

    TipoMuestraEntidad actualizar(TipoMuestraEntidad tipoMuestra) throws PersistenciaException;

    void eliminar(Integer id) throws PersistenciaException;

    TipoMuestraEntidad buscarPorId(Integer id) throws PersistenciaException;

    List<TipoMuestraEntidad> obtenerTodos() throws PersistenciaException;
}
