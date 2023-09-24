package ar.edu.uade.tpoapi.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.tpoapi.controlador.request.Edificio.CreateEdificioDTO;
import ar.edu.uade.tpoapi.controlador.request.persona.LoginPersonaDTO;
import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.services.EdificioService;
import ar.edu.uade.tpoapi.services.EncriptServiceImplement;
import ar.edu.uade.tpoapi.services.PersonaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/persona")
public class ControladorPersona {
    
    @Autowired
    PersonaService personaService;
    @Autowired
    EncriptServiceImplement encriptServiceImplement;
    
    private static ControladorPersona instancia;

    private ControladorPersona() { }

    public static ControladorPersona getInstancia() {
        if(instancia == null)
            instancia = new ControladorPersona();
        return instancia;
    }

    protected Persona buscarPersona(String documento) throws PersonaException {
        return null;
    }

    public void agregarPersona(String documento, String nombre)  throws PersonaException{
        Persona persona = new Persona(documento, nombre, null, null);
        //guardar el objeto
    }

    public void eliminarPersona(String documento) throws PersonaException {
        Persona persona = buscarPersona(documento);
        //eliminar el objeto
    }

    private void modificarPersona(Persona persona) throws PersonaException {
        //modificar el objeto
    }

    public boolean validoParaRegistro(String documento) throws PersonaException {
        Persona p = buscarPersona(documento);
        if(p == null)
            return false;
        else{
            if(p.getMail() == null && p.getPassword() == null)
                return true;
            else
                return false;
        }
    }

    public void registrar(Persona p) throws PersonaException{
        if (validoParaRegistro(p.getDocumento())){
            modificarPersona(p);
        }
        else{
            throw new PersonaException("No se puede registrar");
        }
    }    
}
