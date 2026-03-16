import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class kingskeep {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int inputLength = Integer.parseInt(br.readLine());
        
        int[] x = new int[inputLength];
        int[] y = new int[inputLength];

        for (int i = 0; i < inputLength; i++) {
            String[] input = br.readLine().split(" ");
            x[i] = Integer.parseInt(input[0]);
            y[i] = Integer.parseInt(input[1]);
        }

        double minAvg = 0.0; 

        for (int i = 0; i < inputLength; i++) {
            double dist = 0;
            for (int j = 0; j < inputLength; j++) {
                if (i == j) continue; 
                dist += distance(x[i], y[i], x[j], y[j]); // jeg skal samle den total længde mellem alle punker
            }
            
            double avg = dist / (inputLength - 1);
            if (avg < minAvg) {
                minAvg = avg;
            }   
        }

        System.out.println(minAvg);
    }

    public static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}