/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.saludtotalpersistencia;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import Daos.ClienteDAO;
import Daos.DoctorDAO;
import Entidades.ClienteEntidad;
import Entidades.DoctorEntidad;
import Entidades.UsuarioEntidad;
import exception.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.sql.Date;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author le0jx
 */
public class SaludTotalPersistencia {

    public static void main(String[] args) throws SQLException, PersistenciaException {
        System.out.println("Hello World!");

        IConexionBD conexion = new ConexionBD();
        ClienteDAO cDAO = new ClienteDAO(conexion);
        DoctorDAO dDao = new DoctorDAO(conexion);
        
        ClienteEntidad c1 = new ClienteEntidad("Daniela", "López", "Valenzuela", new Date(1998, 4, 12), "Femenino", "O+");
        ClienteEntidad c2 = new ClienteEntidad("Carlos Alberto", "Méndez", "Ibarra", new Date(1987, 3, 11), "Masculino", "A+");
        
        DoctorEntidad d1 = new DoctorEntidad("Arturo", "Zamora", "Fuentes", "Masculino");
        DoctorEntidad d2 = new DoctorEntidad("Alejandra", "Paredes", "León", "Femenino");
        
        dDao.guardar(d1);
        dDao.guardar(d2);
        
        System.out.println(dDao.obtenerTodos());
        
        
        
        
        
//        EntityManager em = conexion.crearConexion();
//        em.getTransaction().begin();
//
//        UsuarioEntidad usuario = new UsuarioEntidad("admin", BCrypt.hashpw("123", BCrypt.gensalt()));
//        em.persist(usuario);
//        em.getTransaction().commit();
    }
}
