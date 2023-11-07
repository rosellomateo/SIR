package ar.edu.uade.tpoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.uade.tpoapi.modelo.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    
    public Optional<Comentario> findByIdcomentario(Integer idComentario);
}
