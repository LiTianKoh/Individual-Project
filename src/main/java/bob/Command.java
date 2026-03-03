package bob;

/**
 * Represents an executable command in the Bob application.
 * All specific commands (List, Add, Mark, etc.) inherit from this class.
 * Commands follow the Command pattern to encapsulate request handling.
 */
public abstract class Command {

    /**
     * Executes the command, performing its specific operation on the task list,
     * interacting with the user interface, and saving changes to storage.
     *
     * @param tasks The task list to operate on
     * @param ui The user interface for displaying messages
     * @param storage The storage for saving/loading tasks
     * @throws Exception if any error occurs during execution
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws Exception;

    /**
     * Indicates whether this command should cause the application to exit.
     *
     * @return true if the application should exit after this command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}
