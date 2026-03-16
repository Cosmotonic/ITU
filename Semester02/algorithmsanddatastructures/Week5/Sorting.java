package Week5;

public abstract class Sorting {

    static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k]; // copy into Aux 

        int i = lo;         // i left side 
        int j = mid + 1;    // j right side 

        for (int k = lo; k <= hi; k++) {                            // lo 10, hi 20 
            if (i > mid)                                            // 
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (aux[j].compareTo(aux[i]) < 0)
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }
    }

    // ============================================================
    // TOP-DOWN MERGESORT (rekursiv)
    // Del arrayet i to halvdele, sorter rekursivt, flet sammen
    // ============================================================
    static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo)
            return; // stop når subarray har 1 element

        int mid = lo + (hi - lo) / 2; // find midten

        sort(a, aux, lo, mid); // sorter venstre halvdel
        sort(a, aux, mid + 1, hi); // sorter højre halvdel
        merge(a, aux, lo, mid, hi); // flet de to sorterede halvdele
    }

    // ============================================================
    // BOTTOM-UP MERGESORT (iterativ)
    // Ingen rekursion — merger gradvist større bidder
    // størrelse: 1 -> 2 -> 4 -> 8 -> ... -> N
    // ============================================================
    static void sortBottomUp(Comparable[] a, Comparable[] aux) {
        int N = a.length;

        for (int size = 1; size < N; size *= 2) { // fordobl størrelse hver runde
            for (int lo = 0; lo < N - size; lo += size * 2) { // hop frem i arrayet
                int mid = lo + size - 1;
                int hi = Math.min(lo + size * 2 - 1, N - 1); // pas på vi ikke går ud af arrayet
                merge(a, aux, lo, mid, hi);
            }
        }
    }

}

// ============================================================
// SELECTION SORT
// input:    [5, 3, 8, 1, 4]
//
// find min i hele arrayet  → swap 1 til position 0 → [1, 3, 8, 5, 4]
// find min fra position 1  → swap 3 til position 1 → [1, 3, 8, 5, 4]
// find min fra position 2  → swap 4 til position 2 → [1, 3, 4, 5, 8]
// find min fra position 3  → swap 5 til position 3 → [1, 3, 4, 5, 8]
// find min fra position 4  → swap 8 til position 4 → [1, 3, 4, 5, 8]
// resultat: [1, 3, 4, 5, 8]
//
// kigger altid hele resten igennem — ligegyldigt om næsten sorteret
// ============================================================

// ============================================================
// INSERTION SORT
// input: [5, 3, 8, 1, 4]
//
// tag 3 → sammenlign med 5 → 3 < 5 → skub 5 højre → [3, 5, 8, 1, 4]
// tag 8 → sammenlign med 5 → 8 > 5 → stop           → [3, 5, 8, 1, 4]
// tag 1 → sammenlign med 8 → skub 8 → [3, 5, 1, 8, 4]
//       → sammenlign med 5 → skub 5 → [3, 1, 5, 8, 4]
//       → sammenlign med 3 → skub 3 → [1, 3, 5, 8, 4]
// tag 4 → sammenlign med 8 → skub 8 → [1, 3, 5, 4, 8]
//       → sammenlign med 5 → skub 5 → [1, 3, 4, 5, 8]
//       → sammenlign med 3 → 4 > 3  → stop           → [1, 3, 4, 5, 8]
// resultat: [1, 3, 4, 5, 8]
//
// stopper tidligt når elementet er på plads — god på næsten-sorterede arrays
// ============================================================

// ============================================================
// SHELLSORT
// input: [5, 3, 8, 1, 4, 2, 7, 6]   N=8
//
// h=4 (insertion sort med spring 4):
//   sammenlign a[0] og a[4]: 5 og 4 → swap → [4, 3, 8, 1, 5, 2, 7, 6]
//   sammenlign a[1] og a[5]: 3 og 2 → swap → [4, 2, 8, 1, 5, 3, 7, 6]
//   sammenlign a[2] og a[6]: 8 og 7 → swap → [4, 2, 7, 1, 5, 3, 8, 6]
//   sammenlign a[3] og a[7]: 1 og 6 → ok   → [4, 2, 7, 1, 5, 3, 8, 6]
//
// h=1 (normal insertion sort på næsten-sorteret array):
//   [4, 2, 7, 1, 5, 3, 8, 6] → [1, 2, 3, 4, 5, 6, 7, 8]
//
// resultat: [1, 2, 3, 4, 5, 6, 7, 8]
//
// store spring først → elementer rykker langt hurtigt
// til sidst er arrayet næsten sorteret → insertion sort er billig
// ============================================================

