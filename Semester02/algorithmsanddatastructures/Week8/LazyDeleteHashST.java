package Week8; 


public class LazyDeleteHashST<Key, Value> {
    private int M = 16;         // tabel størrelse
    private int N = 0;          // antal aktive nøgler
    private int tombstones = 0; // antal tombstones
    private Key[] keys;
    private Value[] vals;

    public LazyDeleteHashST() {
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    // ------- PUT -------
    public void put(Key key, Value val) {
        // resize op hvis tabel er over 50% fyldt (aktive + tombstones)
        if (N + tombstones >= M / 2) resize(2 * M);

        int i = hash(key);
        while (keys[i] != null) {
            if (keys[i].equals(key)) {
                // nøglen findes allerede
                if (vals[i] == null) tombstones--; // var tombstone — overskriv
                vals[i] = val;
                if (val == null) tombstones++;     // ny tombstone
                else N++;
                return;
            }
            i = (i + 1) % M;
        }
        // helt ny nøgle
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    // ------- GET -------
    public Value get(Key key) {
        int i = hash(key);
        while (keys[i] != null) {
            if (keys[i].equals(key)) {
                return vals[i]; // returnerer null hvis tombstone
            }
            i = (i + 1) % M;
        }
        return null;
    }

    // ------- DELETE (lazy) -------
    public void delete(Key key) {
        int i = hash(key);
        while (keys[i] != null) {
            if (keys[i].equals(key)) {
                if (vals[i] != null) {
                    vals[i] = null;  // sæt value til null — lav tombstone
                    tombstones++;
                    N--;
                }
                // resize ned hvis tabel er for tom
                if (N <= M / 8) resize(M / 2);
                return;
            }
            i = (i + 1) % M;
        }
    }

    // ------- RESIZE -------
    private void resize(int newM) {
        LazyDeleteHashST<Key, Value> temp = new LazyDeleteHashST<>();
        temp.M = newM;
        temp.keys = (Key[]) new Object[newM];
        temp.vals = (Value[]) new Object[newM];

        // genindsæt kun aktive nøgler — tombstones droppes her
        for (int i = 0; i < M; i++) {
            if (keys[i] != null && vals[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys       = temp.keys;
        vals       = temp.vals;
        M          = temp.M;
        N          = temp.N;
        tombstones = 0; // alle tombstones er ryddet op
    }

    public int size()       { return N; }
    public boolean isEmpty(){ return N == 0; }
}