package Week4; 


// page 179 in the book. 

public class OrderOfGrowth {

    // O(1) - Constant
    static int constant(int b, int c) {
        int a = b + c;
        return a;
    }

    // O(log N) - Logarithmic (binary search)
    static int logarithmic(int[] a, int key) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else                   return mid;
        }
        return -1;
    }

    // O(N) - Linear (find the maximum)
    static double linear(double[] a) {
        double max = a[0];
        for (int i = 1; i < a.length; i++)
            if (a[i] > max) max = a[i];
        return max;
    }

    // O(N log N) - Linearithmic (mergesort)
    static void linearithmic(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        linearithmic(a, lo, mid);
        linearithmic(a, mid + 1, hi);
        merge(a, lo, mid, hi);

        // mergeSort(a, 0, a.length);
    }

    private static void merge(int[] a, int lo, int mid, int hi) {
        int[] aux = a.clone();
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)          a[k] = aux[j++];
            else if (j > hi)           a[k] = aux[i++];
            else if (aux[j] < aux[i])  a[k] = aux[j++];
            else                       a[k] = aux[i++];
        }
    }

    // O(N^2) - Quadratic (check all pairs) ( Dobbelt lineær = kvadratisk.)
    static int quadratic(int[] a) {
        int cnt = 0;
        for (int i = 0; i < a.length; i++)
            for (int j = i + 1; j < a.length; j++)
                if (a[i] + a[j] == 0) cnt++;
        return cnt;
    }

    // O(N^3) - Cubic (check all triples)
    static int cubic(int[] a) {
        int cnt = 0;
        for (int i = 0; i < a.length; i++)
            for (int j = i + 1; j < a.length; j++)
                for (int k = j + 1; k < a.length; k++)
                    if (a[i] + a[j] + a[k] == 0) cnt++;
        return cnt;
    }

    // O(2^N) - Exponential (check all subsets)
    static void exponential(int[] a) {
        int n = a.length;
        for (int mask = 0; mask < (1 << n); mask++) {
            for (int i = 0; i < n; i++)
                if ((mask & (1 << i)) != 0)
                    System.out.print(a[i] + " ");
            System.out.println();
        }
    }
}




// Examples 


// eksponential - for every call it makes a new call 
int fib(int n) {
    if (n <= 1) return n;
    return fib(n-1) + fib(n-2);
/*
    fib(4)
        fib(3) + fib(2)
            fib(2) + fib(1)         fib(1) + fib(0)
                fib(1) + fib(0)
                    1  +  0             1   +   0
                = 1
            = 1 + 1 = 2         = 1 + 0 = 1
        = 2 + 1 = 3
    = 3
*/
}


// N log N er korrekt. k*2 fordobler hver gang — det er log N. Kombineret med det ydre loop er det N log N.

for (int i = 0; i < N; i++) {
    int k = 1;
    while (k < N) {
        k = k * 2;
    }
}