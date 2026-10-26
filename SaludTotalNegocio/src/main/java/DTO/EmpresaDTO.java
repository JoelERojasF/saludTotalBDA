/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author le0jx
 */
public class EmpresaDTO {
    
    private Integer id;
    private String nombre;
    private String direcion;
    private String correo;
    private String telefono;
    private String rfc;
    private String cedula;

    public EmpresaDTO(Integer id, String nombre, String direcion, String correo, String telefono, String rfc, String cedula) {
        this.id = id;
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
        return "EmpresaDTO{" + "id=" + id + ", nombre=" + nombre + ", direcion=" + direcion + ", correo=" + correo + ", telefono=" + telefono + ", rfc=" + rfc + ", cedula=" + cedula + '}';
    }
    
    
}
