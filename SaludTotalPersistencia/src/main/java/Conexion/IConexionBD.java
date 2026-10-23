/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Conexion;

import jakarta.persistence.EntityManager;
import java.sql.SQLException;

/**
 *
 * @author le0jx
 */
public interface IConexionBD {
    EntityManager crearConexion() throws SQLException;
}
