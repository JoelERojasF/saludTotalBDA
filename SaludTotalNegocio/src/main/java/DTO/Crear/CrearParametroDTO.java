/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Crear;

/**
 *
 * @author le0jx
 */
public class CrearParametroDTO {
    
    private String nombre;
    private String descripcion;
    private Integer ordenAparicion;
    private Boolean numerico;
    private Integer idEstudio;

    public CrearParametroDTO(String nombre, String descripcion, Integer ordenAparicion, Boolean numerico, Integer idEstudio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ordenAparicion = ordenAparicion;
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

    @Override
    public String toString() {
        return "CrearParametroDTO{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", ordenAparicion=" + ordenAparicion + ", numerico=" + numerico + ", idEstudio=" + idEstudio + '}';
    }
}
