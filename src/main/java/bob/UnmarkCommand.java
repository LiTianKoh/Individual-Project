package bob;

public class UnmarkCommand extends Command {
    private int taskNumber;
    
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException(String.format(
                BobExceptions.TASK_NUMBER_NOT_EXIST, taskNumber));
        }
        
        tasks.markTask(taskNumber, false);
        ui.showTaskMarked(tasks.getTask(taskNumber), taskNumber, false);
        storage.save(tasks.getAllTasks());
    }
}
