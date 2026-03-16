import java.util.Scanner;

// awesome code here - inner class detail. 
public class atlogur {

    // Im just gonan make my helper knight, i think its fun if we can give them armor later
    static class Knight {
        int id;
        int hp;
        int str;

        Knight(int id, int hp, int str) {
            this.id = id;
            this.hp = hp;
            this.str = str;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt())
            return;

        int n = sc.nextInt();

        // 1. creating my most agressive knight 
        Knight winner = new Knight(1, sc.nextInt(), sc.nextInt());

        // 2. create and take the next upcoming challenger 
        for (int i = 2; i <= n; i++) {
            Knight challenger = new Knight(i, sc.nextInt(), sc.nextInt());

            // fight! 
            if (battle(winner, challenger)) {
                winner = challenger; // the new king of the hill 
            }
        }

        System.out.println(winner.id);
    }

    public static boolean battle(Knight winner, Knight challenger) {
        while (winner.hp > 0 && challenger.hp > 0) {
            // the agressive hits first 
            challenger.hp -= winner.str;
            if (challenger.hp <= 0)
                return false; // the challenger ties. Did the challenger win? No. 

            // challenger wins
            winner.hp -= challenger.str; 
        }
        
        return true; // challenger wins
    }
}