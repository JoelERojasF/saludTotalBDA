/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Conexion.ConexionBD;
import DTO.Crear.CrearSolicitudDTO;
import DTO.SolicitudDTO;
import Daos.ClienteDAO;
import Daos.DoctorDAO;
import Daos.EstudioDAO;
import Daos.Interfaces.IClienteDAO;
import Daos.Interfaces.IDoctorDAO;
import Daos.Interfaces.IEstudioDAO;
import Daos.Interfaces.ISolicitudDAO;
import Daos.SolicitudDAO;
import Entidades.SolicitudEntidad;
import Exception.NegocioException;
import Negocio.Interfaces.ISolicitudNegocio;
import Validador.Validador;
import exception.PersistenciaException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class SolicitudNegocio implements ISolicitudNegocio {

    private final ISolicitudDAO solicitudDAO;
    private IDoctorDAO doctorDAO;
    private IClienteDAO clienteDAO;
    private IEstudioDAO estudioDAO;
    Validador val = new Validador();

    public SolicitudNegocio() {
        ConexionBD conexion = new ConexionBD();
        this.solicitudDAO = new SolicitudDAO(conexion);
        this.clienteDAO = new ClienteDAO(conexion);
        this.doctorDAO = new DoctorDAO(conexion);
        this.estudioDAO = new EstudioDAO(conexion);
    }

    @Override
    public SolicitudDTO registrar(CrearSolicitudDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("No se recibieron los datos de la solicitud.");
        }
        validar(dto.getFolio(), dto.getFechaHoraGeneracion(), dto.getIdCliente(), dto.getIdEstudio());
        try {
            SolicitudEntidad entidad = DTOAEntidad(dto);
            return EntidadADTO(solicitudDAO.guardar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar la solicitud. Revisa la conexion con la base de datos.", e);
        }
    }

    @Override
    public SolicitudDTO actualizar(SolicitudDTO dto) throws NegocioException {
        if (dto == null || dto.getId() == null) {
            throw new NegocioException("Falta el id de la solicitud a actualizar.");
        }
        validar(dto.getFolio(), dto.getFechaHoraGeneracion(), dto.getIdCliente(), dto.getIdEstudio());
        try {
            SolicitudEntidad buscada = solicitudDAO.buscarPorId(dto.getId());
            if (buscada == null) {
                throw new NegocioException("La solicitud que intentas actualizar no existe.");
            }
            if (buscada.getResultados().size() != 0) {
                throw new NegocioException("No se puede modificar una solicitud con resultados registrados.");
            }
            SolicitudEntidad entidad = DTOAEntidad(dto);
            entidad.setId(dto.getId());
            return EntidadADTO(solicitudDAO.actualizar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar la solicitud por: " + e.getMessage(), e);
        }
    }

    @Override
    public void cancelarSolicitud(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id de la solicitud.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            SolicitudEntidad entidad = solicitudDAO.buscarPorId(id);
            if (entidad.getResultados().size() != 0) {
                throw new NegocioException("No se puede cancelar una solicitud con resultados registrados.");
            }
            entidad.setCancelada(true);
            solicitudDAO.actualizar(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo cancelar la solicitud.", e);
        }
    }

    @Override
    public SolicitudDTO buscarPorId(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id de la solicitud.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            SolicitudEntidad entidad = solicitudDAO.buscarPorId(id);
            if (entidad == null) {
                throw new NegocioException("No se encontro la solicitud.");
            }
            return EntidadADTO(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo consultar la solicitud.", e);
        }
    }

    @Override
    public List<SolicitudDTO> obtenerTodos() throws NegocioException {
        try {
            List<SolicitudDTO> lista = new ArrayList<>();
            for (SolicitudEntidad e : solicitudDAO.obtenerTodos()) {
                lista.add(EntidadADTO(e));
            }
            return lista;
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo obtener la lista de solicitudes.", e);
        }
    }

    @Override
    public List<SolicitudDTO> obtenerTodosActivos() throws NegocioException {
        try {
            List<SolicitudDTO> lista = new ArrayList<>();
            for (SolicitudEntidad e : solicitudDAO.obtenerTodos()) {
                if (e.getCancelada() == false) {
                    lista.add(EntidadADTO(e));
                }
            }
            return lista;
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo obtener la lista de solicitudes activas.", e);
        }
    }

    @Override
    public List<SolicitudDTO> obtenerTodosPendientes() throws NegocioException {
        try {
            List<SolicitudDTO> lista = new ArrayList<>();
            for (SolicitudEntidad e : solicitudDAO.obtenerTodos()) {
                if (e.getResultados().isEmpty() && e.getCancelada() == false) {
                    lista.add(EntidadADTO(e));
                }
            }
            return lista;
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo obtener la lista de solicitudes pendientes de resultados.", e);
        }
    }

    private void validar(String folio, String fechaHoraGeneracion, Integer idCliente, Integer idEstudio) throws NegocioException {
        if (!val.validarTexto(folio)) {
            throw new NegocioException("El folio es invalido.");
        }
        if (!val.validarFormatoFechaHora(fechaHoraGeneracion)) {
            throw new NegocioException("La fecha y hora son es invalidas.");
        }
        if (!(idCliente > 0)) {
            throw new NegocioException("El id del cliente es invalido.");
        }
        if (!(idEstudio > 0)) {
            throw new NegocioException("El id estudio es invalido.");
        }
    }

    private SolicitudDTO EntidadADTO(SolicitudEntidad e) {
        if(e.getDoctor() == null){
        return new SolicitudDTO(
                e.getId(),
                e.getFolio(),
                e.getFechaHoraGeneracion().toString(),
                e.getCancelada(),
                e.getCliente().getId(),
                null,
                e.getEstudio().getId());
        }
        return new SolicitudDTO(
                e.getId(),
                e.getFolio(),
                e.getFechaHoraGeneracion().toString(),
                e.getCancelada(),
                e.getCliente().getId(),
                e.getDoctor().getId(),
                e.getEstudio().getId());
    }

    private SolicitudEntidad DTOAEntidad(SolicitudDTO dto) throws PersistenciaException {
        if(dto.getIdDoctor() == null){
            SolicitudEntidad creado = new SolicitudEntidad(
                    dto.getFolio(),
                    Timestamp.valueOf(dto.getFechaHoraGeneracion()),
                    dto.getCancelada(),
                    clienteDAO.buscarPorId(dto.getIdCliente()),
                    null,
                    estudioDAO.buscarPorId(dto.getIdEstudio()));
            creado.setId(dto.getId());
            return creado;
        }else{
            SolicitudEntidad creado = new SolicitudEntidad(
                    dto.getFolio(),
                    Timestamp.valueOf(dto.getFechaHoraGeneracion()),
                    dto.getCancelada(),
                    clienteDAO.buscarPorId(dto.getIdCliente()),
                    doctorDAO.buscarPorId(dto.getIdDoctor()),
                    estudioDAO.buscarPorId(dto.getIdEstudio()));
            creado.setId(dto.getId());
            return creado;
        }   
    }

    private SolicitudEntidad DTOAEntidad(CrearSolicitudDTO dto) throws PersistenciaException {
        if(dto.getIdDoctor() == null){
            return new SolicitudEntidad(
                    dto.getFolio(),
                    Timestamp.valueOf(dto.getFechaHoraGeneracion()),
                    dto.getCancelada(),
                    clienteDAO.buscarPorId(dto.getIdCliente()),
                    null,
                    estudioDAO.buscarPorId(dto.getIdEstudio()));
        }
        
        return new SolicitudEntidad(
                dto.getFolio(),
                Timestamp.valueOf(dto.getFechaHoraGeneracion()),
                dto.getCancelada(),
                clienteDAO.buscarPorId(dto.getIdCliente()),
                doctorDAO.buscarPorId(dto.getIdDoctor()),
                estudioDAO.buscarPorId(dto.getIdEstudio()));
    }

}
