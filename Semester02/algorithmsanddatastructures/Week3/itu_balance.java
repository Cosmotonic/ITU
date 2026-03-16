import java.util.Scanner;
import java.util.Stack;
import java.util.Map;

// Check bracketmatching.java for en mere streamlined version. 

public class itu_balance {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String[] inputSplit = sc.nextLine().split("");
        Stack<String> stack = new Stack<>();

        for (String bracket : inputSplit) {

            // System.out.printf("stack status: %s \n", stack);

            if (bracket.equals("(") || bracket.equals("[") || bracket.equals("{")) {
                // System.out.println("opener");
                stack.push(bracket);
            } else if (bracket.equals(")") || bracket.equals("]") || bracket.equals("}")) {
                // System.out.println("closer");

                if (stack.size() == 0) {
                    System.out.println("0");
                    System.exit(0);
                }
                String peek = stack.peek();
                // System.out.printf("Peeking: %s \n", peek);

                if (bracket.equals(")") && peek.equals("(")) {
                    stack.pop(); // LIFO
                } else if (bracket.equals("]") && peek.equals("[")) {
                    stack.pop(); // LIFO
                } else if (bracket.equals("}") && peek.equals("{")) {
                    stack.pop(); // LIFO
                } else {
                    // System.out.printf("ELSE Condition Stack status: %s \n", stack);
                    // stack.clear();
                    // stack.push("0");
                    System.out.println("0");
                    System.exit(0);
                }
            }
        }

        System.out.println(stack.isEmpty() ? "1" : "0");
    };
}
