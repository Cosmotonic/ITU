import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Customer {

    private Set<String> preferences;
    private List<String> advertisements;
    private int maxAdvs;

    // contains only words separated by single spaces adn no punctioactions.
    // each preferene are only single words.

    public Customer(Set<String> preferences, int maxAdvs) {
        this.preferences = preferences;
        this.maxAdvs = maxAdvs;
        this.advertisements = new ArrayList<>();
    }

    public Set<String> getPreferences() {
        return preferences;
    }

    public void addAdvertisement(String adv) {
        if (advertisements.size() >= maxAdvs) {
            return;
        }

        advertisements.add(adv);
    }

    public void addPreferences(String[] newPreferences) {
        for (String preference : newPreferences) {
            preferences.add(preference);
        }
    }

    public void removePreference(String preference) {
        preferences.remove(preference);
    }

    public void readAdvertisement(int n) {
        int counter = 0;
        while (counter < n && !advertisements.isEmpty()) {
            int lastIndex = advertisements.size() - 1; 
            System.out.println(advertisements.get(lastIndex));
            advertisements.remove(lastIndex);
        }
    }

}