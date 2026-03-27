import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Harmony {

    public record Neighbor(int vertex, boolean isConflict) {
    }

    static int[] color;
    static boolean[] marked;
    static boolean possible = true;

    // 
    static void dfs(ArrayList<Neighbor>[] adj, int v) {
        marked[v] = true; // marker den knude vi er på nu
        for (Neighbor neighbor : adj[v]) { // gå gennem alle naboer til v
            if (!marked[neighbor.vertex()]) { // er naboen ikke besøgt endnu?
                // sæt naboens farve baseret på kanttype
                color[neighbor.vertex()] = neighbor.isConflict() ? 1 - color[v] : color[v]; //color[v] er 0 -> 1 - 0 = 1 & color[v] er 1 -> 1 - 1 = 0 smart måde at flippe mellem 0 og 1 på 
                dfs(adj, neighbor.vertex()); // gå ned i naboen — kom tilbage hertil når den er færdig
            } else { // naboen er allerede besøgt
                // vi kan ikke ændre naboens farve, så tjek om den farve den HAR er den rigtige
                int expectedColor = neighbor.isConflict() ? 1 - color[v] : color[v];
                if (color[neighbor.vertex()] != expectedColor) { // farven passer ikke — umuligt at løse
                    possible = false;
                }
            }
        }
    } // når alle naboer er gennemgået vender vi tilbage til den knude der kaldte os

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));// (new
                                                                                 // InputStreamReader(System.in));new
                                                                                 // FileReader("Week7\\input.in")
        String firstInput[] = br.readLine().split(" ");
        int vertex = Integer.parseInt(firstInput[0]);
        int edges = Integer.parseInt(firstInput[1]);

        ArrayList<Neighbor>[] adj = new ArrayList[vertex]; // En Array der indeholder lister[] af Neighbors
        color = new int[vertex];
        marked = new boolean[vertex]; // initialized always as false.

        // "Neighbor" objekter.
        for (int i = 0; i < vertex; i++) {
            adj[i] = new ArrayList<>();
        }

        // 0: [neighbour(vtx_1, conflict)] - kan have flere 
        // 1: [neighbour(vtx_0, conflict)]
        for (int i = 0; i < edges; i++) { // first edge input is 0 1 1
            String edgeInput = br.readLine();
            String[] parts = edgeInput.split(" ");
            int startVtx = Integer.parseInt(parts[0]);
            int endVtx = Integer.parseInt(parts[1]);
            int harmonic = Integer.parseInt(parts[2]);

            // Se på start vtx plads.
            // Tilføj end vertex til startvtx liste.
            // Se på end vtx plads
            // Tilføj start vertix endvtx liste
            adj[startVtx].add(new Neighbor(endVtx, harmonic == 1 ? true : false));
            adj[endVtx].add(new Neighbor(startVtx, harmonic == 1 ? true : false));
        }

        // DFS
        for (int i = 0; i < vertex; i++) { // first edge input is 0 1 1
            if (!marked[i]) {
                dfs(adj, i);
            }
        }
        System.out.println(possible == true ? 1 : 0);
    }
}
