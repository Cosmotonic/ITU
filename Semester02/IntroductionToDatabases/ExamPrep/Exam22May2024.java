public class Exam22May2024 {

    public static void main(String[] args) {

        int counter = 0;

        // for (int i = 0; i < 100_000; i = i+1) {
        // // System.out.println(i);
        // for (int j = i; j > 1; j=j/2) {
        // // System.out.println(i);
        // // System.out.println(j);
        // // System.out.println("*");
        // counter++;
        // }
        // }

        // java
        int nr = r(5);
        System.out.println(nr);

    }

    static int r(int N) {
        if (N > 3) {
            return 2 * r(N - 1) + r(N - 2) - 2;
        } else {
            return 2;
        }
    }

    // 2 * r (n - 1) + r(n - 2) - 2 
    // 

}
// 113.618
// 1.468.930