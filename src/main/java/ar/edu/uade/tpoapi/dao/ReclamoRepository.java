package ar.edu.uade.tpoapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.uade.tpoapi.modelo.Reclamo;

public interface ReclamoRepository extends JpaRepository<Reclamo, Integer>{
    
}
