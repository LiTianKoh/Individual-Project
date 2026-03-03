package bob;

/**
 * Represents a command to add a new task to the task list.
 * This command handles adding Todo, Deadline, and Event tasks.
 */
public class AddCommand extends Command {
    private Task taskToAdd;

    /**
     * Creates an add command for the specified task.
     *
     * @param task The task to be added to the list
     */
    public AddCommand(Task task) {
        this.taskToAdd = task;
    }

    /**
     * Executes the add command by adding the task to the list,
     * displaying a confirmation message, and saving the updated list.
     *
     * @param tasks The task list to add the task to
     * @param ui The user interface for displaying the confirmation
     * @param storage The storage for saving the updated task list
     * @throws Exception if an error occurs during saving
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        tasks.addTask(taskToAdd);
        ui.showTaskAdded(taskToAdd, tasks.size());
        storage.save(tasks.getAllTasks());
    }
}
