/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Timestamp;

/**
 *
 * @author le0jx
 */
public class SolicitudDTO {
    private Integer id;
    private String folio;
    private String fechaHoraGeneracion;
    private Boolean cancelada;
    private Integer idCliente;
    private Integer idDoctor;
    private Integer idEstudio;

    public SolicitudDTO(Integer id, String folio, String fechaHoraGeneracion, Boolean cancelada, Integer idCliente, Integer idDoctor, Integer idEstudio) {
        this.id = id;
        this.folio = folio;
        this.fechaHoraGeneracion = fechaHoraGeneracion;
        this.cancelada = cancelada;
        this.idCliente = idCliente;
        this.idDoctor = idDoctor;
        this.idEstudio = idEstudio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFechaHoraGeneracion() {
        return fechaHoraGeneracion;
    }

    public void setFechaHoraGeneracion(String fechaHoraGeneracion) {
        this.fechaHoraGeneracion = fechaHoraGeneracion;
    }

    public Boolean getCancelada() {
        return cancelada;
    }

    public void setCancelada(Boolean cancelada) {
        this.cancelada = cancelada;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(Integer idDoctor) {
        this.idDoctor = idDoctor;
    }

    public Integer getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(Integer idEstudio) {
        this.idEstudio = idEstudio;
    }
    
}
