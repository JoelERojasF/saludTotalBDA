/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Crear;

/**
 *
 * @author le0jx
 */
public class CrearResultadoDTO {
    
    private String resultado;
    private String observacion;
    private Integer idSolicitud;
    private Integer idParametro;

    public CrearResultadoDTO(String resultado, String observacion, Integer idSolicitud, Integer idParametro) {
        this.resultado = resultado;
        this.observacion = observacion;
        this.idSolicitud = idSolicitud;
        this.idParametro = idParametro;
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

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }
    
    
}
