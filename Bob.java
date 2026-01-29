import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class Bob {
    // Inner class to represent a task with status
    static class Task {
        String description;
        boolean isDone;

        Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        String getStatusIcon() {
            return isDone ? "[X]" : "[ ]";
        }

        @Override //
        public String toString() {
            return getStatusIcon() + " " + description;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>(); //Initialise dynamic array
        String line;

        String logo =
                " ______   _______   ______ \n"
                        + "|  __  \\ |  ___  | |  __  \\ \n"
                        + "| |__)  )| |   | | | |__)  )\n"
                        + "|  __  ( | |   | | |  __  ( \n"
                        + "| |__)  )| |___| | | |__)  )\n"
                        + "|______/ |_______| |______/ \n";
        System.out.println("Hello from\n" + logo);
        System.out.println("    ___________________________");
        System.out.println("    Hello! I'm BOB");
        System.out.println("    What can I do for you?");
        System.out.println("    ___________________________");

        //Word recognition
        Pattern pattern = Pattern.compile("handsome|beautiful", Pattern.CASE_INSENSITIVE);

        do {
            line = in.nextLine();
            System.out.println("    ___________________________");

            //Check if it's not Bye
            if (!line.equalsIgnoreCase("Bye")) {
                //Lists out the tasks when list is inputted
                if (line.equalsIgnoreCase("list")) {
                    if (tasks.isEmpty()) {
                        System.out.println("    No tasks in your list.");
                    } else {
                        System.out.println("    Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println("    " + (i + 1) + "." + tasks.get(i));
                        }
                    }
                    System.out.println("    ___________________________");
                    continue;
                }

                //Handle mark command
                if (line.toLowerCase().startsWith("mark ")) {
                    try  {
                        String[] parts = line.split(" ");
                        int taskNum = Integer.parseInt(parts[1]); //Convert the string digit to int digits

                        //Check if the task number is within the listed range
                        if (taskNum >= 1 && taskNum <= tasks.size()) {
                            Task task = tasks.get(taskNum - 1);  // -1 because of the indexing
                            task.isDone = true; //Add [X]
                            System.out.println("    Nice! I've marked this task as done: ");
                            System.out.println("    " + taskNum + "." + task.toString());
                        } else {
                            System.out.println("    Error: Task number " + taskNum + "does not exist.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("    Error: Please specify a valid task number.");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("    Error: Please specify a task number.");
                    }
                    System.out.println("    ___________________________");
                    continue;
                }

                //Handle unmark command
                if (line.toLowerCase().startsWith("unmark ")) {
                    try  {
                        String[] parts = line.split(" ");
                        int taskNum = Integer.parseInt(parts[1]); //Convert the string digit to int digits

                        //Check if the task number is within the listed range
                        if (taskNum >= 1 && taskNum <= tasks.size()) {
                            Task task = tasks.get(taskNum - 1);  // -1 because of the indexing
                            task.isDone = false; //Add [ ]
                            System.out.println("    Ok, I've marked this task as not done yet: ");
                            System.out.println("    " + taskNum + "." + task.toString());
                        } else {
                            System.out.println("    Error: Task number " + taskNum + "does not exist.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("    Error: Please specify a valid task number.");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("    Error: Please specify a task number.");
                    }
                    System.out.println("    ___________________________");
                    continue;
                }

                //Added personality: Check if the line input consist of handsome or beautiful
                else if (pattern.matcher(line).find()) {
                    System.out.println("    Nonono, you are ;)");
                    System.out.println("    ___________________________");
                }
                //Otherwise, add a task
                else {
                    tasks.add(new Task(line));
                    System.out.println("    " + "added: " + line);
                    System.out.println("    ___________________________");
                }
            }
        } while (!line.toLowerCase().contains("bye")); //Lines containing bye
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ___________________________");

        in.close();
    }
}