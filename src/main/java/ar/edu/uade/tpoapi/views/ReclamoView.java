package ar.edu.uade.tpoapi.views;

import java.util.List;

import ar.edu.uade.tpoapi.modelo.Enumerations.Estado;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReclamoView {

    private int numero;
    private PersonaView usuario;
    private EdificioView edificio;
    private String ubicacion;
    private String descripcion;
    private UnidadView unidad;
    private Estado estado;
    private List<ImagenView> imagenes;
    private List<ComentarioView> comentarios;
    
}
