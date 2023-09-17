package ar.edu.uade.tpoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.uade.tpoapi.modelo.Edificio;

public interface EdificioRepository extends JpaRepository<Edificio, Integer>{
    
    public Optional<Edificio> findByCodigo(Integer codigo);
	public Edificio getEdificioByCodigo(Integer codigo);
    public void deleteByCodigo(int codigo);
    public boolean existsByCodigo(int codigo); 

}
