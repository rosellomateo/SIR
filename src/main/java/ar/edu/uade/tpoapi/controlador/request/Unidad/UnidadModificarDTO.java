package ar.edu.uade.tpoapi.controlador.request.Unidad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadModificarDTO {
    
    private int identificador;
    private int codigo;
    private String piso;
    private String numero;
}
