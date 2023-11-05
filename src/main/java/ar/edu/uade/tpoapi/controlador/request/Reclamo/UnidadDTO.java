
package ar.edu.uade.tpoapi.controlador.request.Reclamo;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadDTO {
    @Min(value = 1, message = "El código debe ser mayor que cero")
    int codigo;
    @NotBlank(message = "El numero de piso tiene que ser mayor a cero")
    String piso;
    @NotBlank(message = "El número no puede estar vacío")
    String numero;
}