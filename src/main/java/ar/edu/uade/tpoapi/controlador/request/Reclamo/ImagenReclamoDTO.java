package ar.edu.uade.tpoapi.controlador.request.Reclamo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ImagenReclamoDTO {
    private int numero;
    private String direccion;
    private String tipo;
    
}