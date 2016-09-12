package commandPat;

import java.util.Stack;

/**
 * Created by HCH on 01-Jun-16.
 */
public class CommandManager {
    private final Stack<Command> commandList = new Stack<>();
    private final Stack<Command> undoList = new Stack<>();

    public void newCommand(Command cmd) {
        cmd.execute();
        commandList.push(cmd);
        undoList.removeAllElements();
    }

    public void undoCommand() {
        if (!commandList.isEmpty()) {
            Command lastCommand = commandList.pop();
            lastCommand.undo();
            undoList.push(lastCommand);
        }
    }

    public void redoCommand() {
        if (!undoList.isEmpty()) {
            Command redoneCommand = undoList.pop();
            redoneCommand.execute();
            commandList.push(redoneCommand);
        }
    }
}