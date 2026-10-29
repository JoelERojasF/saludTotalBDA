/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Conexion.ConexionBD;
import DTO.Crear.CrearResultadoDTO;
import DTO.ResultadoDTO;
import Daos.Interfaces.IParametroDAO;
import Daos.Interfaces.IResultadoDAO;
import Daos.Interfaces.ISolicitudDAO;
import Daos.ParametroDAO;
import Daos.ResultadoDAO;
import Daos.SolicitudDAO;
import Entidades.ResultadoEntidad;
import Exception.NegocioException;
import Negocio.Interfaces.IResultadoNegocio;
import Validador.Validador;
import exception.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class ResultadoNegocio implements IResultadoNegocio{
    private final IResultadoDAO resultadoDAO;
    private IParametroDAO parametroDAO;
    private ISolicitudDAO solicitudDAO;
    Validador val = new Validador();

 public ResultadoNegocio() {
        ConexionBD conexion = new ConexionBD();
        this.resultadoDAO = new ResultadoDAO(conexion);
        this.parametroDAO = new ParametroDAO(conexion);
        this.solicitudDAO = new SolicitudDAO(conexion);
    }   
            
    @Override
    public ResultadoDTO registrar(CrearResultadoDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("No se recibieron los datos del resultado.");
        }
        validar(dto.getResultado(), dto.getObservacion(), dto.getIdSolicitud(), dto.getIdParametro());

        try {
            ResultadoEntidad entidad = DTOAEntidad(dto);
            return EntidadADTO(resultadoDAO.guardar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar el resultado. Revisa la conexion con la base de datos.", e);
        }
    }

    @Override
    public ResultadoDTO actualizar(ResultadoDTO dto) throws NegocioException {
        if (dto == null || dto.getId() == null) {
            throw new NegocioException("Falta el id del resultado a actualizar.");
        }
        validar(dto.getResultado(), dto.getObservacion(), dto.getIdSolicitud(), dto.getIdParametro());

        try {
            if (resultadoDAO.buscarPorId(dto.getId()) == null) {
                throw new NegocioException("El resultado que intentas actualizar no existe.");
            }
            ResultadoEntidad entidad = DTOAEntidad(dto);
            entidad.setId(dto.getId());
            return EntidadADTO(resultadoDAO.actualizar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar el resultado por: "+ e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del resultado a eliminar.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            if (resultadoDAO.buscarPorId(id) == null) {
                throw new NegocioException("El resultado que intentas eliminar no existe.");
            }
            resultadoDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo eliminar el resultado por: "+ e.getMessage(), e);
        }
    }

    @Override
    public ResultadoDTO buscarPorId(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del resultado.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            ResultadoEntidad entidad = resultadoDAO.buscarPorId(id);
            if (entidad == null) {
                throw new NegocioException("No se encontro el resultado.");
            }
            return EntidadADTO(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo consultar el resultado por: "+ e.getMessage(), e);
        }
    }

    @Override
    public List<ResultadoDTO> obtenerTodos() throws NegocioException {
        try {
            List<ResultadoDTO> lista = new ArrayList<>();
            for (ResultadoEntidad e : resultadoDAO.obtenerTodos()) {
                lista.add(EntidadADTO(e));
            }
            return lista;
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo obtener la lista de estudios por: "+ e.getMessage(), e);
        }
    }
    
    private void validar(String resultado, String observacion, Integer idSolicitud, Integer idParametro) throws NegocioException {
        if (!val.validarTexto(resultado)) {
            throw new NegocioException("El resultado es invalido.");
        }
        if (!val.validarTexto(observacion)) {
            throw new NegocioException("La observacion es invalida.");
        }
        if (!(idSolicitud > 0)) {
            throw new NegocioException("El id de la solicitud es invalido.");
        }
        if (!(idParametro > 0)) {
            throw new NegocioException("El id del parametro es invalido.");
        }
    }
    
    private ResultadoDTO EntidadADTO(ResultadoEntidad e) {
        return new ResultadoDTO(
                e.getId(),
                e.getResultado(),
                e.getObservacion(),
                e.getSolicitud().getId(),
                e.getParametro().getId());
    }

    private ResultadoEntidad DTOAEntidad(ResultadoDTO dto) throws PersistenciaException {
        ResultadoEntidad creado = new ResultadoEntidad(
                dto.getResultado(),
                dto.getObservacion(),
                solicitudDAO.buscarPorId(dto.getIdSolicitud()),
                parametroDAO.buscarPorId(dto.getIdParametro()));
        creado.setId(dto.getId());
        return creado;
    }

    private ResultadoEntidad DTOAEntidad(CrearResultadoDTO dto) throws PersistenciaException {
        return new ResultadoEntidad(
                dto.getResultado(),
                dto.getObservacion(),
                solicitudDAO.buscarPorId(dto.getIdSolicitud()),
                parametroDAO.buscarPorId(dto.getIdParametro()));
    }
    
}
