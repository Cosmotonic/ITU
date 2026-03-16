package Week4; 

public class Animals 
{
    static class Node {
    char c;
    Node next;
    }

    static Node build(String C) {
    Node prev = null;
    for (int i = C.length() - 1; i >= 0; --i) {
        Node fresh = new Node();
        fresh.next = prev;
        fresh.c = C.charAt(i);
        prev = fresh;
    }
    return prev;
    }

    public static void main(String[] args) {
    Node S = build("|**...|..*|*..|..|");

    Node current = S;
    int animals_in_current_room = 0;
    while (true) {
        while (current.c != '|') {
        if (current.c == '*') 
            animals_in_current_room += 1;
        if (animals_in_current_room == 2) {
            System.out.println("unsafe!");
            return;
        }
        current = current.next;
        }
        animals_in_current_room = 0;
        if (current.next != null)
        current = current.next;
        else
        break;
    }
    System.out.println("safe!");
    }
}