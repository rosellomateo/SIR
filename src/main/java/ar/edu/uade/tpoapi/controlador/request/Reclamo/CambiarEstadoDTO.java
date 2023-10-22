package ar.edu.uade.tpoapi.controlador.request.Reclamo;

import ar.edu.uade.tpoapi.modelo.Enumerations.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambiarEstadoDTO {
    
    private int numero;
    private Estado estado;
}
