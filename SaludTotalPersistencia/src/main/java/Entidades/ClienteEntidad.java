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
import java.sql.Date;
import java.util.List;

/**
 *
 * @author le0jx
 */
@Entity
@Table(name = "tblClientes") 
public class ClienteEntidad implements Serializable {
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
    
    @Column(name = "fechaNacimiento", nullable = false)
    private Date fechaNacimiento;
    
    @Column(name = "sexo", nullable = false)
    private String sexo;
    
    @Column(name = "tipoSangre", nullable = false)
    private String tipoSangre;  
    
    @OneToMany(mappedBy = "cliente")
    private List<SolicitudEntidad> solicitudes;

    public ClienteEntidad() {
    }

    public ClienteEntidad(String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, String sexo, String tipoSangre) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.tipoSangre = tipoSangre;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public List<SolicitudEntidad> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudEntidad> solicitudes) {
        this.solicitudes = solicitudes;
    }

    @Override
    public String toString() {
        return "ClienteEntidad{" + "id=" + id + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", fechaNacimiento=" + fechaNacimiento + ", sexo=" + sexo + ", tipoSangre=" + tipoSangre + '}';
    }
    
    
}
