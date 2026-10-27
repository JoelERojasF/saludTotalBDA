/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author le0jx
 */
@Entity
@Table(name = "tblRangos") 
public class RangoEntidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "unidadMedida", nullable = false)
    private String unidadMedida;
    
    @Column(name = "inicio", nullable = false)
    private Integer inicio;
    
    @Column(name = "fin", nullable = false)
    private Integer fin;
    
    @Column(name = "edadInicio", nullable = false)
    private Integer edadInicio;
    
    @Column(name = "edadFin", nullable = false)
    private Integer edadFin;
    
    @ManyToOne()
    @JoinColumn(name = "idParametro", nullable = false)
    private ParametroEntidad parametro;

    public RangoEntidad() {
    }

    public RangoEntidad(String unidadMedida, Integer inicio, Integer fin, Integer edadInicio, Integer edadFin, ParametroEntidad parametro) {
        this.unidadMedida = unidadMedida;
        this.inicio = inicio;
        this.fin = fin;
        this.edadInicio = edadInicio;
        this.edadFin = edadFin;
        this.parametro = parametro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public ParametroEntidad getParametro() {
        return parametro;
    }

    public void setParametro(ParametroEntidad parametro) {
        this.parametro = parametro;
    }

    @Override
    public String toString() {
        return "RangoEntidad{" + "id=" + id + ", unidadMedida=" + unidadMedida + ", inicio=" + inicio + ", fin=" + fin + ", edadInicio=" + edadInicio + ", edadFin=" + edadFin + '}';
    }
    
}
