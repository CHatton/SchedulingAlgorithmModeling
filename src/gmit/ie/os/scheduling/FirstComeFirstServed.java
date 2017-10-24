package gmit.ie.os.scheduling;

import gmit.ie.os.CPUCycle;
import gmit.ie.os.MyProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class FirstComeFirstServed implements SchedulingAlgorithm {

    private final List<MyProcess> processes;
    private final List<CPUCycle> cycles;

    public FirstComeFirstServed(final List<MyProcess> processes) {
        this.processes = new ArrayList<>(processes);
        this.cycles = new ArrayList<>();
    }

    @Override
    public List<CPUCycle> execute() {
        cycles.clear(); // every run will be a fresh list of cycles.

        int currentTime = 0; // assuming every process arrives at t=0.
        for (MyProcess process : processes) { // looking at every process.

            int duration = process.getBurstTime(); // process takes up the full burst time in FCFS

            process.setWaitTime(currentTime) // process has been waiting until now.
                    .operateOnFor(duration); // work on the process for the full burst time.

            cycles.add(new CPUCycle(process, currentTime, duration)); // create a new CPU Cycle.
            currentTime += duration; // we are now a full burst time ahead.
        }

        return new ArrayList<>(cycles); // defensive copy.
    }

    @Override
    public double averageWaitTime() {
        // should be called after execute.
        OptionalDouble avg = cycles.stream()
                .map(CPUCycle::getProcess) // look at every cpu cycle's process
                .mapToDouble(MyProcess::getWaitTime) // get the wait times
                .average(); // calc average of wait times

        return avg.isPresent() ? avg.getAsDouble() : 0;
    }

    @Override
    public List<Integer> getProcessWaitTimes() {
        return processes.stream()
                .map(MyProcess::getWaitTime) // get every wait time
                .collect(Collectors.toList()); // collect it as a list.
    }

    @Override
    public String getName() {
        return "First Come First Served";
    }
}
