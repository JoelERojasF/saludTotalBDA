/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import DTO.Crear.CrearDoctorDTO;
import DTO.DoctorDTO;
import Daos.Interfaces.IDoctorDAO;
import Entidades.DoctorEntidad;
import Exception.NegocioException;
import Negocio.Interfaces.IDoctorNegocio;
import Validador.Validador;
import exception.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class DoctorNegocio implements IDoctorNegocio{
    
    private final IDoctorDAO doctorDAO;
    Validador val = new Validador();

    public DoctorNegocio(IDoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    @Override
    public DoctorDTO registrar(CrearDoctorDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("No se recibieron los datos del doctor.");
        }
        validar(dto.getNombre(),  dto.getSexo());
        if (!val.validarNombrePersona(dto.getApellidoPaterno())) {
            dto.setApellidoPaterno(null);
        }
        if (!val.validarNombrePersona(dto.getApellidoMaterno())) {
            dto.setApellidoMaterno(null);
        }

        DoctorEntidad entidad = DTOAEntidad(dto);
        try {
            return EntidadADTO(doctorDAO.guardar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar el doctor. Revisa la conexion con la base de datos.", e);
        }
    }

    @Override
    public DoctorDTO actualizar(DoctorDTO dto) throws NegocioException {
        if (dto == null || dto.getId() == null) {
            throw new NegocioException("Falta el id del doctor a actualizar.");
        }
        validar(dto.getNombre(),  dto.getSexo());
        if (!val.validarNombrePersona(dto.getApellidoPaterno())) {
            dto.setApellidoPaterno(null);
        }
        if (!val.validarNombrePersona(dto.getApellidoMaterno())) {
            dto.setApellidoMaterno(null);
        }

        try {
            if (doctorDAO.buscarPorId(dto.getId()) == null) {
                throw new NegocioException("El doctor que intentas actualizar no existe.");
            }
            DoctorEntidad entidad = DTOAEntidad(dto);
            entidad.setId(dto.getId());
            return EntidadADTO(doctorDAO.actualizar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar el doctor.", e);
        }
    }

    @Override
    public void eliminar(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del doctor a eliminar.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            if (doctorDAO.buscarPorId(id) == null) {
                throw new NegocioException("El doctor que intentas eliminar no existe.");
            }
            doctorDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo eliminar el doctor. Puede tener solicitudes registradas.", e);
        }
    }

    @Override
    public DoctorDTO buscarPorId(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del doctor.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            DoctorEntidad entidad = doctorDAO.buscarPorId(id);
            if (entidad == null) {
                throw new NegocioException("No se encontro el doctor.");
            }
            return EntidadADTO(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo consultar el doctor.", e);
        }
    }

    @Override
    public List<DoctorDTO> obtenerTodos() throws NegocioException {
        try {
            List<DoctorDTO> lista = new ArrayList<>();
            for (DoctorEntidad e : doctorDAO.obtenerTodos()) {
                lista.add(EntidadADTO(e));
            }
            return lista;
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo obtener la lista de doctores.", e);
        }
    }
    
    private void validar(String nombre, String sexo) throws NegocioException {
        if (!val.validarNombrePersona(nombre)) {
            throw new NegocioException("El nombre es invalido.");
        }
        if (!val.validarSexo(sexo)) {
            throw new NegocioException("El sexo es invalido.");
        }
    }

    private DoctorDTO EntidadADTO(DoctorEntidad e) {
        return new DoctorDTO(
                e.getId(),
                e.getNombre(),
                e.getApellidoPaterno(),
                e.getApellidoMaterno(),
                e.getSexo());
    }
    
    private DoctorEntidad DTOAEntidad(DoctorDTO dto){
        return new DoctorEntidad(
                    dto.getNombre().trim(),
                    val.vacioANulo(dto.getApellidoPaterno()),
                    val.vacioANulo(dto.getApellidoMaterno()),
                    dto.getSexo());
    }
    
    private DoctorEntidad DTOAEntidad(CrearDoctorDTO dto){
        return new DoctorEntidad(
                    dto.getNombre().trim(),
                    val.vacioANulo(dto.getApellidoPaterno()),
                    val.vacioANulo(dto.getApellidoMaterno()),
                    dto.getSexo());
    }
    
}
