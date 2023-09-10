package ar.edu.uade.tpoapi.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.uade.tpoapi.modelo.Reclamo;

public interface ReclamoRepository extends JpaRepository<Reclamo, Integer>{
    
    public Optional<Reclamo> getReclamoByNumero(Integer  numero);
    List<Reclamo> findByEdificioCodigo(int codigo);
}
