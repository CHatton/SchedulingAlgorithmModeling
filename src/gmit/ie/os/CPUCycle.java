package gmit.ie.os;

public class CPUCycle {
    private final Process process;
    private final int startTime;
    private final int duration;

    /*
    represents a CPU cycle that has operated on a single process
    for a set duration.
     */
    public CPUCycle(final Process process, int startTime, int duration) {
        this.process = new Process(process);
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-15s %-15s %-15s %-15s",
                process.getName(), startTime,
                process.isAlive() ? process.getRemainingTime() : "finished",
                process.getWaitTime(), duration);
    }

}
