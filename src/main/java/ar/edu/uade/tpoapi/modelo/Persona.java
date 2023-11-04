package ar.edu.uade.tpoapi.modelo;

import java.util.Set;

import ar.edu.uade.tpoapi.modelo.Enumerations.Rol;
import ar.edu.uade.tpoapi.views.PersonaView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    /*
    @ManyToMany(fetch = FetchType.EAGER,targetEntity = Roles.class,cascade = CascadeType.PERSIST)
    @JoinTable(name = "personas_roles",joinColumns = @JoinColumn(name = "documento"),inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Roles roles;
    */
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private String tokenVerificacion;
    @Default private boolean cuentaVerificado = false;

    public boolean validoParaRegistro(){
        return (this.mail == null && this.password == null);
    }

    public PersonaView toView() {
        return new PersonaView(documento, nombre);
    }

    public String toString() {
        return "Documento: " + documento + " Nombre: " + nombre + " Mail: " + mail + " Password: " + password;
    }

    public boolean validoParaRegistroPassword() {
        return (this.mail != null && this.password == null);
    }
}
