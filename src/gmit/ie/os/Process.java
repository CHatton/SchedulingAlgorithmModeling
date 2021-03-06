package gmit.ie.os;

public class Process {

    // name and burst time will never change so can make them final
    private final String name;
    private final int burstTime;

    // not final as these will change
    private int waitTime;
    private int remainingTime;
    private int numCycles;

    // copy constructor.
    public Process(final Process process) {
        this.name = process.name;
        this.burstTime = process.burstTime;
        this.waitTime = process.waitTime;
        this.remainingTime = process.remainingTime;
        this.numCycles = process.numCycles;
    }

    public Process(final String name, final int burstTime) {
        this.name = name;
        this.burstTime = burstTime;
        this.remainingTime = burstTime; // all the time is remaining.
        this.waitTime = 0;
        this.numCycles = 0;
    }


    public boolean isAlive() { // any time left
        return remainingTime > 0;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getNumCycles() {
        return numCycles;
    }

    public void operateOnFor(final int time) {
        if (this.remainingTime - time < 0) {
            throw new IllegalArgumentException(String.format(
                    "Cannot operate on for [%s]. There is only [%s] time remaining.", time, remainingTime));
        }
        this.remainingTime -= time;
        this.numCycles++; // this process was "operated on" in a CPUCycle.
    }

    public int getBurstTime() {
        return burstTime;
    }

    public String getName() {
        return name;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public Process setWaitTime(final int waitTime) {
        this.waitTime = waitTime;
        return this;
    }

}
