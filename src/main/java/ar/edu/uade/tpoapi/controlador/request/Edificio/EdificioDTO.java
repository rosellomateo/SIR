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
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    
} 
