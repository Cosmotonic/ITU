import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class sixseven2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String initInput = br.readLine();


        String initInputs[] = initInput.trim().split("");

        boolean foundSix = false; 
        boolean foundPair = false;

        for (int i = 0; i < initInputs.length; i++) {
            if (initInputs[i].equals("6")){
                foundSix = true; 
            }
            if (foundSix && initInputs[i].equals("7")){
                foundPair = true; 
                break;
            }
        }

        if (foundPair){
            System.out.println("six seven");
        } else {
            System.out.println("no");
        }
    }
}