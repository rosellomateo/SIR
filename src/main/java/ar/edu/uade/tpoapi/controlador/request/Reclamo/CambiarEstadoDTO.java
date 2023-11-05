package ar.edu.uade.tpoapi.controlador.request.Reclamo;

import ar.edu.uade.tpoapi.modelo.Enumerations.Estado;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambiarEstadoDTO {
    @Min(value = 1, message = "El número de reclamo debe ser mayor a 0")
    private int numero;
    private Estado estado;
}
