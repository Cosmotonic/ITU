package ArchiveCode;
import java.util.Scanner;
import java.io.PrintWriter;

public class disjointsetssimple_my {

    private int[] numbersArray;
    private int[] scoreCount;
    private Scanner sc;

    public disjointsetssimple_my() {
        this.sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        if (!sc.hasNext())
            return;

        int numberRange = sc.nextInt();
        int inputLines = sc.nextInt();

        this.numbersArray = new int[numberRange];
        this.scoreCount = new int[numberRange];

        // Initialisering
        for (int i = 0; i < numberRange; i++) {
            numbersArray[i] = i; // Hvert element peger på sig selv
            scoreCount[i] = 1; // Alle starter med størrelse 1
        }

        for (int i = 0; i < inputLines; i++) {
            String op = sc.next();
            int p = sc.nextInt();
            int q = sc.nextInt();

            if (op.equals("?")) {
                if (find(p) == find(q)) {
                    out.println("1");
                } else {
                    out.println("0");
                }
            } else if (op.equals("=")) {
                union(p, q);
            }
        }
        out.flush();
        out.close();
        this.sc.close();
    }

    private int find(int i) {
        if (numbersArray[i] == i) { 
            return i;
        }
        return numbersArray[i] = find(numbersArray[i]);
    }

    private void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j)
            return;

        if (scoreCount[i] < scoreCount[j]) {
            numbersArray[i] = j;
            scoreCount[j] += scoreCount[i];
        } else {
            numbersArray[j] = i;
            scoreCount[i] += scoreCount[j];
        }
    }

    public static void main(String[] args) {
        new disjointsetssimple_my();
    }
}