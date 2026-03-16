public class Robot {
    private int batteryLevel;
    private int maxLevel;
    private int chargingRate;

    public Robot(int batteryLevel, int maxLevel, int chargingRate) {
        this.batteryLevel = batteryLevel;
        this.maxLevel = maxLevel;
        this.chargingRate = chargingRate;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public boolean performTask(int requiredEnergy) {
        if (requiredEnergy > batteryLevel) {
            return false;
        }

        batteryLevel = batteryLevel - requiredEnergy;
        return true;
    }

    public int timeToCharge() {
        return 60;
    }

    public void charge() {
        batteryLevel = maxLevel;
    }

    public void setBatteryLevel(int level){
        if (level >=0 && level <= maxLevel){
            batteryLevel = maxLevel; 
        }
    }

}
