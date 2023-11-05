package ar.edu.uade.tpoapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.uade.tpoapi.modelo.Unidad;

public interface UnidadRepository extends JpaRepository<Unidad, Integer>{
    
    public Optional<Unidad> findById(Integer  id);
    public void deleteById(Integer id);
    public Unidad getById(Integer id);
    public Unidad findByEdificioCodigoAndPisoAndNumero(int codigo, String piso, String numero);
    public boolean existsByEdificioCodigoAndPisoAndNumero(int codigo, String piso, String numero);

}
