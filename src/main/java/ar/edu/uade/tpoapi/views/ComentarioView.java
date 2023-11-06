package ar.edu.uade.tpoapi.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComentarioView {

    private int idcomentario;
    private String texto;
    private String fecha;
    private PersonaView persona; 
    private String urlImagen;
    
}
