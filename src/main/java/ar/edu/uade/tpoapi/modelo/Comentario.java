package ar.edu.uade.tpoapi.modelo;

import java.util.Date;

import ar.edu.uade.tpoapi.views.ComentarioView;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String urlImagen;    

    public ComentarioView toView()
    {
        return ComentarioView.builder()
            .idcomentario(this.idcomentario)
            .texto(this.texto)
            .fecha(this.fecha.toString())
            .persona(this.usuario.toView())
            .urlImagen(this.urlImagen)
            .build();
    }
}
