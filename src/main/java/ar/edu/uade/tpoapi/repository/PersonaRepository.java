package ar.edu.uade.tpoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.uade.tpoapi.modelo.Persona;

public interface PersonaRepository extends JpaRepository<Persona, String>{

    public Optional<Persona> findByDocumento(String documento);
    public void deleteByDocumento(String documento);
    public boolean existsByDocumento(String documento);
    public Persona getByDocumento(String documento);
}
