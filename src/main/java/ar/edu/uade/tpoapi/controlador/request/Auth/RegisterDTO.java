package ar.edu.uade.tpoapi.controlador.request.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    
    @NotBlank
    String documento;
    @NotBlank
    @Email
    String mail;
    @NotBlank
    String password;
}
