/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Daos.Interfaces;

import Entidades.SolicitudEntidad;
import exception.PersistenciaException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface ISolicitudDAO {
    SolicitudEntidad guardar(SolicitudEntidad solicitud) throws PersistenciaException;

    SolicitudEntidad actualizar(SolicitudEntidad solicitud) throws PersistenciaException;

    void eliminar(Integer id) throws PersistenciaException;

    SolicitudEntidad buscarPorId(Integer id) throws PersistenciaException;

    List<SolicitudEntidad> obtenerTodos() throws PersistenciaException;
}
