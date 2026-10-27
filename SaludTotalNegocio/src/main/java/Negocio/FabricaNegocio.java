/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import Negocio.Interfaces.IClienteNegocio;
import Negocio.Interfaces.IDoctorNegocio;
import Negocio.Interfaces.IEstudioNegocio;
import Negocio.Interfaces.ITipoMuestraNegocio;

/**
 *
 * @author Pirown
 */
public class FabricaNegocio {

    private static final IConexionBD CONEXION = new ConexionBD();

    private FabricaNegocio() {
    }

    public static IClienteNegocio crearClienteNegocio() {
        return new ClienteNegocio();
    }

    public static IDoctorNegocio crearDoctorNegocio() {
        return new DoctorNegocio();
    }

    public static IEstudioNegocio crearEstudioNegocio() {
        return new EstudioNegocio();
    }

    public static ITipoMuestraNegocio crearTipoMuestraNegocio() {
        return new TipoMuestraNegocio();
    }
}