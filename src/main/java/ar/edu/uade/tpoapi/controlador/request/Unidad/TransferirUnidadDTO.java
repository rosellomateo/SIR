package ar.edu.uade.tpoapi.controlador.request.Unidad;



import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransferirUnidadDTO {
    @NotBlank
    private int identificador;
    @NotBlank
    private String documento;
}