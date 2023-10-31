package ar.edu.uade.tpoapi.views;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SendRequest {

	private String to;
	
	private String subject;
	
	private Integer template;
	
	private List<MetaData> metaData;

	public List<MetaData> getMetaData() {
		return metaData;
	}

	public void setMetaData(List<MetaData> metaData) {
		this.metaData = metaData;
	}

}
