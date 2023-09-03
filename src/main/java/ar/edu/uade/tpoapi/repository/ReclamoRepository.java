package ar.edu.uade.tpoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.uade.tpoapi.modelo.Reclamo;

public interface ReclamoRepository extends JpaRepository<Reclamo, Integer>{
    
}
