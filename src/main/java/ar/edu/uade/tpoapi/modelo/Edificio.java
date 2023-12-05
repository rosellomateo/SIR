package ar.edu.uade.tpoapi.modelo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.edu.uade.tpoapi.views.EdificioView;
import ar.edu.uade.tpoapi.views.PersonaView;
import ar.edu.uade.tpoapi.views.UnidadView;
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

    public Set<PersonaView> habilitados(){
        Set<PersonaView> habilitados = new HashSet<PersonaView>();
        for(Unidad unidad : unidades) {
            List<Persona> duenios = unidad.getDuenios();
            for(Persona p : duenios)
                habilitados.add(p.toView());
            List<Persona> inquilinos = unidad.getInquilinos();
            for(Persona p : inquilinos)
                habilitados.add(p.toView());
        }
        return habilitados;
    }

    public Set<PersonaView> duenios() {
        Set<PersonaView> resultado = new HashSet<PersonaView>();
        for(Unidad unidad : unidades) {
            List<Persona> duenios = unidad.getDuenios();
            for(Persona p : duenios)
                resultado.add(p.toView());
        }
        return resultado;
    }

    public Set<PersonaView> habitantes() {
        Set<PersonaView> resultado = new HashSet<PersonaView>();
        for(Unidad unidad : unidades) {
            if(unidad.estaHabitado()) {
                List<Persona> inquilinos = unidad.getInquilinos();
                if (!inquilinos.isEmpty()) {
                    for(Persona p : inquilinos)
                        resultado.add(p.toView());
                } else {
                    List<Persona> duenios = unidad.getDuenios();
                    for(Persona p : duenios)
                        resultado.add(p.toView());
                }
            }
        }
        return resultado;
    }

    public Set<UnidadView> unidades() {
        Set<UnidadView> resultado = new HashSet<UnidadView>();
        for(Unidad unidad : unidades) {
            resultado.add(unidad.toView());
        }
        return resultado;
    }

    public EdificioView toView() {
        return new EdificioView(codigo, nombre, direccion);
    }

    public String toString() {
        return codigo + ";" + nombre + ";" + direccion;
    }

    public Set<PersonaView> inquilinos() {
        Set<PersonaView> resultado = new HashSet<PersonaView>();
        for(Unidad unidad : unidades) {
            List<Persona> duenios = unidad.getInquilinos();
            for(Persona p : duenios)
                resultado.add(p.toView());
        }
        return resultado;
    }
}
