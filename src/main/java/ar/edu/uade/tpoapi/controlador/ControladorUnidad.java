package ar.edu.uade.tpoapi.controlador;

import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.exceptions.UnidadException;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Unidad;

public class ControladorUnidad {

    private static ControladorUnidad instancia;
    private static ControladorPersona controladorPersona = ControladorPersona.getInstancia();

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
}
