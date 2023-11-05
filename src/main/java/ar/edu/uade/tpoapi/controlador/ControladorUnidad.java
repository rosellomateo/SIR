package ar.edu.uade.tpoapi.controlador;

import java.util.ArrayList;
import java.util.List;

import ar.edu.uade.tpoapi.controlador.request.Unidad.TransferirUnidadDTO;
import ar.edu.uade.tpoapi.controlador.request.Unidad.UnidadDTO;
import ar.edu.uade.tpoapi.exceptions.EdificioException;
import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.exceptions.UnidadException;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.services.UnidadService;
import ar.edu.uade.tpoapi.views.PersonaView;
import jakarta.validation.Valid;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unidad")
public class ControladorUnidad {
    @Autowired
    UnidadService unidadService;

    @PutMapping("/crearUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> crearUnidad(@Valid @RequestBody UnidadDTO unidadDTO) throws UnidadException, EdificioException{
        Edificio edificio = ControladorEdificio.getInstancia().buscarEdificio(unidadDTO.getCodigo())  ;

        Unidad unidad = Unidad.builder()
                .edificio(edificio)
                .piso(unidadDTO.getPiso())
                .numero(unidadDTO.getNumero())
                .build();   
        unidadService.crearUnidad(unidad);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/transferirUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> transferirUnidad(@Valid @RequestBody TransferirUnidadDTO transferirUnidadDTO) throws UnidadException, PersonaException {
        return ResponseEntity.ok().body(unidadService.transferirUnidad(transferirUnidadDTO));
    }

    @PostMapping("/alquilarUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> alquilarUnidad(@Valid @RequestBody UnidadDTO unidadDTO) throws UnidadException, PersonaException{
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        //Persona persona = controladorPersona.buscarPersona(unidadDTO.getDocumento());
        //unidad.alquilar(persona);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/liberarUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> liberarUnidad(@Valid @RequestBody UnidadDTO unidadDTO) throws UnidadException {
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        unidad.liberar();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/habitarUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> habitarUnidad(@Valid @RequestBody UnidadDTO unidadDTO) throws UnidadException {
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        unidad.habitar();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminarUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> eliminarUnidad(@Valid @RequestBody UnidadDTO unidadDTO) throws UnidadException {
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        unidadService.eliminarUnidad(unidad);
        return ResponseEntity.ok().build();
    }

    protected Unidad buscarUnidad(int codigo, String piso, String numero) throws UnidadException{
        Unidad unidad = unidadService.buscarUnidad(codigo, piso, numero);
        if(unidad == null)
            throw new UnidadException("No se encontro la unidad");
        return unidad;
    }

    @PostMapping("/agregarDuenioUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> agregarDuenioUnidad(@Valid @RequestBody UnidadDTO unidadDTO) throws UnidadException, PersonaException {
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        //Persona persona = controladorPersona.buscarPersona(unidadDTO.getDocumento());
        //unidad.agregarDuenio(persona);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/agregarInquilinoUnidad")    
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> agregarInquilinoUnidad(@Valid @RequestBody UnidadDTO unidadDTO) throws UnidadException, PersonaException{
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        //Persona persona = controladorPersona.buscarPersona(unidadDTO.getDocumento());
        //unidad.agregarInquilino(persona);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dueniosPorUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> dueniosPorUnidad(@Valid @RequestBody UnidadDTO unidadDTO) throws UnidadException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        List<Persona> duenios = unidad.getDuenios();
        for(Persona persona : duenios)
            resultado.add(persona.toView());
        return ResponseEntity .ok().body(resultado);
    }

    @GetMapping("/inquilinosPorUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> inquilinosPorUnidad(@Valid @RequestBody UnidadDTO unidadDTO) throws UnidadException{
        List<PersonaView> resultado = new ArrayList<PersonaView>();
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        List<Persona> inquilinos = unidad.getInquilinos();
        for(Persona persona : inquilinos)
            resultado.add(persona.toView());
        return ResponseEntity .ok().body(resultado);
    }

}

