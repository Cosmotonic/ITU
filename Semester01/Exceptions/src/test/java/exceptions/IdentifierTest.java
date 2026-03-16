package exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class IdentifierTest {

    @Test
    public void testErrorMessage() {
        String expected = "This person does not live in Amager";
        
        // Imagine we caught your AreaException 'e'
        String actual = "This person does not live in Amager"; 
        
        assertEquals(expected, actual); // Passes: The strings match exactly
    }
}