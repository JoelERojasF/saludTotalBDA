/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.Interfaces;

import DTO.Crear.CrearEmpresaDTO;
import DTO.EmpresaDTO;
import Exception.NegocioException;

/**
 *
 * @author le0jx
 */
public interface IEmpresaNegocio {
    
    EmpresaDTO registrar(CrearEmpresaDTO dto) throws NegocioException;

    EmpresaDTO buscarPorId(Integer id) throws NegocioException;
}
