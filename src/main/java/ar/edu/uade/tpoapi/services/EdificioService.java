package ar.edu.uade.tpoapi.services;

import ar.edu.uade.tpoapi.controlador.request.Edificio.EdificioDTO;
import ar.edu.uade.tpoapi.exceptions.EdificioException;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.repository.EdificioRepository;
import ar.edu.uade.tpoapi.views.EdificioView;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EdificioService  {
    @Autowired
    EdificioRepository edificioRepository;
    

    public List<EdificioView> buscarTodosEdificios()
    {
        List<Edificio> edificios = edificioRepository.findAll();
        List<EdificioView> edificioViews = new ArrayList<EdificioView>();
        
        for (Edificio edificio : edificios) {
            edificioViews.add(edificio.toView());
        }

        return edificioViews;
    }

    public Edificio buscarEdificioPorCodigo(int codigo)
    {
        return edificioRepository.getEdificioByCodigo(codigo);
    }

    public Edificio guardarEdificio(Edificio edificio)
    {
        edificio.setUnidades(new ArrayList<Unidad>());
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

    public boolean existeDireccion(String direccion) {
        return edificioRepository.existsByDireccion(direccion);
    }

    public boolean existeNombre(String nombre) {
        return edificioRepository.existsByNombre(nombre);
    }

	public void eliminarEdificio(int codigo) {
        edificioRepository.deleteByCodigo(codigo);
	}

    public void modificarEdificio(@Valid EdificioView edificioView) {
        Edificio edificio = edificioRepository.getEdificioByCodigo(edificioView.getCodigo());
        edificio.updateEdificio(edificioView);
        edificioRepository.save(edificio);
    }
}
