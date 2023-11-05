package ar.edu.uade.tpoapi.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.tpoapi.controlador.request.Unidad.CreateUnidadDTO;
import ar.edu.uade.tpoapi.controlador.request.Unidad.TransferirUnidadDTO;
import ar.edu.uade.tpoapi.controlador.request.Unidad.UnidadDTO;
import ar.edu.uade.tpoapi.exceptions.UnidadException;
import ar.edu.uade.tpoapi.modelo.Edificio;
import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.repository.EdificioRepository;
import ar.edu.uade.tpoapi.repository.PersonaRepository;
import ar.edu.uade.tpoapi.repository.UnidadRepository;
import ar.edu.uade.tpoapi.views.PersonaView;

@Service
public class UnidadService {
    @Autowired
    UnidadRepository unidadRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    EdificioRepository edificioRepository;

    public boolean existeUnidad(int identificador){
        return unidadRepository.existsById(identificador);
    }

    public boolean existeUnidad(CreateUnidadDTO createUnidadDTO){
        return unidadRepository.existsByEdificioCodigoAndPisoAndNumero(createUnidadDTO.getCodigo(), createUnidadDTO.getPiso(), createUnidadDTO.getNumero());
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
    public String transferirUnidad(TransferirUnidadDTO transferirUnidadDTO){

        Persona persona = personaRepository.findByDocumento(transferirUnidadDTO.getDocumento()).orElse(null);

        if(persona != null){
          Unidad unidad = unidadRepository.findById(transferirUnidadDTO.getIdentificador()).orElse(null);
          if(unidad != null){
              unidad.transferir(persona);
              return "Unidad transferida";
          }
          else {
              return "No se ha encontrado una unidad con ese codigo";
          }
        }
        return "No se ha encontrado la persona";
    }

    public Unidad crearUnidad(CreateUnidadDTO createUnidadDTO) {
        Edificio edificio = edificioRepository.findById(createUnidadDTO.getCodigo()).orElse(null);
        if(edificio != null){
            Unidad unidad = Unidad.builder().edificio(edificio).numero(createUnidadDTO.getNumero()).piso(createUnidadDTO.getPiso()).build();
            return unidadRepository.save(unidad);
        }
        return null;
    }

    public String alquilarUnidad(UnidadDTO unidadDTO) throws UnidadException {
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        Persona persona = personaRepository.findByDocumento(unidadDTO.getDocumento()).orElse(null);
        if(persona != null){
            unidad.alquilar(persona);
            return "Unidad alquilada";
        }
        return "No se ha encontrado la persona";
    }

    public String liberarUnidad(int identificador)  throws UnidadException{
        Unidad unidad = buscarUnidad(identificador);
        if(unidad == null)
            return "No se ha encontrado la unidad";
        unidad.liberar();
        return "Unidad liberada";
    }

    public String habitarUnidad(int identificador) throws UnidadException {
        Unidad unidad = buscarUnidad(identificador);
        if(unidad == null)
            return "No se ha encontrado la unidad";
        unidad.habitar();
        return "Unidad habitada";
    }

	public String eliminarUnidad(int identificador) throws UnidadException{
		Unidad unidad = unidadRepository.findById(identificador).orElse(null);
        if(unidad == null)
            return "No se ha encontrado la unidad";
        unidadRepository.delete(unidad);
        if(unidadRepository.findById(identificador).orElse(null) == null)
            return "Unidad eliminada";
        else
            return "No se pudo eliminar la unidad";
	}

    public String agregarDuenioUnidad(UnidadDTO unidadDTO) {
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        if(unidad == null)
            return "No se ha encontrado la unidad";
        Persona persona = personaRepository.findByDocumento(unidadDTO.getDocumento()).orElse(null);
        if(persona != null){
            unidad.agregarDuenio(persona);
            return "Duenio agregado";
        }
        return "No se ha encontrado la persona";
    }

    public String agregarInquilinoUnidad(UnidadDTO unidadDTO) {
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        if(unidad == null)
            return "No se ha encontrado la unidad";
        Persona persona = personaRepository.findByDocumento(unidadDTO.getDocumento()).orElse(null);
        if(persona != null){
            unidad.agregarInquilino(persona);
            return "Inquilino agregado";
        }
        return "No se ha encontrado la persona";
    }

    public Set<PersonaView> dueniosPorUnidad(UnidadDTO unidadDTO) {
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        if(unidad == null)
            return null;
        List<Persona> duenios = unidad.getDuenios();
        Set<PersonaView> resultado = new HashSet<>();
        for(Persona persona : duenios)
            resultado.add(persona.toView());
        return resultado;        
    }

    public Set<PersonaView> inquilinosPorUnidad(UnidadDTO unidadDTO){
        Unidad unidad = buscarUnidad(unidadDTO.getCodigo(),unidadDTO.getPiso(),unidadDTO.getNumero());
        if(unidad == null){
            return null;
        }else{
            List<Persona> inquilinos = unidad.getInquilinos();
            Set<PersonaView> resultado = new HashSet<>();
            for (Persona persona : inquilinos){
                resultado.add(persona.toView());
            }
            return resultado;
        }
    }    
}
