import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExamObservation {

    public static void main(String[] args) {

        // produce an output of values that are unique in each list.
        int[] a1 = { 1, 3, 7, 11, 12 };
        int[] b1 = { 2, 3, 4, 7, 12, 14 };

        // could do double loop to produce an out n^2
        // we can also do a merge join
        // have a pointer at each list. Compare lowest pointer. Put lowest into new
        // unique list.

        // JAVA Solution:

        List<Integer> a = List.of(1, 2, 3, 4);
        List<Integer> b = List.of(3, 4, 5, 6);

        Set<Integer> onlyInA = new HashSet<>(a);
        onlyInA.removeAll(b);
        System.out.println(onlyInA);
        
        Set<Integer> onlyInB = new HashSet<>(b);
        onlyInB.removeAll(a);
        System.out.println(onlyInB);

        onlyInA.addAll(onlyInB); // onlyInA now contains the symmetric difference
    }
}
