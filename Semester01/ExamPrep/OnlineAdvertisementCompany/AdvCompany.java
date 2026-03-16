import java.util.ArrayList;
import java.util.List;

public class AdvCompany  {
    
    private List<Display> allDisplays; // all displays
    private String name; // company name

    public AdvCompany(String name){
        this.name = name; 
        this.allDisplays = new ArrayList<>(); 
    }

    public void addDisplay(Display d){
        String message = String.format("We are looking for your advertisements at %s", this.name); 
        d.setDefaultMessage(message);

        allDisplays.add(d); 
    }

    public void elapsDay(){
        for (Display display : allDisplays){
            display.decrementDay();
        }
    }

    public void updateDailyPrice(int n, int i){

        // Just going to use an exception here for show. 
        try {
            Display display = allDisplays.get(i); 
            display.updateDailyPrice(n);

        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Display does not exists");
        }
    }

    public void findDisplay(String adv, int n, int budget){
        
        for (Display display : allDisplays){
            if (display.isAdvExperied() && display.calculateTotalPrice(n, adv)<budget){
                display.loadAdvertisement(n, adv); 
            }
        }
    }

}
