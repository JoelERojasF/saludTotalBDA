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
@Table(name = "tblTiposMuestras") 
public class TipoMuestraEntidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @OneToMany(mappedBy = "tipoMuestra")
    private List<EstudioEntidad> estudios;

    public TipoMuestraEntidad() {
    }

    public TipoMuestraEntidad(String nombre) {
        this.nombre = nombre;
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

    public List<EstudioEntidad> getEstudios() {
        return estudios;
    }

    public void setEstudios(List<EstudioEntidad> estudios) {
        this.estudios = estudios;
    }

    @Override
    public String toString() {
        return "TipoMuestraEntidad{" + "id=" + id + ", nombre=" + nombre + '}';
    }
    
    
}
