import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Streams {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Anna", "bob", "Alice", "Charlie", "Alex");
        List<Integer> nrs = Arrays.asList(1, 2, 3, 4);

        // .filter() keeps only elements that matches the conditions.
        names.stream().filter(name -> name.startsWith("A")).forEach(System.out::println);
        names.stream().filter(name -> name.length() < 5).forEach(System.out::println);

        // .collect(Collectors.toList()) put results into a new List.
        List<String> aNames = names.stream().filter(name -> name.startsWith("A")).collect(Collectors.toList());

        long evenCount = nrs.stream().filter(n -> n % 2 == 0).count(); // count the amount of even numbers
        long higherthan = nrs.stream().filter(n -> n > 3).count(); // count numbers over 3

        System.out.println("Higher than 3: " + higherthan);
        System.out.println("Even numbers: " + evenCount);

        // .map() transforms each element — in this case, from lowercase to uppercase.
        names.stream().map(name -> name.toUpperCase()).forEach(System.out::println);
        names.stream().map(name -> name.length()).forEach(System.out::println);

        List<String> uNames = names.stream().map(name -> name.toUpperCase()).collect(Collectors.toList()); // .collect(Collectors.toList())
                                                                                                           // put
                                                                                                           // results
                                                                                                           // into a new
                                                                                                           // List.
        System.out.println("Upper list: " + uNames);

        // .sorted() sorts elements in natural order (alphabetically for strings).
        names.stream().sorted().forEach(System.out::println); // orders alphabetically
        names.stream().sorted(Comparator.comparingInt(String::length)).forEach(System.out::println); // orders by length
                                                                                                     // of name
        // .distinct() fjerner dubletter, så du kun har unikke værdier tilbage.
        List<Integer> numbers = Arrays.asList(1, 1, 2, 2, 3, 3);
        numbers.stream().distinct().forEach(System.out::println); // Udskriver: 1, 2, 3

        // .limit(n) stopper strømmen efter de første n elementer.
        names.stream().limit(3).forEach(System.out::println); // Tager kun de 3 første navne

        // .anyMatch() returnerer en boolean (true/false) hvis MINDST ét element
        // matcher.
        boolean startsWithA = names.stream().anyMatch(name -> name.startsWith("A"));
        System.out.println("Er der nogen der starter med A? " + startsWithA);

        // .allMatch() returnerer true, hvis ALLE elementer matcher betingelsen.
        boolean allLongerThan2 = names.stream().allMatch(name -> name.length() > 2);
        System.out.println("Er alle navne længere end 2 tegn? " + allLongerThan2);

        // .noneMatch() returnerer true, hvis INGEN elementer matcher betingelsen.
        boolean noneEmpty = names.stream().noneMatch(name -> name.isEmpty());
        System.out.println("Er der ingen tomme navne? " + noneEmpty);
        // System.out.println(names);

        // nrs.stream().filter(nr -> nr.)

    }
}

/*
 * // MAKE AN ASSIGNMENT WITH EACH OF THESE. especailly the lower ones.
 * 
 * 💡 Quick Reference (Common Stream Methods)
 * Method What it does
 * filter() Keeps elements that match a condition
 * map() Transforms each element
 * sorted() Sorts the stream
 * forEach() Performs an action for each element
 * collect() Turns the stream into a list, set, etc.
 * count() Counts how many elements remain
 * distinct() Removes duplicates
 * limit(n) Takes the first n elements
 * anyMatch() Checks if any element matches a condition
 * 
 */