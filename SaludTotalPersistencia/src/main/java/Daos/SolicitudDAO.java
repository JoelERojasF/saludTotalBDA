/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Daos;

import Conexion.IConexionBD;
import Daos.Interfaces.ISolicitudDAO;
import Entidades.SolicitudEntidad;
import exception.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class SolicitudDAO implements ISolicitudDAO{
    
    private final IConexionBD conexion;

    public SolicitudDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public SolicitudEntidad guardar(SolicitudEntidad solicitud) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            em.persist(solicitud);
            em.getTransaction().commit();
            return solicitud;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo guardar la solicitud en la base de datos.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public SolicitudEntidad actualizar(SolicitudEntidad solicitud) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            SolicitudEntidad actualizado = em.merge(solicitud);
            em.getTransaction().commit();
            return actualizado;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo actualizar la solicitud.", e);
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
            SolicitudEntidad solicitud = em.find(SolicitudEntidad.class, id);
            if (solicitud != null) {
                em.remove(solicitud);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo eliminar la solicitud.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public SolicitudEntidad buscarPorId(Integer id) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.find(SolicitudEntidad.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar la solicitud.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public List<SolicitudEntidad> obtenerTodos() throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.createQuery("SELECT s FROM SolicitudEntidad s", SolicitudEntidad.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar la lista de solicitudes.", e);
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
