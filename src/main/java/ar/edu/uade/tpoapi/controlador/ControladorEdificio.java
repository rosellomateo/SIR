package ar.edu.uade.tpoapi.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ar.edu.uade.tpoapi.exceptions.EdificioException;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.views.EdificioView;
import ar.edu.uade.tpoapi.views.PersonaView;
import ar.edu.uade.tpoapi.views.UnidadView;

public class ControladorEdificio {

    private static ControladorEdificio instancia;

    private ControladorEdificio() { }

    public static ControladorEdificio getInstancia() {
        if(instancia == null)
            instancia = new ControladorEdificio();
        return instancia;
    }

    public List<EdificioView> getEdificios(){
        return null;
    }

    public List<UnidadView> getUnidadesPorEdificio(int codigo) throws EdificioException{
         List<UnidadView> resultado = new ArrayList<UnidadView>();
         Edificio edificio = buscarEdificio(codigo);
         List<Unidad> unidades = edificio.getUnidades();
         for(Unidad unidad : unidades)
             resultado.add(unidad.toView());
         return resultado;
    }

    public List<PersonaView> habilitadosPorEdificio(int codigo) throws EdificioException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Edificio edificio = buscarEdificio(codigo);
        Set<Persona> habilitados = edificio.habilitados();
        for(Persona persona : habilitados)
            resultado.add(persona.toView());
        return resultado;
    }

    public List<PersonaView> dueniosPorEdificio(int codigo) throws EdificioException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Edificio edificio = buscarEdificio(codigo);
        Set<Persona> duenios = edificio.duenios();
        for(Persona persona : duenios)
            resultado.add(persona.toView());
        return resultado;
    }

    public List<PersonaView> habitantesPorEdificio(int codigo) throws EdificioException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Edificio edificio = buscarEdificio(codigo);
        Set<Persona> habitantes = edificio.duenios();
        for(Persona persona : habitantes)
            resultado.add(persona.toView());
        return resultado;
    }

    protected Edificio buscarEdificio(int codigo) throws EdificioException {
        return null;
    }
}
