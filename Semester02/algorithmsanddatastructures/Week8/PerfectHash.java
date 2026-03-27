
package Week8;
import java.util.*;

public class PerfectHash {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter letters (e.g. SEARCH): ");
        String input = scanner.next().toUpperCase();

        Map<Character, Integer> keyMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            keyMap.put(c, c - 'A' + 1); // A=1, B=2, ... Z=26
        }

        System.out.println("Keys: " + keyMap);

        for (int a = 1; a <= 100; a++) {
            for (int M = keyMap.size(); M <= 50; M++) {
                if (isPerfect(a, M, keyMap)) {
                    System.out.println("\nFound: a=" + a + ", M=" + M);
                    printTable(a, M, keyMap);
                    return;
                }
            }
        }
        System.out.println("No perfect hash found.");
    }

    static boolean isPerfect(int a, int M, Map<Character, Integer> keyMap) {
        boolean[] seen = new boolean[M];
        for (int k : keyMap.values()) {
            int hash = (a * k) % M;
            if (seen[hash]) return false;
            seen[hash] = true;
        }
        return true;
    }

    static void printTable(int a, int M, Map<Character, Integer> keyMap) {
        for (Map.Entry<Character, Integer> entry : keyMap.entrySet()) {
            System.out.println(entry.getKey() + " (k=" + entry.getValue() + ") -> " + (a * entry.getValue()) % M);
        }
    }
}
