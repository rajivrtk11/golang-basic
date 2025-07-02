import java.util.*;

public class Main {

    static class Edge implements Comparable<Edge> {
        int from, to, weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }

    static class UnionFind {
        int[] parent;

        UnionFind(int n) {
            parent = new int[n + 1];
            for (int i = 1; i <= n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) return false;
            parent[rootY] = rootX;
            return true;
        }
    }

    public static int optimizeAPIRegions(int api_nodes, int[] api_from, int[] api_to, int[] api_weight, int k) {
        int n = api_from.length;
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            edges.add(new Edge(api_from[i], api_to[i], api_weight[i]));
        }

        Collections.sort(edges);

        UnionFind uf = new UnionFind(api_nodes);
        List<Integer> usedEdgeWeights = new ArrayList<>();

        for (Edge edge : edges) {
            if (uf.union(edge.from, edge.to)) {
                usedEdgeWeights.add(edge.weight);
            }
        }

        // Sort descending to remove the (k - 1) largest edges
        Collections.sort(usedEdgeWeights, Collections.reverseOrder());

        // Remove k-1 largest weights to split into k regions
        for (int i = 0; i < k - 1 && !usedEdgeWeights.isEmpty(); i++) {
            usedEdgeWeights.remove(0);
        }

        // The remaining maximum edge weight is the answer
        return usedEdgeWeights.isEmpty() ? 0 : usedEdgeWeights.get(0);
    }

    // Example usage
    // public static void main(String[] args) {
    //     int api_nodes = 3;
    //     int[] api_from = {1, 2, 3};
    //     int[] api_to = {2, 3, 1};
    //     int[] api_weight = {4, 5, 3};
    //     int k = 2;

    //     int result = optimizeAPIRegions(api_nodes, api_from, api_to, api_weight, k);
    //     System.out.println("Minimum possible max-latency: " + result);  // Output: 4

    //     // int api_nodes = 3;
    //     // int[] api_from = {1, 3, 1};
    //     // int[] api_to = {2, 2, 3};
    //     // int[] api_weight = {3, 4, 6};
    //     // int k = 1;
    
    //     // int result = optimizeAPIRegions(api_nodes, api_from, api_to, api_weight, k);
    //     // System.out.println("Minimum possible max-latency: " + result);
    // }

    public static int optimizeAPIRegions(int apiNodes, List<Integer> apiFrom, List<Integer> apiTo, List<Integer> apiWeight, int k) {
        int n = apiNodes;
        int m = apiFrom.size();
        
        // Edge class for easier handling
        class Edge {
            int u, v, weight;
            Edge(int u, int v, int weight) {
                this.u = u;
                this.v = v;
                this.weight = weight;
            }
        }

        // Union-Find (Disjoint Set Union) with path compression
        class DSU {
            int[] parent;
            DSU(int n) {
                parent = new int[n + 1]; // 1-based indexing
                for (int i = 1; i <= n; i++) {
                    parent[i] = i;
                }
            }
            int find(int x) {
                if (parent[x] != x)
                    parent[x] = find(parent[x]);
                return parent[x];
            }
            boolean union(int x, int y) {
                int px = find(x);
                int py = find(y);
                if (px == py) return false;
                parent[py] = px;
                return true;
            }
        }

        // Step 1: Build list of edges
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            edges.add(new Edge(apiFrom.get(i), apiTo.get(i), apiWeight.get(i)));
        }

        // Step 2: Sort edges by weight
        edges.sort(Comparator.comparingInt(e -> e.weight));

        // Step 3: Kruskal's algorithm to build MST
        DSU dsu = new DSU(n);
        List<Integer> mstWeights = new ArrayList<>();

        for (Edge edge : edges) {
            if (dsu.union(edge.u, edge.v)) {
                mstWeights.add(edge.weight);
            }
        }

        // Step 4: Remove (k - 1) largest edges from MST to create k regions
        Collections.sort(mstWeights, Collections.reverseOrder());
        for (int i = 0; i < k - 1 && !mstWeights.isEmpty(); i++) {
            mstWeights.remove(0); // remove largest
        }

        // Step 5: Return max latency in the remaining regions
        return mstWeights.isEmpty() ? 0 : Collections.max(mstWeights);
    }

    // public static void main(String[] args) {
        // int apiNodes = 3;
        // List<Integer> apiFrom = Arrays.asList(1, 2, 3);
        // List<Integer> apiTo = Arrays.asList(2, 3, 1);
        // List<Integer> apiWeight = Arrays.asList(4, 5, 3);
        // int k = 2;

        // int result = optimizeAPIRegions(apiNodes, apiFrom, apiTo, apiWeight, k);
        // System.out.println("Minimum possible max-latency: " + result); // Should print 3
    // }
    public static void main(String[] args) {
        // Test Case 1: Simple cycle, breaking into 2 regions
        testCase(1, 3,
                Arrays.asList(1, 2, 3),
                Arrays.asList(2, 3, 1),
                Arrays.asList(4, 5, 3),
                2, 3);

        // Test Case 2: Tree structure with 4 nodes, k=2
        testCase(2, 4,
                Arrays.asList(1, 1, 2),
                Arrays.asList(2, 3, 4),
                Arrays.asList(1, 2, 3),
                2, 2);

        // Test Case 3: Already separated graph (k=1 means whole MST)
        testCase(3, 5,
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(2, 3, 4, 5),
                Arrays.asList(10, 20, 30, 40),
                1, 40);

        // Test Case 4: Graph with equal weights
        testCase(4, 4,
                Arrays.asList(1, 2, 3),
                Arrays.asList(2, 3, 4),
                Arrays.asList(5, 5, 5),
                2, 5);

        // Test Case 5: Disconnected graph (not possible in valid input but still handled)
        testCase(5, 4,
                Arrays.asList(1, 2),
                Arrays.asList(2, 3),
                Arrays.asList(1, 2),
                2, 2); // It will just consider available connected components
    }

    public static void testCase(int id, int apiNodes, List<Integer> apiFrom, List<Integer> apiTo, List<Integer> apiWeight, int k, int expected) {
        int result = optimizeAPIRegions(apiNodes, apiFrom, apiTo, apiWeight, k);
        System.out.println("Test Case " + id + ": Expected = " + expected + ", Got = " + result + " → " + (result == expected ? "✅ Passed" : "❌ Failed"));
    }
}
