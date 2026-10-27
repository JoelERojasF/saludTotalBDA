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
public class EstudioDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer idTipoMuestra;
    private String nombreTipoMuestra;
    private List<String> nombresParametros = new ArrayList<>();

    public EstudioDTO(Integer id, String nombre, String descripcion, Integer idTipoMuestra) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idTipoMuestra = idTipoMuestra;
    }

    public String getNombreTipoMuestra() {
        return nombreTipoMuestra;
    }

    public void setNombreTipoMuestra(String nombreTipoMuestra) {
        this.nombreTipoMuestra = nombreTipoMuestra;
    }

    public List<String> getNombresParametros() {
        return nombresParametros;
    }

    public void setNombresParametros(List<String> nombresParametros) {
        this.nombresParametros = nombresParametros;
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

    public Integer getIdTipoMuestra() {
        return idTipoMuestra;
    }

    public void setIdTipoMuestra(Integer idTipoMuestra) {
        this.idTipoMuestra = idTipoMuestra;
    }

    @Override
    public String toString() {
        return "EstudioDTO{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", idTipoMuestra=" + idTipoMuestra + '}';
    }

}
