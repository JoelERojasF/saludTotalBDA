/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.saludtotalpersistencia;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import Entidades.UsuarioEntidad;
import jakarta.persistence.EntityManager;
import java.sql.SQLException;

/**
 *
 * @author le0jx
 */
public class SaludTotalPersistencia {

    public static void main(String[] args) throws SQLException {
        System.out.println("Hello World!");
        
        IConexionBD conexion = new ConexionBD();
        EntityManager em = conexion.crearConexion();
        em.getTransaction().begin();
        UsuarioEntidad usuario = new UsuarioEntidad("luis", "123");
        em.persist(usuario);
        em.getTransaction().commit();
    }
}
