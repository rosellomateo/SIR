package ar.edu.uade.tpoapi.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.tpoapi.controlador.request.CreateEdificioDTO;
import ar.edu.uade.tpoapi.exceptions.EdificioException;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.services.EdificioService;
import ar.edu.uade.tpoapi.views.EdificioView;
import ar.edu.uade.tpoapi.views.PersonaView;
import ar.edu.uade.tpoapi.views.UnidadView;
import jakarta.validation.Valid;

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

    @RequestMapping(value = "/edificios/{codigo}",method = RequestMethod.GET)
    public List<UnidadView> getUnidadesPorEdificio(@PathVariable int codigo) throws EdificioException{
         List<UnidadView> resultado = new ArrayList<UnidadView>();
         Edificio edificio = buscarEdificio(codigo);
         List<Unidad> unidades = edificio.getUnidades();
         for(Unidad unidad : unidades)
             resultado.add(unidad.toView());
         return resultado;
    }

    @RequestMapping(value = "/habilitadosxedificios/{codigo}",method = RequestMethod.GET)
    public List<PersonaView> habilitadosPorEdificio(@PathVariable int codigo) throws EdificioException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Edificio edificio = buscarEdificio(codigo);
        Set<Persona> habilitados = edificio.habilitados();
        for(Persona persona : habilitados)
            resultado.add(persona.toView());
        return resultado;
    }

    @RequestMapping(value = "/dueniosxedificios/{codigo}",method = RequestMethod.GET)
    public List<PersonaView> dueniosPorEdificio(@PathVariable int codigo) throws EdificioException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Edificio edificio = buscarEdificio(codigo);
        Set<Persona> duenios = edificio.duenios();
        for(Persona persona : duenios)
            resultado.add(persona.toView());
        return resultado;
    }

    @RequestMapping(value = "/habitantesxedificios/{codigo}",method = RequestMethod.GET)
    public List<PersonaView> habitantesPorEdificio(@PathVariable int codigo) throws EdificioException{
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

    @PostMapping(value = "/agregaredificio")
    public ResponseEntity<?> agregarEdificio(@Valid @RequestBody CreateEdificioDTO createEdificioDTO) throws EdificioException {
        if(edificioService.existeNombre(createEdificioDTO.getNombre()))
            return ResponseEntity.badRequest().body("Ya existe un edificio con ese nombre");
        else if (edificioService.existeDireccion(createEdificioDTO.getDireccion()))
            return ResponseEntity.badRequest().body("Ya existe un edificio con esa direccion");
        else{
            Edificio edificio = Edificio.builder()
                    .nombre(createEdificioDTO.getNombre())
                    .direccion(createEdificioDTO.getDireccion())
                    .build();
            edificioService.guardarEdificio(edificio);
            return ResponseEntity.ok().body("Edificio agregado correctamente");
        }
    }
}
