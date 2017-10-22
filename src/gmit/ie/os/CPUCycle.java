package gmit.ie.os;

public class CPUCycle {
    private final MyProcess process;
    private final int startTime;

    public CPUCycle(final MyProcess process, int startTime, int duration) {
        this.process = new MyProcess(process);
        this.startTime = startTime;
    }

    public MyProcess getProcess() {
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
