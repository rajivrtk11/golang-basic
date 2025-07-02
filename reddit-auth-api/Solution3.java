import java.util.*;

public class Solution3 {

    static class DSU {
        int[] parent;
        int[] rank;
        TreeSet<Integer>[] activeSet;

        public DSU(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];
            activeSet = new TreeSet[n + 1];
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
                rank[i] = 0;
                activeSet[i] = new TreeSet<>();
                activeSet[i].add(i); // initially, each cluster is active
            }
        }

        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) return;

            // always attach smaller to larger
            if (rank[px] < rank[py]) {
                parent[px] = py;
                activeSet[py].addAll(activeSet[px]);
            } else {
                parent[py] = px;
                activeSet[px].addAll(activeSet[py]);
                if (rank[px] == rank[py]) rank[px]++;
            }
        }

        void deactivate(int x) {
            int root = find(x);
            activeSet[root].remove(x);
        }

        int getMin(int x) {
            int root = find(x);
            if (activeSet[root].isEmpty()) return -1;
            return activeSet[root].first();
        }
    }

    public static List<Integer> getAssignedCluster(int clusters, List<List<Integer>> connections, List<List<Integer>> queries) {
        DSU dsu = new DSU(clusters);

        for (List<Integer> conn : connections) {
            dsu.union(conn.get(0), conn.get(1));
        }

        List<Integer> result = new ArrayList<>();

        for (List<Integer> q : queries) {
            int type = q.get(0);
            int cluster = q.get(1);

            if (type == 1) {
                result.add(dsu.getMin(cluster));
            } else {
                dsu.deactivate(cluster);
            }
        }

        return result;
    }

    // For quick testing
    public static void main(String[] args) {
        int clusters = 3;
        List<List<Integer>> connections = Arrays.asList(
            Arrays.asList(1, 2),
            Arrays.asList(2, 3)
        );
        List<List<Integer>> queries = Arrays.asList(
            Arrays.asList(2, 2),
            Arrays.asList(1, 2),
            Arrays.asList(2, 1),
            Arrays.asList(2, 3),
            Arrays.asList(1, 1)
        );

        List<Integer> result = getAssignedCluster(clusters, connections, queries);
        for (int r : result) {
            System.out.println(r);
        }
    }
}
