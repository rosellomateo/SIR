package ar.edu.uade.tpoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.uade.tpoapi.modelo.Reclamo;

public interface ReclamoRepository extends JpaRepository<Reclamo, Integer>{
    
    public Optional<Reclamo> getReclamoByNumero(Integer  numero); 
    
}
