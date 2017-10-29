package gmit.ie.os;

public class CPUCycle {
    private final Process process;
    private final int startTime;

    public CPUCycle(final Process process, int startTime, int duration) {
        this.process = new Process(process);
        this.startTime = startTime;
    }

    public Process getProcess() {
        return process;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-15s %-15s %-15s",
                process.getName(), startTime,
                process.isAlive() ? process.getRemainingTime() : "finished",
                process.getWaitTime());
    }

}
