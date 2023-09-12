package ar.edu.uade.tpoapi.controlador;

import java.util.ArrayList;
import java.util.List;

import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.exceptions.UnidadException;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.views.PersonaView;

public class ControladorUnidad {

    private static ControladorUnidad instancia;
    private static final ControladorPersona controladorPersona = ControladorPersona.getInstancia();

    private ControladorUnidad() { }

    public static ControladorUnidad getInstancia() {
        if(instancia == null)
            instancia = new ControladorUnidad();
        return instancia;
    }

    public void transferirUnidad(int codigo, String piso, String numero, String documento) throws UnidadException, PersonaException {
        Unidad unidad = buscarUnidad(codigo, piso, numero);
        Persona persona = controladorPersona.buscarPersona(documento);
        unidad.transferir(persona);
    }

    public void agregarDuenioUnidad(int codigo, String piso, String numero, String documento) throws UnidadException, PersonaException {
        Unidad unidad = buscarUnidad(codigo, piso, numero);
        Persona persona = controladorPersona.buscarPersona(documento);
        unidad.agregarDuenio(persona);
    }

    public void alquilarUnidad(int codigo, String piso, String numero, String documento) throws UnidadException, PersonaException{
        Unidad unidad = buscarUnidad(codigo, piso, numero);
        Persona persona = controladorPersona.buscarPersona(documento);
        unidad.alquilar(persona);
    }

    public void agregarInquilinoUnidad(int codigo, String piso, String numero, String documento) throws UnidadException, PersonaException{
        Unidad unidad = buscarUnidad(codigo, piso, numero);
        Persona persona = controladorPersona.buscarPersona(documento);
        unidad.agregarInquilino(persona);
    }

    public void liberarUnidad(int codigo, String piso, String numero) throws UnidadException {
        Unidad unidad = buscarUnidad(codigo, piso, numero);
        unidad.liberar();
    }

    public void habitarUnidad(int codigo, String piso, String numero) throws UnidadException {
        Unidad unidad = buscarUnidad(codigo, piso, numero);
        unidad.habitar();;
    }

    protected Unidad buscarUnidad(int codigo, String piso, String numero) throws UnidadException{
        return null;
    }

    public List<PersonaView> dueniosPorUnidad(int codigo, String piso, String numero) throws UnidadException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Unidad unidad = buscarUnidad(codigo, piso, numero);
        List<Persona> duenios = unidad.getDuenios();
        for(Persona persona : duenios)
            resultado.add(persona.toView());
        return resultado;
    }

    public List<PersonaView> inquilinosPorUnidad(int codigo, String piso, String numero) throws UnidadException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Unidad unidad = buscarUnidad(codigo, piso, numero);
        List<Persona> inquilinos = unidad.getInquilinos();
        for(Persona persona : inquilinos)
            resultado.add(persona.toView());
        return resultado;
    }
}
