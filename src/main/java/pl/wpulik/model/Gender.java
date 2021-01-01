package pl.wpulik.model;

public enum Gender {
	
	MALE("mężczyzna"), 
	FEMALE("kobieta"), 
	WHO_CARES("Kogo to?");
	
	private final String displayValue;
	
	private Gender(String displayValue) {
		this.displayValue = displayValue;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}
}
