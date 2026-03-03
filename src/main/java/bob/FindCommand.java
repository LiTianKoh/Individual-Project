package bob;

import java.util.ArrayList;
/*
Class to hand find command
 */
public class FindCommand extends Command {
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matches = tasks.findTasks(keyword);
        ui.showMatchingTasks(matches, keyword);
    }
}