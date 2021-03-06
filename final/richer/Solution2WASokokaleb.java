/**
 * sokokaleb's AC solution to problem richer, easy.
 *
 * Complexity: O(N log N)
 * How: Magic
 */

import java.io.*;
import java.util.*;

public class Solution2WASokokaleb {
    private static final int MAX_N = 100005;
    private static final int MAX_K = 100005;
    
    static int N;
    static int[] K = new int[MAX_N];
    static int[] nextSibling = new int[MAX_N];
    static int[] firstChild = new int[MAX_N];
    static int[] lastChild = new int[MAX_N];
   
    private static final Comparator<Integer> BY_K_COMPARATOR = new Comparator<Integer>() {
        @Override
        public int compare(Integer i1, Integer i2) {
            return Integer.compare(K[i1], K[i2]);
        }
    };

    static class FenwickTree {
        final int[] bit;
        
        FenwickTree() {
            bit = new int[100005];
        }

        void reset() {
            Arrays.fill(bit, 0);
        }

        void update(int x, int val) {
            ++x;
            for (; x < MAX_K; x += (x & -x)) {
                bit[x] += val;
            }
        }

        int read(int x) {
            int res = 0;
            ++x;
            for (; x > 0; x -= (x & -x)) {
                res += bit[x];
            }
            return res;
        }
    }

    static FenwickTree ft = new FenwickTree();

    static int[] in = new int[MAX_N];
    static int[] out = new int[MAX_N];
    static int dfsOrder;
    
    static int[] stack = new int[MAX_N * 2];
    static int stackSize = 0;
    static BitSet bs = new BitSet(MAX_N);
    
    static void dfs(int u) {
        bs.clear();
        stackSize = 0;
        stack[stackSize++] = u;
        dfsOrder = 0;

        while (stackSize > 0) {
            u = stack[--stackSize];
            if (!bs.get(u)) {
                bs.set(u, true);
                in[u] = dfsOrder++;
                stack[stackSize++] = u;
                int v = firstChild[u];
                while (v != -1) {
                    stack[stackSize++] = v;
                    v = nextSibling[v];
                }
            } else {
                bs.set(u, false);
                out[u] = dfsOrder;
            }
        }

    static Integer[] idxByK = new Integer[MAX_N];

    static void prepare(int N) {
        Arrays.fill(nextSibling, -1);
        Arrays.fill(firstChild, -1);
        Arrays.fill(lastChild, -1);
        ft.reset();
    }

    static void solve() throws IOException {
        N = reader.getInt();
        prepare(N);
        for (int i = 1; i < N; ++i) {
            int p = reader.getInt() - 1;
            if (lastChild[p] == -1) {
                firstChild[p] = i;
            } else {
                nextSibling[lastChild[p]] = i;
            }
            lastChild[p] = i;
        }
        for (int i = 0; i < N; ++i) {
            K[i] = reader.getInt();
        }

        dfsOrder = 0;
        dfs(0);

        for (int i = 0; i < N; ++i) {
            idxByK[i] = i;
        }
        Arrays.sort(idxByK, 0, N, BY_K_COMPARATOR);

        int lstK = K[idxByK[0]];
        int lptr = 0;
        int ans = 0;
        for (int i = 0; i < N; ++i) {
            int idx = idxByK[i];
            if (lstK != K[idx]) {
                while (lptr < i) {
                    ft.update(in[idxByK[lptr++]], 1);
                }
            }

            int tmp = ft.read(out[idx] - 1) - ft.read(in[idx]);
            ans += (tmp * (tmp - 1)) >> 1;

            lstK = K[idx];
        }

        writer.println(ans);
    }

    public static void main(String[] args) throws IOException {
        int T = reader.getInt();

        for (int tc = 0; tc < T; ++tc) {
            solve();
        }

        writer.flush();
    }

    /*
     * Helper classes below.
     */

    static Reader reader = new Reader();
    static Writer writer = new Writer();

    static class Reader {
        final BufferedReader br;
        StringTokenizer st;
        
        Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
            st = new StringTokenizer("");
        }

        void checkToken() throws IOException {
            if (!st.hasMoreTokens()) {
                st = new StringTokenizer(br.readLine());
            }
        }

        String getString() throws IOException {
            checkToken();
            return st.nextToken();
        }

        int getInt() throws IOException {
            checkToken();
            return Integer.parseInt(st.nextToken());
        }
    }

    static class Writer {
        final BufferedWriter bw;

        Writer() {
            bw = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        void print(Object s) throws IOException {
            bw.write(s.toString());
        }

        void println(Object s) throws IOException {
            print(s);
            print("\n");
        }

        void flush() throws IOException {
            bw.flush();
        }
    }
}


