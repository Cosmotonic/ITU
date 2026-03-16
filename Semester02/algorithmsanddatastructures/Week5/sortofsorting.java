import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class sortofsorting {
    record Student(String lastName) {
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int antalNavne;
        boolean firstRound = true;

        while ((antalNavne = Integer.parseInt(br.readLine())) != 0) {
            if (!firstRound) {
                System.out.println("");
            }

            Student[] students = new Student[antalNavne];

            for (int i = 0; i < antalNavne; i++) {
                String lastName = br.readLine();
                students[i] = new Student(lastName);
            }

            Arrays.sort(students, byLastName);

            for (Student s : students) {
                System.out.println(s.lastName);
            }

            firstRound = false;
        }
    }

    private static Comparator<Student> byLastName = (Student studentA, Student studentB) -> {
        String subA = studentA.lastName.substring(0, 2);
        String subB = studentB.lastName.substring(0, 2);
        return subA.compareTo(subB); // 0 = equal,
    };
}
