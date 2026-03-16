import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FitnessTracker {
    private int goal; // kilocalorie burn goal for a day
    private List<Activity> activityList;

    public FitnessTracker(int goal) {
        this.goal = goal;
        activityList = new ArrayList<>();
    }

    // 1.12
    public void addActivity(Activity a) {
        int currentTotal = 0;
        for (Activity activity : activityList) {
            currentTotal += activity.calculateCalories();
        }

        int newTotal = currentTotal + a.calculateCalories();

        // Check if the goal is achieved specifically by adding 'a'
        if (currentTotal < this.goal && newTotal >= this.goal) {
            System.out.println("Congratulations! You achieved your daily goal.");
        }

        activityList.add(a);
    }

    // 1.13
    public void removeActivities(Activity a) {
        String targetSummary = a.getSummary();

        // Loop backwards to safely remove items by index
        for (int i = activityList.size() - 1; i >= 0; i--) {
            if (activityList.get(i).getSummary().equals(targetSummary)) {
                activityList.remove(i);
                System.out.println(i);
            }
        }

        // Always add the activity to the end
        activityList.add(a);
    }

    // 1.4
    public void findLongest() {
        Activity longestActivity = activityList.get(0);

        for (Activity activitiy : activityList) {
            if (activitiy.calculateDuration() >= longestActivity.calculateDuration()) {
                longestActivity = activitiy;  
            }
        }

        System.out.println(longestActivity.getSummary());
    }

}
