public class EnduranceActivity extends Activity {
    private int distance; // distance covered during activity in kilometers
    private int caloriesPerKM; // kilocalories burned per km 

    public EnduranceActivity(String name, int caloriesPerMin, int k, int d){
        super(name, caloriesPerMin); 
        this.distance = d; 
        this.caloriesPerKM = k;
    }

    // 1.9

    @Override
    public int calculateCalories(){
        return super.calculateCalories()+this.distance*this.caloriesPerKM; 
    }

    // 1.10
    @Override 
    public String getSummary(){
        int speed = Math.round((this.distance/calculateDuration())*60); 
        return super.getSummary() + speed; 
    }

    // 1.11



}
