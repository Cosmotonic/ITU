package exceptions;

import java.util.HashMap;

public abstract class Person {
    String name;
    int age;
    String skinColor;
    boolean gender;

    public Person(String name, int age, String skinColor, boolean gender) {
        this.name = name;
        this.age = age;
        this.skinColor = skinColor;
        this.gender = gender;
    }

    public void getBasicInfo() {
        System.out.printf("Name: %s. Age: %d", name, age);
    }

    // HashMap<String, String>
}
