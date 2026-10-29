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
public class CrearParametroDTO {
    
    private String nombre;
    private String descripcion;
    private Integer ordenAparicion;
    private String unidadMedida;
    private Boolean numerico;
    private Integer idEstudio;

    private List<CrearRangoDTO> rangos = new ArrayList<>();

    public CrearParametroDTO(String nombre, String descripcion, Integer ordenAparicion, String unidadMedida, Boolean numerico, Integer idEstudio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ordenAparicion = ordenAparicion;
        this.unidadMedida = unidadMedida;
        this.numerico = numerico;
        this.idEstudio = idEstudio;
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

    public Integer getOrdenAparicion() {
        return ordenAparicion;
    }

    public void setOrdenAparicion(Integer ordenAparicion) {
        this.ordenAparicion = ordenAparicion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Boolean getNumerico() {
        return numerico;
    }

    public void setNumerico(Boolean numerico) {
        this.numerico = numerico;
    }

    public Integer getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(Integer idEstudio) {
        this.idEstudio = idEstudio;
    }

    public List<CrearRangoDTO> getRangos() {
        return rangos;
    }

    public void setRangos(List<CrearRangoDTO> rangos) {
        this.rangos = rangos;
    }

    @Override
    public String toString() {
        return "CrearParametroDTO{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", ordenAparicion=" + ordenAparicion + ", unidadMedida=" + unidadMedida + ", numerico=" + numerico + '}';
    }
    
}