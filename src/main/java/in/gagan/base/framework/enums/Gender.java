package in.gagan.base.framework.enums;

/**
 * This enum is used to represent the genders.
 * 
 * @author gaganthind
 *
 */
public enum Gender {
	
	M("Male"),
	F("Female"),
	O("Others"),
	NULL(null);
	
	private final String gender;
	
	Gender(String gender) {
		this.gender = gender;
	}
	
	public String getGender() {
		return this.gender;
	}

}
