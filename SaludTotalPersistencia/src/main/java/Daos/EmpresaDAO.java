/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Daos;

import Conexion.IConexionBD;
import Daos.Interfaces.IEmpresaDAO;
import Entidades.EmpresaEntidad;
import exception.PersistenciaException;
import jakarta.persistence.EntityManager;

/**
 *
 * @author le0jx
 */
public class EmpresaDAO implements IEmpresaDAO{
    
    private final IConexionBD conexion;

    public EmpresaDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }
    
    @Override
    public EmpresaEntidad guardar(EmpresaEntidad empresa) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            em.persist(empresa);
            em.getTransaction().commit();
            return empresa;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo guardar la empresa en la base de datos.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public EmpresaEntidad buscarPorId(Integer id) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.find(EmpresaEntidad.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar la empresa.", e);
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
