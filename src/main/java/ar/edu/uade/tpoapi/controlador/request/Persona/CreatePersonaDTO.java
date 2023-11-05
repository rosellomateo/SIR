package ar.edu.uade.tpoapi.controlador.request.Persona;


import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonaDTO {
    
    @NotBlank
    private String documento;
    @NotBlank
    private String nombre;
    @NotBlank
    private String rol;
}
