/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Conexion.ConexionBD;
import DTO.Crear.CrearEstudioDTO;
import DTO.EstudioDTO;
import Daos.EstudioDAO;
import Daos.Interfaces.IEstudioDAO;
import Daos.Interfaces.ITipoMuestraDAO;
import Entidades.EstudioEntidad;
import Exception.NegocioException;
import Negocio.Interfaces.IEstudioNegocio;
import Validador.Validador;
import exception.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class EstudioNegocio implements IEstudioNegocio{
    
    private final IEstudioDAO estudioDAO;
    private ITipoMuestraDAO tipoMuestraDAO;
    Validador val = new Validador();

    public EstudioNegocio() {
        this.estudioDAO = new EstudioDAO(new ConexionBD());
    }

    @Override
    public EstudioDTO registrar(CrearEstudioDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("No se recibieron los datos del estudio.");
        }
        validar(dto.getNombre(), dto.getDescripcion(), dto.getIdTipoMuestra());

        try {
            EstudioEntidad entidad = DTOAEntidad(dto);
            return EntidadADTO(estudioDAO.guardar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar el estudio. Revisa la conexion con la base de datos.", e);
        }
    }

    @Override
    public EstudioDTO actualizar(EstudioDTO dto) throws NegocioException {
        if (dto == null || dto.getId() == null) {
            throw new NegocioException("Falta el id del estudio a actualizar.");
        }
        validar(dto.getNombre(), dto.getDescripcion(), dto.getIdTipoMuestra());

        try {
            if (estudioDAO.buscarPorId(dto.getId()) == null) {
                throw new NegocioException("El Estudio que intentas actualizar no existe.");
            }
            EstudioEntidad entidad = DTOAEntidad(dto);
            entidad.setId(dto.getId());
            return EntidadADTO(estudioDAO.actualizar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar el estudio.", e);
        }
    }

    @Override
    public void eliminar(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del estudio a eliminar.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            if (estudioDAO.buscarPorId(id) == null) {
                throw new NegocioException("El estudio que intentas eliminar no existe.");
            }
            estudioDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo eliminar el estudio.", e);
        }
    }

    @Override
    public EstudioDTO buscarPorId(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del estudio.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            EstudioEntidad entidad = estudioDAO.buscarPorId(id);
            if (entidad == null) {
                throw new NegocioException("No se encontro el estudio.");
            }
            return EntidadADTO(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo consultar el estudio.", e);
        }
    }

    @Override
    public List<EstudioDTO> obtenerTodos() throws NegocioException {
        try {
            List<EstudioDTO> lista = new ArrayList<>();
            for (EstudioEntidad e : estudioDAO.obtenerTodos()) {
                lista.add(EntidadADTO(e));
            }
            return lista;
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo obtener la lista de estudios.", e);
        }    
    }
    
    private void validar(String nombre, String descripcion, Integer idTipoMuestra) throws NegocioException {
        if (!val.validarNombrePersona(nombre)) {
            throw new NegocioException("El nombre es invalido.");
        }
        if (!val.validarTexto(descripcion)) {
            throw new NegocioException("La descripcion es invalida.");
        }
        if (!(idTipoMuestra > 0)) {
            throw new NegocioException("El id del tipo de muestra es invalido.");
        }
    }
    
    private EstudioDTO EntidadADTO(EstudioEntidad e) {
        return new EstudioDTO(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getTipoMuestra().getId());
    }

    private EstudioEntidad DTOAEntidad(EstudioDTO dto) throws PersistenciaException {
        EstudioEntidad creado = new EstudioEntidad(
                dto.getNombre(),
                dto.getDescripcion(),
                tipoMuestraDAO.buscarPorId(dto.getIdTipoMuestra()));
        creado.setId(dto.getId());
        return creado;
    }

    private EstudioEntidad DTOAEntidad(CrearEstudioDTO dto) throws PersistenciaException {
        return new EstudioEntidad(
                dto.getNombre(),
                dto.getDescripcion(),
                tipoMuestraDAO.buscarPorId(dto.getIdTipoMuestra()));
    }
}
