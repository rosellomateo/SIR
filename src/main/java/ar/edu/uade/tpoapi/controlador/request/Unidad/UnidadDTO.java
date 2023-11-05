package ar.edu.uade.tpoapi.controlador.request.Unidad;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class UnidadDTO {
    @Min(value = 1, message = "El código debe ser mayor que cero")
    private int codigo;
    @NotBlank(message = "El piso no puede estar vacío")
    private String piso;
    @NotBlank(message = "El número no puede estar vacío")
    private String numero;
    @NotBlank(message = "El documento no puede estar vacío")
    private String documento;
}