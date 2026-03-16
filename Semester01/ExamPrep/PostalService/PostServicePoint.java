import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class PostServicePoint {

    // array arrays 
    private Item[] registeredItems = new Item[10];
    private List<Item> processedItems;
    private int maxWeight;
    private int count;

    public PostServicePoint(int maxWeigth) {
        this.maxWeight = maxWeigth;
        this.count = 0;
        this.processedItems = new ArrayList<>();
    }

    public void register(Item item) {
        if (count > 9) {
            System.out.println("no available capacity!");
            return;
        }

        registeredItems[count] = item;
        count++;
    }

    public boolean checkItemAddress(String adr) {
        return adr.length() > 20 ? true : false;
    }

    public boolean checkItemWeight(int wgh) {
        return wgh < maxWeight;
    }

    // 8
    public void printPrice(int i) {
        if (i < registeredItems.length && registeredItems[i] != null) {
            int round = registeredItems[i].getWeight() % 2;
            int weightMultiplier = registeredItems[i].getWeight() / 2;
            int price = (weightMultiplier + round) * 12;
            System.out.printf("The price for sending is: %d%n", price);
        }
    }

    public void printAllPrices() {
        for (int i = 0; i < registeredItems.length; i++) {
            printPrice(i);
        }
    }

    // 10
    public void processOne() {

        if (count <= 0) {
            return;
        }

        Item item = registeredItems[count - 1];
        registeredItems[count - 1] = null;
        count--;

        if (!checkItemAddress(item.getAddress())) {
            System.out.println("Invalid address");
            return;
        }
        if (!checkItemWeight(item.getWeight())) {
            System.out.println("Invalid weight");
            return;
        }

        processedItems.add(item);
    }

    // 11
    public void processAll() {
        while (count > 0) {
            processOne();
        }
    }

    public int totalWeight() {
        return processedItems.stream()
                .mapToInt(item -> item.getWeight())
                .sum();
    }

    public Map<String, Integer> checkCategories() {

        Map<String, Integer> categoryMap = new HashMap<>();

        // remember you can use MERGE
        for (Item item : processedItems) {
            if (categoryMap.containsKey(item.getCategory())) {
                int value = categoryMap.get(item.getCategory());
                categoryMap.put(item.getCategory(), value + 1);
            } else {
                categoryMap.put(item.getCategory(), 1);
            }
        }
        return categoryMap;
    }

    public void removeCategory(String c) {
        processedItems.removeIf(item -> item.getCategory().equals(c));
    }

    public void removeCategoryGemini(String c) {
        Iterator<Item> it = processedItems.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getCategory().equals(c)) {
                it.remove(); 
            }
        }
    }

    public static void main(String[] args) {

    }

}
