package ar.edu.uade.tpoapi.controlador.request.Unidad;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreateUnidadDTO {

    @Min(value = 1, message = "El código debe ser mayor que cero")
    private int codigo;
    @NotBlank(message = "El piso no puede estar vacío")
    private String piso;
    @NotBlank(message = "El número no puede estar vacío")
    private String numero;
    
}