package ar.edu.uade.tpoapi.controlador.request.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMailDTO {

    @NotBlank(message = "El documento no puede estar vacío")
    String documento;
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    String mail;
}
