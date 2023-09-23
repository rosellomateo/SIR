package ar.edu.uade.tpoapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.tpoapi.repository.PersonaRepository;

@Service
public class PersonaService {
    @Autowired
    PersonaRepository personaRepository;
}
