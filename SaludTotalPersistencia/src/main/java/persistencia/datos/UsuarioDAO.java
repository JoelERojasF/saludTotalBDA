package persistencia.datos;

import Conexion.IConexionBD;
import Entidades.UsuarioEntidad;
import jakarta.persistence.EntityManager;


public class UsuarioDAO implements IUsuarioDAO{
    
    private final IConexionBD conexion;

    public UsuarioDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public UsuarioEntidad guardar(UsuarioEntidad usuario) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            return usuario;
        } catch (Exception e) {
            revertir(em);
            throw new PersistenciaException("No se pudo guardar el usuario en la base de datos.", e);
        } finally {
            cerrar(em);
        }
    }

    @Override
    public UsuarioEntidad buscarPorNombre(String nombre) throws PersistenciaException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            return em.createQuery("SELECT u FROM UsuarioEntidad u WHERE u.nombre = :nombre", UsuarioEntidad.class)
                    .setParameter("nombre", nombre)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar el usuario.", e);
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
