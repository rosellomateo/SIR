package ar.edu.uade.tpoapi.modelo;

import ar.edu.uade.tpoapi.views.ImagenView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "imagenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numero;
    @Column(name = "path")
    private String direccion;
    private String tipo;

    public String toString(){
        return "Imagen: " + this.numero + " - " + this.direccion + " - " + this.tipo;
    }
    
    public ImagenView toView() {
        return new ImagenView(this.numero, this.direccion, this.tipo);
    }
}
