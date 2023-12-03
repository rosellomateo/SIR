package ar.edu.uade.tpoapi.controlador;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.tpoapi.controlador.request.Unidad.CreateUnidadDTO;
import ar.edu.uade.tpoapi.controlador.request.Unidad.TransferirUnidadDTO;
import ar.edu.uade.tpoapi.controlador.request.Unidad.UnidadDTO;
import ar.edu.uade.tpoapi.exceptions.EdificioException;
import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.exceptions.UnidadException;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.services.UnidadService;
import ar.edu.uade.tpoapi.views.PersonaView;
import ar.edu.uade.tpoapi.views.UnidadView;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unidad")
public class ControladorUnidad {
    @Autowired
    UnidadService unidadService;

    @PutMapping("/crearUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    @Transactional
    public ResponseEntity<?> crearUnidad(@Valid @RequestBody CreateUnidadDTO createUnidadDTO) throws UnidadException, EdificioException{
        if(unidadService.existeUnidad(createUnidadDTO))
            return ResponseEntity.badRequest().body("Ya existe una unidad igual para ese edificio");
        else{
            Unidad unidad = unidadService.crearUnidad(createUnidadDTO);
            if(unidad != null)
                return ResponseEntity.ok().body(unidad.toView());
            return ResponseEntity.badRequest().body("No se pudo crear la unidad");
        }
    }

    @GetMapping("/buscarUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin') or hasRole('Encargado') or hasRole('Residente')")
    public ResponseEntity<?> buscarUnidad(@RequestParam int identificador) throws UnidadException{
        Unidad unidad = unidadService.buscarUnidad(identificador);
        if(unidad != null)
            return ResponseEntity.ok().body(unidad.toView());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/transferirUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> transferirUnidad(@Valid @RequestBody TransferirUnidadDTO transferirUnidadDTO) throws UnidadException, PersonaException {
        return ResponseEntity.ok().body(unidadService.transferirUnidad(transferirUnidadDTO));
    }

    @PostMapping("/alquilarUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> alquilarUnidad(@Valid @RequestBody UnidadDTO unidadDTO) throws UnidadException, PersonaException{
        return ResponseEntity.ok().body(unidadService.alquilarUnidad(unidadDTO));
    }

    @PostMapping("/liberarUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> liberarUnidad(@RequestParam int identificador) throws UnidadException {
        return ResponseEntity.ok().body(unidadService.liberarUnidad(identificador));
    }

    @PostMapping("/habitarUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> habitarUnidad(@RequestParam int identificador) throws UnidadException {
        return ResponseEntity.ok().body(unidadService.habitarUnidad(identificador));
    }

    @DeleteMapping("/eliminarUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> eliminarUnidad(@RequestParam int identificador) throws UnidadException {
        return ResponseEntity.ok().body(unidadService.eliminarUnidad(identificador));
    }

    @PostMapping("/agregarDuenioUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> agregarDuenioUnidad(@Valid @RequestBody UnidadDTO unidadDTO) throws UnidadException, PersonaException {
        return ResponseEntity.ok().body(unidadService.agregarDuenioUnidad(unidadDTO));
    }
    
    @PostMapping("/agregarInquilinoUnidad")    
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> agregarInquilinoUnidad(@Valid @RequestBody UnidadDTO unidadDTO) throws UnidadException, PersonaException{
        return ResponseEntity.ok().body(unidadService.agregarInquilinoUnidad(unidadDTO));
    }

    @GetMapping("/dueniosPorUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> dueniosPorUnidad(@RequestParam int identificador) throws UnidadException{
        Set<PersonaView> resultado = unidadService.dueniosPorUnidad(identificador);
        if(resultado.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(resultado);
    }

    @GetMapping("/inquilinosPorUnidad")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> inquilinosPorUnidad(@RequestParam int identificador) throws UnidadException{
        Set<PersonaView> resultado = unidadService.inquilinosPorUnidad(identificador);
         if(resultado.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(resultado);
    }

    @GetMapping("/obtenerTodas")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?> obtenerTodas() throws UnidadException{
        Set<UnidadView> resultado = unidadService.obtenerTodas();
        if(resultado.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(resultado);
    }
}

