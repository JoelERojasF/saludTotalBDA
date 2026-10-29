/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author le0jx
 */
public class ParametroDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer ordenAparicion;
    private String unidadMedida;
    private Boolean numerico;
    private Integer idEstudio;

    private List<RangoDTO> rangos = new ArrayList<>();

    public ParametroDTO(Integer id, String nombre, String descripcion, Integer ordenAparicion, String unidadMedida, Boolean numerico, Integer idEstudio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ordenAparicion = ordenAparicion;
        this.unidadMedida = unidadMedida;
        this.numerico = numerico;
        this.idEstudio = idEstudio;
    }

    public List<RangoDTO> getRangos() {
        return rangos;
    }

    public void setRangos(List<RangoDTO> rangos) {
        this.rangos = rangos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ParametroDTO{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", ordenAparicion=" + ordenAparicion + ", unidadMedida=" + unidadMedida + ", numerico=" + numerico + ", idEstudio=" + idEstudio + '}';
    }

}
