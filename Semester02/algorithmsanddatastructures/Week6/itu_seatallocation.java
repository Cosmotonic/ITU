package Week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class itu_seatallocation<Key extends Comparable<Key>> {

   record Party(int votes, int seats) {
   }

   private Key[] pq;
   private int n;

   public static void main(String[] args) throws IOException {

      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String[] partiesAndSeats = br.readLine().split(" ");
      int totalParties = Integer.parseInt(partiesAndSeats[0]);
      int totalSeats = Integer.parseInt(partiesAndSeats[1]);

      // Party[] parties = new Party[totalParties+1];
      int[] seats = new int[totalParties + 1];
      int[] votes = new int[totalParties + 1];

      itu_seatallocation<Integer> pq = new itu_seatallocation<>(totalParties);

      // Først mapper du alle inputs. husk nul skal være første værdi. Så party1
      // bliver index1. // så loop over parties.
      for (int index = 1; index < totalParties + 1; index++) {
         // if (index==0){continue;} // COULD BE A BUG HERE: index 1 instead of 0
         int inputVotes = Integer.parseInt(br.readLine());
         votes[index] = inputVotes;
         seats[index] = 0; // init seats
         // System.out.println(parties[index]);
      }

      // Dererfter ska jeg tælle seats til hvert prati. (evt med party class)
      for (int i = 0; i < totalSeats; i++) {
         double coefficient = 0.0;
         int seatWinner = 0;
         for (int index = 1; index < totalParties + 1; index++) {
            double tempCo = (double) votes[index] / (seats[index] + 1);
            if (tempCo > coefficient) {
               coefficient = tempCo;
               seatWinner = index;
            }
         }
         seats[seatWinner]++;
      }

      for (int index = 1; index < totalParties + 1; index++) {
         System.out.println(seats[index]);
      }
      
   }

   public itu_seatallocation(int capacity) {
      pq = (Key[]) new Comparable[capacity + 1];
   }

   public boolean isEmpty() {
      return n == 0;
   }

   public void insert(Key x) {
      n += 1;
      pq[n] = x;
      swim(n);
   }

   public Key delMax() {
      Key max = pq[1];
      exch(1, n);
      n -= 1;
      sink(1);
      pq[n + 1] = null;
      return max;
   }

   public void swim(int k) {
      while (k > 1 && less(k / 2, k)) {
         exch(k, k / 2);
         k = k / 2;
      }
   }

   public void sink(int k) {
      while (2 * k <= n) {
         int j = 2 * k;
         if (j < n && less(j, j + 1)) {
            j += 1;
         }
         if (!less(k, j)) {
            break;
         }
         exch(k, j);
         k = j;
      }
   }

   public boolean less(int i, int j) {
      return pq[i].compareTo(pq[j]) < 0;
   }

   private void exch(int i, int j) {
      Key temp = pq[i];
      pq[i] = pq[j];
      pq[j] = temp;
   }
}