public class Activity {
    private String name; // swimming
    private int caloriesPerMin; // number of calories burned per min
    private int startHour; // 14
    private int startMin; // 10 (14.10) always assumed same day.
    private int endHour;
    private int endMin;

    public Activity(String name, int caloriesPerMin) {
        this.name = name;
        this.caloriesPerMin = caloriesPerMin;
        this.startHour = 0;
        this.startMin = 0;
        this.endHour = 0;
        this.endMin = 0;
    }

    public boolean checkTime(int hour, int min) {

        if (hour < 24 && hour >= 0 && min < 60 && min >=0 ) {
            return true;
        }
        return false;
    }

    public void setStartTime(int hour, int min) {

        if (checkTime(hour, min)) {
            this.startHour = hour;
            this.startMin = min;
        } else {
            throw new IllegalArgumentException("Invalid time!");
        }
    }

    public void setEndTime(int hour, int min) {

        int combinedEndHour = hour * 100 + min;
        int combinedStartHour = this.startHour * 100 + this.startMin;

        if (checkTime(hour, min) && combinedEndHour > combinedStartHour) {
            this.endMin = min;
            this.endHour = hour;
        } else {
            throw new IllegalArgumentException("Invalid time!");
        }
    }

    // 1.5 
    public int calculateDuration(){

        int combinedEndHour = this.endHour * 60 + this.endMin;
        int combinedStartHour = this.startHour * 60 + this.startMin;
        return combinedEndHour - combinedStartHour; 
    }

    // 1.6
    public int calculateCalories(){

        return calculateDuration()*caloriesPerMin; 
    }

    // 1.7
    public String getSummary(){
        return String.format("Name: %s, calories per minute: %d, duration: %d minutes", name, caloriesPerMin, calculateDuration()); 
    }
}
