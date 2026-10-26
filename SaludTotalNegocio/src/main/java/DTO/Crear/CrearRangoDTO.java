/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Crear;

/**
 *
 * @author le0jx
 */
public class CrearRangoDTO {
    
    private String unidadMedida;
    private Integer inicio;
    private Integer fin;
    private Integer edadInicio;
    private Integer edadFin;
    private Integer idParametro;

    public CrearRangoDTO(String unidadMedida, Integer inicio, Integer fin, Integer edadInicio, Integer edadFin, Integer idParametro) {
        this.unidadMedida = unidadMedida;
        this.inicio = inicio;
        this.fin = fin;
        this.edadInicio = edadInicio;
        this.edadFin = edadFin;
        this.idParametro = idParametro;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Integer getInicio() {
        return inicio;
    }

    public void setInicio(Integer inicio) {
        this.inicio = inicio;
    }

    public Integer getFin() {
        return fin;
    }

    public void setFin(Integer fin) {
        this.fin = fin;
    }

    public Integer getEdadInicio() {
        return edadInicio;
    }

    public void setEdadInicio(Integer edadInicio) {
        this.edadInicio = edadInicio;
    }

    public Integer getEdadFin() {
        return edadFin;
    }

    public void setEdadFin(Integer edadFin) {
        this.edadFin = edadFin;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    @Override
    public String toString() {
        return "CrearRangoDTO{" + "unidadMedida=" + unidadMedida + ", inicio=" + inicio + ", fin=" + fin + ", edadInicio=" + edadInicio + ", edadFin=" + edadFin + ", idParametro=" + idParametro + '}';
    }
    
    
}
