/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Daos;

import Conexion.IConexionBD;
import Daos.Interfaces.IEstudioDAO;
import Entidades.EstudioEntidad;
import Entidades.ParametroEntidad;
import Entidades.RangoEntidad;
import Entidades.TipoMuestraEntidad;
import exception.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author le0jx
 */
public class EstudioDAO implements IEstudioDAO {

    private final IConexionBD conexion;

    public EstudioDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public EstudioEntidad guardar(EstudioEntidad estudio) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            em.persist(estudio);
            em.getTransaction().commit();
            return estudio;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo guardar el estudio en la base de datos.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public EstudioEntidad guardarCompleto(EstudioEntidad estudio, List<ParametroEntidad> parametros) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();

            estudio.setParametros(new ArrayList<>());
            em.persist(estudio);
            for (ParametroEntidad parametro : parametros) {
                parametro.setEstudio(estudio);
                estudio.getParametros().add(parametro);
                if (parametro.getRangos() != null) {
                    for (RangoEntidad rango : parametro.getRangos()) {
                        rango.setParametro(parametro);
                    }
                }
                em.persist(parametro);
                if (parametro.getRangos() != null) {
                    for (RangoEntidad rango : parametro.getRangos()) {
                        em.persist(rango);
                    }
                }
            }
            em.getTransaction().commit();
            return estudio;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo guardar el estudio con sus parametros en la base de datos.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public EstudioEntidad actualizar(EstudioEntidad estudio) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            EstudioEntidad actualizado = em.merge(estudio);
            em.getTransaction().commit();
            return actualizado;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo actualizar el estudio.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public void eliminar(Integer id) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            EstudioEntidad estudio = em.find(EstudioEntidad.class, id);
            if (estudio != null) {
                em.remove(estudio);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo eliminar el estudio.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public EstudioEntidad buscarPorId(Integer id) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.find(EstudioEntidad.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar el estudio.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public List<EstudioEntidad> obtenerTodos() throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.createQuery(
                    "SELECT DISTINCT e FROM EstudioEntidad e "
                    + "LEFT JOIN FETCH e.parametros "
                    + "JOIN FETCH e.tipoMuestra", EstudioEntidad.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar la lista de estudios.", e);
        } finally {
            cerrar(em);
        }
    }

    private void revertir(EntityManager em) {
        if (em != null && em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    private void cerrar(EntityManager em) {
        if (em != null) {
            em.close();
        }
    }


    @Override
    public EstudioEntidad buscarCompletoPorId(Integer id) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            EstudioEntidad estudio = em.createQuery(
                    "SELECT DISTINCT e FROM EstudioEntidad e "
                    + "JOIN FETCH e.tipoMuestra "
                    + "LEFT JOIN FETCH e.parametros "
                    + "WHERE e.id = :id", EstudioEntidad.class)
                    .setParameter("id", id)
                    .getResultStream().findFirst().orElse(null);
            if (estudio == null) {
                return null;
            }
            em.createQuery(
                    "SELECT DISTINCT p FROM ParametroEntidad p "
                    + "LEFT JOIN FETCH p.rangos "
                    + "WHERE p.estudio.id = :id", ParametroEntidad.class)
                    .setParameter("id", id)
                    .getResultList();
            return estudio;
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar el estudio completo.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public EstudioEntidad actualizarCompleto(EstudioEntidad estudio, List<ParametroEntidad> parametros) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();

            EstudioEntidad estudioentidad = em.find(EstudioEntidad.class, estudio.getId());
            if (estudioentidad == null) {
                throw new PersistenciaException("El estudio que intentas actualizar no existe.");
            }
            estudioentidad.setNombre(estudio.getNombre());
            estudioentidad.setDescripcion(estudio.getDescripcion());
            estudioentidad.setTipoMuestra(em.find(TipoMuestraEntidad.class, estudio.getTipoMuestra().getId()));

            Set<Integer> id = new HashSet<>();
            for (ParametroEntidad p : parametros) {
                if (p.getId() != null) {
                    id.add(p.getId());
                }
            }

            List<ParametroEntidad> actuales = em.createQuery(
                    "SELECT p FROM ParametroEntidad p WHERE p.estudio.id = :id", ParametroEntidad.class)
                    .setParameter("id", estudioentidad.getId())
                    .getResultList();
            for (ParametroEntidad actual : actuales) {
                if (id.contains(actual.getId())) {
                    continue;
                }
                Long usos = em.createQuery(
                        "SELECT COUNT(r) FROM ResultadoEntidad r WHERE r.parametro.id = :id", Long.class)
                        .setParameter("id", actual.getId())
                        .getSingleResult();
                if (usos > 0) {
                    throw new PersistenciaException("El parametro '" + actual.getNombre()
                            + "' ya tiene resultados registrados y no se puede eliminar.");
                }
                em.createQuery("DELETE FROM RangoEntidad r WHERE r.parametro.id = :id")
                        .setParameter("id", actual.getId())
                        .executeUpdate();
                em.remove(actual);
            }

            for (ParametroEntidad p : parametros) {
                ParametroEntidad destino;
                if (p.getId() == null) {
                    destino = new ParametroEntidad();
                    destino.setEstudio(estudioentidad);
                } else {
                    destino = em.find(ParametroEntidad.class, p.getId());
                    if (destino == null || !destino.getEstudio().getId().equals(estudioentidad.getId())) {
                        throw new PersistenciaException("Un parametro no pertenece a este estudio.");
                    }
                    em.createQuery("DELETE FROM RangoEntidad r WHERE r.parametro.id = :id")
                            .setParameter("id", destino.getId())
                            .executeUpdate();
                }
                destino.setNombre(p.getNombre());
                destino.setDescripcion(p.getDescripcion());
                destino.setOrdenAparicion(p.getOrdenAparicion());
                destino.setUnidadMedida(p.getUnidadMedida());
                destino.setNumerico(p.getNumerico());
                if (p.getId() == null) {
                    em.persist(destino);
                }
                if (p.getRangos() != null) {
                    for (RangoEntidad r : p.getRangos()) {
                        em.persist(new RangoEntidad(r.getInicio(), r.getFin(),
                                r.getEdadInicio(), r.getEdadFin(), destino));
                    }
                }
            }

            em.getTransaction().commit();
        } catch (PersistenciaException e) {
            revertir(em);
            throw e;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo actualizar el estudio con sus parametros.", e);
        } finally {
            cerrar(em);
        }
        return buscarCompletoPorId(estudio.getId());
    }
}
