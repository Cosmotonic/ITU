/**
 * IP2024-FinalExam-2 LOGIC CHECKLIST
 */
public class FinalExam2_Solutions {

    // 3. CONSTRUCTOR SIGNATURES
    [cite_start]// Correct: (b), (d) [cite: 2134, 2136]
    // Note: Constructors must have the exact class name and NO return type (not even void).

    // 4. CLASS & METHOD INFERENCE
    [cite_start]// Correct: (c) [cite: 2144, 2149]
    // Note: Gameplay() is called without an object prefix inside main, meaning it must be static.

    // 5. STATIC FIELDS & DEFAULTS
    [cite_start]// Correct: (a), (d) [cite: 2152, 2155]
    // Note: Static fields are shared by all objects; int fields default to 0.

    // 6. EXECUTION ORDER (METHOD TRACING)
    [cite_start]// Correct: (c) [cite: 2162]
    // Note: Input '2' goes to else: div(2) runs (L3), then mult(result) runs (L2), then returns (L1).

    // 7. SET SIZE & MODULO
    [cite_start]// Correct: (a) [cite: 2184]
    // Note: num%3 results in 0, 1, or 2. Set stores unique values, so max size is 3, min is 1.

    // 8. LOOP ITERATIONS
    [cite_start]// Correct: (c) [cite: 2190]
    // Note: Condition (x >= 0) means for x=5, it runs for 5,4,3,2,1,0 (6 times, which is x+1).

    // 9. NESTED COLLECTIONS
    [cite_start]// Correct: (b) [cite: 2200]
    // Note: pages.get(0) returns a List<String>; getting from that list returns a String.

    // 10. STREAM FILTER & REDUCE
    [cite_start]// Correct: (c) [cite: 2208]
    // Note: .filter(n->n > m) keeps high values; .reduce(0, (sum, n) -> sum + n) adds them up.

    // 11. CLASS STRUCTURE
    [cite_start]// Correct: (a), (b), (d) [cite: 2212]
    // Note: 'st' and 'A' are reference types; 'top' is local to the method it is defined in.

    // 12. METHOD RETURN TYPES
    [cite_start]// Correct: (d) [cite: 2214]
    // Note: id (String) + wrap() (int) uses string concatenation, so the return type is String.

    // 13. LIST MANIPULATION
    [cite_start]// Correct: (d) [cite: 2218]
    // Note: (1) adds A and L; (2) shifts indices so get(3) and get(5) match "A" and "L".

    // 14. SCOPE & ACCESSIBILITY
    [cite_start]// Correct: (b), (d) [cite: 2221, 2223]
    // Note: Local variables are private to their block {}; fields are accessible to all class methods.

    // 15. INCREMENT/DECREMENT TRAP
    [cite_start]// Correct: (b), (c) [cite: 2226, 2227]
    // Note: i++ (post-increment) sends 7 THEN changes to 8; --i (pre-decrement) changes 8 to 7 THEN sends it.

    // 16. OBJECT REFERENCES
    [cite_start]// Correct: (b) [cite: 2232, 2242]
    // Note: num2 = num1 means both point to the same 5; num1.change(-1) makes both see 4.

    // 17. INTERFACE RULES
    [cite_start]// Correct: (c), (d) [cite: 2249, 2250]
    // Note: Classes can implement many interfaces; interfaces can extend many interfaces.

    // 18. SHORT-CIRCUIT LOOP
    [cite_start]// Correct: (b), (c) [cite: 2258, 2259]
    // Note: If it ran 4 times, index 3 was non-zero; if there's a 5th element (index 4), it must be zero.

    // 19. REGEX MATCHING
    [cite_start]// Correct: (a) [cite: 2262]
    // Note: [a-z] (one letter), \w* (any word chars), \d\d (exactly two digits at the end).

    // 20. STRING SPLIT SIZE
    [cite_start]// Correct: (a) [cite: 2268, 2274]
    // Note: "1110011011" split by "0" creates ["111", "", "11", "11"]. Size is 4.

    // 21. SET ADD & POST-INCREMENT
    [cite_start]// Correct: (b), (c) [cite: 2279, 2283, 2291]
    // Note: add(10) fails if 10 was already there; add(b++) adds 10 then turns b into 11.

    // 22. STATIC FIELDS (SHARED)
    [cite_start]// Correct: (a), (d) [cite: 2303, 2306]
    // Note: Static 'names' is shared; 'b' overwrote 'a' with a 3-length array; a.changeNames affects b's data.

    // 23. TERNARY OPERATOR
    [cite_start]// Correct: (c) [cite: 2331]
    // Note: 'a <= b' is the logical opposite of 'a > b', so the results swap places.

    // 24. ITERATOR REMOVE
    [cite_start]// Correct: (d) [cite: 2358]
    // Note: You must use itr.remove() to avoid ConcurrentModificationException while looping.

    // 25. INHERITANCE & CONSTRUCTORS
    [cite_start]// Correct: (a), (b) [cite: 2364, 2369]
    // Note: super("Init") proves Frame has a String constructor; SSFrame must call SFrame's 2-arg constructor.

    // 26. FLOATING POINT MATH
    [cite_start]// Correct: (c) [cite: 2377]
    // Note: 0.0 / 0.0 results in NaN; the 'if' (NaN == 0.0) fails, returning the divisor 'b' (0.0).

    // 27. INTERFACES & IMPLEMENTATION
    [cite_start]// Correct: (a), (d) [cite: 2388, 2391]
    // Note: You can declare an interface variable (I i); A must implement both methods from I.

    // 28. STATIC VS DYNAMIC TYPE
    [cite_start]// Correct: (d) [cite: 2402]
    // Note: Static type is the parameter label (Person); Dynamic type is the actual object (Student).

    // 29. BRANCH COVERAGE
    [cite_start]// Correct: (a) [cite: 2408]
    // Note: Needs inputs for: length > 5, 3 <= length <= 5, and length < 3.

    // 30. LOGIC TESTING (ASSERTIONS)
    [cite_start]// Correct: (a), (c) [cite: 2415, 2417]
    // Note: Tests require (T, F) -> T and (T, T) -> F. Only (a != b) and the assignment in (c) fit.

    // 31. ABSTRACT CLASSES
    [cite_start]// Correct: (a), (b), (d) [cite: 2428, 2429, 2431]
    // Note: Subclasses must implement abstract methods; you cannot instantiate an abstract class.

    // 32. EXCEPTIONS & FINALLY
    [cite_start]// Correct: (c) [cite: 2443]
    // Note: 0/2 is 0, so it hits the 'else throw ArithmeticException'. Catch prints 'A', Finally prints 'C'.

}