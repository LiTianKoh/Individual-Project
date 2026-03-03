package bob;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) {
        return tasks.remove(index - 1); // Convert 1-based to 0-based
    }

    public Task getTask(int index) {
        return tasks.get(index - 1); // Convert 1-based to 0-based
    }

    public void markTask(int index, boolean isDone) {
        Task task = tasks.get(index - 1); // Convert 1-based to 0-based
        task.setDone(isDone);
    }

    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                matches.add(task);
            }
        }
        return matches;
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}