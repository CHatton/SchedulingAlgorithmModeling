package gmit.ie.os;

public class MyProcess { // called the class MyProcess to avoid clashing with java.lang.Process

    // name and burst time will never change so can make them final
    private final String name;
    private final int burstTime;

    // not final as these will change
    private int startTime;
    private int waitTime;
    private int remainingTime;
    private int numCycles;

    // copy constructor.
    public MyProcess(final MyProcess process) {
        this.name = process.name;
        this.burstTime = process.burstTime;
        this.startTime = process.startTime;
        this.waitTime = process.waitTime;
        this.remainingTime = process.remainingTime;
        this.numCycles = process.numCycles;
    }

    public MyProcess(final String name, final int burstTime) {
        this.name = name;
        this.burstTime = burstTime;
        this.remainingTime = burstTime; // all the time is remaining.
        this.startTime = 0;
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

    public MyProcess setWaitTime(final int waitTime) {
        this.waitTime = waitTime;
        return this;
    }

}
