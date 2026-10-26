/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Daos;

import Conexion.IConexionBD;
import Daos.Interfaces.IParametroDAO;
import Entidades.ParametroEntidad;
import exception.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class ParametroDAO implements IParametroDAO{
    
    private final IConexionBD conexion;

    public ParametroDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public ParametroEntidad guardar(ParametroEntidad parametro) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            em.persist(parametro);
            em.getTransaction().commit();
            return parametro;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo guardar el parametro en la base de datos.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public ParametroEntidad actualizar(ParametroEntidad parametro) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            ParametroEntidad actualizado = em.merge(parametro);
            em.getTransaction().commit();
            return actualizado;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo actualizar el parametro.", e);
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
            ParametroEntidad parametro = em.find(ParametroEntidad.class, id);
            if (parametro != null) {
                em.remove(parametro);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo eliminar el parametro.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public ParametroEntidad buscarPorId(Integer id) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.find(ParametroEntidad.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar el parametro.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public List<ParametroEntidad> obtenerTodos() throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.createQuery("SELECT p FROM ParametroEntidad p", ParametroEntidad.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar la lista de parametros.", e);
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
