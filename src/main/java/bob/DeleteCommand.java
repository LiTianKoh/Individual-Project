package bob;

public class DeleteCommand extends Command {
    private int taskNumber;
    
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException(String.format(
                BobExceptions.TASK_NUMBER_NOT_EXIST, taskNumber));
        }
        
        Task removed = tasks.deleteTask(taskNumber);
        ui.showTaskRemoved(removed, tasks.size());
        storage.save(tasks.getAllTasks());
    }
}
