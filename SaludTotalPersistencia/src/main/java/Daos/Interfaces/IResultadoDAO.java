/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Daos.Interfaces;

import Entidades.ResultadoEntidad;
import exception.PersistenciaException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface IResultadoDAO {
    ResultadoEntidad guardar(ResultadoEntidad resultado) throws PersistenciaException;

    ResultadoEntidad actualizar(ResultadoEntidad resultado) throws PersistenciaException;

    void eliminar(Integer id) throws PersistenciaException;

    ResultadoEntidad buscarPorId(Integer id) throws PersistenciaException;

    List<ResultadoEntidad> obtenerTodos() throws PersistenciaException;
}
