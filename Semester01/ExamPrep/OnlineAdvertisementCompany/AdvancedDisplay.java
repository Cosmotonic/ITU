public class AdvancedDisplay extends Display {
    
    private int maxlength; 
    private int additionalPrice; 

    public AdvancedDisplay(int dailyPrice, int maxLength, int additionalPrice){
        super(dailyPrice); 
        this.maxlength = maxLength; 
        this.additionalPrice = additionalPrice; 
    }

    public boolean isLongAdv(String adv){
        System.out.println();
        if (adv.length()>this.maxlength){
            return true; 
        }
        return false; 
    }

    @Override
    public int calculateTotalPrice(int n, Stirng adv){

        if (isLongAdv(adv)){
            return super.calculateTotalPrice() + this.additionalPrice; 
        }

        return 0; 
    }

    
}
