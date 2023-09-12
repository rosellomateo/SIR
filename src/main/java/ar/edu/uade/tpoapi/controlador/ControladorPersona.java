package ar.edu.uade.tpoapi.controlador;

import ar.edu.uade.tpoapi.exceptions.PersonaException;
import ar.edu.uade.tpoapi.modelo.Persona;

public class ControladorPersona {
    
    private static ControladorPersona instancia;

    private ControladorPersona() { }

    public static ControladorPersona getInstancia() {
        if(instancia == null)
            instancia = new ControladorPersona();
        return instancia;
    }

    protected Persona buscarPersona(String documento) throws PersonaException {
        return null;
    }

    public void agregarPersona(String documento, String nombre)  throws PersonaException{
        Persona persona = new Persona(documento, nombre, null, null);
        //guardar el objeto
    }

    public void eliminarPersona(String documento) throws PersonaException {
        Persona persona = buscarPersona(documento);
        //eliminar el objeto
    }

    private void modificarPersona(Persona persona) throws PersonaException {
        //modificar el objeto
    }

    public boolean validoParaRegistro(String documento) throws PersonaException {
        Persona p = buscarPersona(documento);
        if(p == null)
            return false;
        else{
            if(p.getMail() == null && p.getPassword() == null)
                return true;
            else
                return false;
        }
    }

    public void registrar(Persona p) throws PersonaException{
        if (validoParaRegistro(p.getDocumento())){
            modificarPersona(p);
        }
        else{
            throw new PersonaException("No se puede registrar");
        }
    }

    public Boolean Logear(String documento, String mail, String password) throws PersonaException{

        Persona p = buscarPersona(documento);
        if(p == null)
            throw new PersonaException("No existe la persona");
        else{
            if(p.getMail() == mail && p.getPassword() == password)
                return true;
            else
                return false;
        }
    }

    
}
