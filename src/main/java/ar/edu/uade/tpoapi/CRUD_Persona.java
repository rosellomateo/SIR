package ar.edu.uade.tpoapi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;


import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.repository.PersonaRepository;

public class CRUD_Persona implements CommandLineRunner {
    @Autowired
    PersonaRepository personaRepository;
    public static void main(String[] args) {
        SpringApplication.run(CRUD_Persona.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //CRUD Persona
        //1.Encontrar una persona determinada
        Optional<Persona> persona = personaRepository.findByDocumento("DNI29988738");
        System.out.println(persona.toString());

        //2. Encontrar todas las personas
        List<Persona> p = personaRepository.findAll();
        for (Persona per:
                p) {
            System.out.println(per.toString());
        }
    }   
}
        