package ar.edu.uade.tpoapi.controlador.request.Reclamo;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ImagenReclamoDTO {
    @Min(value = 1, message = "El número de reclamo debe ser mayor a 0")
    private int numero;
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;
    @NotBlank(message = "El tipo no puede estar vacío")
    private String tipo;
    
}