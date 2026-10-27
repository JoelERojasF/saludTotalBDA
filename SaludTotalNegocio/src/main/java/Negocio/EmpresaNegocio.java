/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Conexion.ConexionBD;
import DTO.Crear.CrearEmpresaDTO;
import DTO.EmpresaDTO;
import Daos.EmpresaDAO;
import Daos.Interfaces.IEmpresaDAO;
import Entidades.EmpresaEntidad;
import Exception.NegocioException;
import Negocio.Interfaces.IEmpresaNegocio;
import Validador.Validador;
import exception.PersistenciaException;

/**
 *
 * @author le0jx
 */
public class EmpresaNegocio implements IEmpresaNegocio{
    private final IEmpresaDAO empresaDAO;
    Validador val = new Validador();

    public EmpresaNegocio() {
        this.empresaDAO = new EmpresaDAO(new ConexionBD());
    }

    @Override
    public EmpresaDTO registrar(CrearEmpresaDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("No se recibieron los datos de la empresa.");
        }
        validar(dto.getNombre(), dto.getDirecion(), dto.getCorreo(), dto.getTelefono(), dto.getRfc(), dto.getCedula());
        
        EmpresaEntidad entidad = DTOAEntidad(dto);
        
        try {
            return EntidadADTO(empresaDAO.guardar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar la . Revisa la conexion con la base de datos.", e);
        }
    }

    @Override
    public EmpresaDTO buscarPorId(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id de la empresa.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            EmpresaEntidad empresa = empresaDAO.buscarPorId(id);
            if (empresa == null) {
                throw new NegocioException("No se encontro la empresa.");
            }
            return EntidadADTO(empresa);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo consultar la empresa.", e);
        }
        
    }
    
    private void validar(String nombre, String direcion, String correo, String telefono, String rfc, String cedula) throws NegocioException {
        if (!val.validarTexto(nombre)) {
            throw new NegocioException("El nombre es invalido.");
        }
        if (!val.validarTexto(direcion)) {
            throw new NegocioException("La direccion es invalida.");
        }
        if (!val.validarCorreo(correo)) {
            throw new NegocioException("El correo es invalido.");
        }
        if (!val.validarTelefono(telefono)) {
            throw new NegocioException("El telefono es invalido.");
        }
        if (!val.validarTexto(rfc)) {
            throw new NegocioException("El rfc es invalido.");
        }
        if (!val.validarTexto(cedula)) {
            throw new NegocioException("La cedula es invalida.");
        }
        
    }
    
    private EmpresaDTO EntidadADTO(EmpresaEntidad e) {
        return new EmpresaDTO(e.getId(), e.getNombre(), e.getDirecion(), e.getCorreo(), e.getTelefono(), e.getRfc(), e.getCedula());
    }
    
    private EmpresaEntidad DTOAEntidad(CrearEmpresaDTO dto){
        return new EmpresaEntidad(
                dto.getNombre(), 
                dto.getDirecion(), 
                val.vacioANulo(dto.getCorreo()), 
                val.vacioANulo(dto.getTelefono()), 
                dto.getRfc(), 
                dto.getCedula());
    }
    
    
    
}
