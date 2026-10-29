/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Conexion.ConexionBD;
import DTO.Crear.CrearTipoMuestraDTO;
import DTO.TipoMuestraDTO;
import Daos.Interfaces.ITipoMuestraDAO;
import Daos.TipoMuestraDAO;
import Entidades.TipoMuestraEntidad;
import Exception.NegocioException;
import Negocio.Interfaces.ITipoMuestraNegocio;
import Validador.Validador;
import exception.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class TipoMuestraNegocio implements ITipoMuestraNegocio{

    private final ITipoMuestraDAO tipoMuestraDAO;
    Validador val = new Validador();

    public TipoMuestraNegocio() {
        this.tipoMuestraDAO = new TipoMuestraDAO(new ConexionBD());
    }
    
    @Override
    public TipoMuestraDTO registrar(CrearTipoMuestraDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("No se recibieron los datos del tipo de muestra.");
        }
        validar(dto.getNombre());

        TipoMuestraEntidad entidad = DTOAEntidad(dto);
        try {
            return EntidadADTO(tipoMuestraDAO.guardar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar el tipo de muestra. Revisa la conexion con la base de datos.", e);
        }
    }

    @Override
    public TipoMuestraDTO actualizar(TipoMuestraDTO dto) throws NegocioException {
        if (dto == null || dto.getId() == null) {
            throw new NegocioException("Falta el id del tipo de muestra a actualizar.");
        }
        validar(dto.getNombre());

        try {
            if (tipoMuestraDAO.buscarPorId(dto.getId()) == null) {
                throw new NegocioException("El tipo de muestra que intentas actualizar no existe.");
            }
            TipoMuestraEntidad entidad = DTOAEntidad(dto);
            entidad.setId(dto.getId());
            return EntidadADTO(tipoMuestraDAO.actualizar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar el tipo de muestra por: "+ e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del tipo de muestra a eliminar.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            if (tipoMuestraDAO.buscarPorId(id) == null) {
                throw new NegocioException("El tipo de muestra que intentas eliminar no existe.");
            }
            tipoMuestraDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo eliminar el tipo de muestra por: "+ e.getMessage(), e);
        }
    }

    @Override
    public TipoMuestraDTO buscarPorId(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del tipo de muestra.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            TipoMuestraEntidad entidad = tipoMuestraDAO.buscarPorId(id);
            if (entidad == null) {
                throw new NegocioException("No se encontro el tipo de muestra.");
            }
            return EntidadADTO(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo consultar el tipo de muestra por: "+ e.getMessage(), e);
        }    }

    @Override
    public List<TipoMuestraDTO> obtenerTodos() throws NegocioException {
        try {
            List<TipoMuestraDTO> lista = new ArrayList<>();
            for (TipoMuestraEntidad e : tipoMuestraDAO.obtenerTodos()) {
                lista.add(EntidadADTO(e));
            }
            return lista;
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo obtener la lista de tipos de muestra por: "+ e.getMessage(), e);
        }
    }
    
    private void validar(String nombre) throws NegocioException {
        if (!val.validarTexto(nombre)) {
            throw new NegocioException("El nombre es invalido.");
        }
    }

    private TipoMuestraDTO EntidadADTO(TipoMuestraEntidad e) {
        return new TipoMuestraDTO(
                e.getId(),
                e.getNombre());
    }
    
    private TipoMuestraEntidad DTOAEntidad(TipoMuestraDTO dto){
        TipoMuestraEntidad creado = new TipoMuestraEntidad(
                    dto.getNombre().trim());
        creado.setId(dto.getId());
        return creado;
    }
    
    private TipoMuestraEntidad DTOAEntidad(CrearTipoMuestraDTO dto){
        return new TipoMuestraEntidad(
                    dto.getNombre().trim()
                    );
    }
    
}
