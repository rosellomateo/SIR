package ar.edu.uade.tpoapi.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ar.edu.uade.tpoapi.exceptions.EdificioException;
import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.exceptions.ReclamoException;
import ar.edu.uade.tpoapi.exceptions.UnidadException;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Reclamo;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.views.EdificioView;
import ar.edu.uade.tpoapi.views.Estado;
import ar.edu.uade.tpoapi.views.PersonaView;
import ar.edu.uade.tpoapi.views.ReclamoView;
import ar.edu.uade.tpoapi.views.UnidadView;

public class ControladorReclamo {

    private static ControladorReclamo instancia;
    private static final ControladorEdificio controladorEdificio = ControladorEdificio.getInstancia();
    private static final ControladorUnidad controlerUnidad = ControladorUnidad.getInstancia();
    private static final ControladorPersona controlerPersona = ControladorPersona.getInstancia();

    private ControladorReclamo() { }

    public static ControladorReclamo getInstancia() {
        if(instancia == null)
            instancia = new ControladorReclamo();
        return instancia;
    }
    
    public List<ReclamoView> reclamosPorEdificio(int codigo){
        List<ReclamoView> resultado = new ArrayList<ReclamoView>();
        return resultado;
    }

    public List<ReclamoView> reclamosPorUnidad(int codigo, String piso, String numero) {
        List<ReclamoView> resultado = new ArrayList<ReclamoView>();
        return resultado;
    }

    public ReclamoView reclamosPorNumero(int numero) {
        ReclamoView resultado = null;
        return resultado;
    }

    public List<ReclamoView> reclamosPorPersona(String documento) {
        List<ReclamoView> resultado = new ArrayList<ReclamoView>();
        return resultado;
    }

    public int agregarReclamo(int codigo, String piso, String numero, String documento, String ubicacion, String descripcion) throws EdificioException, UnidadException, PersonaException {
        Edificio edificio = controladorEdificio.buscarEdificio(codigo);
        Unidad unidad = controlerUnidad.buscarUnidad(codigo, piso, numero);
        Persona persona = controlerPersona.buscarPersona(documento);
        Reclamo reclamo = new Reclamo(persona, edificio, ubicacion, descripcion, unidad);
        return reclamo.getNumero();
    }

    public void agregarImagenAReclamo(int numero, String direccion, String tipo) throws ReclamoException {
        Reclamo reclamo = buscarReclamo(numero);
        reclamo.agregarImagen(direccion, tipo);
    }

    public void cambiarEstado(int numero, Estado estado) throws ReclamoException {
        Reclamo reclamo = buscarReclamo(numero);
        reclamo.cambiarEstado(estado);
    }

    private Reclamo buscarReclamo(int numero) throws ReclamoException {
        return null;
    }
}
