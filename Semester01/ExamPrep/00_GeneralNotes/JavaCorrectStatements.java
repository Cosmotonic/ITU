/**
 * JAVA CONCEPTUAL REPOSITORY - CORRECT STATEMENTS ONLY
 * This file contains confirmed correct logic from Exam Examples 1, 2, 3, 4, and
 * IP2024.
 */
public class JavaCorrectStatements {

    // --- SCOPE & ACCESSIBILITY ---
    public void scopeLogic() {
        [cite_start]// Local variables are accessible only in the code block where they are defined[cite: 860, 2221].
        [cite_start]// Local variables can be defined in methods and constructors[cite: 2223].
        [cite_start]// Parameters of a method (like 'int p') are only accessible in the body of that method[cite: 864].
        [cite_start]// Fields (Instance variables) are accessible to all methods in the class[cite: 858, 2222].
    }

    // --- CLASS DESIGN & COHESION ---
    public void designPrinciples() {
        [cite_start]// High cohesion increases readability and is desirable in good class design[cite: 1167, 1173].
        [cite_start]// Low coupling is desirable in good class design[cite: 1169].
        [cite_start]// Code duplication is NOT desirable in good class design[cite: 1171, 1627].
        [cite_start]// High cohesion means a code unit is responsible for a single logical task[cite: 1171].
    }

    // --- INHERITANCE & INTERFACES ---
    public void inheritanceRules() {
        [cite_start]// Object is a superclass of all classes in Java[cite: 734].
        [cite_start]// Each class can extend at most one class[cite: 1621].
        [cite_start]// An advantage of inheritance is avoiding code duplication[cite: 740].
        [cite_start]// A class can implement multiple interfaces[cite: 675, 2250].
        [cite_start]// An interface can extend multiple interfaces[cite: 2249]. - but not classes, netiehr can it implemnt interfaces. 
        [cite_start]// If a class B implements interface A, it can also implement other interfaces[cite: 675].
    }

    // --- ABSTRACT CLASSES & CONSTRUCTORS ---
    public void objectCreation() {
        [cite_start]// Instances of an abstract class CANNOT be created[cite: 1623, 2430].
        [cite_start]// If multiple constructors exist, they must have different signatures[cite: 800].
        [cite_start]// Subclasses must implement abstract methods (like 'balance()') to be non-abstract[cite: 2431].
        [cite_start]// The first line of a subclass constructor must invoke the super constructor (explicitly or implicitly)[cite: 317].
    }

    // --- ARRAYS, LISTS & MAPS ---
    public void collectionsLogic() {
        [cite_start]// The value of elements in a 'final' array CAN be changed; the reference cannot[cite: 169, 686, 1373].
        // Trying to .add() to a fixed-size list (like List.of) throws an UnsupportedOperationException.
        [cite_start]// To safely remove items from a collection during a loop, use an Iterator's remove() method[cite: 1201, 2358].
        [cite_start]// Using fruits.remove() inside a for-each loop causes a ConcurrentModificationException[cite: 2341, 2345].
    }

    // --- EXCEPTIONS ---
    public void exceptionHandling() {
        [cite_start]// The 'finally' block always runs, regardless of whether an exception was thrown or caught[cite: 359, 1355, 2439].
        [cite_start]// Any checked exception thrown by a method must be declared in the method signature[cite: 302].
        [cite_start]// Checked exceptions are NOT subclasses of RuntimeException[cite: 298].
    }

    // --- FLOATING POINT & PRIMITIVES ---
    public void primitiveLogic() {
        [cite_start]// Primitive types include: int, char, boolean, long, float, double [cite: 150-158].
        [cite_start]// String and Student are NOT primitive types; they are Reference types[cite: 160, 1206, 1208].
        [cite_start]// 0.0 / 0.0 results in NaN (Not a Number)[cite: 2066, 2377].
        [cite_start]// 1.0 / 0.0 results in +Inf (Positive Infinity)[cite: 1521, 2068, 2375].
    }

    /**
     * JAVA CONCEPTUAL REPOSITORY - CORRECT STATEMENTS ONLY
     * This file contains confirmed correct logic from Exam Examples 1, 2, 3, 4, and
     * IP2024.
     */
    public class JavaCorrectStatements {

        // --- SCOPE & ACCESSIBILITY ---
    public void scopeLogic() {
        [cite_start]// Local variables are accessible only in the code block where they are defined[cite: 860, 2221].
        [cite_start]// Local variables can be defined in methods and constructors[cite: 2223].
        [cite_start]// Parameters of a method (like 'int p') are only accessible in the body of that method[cite: 864].
        [cite_start]// Fields (Instance variables) are accessible to all methods in the class[cite: 858, 2222].
    }

        // --- CLASS DESIGN & COHESION ---
    public void designPrinciples() {
        [cite_start]// High cohesion increases readability and is desirable in good class design[cite: 1167, 1173].
        [cite_start]// Low coupling is desirable in good class design[cite: 1169].
        [cite_start]// Code duplication is NOT desirable in good class design[cite: 1171, 1627].
        [cite_start]// High cohesion means a code unit is responsible for a single logical task[cite: 1171].
    }

