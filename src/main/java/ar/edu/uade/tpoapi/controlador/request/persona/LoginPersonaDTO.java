package ar.edu.uade.tpoapi.controlador.request.persona;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginPersonaDTO {
    @NotBlank
    private String documento;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String Email;
}
