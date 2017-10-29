package gmit.ie.os;

import java.util.Scanner;

public class SchedulerRunner {
    public static void main(String[] args) {
        ProcessScheduler scheduler = new ProcessScheduler(new Scanner(System.in));
        scheduler.start();
    }
}
