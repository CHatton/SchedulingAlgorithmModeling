package gmit.ie.os.scheduling;

import gmit.ie.os.CPUCycle;
import gmit.ie.os.MyProcess;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShortestJobFirst implements SchedulingAlgorithm {

    // use an fcfs implementation to delegate the methods as
    // sjf is the same as fcfs with the processes sorted by burst time.
    private final FirstComeFirstServed fcfs;

    public ShortestJobFirst(final List<MyProcess> processes) {
        // sorted version of the processes based on burst time
        List<MyProcess> sortedProcesses = processes
                .stream()
                .sorted(Comparator.comparingInt(MyProcess::getBurstTime))// sort by burst time (low -> high)
                .collect(Collectors.toList());  // get it as a list

        this.fcfs = new FirstComeFirstServed(sortedProcesses);

    }

    @Override
    public List<CPUCycle> execute() {
        return fcfs.execute();
    }

    @Override
    public double averageWaitTime() {
        return fcfs.averageWaitTime();
    }
}