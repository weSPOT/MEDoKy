package at.tugraz.kmi.medokyservice.datapreprocessing;

public class Badge {
	String connotation;
	String critiria;
	String issuer;
	String description;
	String name;
	
	public Badge(){}
	
	public Badge(String originalText){
		// FIXME: split the original text into object properties
		//System.err.println(originalText);
	}
	
	public String getConnotation() {
		return connotation;
	}
	public void setConnotation(String connotation) {
		this.connotation = connotation;
	}
	public String getCritiria() {
		return critiria;
	}
	public void setCritiria(String critiria) {
		this.critiria = critiria;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
