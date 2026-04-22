import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class snooze {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String t = br.readLine();
        int m = Integer.parseInt(br.readLine());
        int n = Integer.parseInt(br.readLine());

        String timeParts[] = t.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);

        int totalMinutes = hours * 60 + minutes;

        int snoozeTime = n * m;
        int startTime = totalMinutes - snoozeTime;

        if (startTime < 0) {
            startTime += 1440;
        }

        int lastHours = startTime / 60;
        int lastMinutes = startTime % 60;

        System.out.printf("%02d:%02d\n", lastHours, lastMinutes);
    }
}