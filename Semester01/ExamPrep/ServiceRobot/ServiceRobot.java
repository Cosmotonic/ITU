import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ServiceRobot extends Robot {
    private Map<String, Integer> taskInfo; // task & required energy

    public ServiceRobot(int m, int b, int c) {
        super(b, m, c);
        this.taskInfo = new HashMap<>();
    }

    public void defineTask() {
        Scanner userInput = new Scanner(System.in);
        String taskDescription = userInput.nextLine();
        int requiredEnergy = Integer.valueOf(userInput.nextLine());

        if (requiredEnergy > getMaxLevel() || requiredEnergy < 0) {
            throw new IllegalArgumentException("The value of required energy is not valid");
        }
        taskInfo.put(taskDescription, requiredEnergy);
    }

    public void charge() {
        System.out.printf("Time to charge is %d minutes%n", timeToCharge());
        setBatteryLevel(getMaxLevel());
    }

    public void service(int n) {
        Scanner userInput = new Scanner(System.in);

        for (int i = 0; i < n; i++) {
            String taskRequest = userInput.nextLine();
            if (taskInfo.containsKey(taskRequest)) {

                if (performTask(taskInfo.get(taskRequest))) {
                    System.out.printf("Performing task %s%n", taskRequest);
                } else {
                    System.out.println("Sorry I do not have enough battery");
                }
            } else {
                System.out.printf("Sorry the task %s is not among my defined tasks!%n", taskRequest);
            }
        }
        
        userInput.close();
    }
}
