package ar.edu.uade.tpoapi.controlador.request.Reclamo;

import java.util.List;

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
    @Min(value = 0, message = "El número del reclamo debe ser mayor a 0")
    private int numeroPadre;
    @NotBlank(message = "El texto del comentario no puede estar vacío")
    private String texto;
    @NotBlank(message = "El documento del usuario no puede estar vacío")
    private String documento;
    private List<ImagenReclamoDTO> imagenes;
}
