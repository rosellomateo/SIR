package ar.edu.uade.tpoapi.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.edu.uade.tpoapi.views.EdificioView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "edificios")
public class Edificio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private int codigo;
    private String nombre;
    private String direccion;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigoedificio")
    private List<Unidad> unidades;
    
    public void updateEdificio(EdificioView eView){
        this.nombre = eView.getNombre();
        this.direccion = eView.getDireccion();
    }

    public void agregarUnidad(Unidad unidad) {
        unidades.add(unidad);
    }

    public Set<Persona> habilitados(){
        Set<Persona> habilitados = new HashSet<Persona>();
        for(Unidad unidad : unidades) {
            List<Persona> duenios = unidad.getDuenios();
            for(Persona p : duenios)
                habilitados.add(p);
            List<Persona> inquilinos = unidad.getInquilinos();
            for(Persona p : inquilinos)
                habilitados.add(p);
        }
        return habilitados;
    }

    

    public Set<Persona> duenios() {
        Set<Persona> resultado = new HashSet<Persona>();
        for(Unidad unidad : unidades) {
            List<Persona> duenios = unidad.getDuenios();
            resultado.addAll(duenios); // Agregar todos los elementos de duenios a resultado
        }
        return resultado;
    }

    public Set<Persona> habitantes() {
        Set<Persona> resultado = new HashSet<Persona>();
        for(Unidad unidad : unidades) {
            if(unidad.estaHabitado()) {
                List<Persona> inquilinos = unidad.getInquilinos();
                if (!inquilinos.isEmpty()) {
                    resultado.addAll(inquilinos); // Agregar todos los inquilinos a resultado
                } else {
                    List<Persona> duenios = unidad.getDuenios();
                    resultado.addAll(duenios); // Agregar todos los dueños a resultado
                }
            }
        }
        return resultado;
    }
    
    public EdificioView toView() {
        return new EdificioView(codigo, nombre, direccion);
    }

    public String toString() {
        return codigo + ";" + nombre + ";" + direccion;
    }
}
