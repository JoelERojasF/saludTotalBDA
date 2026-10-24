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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author le0jx
 */
@Entity
@Table(name = "tblDoctores") 
public class DoctorEntidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "apellidoPaterno", nullable = true)
    private String apellidoPaterno;
    
    @Column(name = "apellidoMaterno", nullable = true)
    private String apellidoMaterno;
    
    @Column(name = "sexo", nullable = false)
    private String sexo;
    
    @OneToMany(mappedBy = "doctor")
    private List<SolicitudEntidad> solicitudes;

    public DoctorEntidad() {
    }

    public DoctorEntidad(String nombre, String apellidoPaterno, String apellidoMaterno, String sexo) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.sexo = sexo;
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

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public List<SolicitudEntidad> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicituds(List<SolicitudEntidad> solicitudes) {
        this.solicitudes = solicitudes;
    }
    
    @Override
    public String toString() {
        return "DoctorEntidad{" + "id=" + id + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", sexo=" + sexo + '}';
    }
    
    
}
