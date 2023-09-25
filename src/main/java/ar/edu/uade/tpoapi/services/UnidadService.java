package ar.edu.uade.tpoapi.services;

import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.repository.UnidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnidadService {
    @Autowired
    UnidadRepository unidadRepository;

    public boolean existeUnidad(int codigo){
        return unidadRepository.existsById(codigo);
    }

    public Unidad buscarUnidad(int codigo){
        if (existeUnidad(codigo)) {
            return unidadRepository.findById(codigo).get();
        }else{
            return null;
        }
    }

}
