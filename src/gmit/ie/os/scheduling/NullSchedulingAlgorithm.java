package gmit.ie.os.scheduling;

import gmit.ie.os.CPUCycle;
import gmit.ie.os.MyProcess;

import java.util.ArrayList;
import java.util.List;

public class NullSchedulingAlgorithm implements SchedulingAlgorithm {

    @Override
    public List<CPUCycle> execute() {
        return new ArrayList<>();
    }

    @Override
    public double averageWaitTime() {
        return 0;
    }
	@Override
	public List<Double> getProcessWaitTimes() {
		return new ArrayList<>();
	}
}
