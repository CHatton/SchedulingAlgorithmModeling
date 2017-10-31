package gmit.ie.os.scheduling;

import gmit.ie.os.CPUCycle;
import gmit.ie.os.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RoundRobin implements SchedulingAlgorithm {

    private final List<Process> processes;
    private final List<CPUCycle> cycles;
    private final int quantum;
    private final String name;

    public RoundRobin(final List<Process> processes, final int quantum) {
        this.processes = new ArrayList<>(processes); // defensive copy.
        this.quantum = quantum;
        cycles = new ArrayList<>();
        name = "Round Robin";
    }

    private boolean finished() {
        // algorithm is finished running if no process is alive.
        return processes.stream().noneMatch(Process::isAlive);
    }

    private List<Process> liveProcesses() {
        // returns a filtered list of only the processes that are still alive.
        return processes.stream()
                .filter(Process::isAlive) // get only processes that are still alive
                .collect(Collectors.toList()); // get them as a list
    }

    private int getCycleDurationFor(Process process) {
        // get the lower of the remaining time and quantum.
        return process.getRemainingTime() < quantum ? process.getRemainingTime() : quantum;
    }

    @Override
    public List<CPUCycle> execute() {
        int currentTime = 0; // assumption that all processes start at t=0
        while (!finished()) {
            // with each iteration just consider the processes that are still alive.
            for (Process process : liveProcesses()) {

                int cycleDuration = getCycleDurationFor(process); // lower of quantum or remaining time.

                process.setWaitTime(currentTime) // process has been waiting for this long
                        .operateOnFor(cycleDuration); // perform the actual "operations" on the process.

                cycles.add(new CPUCycle(process, currentTime, cycleDuration)); // represent this operation as a new cpu cycle.
                currentTime += cycleDuration; // one more duration has passed.
            }
        }
        return new ArrayList<>(cycles); // defensive copy.
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
        return processes.stream()
                .mapToDouble(this::calcProcessAverage)
                .average() // calculate average
                .orElse(0); // 0 is the default value if the optional double is not present.
    }

    @Override
    public List<Integer> getProcessWaitTimes() {
        return processes.stream()
                .map(this::calcProcessAverage)
                .map(num -> (int) num.doubleValue())   // will lose some precision casting to int.
                .collect(Collectors.toList()); // get them back as a list
    }

    @Override
    public String getName() {
        return name;
    }
}
