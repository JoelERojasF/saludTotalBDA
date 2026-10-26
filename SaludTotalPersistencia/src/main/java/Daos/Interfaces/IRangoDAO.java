/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Daos.Interfaces;

import Entidades.RangoEntidad;
import exception.PersistenciaException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface IRangoDAO {
    RangoEntidad guardar(RangoEntidad rango) throws PersistenciaException;

    RangoEntidad actualizar(RangoEntidad rango) throws PersistenciaException;

    void eliminar(Integer id) throws PersistenciaException;

    RangoEntidad buscarPorId(Integer id) throws PersistenciaException;

    List<RangoEntidad> obtenerTodos() throws PersistenciaException;
}
