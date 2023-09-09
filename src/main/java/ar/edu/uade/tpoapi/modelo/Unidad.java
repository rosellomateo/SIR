package ar.edu.uade.tpoapi.modelo;

import java.util.ArrayList;
import java.util.List;

import ar.edu.uade.tpoapi.exceptions.UnidadException;
import ar.edu.uade.tpoapi.views.EdificioView;
import ar.edu.uade.tpoapi.views.UnidadView;
import jakarta.persistence.*;

@Entity
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

    public Unidad(int id, String piso, String numero, Edificio edificio) {
        this.id = id;
        this.piso = piso;
        this.numero = numero;
        this.habitado = false;
        this.edificio = edificio;
        this.duenios = new ArrayList<Persona>();
        this.inquilinos = new ArrayList<Persona>();
    }

    public Unidad( String piso, String numero, Edificio edificio){

        this.piso = piso;
        this.numero = numero;
        this.habitado = false;
        this.edificio = edificio;
        this.duenios = new ArrayList<Persona>();
        this.inquilinos = new ArrayList<Persona>();

    }

    public Unidad() {
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

    public int getId() {
        return id;
    }

    public String getPiso() {
        return piso;
    }

    public String getNumero() {
        return numero;
    }


    public Edificio getEdificio() {
         return edificio;
    }

    public List<Persona> getDuenios() {
        return duenios;
    }

    public List<Persona> getInquilinos() {
        return inquilinos;
    }

     public UnidadView toView() {
         EdificioView auxEdificio = edificio.toView();
         return new UnidadView(id, piso, numero, habitado, auxEdificio);
     }

    public String toString() {
        return "Unidad: " + piso + " " + numero + " " + habitado;
    }
}
