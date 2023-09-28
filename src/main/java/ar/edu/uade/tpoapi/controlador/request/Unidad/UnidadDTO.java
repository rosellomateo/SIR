package ar.edu.uade.tpoapi.controlador.request.Unidad;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class UnidadDTO {
    
    private int codigo;
    private String piso;
    private String numero;
    private String documento;
}