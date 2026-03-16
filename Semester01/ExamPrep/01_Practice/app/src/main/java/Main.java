import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        // var arr = new int[3];
        // System.out.println(arr.length);
        // List<String> emails = new ArrayList<>();
        // myMethod("hello", emails);

        double a = 5.0; 
        double b = 0.0; 

        System.out.println(b/a);

        String s1 = "Kapper peter ole john niels frederik mathias constantin"; 
        String s2 = "Kapper Kapper Kapper Kapper Kapper Kapper Kapper Kapper"; 

        Set<String> mySet = new HashSet<>();
        var words = s2.split(" ");
        for (var w : words) {
            mySet.add(w);
        }

        System.out.println(words.length);

        // System.out.println("running main");
        // Identifier identify = new Identifier("Kapper", "white", Person.Gender.MALE,
        // 35, 2300);
        // System.out.println(identify.getGender());

        // int age = identify.getAge();
        // int area = identify.getAreaCode();
        // System.out.println(age);
        // System.out.println(area);
    }

    private static void myMethod(String match, List<String> emails) {
        var itr = emails.iterator();
        while (itr.hasNext()) {
            if (itr.next().equals(match)) {
                emails.remove(match);
            }
        }
    }

    private static void myMethoda(String match, List<String> emails) {
        var itr = emails.iterator();
        while (itr.hasNext()) {
            if (itr.next().equals(match)) {
                emails.remove(match);
            }
        }
    }
}