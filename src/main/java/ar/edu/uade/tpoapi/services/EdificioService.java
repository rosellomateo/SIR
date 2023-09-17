package ar.edu.uade.tpoapi.services;

import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.repository.EdificioRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EdificioService  {
    @Autowired
    EdificioRepository edificioRepository;
    

    public List<Edificio> buscarTodosEdificios()
    {
        return edificioRepository.findAll();
    }

    public Edificio buscarEdificioPorCodigo(int codigo)
    {
        return edificioRepository.getEdificioByCodigo(codigo);
    }

    public Edificio guardarEdificio(Edificio edificio)
    {
        return edificioRepository.save(edificio);
    }

    public void eliminarEdificio(Edificio edificio)
    {
        edificioRepository.delete(edificio);
    }

    public void eliminarEdificioPorCodigo(int codigo)
    {
        edificioRepository.deleteByCodigo(codigo);
    }

    public boolean existeEdificio(int codigo)
    {
        return edificioRepository.existsByCodigo(codigo);
    }
}
