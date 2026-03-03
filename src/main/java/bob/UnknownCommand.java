package bob;

import java.util.regex.Pattern;

public class UnknownCommand extends Command {
    private String input;
    private static final Pattern COMPLIMENT_PATTERN =
            Pattern.compile("handsome|beautiful", Pattern.CASE_INSENSITIVE);
    
    public UnknownCommand(String input) {
        this.input = input;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        if (COMPLIMENT_PATTERN.matcher(input).find()) {
            ui.showComplimentResponse();
        } else {
            Task genericTask = new Task(input);
            tasks.addTask(genericTask);
            ui.showMessage("added: " + input);
            ui.showLine();
            storage.save(tasks.getAllTasks());
        }
    }
}
