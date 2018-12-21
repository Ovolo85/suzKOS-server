package de.patrickdassler.suzkos.utility.httpbodies;

public class SUZ_MA {

	private String id;
	private String name;
	private String mailAddress;
	private String phoneNumber;
	
	public SUZ_MA() {
	}
	
	public SUZ_MA(String id, String name, String mailAddress, String phoneNumber) {
		//super();
		this.id = id;
		this.name = name;
		this.mailAddress = mailAddress;
		this.phoneNumber = phoneNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
