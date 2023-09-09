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

@Entity
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
    

    public Edificio(int codigo, String nombre, String direccion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        unidades = new ArrayList<Unidad>();
    }
    //constructor sin codigo. CONSULTAR
    public Edificio(String nombre ,String direccion){
        this.nombre = nombre;
        this.direccion = direccion;
        unidades = new ArrayList<Unidad>();
    }
    public Edificio() {
    }
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

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Unidad> getUnidades() {
        return unidades;
    }

    public Set<Persona> duenios() {
        Set<Persona> resultado = new HashSet<Persona>();
        for(Unidad unidad : unidades) {
            List<Persona> duenios = unidad.getDuenios();
            resultado.addAll(duenios); // Agregar todos los elementos de duenios a resultado
        }
        return resultado;
    }
    

    // public Set<Persona> duenios() {
    //     Set<Persona> resultado = new HashSet<Persona>();
    //     for(Unidad unidad : unidades) {
    //         List<Persona> duenios = unidad.getDuenios();
    //         for(Persona p : duenios)
    //             duenios.add(p);
    //     }
    //     return resultado;
    // }

    // public Set<Persona> habitantes() {
    //     Set<Persona> resultado = new HashSet<Persona>();
    //     for(Unidad unidad : unidades) {
    //         if(unidad.estaHabitado()) {
    //             List<Persona> inquilinos = unidad.getInquilinos();
    //             if(inquilinos.size() > 0)
    //                 for(Persona p : inquilinos)
    //                     resultado.add(p);
    //             else {
    //                 List<Persona> duenios = unidad.getDuenios();
    //                 for(Persona p : duenios)
    //                     resultado.add(p);
    //             }
    //         }
    //     }
    //     return resultado;
    // }

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
        return codigo + " " + nombre + " " + direccion;
    }
}
