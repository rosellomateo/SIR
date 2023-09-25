package ar.edu.uade.tpoapi.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.services.PersonaService;

@RestController
@RequestMapping("/persona")
public class ControladorPersona {
    
    @Autowired
    PersonaService personaService;
    
    private static ControladorPersona instancia;

    private ControladorPersona() { }

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

    public void agregarPersona(String documento, String nombre)  throws PersonaException{
        if(personaService.existePersona(documento))
            throw new PersonaException("Ya existe una persona con ese documento");
        else{
            personaService.guardarPersona(new Persona(documento, nombre, null, null, null));
        }
    }

    public void eliminarPersona(String documento) throws PersonaException {
        personaService.eliminarPersona(documento);
    }

    private void modificarPersona(Persona persona) throws PersonaException {

        if(personaService.existePersona(persona.getDocumento()))
            personaService.guardarPersona(persona);
        else
            throw new PersonaException("No existe una persona con ese documento");
    }


}
