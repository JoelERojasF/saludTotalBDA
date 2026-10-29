/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Conexion.ConexionBD;
import DTO.Crear.CrearParametroDTO;
import DTO.ParametroDTO;
import Daos.Interfaces.IEstudioDAO;
import Daos.Interfaces.IParametroDAO;
import Daos.ParametroDAO;
import Entidades.ParametroEntidad;
import Exception.NegocioException;
import Negocio.Interfaces.IParametroNegocio;
import Validador.Validador;
import exception.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class ParametroNegocio implements IParametroNegocio{
    private final IParametroDAO parametroDAO;
    private IEstudioDAO estudioDAO;
    Validador val = new Validador();

    public ParametroNegocio() {
        this.parametroDAO = new ParametroDAO(new ConexionBD());
    }

    @Override
    public ParametroDTO registrar(CrearParametroDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("No se recibieron los datos del parametro.");
        }
        validar(dto.getNombre(), dto.getDescripcion(), dto.getOrdenAparicion(), dto.getUnidadMedida(), dto.getIdEstudio());
        
        try {
            ParametroEntidad entidad = DTOAEntidad(dto);
            return EntidadADTO(parametroDAO.guardar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar el parametro. Revisa la conexion con la base de datos.", e);
        }
    }

    @Override
    public ParametroDTO actualizar(ParametroDTO dto) throws NegocioException {
        if (dto == null || dto.getId() == null) {
            throw new NegocioException("Falta el id del parametro a actualizar.");
        }
        validar(dto.getNombre(), dto.getDescripcion(), dto.getOrdenAparicion(),dto.getUnidadMedida(), dto.getIdEstudio());

        try {
            if (parametroDAO.buscarPorId(dto.getId()) == null) {
                throw new NegocioException("El parametro que intentas actualizar no existe.");
            }
            ParametroEntidad entidad = DTOAEntidad(dto);
            entidad.setId(dto.getId());
            return EntidadADTO(parametroDAO.actualizar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar el parametro por: "+ e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del parametro a eliminar.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            if (parametroDAO.buscarPorId(id) == null) {
                throw new NegocioException("El parametro que intentas eliminar no existe.");
            }
            parametroDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo eliminar el parametro por: "+ e.getMessage(), e);
        }
    }

    @Override
    public ParametroDTO buscarPorId(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del parametro.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            ParametroEntidad entidad = parametroDAO.buscarPorId(id);
            if (entidad == null) {
                throw new NegocioException("No se encontro el parametro.");
            }
            return EntidadADTO(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo consultar el parametro por: "+ e.getMessage(), e);
        }
    }

    @Override
    public List<ParametroDTO> obtenerTodos() throws NegocioException {
        try {
            List<ParametroDTO> lista = new ArrayList<>();
            for (ParametroEntidad e : parametroDAO.obtenerTodos()) {
                lista.add(EntidadADTO(e));
            }
            return lista;
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo obtener la lista de parametros por: "+ e.getMessage(), e);
        }
    }
    
     private void validar(String nombre, String descripcion, Integer ordenAparicion,String unidadMedida,  Integer idEstudio) throws NegocioException {
        if (!val.validarNombrePersona(nombre)) {
            throw new NegocioException("El nombre es invalido.");
        }
        if (!val.validarTexto(descripcion)) {
            throw new NegocioException("La descripcion es invalida.");
        }
        if (!(ordenAparicion > 0)) {
            throw new NegocioException("El orden de aparicion es invalido.");
        }
         if (!val.validarTexto(unidadMedida)) {
             throw new NegocioException("El nombre es invalido.");
         }
        if (!(idEstudio > 0)) {
            throw new NegocioException("El id del estudio es invalido.");
        }
    }
    
    private ParametroDTO EntidadADTO(ParametroEntidad e) {
        return new ParametroDTO(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getOrdenAparicion(),
                e.getUnidadMedida(),
                e.getNumerico(),
                e.getEstudio().getId());
    }

    private ParametroEntidad DTOAEntidad(ParametroDTO dto) throws PersistenciaException {
        ParametroEntidad creado = new ParametroEntidad(
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getOrdenAparicion(),
                dto.getUnidadMedida(),
                dto.getNumerico(),
                estudioDAO.buscarPorId(dto.getIdEstudio()));
        creado.setId(dto.getId());
        return creado;
    }

    private ParametroEntidad DTOAEntidad(CrearParametroDTO dto) throws PersistenciaException {
        return new ParametroEntidad(
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getOrdenAparicion(),
                dto.getUnidadMedida(),
                dto.getNumerico(),
                estudioDAO.buscarPorId(dto.getIdEstudio()));
    }
    
}
