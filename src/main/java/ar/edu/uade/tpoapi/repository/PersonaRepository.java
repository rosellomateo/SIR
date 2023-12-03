package ar.edu.uade.tpoapi.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Enumerations.Rol;

public interface PersonaRepository extends JpaRepository<Persona, String>{

    public Optional<Persona> findByDocumento(String documento);
    public void deleteByDocumento(String documento);
    public boolean existsByDocumento(String documento);
    public Persona getByDocumento(String documento);
    public boolean existsByMail(String mail);
    public Optional<Persona> findByMail(String mail);
    public ArrayList<Persona> findByRol(Rol rol);
}
