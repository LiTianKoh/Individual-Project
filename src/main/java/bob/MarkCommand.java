package bob;

public class MarkCommand extends Command {
    private int taskNumber;
    
    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException(String.format(
                BobExceptions.TASK_NUMBER_NOT_EXIST, taskNumber));
        }
        
        tasks.markTask(taskNumber, true);
        ui.showTaskMarked(tasks.getTask(taskNumber), taskNumber, true);
        storage.save(tasks.getAllTasks());
    }
}
