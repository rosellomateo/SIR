package ar.edu.uade.tpoapi.controlador.request.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPasswordDTO {
    
    @NotBlank
    String documento;
    @NotBlank
    String password;
}
