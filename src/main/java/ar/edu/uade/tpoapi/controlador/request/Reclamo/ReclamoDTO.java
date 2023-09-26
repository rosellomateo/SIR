package ar.edu.uade.tpoapi.controlador.request.Reclamo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReclamoDTO {
    private int codigo;
    private String piso;
    private String numero; 
    private String documento; 
    private String ubicacion; 
    private String descripcion;
}