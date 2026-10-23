/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.sql.SQLException;

/**
 *
 * @author le0jx
 */
public class ConexionBD implements IConexionBD{

    @Override
    public EntityManager crearConexion() throws SQLException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SaludTotal");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager;    
    }
    
}
