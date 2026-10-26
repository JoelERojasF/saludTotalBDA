package Daos;

import Daos.Interfaces.IClienteDAO;
import exception.PersistenciaException;
import Conexion.IConexionBD;
import Entidades.ClienteEntidad;
import jakarta.persistence.EntityManager;
import java.util.List;


public class ClienteDAO implements IClienteDAO {

    private final IConexionBD conexion;

    public ClienteDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public ClienteEntidad guardar(ClienteEntidad cliente) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return cliente;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo guardar el cliente en la base de datos.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public ClienteEntidad actualizar(ClienteEntidad cliente) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            ClienteEntidad actualizado = em.merge(cliente);
            em.getTransaction().commit();
            return actualizado;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo actualizar el cliente.", e);
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
            ClienteEntidad cliente = em.find(ClienteEntidad.class, id);
            if (cliente != null) {
                em.remove(cliente);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo eliminar el cliente.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public ClienteEntidad buscarPorId(Integer id) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.find(ClienteEntidad.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar el cliente.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public List<ClienteEntidad> obtenerTodos() throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.createQuery("SELECT c FROM ClienteEntidad c", ClienteEntidad.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar la lista de clientes.", e);
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
