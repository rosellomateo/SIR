package ar.edu.uade.tpoapi.controlador.request.Edificio;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaEdificioDTO {
    @Min(value = 1, message = "El código debe ser mayor que cero")
    private int codigo;
}
