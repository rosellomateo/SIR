package ar.edu.uade.tpoapi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.uade.tpoapi.modelo.Imagen;

public interface ImagenRepository extends JpaRepository<Imagen, Integer>{
    
	public Optional<Imagen> findByNumero(Integer  numero); 
}
