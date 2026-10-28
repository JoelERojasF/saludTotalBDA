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
    
    @Column(name = "inicio", nullable = false)
    private Double inicio;
    
    @Column(name = "fin", nullable = false)
    private Double fin;
    
    @Column(name = "edadInicio", nullable = false)
    private Integer edadInicio;
    
    @Column(name = "edadFin", nullable = false)
    private Integer edadFin;
    
    @ManyToOne()
    @JoinColumn(name = "idParametro", nullable = false)
    private ParametroEntidad parametro;

    public RangoEntidad() {
    }

    public RangoEntidad(Double inicio, Double fin, Integer edadInicio, Integer edadFin, ParametroEntidad parametro) {
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

    public ParametroEntidad getParametro() {
        return parametro;
    }

    public void setParametro(ParametroEntidad parametro) {
        this.parametro = parametro;
    }

    @Override
    public String toString() {
        return "RangoEntidad{" + "id=" + id +  ", inicio=" + inicio + ", fin=" + fin + ", edadInicio=" + edadInicio + ", edadFin=" + edadFin + '}';
    }
    
}
