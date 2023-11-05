package ar.edu.uade.tpoapi.modelo;

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
@Table(name = "templates")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Templates {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String description;
	@Column(length = 1000000)
	private String content;
	
	private String vars;

	public String toString() {
		return "Templates(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", content=" + this.getContent() + ", vars=" + this.getVars() + ")";
	}
}
