import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class Bob {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>(); //Initialise dynamic array
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
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println("    " + (i + 1) + ". " + tasks.get(i));
                        }
                    }
                    System.out.println("    ___________________________");
                    continue;
                }
                //Check if the line input consist of handsome or beautiful
                else if (pattern.matcher(line).find()) {
                    System.out.println("    Nonono, you are ;)");
                    System.out.println("    ___________________________");
                }
                //Otherwise, add a task
                else {
                    tasks.add(line);
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