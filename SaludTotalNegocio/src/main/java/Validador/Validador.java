/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;

/**
 *
 * @author le0jx
 */
public class Validador {
    public boolean validarNombrePersona(String texto){
        if(texto == null || texto.isEmpty()) return false;
        
        String patron = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]{1,50}$";
        return texto.trim().matches(patron);
    }
    
    public boolean validarTexto(String texto){
        if(texto == null || texto.isEmpty()) return false;
        
        String patron = "^[A-Za-zÁÉÍÓÚáéíóúÜüÑñ0-9 .,/#-]+$";
        return texto.trim().matches(patron);
    }
    
    public boolean validarSexo(String texto){
        if(texto == null || texto.isEmpty()) return false;
        
        if(texto.equalsIgnoreCase("Masculino") || texto.equalsIgnoreCase("Femenino")) return true;
        return false;
    }
    
    public boolean validarTipoSangre(String texto){
        if(texto == null || texto.isEmpty()) return false;
        
        if(texto.equalsIgnoreCase("A+") || texto.equalsIgnoreCase("A-")|| texto.equalsIgnoreCase("B+")|| texto.equalsIgnoreCase("B-")|| texto.equalsIgnoreCase("AB+")|| texto.equalsIgnoreCase("AB-")|| texto.equalsIgnoreCase("O+")|| texto.equalsIgnoreCase("O-")) return true;
        return false;
    }
    
    public boolean validarNumeroEntero(String texto){
        if(texto == null || texto.isEmpty()) return false;
        
        String patron = "^[1-9][0-9]*$";
        return texto.trim().matches(patron);
    }
    
    public boolean validarNumeroDecimal(String texto){
        if(texto == null || texto.isEmpty()) return false;
        
        String patron = "^[0-9]+(\\.[0-9]+)?$";
        return texto.trim().matches(patron);
    }
    
    public boolean validarTelefono(String texto){
        if(texto == null || texto.isEmpty()) return false;
        
        String patron = "^[0-9]{10}$";
        return texto.trim().matches(patron);
    }
    
    public boolean validarCorreo(String texto){
        if(texto == null || texto.isEmpty()) return false;
        
        String patron = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return texto.trim().matches(patron);
    }
    
    public boolean validarFormatoFecha(String fecha){
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);

            LocalDate.parse(fecha, formato);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean validarFormatoFechaHora(String fecha){
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);

            LocalDateTime.parse(fecha, formato);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String vacioANulo(String texto) {
        return (texto == null || texto.isBlank()) ? null : texto.trim();
    }
    
}
