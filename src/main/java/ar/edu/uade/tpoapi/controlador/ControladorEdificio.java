package ar.edu.uade.tpoapi.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.tpoapi.exceptions.EdificioException;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.services.EdificioService;
import ar.edu.uade.tpoapi.views.EdificioView;
import ar.edu.uade.tpoapi.views.PersonaView;
import ar.edu.uade.tpoapi.views.UnidadView;

@RestController
public class ControladorEdificio {
    @Autowired
    EdificioService edificioService;

    private static ControladorEdificio instancia;


    public static ControladorEdificio getInstancia() {
        if(instancia == null)
            instancia = new ControladorEdificio();
        return instancia;
    }

    @RequestMapping(value = "/edificios",method = RequestMethod.GET)
    public List<EdificioView> getEdificios(){
        List<Edificio> edificios = edificioService.buscarTodosEdificios();
        List<EdificioView>edificioViews = new ArrayList<>();

        for(Edificio e : edificios)
            edificioViews.add(e.toView());

        return edificioViews;
    }

    @RequestMapping(value = "/edificios/{codigo}",method = RequestMethod.POST)
    public List<UnidadView> getUnidadesPorEdificio(@PathVariable int codigo) throws EdificioException{
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
        return edificioService.buscarEdificioPorCodigo(codigo);
    }


}
