package ar.edu.uade.tpoapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.uade.tpoapi.modelo.Templates;

@Repository
public interface TemplateRepository extends CrudRepository<Templates, Integer>{

}
