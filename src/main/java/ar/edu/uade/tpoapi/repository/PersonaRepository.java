package ar.edu.uade.tpoapi.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.uade.tpoapi.modelo.Persona;
import ar.edu.uade.tpoapi.modelo.Enumerations.Rol;

public interface PersonaRepository extends JpaRepository<Persona, String>{

    public Optional<Persona> findByDocumento(String documento);
    public void deleteByDocumento(String documento);
    public boolean existsByDocumento(String documento);
    public Persona getByDocumento(String documento);
    public boolean existsByMail(String mail);
    public Optional<Persona> findByMail(String mail);
    public ArrayList<Persona> findByRol(Rol rol);

    //Crea un sql para un reporte de usuarios
    @Query(value = "SELECT (SELECT COUNT(documento) FROM personas WHERE cuenta_verificado = 1 AND contrasenia IS NOT NULL) AS registeredUsers, (SELECT COUNT(documento) FROM personas WHERE cuenta_verificado = 1 AND contrasenia IS NULL) AS confirmedNoPassword,\r\n" + //
            "\r\n" + //
            "\t (SELECT COUNT(documento)\r\n" + //
            "     FROM personas\r\n" + //
            "     WHERE cuenta_verificado = 0 AND contrasenia IS NULL) AS noConfirmNoPassword", nativeQuery = true)
    public Object getReporteUsuarios();
}
