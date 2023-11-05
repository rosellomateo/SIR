package ar.edu.uade.tpoapi.controlador.request.Reclamo;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReclamoDTO {
    @Min(value = 1, message = "El número de reclamo debe ser mayor a 0")
    private int codigo;
    @NotBlank(message = "El piso no puede estar vacío")
    private String piso;
    @NotBlank(message = "El número no puede estar vacío")
    private String numero; 
    @NotBlank(message = "El documento no puede estar vacío")
    private String documento; 
    @NotBlank(message = "La ubicacion no puede estar vacío")
    private String ubicacion; 
    @NotBlank(message = "El descripcion no puede estar vacío")
    private String descripcion;
}