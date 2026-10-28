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
    
    
    private Double inicio;
    private Double fin;
    private Integer edadInicio;
    private Integer edadFin;
    private Integer idParametro;

    public CrearRangoDTO( Double inicio, Double fin, Integer edadInicio, Integer edadFin, Integer idParametro) {
        this.inicio = inicio;
        this.fin = fin;
        this.edadInicio = edadInicio;
        this.edadFin = edadFin;
        this.idParametro = idParametro;
    }

    public Double getInicio() {
        return inicio;
    }

    public void setInicio(Double inicio) {
        this.inicio = inicio;
    }

    public Double getFin() {
        return fin;
    }

    public void setFin(Double fin) {
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
        return "CrearRangoDTO{" + ", inicio=" + inicio + ", fin=" + fin + ", edadInicio=" + edadInicio + ", edadFin=" + edadFin + ", idParametro=" + idParametro + '}';
    }
    
    
}
