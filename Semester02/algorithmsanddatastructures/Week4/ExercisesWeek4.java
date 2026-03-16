
// 1.4.5 

/* 

a. N + 1
N + 1 ~ N
Det dominerende led er N, konstanten forsvinder.

b. 1 + 1/N
1 + 1/N ~ 1
For stor N går 1/N → 0, så det dominerende led er 1.

c. (1 + 1/N)(1 + 2/N)
Ganges ud: 1 + 2/N + 1/N + 2/N² = 1 + 3/N + 2/N²
(1 + 1/N)(1 + 2/N) ~ 1
Alle korrektionsleddene forsvinder for stor N 
Fordi 1/N vil give mindre jo større N er. Derfor er 1 størst. 

d. 2N³ − 15N² + N
2N³ − 15N² + N ~ 2N³

på nogle af tallene vil det være sandt at 2N^3 er større, (fx. N=2, men langt de fleste vil det være ^3 som vil vinde) 

N=2    2N³=16          15N²=60
N=10   2N³=2000        15N²=1500
N=100  2N³=2000000     15N²=150000
N=1000 2N³=2000000000  15N²=15000000

*/

package Week4;

import java.util.ArrayList;
import java.util.Collections;

public class ExercisesWeek4 {

    public static void main(String[] args) {

        int[] nrs = { 0, 3, 4, 5, 6 };
        int[] nrs1 = { 0, 2, 4, 5, 5 };
        // System.out.println(binarySearch(nrs,1));

        orderOfN();
        // matches(nrs, nrs1);

    }

    // 1.4.6
    public static void orderOfN() {
        
        // 
        
        
        // B

        // for (int n = N; n > 0; n /= 2) {
        // System.out.println(sum);
        // for (int i = 0; i < n; i++)
        // sum++;
        // }
        // System.out.printf("End sum: \n %d", sum); // aka the amount of times the
        // inner loop runs?
        //


        // when you see geometric sum its O(N)
        // c // N = 50 ()
        int N = 50;
        int sum = 0;
        for (int i = 1; i < N; i *= 2) { // Ydre løkke kører: i = 1, 2, 4, 8, 16, 32 → 6 gange (64 > 50 så den stopper)
            for (int j = 0; j < N; j++) { // Indre løkke kører: 50 gange hver gang
                System.out.printf("inner: %d \n", sum);
                sum++;
            }
            System.out.println(sum);

            // 6 × 50 = 300
            // n log(N)
            // Og N log N = 50 × log₂(50) = 50 × 5.64 = 282
        }
    }

    // 1.4.10
    public static int binarySearch(int[] a, int key) {
        // Så spørgsmålet er: hvordan ændrer du binary search så den ikke stopper
        // når den finder et match, men fortsætter med at lede efter et tidligere match?

        int lowestMatchPosition = -1;

        int lo = 0;
        int hi = a.length - 1; // first and last index

        // Note: Binary search only works on orderd list.
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) { // first half
                hi = mid - 1;
            } else if (key > a[mid]) { // last half
                lo = mid + 1;
            } else { // match position
                lowestMatchPosition = mid;
                hi = mid - 1;
            }
        }

        return lowestMatchPosition;
    }


    // 1.4.12 
    public static void matches(int[] sortedList_a, int[] sortedList_b) {

        int aIncrement = 0, bIncrement = 0;

        while (aIncrement < sortedList_a.length && bIncrement < sortedList_b.length) {
            if (sortedList_a[aIncrement] > sortedList_b[bIncrement]) { // 0.4 v 0.2, 0.4 v 1.3, 0.4 v 2.6, 1.5v 2.6, 2.6
                bIncrement++;
            } else if (sortedList_a[aIncrement] < sortedList_b[bIncrement]) {
                aIncrement++;
            } else { // match
                System.out.println(sortedList_a[aIncrement]);
                aIncrement++;
                bIncrement++;
            }
        }
    }


    public static 



}

// 1.4.5 
