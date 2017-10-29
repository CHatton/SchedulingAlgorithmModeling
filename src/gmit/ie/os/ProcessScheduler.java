package gmit.ie.os;

import gmit.ie.os.scheduling.FirstComeFirstServed;
import gmit.ie.os.scheduling.RoundRobin;
import gmit.ie.os.scheduling.SchedulingAlgorithm;
import gmit.ie.os.scheduling.ShortestJobFirst;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcessScheduler {

    private final Scanner sc; // read in input
    private List<Process> processes; // the list of processes that will be operated on by the scheduling algorithms.

    public ProcessScheduler(Scanner sc) { // take scanner as parameter so you can also pass in a Scanner made from a file, not always System.in
        processes = new ArrayList<>();
        this.sc = sc;
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

            // the process will be operated on by the chosen algorithm.
            processes.add(new Process(name, burstTime));
        }

    }

    private SchedulingAlgorithm chooseAlgorithm() {
        System.out.println("Choose the scheduling algorithm you want to use.");
        System.out.println("1 - First Come First Served (FCFS)");
        System.out.println("2 - Shorted Job First (SJF)");
        System.out.println("3 - Round Robin (RR)");
        System.out.println("4 - Exit.");
        int choice = promptForInt("");
        while (true) {
            switch (choice) {
                case 1:
                    return new FirstComeFirstServed(processes);
                case 2:
                    return new ShortestJobFirst(processes);
                case 3:
                    int quantum = promptForInt("Enter quantum for Round Robin.");
                    return new RoundRobin(processes, quantum);
                case 4:
                    return null; // no algorithm should run - the program should exit.
            }
        }
    }

    public void start() {
        fillProcesses();
        SchedulingAlgorithm algorithm = chooseAlgorithm();
        if (algorithm != null) { // it's either RR, SJF or FCFS
            System.out.println("======================================================================");
            System.out.println(String.format("%-15s %-15s %-15s %-15s %-15s\n",
                    "Process Name", "Start Time", "Remaining Time", "Wait Time", "Cycle Duration"));

            // print out all the info about each CPUCycle.
            algorithm.execute().forEach(System.out::println);

            // The wait times correspond to the processes provided.
            List<Integer> averageWaitTimes = algorithm.getProcessWaitTimes();

            System.out.println();

            // the index "i" lines up the processes with the averages.
            for (int i = 0; i < processes.size(); i++) {
                System.out.println("Process: " + processes.get(i).getName() + " has average wait time of " + averageWaitTimes.get(i));
            }

            System.out.println();
            System.out.printf("Average wait time for %s: %.2f", algorithm.getName(), algorithm.averageWaitTime());
            System.out.println();
            System.out.println("======================================================================");
        }
    }
}
