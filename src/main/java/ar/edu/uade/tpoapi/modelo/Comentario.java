package ar.edu.uade.tpoapi.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.uade.tpoapi.views.ComentarioView;
import ar.edu.uade.tpoapi.views.ImagenView;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcomentario;
    private String texto;
    private Date fecha;
    @ManyToOne
    @JoinColumn(name = "documento")
    private Persona usuario; 
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "idreclamo")
    private List<Imagen> imagenes; 
    @OneToMany(mappedBy = "comentarioPadre", cascade = CascadeType.ALL)
    private List<Comentario> respuestas;
    @ManyToOne
    @JoinColumn(name = "comentario_padre_id")
    private Comentario comentarioPadre;

    public void agregarRespuesta(Comentario respuesta) {
        respuesta.setComentarioPadre(this);
        respuestas.add(respuesta);
    }

    public void agregarImagen(Imagen imagen) {
        imagenes.add(imagen);
    }

    public ComentarioView toView()
    {
        List<ImagenView> imagenesView = new ArrayList<ImagenView>();
        for (Imagen imagen : this.imagenes) {
            imagenesView.add(imagen.toView());
        }
        List<ComentarioView> respuestasView = respuestas.stream()
                .map(Comentario::toView)
                .collect(Collectors.toList());

        return ComentarioView.builder()
            .idcomentario(this.idcomentario)
            .texto(this.texto)
            .fecha(this.fecha.toString())
            .persona(this.usuario.toView())
            .imagenes(imagenesView)
            .respuestas(respuestasView)
            .build();
    }
}
