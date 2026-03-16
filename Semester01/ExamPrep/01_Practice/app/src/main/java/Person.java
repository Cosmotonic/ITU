
public abstract class Person {
    String name;
    int age;
    String skinColor;
    Gender gender;

    public enum Gender { MALE, FEMALE, OTHER }

    public Person(String name, int age, String skinColor, Gender gender) {
        this.name = name;
        this.age = age;
        this.skinColor = skinColor;
        this.gender = gender;
    }


    public void getBasicInfo() {
        System.out.printf("Name: %s. Age: %d", name, age);
    }

    public Enum getGender(){
        return gender; 
    }

    // HashMap<String, String>
}
