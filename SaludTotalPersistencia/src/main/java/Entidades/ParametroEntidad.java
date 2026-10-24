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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author le0jx
 */
@Entity
@Table(name = "tblParametros")
public class ParametroEntidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    
    @Column(name = "ordenAparicion", nullable = false)
    private Integer ordenAparicion;
    
    @Column(name = "valorInicial", nullable = true)
    private Integer valorInicial;
    
    @Column(name = "valorFinal", nullable = true)
    private Integer valorFinal;
    
    @Column(name = "numerico", nullable = false)
    private Boolean numerico;
    
    @ManyToOne()
    @JoinColumn(name = "idEstudio", nullable = false)
    private EstudioEntidad estudio;
    
    @OneToMany(mappedBy = "parametro")
    private List<RangoEntidad> rangos;
    
    @OneToMany(mappedBy = "parametro")
    private List<ResultadoEntidad> resultados;

    public ParametroEntidad() {
    }

    public ParametroEntidad(String nombre, String descripcion, Integer ordenAparicion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ordenAparicion = ordenAparicion;
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

    public Integer getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(Integer valorInicial) {
        this.valorInicial = valorInicial;
    }

    public Integer getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Integer valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Boolean getNumerico() {
        return numerico;
    }

    public void setNumerico(Boolean numerico) {
        this.numerico = numerico;
    }

    public EstudioEntidad getEstudio() {
        return estudio;
    }

    public void setEstudio(EstudioEntidad estudio) {
        this.estudio = estudio;
    }

    public List<RangoEntidad> getRangos() {
        return rangos;
    }

    public void setRangos(List<RangoEntidad> rangos) {
        this.rangos = rangos;
    }

    public List<ResultadoEntidad> getResultados() {
        return resultados;
    }

    public void setResultados(List<ResultadoEntidad> resultados) {
        this.resultados = resultados;
    }

    @Override
    public String toString() {
        return "ParametroEntidad{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", ordenAparicion=" + ordenAparicion + ", valorInicial=" + valorInicial + ", valorFinal=" + valorFinal + ", numerico=" + numerico + '}';
    }
    
    
}
