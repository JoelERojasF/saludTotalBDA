/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Daos.Interfaces;

import Entidades.DoctorEntidad;
import exception.PersistenciaException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface IDoctorDAO {
    DoctorEntidad guardar(DoctorEntidad doctor) throws PersistenciaException;

    DoctorEntidad actualizar(DoctorEntidad doctor) throws PersistenciaException;

    void eliminar(Integer id) throws PersistenciaException;

    DoctorEntidad buscarPorId(Integer id) throws PersistenciaException;

    List<DoctorEntidad> obtenerTodos() throws PersistenciaException;
}
