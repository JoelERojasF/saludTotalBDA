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
@Table(name = "tblResultados") 
public class ResultadoEntidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "resultado", nullable = false)
    private String resultado;
    
    @Column(name = "observacion", nullable = false)
    private String observacion;
    
    @ManyToOne()
    @JoinColumn(name = "idSolicitud", nullable = false)
    private SolicitudEntidad solicitud;

    @ManyToOne()
    @JoinColumn(name = "idParametro", nullable = false)
    private ParametroEntidad parametro;

    public ResultadoEntidad() {
    }

    public ResultadoEntidad(String resultado, String observacion, SolicitudEntidad solicitud, ParametroEntidad parametro) {
        this.resultado = resultado;
        this.observacion = observacion;
        this.solicitud = solicitud;
        this.parametro = parametro;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SolicitudEntidad getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudEntidad solicitud) {
        this.solicitud = solicitud;
    }

    public ParametroEntidad getParametro() {
        return parametro;
    }

    public void setParametro(ParametroEntidad parametro) {
        this.parametro = parametro;
    }
    
    

    @Override
    public String toString() {
        return "ResultadoEntidad{" + "id=" + id + ", resultado=" + resultado + ", observacion=" + observacion + '}';
    }
    
}
