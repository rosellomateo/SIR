package ar.edu.uade.tpoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.uade.tpoapi.modelo.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer>{

    public Persona findByDocumento(String documento);
    
}
