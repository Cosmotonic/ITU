package exceptions; 

public class utilities {
    public static void main(String[] args) {

        try{
            int dividing = 10 / 0; 
            System.out.println("Result from dividing: " + dividing + "." );
        }catch(ArithmeticException e ){
            System.out.println("Error: You cannot divide 10 by 0");
        }finally{
            System.out.println("Cleaning up resources... ");
        }

        System.out.println("Program continues");
        
    }
}