import java.util.Scanner;

public class coffeecupcombo {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        if (!scan.hasNextInt()) return;

        int lectureHalls = scan.nextInt();
        scan.nextLine(); 
        String roomsWithCoffee = scan.nextLine();

        int lecturesAttended = 0;
        int coffiesInHand = 0;

        for (int i = 0; i < lectureHalls; i++) {
            if (roomsWithCoffee.charAt(i) == '1') {
                lecturesAttended++;
                coffiesInHand = 2; 
            } else if (coffiesInHand >= 1 ) // && roomsWithCoffee.charAt(i) == '0') { 
                lecturesAttended++;
                coffiesInHand--; 
            }
            // Scenario C: if i dont have coffee and there is no machine ...
            // ...I cannot get an attendence score and the lectures attended remains the same.
            continue; 
        }

        System.out.println(lecturesAttended);
    }
}