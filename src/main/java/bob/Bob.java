package bob;

public class Bob {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private Parser parser;

    public Bob(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();

        try {
            storage.initialize();
            tasks = new TaskList(storage.load());
            ui.showMessage("Loaded " + tasks.size() + " tasks from save file.");
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();

                Command command = parser.parseCommand(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();

            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Bob("data/bob.txt").run();
    }
}