        // --- INHERITANCE & INTERFACES ---
    public void inheritanceRules() {
        [cite_start]// Object is a superclass of all classes in Java[cite: 734].
        [cite_start]// Each class can extend at most one class[cite: 1621].
        [cite_start]// An advantage of inheritance is avoiding code duplication[cite: 740].
        [cite_start]// A class can implement multiple interfaces[cite: 675, 2250].
        [cite_start]// An interface can extend multiple interfaces[cite: 2249].
        [cite_start]// If a class B implements interface A, it can also implement other interfaces[cite: 675].
    }

        // --- ABSTRACT CLASSES & CONSTRUCTORS ---
    public void objectCreation() {
        [cite_start]// Instances of an abstract class CANNOT be created[cite: 1623, 2430].
        [cite_start]// If multiple constructors exist, they must have different signatures[cite: 800].
        [cite_start]// Subclasses must implement abstract methods (like 'balance()') to be non-abstract[cite: 2431].
        [cite_start]// The first line of a subclass constructor must invoke the super constructor (explicitly or implicitly)[cite: 317].
    }

        // --- ARRAYS, LISTS & MAPS ---
    public void collectionsLogic() {
        [cite_start]// The value of elements in a 'final' array CAN be changed; the reference cannot[cite: 169, 686, 1373].
        // Trying to .add() to a fixed-size list (like List.of) throws an UnsupportedOperationException.
        [cite_start]// To safely remove items from a collection during a loop, use an Iterator's remove() method[cite: 1201, 2358].
        [cite_start]// Using fruits.remove() inside a for-each loop causes a ConcurrentModificationException[cite: 2341, 2345].
    }

        // --- EXCEPTIONS ---
    public void exceptionHandling() {
        [cite_start]// The 'finally' block always runs, regardless of whether an exception was thrown or caught[cite: 359, 1355, 2439].
        [cite_start]// Any checked exception thrown by a method must be declared in the method signature[cite: 302].
        [cite_start]// Checked exceptions are NOT subclasses of RuntimeException[cite: 298].
    }

        // --- FLOATING POINT & PRIMITIVES ---
    public void primitiveLogic() {
        [cite_start]// Primitive types include: int, char, boolean, long, float, double [cite: 150-158].
        [cite_start]// String and Student are NOT primitive types; they are Reference types[cite: 160, 1206, 1208].
        [cite_start]// 0.0 / 0.0 results in NaN (Not a Number)[cite: 2066, 2377].
        [cite_start]// 1.0 / 0.0 results in +Inf (Positive Infinity)[cite: 1521, 2068, 2375].
    }

/**
 * JAVA FUNDAMENTALS - LOOKUP & CORRECT STATEMENTS
 * Contains logic from Exam Sets 1-4 and IP2024.
 */
public class JavaFundamentalsCheatSheet {

    // --- DATA TYPES: THE CORE DIFFERENCE ---
    public void dataTypes() {
        // PRIMITIVES: Simple values (int, double, boolean, long, char, float). [cite: 150-158]
        // DEFAULT VALUES: Primitives like 'int' default to 0. [cite: 2155]
        // REFERENCE TYPES: Objects (String, Student, Arrays, List). [cite: 1206-1211]
        // NULL: Only Reference types can be null. Primitives CANNOT. [cite: 1881-1889]
        // COMPARISON: Use == for primitives; use .equals() for objects (Strings/Integers). [cite: 643-645, 2040]
    }

    // --- COLLECTIONS: HOW DATA IS ORGANIZED ---
    public void collectionsLookup() {
        // LIST: Ordered sequence. Allows duplicates. Access via index (e.g., list.get(i)). [cite: 233-247]
        // SET: Unordered unique values. Automatically ignores duplicate adds. [cite: 394-403, 1221]
        // MAP: Key-Value pairs. Use key to get value (e.g., map.get("Kasper")). [cite: 191-200, 2122]
        // ITERATOR: Used to safely remove items during a loop. [cite: 1186-1202, 2354-2358]
    }

    // --- RECURRING EXAM LOGIC ---
    public void examLogic() {
        // STATIC: Fields/methods belong to the Class. Shared by ALL objects. [cite: 1242, 2152, 2296]
        // FINAL: Value cannot change once set. Note: final arrays can have elements changed, but not the array itself. [cite: 163-171, 1365-1373]
        // CONSTRUCTOR: No return type (not even void!). Must match Class name exactly. [cite: 2132-2136]
        // SUPER(): Must be the first call in a subclass constructor. [cite: 200, 2364]
        // FINALLY: Block that ALWAYS executes after try/catch. [cite: 359, 653, 2439]
    }

    // --- MATH & FLOATING POINT ---
    public void mathPitfalls() {
        // INTEGER DIVISION: (int) 5 / 2 results in 2 (it cuts the decimals).
        // MODULO (%): Returns the remainder. 7 % 3 is 1. [cite: 105, 802, 2184]
        // NaN: Not a Number. Occurs if you do 0.0 / 0.0. [cite: 392, 1519, 2066]
        // +INF: Positive Infinity. Occurs if you do 1.0 / 0.0. [cite: 387, 1521, 2068]
    }

    public void booleanVariables(){
       //  c = !a && !b ? false : true; - both a and b are false then the answer is also false.
       //  c = !a && !b ? false : a || b; - both a and b are false so this is also false.
    }
}
    }
}


