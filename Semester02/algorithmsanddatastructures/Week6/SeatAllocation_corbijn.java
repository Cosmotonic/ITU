import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class SeatAllocation {
    public record PartyVotes(int votes, int partyIndex, int quotient) {
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parameters = br.readLine().split(" ");

        int nrOfParties = Integer.parseInt(parameters[0]);
        int nrOfSeats = Integer.parseInt(parameters[1]);

        int[] seatDistribution = new int[nrOfParties];
        PriorityQueue<PartyVotes> partyVotesQueue = new PriorityQueue<>(
                (a, b) -> Long.compare(
                        (long) b.votes() * a.quotient(),
                        (long) a.votes() * b.quotient()));

        for (int i = 0; i < nrOfParties; i++) {
            int votes = Integer.parseInt(br.readLine());
            partyVotesQueue.add(new PartyVotes(votes, i, 1)); // 1 begin value quotient
        }

        for (int i = 0; i < nrOfSeats; i++) {
            PartyVotes votesForParty = partyVotesQueue.remove();

            seatDistribution[votesForParty.partyIndex()] += 1;

            int newQuotient = votesForParty.quotient + 1;

            PartyVotes recalculatedRecord = new PartyVotes(votesForParty.votes, votesForParty.partyIndex, newQuotient);
            partyVotesQueue.add(recalculatedRecord);
        }

        for (int seats : seatDistribution) {
            System.out.println(seats);
        }
    }
}
