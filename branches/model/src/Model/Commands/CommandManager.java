package Model.Commands;

import java.util.Stack;


public class CommandManager {
	private Stack<Command> undoStack;
	private Stack<Command> redoStack;
	
	public CommandManager() {
		undoStack = new Stack<Command>();
		redoStack = new Stack<Command>();
	}
	
	public void doCommand(Command com) {
		com.execute();
		undoStack.push(com);
		redoStack.clear();
	}
	
	public void undo() {
		Command com = undoStack.pop();
		com.unExecute();
		redoStack.push(com);
	}
	
	public void redo() {
		Command com = redoStack.pop();
		com.execute();
		undoStack.push(com);
	}
}
