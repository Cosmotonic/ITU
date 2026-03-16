import java.util.Scanner;

public class itu_ksaldas_changebto01 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (sc.hasNextLine()) {
            String input = sc.nextLine();
            itu_ksaldas_changebto01(input);
            // mystery("kasper"); // returns repsak
        }
    }

    // Awesome code snippet
    public static void itu_ksaldas_changebto01(String input) {
        // itu_ksaldas_changebto01(input);

        String s = input; // "abbbaabababa";
        char[] asCha = new char[s.length()];
        int bCounter = 0;

        for (int i = 0; i < asCha.length; i++) {
            char currentChar = s.charAt(i);

            if (currentChar == 'b') {
                asCha[i] = (bCounter % 2 == 0 ? '0' : '1');
                bCounter++;
                continue;
            }

            asCha[i] = currentChar;
        }

        String t = new String(asCha);
        System.out.println(t);
    }

    public static void reverseList() {

        int[] numbers = { 2, 4, 6, 8 };
        int[] newList = new int[numbers.length];

        System.out.println(newList.length);
        System.out.println(numbers.length);
        int i = 0;

        while (i < numbers.length) {
            newList[i] = numbers[numbers.length - 1 - i];
            i++;
        }

        for (int j = 0; j < newList.length; j++) {

            System.out.println(newList[j]);
        }
    }

    public static String mystery(String s) { // kasper
        int N = s.length(); // 6
        if (N <= 1) // not shorter
            return s;
        String a = s.substring(0, N / 2); // 0-3 kas
        String b = s.substring(N / 2, N); // 3-6
        return mystery(b) + mystery(a); // per kas // r e p s a k

    }
}
