package ar.edu.uade.tpoapi.controlador.request.Persona;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletePersonaDTO {
    @NotBlank(message = "El documento no puede estar vacío")
    private String documento;
}
