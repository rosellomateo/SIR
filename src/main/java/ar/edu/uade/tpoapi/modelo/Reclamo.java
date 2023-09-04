package ar.edu.uade.tpoapi.modelo;

import java.util.ArrayList;
import java.util.List;

import ar.edu.uade.tpoapi.views.Estado;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "reclamos")
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreclamo")
    private int numero;
    @ManyToOne
    @JoinColumn(name = "documento")
    private Persona usuario;
    @OneToOne
    @JoinColumn(name = "codigo")
    private Edificio edificio;
    private String ubicacion;
    private String descripcion;
    @OneToOne
    @JoinColumn(name = "identificador")
    private Unidad unidad;
    private Estado estado;
    @OneToMany
    @JoinTable(name = "imagenes", joinColumns = @JoinColumn(name = "numero"))
    private List<Imagen> imagenes;

    public Reclamo(Persona usuario, Edificio edificio, String ubicacion, String descripcion, Unidad unidad) {
        this.usuario = usuario;
        this.edificio = edificio;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.estado = Estado.nuevo;
        imagenes = new ArrayList<Imagen>();
    }

    public Reclamo() {
    }

    public void agregarImagen(String direccion, String tipo) {
        Imagen imagen = new Imagen(direccion, tipo);
        imagenes.add(imagen);
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Persona getUsuario() {
        return usuario;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public Estado getEstado() {
        return estado;
    }

    public List<Imagen> getImagenes(){
        return this.imagenes;
    }

    public void cambiarEstado(Estado estado) {
        this.estado = estado;
    }

}
