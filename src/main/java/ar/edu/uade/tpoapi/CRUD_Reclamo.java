package ar.edu.uade.tpoapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import ar.edu.uade.tpoapi.repository.EdificioRepository;
import ar.edu.uade.tpoapi.repository.UnidadRepository;

public class CRUD_Reclamo implements CommandLineRunner{
    @Autowired
    UnidadRepository unidadRepository;
    @Autowired
    EdificioRepository edificioRepository;

    public static void main(String[] args) {
        SpringApplication.run(CRUD_Reclamo.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
    
}
