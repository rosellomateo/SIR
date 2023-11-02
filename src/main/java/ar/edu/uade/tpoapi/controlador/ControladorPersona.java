package ar.edu.uade.tpoapi.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.tpoapi.controlador.request.Persona.CreatePersonaDTO;
import ar.edu.uade.tpoapi.controlador.request.Persona.DeletePersonaDTO;
import ar.edu.uade.tpoapi.controlador.request.Persona.UpdatePersonaDTO;
import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Enumerations.Rol;
import ar.edu.uade.tpoapi.services.PersonaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/persona")
public class ControladorPersona {
    
    @Autowired
    PersonaService personaService;
    
    private static ControladorPersona instancia;

    public static ControladorPersona getInstancia() {
        if(instancia == null)
            instancia = new ControladorPersona();
        return instancia;
    }

    protected Persona buscarPersona(String documento) throws PersonaException {
        if(personaService.existePersona(documento))
            return personaService.buscarPersona(documento);
        else
            throw new PersonaException("No existe una persona con ese documento");
    }

    @PostMapping("/agregar")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados')or hasRole('SuperAdmin')")
    public ResponseEntity<?>agregarPersona(@Valid @RequestBody CreatePersonaDTO createPersonaDTO)  throws PersonaException{
        if(personaService.existePersona(createPersonaDTO.getDocumento()))
            return ResponseEntity.badRequest().body("Ya existe una persona con ese documento");
        else{
            personaService.guardarPersona(Persona.builder().documento(createPersonaDTO.getDocumento()).nombre(createPersonaDTO.getNombre()).rol(Rol.valueOf(createPersonaDTO.getRol())).build());
            return ResponseEntity.ok().body("Persona agregada correctamente");
        }
    }

    @DeleteMapping("/eliminar")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> eliminarPersona(@Valid @RequestBody DeletePersonaDTO deletePersonaDTO) throws PersonaException {
        if(personaService.existePersona(deletePersonaDTO.getDocumento()))
        {
            try {
                personaService.eliminarPersona(deletePersonaDTO.getDocumento());
                return ResponseEntity.ok().body("Persona eliminada correctamente");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("No se pudo eliminar la persona");
            }
        }
        else
            return ResponseEntity.badRequest().body("No existe una persona con ese documento");
    }

    @PatchMapping("/modificar")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    private ResponseEntity<?> modificarPersona(@Valid @RequestBody UpdatePersonaDTO updatePersonaDTO) throws PersonaException {
        if(personaService.existePersona(updatePersonaDTO.getDocumento()))
        {
            try {
                personaService.modificarPersona(updatePersonaDTO.getDocumento(), updatePersonaDTO.getRoles());
                return ResponseEntity.ok().body("Persona modificada correctamente");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("No se pudo modificar la persona");
            }
        }
        else
            return ResponseEntity.badRequest().body("No existe una persona con ese documento");
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasRole('Admin') or hasRole('Empleados') or hasRole('SuperAdmin')")
    public ResponseEntity<?> testBuscarPersona(@Valid @RequestParam String documento) throws PersonaException {
        if(personaService.existePersona(documento))
        {
            try {
                return ResponseEntity.ok().body(personaService.buscarPersona(documento));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("No se pudo encontrar la persona");
            }
        }
        else
            return ResponseEntity.badRequest().body("No existe una persona con ese documento");
    }


}
