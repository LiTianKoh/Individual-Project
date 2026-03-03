package bob;

public abstract class Command {
    
    /**
     * Executes the command
     * @param tasks The task list
     * @param ui The UI for user interaction
     * @param storage The storage for saving/loading
     * @throws Exception if execution fails
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws Exception;
    
    /**
     * @return true if this command should exit the program
     */
    public boolean isExit() {
        return false;
    }
}
