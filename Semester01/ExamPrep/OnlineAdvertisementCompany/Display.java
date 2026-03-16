public class Display {
    private int daysRemaining; 
    private int dailyPrice; // number of remaing days to show advertisement on display and price per day 
    private String advertisement; 
    private String defaultMessage; // when advertisement has expired. 

    public Display(int dailyPrice){
        this.dailyPrice = dailyPrice; 
        this.daysRemaining = 0; 
        this.advertisement = "Advertisement coming soon!"; 
        this.defaultMessage = "Advertisement coming soon!"; 
    }

    public String getAdvertisement(){
        return this.advertisement; 
    }

    public void setDefaultMessage(String msg){
        this.defaultMessage = msg; 
    }

    public boolean isAdvExperied(){
        if(this.daysRemaining>0){
            return true; 
        }
        return false;
    }

    public void decrementDay(){
        
        if (isAdvExperied()){
            this.daysRemaining -=1; 
            return; 
        }

        this.advertisement = this.defaultMessage; 
    }

    public void updateDailyPrice(int n){
        // update n percent to current value of daily price 

        int percentage = 100; 
        if (n<0){
            percentage -= n; // 96
        }else{
            percentage += n; // 104
        }

        this.dailyPrice = Math.round(this.dailyPrice * percentage); 

    }

    public int calculateTotalPrice(int n, String adv){

        if (adv.length()<10){
            return 0; 
        }
        // if less than 10, returns 0 because its free. 
        int advPrice = this.dailyPrice * n; 

        return advPrice;  
    }

    public boolean loadAdvertisement(int n, String adv){

        if (n>0){
            this.advertisement = adv; 
            this.daysRemaining = n; 
            return true; 
        }

        return false; 
    }

    
}
