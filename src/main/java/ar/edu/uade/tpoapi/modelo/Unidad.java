package ar.edu.uade.tpoapi.modelo;

import java.util.ArrayList;
import java.util.List;

import ar.edu.uade.tpoapi.exceptions.UnidadException;
import ar.edu.uade.tpoapi.views.EdificioView;
import ar.edu.uade.tpoapi.views.UnidadView;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "unidades")
public class Unidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identificador")
    private int id;
    private String piso;
    private String numero;
    private boolean habitado;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigoedificio")
    private Edificio edificio;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "duenios", joinColumns = @JoinColumn(name = "identificador"), inverseJoinColumns = @JoinColumn(name = "documento"))
    private List<Persona> duenios;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "inquilinos", joinColumns = @JoinColumn(name = "identificador"), inverseJoinColumns = @JoinColumn(name = "documento"))
    private List<Persona> inquilinos;

    public void updateUnidad(UnidadView uView){
        this.piso = uView.getPiso();
        this.numero = uView.getNumero();
        this.habitado = uView.isHabitado();
    }

    public void transferir(Persona nuevoDuenio) {
        duenios = new ArrayList<Persona>();
        duenios.add(nuevoDuenio);
    }

    public void agregarDuenio(Persona duenio) {
        duenios.add(duenio);
    }

    public void alquilar(Persona inquilino) throws UnidadException {
        if(!this.habitado) {
            this.habitado = true;
            inquilinos = new ArrayList<Persona>();
            inquilinos.add(inquilino);
        }
        else
            throw new UnidadException("La unidad esta ocupada");
    }

    public void agregarInquilino(Persona inquilino) {
        inquilinos.add(inquilino);
    }

    public boolean estaHabitado() {
        return habitado;
    }

    public void liberar() {
        this.inquilinos = new ArrayList<Persona>();
        this.habitado = false;
    }

    public void habitar() throws UnidadException {
        if(this.habitado)
            throw new UnidadException("La unidad ya esta habitada");
        else
            this.habitado = true;
    }

   

     public UnidadView toView() {
         EdificioView auxEdificio = edificio.toView();
         return new UnidadView(id, piso, numero, habitado, auxEdificio);
     }

    public String toString() {
        return "piso: " + piso + " numero: " + numero + " habitado: " + habitado;
    }
}
