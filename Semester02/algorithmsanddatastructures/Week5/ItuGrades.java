import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

// CODE BY CORBIJN
// 
// Smart løsning. Tre ting der er bedre end min:
// 1. gradeScore returnerer ét tal der indeholder begge bogstav og plus/minus
// baseScore * 100 + modifier — A+++ bliver 703, A--- bliver 697. Så er hele compareTo bare én sammenligning i stedet for dine tre trin.
// 2. record i stedet for class
// record Student(String name, String grade) giver automatisk constructor, getters, equals og toString. Mindre kode.
// 3. BufferedReader i stedet for Scanner
// Hurtigere til store input — Scanner er langsom.
// Det smarte trick er baseScore * 100 — det sikrer at bogstavet altid vejer mere end plus/minus, så du aldrig kan få A- til at slå B+++.


public class ItuGrades {
    record Student(String name, String grade) {
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int inputLength = Integer.parseInt(br.readLine());
        Student[] students = new Student[inputLength];

        for (int i = 0; i < inputLength; i++) {
            String[] input = br.readLine().split(" ");
            String studentname = input[0];
            String grade = input[1];
            students[i] = new Student(studentname, grade);
        }

        // Arrays.sort tager et array og en Comparator — den bruger byGrade til at sammenligne studerende.
        // Dog bruger Arrays.sort ikke merge sort — det er Timsort, som er en hybrid af merge sort og insertion sort.
        Arrays.sort(students, byGrade);

        for (Student student : students) {
            System.out.println(student.name);
        }
    }

    // Bliver brugt som comparator foroven i Arrays.sort
    private static Comparator<Student> byGrade = (Student studentA, Student studentB) -> {
        int score1 = gradeScore(studentA.grade);
        int score2 = gradeScore(studentB.grade);

        if (score1 != score2) {
            return score2 - score1;
        } else {
            return studentA.name.compareTo(studentB.name); // 0 = equal, 
        }
    };

    private static int gradeScore(String grade) {
        int baseScore = 0;
        int modifierStart = 1;

        if (grade.startsWith("A")) {
            baseScore = 7;
        } else if (grade.startsWith("B")) {
            baseScore = 6;
        } else if (grade.startsWith("C")) {
            baseScore = 5;
        } else if (grade.startsWith("D")) {
            baseScore = 4;
        } else if (grade.startsWith("E")) {
            baseScore = 3;
        } else if (grade.startsWith("FX")) {
            baseScore = 2;
            modifierStart = 2;
        } else { // grade F
            baseScore = 1;
        }

        String modifiers = grade.substring(modifierStart);

        int modifier = 0;
        if (!modifiers.isEmpty()) {
            if (modifiers.charAt(0) == '+')
                modifier = modifiers.length();
            if (modifiers.charAt(0) == '-')
                modifier = -modifiers.length();
        }

        return baseScore * 100 + modifier;
    }
}