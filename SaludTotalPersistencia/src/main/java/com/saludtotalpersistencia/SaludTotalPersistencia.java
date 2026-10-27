/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.saludtotalpersistencia;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import Daos.ClienteDAO;
import Daos.DoctorDAO;
import Daos.EstudioDAO;
import Daos.ParametroDAO;
import Daos.TipoMuestraDAO;
import Entidades.ClienteEntidad;
import Entidades.DoctorEntidad;
import Entidades.EstudioEntidad;
import Entidades.ParametroEntidad;
import Entidades.TipoMuestraEntidad;
import exception.PersistenciaException;
import java.sql.Date;
import java.sql.SQLException;

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

        //Estudio , muestras 
        EstudioDAO eDAO = new EstudioDAO(conexion);
        TipoMuestraDAO tDAO = new TipoMuestraDAO(conexion);
        TipoMuestraEntidad t1 = new TipoMuestraEntidad("sangre");
        EstudioEntidad e1 = new EstudioEntidad("estudio", "descripcion", t1);
        tDAO.guardar(t1);
        eDAO.guardar(e1);
 
        dDao.guardar(d1);
        dDao.guardar(d2);

        cDAO.guardar(c1);
        cDAO.guardar(c2);

        System.out.println(dDao.obtenerTodos());

//        EntityManager em = conexion.crearConexion();
//        em.getTransaction().begin();
//
//        UsuarioEntidad usuario = new UsuarioEntidad("admin", BCrypt.hashpw("123", BCrypt.gensalt()));
//        em.persist(usuario);
//        em.getTransaction().commit();
    }
}
