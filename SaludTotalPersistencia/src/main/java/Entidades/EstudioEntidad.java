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
import java.util.List;

/**
 *
 * @author le0jx
 */
@Entity
@Table(name = "tblEstudios") 
public class EstudioEntidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    
    @ManyToOne()
    @JoinColumn(name = "idTipoMuestra", nullable = false)
    private TipoMuestraEntidad tipoMuestra;
    
    @OneToMany(mappedBy = "estudio")
    private List<ParametroEntidad> parametros;

    public EstudioEntidad() {
    }

    public EstudioEntidad(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoMuestraEntidad getTipoMuestra() {
        return tipoMuestra;
    }

    public void setTipoMuestra(TipoMuestraEntidad tipoMuestra) {
        this.tipoMuestra = tipoMuestra;
    }

    public List<ParametroEntidad> getParametros() {
        return parametros;
    }

    public void setParametros(List<ParametroEntidad> parametros) {
        this.parametros = parametros;
    }

    @Override
    public String toString() {
        return "EstudioEntidad{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
    }
    
    
}
