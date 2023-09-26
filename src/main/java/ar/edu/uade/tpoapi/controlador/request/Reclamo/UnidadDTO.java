package ar.edu.uade.tpoapi.controlador.request.Reclamo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadDTO {

    int codigo;
    String piso;
    String numero;
}