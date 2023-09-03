package ar.edu.uade.tpoapi.repository;

import java.util.Optional;

import ar.edu.uade.tpoapi.views.EdificioView;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.uade.tpoapi.modelo.Edificio;

public interface EdificioRepository extends JpaRepository<Edificio, Integer>{
    
    	public Optional<Edificio> findByCodigo(Integer codigo);

}
