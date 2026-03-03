package bob;

public class AddCommand extends Command {
    private Task taskToAdd;
    
    public AddCommand(Task task) {
        this.taskToAdd = task;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        tasks.addTask(taskToAdd);
        ui.showTaskAdded(taskToAdd, tasks.size());
        storage.save(tasks.getAllTasks());
    }
}
