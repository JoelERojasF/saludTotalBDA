/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author le0jx
 */
public class ParametroDTO {
    
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer ordenAparicion;
    private String valorInicial;
    private String valorFinal;
    private Boolean numerico;
    private Integer idEstudio;

    public ParametroDTO(Integer id, String nombre, String descripcion, Integer ordenAparicion, String valorInicial, String valorFinal, Boolean numerico, Integer idEstudio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ordenAparicion = ordenAparicion;
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
        this.numerico = numerico;
        this.idEstudio = idEstudio;
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

    public String getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(String valorInicial) {
        this.valorInicial = valorInicial;
    }

    public String getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(String valorFinal) {
        this.valorFinal = valorFinal;
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
        return "ParametroDTO{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", ordenAparicion=" + ordenAparicion + ", valorInicial=" + valorInicial + ", valorFinal=" + valorFinal + ", numerico=" + numerico + ", idEstudio=" + idEstudio + '}';
    }
    
    
}
