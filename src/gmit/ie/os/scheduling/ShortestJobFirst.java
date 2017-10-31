package gmit.ie.os.scheduling;

import gmit.ie.os.CPUCycle;
import gmit.ie.os.Process;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShortestJobFirst implements SchedulingAlgorithm {

    // use an fcfs instance to delegate the methods to as
    // sjf is the same as fcfs with the processes sorted by burst time.
    private final FirstComeFirstServed fcfs;
    private final String name;

    public ShortestJobFirst(final List<Process> processes) {
        // sorted version of the processes based on burst time
        List<Process> sortedProcesses = processes.stream()
                .sorted(Comparator.comparingInt(Process::getBurstTime)) // sort by burst time (low -> high)
                .collect(Collectors.toList());  // get it as a list

        // with sorted processes SJF is identical to FCFS
        fcfs = new FirstComeFirstServed(sortedProcesses);
        name = "Shortest Job First";
    }

    @Override
    public List<CPUCycle> execute() {
        return fcfs.execute();
    }

    @Override
    public double averageWaitTime() {
        return fcfs.averageWaitTime();
    }

    @Override
    public List<Integer> getProcessWaitTimes() {
        return fcfs.getProcessWaitTimes();
    }

    @Override
    public String getName() {
        return name;
    }
}
