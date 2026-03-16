// stak: Stack = LIFO (push/pop)  // sidste i kø kommer først ud - fylde et hul op. 
// Kø:   queue = FIFO (enqueue/dequeue)// kø i supermarked - første i kø kommer først ud.

import java.util.Iterator; 
import java.util.Stack;

public class notes {

    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();

        while (!q.isEmpty()) {
            stack.push(q.dequeue());
            // [1] // push 1 (FI only, we dont pop so we dont use FO)
            // [1, 2] // push 2 on to 1
            // [1, 2, 3] // push 3 on to 2
        }
        while (!stack.isEmpty()) {
            q.enqueue(stack.pop());
            // [3]
            // [3, 2]
            // [3, 2, 1]
        }
    }

    public void LIFO_example(){

        
        Stack<Integer> S= new Stack<Integer>();
        S.push(1);
        S.push(2);
        Iterator<Integer> I = S.iterator();
        Iterator<Integer> J = S.iterator();
        int x = I.next();
        int y = J.next();
    }
    
}