// ============================================================
// TOP-DOWN MERGESORT
// input: [5, 3, 8, 1]
//
// split:         [5, 3, 8, 1]
//               /            \
//           [5, 3]          [8, 1]
//           /    \          /    \
//         [5]   [3]       [8]   [1]
//
// merge op:
//   merge [5] og [3]:
//     sammenlign 5 og 3 → tag 3 → resultat: [3]
//     venstre tom       → tag 5 → resultat: [3, 5]
//
//   merge [8] og [1]:
//     sammenlign 8 og 1 → tag 1 → resultat: [1]
//     højre tom         → tag 8 → resultat: [1, 8]
//
//   merge [3, 5] og [1, 8]:
//     sammenlign 3 og 1 → tag 1 → resultat: [1]
//     sammenlign 3 og 8 → tag 3 → resultat: [1, 3]
//     sammenlign 5 og 8 → tag 5 → resultat: [1, 3, 5]
//     højre ikke tom    → tag 8 → resultat: [1, 3, 5, 8]
//
// resultat: [1, 3, 5, 8]
// ============================================================

// ============================================================
// BOTTOM-UP MERGESORT
// input: [5, 3, 8, 1, 4, 2]
//
// size=1 (merge par af enkelt elementer):
//   merge [5] og [3]  → [3, 5]
//   merge [8] og [1]  → [1, 8]
//   merge [4] og [2]  → [2, 4]
//   array: [3, 5, 1, 8, 2, 4]
//
// size=2 (merge par af 2):
//   merge [3, 5] og [1, 8] → [1, 3, 5, 8]
//   merge [2, 4] → kun ét subarray tilbage, kopieres direkte
//   array: [1, 3, 5, 8, 2, 4]
//
// size=4 (merge par af 4):
//   merge [1, 3, 5, 8] og [2, 4]:
//     sammenlign 1 og 2 → tag 1 → [1]
//     sammenlign 3 og 2 → tag 2 → [1, 2]
//     sammenlign 3 og 4 → tag 3 → [1, 2, 3]
//     sammenlign 5 og 4 → tag 4 → [1, 2, 3, 4]
//     højre tom         → tag 5 → [1, 2, 3, 4, 5]
//     højre tom         → tag 8 → [1, 2, 3, 4, 5, 8]
//
// resultat: [1, 2, 3, 4, 5, 8]
//
// samme resultat som top-down, ingen rekursion
// ============================================================
// Algoritme | Tid (avg) | Tid (best) | Ekstra plads | Note
//

// Selection | O(N^2) | O(N^2) | O(1) | Antal swaps er minimalt
// Insertion | O(N^2) | O(N) | O(1) | God på næsten-sorterede
// Shellsort | O(N^1.5) | O(N log N) | O(1) | God til embedded/hardware
// Mergesort | O(N log N) | O(N log N) | O(N) | Garanteret, stabil




// TOP-DOWN MERGESORT — split af [5, 3, 8, 1, 4, 9, 2, 7, 6, 11, 13, 10, 15, 14, 12]
//
//                        [5,3,8,1,4,9,2,7,6,11,13,10,15,14,12]
//                       /                                     \
//          [5,3,8,1,4,9,2]                          [7,6,11,13,10,15,14,12]
//           /            \                            /                   \
//      [5,3,8]         [1,4,9,2]               [7,6,11,13]           [10,15,14,12]
//       /    \           /    \                  /      \               /       \
//    [5,3]  [8]       [1,4]  [9,2]           [7,6]   [11,13]       [10,15]   [14,12]
//    /   \             /  \   /  \            /  \     /   \         /   \     /   \
//  [5]  [3]          [1] [4][9] [2]         [7] [6] [11] [13]    [10] [15] [14] [12]
//
// --- merger opad ---
//
//  [5][3]     → merge → [3,5]
//  [3,5][8]   → merge → [3,5,8]
//  [1][4]     → merge → [1,4]
//  [9][2]     → merge → [2,9]
//  [1,4][2,9] → merge → [1,2,4,9]
//  [3,5,8][1,2,4,9] → merge → [1,2,3,4,5,8,9]
//  ... og så videre op ad højre side
//
// resultat: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]

// 
// SORT:
// 
//                     [E A S Y Q U E S T I O N]
//                    /                          \
//           [E A S Y Q U]                [E S T I O N]
//           /            \               /            \
//       [E A S]        [Y Q U]      [E S T]        [I O N]
//       /     \        /     \      /     \        /     \
//    [E A]   [S]   [Y Q]   [U]  [E S]   [T]   [I O]   [N]
//    /   \          /  \          /  \          /  \
//  [E]  [A]       [Y] [Q]       [E] [S]       [I] [O]
// 
// MERGE:
// 
//  [E]  [A]       [Y] [Q]       [E] [S]       [I] [O]
//    \   /          \  /          \  /          \  /
//   [A E]          [Q Y]          [E S]          [I O]
//      \    [S]       \    [U]       \    [T]       \    [N]
//       \   /          \   /          \   /          \   /
//     [A E S]        [Q U Y]        [E S T]        [I N O]
//           \        /                    \        /
//       [A E Q S U Y]                [E I N O S T]
//                     \              /
//               [A E E I N O Q S S T U Y]
// 

