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
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author le0jx
 */
@Entity
@Table(name = "tblSolicitudes") 
public class SolicitudEntidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "folio", nullable = false)
    private String folio;
    
    @Column(name = "fechaHoraGeneracion", nullable = false)
    private Timestamp fechaHoraGeneracion;
    
    @Column(name = "cancelada", nullable = true)
    private Boolean cancelada = false;
    
    @ManyToOne()
    @JoinColumn(name = "idCliente", nullable = false)
    private ClienteEntidad cliente;

    @ManyToOne()
    @JoinColumn(name = "idDoctor", nullable = false)
    private DoctorEntidad doctor;
    
    @ManyToOne()
    @JoinColumn(name = "idEstudio", nullable = false)
    private EstudioEntidad estudio;
    
    @OneToMany(mappedBy = "solicitud")
    private List<ResultadoEntidad> resultados;

    public SolicitudEntidad() {
    }

    public SolicitudEntidad(Timestamp fechaHoraGeneracion, ClienteEntidad cliente, DoctorEntidad doctor) {
        this.fechaHoraGeneracion = fechaHoraGeneracion;
        this.cliente = cliente;
        this.doctor = doctor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getFechaHoraGeneracion() {
        return fechaHoraGeneracion;
    }

    public void setFechaHoraGeneracion(Timestamp fechaHoraGeneracion) {
        this.fechaHoraGeneracion = fechaHoraGeneracion;
    }

    public Boolean getCancelada() {
        return cancelada;
    }

    public void setCancelada(Boolean cancelada) {
        this.cancelada = cancelada;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public ClienteEntidad getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntidad cliente) {
        this.cliente = cliente;
    }

    public DoctorEntidad getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntidad doctor) {
        this.doctor = doctor;
    }

    public List<ResultadoEntidad> getResultados() {
        return resultados;
    }

    public void setResultados(List<ResultadoEntidad> resultados) {
        this.resultados = resultados;
    }

    public EstudioEntidad getEstudio() {
        return estudio;
    }

    public void setEstudio(EstudioEntidad estudio) {
        this.estudio = estudio;
    }

    @Override
    public String toString() {
        return "SolicitudEntidad{" + "id=" + id + ", fechaHoraGeneracion=" + fechaHoraGeneracion + ", cancelada=" + cancelada + '}';
    }
    
}
