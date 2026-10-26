/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Daos;

import Conexion.IConexionBD;
import Daos.Interfaces.ITipoMuestraDAO;
import Entidades.TipoMuestraEntidad;
import exception.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class TipoMuestraDAO implements ITipoMuestraDAO{
    
    private final IConexionBD conexion;

    public TipoMuestraDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }
    
    @Override
    public TipoMuestraEntidad guardar(TipoMuestraEntidad tipoMuestra) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            em.persist(tipoMuestra);
            em.getTransaction().commit();
            return tipoMuestra;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo guardar el tipo de muestra en la base de datos.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public TipoMuestraEntidad actualizar(TipoMuestraEntidad tipoMuestra) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            TipoMuestraEntidad actualizado = em.merge(tipoMuestra);
            em.getTransaction().commit();
            return actualizado;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo actualizar el tipo de muestra.", e);
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
            TipoMuestraEntidad tipoMuestra = em.find(TipoMuestraEntidad.class, id);
            if (tipoMuestra != null) {
                em.remove(tipoMuestra);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo eliminar el tipo de muestra.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public TipoMuestraEntidad buscarPorId(Integer id) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.find(TipoMuestraEntidad.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar el tipo de muestra.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public List<TipoMuestraEntidad> obtenerTodos() throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.createQuery("SELECT t FROM TipoMuestraEntidad t", TipoMuestraEntidad.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar la lista de tipos de muestras.", e);
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
