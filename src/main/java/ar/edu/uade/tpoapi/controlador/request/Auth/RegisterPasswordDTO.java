package ar.edu.uade.tpoapi.controlador.request.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPasswordDTO {
    
    @NotBlank(message = "El documento no puede estar vacío")
    String documento;
    @NotBlank(message = "La contraseña no puede estar vacía")
    String password;
}
