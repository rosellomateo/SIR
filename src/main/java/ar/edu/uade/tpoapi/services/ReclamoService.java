package ar.edu.uade.tpoapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.tpoapi.controlador.request.Reclamo.UnidadDTO;
import ar.edu.uade.tpoapi.modelo.Reclamo;
import ar.edu.uade.tpoapi.modelo.Unidad;
import ar.edu.uade.tpoapi.repository.ReclamoRepository;
import ar.edu.uade.tpoapi.repository.UnidadRepository;
import ar.edu.uade.tpoapi.views.ReclamoView;

@Service
public class ReclamoService {
    @Autowired
    ReclamoRepository reclamoRepository;
    @Autowired
    UnidadRepository unidadRepository;

    public List<ReclamoView> reclamosPorEdificio(int codigo) {
        List<Reclamo> reclamos = reclamoRepository.findByEdificioCodigo(codigo);
        List<ReclamoView> resultado = new ArrayList<ReclamoView>();

        for (Reclamo reclamo : reclamos) {
            resultado.add(reclamo.toView());
        }
        return resultado;
    }

    public List<ReclamoView> reclamosPorUnidad(UnidadDTO unidadDTO) {
        Unidad unidad = unidadRepository.findByEdificioCodigoAndPisoAndNumero(unidadDTO.getCodigo(), unidadDTO.getPiso(), unidadDTO.getNumero());
        List<Reclamo> reclamos = reclamoRepository.findByUnidadId(unidad.getId());
        List<ReclamoView> resultado = new ArrayList<ReclamoView>();

        for (Reclamo reclamo : reclamos) {
            resultado.add(reclamo.toView());
        }
        return resultado;
    }

    public List<ReclamoView> reclamosPorPersona(String documento) {
        List<Reclamo> reclamos = reclamoRepository.findByPersonaDocumento(documento);
        List<ReclamoView> resultado = new ArrayList<ReclamoView>();

        for (Reclamo reclamo : reclamos) {
            resultado.add(reclamo.toView());
        }
        return resultado;
    }

    public Reclamo agregarReclamo(Reclamo reclamo) {
        return reclamoRepository.save(reclamo);
    }

    public void agregarImagenAReclamo(Reclamo reclamo) {
        reclamoRepository.save(reclamo);
    }

    public void ActualizarEstado(Reclamo reclamo) {
        reclamoRepository.save(reclamo);

    }

    public Reclamo buscarReclamo(int numero) {
        return reclamoRepository.findById(numero).orElse(null);
    }

}
