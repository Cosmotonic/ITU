public class Car {
    private String plate;
    private int mileage;
    private int lastServiceMileage; // milages at last service.
    private int tankCapacity;
    private int gasLevel;
    private float consumption; // the number of liters of gas the car consumes per kilometer driven.

    public Car() {
        this.plate = 0;
        this.tankCapacity = 0;
        this.consumption = 0;
    }

    public String getPlate() {
        return plate;
    }

    public int getTankCapacity() {
        return tankCapacity;
    }

    public float getConsumption() {
        return consumption;
    }

    public int getGasLevel(){
        return gasLevel; 
    }

    public int kmSinceService() {
        return mileage - lastServiceMileage;
    }

    public void fillTank(int gasAmount){

        if (gasAmount+gasLevel>tankCapacity){
            throw new GasOverFlowException(); 
        }
        gasLevel += gasAmount; 
    }

    public void service(){
        lastServiceMileage = mileage; 
    }

    public boolean needsService(){
        return kmSinceService()>30000 ? true : false; 
    }

    public boolean drive(int d){
        // d = kilometers 
        int gasRequired = Math.round(d*consumption); 

        if (gasRequired <= gasLevel){
            gasLevel = gasLevel - gasRequired; 
            mileage += d; 
            return true; 
        }
        return false; 
    }

}
