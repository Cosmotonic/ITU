// id = each building has a position, at the position is the parent number 
// sz = the size of the the building number. Then you can compare set sizes 

// Output: Efter hver ny forbindelse skal du printe det samlede antal bygninger i den pågældende gruppe.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class bridgesandtunnels {

    private static HashMap<String, Integer> buildings;
    private static int counter;
    private static int[] id;
    private static int[] sz;

    public static void main(String[] args) throws IOException {

        buildings = new HashMap<>();
        counter = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] input = br.readLine().split("\\s");
        int connectionAmount = Integer.parseInt(input[0]);

        id = new int[connectionAmount*2];
        sz = new int[connectionAmount*2];

        // not sure this is correct
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
            sz[i] = 1;
        }

        int i = 0;
        while (i < connectionAmount) {
            String[] buildingInput = br.readLine().split("\\s");

            for (String building : buildingInput) {

                if (!buildings.containsKey(building)) {
                    buildings.put(building, counter);
                    counter++;
                }
            }

            // get the roots
            int rootA = root(buildings.get(buildingInput[0])); 
            int rootB = root(buildings.get(buildingInput[1])); 

            int biggest = union(rootA, rootB); 

            // print size of biggest tree from sz
            System.out.println(biggest);

            i++;
            // System.out.println(buildings);
        }

    }

    // Find med path compression
    private static int root(int i) {
        while (i != id[i]) {            // Hvis i er er id[i] så er den en root. 
            id[i] = id[id[i]];          // tag rooten til nuværende led
            i = id[i];                  // test om nuværende led er root 
        }
        return i;
    }

    private static int union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) // Same parent
            return sz[i];  

        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
            return sz[j]; 
        } else {
            id[j] = i;
            sz[i] += sz[j];
            return sz[i]; 
        } 
    }
}
