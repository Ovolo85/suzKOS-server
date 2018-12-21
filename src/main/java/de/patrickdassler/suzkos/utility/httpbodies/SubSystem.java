package de.patrickdassler.suzkos.utility.httpbodies;

public class SubSystem {

	String name;
	String parent;
	
	public SubSystem() {
		
	}
	
	public SubSystem(String name, String parent) {
		super();
		this.name = name;
		this.parent = parent;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	
}
