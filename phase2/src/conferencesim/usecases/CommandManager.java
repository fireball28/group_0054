package conferencesim.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import conferencesim.entities.Command;

public class CommandManager {
	
	private List<Command> commandList;
	
	public CommandManager(List<String> permList) {
		this.commandList = new ArrayList<>();
		for (String perm : permList) {
			this.commandList.add(new Command(perm.split(" ")[0], perm));
		}
	}
	
	public void addCommand(Command cmd) {
		this.commandList.add(cmd);
	}
	
	public Command getCommand(String command) {
		return commandList.stream().filter(c -> c.getNameRepresentation().equalsIgnoreCase(command)).findFirst().orElse(null);
	}
	
	public List<String> getCommandList() {
		return commandList.stream().map(c -> c.getNameRepresentation()).collect(Collectors.toList());
	}
	
	public String getCommandDocumentation(String command) {
		Command cmd = commandList.stream().filter(c -> c.getNameRepresentation().equalsIgnoreCase(command)).findFirst().orElse(null);
		return (cmd != null) ? cmd.toString() : null;
	}
	
	public String getCompleteDocumentation() {
		String doc = String.join("\n", commandList.stream().map(c -> c.toString()).collect(Collectors.toList()));
		return doc;
	}
	
	public String getFilteredDocumentation(String command) {
		List<String> applicables = commandList.stream().filter(c -> c.getNameRepresentation().toLowerCase().startsWith(command.toLowerCase())).map(c -> c.toString()).collect(Collectors.toList());
		String doc = String.join("\n", applicables);
		return doc;
	}
}
