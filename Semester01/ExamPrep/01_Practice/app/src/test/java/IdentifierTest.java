/*
./gradlew test -i
./gradlew clean test

Test setup on learn it: https://docs.gradle.org/current/samples/sample_building_java_applications.html
*/


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IdentifierTest {
    
    private static Identifier identifier; 
    private int rounds; 

    @BeforeAll
    public static void setup(){
        identifier = new Identifier("Kasper", "white", Person.Gender.MALE, 37, 2300);
    }

    @BeforeEach
    public void resetRounds(){
        rounds = 0; 
    }

    @Test
    public void doesAreaCodeExist(){
        assertNotNull(identifier.getAreaCode());
    }    
    
    @Test
    public void isGenderMale(){
        assertNotEquals(Person.Gender.FEMALE, identifier.gender);
    }

    @Test
    public void isNotFemale(){
        assertEquals(Person.Gender.MALE, identifier.gender);
    }

    @Test
    public void isMale(){
        assertFalse(identifier.gender == Person.Gender.FEMALE);
    }

    @Test
    public void checkAge(){
        assertNotEquals(0, identifier.getAge()); 
    }

    @Test
    public void hasName(){
        assertTrue(identifier.name != null); 
    }
}
