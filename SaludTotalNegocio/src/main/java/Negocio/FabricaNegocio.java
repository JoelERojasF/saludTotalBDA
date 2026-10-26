/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import Daos.ClienteDAO;
import Daos.DoctorDAO;
import Negocio.Interfaces.IClienteNegocio;
import Negocio.Interfaces.IDoctorNegocio;

/**
 *
 * @author Pirown
 */
public class FabricaNegocio {

    private static final IConexionBD CONEXION = new ConexionBD();

    private FabricaNegocio() {
    }

    public static IClienteNegocio crearClienteNegocio() {
        return new ClienteNegocio(new ClienteDAO(CONEXION));
    }

    public static IDoctorNegocio crearDoctorNegocio() {
        return new DoctorNegocio(new DoctorDAO(CONEXION));
    }
    
}
