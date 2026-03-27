import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.PriorityQueue;
import Week6.SeatAllocation.PartyVotes;

// the end points of each conflict edge have different colours, and
// the end points of each harmony edge have the same colour. ( dashed lines )

class Harmony1_useless {
    public record Graph(AdjVtx adjVtx, Edge[] edges) {
    }

    public record AdjVtx(int index, ArrayList<Integer> adjVtx) {
    }

    public record Edge(int start, int end, boolean harmonic) {
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Week7\\input.in"));
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstInput[] = br.readLine().split(" ");

        int vertex = Integer.parseInt(firstInput[0]);
        int edges = Integer.parseInt(firstInput[1]);

        // ArrayList<Graph> grapPoints = new ArrayList<>();
        ArrayList<AdjVtx> adjVtxList = new ArrayList<>();
        ArrayList<Edge> edgeList = new ArrayList<>();
        // (a, b) -> Long.compare(b.index(), a.index()));

        // Add all 13 graph vtx to the graph.
        for (int i = 0; i < vertex; i++) {
            AdjVtx adjV = new AdjVtx(i, new ArrayList<>());
            adjVtxList.add(adjV);
        }

        // add adjecent vertex to list
        for (int i = 0; i < edges; i++) { // first edge input is 0 1 1
            String edgeInput = br.readLine();
            int startVtx = Integer.parseInt(edgeInput.split(" ")[0]);
            int endVtx = Integer.parseInt(edgeInput.split(" ")[1]);
            int harmonic = Integer.parseInt(edgeInput.split(" ")[2]); 
            adjVtxList.get(startVtx).adjVtx.add(endVtx);
            edgeList.add(new Edge(startVtx, endVtx, harmonic == 1 ? true : false));
        }

        for (AdjVtx adjVtx : adjVtxList) {
            System.out.println(adjVtx);
        }
    }
}

// 13 15 // vtx, edges
// 0 1 1 // edge points
// 0 2 1
// 0 5 1
// 0 6 1
// 1 3 1