package ar.edu.uade.tpoapi.views;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadView {

    private int id;
    private String piso;
    private String numero;
    private boolean habitado;
    private EdificioView edificio;
    private ArrayList<PersonaView> duenio;
    private ArrayList<PersonaView> inquilino;

    public String toString() {
        return piso + " " + numero;
    }
}
