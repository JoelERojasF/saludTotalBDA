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
@Table(name = "tblUsuarios") 
public class UsuarioEntidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "contraseña", nullable = false)
    private String contraseña;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public UsuarioEntidad() {
    }

    public UsuarioEntidad(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return "UsuarioEntidad{" + "id=" + id + ", nombre=" + nombre + ", contrase\u00f1a=" + contraseña + '}';
    }

    
    
}
