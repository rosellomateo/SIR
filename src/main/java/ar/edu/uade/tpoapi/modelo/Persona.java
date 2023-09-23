package ar.edu.uade.tpoapi.modelo;

import ar.edu.uade.tpoapi.views.PersonaView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "personas")
public class Persona {
    @Id
    private String documento;
    @NotBlank
    @Size(min = 1,max = 100)
    private String nombre;
    @Email
    @NotBlank
    @Size(min = 1,max = 100)
    private String mail;
    @Column(name = "contrasenia")
    @NotBlank
    private String password;

    public Persona(String documento, String nombre, String mail, String password) {
        this.documento = documento;
        this.nombre = nombre;
        this.mail = mail;
        this.password = password;
    }

    public Persona() {
    }
    public Persona(String nombre, String mail, String password){
        this.nombre = nombre;
        this.mail = mail;
        this.password = password;
    }
    public void cambiarPassword(String password) {
        this.password = password;
    }
    public boolean validoParaRegistro(){
        return (this.mail == null && this.password == null);
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }


    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public PersonaView toView() {
        return new PersonaView(documento, nombre);
    }

    public String toString() {
        return "Documento: " + documento + " Nombre: " + nombre + " Mail: " + mail + " Password: " + password;
    }
}
