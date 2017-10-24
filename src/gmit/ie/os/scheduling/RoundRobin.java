package gmit.ie.os.scheduling;

import gmit.ie.os.CPUCycle;
import gmit.ie.os.MyProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;


public class RoundRobin implements SchedulingAlgorithm {

    private final List<MyProcess> processes;
    private final List<CPUCycle> cycles;
    private final int quantum;

    public RoundRobin(final List<MyProcess> processes, final int quantum) {
        this.processes = new ArrayList<>(processes);
        this.cycles = new ArrayList<>();
        this.quantum = quantum;
    }

    private boolean finished() {
        // algorithm is finished running if no process is alive.
        return processes.stream().noneMatch(MyProcess::isAlive);
    }

    @Override
    public List<CPUCycle> execute() {
        cycles.clear();
        int currentTime = 0; // assume all processes start at t=0

        while (!finished()) {
            List<MyProcess> liveProcesses = this.processes.stream()
                    .filter(MyProcess::isAlive) // get all processes that are still alive
                    .collect(Collectors.toList()); // get them as a list

            for (MyProcess process : liveProcesses) {
                // cycle duration is the lower of the remaining time and quantum.
                int cycleDuration = process.getRemainingTime() < quantum
                        ? process.getRemainingTime() : quantum;

                process.setWaitTime(currentTime)
                	.operateOnFor(cycleDuration);

                cycles.add(new CPUCycle(process, currentTime, cycleDuration));
                currentTime += cycleDuration;
            }
        }

        // defensive copy of arraylist.
        return new ArrayList<>(cycles);
    }

    private double calcProcessAverage(MyProcess process) { // use the formula "last time ran - quantum x num cycles"
        // wait time will be the last time it ran.
        // num cycles gets updated ever time that time is deducted from the process.
        // -1 because we don't count the last cycle as wait time.
        return process.getWaitTime() - quantum * (process.getNumCycles() - 1);

    }

    @Override
    public double averageWaitTime() {
        OptionalDouble avg = processes.stream()
                .mapToDouble(this::calcProcessAverage)
                .average();

        return avg.isPresent() ? avg.getAsDouble() : 0;
    }

	@Override
	public List<Double> getProcessWaitTimes() {
		List<Double> averages = new ArrayList<>();
		for (MyProcess process : processes){
			averages.add(calcProcessAverage(process));
		}
		return averages;
	}
}
