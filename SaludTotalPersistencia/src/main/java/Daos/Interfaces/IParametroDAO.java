/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Daos.Interfaces;

import Entidades.ParametroEntidad;
import exception.PersistenciaException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface IParametroDAO {
    ParametroEntidad guardar(ParametroEntidad parametro) throws PersistenciaException;

    ParametroEntidad actualizar(ParametroEntidad parametro) throws PersistenciaException;

    void eliminar(Integer id) throws PersistenciaException;

    ParametroEntidad buscarPorId(Integer id) throws PersistenciaException;

    List<ParametroEntidad> obtenerTodos() throws PersistenciaException;
}
