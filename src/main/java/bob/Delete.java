package bob;

import java.util.ArrayList;

public class Delete {
    private static final String INDENT = "    ";
    private static final String SEPARATOR = "    ___________________________";

    /**
     * Handles the delete command - removes a task from the list
     * @param tasks The list of tasks
     * @param taskNum The task number to delete (1-based indexing)
     */
    public static void handleDelete(ArrayList<Task> tasks, int taskNum) {
        // Validate task number exists
        BobExceptions.validateTaskNumber(taskNum, tasks.size());

        // Get the task before removing (for display)
        Task removedTask = tasks.get(taskNum - 1);

        // Remove the task (adjusting for 0-based indexing)
        tasks.remove(taskNum - 1);

        // Print confirmation
        System.out.println(INDENT + "Noted. I've removed this task:");
        System.out.println(INDENT + removedTask.toString());
        System.out.println(INDENT + "Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }
}