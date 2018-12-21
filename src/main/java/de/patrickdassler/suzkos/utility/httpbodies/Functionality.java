package de.patrickdassler.suzkos.utility.httpbodies;

public class Functionality {

	String lri;
	String name;
	
	public Functionality() {
		super();
	}

	public Functionality(String lri, String functionality, String name) {
		super();
		this.lri = lri;
		this.name = name;
	}
	
	public String getLri() {
		return lri;
	}
	public void setLri(String lri) {
		this.lri = lri;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
