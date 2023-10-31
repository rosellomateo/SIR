package ar.edu.uade.tpoapi.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Roles;
import ar.edu.uade.tpoapi.repository.PersonaRepository;

@Service
public class PersonaService {
    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    SendMessageService sendMessageService;

    public boolean eliminarPersona(String documento) {
        try {
            personaRepository.deleteById(documento);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean existePersona(String documento) {

        return personaRepository.existsById(documento);
    }

    public boolean existeMail(String mail) {
        return personaRepository.existsByMail(mail);
    }

    public Persona buscarPersona(String documento) {
        return personaRepository.findById(documento).orElse(null);
        
    }

    public Boolean guardarPersona(Persona personaRegistro) {
        try {
            personaRepository.save(personaRegistro);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Persona buscarPersonaPorMail(String mail) {
        Optional<Persona> persona = personaRepository.findByMail(mail);
        return persona.orElse(null);
    }

    public void modificarPersona(String documento, Set<Roles> roles) {
        Persona persona = buscarPersona(documento);
        persona.setRoles(roles);
        personaRepository.save(persona);
    }

    public void enviarMailConfirmacion(String documentoValidar, String mailValidar) {
        Persona persona = buscarPersona(documentoValidar);
        if(persona == null)
        {
            return;
        }
        String token = generarToken();
        persona.setTokenMail(token);
        personaRepository.save(persona);
        sendMessageService.enviarMailConfirmacion(mailValidar, token);
    }

    private String generarToken() {
        return String.valueOf((int) (Math.random() * 999999 + 100000));
    }
}
