package ar.edu.uade.tpoapi.modelo;

import ar.edu.uade.tpoapi.modelo.Enumerations.Rol;
import ar.edu.uade.tpoapi.views.PersonaView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity
@Table(name = "personas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona {
    @Id
    private String documento;
    private String nombre;
    @Email
    private String mail;
    @Column(name = "contrasenia")
    private String password;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private String tokenVerificacion;
    @Default private boolean cuentaVerificado = false;

    public boolean validoParaRegistroMail(){
        return !this.cuentaVerificado;
    }

    public PersonaView toView() {
        return new PersonaView(documento, nombre);
    }

    public String toString() {
        return "Documento: " + documento + " Nombre: " + nombre + " Mail: " + mail + " Password: " + password;
    }

    public boolean validoParaRegistroPassword() {
        return (this.cuentaVerificado && this.password == null);
    }
}
