package ar.edu.uade.tpoapi.controlador.request.Unidad;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransferirUnidadDTO {
    @Min(value = 1, message = "El identificador debe ser mayor que cero")
    private int identificador;
    @NotBlank(message = "El documento no puede estar vacío")
    private String documento;
}