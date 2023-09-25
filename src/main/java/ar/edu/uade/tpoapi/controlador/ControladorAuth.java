package ar.edu.uade.tpoapi.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.tpoapi.controlador.request.Auth.RegisterDTO;
import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.services.PersonaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class ControladorAuth {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    PersonaService personaService;

    @RequestMapping("/validarDocumento")
    public ResponseEntity<?> validoParaRegistro(@RequestParam String documento) throws PersonaException {
        String documentoValidar = documento;
        if (!personaService.existePersona(documentoValidar))
        {
            return ResponseEntity.badRequest().body("No se encuentra cargado el documento");
        }
        else{
            if(personaService.buscarPersona(documentoValidar).validoParaRegistro())
            {
                return ResponseEntity.ok().body("El documento es valido para registro");
            }
            else
            {
                return ResponseEntity.badRequest().body("El documento ya posee un usuario registrado");
            }
        }
    }

    @PatchMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegisterDTO registerDTO) throws PersonaException{
        if(!personaService.existePersona(registerDTO.getDocumento()))
        {
            return ResponseEntity.badRequest().body("No se encuentra cargado el documento");
        }
        else{
            if(personaService.buscarPersona(registerDTO.getDocumento()).validoParaRegistro())
            {
                if(personaService.existeMail(registerDTO.getMail()))
                {
                    return ResponseEntity.badRequest().body("El mail ya se encuentra registrado");
                }
                else
                {
                    Persona persona = personaService.buscarPersona(registerDTO.getDocumento());
                    persona.setMail(registerDTO.getMail());
                    persona.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
                    if(personaService.guardarPersona(persona))
                        return ResponseEntity.ok().body("Usuario registrado correctamente");
                    else
                        return ResponseEntity.badRequest().body("Error al registrar el usuario");
                }
            }
            else
            {
                return ResponseEntity.badRequest().body("El documento ya posee un usuario registrado");
            }
        }
        
    }
}
