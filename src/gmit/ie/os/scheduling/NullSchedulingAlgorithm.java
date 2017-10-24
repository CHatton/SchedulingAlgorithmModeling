package gmit.ie.os.scheduling;

import gmit.ie.os.CPUCycle;

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
	public List<Integer> getProcessWaitTimes() {
		return new ArrayList<>();
	}

    @Override
    public String getName() {
        return "";
    }
}
