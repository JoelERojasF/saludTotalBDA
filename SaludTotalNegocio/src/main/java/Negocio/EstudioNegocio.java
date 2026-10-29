/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Conexion.ConexionBD;
import DTO.Crear.CrearEstudioDTO;
import DTO.Crear.CrearParametroDTO;
import DTO.Crear.CrearRangoDTO;
import DTO.EstudioDTO;
import Daos.EstudioDAO;
import Daos.Interfaces.IEstudioDAO;
import Daos.Interfaces.ITipoMuestraDAO;
import Daos.TipoMuestraDAO;
import Entidades.EstudioEntidad;
import Entidades.ParametroEntidad;
import Entidades.RangoEntidad;
import Entidades.TipoMuestraEntidad;
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
public class EstudioNegocio implements IEstudioNegocio {

    private final IEstudioDAO estudioDAO;
    private ITipoMuestraDAO tipoMuestraDAO;
    Validador val = new Validador();

    public EstudioNegocio() {
        ConexionBD conexion = new ConexionBD();
        this.estudioDAO = new EstudioDAO(conexion);
        this.tipoMuestraDAO = new TipoMuestraDAO(conexion);
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
    public EstudioDTO registrarCompleto(CrearEstudioDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("No se recibieron los datos del estudio.");
        }
        if (dto.getIdTipoMuestra() == null) {
            throw new NegocioException("Falta el tipo de muestra del estudio.");
        }
        validar(dto.getNombre(), dto.getDescripcion(), dto.getIdTipoMuestra());

        List<CrearParametroDTO> parametros = dto.getParametros();
        if (parametros == null || parametros.isEmpty()) {
            throw new NegocioException("El estudio debe tener al menos un parametro.");
        }
        validarParametros(parametros);

        try {
            TipoMuestraEntidad tipoMuestra = tipoMuestraDAO.buscarPorId(dto.getIdTipoMuestra());
            if (tipoMuestra == null) {
                throw new NegocioException("El tipo de muestra seleccionado no existe.");
            }

            EstudioEntidad estudio = new EstudioEntidad(
                    dto.getNombre().trim(),
                    dto.getDescripcion().trim(),
                    tipoMuestra);

            List<ParametroEntidad> entidadesParametro = new ArrayList<>();
            for (CrearParametroDTO p : parametros) {
                entidadesParametro.add(parametroAEntidad(p));
            }

            return EntidadADTO(estudioDAO.guardarCompleto(estudio, entidadesParametro));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar el estudio con sus parametros. No se guardo nada por: "+ e.getMessage(), e);
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
            throw new NegocioException("No se pudo actualizar el estudio por: "+ e.getMessage(), e);
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
            throw new NegocioException("No se pudo eliminar el estudio por: "+ e.getMessage(), e);
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
            throw new NegocioException("No se pudo consultar el estudio por: "+ e.getMessage(), e);
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
            throw new NegocioException("No se pudo obtener la lista de estudios por: "+ e.getMessage(), e);
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

    private void validarParametros(List<CrearParametroDTO> parametros) throws NegocioException {
        for (CrearParametroDTO p : parametros) {
            if (p == null) {
                throw new NegocioException("Hay un parametro sin datos.");
            }
            if (!val.validarTexto(p.getNombre())) {
                throw new NegocioException("El nombre del parametro es invalido.");
            }
            if (!val.validarTexto(p.getDescripcion())) {
                throw new NegocioException("La descripcion del parametro '" + p.getNombre() + "' es invalida.");
            }
            if (p.getOrdenAparicion() == null || p.getOrdenAparicion() <= 0) {
                throw new NegocioException("El orden de aparicion del parametro '" + p.getNombre() + "' es invalido.");
            }
            if (!val.validarTexto(p.getUnidadMedida())) {
                throw new NegocioException("La unidad de medida del parametro '" + p.getNombre() + "' es invalida.");
            }
            if (Boolean.TRUE.equals(p.getNumerico())) {
                if (p.getRangos() == null || p.getRangos().isEmpty()) {
                    throw new NegocioException("El parametro '" + p.getNombre() + "' necesita al menos un rango.");
                }
                for (CrearRangoDTO r : p.getRangos()) {
                    validarRango(p.getNombre(), r);
                }
            }
        }
    }

    private void validarRango(String parametro, CrearRangoDTO r) throws NegocioException {
        if (r == null) {
            throw new NegocioException("El parametro '" + parametro + "' tiene un rango sin datos.");
        }
        if (r.getInicio() == null || r.getFin() == null || r.getInicio() < 0 || r.getFin() < 0) {
            throw new NegocioException("Los valores del rango de '" + parametro + "' son invalidos.");
        }
        if (r.getInicio() > r.getFin()) {
            throw new NegocioException("El inicio del rango de '" + parametro + "' no puede ser mayor que el fin.");
        }
        if (r.getEdadInicio() == null || r.getEdadFin() == null || r.getEdadInicio() < 0 || r.getEdadFin() < 0) {
            throw new NegocioException("Las edades del rango de '" + parametro + "' son invalidas.");
        }
        if (r.getEdadInicio() > r.getEdadFin()) {
            throw new NegocioException("La edad de inicio del rango de '" + parametro + "' no puede ser mayor que la de fin.");
        }

    }

    /**
     * El estudio se asigna despues, dentro de la transaccion del DAO.
     */
    private ParametroEntidad parametroAEntidad(CrearParametroDTO p) {
        boolean numerico = Boolean.TRUE.equals(p.getNumerico());

        ParametroEntidad entidad = new ParametroEntidad(
                p.getNombre().trim(),
                p.getDescripcion().trim(),
                p.getOrdenAparicion(),
                p.getUnidadMedida(),
                numerico,
                null);

        List<RangoEntidad> rangos = new ArrayList<>();
        if (numerico) {
            for (CrearRangoDTO r : p.getRangos()) {
                rangos.add(new RangoEntidad(
                        r.getInicio(),
                        r.getFin(),
                        r.getEdadInicio(),
                        r.getEdadFin(),
                        null));
            }
        }
        entidad.setRangos(rangos);
        return entidad;
    }

    private EstudioDTO EntidadADTO(EstudioEntidad e) {
        EstudioDTO dto = new EstudioDTO(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getTipoMuestra().getId());
        dto.setNombreTipoMuestra(e.getTipoMuestra().getNombre());

        if (e.getParametros() != null) {
            e.getParametros().stream()
                    .sorted(java.util.Comparator.comparing(ParametroEntidad::getOrdenAparicion))
                    .forEach(p -> dto.getNombresParametros().add(p.getNombre()));
        }
        return dto;
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
