package Daos.Interfaces;

import Entidades.EstudioEntidad;
import Entidades.ParametroEntidad;
import exception.PersistenciaException;
import java.util.List;

/**
 *
 * @author le0jx
 */
public interface IEstudioDAO {

    EstudioEntidad guardar(EstudioEntidad estudio) throws PersistenciaException;

    EstudioEntidad guardarCompleto(EstudioEntidad estudio, List<ParametroEntidad> parametros) throws PersistenciaException;

    EstudioEntidad actualizar(EstudioEntidad estudio) throws PersistenciaException;

    void eliminar(Integer id) throws PersistenciaException;

    EstudioEntidad buscarPorId(Integer id) throws PersistenciaException;

    List<EstudioEntidad> obtenerTodos() throws PersistenciaException;

    EstudioEntidad buscarCompletoPorId(Integer id) throws PersistenciaException;

    EstudioEntidad actualizarCompleto(EstudioEntidad estudio, List<ParametroEntidad> parametros) throws PersistenciaException;

}
