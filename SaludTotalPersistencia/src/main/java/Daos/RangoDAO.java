/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Daos;

import Conexion.IConexionBD;
import Daos.Interfaces.IRangoDAO;
import Entidades.RangoEntidad;
import exception.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class RangoDAO implements IRangoDAO{
    
    private final IConexionBD conexion;

    public RangoDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public RangoEntidad guardar(RangoEntidad rango) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            em.persist(rango);
            em.getTransaction().commit();
            return rango;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo guardar el rango en la base de datos.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public RangoEntidad actualizar(RangoEntidad rango) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            RangoEntidad actualizado = em.merge(rango);
            em.getTransaction().commit();
            return actualizado;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo actualizar el rango.", e);
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
            RangoEntidad rango = em.find(RangoEntidad.class, id);
            if (rango != null) {
                em.remove(rango);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo eliminar el rango.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public RangoEntidad buscarPorId(Integer id) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.find(RangoEntidad.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar el rango.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public List<RangoEntidad> obtenerTodos() throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.createQuery("SELECT r FROM RangoEntidad r", RangoEntidad.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar la lista de rangos.", e);
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
