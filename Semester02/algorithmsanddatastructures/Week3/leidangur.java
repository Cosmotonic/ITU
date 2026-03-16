import java.util.*;
import java.util.Scanner;

public class leidangur {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String journey = scanner.nextLine().trim();

        int[] leidangur = leidangur(journey);

        if (leidangur == null) {
            System.out.println("Neibb");
        } else {
            System.out.println(leidangur[0]); // money (p)
            System.out.println(leidangur[1]); // gold (g)
            System.out.println(leidangur[2]); // jewels (o)
        }
    }

    static int[] leidangur(String journey) {
        Stack<Character> backpack = new Stack<>();

        for (int i = 0; i < journey.length(); i++) {
            char c = journey.charAt(i);

            if (c == 'p' || c == 'g' || c == 'o') {
                // Pick up item and put on top of backpack
                backpack.push(c);

            } else if (c == 'P' || c == 'G' || c == 'O') {
                // Bad guy - figure out what they want
                char want = Character.toLowerCase(c);

                // Check if we even have this item anywhere in backpack
                if (!backpack.contains(want)) {
                    return null; // Journey fails - we don't have the item
                }

                // Discard items from top until we find the right one
                while (backpack.peek() != want) {
                    backpack.pop(); // throw away items above it
                }
                backpack.pop(); // give the item to the bad guy

            }
            // '.' is empty space, do nothing
        }

        // Count remaining items in backpack
        int money = 0, gold = 0, jewels = 0;
        while (!backpack.isEmpty()) {
            char item = backpack.pop();
            if (item == 'p') money++;
            else if (item == 'g') gold++;
            else if (item == 'o') jewels++;
        }

        return new int[]{money, gold, jewels};
    }
}