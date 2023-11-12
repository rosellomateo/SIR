package ar.edu.uade.tpoapi.controlador.request.Persona;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonaDTO {
    
    @NotBlank(message = "El documento no puede estar vacío")
    private String documento;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "El Rol no puede estar vacío")
    private String rol;
}
