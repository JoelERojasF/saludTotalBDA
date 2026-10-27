/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Crear;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class CrearEstudioDTO {
    
    private String nombre;
    private String descripcion;
    private Integer idTipoMuestra;

    private List<CrearParametroDTO> parametros = new ArrayList<>();

    public CrearEstudioDTO(String nombre, String descripcion, Integer idTipoMuestra) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idTipoMuestra = idTipoMuestra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdTipoMuestra() {
        return idTipoMuestra;
    }

    public void setIdTipoMuestra(Integer idTipoMuestra) {
        this.idTipoMuestra = idTipoMuestra;
    }

    public List<CrearParametroDTO> getParametros() {
        return parametros;
    }

    public void setParametros(List<CrearParametroDTO> parametros) {
        this.parametros = parametros;
    }

    @Override
    public String toString() {
        return "CrearEstudioDTO{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", idTipoMuestra=" + idTipoMuestra + ", parametros=" + parametros + '}';
    }
    
    
}