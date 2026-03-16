package ArchiveCode;
import java.io.*;
 
public class disjointsetssimple_mathias {
    private static int[] id;
    private static int[] sz;
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
        String[] first = br.readLine().split(" ");
        int n = Integer.parseInt(first[0]);
        int m = Integer.parseInt(first[1]);
 
        // Initialize
        id = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1;
        }
 
        for (int i = 0; i < m; i++) {
            String[] line = br.readLine().split(" ");
            String op = line[0];
            int s = Integer.parseInt(line[1]);
            int t = Integer.parseInt(line[2]);
 
            if (op.equals("=")) {
                union(s, t);
            } else {
                System.out.println(find(s) == find(t) ? 1 : 0);
            }
        }
 
    }
 
    // Find med path compression
    private static int find(int p) {
        if (p != id[p])
            id[p] = find(id[p]);
        return id[p];
    }
 
    private static void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j)
            return;
 
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }
}