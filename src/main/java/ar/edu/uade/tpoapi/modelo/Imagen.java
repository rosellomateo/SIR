package ar.edu.uade.tpoapi.modelo;

import ar.edu.uade.tpoapi.views.ImagenView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "imagenes")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numero;
    @Column(name = "path")
    private String direccion;
    private String tipo;

    public Imagen(String direccion, String tipo) {
        this.direccion = direccion;
        this.tipo = tipo;
    }

    public Imagen() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String toString(){
        return "Imagen: " + this.numero + " - " + this.direccion + " - " + this.tipo;
    }


    public ImagenView toView() {
        return new ImagenView(this.numero, this.direccion, this.tipo);
    }
}
