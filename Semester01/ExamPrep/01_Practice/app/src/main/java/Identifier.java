
import exception.AgeException;
import exception.AreaException;

public class Identifier extends Person {
    private int areaCode;
    
    public Identifier(String name, String skinColor, Gender gender, int age,  int areaCode) {
        super(name, age, skinColor, gender); 
        
        this.areaCode = areaCode;
        
        try {
            checkAge(age);
        } catch (AgeException e) {
            System.out.println("Caught Age Error: " + e.getMessage());
        }
        
        try {
            checkAreaCode(areaCode);
        } catch (AreaException e) {
            System.out.println("Caught Area Error: " + e.getMessage());
        }
        
        System.out.println("Program continues");
    }
    
    static void checkAreaCode(int areaCode) throws AreaException {
        if (areaCode != 2300) {
            throw new AreaException("This person does not live in Amager");
        } else {
            System.out.println("Welcome Aamagerkaner.");
        }
    }
    
    static void checkAge(int age) throws AgeException {
        if (age <= 18) {
            throw new AgeException("Access Denied: To enter this site you need to be 18 years or above.");
        } else {
            System.out.println("Welcome into the site");
        }
    }
    
    public int getAge() {
        return age;
    }
    
    public int getAreaCode() {
        return areaCode;
    }
}
