import java.util.ArrayList;
import java.util.List;

public class C {

    public static void c(String line) {
        builder(line); 
        /*
        int n = line.length();

        int[] alphabetCounter = new int[26];

        for (int i = 0; i < n; i++) {
            char ch = line.charAt(i);
            alphabetCounter[ch - 'A']++;
        }

        List<Character> letters = new ArrayList<>();

        for (int i = 0; i < 26; i++) {
            if (alphabetCounter[i] > 0) {
                letters.add((char) (i + 'A')); // i + 'A' = 2 + 65 = 67 // (char)67 = 'C'
            }
        }
        System.out.println(letters);

        letters.sort((a, b) -> alphabetCounter[b - 'A'] - alphabetCounter[a - 'A']);

        for (char ch : letters) {
            System.out.print(ch);
        }
        */

        /*
         * for (int i = 0; i < n; i++) {
         * boolean found = false;
         * 
         * for (int j = i + 1; j < n; j++) {
         * if (line.charAt(i) == line.charAt(j)) {
         * found = true;
         * }
         * }
         * 
         * if (found) {
         * System.out.print(line.charAt(i));
         * }
         * }
         */
    }

    public static void builder(String line){
            StringBuilder sb = new StringBuilder(line);
            // System.out.println("Initial StringBuilder: " + sb);
            // sb.append(" is awesome!");
            // System.out.println("After append: " + sb);

            System.out.println(sb.reverse());
    }

    public static void main(String[] args) {
        // System.out.println("hello");
        c("Hello");
        // c("");
    }
}