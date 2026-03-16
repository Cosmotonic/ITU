package Week5;

import java.util.HashMap;
import java.util.Scanner;

public class itu_grades {

    static class Student implements Comparable<Student> {
        String name;
        String grade;
        int letterGrade;
        int extensionCount; 

        Student(String name, String grade) {
            this.name = name;
            this.grade = grade;
            this.letterGrade = GradeScore.get(grade.replaceAll("[^A-Z]", ""));
            this.extensionCount = grade.replaceAll("[^+]", "").length() 
                                - grade.replaceAll("[^-]", "").length();
        }

        @Override
        public int compareTo(Student otherStudent) {

            // 100% same grade? 
            if (otherStudent.grade.equals(grade)) {
                return name.compareTo(otherStudent.name);
            }

            // Compare letter score
            if (letterGrade > otherStudent.letterGrade) {
                return -1;
            } else if (letterGrade < otherStudent.letterGrade) {
                return 1;

            // Same letter grade? Compare + & -
            } else {
                if (this.extensionCount > otherStudent.extensionCount) {
                    return -1;
                } else if (this.extensionCount < otherStudent.extensionCount) {
                    return 1;
                }
                return 0;
            }
        }

        static final HashMap<String, Integer> GradeScore = new HashMap<>();
        static {
            GradeScore.put("A", 6);
            GradeScore.put("B", 5);
            GradeScore.put("C", 4);
            GradeScore.put("D", 3);
            GradeScore.put("E", 2);
            GradeScore.put("FX", 1);
            GradeScore.put("F", 0);
        }
    }

    Student[] students;
    Student[] aux;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        itu_grades grades = new itu_grades(sc); // initialize and populate students array.
        // System.out.println("student length: " + grades.students.length);
        sort(grades.students, grades.aux, 0, grades.students.length - 1);

        // first grade - then name
        prtStudent(false, grades.students);

        sc.close();
    }

    static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo)
            return; // stop når subarray har 1 element

        int mid = lo + (hi - lo) / 2; // find midten

        sort(a, aux, lo, mid); // sorter venstre halvdel
        sort(a, aux, mid + 1, hi); // sorter højre halvdel
        merge(a, aux, lo, mid, hi); // flet de to sorterede halvdele
    }

    static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        for (int k = lo; k <= hi; k++)
            aux[k] = a[k]; // copy into Aux

        int i = lo; // i left side
        int j = mid + 1; // j right side

        for (int k = lo; k <= hi; k++) { // lo 10, hi 20
            if (i > mid) //
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (aux[j].compareTo(aux[i]) < 0)
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }
    }

    public itu_grades(Scanner sc) {
        // Initialize student list.
        int numberOfStudents = Integer.parseInt(sc.nextLine().trim());
        students = new Student[numberOfStudents];
        aux = new Student[numberOfStudents];

        for (int i = 0; i < numberOfStudents; i++) {
            String[] studentLine = sc.nextLine().split("\\s");
            Student student = new Student(studentLine[0], studentLine[1]);
            students[i] = student;
        }
    }

    public static void prtStudent(Boolean includeGrade, Student[] students) {
        for (Student student : students) {
            if (!includeGrade) {
                System.out.printf("%s \n", student.name);
            }
            if (includeGrade) {
                System.out.printf("%s  ", student.name);
                System.out.printf("Grade: %s\n", student.grade);
            }
        }
    }
}
