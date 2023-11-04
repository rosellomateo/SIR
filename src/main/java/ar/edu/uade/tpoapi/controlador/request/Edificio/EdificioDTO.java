package ar.edu.uade.tpoapi.controlador.request.Edificio;

import java.util.List;

import ar.edu.uade.tpoapi.modelo.Unidad;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EdificioDTO {
    
    @NotBlank
    private String nombre;
    @NotBlank
    private String direccion;

    
} 
