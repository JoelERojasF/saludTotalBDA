/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio.Interfaces;

import DTO.Crear.CrearDoctorDTO;
import DTO.DoctorDTO;
import Exception.NegocioException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface IDoctorNegocio {
    
    DoctorDTO registrar(CrearDoctorDTO dto) throws NegocioException;

    DoctorDTO actualizar(DoctorDTO dto) throws NegocioException;

    void eliminar(Integer id) throws NegocioException;

    DoctorDTO buscarPorId(Integer id) throws NegocioException;

    List<DoctorDTO> obtenerTodos() throws NegocioException;
}
