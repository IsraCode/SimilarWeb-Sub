import api.Query;
import api.QueryProcessor;

import java.util.Scanner;


class Runner {

    public static void main(String[] args) {
        QueryProcessor queryProcessor = QueryProcessor.getInstance();
        String line;
        Scanner scanner = new Scanner(System.in);
        System.out.println("write function name and parameter");
        System.out.println("Example: Num_sessions www.s_1.com");
        System.out.println("Example: Median_session_length www.s_1.com");
        System.out.println("Example: Num_unique_visited_sites visitor_1");

        while (scanner.hasNextLine() &&
                !(line = scanner.nextLine()).equals("exit")) {
            String[] cmdArgs = line.split(" ");
            if (cmdArgs[0].equals("exit"))
                System.exit(0);
            else if (cmdArgs.length != 2)
                System.out.println("wrong input");
            else {
                String functionName = cmdArgs[0];
                String parameter = cmdArgs[1];
                Query handler = queryProcessor.handler(functionName, parameter);
                handler.printAns();
            }
        }
        scanner.close();
        System.exit(0);

    }


}
