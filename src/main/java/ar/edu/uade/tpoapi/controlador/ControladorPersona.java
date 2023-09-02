package ar.edu.uade.tpoapi.controlador;

import java.util.ArrayList;
import java.util.List;

import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.exceptions.UnidadException;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.views.PersonaView;

public class ControladorPersona {
    
    private static ControladorPersona instancia;
    private static ControladorUnidad controladorUnidad = ControladorUnidad.getInstancia();

    private ControladorPersona() { }

    public static ControladorPersona getInstancia() {
        if(instancia == null)
            instancia = new ControladorPersona();
        return instancia;
    }

    protected Persona buscarPersona(String documento) throws PersonaException {
        return null;
    }

    public void agregarPersona(String documento, String nombre) {
        Persona persona = new Persona(documento, nombre, null, null);
        //guardar el objeto
    }

    public void eliminarPersona(String documento) throws PersonaException {
        Persona persona = buscarPersona(documento);
        //eliminar el objeto
    }

    public List<PersonaView> dueniosPorUnidad(int codigo, String piso, String numero) throws UnidadException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Unidad unidad = controladorUnidad.buscarUnidad(codigo, piso, numero);
        List<Persona> duenios = unidad.getDuenios();
        for(Persona persona : duenios)
            resultado.add(persona.toView());
        return resultado;
    }

    public List<PersonaView> inquilinosPorUnidad(int codigo, String piso, String numero) throws UnidadException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Unidad unidad = controladorUnidad.buscarUnidad(codigo, piso, numero);
        List<Persona> inquilinos = unidad.getInquilinos();
        for(Persona persona : inquilinos)
            resultado.add(persona.toView());
        return resultado;
    }
}
