/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Daos.Interfaces;

import Entidades.EmpresaEntidad;
import exception.PersistenciaException;

/**
 *
 * @author le0jx
 */
public interface IEmpresaDAO {
    EmpresaEntidad guardar(EmpresaEntidad empresa) throws PersistenciaException;

    EmpresaEntidad buscarPorId(Integer id) throws PersistenciaException;

}
