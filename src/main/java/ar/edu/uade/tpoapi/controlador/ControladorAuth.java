package ar.edu.uade.tpoapi.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.tpoapi.controlador.request.Auth.RegisterPasswordDTO;
import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.security.jwt.JwtUtils;
import ar.edu.uade.tpoapi.services.PersonaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class ControladorAuth {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    PersonaService personaService;

    @RequestMapping("/validarMail")
    public ResponseEntity<?> validarMailParaRegistro(@RequestParam String documento, @RequestParam String mail) throws PersonaException {
        String documentoValidar = documento;
        String mailValidar = mail;
        if (!personaService.existePersona(documentoValidar))
        {
            return ResponseEntity.badRequest().body("No se encuentra cargado el documento");
        }
        else{
            if(personaService.buscarPersona(documentoValidar).validoParaRegistroMail())
            {
                if(personaService.existeMail(mailValidar))
                {
                    return ResponseEntity.badRequest().body("El mail ya se encuentra registrado");
                }
                else
                {
                    //Envia mail de confirmacion
                    personaService.enviarMailConfirmacion(documentoValidar, mailValidar);
                    return ResponseEntity.ok().body("El mail es valido para registro, se envio un mail de confirmacion");
                }
            }
            else
            {
                return ResponseEntity.badRequest().body("El documento ya posee un usuario registrado");
            }
        }
    }

    @PatchMapping("/registrarPassword")
    public ResponseEntity<?> registrarPassword(@Valid @RequestBody RegisterPasswordDTO registerDTO) throws PersonaException{
        if(!personaService.existePersona(registerDTO.getDocumento()))
        {
            return ResponseEntity.badRequest().body("No se encuentra cargado el documento");
        }
        else{
            if(personaService.buscarPersona(registerDTO.getDocumento()).validoParaRegistroPassword())
            {
                Persona persona = personaService.buscarPersona(registerDTO.getDocumento());
                persona.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
                if(personaService.guardarPersona(persona))
                    return ResponseEntity.ok().body("Usuario registrado correctamente");
                else
                    return ResponseEntity.badRequest().body("Error al registrar el usuario");
            }
            else
            {
                return ResponseEntity.badRequest().body("El documento ya posee un usuario registrado");
            }
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<String> refreshJwtToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String authToken = token.substring(7);
            if (jwtUtils.validateAccesToken(authToken)) {
                String email = jwtUtils.getMailFromToken(authToken);
                // Genera un nuevo token
                String newToken = jwtUtils.generateAccesToken(email);
                // Devuelve el nuevo token
                return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + newToken).body("Token refreshed successfully.");
            }
        }
        return ResponseEntity.badRequest().body("Token invalid or missing.");
    }

    @PostMapping("/confirmarMail")
    public ResponseEntity<?> confirmarMail(@RequestParam String token, @RequestParam String mail) throws PersonaException {
        Persona persona = personaService.buscarPersonaPorMail(mail);
        if(persona == null)
        {
            return ResponseEntity.badRequest().body("No se encuentra cargado el mail");
        }
        else
        {
            if(persona.getTokenVerificacion() == null)
            {
                return ResponseEntity.badRequest().body("El mail ya se encuentra confirmado");
            }
            if(!persona.getTokenVerificacion().equals(token))
            {
                return ResponseEntity.badRequest().body("El token no es valido");
            }
            personaService.confirmarMail(mail);
            return ResponseEntity.ok().body("Mail confirmado correctamente");
        }
    }

    @PostMapping("/olvidePassword")
    public ResponseEntity<?> olvidePassword(@RequestParam String mail) throws PersonaException {
        return ResponseEntity.ok().body(personaService.enviarMailOlvidePassword(mail));
    }

    @PatchMapping("/cambiarPassword")
    public ResponseEntity<?> cambiarPassword(@RequestParam String token, @RequestParam String mail, @RequestParam String password) throws PersonaException {
        Persona persona = personaService.buscarPersonaPorMail(mail);
        if(persona == null)
        {
            return ResponseEntity.badRequest().body("No se encuentra cargado el mail");
        }
        else
        {
            if(persona.getTokenVerificacion() == null)
            {
                return ResponseEntity.badRequest().body("El mail ya se encuentra confirmado");
            }
            if(!persona.getTokenVerificacion().equals(token))
            {
                return ResponseEntity.badRequest().body("El token no es valido");
            }
            persona.setPassword(passwordEncoder.encode(password));
            persona.setTokenVerificacion(null);
            personaService.guardarPersona(persona);
            return ResponseEntity.ok().body("Password cambiado correctamente");
        }
    }

    @GetMapping("/reenviarTokenMail")
    public ResponseEntity<?> reenviarToken(@RequestParam String mail) throws PersonaException {
        Persona persona = personaService.buscarPersonaPorMail(mail);
        if(persona == null)
        {
            return ResponseEntity.badRequest().body("No se encuentra cargado el mail");
        }
        else
        {
            if(persona.isCuentaVerificado())
            {
                return ResponseEntity.badRequest().body("El mail ya se encuentra confirmado");
            }
            personaService.enviarMailConfirmacion(persona.getDocumento(), mail);
            return ResponseEntity.ok().body("Mail reenviado correctamente");
        }
    }

    @GetMapping("/reenviarTokenPassword")
    public ResponseEntity<?> reenviarTokenPassword(@RequestParam String mail) throws PersonaException {
        Persona persona = personaService.buscarPersonaPorMail(mail);
        if(persona == null)
        {
            return ResponseEntity.badRequest().body("No se encuentra cargado el mail");
        }
        else
        {
            if(persona.getTokenVerificacion() == null)
            {
                return ResponseEntity.badRequest().body("No se encuentra solicitado el cambio de password");
            }
            return ResponseEntity.ok().body(personaService.enviarMailOlvidePassword(mail));
        }
    }
}
