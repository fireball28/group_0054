package conferencesim.entities;

public class Command {
	
	private String name;
	private String documentation;
	
	public Command(String name, String documentation) {
		this.name = name;
		this.documentation = documentation;
	}
	
	public String getNameRepresentation() {
		return name;
	}
	
	@Override
	public String toString() {
		return documentation;
	}
}
