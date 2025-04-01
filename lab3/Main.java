// Mann Patel - 22BCP107

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Check if any arguments are provided
        if (args.length == 0) {
            System.out.println("Usage: java Main (number of processes) [filename of commands]");
            return;
        }

        // Get the number of processes
        int length = Integer.parseInt(args[0]); 
        String input;

        try {
            int n = Integer.parseInt(args[0]);
            LamportClock[] clocks = new LamportClock[n];

            // Initialize and start each LamportClock process
            for (int i = 0; i < n; ++i) {
                LamportClock lc = new LamportClock(i);
                lc.start();
                clocks[i] = lc;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                input = in.readLine();

                // Split the input into commands and arguments
                String[] splits = input.split(" ");
                if (splits.length == 0) {
                    continue;
                }

                switch (splits[0].toUpperCase()) {

                    // Handle SEND event
                    case "SEND":
                        int senderId = Integer.parseInt(splits[1]);
                        int receiverId = Integer.parseInt(splits[2]);

                        long firstProcessId = clocks[senderId].threadId();
                        long secondProcessId = clocks[receiverId].threadId();
                        String messageContent = "";

                        // If a message is provided, concatenate it
                        if (splits.length >= 4) {
                            List<String> wordsList = Arrays.asList(
                                    Arrays.copyOfRange(splits, 3, splits.length));
                            messageContent = String.join(" ", wordsList);
                        }

                        // Send event on sender's clock
                        Event sendEvent = new Event(1, firstProcessId, secondProcessId, "");
                        clocks[senderId].updateTime(sendEvent);

                        // Receive event on receiver's clock
                        Event receiveEvent = new Event(2, firstProcessId, secondProcessId, clocks[senderId].getTime(), messageContent);
                        clocks[receiverId].updateTime(receiveEvent);
                        break;

                    // Handle LOCAL event
                    case "LOCAL":
                        int clockArrayId = Integer.parseInt(splits[1]);
                        long processId = clocks[clockArrayId].threadId();
                        String localMessageContent = "";

                        Event localEvent = new Event(0, processId, 0, localMessageContent);
                        clocks[clockArrayId].updateTime(localEvent);
                        break;

                    // Handle EXIT command, print all local times, and exit
                    case "EXIT":
                        for (int i = 0; i < length; i++) {
                            System.out.println("Local Time of process " + i + " is " + clocks[i].getTime() + ".");
                        }
                        System.exit(0);
                        break;

                    // Handle invalid command
                    default:
                        System.out.println("Invalid event name");
                }
            }
        } catch (Exception e) {
            // Print exception details if an error occurs
            System.err.println(e);
            return;
        }
    }
}