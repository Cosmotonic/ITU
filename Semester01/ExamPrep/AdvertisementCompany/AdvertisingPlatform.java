import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AdvertisingPlatform {

    private List<Customer> subscribers;
    private Set<String> forbiddenWords;

    public AdvertisingPlatform(Set<String> forbiddenWords) {
        this.forbiddenWords = forbiddenWords;
        this.subscribers = new ArrayList<>();
    }

    public void addCustomer(Customer cm) {
        if (subscribers.contains(cm)) {
            System.out.println("customer already exists");
            return;
        }

        subscribers.add(cm);
        System.out.println("customer is added!");
    }

    // 9. "hvis der er et ord i adv som passer til pref..."
    public void publishAdv(String adv, Customer cm) {
        String[] advertWords = adv.split("\\s+");

        for (String addWord : advertWords) {
            if (cm.getPreferences().contains(addWord)) {
                cm.addAdvertisement(adv);
                break;
            }
        }
    }

    // 10.
    public boolean checkValidity(String adv) {
        String[] advertWords = adv.split("\\s+");
        return Collections.disjoint(Arrays.asList(advertWords), forbiddenWords);
    }

    // 11. 
    public void publishToAll(String adv){
        if (checkValidity(adv)){
            return; 
        }

        for (Customer cm : subscribers){
                publishAdv(adv, cm);
        }
    }
}
