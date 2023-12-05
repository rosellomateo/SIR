package ar.edu.uade.tpoapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.uade.tpoapi.modelo.Edificio;

public interface EdificioRepository extends JpaRepository<Edificio, Integer>{

    public Optional<Edificio> findByCodigo(Integer codigo);
	public Edificio getEdificioByCodigo(Integer codigo);
    public void deleteByCodigo(int codigo);
    public boolean existsByCodigo(int codigo);
    public boolean existsByDireccion(String direccion);
    public boolean existsByNombre(String nombre);

    @Query(value = "SELECT * FROM edificios", nativeQuery = true)
    public List<Object[]> obtenerSelector();

}
