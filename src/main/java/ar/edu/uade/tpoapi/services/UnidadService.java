package ar.edu.uade.tpoapi.services;

import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.repository.UnidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnidadService {
    @Autowired
    UnidadRepository unidadRepository;

    public boolean existeUnidad(int identificador){
        return unidadRepository.existsById(identificador);
    }

    public Unidad buscarUnidad(int identificador){
        if (existeUnidad(identificador)) {
            return unidadRepository.findById(identificador).get();
        }else{
            return null;
        }
    }

    public Unidad buscarUnidad(int codigo, String piso, String numero) {
        Unidad unidad = unidadRepository.findByEdificioCodigoAndPisoAndNumero(codigo, piso, numero);
        return unidad;
    }

    public Unidad crearUnidad(Unidad unidad) {

        return unidadRepository.save(unidad);
    }

    public void eliminarUnidad(Unidad unidad) {
        unidadRepository.delete(unidad);

    }




}
