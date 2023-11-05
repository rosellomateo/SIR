package ar.edu.uade.tpoapi.controlador.request.Persona;


import java.util.Set;

import ar.edu.uade.tpoapi.modelo.Enumerations.Rol;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePersonaDTO {

    @NotBlank(message = "El documento no puede estar vacío")
    private String documento;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    private Rol roles;
}
