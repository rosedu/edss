package edss.model.commands;


public interface Command {
	public void execute();
	public void reExecute();
	public void unExecute();
}
