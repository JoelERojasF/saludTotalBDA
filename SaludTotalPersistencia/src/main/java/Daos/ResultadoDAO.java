/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Daos;

import Conexion.IConexionBD;
import Daos.Interfaces.IResultadoDAO;
import Entidades.ResultadoEntidad;
import exception.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class ResultadoDAO implements IResultadoDAO{
    
    private final IConexionBD conexion;

    public ResultadoDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public ResultadoEntidad guardar(ResultadoEntidad resultado) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            em.persist(resultado);
            em.getTransaction().commit();
            return resultado;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo guardar el resultado en la base de datos.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public ResultadoEntidad actualizar(ResultadoEntidad resultado) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            ResultadoEntidad actualizado = em.merge(resultado);
            em.getTransaction().commit();
            return actualizado;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo actualizar el resultado.", e);
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
            ResultadoEntidad resultado = em.find(ResultadoEntidad.class, id);
            if (resultado != null) {
                em.remove(resultado);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo eliminar el resultado.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public ResultadoEntidad buscarPorId(Integer id) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.find(ResultadoEntidad.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar el resultado.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public List<ResultadoEntidad> obtenerTodos() throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.createQuery("SELECT r FROM ResultadoEntidad r", ResultadoEntidad.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar la lista de resultados.", e);
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
