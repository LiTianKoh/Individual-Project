package bob;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Save {
    private static final String DATA_DIR = "data";
    private static final String DATA_FILE = "bob.txt";
    private static final String FILE_PATH = DATA_DIR + File.separator + DATA_FILE;
    private static final String HEADER = "===================\nTaskings\n===================";

    /**
     * Creates data directory and file if they don't exist
     */
    public static void initializeDataFile() throws IOException {
        Path dataDir = Paths.get(DATA_DIR);
        Path dataFile = Paths.get(FILE_PATH);

        // Create directory if it doesn't exist
        if (!Files.exists(dataDir)) {
            Files.createDirectory(dataDir);
            System.out.println("Created data directory");
        }

        // Create file if it doesn't exist
        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
            // Write header to new file
            FileWriter writer = new FileWriter(FILE_PATH);
            writer.write(HEADER + System.lineSeparator());
            writer.close();
            System.out.println("Created new save file");
        }
    }

    /**
     * LOADS tasks from the save file
     * This is what you're asking about!
     */
    public static ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        // If file doesn't exist, return empty list
        if (!file.exists()) {
            System.out.println("No save file found, starting with empty list");
            return tasks;
        }

        System.out.println("Loading tasks from: " + FILE_PATH);
        Scanner scanner = new Scanner(file);
        int lineNumber = 0;
        boolean headerPassed = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            lineNumber++;

            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }

            // Skip header lines
            if (line.equals("===================") || line.equals("Taskings")) {
                continue;
            }

            // Once we've passed the header section, parse tasks
            try {
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                    System.out.println("Loaded: " + task); // Debug print
                }
            } catch (Exception e) {
                System.out.println("Warning: Skipping corrupted line " + lineNumber + ": " + line);
            }
        }
        scanner.close();

        System.out.println("Successfully loaded " + tasks.size() + " tasks");
        return tasks;
    }

    /**
     * Saves tasks to the file
     */
    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        System.out.println("Saving " + tasks.size() + " tasks to " + FILE_PATH);
        FileWriter writer = new FileWriter(FILE_PATH);

        // Write header
        writer.write(HEADER + System.lineSeparator());

        // Write each task
        for (Task task : tasks) {
            String line = taskToFileString(task);
            writer.write(line + System.lineSeparator());
            System.out.println("Saved: " + line); // Debug print
        }

        writer.close();
        System.out.println("Save complete!");
    }

    /**
     * Converts a Task to string format for saving
     */
    private static String taskToFileString(Task task) {
        String status = task.isDone() ? "[✓]" : "[ ]";

        if (task instanceof Todo) {
            return String.format("T | %s | %s",
                    status,
                    task.getDescription());

        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return String.format("D | %s | %s | %s",
                    status,
                    deadline.getDescription(),
                    deadline.getBy());

        } else if (task instanceof Event) {
            Event event = (Event) task;
            return String.format("E | %s | %s | %s to %s",
                    status,
                    event.getDescription(),
                    event.getStart(),
                    event.getEnd());
        }

        return "";
    }

    /**
     * Parses a line from the save file into a Task object
     * This is the KEY method for loading!
     */
    private static Task parseTaskFromLine(String line) {
        // Split by " | " (space-pipe-space)
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid format: " + line);
        }

        String type = parts[0];
        String statusStr = parts[1];
        boolean isDone = statusStr.equals("[✓]");

        switch (type) {
        case "T": // Todo
            if (parts.length >= 3) {
                Todo todo = new Todo(parts[2]);
                todo.setDone(isDone);
                return todo;
            }
            break;

        case "D": // Deadline
            if (parts.length >= 4) {
                Deadline deadline = new Deadline(parts[2], parts[3]);
                deadline.setDone(isDone);
                return deadline;
            }
            break;

        case "E": // Event
            if (parts.length >= 4) {
                // Handle "start to end" format
                String period = parts[3];
                String[] timeParts = period.split(" to ");
                if (timeParts.length >= 2) {
                    Event event = new Event(parts[2], timeParts[0], timeParts[1]);
                    event.setDone(isDone);
                    return event;
                }
            }
            break;

        default:
            throw new IllegalArgumentException("Unknown task type: " + type);
        }

        return null;
    }
}