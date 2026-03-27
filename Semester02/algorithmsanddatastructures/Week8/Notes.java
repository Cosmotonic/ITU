package Week8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Notes {

    public static void main(String[] args) {
    
        tester();

    }

    static void tester(){
        HashMap<String, Integer> hash = new HashMap<>(); 
        // System.out.println(hash.getClass()); // very useful when wanting to make functional functions.
        hash.put("kasper", 10);
        hash.put("Franzi", 20); // bliver overskrevet 
        hash.put("lasse", 30);
        hash.put("Franzi", 30); // overskriver 

        int[] arr = {10, 20, 30, 40, 50};
        int index = Arrays.binarySearch(arr, 30); // returns 2 

        System.out.println(hash.keySet());
        for (Map.Entry<String, Integer> entry : hash.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
}
    }

}




