package gmit.ie.os.scheduling;

import gmit.ie.os.CPUCycle;
import gmit.ie.os.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;


public class RoundRobin implements SchedulingAlgorithm {

    private final List<Process> processes;
    private final List<CPUCycle> cycles;
    private final int quantum;

    public RoundRobin(final List<Process> processes, final int quantum) {
        this.processes = new ArrayList<>(processes);
        this.cycles = new ArrayList<>();
        this.quantum = quantum;
    }

    private boolean finished() {
        // algorithm is finished running if no process is alive.
        return processes.stream().noneMatch(Process::isAlive);
    }

    @Override
    public List<CPUCycle> execute() {
        cycles.clear();
        int currentTime = 0; // assumption that all processes start at t=0

        while (!finished()) {
            List<Process> liveProcesses = processes.stream()
                    .filter(Process::isAlive) // get all processes that are still alive
                    .collect(Collectors.toList()); // get them as a list

            for (Process process : liveProcesses) {
                // cycle duration is the lower of the remaining time and quantum.
                int cycleDuration = process.getRemainingTime() < quantum
                        ? process.getRemainingTime() : quantum;

                process.setWaitTime(currentTime) // process has been waiting for this long
                        .operateOnFor(cycleDuration); // perform the actual "operations" on the process.

                cycles.add(new CPUCycle(process, currentTime, cycleDuration)); // save the cpu cycle.
                currentTime += cycleDuration; // one more duration has passed.
            }
        }

        // defensive copy of array list.
        return new ArrayList<>(cycles);
    }

    private double calcProcessAverage(Process process) { // use the formula "last time ran - quantum x num cycles"
        // wait time will be the last time it ran.
        // num cycles gets updated ever time that time is deducted from the process.
        // -1 because we don't count the last cycle as wait time.
        return process.getWaitTime() - quantum * (process.getNumCycles() - 1);

    }

    @Override
    public double averageWaitTime() {
        // double precision will be more accurate
        // than the list of Integers given back from "getProcessWaitTime"
        OptionalDouble avg = processes.stream()
                .mapToDouble(this::calcProcessAverage)
                .average();

        return avg.isPresent() ? avg.getAsDouble() : 0;
    }

    @Override
    public List<Integer> getProcessWaitTimes() {
        return processes.stream().map(this::calcProcessAverage)
                // need to case the double value as an int to get a list of integers
                // will lose some precision casting to int.
                .map(num -> (int) num.doubleValue())
                .collect(Collectors.toList()); // get them back as a list
    }

    @Override
    public String getName() {
        return "Round Robin";
    }
}
