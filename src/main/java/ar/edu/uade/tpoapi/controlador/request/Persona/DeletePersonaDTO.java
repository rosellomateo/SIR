package ar.edu.uade.tpoapi.controlador.request.Persona;


import java.util.Set;

import ar.edu.uade.tpoapi.modelo.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletePersonaDTO {
    
    private String documento;
}
