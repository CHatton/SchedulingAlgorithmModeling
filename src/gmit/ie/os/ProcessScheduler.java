package gmit.ie.os;

import gmit.ie.os.scheduling.FirstComeFirstServed;
import gmit.ie.os.scheduling.NullSchedulingAlgorithm;
import gmit.ie.os.scheduling.RoundRobin;
import gmit.ie.os.scheduling.SchedulingAlgorithm;
import gmit.ie.os.scheduling.ShortestJobFirst;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcessScheduler {

    private final Scanner sc; // read in user input
    private List<MyProcess> processes; // the list of processes that will be operated on by the scheduling algorithms.
    private boolean keepRunning; // flag to keep the program running.

    public ProcessScheduler() {
        processes = new ArrayList<>();
        sc = new Scanner(System.in);
        keepRunning = true;
    }

    private int promptForInt(String prompt) {
        System.out.println(prompt);
        int num = sc.nextInt();
        sc.nextLine(); // swallow new line character
        return num;
    }


    private void fillProcesses() {
        processes.clear(); // remove all existing processes.
        System.out.println("Enter process names and burst times. (type X when you're done adding processes.)");
        while (true) {
            System.out.print("Enter process name: ");
            String name = sc.nextLine();
            if (name.equalsIgnoreCase("X")) {
                // done adding processes.
                return;
            }
            int burstTime = promptForInt("Enter process burst time. ");

            // the process will be operated on by the algorithm.
            processes.add(new MyProcess(name, burstTime));
        }
    }

    private SchedulingAlgorithm chooseAlgorithm() {
        System.out.println("Choose the scheduling algorithm you want to use.");
        System.out.println("1 - First Come First Served (FCFS)");
        System.out.println("2 - Shorted Job First (SJF)");
        System.out.println("3 - Round Robin (RR)");
        System.out.println("4 - Exit.");
        int choice = promptForInt("");
        while (true){
            switch (choice) {
                case 1:
                    return new FirstComeFirstServed(processes);
                case 2:
                    return new ShortestJobFirst(processes);
                case 3:
                    int quantum = promptForInt("Enter quantum for Round Robin.");
                    return new RoundRobin(processes, quantum);
                case 4:
                    // represents "no" algorithm chosen, instead of using null
                    // use a valid implementation of SchedulingAlgorithm that "does nothing"
                    keepRunning = false;
                    return new NullSchedulingAlgorithm();
            }
        }
    }

    public void start() {
        fillProcesses();
        SchedulingAlgorithm algorithm = chooseAlgorithm();
        if (keepRunning) {
            System.out.println("=========================================================");
            System.out.println(String.format("%-15s %-15s %-15s %-15s\n",
                    "Process Name", "Start Time", "Remaining Time", "Wait Time"));
            algorithm.execute().forEach(System.out::println);
            List<Integer> averageWaitTimes = algorithm.getProcessWaitTimes();
            
            System.out.println();
            // the index "i" lines up the processes with the averages.
            for(int i = 0; i < processes.size(); i++){
            	System.out.println("Process: " + processes.get(i).getName() + " has average wait time of " + averageWaitTimes.get(i));
            }
            
            System.out.println();
            System.out.printf("Average wait time for %s: %.2f", algorithm.getName(), algorithm.averageWaitTime());
            System.out.println();
            System.out.println("=========================================================");
        }
    }
}
