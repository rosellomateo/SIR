package ar.edu.uade.tpoapi.controlador.request.Reclamo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComentarReclamoDTO {

    @Min(value = 1, message = "El número del reclamo debe ser mayor a 0")
    private int numero;
    @NotBlank(message = "El texto del comentario no puede estar vacío")
    private String texto;
    @NotBlank(message = "La url de la imagen no puede estar vacía")
    private String urlImagen;
    @NotBlank(message = "El documento del usuario no puede estar vacío")
    private String documento;
}
