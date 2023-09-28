package ar.edu.uade.tpoapi.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Roles;
import ar.edu.uade.tpoapi.repository.PersonaRepository;

@Service
public class PersonaService {
    @Autowired
    PersonaRepository personaRepository;

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

    
}
