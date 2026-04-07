
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.TreeSet;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

public class flights {

    public record FlightInfo(int flightTimeSeconds, String destination) implements Comparable<FlightInfo> {

        @Override
        public int compareTo(FlightInfo other) {
            return Integer.compare(this.flightTimeSeconds, other.flightTimeSeconds);
        }
    }

    public static void main(String[] args) throws IOException {

        // TreeSet<FlightInfo> flightsSet = new TreeSet<>();
        TreeMap<Integer, String> flightMap = new TreeMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //(new FileReader("Week7\\input.in"));
        PrintWriter brOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        String initInput = br.readLine();

        String initInputs[] = initInput.trim().split(" "); 
        int flights = Integer.parseInt(initInputs[0]);
        int operations = Integer.parseInt(initInputs[1]);

        // flights 
        for (int i = 0; i < flights; i++) {
            String inputFlightInfo = br.readLine();
            //FlightInfo flightInfo = new FlightInfo(formatFlightTimeToSeconds(inputFlightInfo.split(" ")[0]),
            // inputFlightInfo.split(" ")[1]);
            String[] parts = inputFlightInfo.split(" ");
            flightMap.put(formatFlightTimeToSeconds(parts[0]), parts[1]);
        }

        // operations 
        for (int i = 0; i < operations; i++) {
            String inputOperations = br.readLine();

            String operation = inputOperations.split(" ")[0];
            switch (operation) {
                case ("next"): // Get next flight from given time
                    getNextFlight(flightMap, inputOperations, brOut);
                    break;
                case "destination": // Output the flight destination at given time
                    getDestinationAtGivenTime(flightMap, inputOperations, brOut);
                    break;
                case "cancel": // Remove the flight 
                    cancelFlight(flightMap, inputOperations);
                    break;
                case "delay": // delay the flight with given input seconds. 
                    delayFlight(flightMap, inputOperations);
                    break;
                case "reroute": // give the time a new destination
                    rerouteFlight(flightMap, inputOperations);
                    break;
                case "count": // count flights in given interval 
                    countFlightsInInterval(flightMap, inputOperations, brOut);
                    break;
            }
        }
        brOut.flush(); // VIGTIGT - uden dette kommer der ingen output

    }

    public static int formatFlightTimeToSeconds(String flightTime) {
        String[] parts = flightTime.split(":");
        int hh = Integer.parseInt(parts[0]);
        int mm = Integer.parseInt(parts[1]);
        int ss = Integer.parseInt(parts[2]);
        return hh * 3600 + mm * 60 + ss;
    }

    public static String formatSecondsToString(int flightSecond) {
        int hh = flightSecond / 3600;
        int mm = (flightSecond % 3600) / 60;
        int ss = flightSecond % 60;
        return String.format("%02d:%02d:%02d", hh, mm, ss);
    }

    public static void getNextFlight(TreeMap<Integer, String> flightMap, String inputOperations, PrintWriter brOut) {
        int timeCheck = formatFlightTimeToSeconds(inputOperations.trim().split(" ")[1]);
        int nextTime = flightMap.ceilingKey(timeCheck);
        brOut.printf("%s %s\n", formatSecondsToString(nextTime), flightMap.get(nextTime));
    }

    public static void getDestinationAtGivenTime(TreeMap<Integer, String> flightMap, String inputOperations, PrintWriter brOut) {
        int timeCheck = formatFlightTimeToSeconds(inputOperations.trim().split(" ")[1]);
        if (!flightMap.containsKey(timeCheck)) {
            brOut.printf("-\n"); // eller brOut.println("-")
            return;
        }
        brOut.printf("%s\n", flightMap.get(timeCheck));
    }

    public static void cancelFlight(TreeMap<Integer, String> flightMap, String inputOperations) {
        int timeCheck = formatFlightTimeToSeconds(inputOperations.trim().split(" ")[1]);
        flightMap.remove(timeCheck);
    }

    public static void delayFlight(TreeMap<Integer, String> flightMap, String inputOperations) {
        //System.out.println("input operation in delayed flights: " + inputOperations);
        String[] parts = inputOperations.trim().split(" ");
        int flightToChange = formatFlightTimeToSeconds(parts[1]);
        int delayTime = Integer.parseInt(parts[2]);

        String FlightName = flightMap.get(flightToChange);
        flightMap.remove(flightToChange);
        flightMap.put(flightToChange + delayTime, FlightName);
    }

    public static void rerouteFlight(TreeMap<Integer, String> flightMap, String inputOperations) {
        
        String[] parts = inputOperations.trim().split(" ");
        int flightToChange = formatFlightTimeToSeconds(parts[1]);
        String desinationName = parts[2];
        flightMap.put(flightToChange, desinationName); // overrides my current name 
    }

    public static void countFlightsInInterval(TreeMap<Integer, String> flightMap, String inputOperations, PrintWriter brOut) {
        String[] parts = inputOperations.trim().split(" ");

        int startTime = formatFlightTimeToSeconds(parts[1]);
        int endTime = formatFlightTimeToSeconds(parts[2]);
        brOut.printf("%d\n", flightMap.subMap(startTime, true, endTime, true).size());
    }
}
