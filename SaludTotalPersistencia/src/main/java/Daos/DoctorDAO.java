/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Daos;

import Conexion.IConexionBD;
import Daos.Interfaces.IDoctorDAO;
import Entidades.DoctorEntidad;
import exception.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class DoctorDAO implements IDoctorDAO{
    
    private final IConexionBD conexion;

    public DoctorDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public DoctorEntidad guardar(DoctorEntidad doctor) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            em.persist(doctor);
            em.getTransaction().commit();
            return doctor;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo guardar el doctor en la base de datos.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public DoctorEntidad actualizar(DoctorEntidad doctor) throws PersistenciaException {
EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            DoctorEntidad actualizado = em.merge(doctor);
            em.getTransaction().commit();
            return actualizado;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo actualizar el doctor.", e);
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
            DoctorEntidad doctor = em.find(DoctorEntidad.class, id);
            if (doctor != null) {
                em.remove(doctor);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo eliminar el doctor.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public DoctorEntidad buscarPorId(Integer id) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.find(DoctorEntidad.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar el doctor.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public List<DoctorEntidad> obtenerTodos() throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.createQuery("SELECT d FROM DoctorEntidad d", DoctorEntidad.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar la lista de doctores.", e);
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
