package bob;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String DATA_DIR = "data";
    private static final String HEADER = "===================\nTaskings\n===================";
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void initialize() throws IOException {
        Path dataDir = Paths.get(DATA_DIR);
        Path dataFile = Paths.get(filePath);

        // Create directory if it doesn't exist
        if (!Files.exists(dataDir)) {
            Files.createDirectory(dataDir);
        }

        // Create file if it doesn't exist
        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
            FileWriter writer = new FileWriter(filePath);
            writer.write(HEADER + System.lineSeparator());
            writer.close();
        }
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty() || line.equals("===================") || line.equals("Taskings")) {
                continue;
            }

            try {
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            } catch (Exception e) {
                // Skip corrupted lines
            }
        }
        scanner.close();
        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        FileWriter writer = new FileWriter(filePath);

        writer.write(HEADER + System.lineSeparator());

        for (Task task : tasks) {
            String line = taskToFileString(task);
            writer.write(line + System.lineSeparator());
        }

        writer.close();
    }

    private String taskToFileString(Task task) {
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

    private Task parseTaskFromLine(String line) {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("[✓]");

        switch (type) {
        case "T":
            if (parts.length >= 3) {
                Todo todo = new Todo(parts[2]);
                todo.setDone(isDone);
                return todo;
            }
            break;

        case "D":
            if (parts.length >= 4) {
                Deadline deadline = new Deadline(parts[2], parts[3]);
                deadline.setDone(isDone);
                return deadline;
            }
            break;

        case "E":
            if (parts.length >= 4) {
                String period = parts[3];
                String[] timeParts = period.split(" to ");
                if (timeParts.length >= 2) {
                    Event event = new Event(parts[2], timeParts[0], timeParts[1]);
                    event.setDone(isDone);
                    return event;
                }
            }
            break;
        }

        return null;
    }
}