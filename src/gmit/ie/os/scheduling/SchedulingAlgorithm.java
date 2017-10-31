package gmit.ie.os.scheduling;

import gmit.ie.os.CPUCycle;

import java.util.List;

public interface SchedulingAlgorithm {
    List<CPUCycle> execute(); // executes the scheduling algorithm returns a list of CPUCycles that represent every stage.
    double averageWaitTime(); //  gives back the average wait time of the processes.
    List<Integer> getProcessWaitTimes(); // gives back a list of average times for the processes that ran.
    String getName(); // the name of the algorithm.
}
