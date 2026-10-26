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
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author le0jx
 */
@Entity
@Table(name = "tblEmpresas") 
public class EmpresaEntidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "direccion", nullable = false)
    private String direcion;
    
    @Column(name = "correo", nullable = true)
    private String correo;
    
    @Column(name = "telefono", nullable = true)
    private String telefono;
    
    @Column(name = "rfc", nullable = false)
    private String rfc;
    
    @Column(name = "cedula", nullable = false)
    private String cedula;

    public EmpresaEntidad() {
    }

    public EmpresaEntidad(String nombre, String direcion, String correo, String telefono, String rfc, String cedula) {
        this.nombre = nombre;
        this.direcion = direcion;
        this.correo = correo;
        this.telefono = telefono;
        this.rfc = rfc;
        this.cedula = cedula;
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

    public String getDirecion() {
        return direcion;
    }

    public void setDirecion(String direcion) {
        this.direcion = direcion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    @Override
    public String toString() {
        return "EmpresaEntidad{" + "id=" + id + ", nombre=" + nombre + ", direcion=" + direcion + ", correo=" + correo + ", telefono=" + telefono + ", rfc=" + rfc + ", cedula=" + cedula + '}';
    }
    
    
    
}
