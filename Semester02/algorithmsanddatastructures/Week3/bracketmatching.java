import java.util.Scanner;
import java.util.Map;
import java.util.Stack;

public class bracketmatching {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int iter = Integer.parseInt(sc.nextLine());
        String input = sc.nextLine();
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> pairs = Map.of(')', '(', ']', '[', '}', '{');
        // alle ine values er "open" alle keys er "closed"

        for (int i = 0; i < iter; i++) {
            char c = input.charAt(i);
            if (pairs.containsValue(c)) {
                stack.push(c);
            } else if (pairs.containsKey(c)) {
                if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                    System.out.println("Invalid");
                    return;
                }
            }
        }
        System.out.println(stack.isEmpty() ? "Valid" : "Invalid");
    }
}