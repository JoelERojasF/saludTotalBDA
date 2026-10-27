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
import exception.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

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
}
