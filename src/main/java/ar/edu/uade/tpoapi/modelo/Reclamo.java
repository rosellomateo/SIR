package ar.edu.uade.tpoapi.modelo;

import java.util.ArrayList;
import java.util.List;

import ar.edu.uade.tpoapi.modelo.Enumerations.Estado;
import ar.edu.uade.tpoapi.views.ComentarioView;
import ar.edu.uade.tpoapi.views.ImagenView;
import ar.edu.uade.tpoapi.views.ReclamoView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reclamos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "idreclamo")
    private List<Imagen> imagenes;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "idreclamo")
    private List<Comentario> comentarios;

    public void agregarImagen(Imagen imagen) {
        imagenes.add(imagen);
    }

    public String toString() {
        return "Reclamo: " + this.numero + "  - " + this.ubicacion + " - " + this.descripcion + " - " + this.estado;
    }

    public ReclamoView toView(){
        List<ImagenView> imagenesView = new ArrayList<ImagenView>();
        for (Imagen imagen : this.imagenes) {
            imagenesView.add(imagen.toView());
        }
        List<ComentarioView> comentariosView = new ArrayList<ComentarioView>();
        for (Comentario comentario : this.comentarios) {
            comentariosView.add(comentario.toView());
        }
        return ReclamoView.builder()
            .numero(this.numero)
            .usuario(this.usuario.toView())
            .edificio(this.edificio.toView())
            .ubicacion(this.ubicacion)
            .descripcion(this.descripcion)
            .unidad(this.unidad.toView())
            .estado(this.estado)
            .imagenes(imagenesView)
            .comentarios(comentariosView)
            .build();
    }

    public void comentar(String texto, Persona persona, String urlImagen) {
    }
}
