/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Conexion.ConexionBD;
import DTO.Crear.CrearRangoDTO;
import DTO.RangoDTO;
import Daos.Interfaces.IParametroDAO;
import Daos.Interfaces.IRangoDAO;
import Daos.ParametroDAO;
import Daos.RangoDAO;
import Entidades.RangoEntidad;
import Exception.NegocioException;
import Negocio.Interfaces.IRangoNegocio;
import Validador.Validador;
import exception.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class RangoNegocio implements IRangoNegocio{
    private final IRangoDAO rangoDAO;
    private IParametroDAO parametroDAO;
    private ParametroNegocio parN = new ParametroNegocio();
    Validador val = new Validador();

    public RangoNegocio() {
        this.rangoDAO = new RangoDAO(new ConexionBD());
    }
    
    @Override
    public RangoDTO registrar(CrearRangoDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("No se recibieron los datos del rango.");
        }
        validar( dto.getInicio(), dto.getFin(), dto.getEdadInicio(), dto.getEdadFin(), dto.getIdParametro());
        try {
            RangoEntidad entidad = DTOAEntidad(dto);
            return EntidadADTO(rangoDAO.guardar(entidad));
            
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar el rango. Revisa la conexion con la base de datos.", e);
        }
    }

    @Override
    public RangoDTO actualizar(RangoDTO dto) throws NegocioException {
        if (dto == null || dto.getId() == null) {
            throw new NegocioException("Falta el id del rango a actualizar.");
        }
        validar(dto.getInicio(), dto.getFin(), dto.getEdadInicio(), dto.getEdadFin(), dto.getIdParametro());

        try {
            if (rangoDAO.buscarPorId(dto.getId()) == null) {
                throw new NegocioException("El rango que intentas actualizar no existe.");
            }
            RangoEntidad entidad = DTOAEntidad(dto);
            entidad.setId(dto.getId());
            return EntidadADTO(rangoDAO.actualizar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar el rango por: "+ e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del rango a eliminar.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            if (rangoDAO.buscarPorId(id) == null) {
                throw new NegocioException("El rango que intentas eliminar no existe.");
            }
            rangoDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo eliminar el rango por: "+ e.getMessage(), e);
        }
    }

    @Override
    public RangoDTO buscarPorId(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del rango.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            RangoEntidad entidad = rangoDAO.buscarPorId(id);
            if (entidad == null) {
                throw new NegocioException("No se encontro el rango.");
            }
            return EntidadADTO(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo consultar el rango por: "+ e.getMessage(), e);
        }
    }

    @Override
    public List<RangoDTO> obtenerTodos() throws NegocioException {
        try {
            List<RangoDTO> lista = new ArrayList<>();
            for (RangoEntidad e : rangoDAO.obtenerTodos()) {
                lista.add(EntidadADTO(e));
            }
            return lista;
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo obtener la lista de rangos por: "+ e.getMessage(), e);
        }
    }
    
    private void validar(Double inicio, Double fin, Integer edadInicio, Integer edadFin, Integer idParametro) throws NegocioException {
        if (!(inicio > 0)) {
            throw new NegocioException("El inicio del rango es invalido.");
        }
        if (!(fin > 0)) {
            throw new NegocioException("El fin del rango es invalido.");
        }
        if(inicio > fin){
            throw new NegocioException("El inicio del rango no puede ser mayor que el fin.");
        }
        if (!(edadInicio > 0)) {
            throw new NegocioException("La edad de inicio es invalida.");
        }
        if (!(edadFin > 0)) {
            throw new NegocioException("La edad de fin es invalida.");
        }
        if(edadInicio > edadFin){
            throw new NegocioException("La edad de inicio no puede ser mayor que la edad de fin.");
        }
        if (!(idParametro > 0)) {
            throw new NegocioException("El id del parametro es invalido.");
        }
    }
    
    private RangoDTO EntidadADTO(RangoEntidad e) {
        return new RangoDTO(
                e.getId(),
                e.getInicio(),
                e.getFin(),
                e.getEdadInicio(),
                e.getEdadFin(),
                e.getParametro().getId());
    }

    private RangoEntidad DTOAEntidad(RangoDTO dto) throws PersistenciaException {
        RangoEntidad creado = new RangoEntidad(
                dto.getInicio(),
                dto.getFin(),
                dto.getEdadInicio(),
                dto.getEdadFin(),
                parametroDAO.buscarPorId(dto.getIdParametro()));
        creado.setId(dto.getId());
        return creado;
    }

    private RangoEntidad DTOAEntidad(CrearRangoDTO dto) throws PersistenciaException {
        return new RangoEntidad(
                dto.getInicio(),
                dto.getFin(),
                dto.getEdadInicio(),
                dto.getEdadFin(),
                parametroDAO.buscarPorId(dto.getIdParametro()));
    }
}
