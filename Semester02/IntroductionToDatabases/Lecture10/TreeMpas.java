
public class TreeMpas {
    public static void main(String[] args) {
    }

    private TreeMap<Integer, List<Integer>> createPriceIndex() {
        TreeMap<Integer, List<Integer>> priceIndex = new TreeMap<>();
        priceIndex.computeIfAbsent(85, _ -> new ArrayList<>()).add(100);
        priceIndex.computeIfAbsent(100, _ -> new ArrayList<>()).add(150);
        priceIndex.computeIfAbsent(100, _ -> new ArrayList<>()).add(200);
        priceIndex.computeIfAbsent(110, _ -> new ArrayList<>()).add(250);
        priceIndex.computeIfAbsent(125, _ -> new ArrayList<>()).add(300);
        return priceIndex;

    }
}
