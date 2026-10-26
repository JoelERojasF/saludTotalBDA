/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Daos.Interfaces;

import Entidades.EstudioEntidad;
import exception.PersistenciaException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface IEstudioDAO {
    EstudioEntidad guardar(EstudioEntidad estudio) throws PersistenciaException;

    EstudioEntidad actualizar(EstudioEntidad estudio) throws PersistenciaException;

    void eliminar(Integer id) throws PersistenciaException;

    EstudioEntidad buscarPorId(Integer id) throws PersistenciaException;

    List<EstudioEntidad> obtenerTodos() throws PersistenciaException;
}
