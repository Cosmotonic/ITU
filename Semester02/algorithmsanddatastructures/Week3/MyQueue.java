import java.util.Iterator;
import java.util.LinkedList;

public class MyQueue<Item> implements Iterable<Item> {

   private LinkedList<Item> linkedList = new LinkedList<>();

   static class Knight {
      int id;
      int hp;
      int str;
      String name; 

      Knight(int id, int hp, int str, String name) {
         this.id = id;
         this.hp = hp;
         this.str = str;
         this.name = name; 
      }
   }

   public void enqueue(Item item) {
      linkedList.addLast(item);
   }

   public Item dequeue() {
      return linkedList.removeFirst();
   }

   public boolean isEmpty() {
      return linkedList.isEmpty();
   }

   public int size() {
      return linkedList.size();
   }

   @Override
   public Iterator<Item> iterator() {
      return linkedList.iterator();
   }

   public static void main(String[] args) {

      // clean linkedListS
      LinkedList<Knight> knightList = new LinkedList<>();
      knightList.addLast(new Knight(3, 10, 2, "Sir Kapper")); 
      knightList.addLast(new Knight(3, 10, 2, "Sir Franzi"));
      System.out.printf("knightlist peek: %s \n", knightList.peek().name);
      knightList.removeFirst();
      System.out.printf("knightlist peek: %s \n", knightList.peek().name);

      // String que 
      MyQueue<String> stringAueue = new MyQueue<>();
      stringAueue.enqueue("first");
      stringAueue.enqueue("second");

      for (String q : stringAueue) {
         System.out.println(q);
      }

      stringAueue.dequeue();
      stringAueue.enqueue("hello");
      System.out.println(stringAueue.dequeue());
      System.out.println(stringAueue.dequeue());

      // knight que using the knight as "item" to type the que
      MyQueue<Knight> knightQueue = new MyQueue<>();
      knightQueue.enqueue(new Knight(1, 100, 50, "Sir Cadagon 1st"));
      knightQueue.enqueue(new Knight(2, 100, 50, "Robert The Bruce 17th"));

      for (Knight knight : knightQueue){
         System.out.println(knight.name);
      }

   }
}

/*
2* public class Queue<Item> {
 * 
 * private Queue que;
 * private Node first;
 * private Node last;
 * private int N;
 * 
 * private class Node {
 * Item item;
 * Node next;
 * }
 * 
 * // file:///C:/MScSDGit/Semester02/Algorithhms%204th%20Edition%20by%20Robert%
 * 20Sedgewick,%20Kevin%20Wayne%201.pdf
 * // page 151, pdf 164
 * 
 * public void enqueue(Item item) {
 * // Add item to the end of the linkedList.
 * Node oldlast = last;
 * last = new Node();
 * last.item = item;
 * last.next = null;
 * if (isEmpty())
 * first = last;
 * else
 * oldlast.next = last;
 * N++;
 * }
 * 
 * public boolean isEmpty() {
 * return first == null;
 * } // Or: N == 0.
 * 
 * int nr;
 * 
 * public static void main(String[] args) {
 * new Queue();
 * }
 * 
 * public Queue() {
 * nr = 0;
 * recur();
 * 
 * }
 * 
 * public void recur() {
 * 
 * System.out.println("Recur");
 * while (nr < 3) {
 * System.out.printf("Nr.: %d", nr);
 * nr++;
 * recur();
 * }
 * }
 * 
 * }
 * 
 * 
 * 
 * public class Queue<Item> implements Iterable<Item>
 * {
 * private Node first; // link to least recently added node
 * private Node last; // link to most recently added node
 * private int N;
 * // number of items on the queue
 * private class Node
 * {
 * // nested class to define nodes
 * Item item;
 * Node next;
 * }
 * public boolean isEmpty() { return first == null; } // Or: N == 0.
 * public int size() { return N; }
 * public void enqueue(Item item)
 * {
 * // Add item to the end of the linkedList.
 * Node oldlast = last;
 * last = new Node();
 * last.item = item;
 * last.next = null;
 * if (isEmpty()) first = last;
 * else oldlast.next = last;
 * N++;
 * }
 * public Item dequeue()
 * {
 * // Remove item from the beginning of the linkedList.
 * Item item = first.item;
 * first = first.next;
 * if (isEmpty()) last = null;
 * N--;
 * return item;
 * }
 * // See page 155 for iterator() implementation.
 * // See page 150 for test client main().
 * 
 * @Override
 * public Iterator<Item> iterator() {
 * // TODO Auto-generated method stub
 * throw new UnsupportedOperationException("Unimplemented method 'iterator'");
 * }
 * }
 */
