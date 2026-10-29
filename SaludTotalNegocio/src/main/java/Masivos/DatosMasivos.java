/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Masivos;

import Conexion.ConexionBD;
import DTO.Crear.CrearClienteDTO;
import DTO.Crear.CrearDoctorDTO;
import DTO.Crear.CrearEmpresaDTO;
import DTO.Crear.CrearTipoMuestraDTO;
import DTO.Crear.CrearUsuarioDTO;
import Daos.ClienteDAO;
import Daos.DoctorDAO;
import Daos.EmpresaDAO;
import Daos.TipoMuestraDAO;
import Daos.UsuarioDAO;
import Exception.NegocioException;
import Negocio.ClienteNegocio;
import Negocio.DoctorNegocio;
import Negocio.EmpresaNegocio;
import Negocio.TipoMuestraNegocio;
import Negocio.UsuarioNegocio;

/**
 *
 * @author le0jx
 */
public class DatosMasivos {
    
    ClienteNegocio CNeg = new ClienteNegocio();
    DoctorNegocio DNeg = new DoctorNegocio();
    TipoMuestraNegocio TNeg = new TipoMuestraNegocio();
    UsuarioNegocio UNeg = new UsuarioNegocio();
    EmpresaNegocio Eneg = new EmpresaNegocio();
    
    
    

    public DatosMasivos() {
    }
    
    public void InsertsDatosMasivos() throws NegocioException{
        CrearUsuarioDTO UDTO = new CrearUsuarioDTO("admin", "123");
        CrearEmpresaDTO EDTO = new CrearEmpresaDTO("Salud Total", "Av. Bienestar 245, Col. Centro Médico, C.P. 83000, Hermosillo, Sonora, México.", "contacto@saludintegral.example", "6620001234", "SINO261027AB1", "SALUD-EMP-2026-001");
        
        CrearClienteDTO CDTO1 = new CrearClienteDTO("Daniela", "López", "Valenzuela", "2021-03-15", "Femenino", "O+");
        CrearClienteDTO CDTO2 = new CrearClienteDTO("Carlos Alberto", "Méndez", "Ibarra","2021-08-22", "Masculino", "A+");
        CrearClienteDTO CDTO3 = new CrearClienteDTO("Fernanda", "Ruiz", "Cota", "2016-02-10", "Femenino", "B+");
        CrearClienteDTO CDTO4 = new CrearClienteDTO("José Manuel", "Navarro", "Acosta", "2016-09-05", "Masculino", "AB-");
        CrearClienteDTO CDTO5 = new CrearClienteDTO("Andrea", "Torres", "Camacho", "2011-01-20", "Femenino", "O-");
        CrearClienteDTO CDTO6 = new CrearClienteDTO("Miguel Ángel", "Salazar", "Duarte", "2011-07-12", "Masculino", "A-");
        CrearClienteDTO CDTO7 = new CrearClienteDTO("Valeria", "Espinoza", "Romero", "2006-04-18", "Femenino", "B-");
        CrearClienteDTO CDTO8 = new CrearClienteDTO("Ricardo", "Beltrán", "Soto", "2006-10-01", "Masculino", "AB+");
        CrearClienteDTO CDTO9 = new CrearClienteDTO("Paola", "Cervantes", "Medina", "2001-03-08", "Femenino", "O+");
        CrearClienteDTO CDTO10 = new CrearClienteDTO("Luis Enrique", "González", "Armenta", "2001-09-19", "Masculino", "A+");
        
        CrearDoctorDTO DDTO1 = new CrearDoctorDTO("Arturo", "Zamora", "Fuentes", "Masculino");
        CrearDoctorDTO DDTO2 = new CrearDoctorDTO("Alejandra", "Paredes", "León", "Femenino");
        CrearDoctorDTO DDTO3 = new CrearDoctorDTO("Roberto", "Aguilar", "Noriega", "Masculino");
        CrearDoctorDTO DDTO4 = new CrearDoctorDTO("Mariana", "Ochoa", "Valdez", "Femenino");
        CrearDoctorDTO DDTO5 = new CrearDoctorDTO("Sergio", "Castro", "Vázquez", "Masculino");
        CrearDoctorDTO DDTO6 = new CrearDoctorDTO("Karla", "Delgado", "Montoya", "Femenino");
        CrearDoctorDTO DDTO7 = new CrearDoctorDTO("Fernando", "Herrera", "Escalante", "Masculino");
        CrearDoctorDTO DDTO8 = new CrearDoctorDTO("Patricia", "Ríos", "Mendoza", "Femenino");
        CrearDoctorDTO DDTO9 = new CrearDoctorDTO("Eduardo", "Luna", "Sandoval", "Masculino");
        CrearDoctorDTO DDTO10 = new CrearDoctorDTO("Sofía", "Jiménez", "Tapia", "Femenino");
        
        CrearTipoMuestraDTO TDTO1 = new CrearTipoMuestraDTO("Sangre");
        CrearTipoMuestraDTO TDTO2 = new CrearTipoMuestraDTO("Orina");
        CrearTipoMuestraDTO TDTO3 = new CrearTipoMuestraDTO("Heces fecales");
        CrearTipoMuestraDTO TDTO4 = new CrearTipoMuestraDTO("Saliva");
        CrearTipoMuestraDTO TDTO5 = new CrearTipoMuestraDTO("Esputo (flema)");
        CrearTipoMuestraDTO TDTO6 = new CrearTipoMuestraDTO("Hisopado nasal");
        CrearTipoMuestraDTO TDTO7 = new CrearTipoMuestraDTO("Hisopado faríngeo");
        CrearTipoMuestraDTO TDTO8 = new CrearTipoMuestraDTO("Biopsia de tejido");
        CrearTipoMuestraDTO TDTO9 = new CrearTipoMuestraDTO("Líquido cefalorraquídeo");
        CrearTipoMuestraDTO TDTO10 = new CrearTipoMuestraDTO("Semen");
        CrearTipoMuestraDTO TDTO11 = new CrearTipoMuestraDTO("Cabello");
        CrearTipoMuestraDTO TDTO12 = new CrearTipoMuestraDTO("Uñas");
        
        UNeg.registrarUsuario(UDTO);

        Eneg.registrar(EDTO);

        
        CNeg.registrar(CDTO1);
        CNeg.registrar(CDTO2);
        CNeg.registrar(CDTO3);
        CNeg.registrar(CDTO4);
        CNeg.registrar(CDTO5);
        CNeg.registrar(CDTO6);
        CNeg.registrar(CDTO7);
        CNeg.registrar(CDTO8);
        CNeg.registrar(CDTO9);
        CNeg.registrar(CDTO10);

        
        DNeg.registrar(DDTO1);
        DNeg.registrar(DDTO2);
        DNeg.registrar(DDTO3);
        DNeg.registrar(DDTO4);
        DNeg.registrar(DDTO5);
        DNeg.registrar(DDTO6);
        DNeg.registrar(DDTO7);
        DNeg.registrar(DDTO8);
        DNeg.registrar(DDTO9);
        DNeg.registrar(DDTO10);

        
        TNeg.registrar(TDTO1);
        TNeg.registrar(TDTO2);
        TNeg.registrar(TDTO3);
        TNeg.registrar(TDTO4);
        TNeg.registrar(TDTO5);
        TNeg.registrar(TDTO6);
        TNeg.registrar(TDTO7);
        TNeg.registrar(TDTO8);
        TNeg.registrar(TDTO9);
        TNeg.registrar(TDTO10);
        TNeg.registrar(TDTO11);
        TNeg.registrar(TDTO12);

    }
}
