package ar.edu.uade.tpoapi.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.tpoapi.controlador.request.Edificio.ConsultaEdificioDTO;
import ar.edu.uade.tpoapi.controlador.request.Edificio.CreateEdificioDTO;
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
@RequestMapping("/edificio")
public class ControladorEdificio {
    @Autowired
    EdificioService edificioService;

    private static ControladorEdificio instancia;


    public static ControladorEdificio getInstancia() {
        if(instancia == null)
            instancia = new ControladorEdificio();
        return instancia;
    }

    @GetMapping(value = "/getAll")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> getEdificios(){
        List<Edificio> edificios = edificioService.buscarTodosEdificios();
        List<EdificioView>edificioViews = new ArrayList<>();

        for(Edificio e : edificios)
            edificioViews.add(e.toView());
        
        return ResponseEntity.ok().body(edificioViews);
    }

    @GetMapping(value = "/getUnidades")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> getUnidadesPorEdificio(@RequestParam int codigo) throws EdificioException{
         List<UnidadView> resultado = new ArrayList<UnidadView>();
         Edificio edificio = buscarEdificio(codigo);
         List<Unidad> unidades = edificio.getUnidades();
         for(Unidad unidad : unidades)
             resultado.add(unidad.toView());
        return ResponseEntity.ok().body(resultado);
    }

    @GetMapping(value = "/habilitados")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> habilitadosPorEdificio(@RequestParam int codigo) throws EdificioException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Edificio edificio = buscarEdificio(codigo);
        Set<Persona> habilitados = edificio.habilitados();
        for(Persona persona : habilitados)
            resultado.add(persona.toView());
        return ResponseEntity.ok().body(resultado);
    }

    @GetMapping(value = "/duenios")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public List<PersonaView> dueniosPorEdificio(@RequestBody int codigo) throws EdificioException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Edificio edificio = buscarEdificio(codigo);
        Set<Persona> duenios = edificio.duenios();
        for(Persona persona : duenios)
            resultado.add(persona.toView());
        return resultado;
    }

    @GetMapping(value = "/habitantes")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public List<PersonaView> habitantesPorEdificio(@RequestParam int codigo) throws EdificioException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Edificio edificio = buscarEdificio(codigo);
        Set<Persona> habitantes = edificio.duenios();
        for(Persona persona : habitantes)
            resultado.add(persona.toView());
        return resultado;
    }

    @PostMapping(value = "/agregaredificio")
    public ResponseEntity<?> agregarEdificio(@Valid @RequestBody CreateEdificioDTO createEdificioDTO ) throws EdificioException {
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

    protected Edificio buscarEdificio(int codigo) throws EdificioException {
        return edificioService.buscarEdificioPorCodigo(codigo);
    }
}
