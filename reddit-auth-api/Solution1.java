import java.util.*;

public class Solution1 {

    public static long minRequestTime(List<Integer> requestedServers, List<Integer> transitionTime) {
        int n = transitionTime.size();  // number of transitions (n servers in a ring)
        long[] prefixSum = new long[n + 1];

        // Precompute prefix sums of transitionTime
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + transitionTime.get(i);
        }

        long totalTime = prefixSum[n];  // total time for a full circle
        long currentTime = 0;
        int currentPos = 1;  // starts at server 1

        for (int target : requestedServers) {
            if (currentPos == target) continue;

            int from = currentPos - 1;
            int to = target - 1;

            // Clockwise time
            long clockwise;
            if (to >= from) {
                clockwise = prefixSum[to] - prefixSum[from];
            } else {
                clockwise = totalTime - (prefixSum[from] - prefixSum[to]);
            }

            // Counter-clockwise time
            long counterClockwise = totalTime - clockwise;

            currentTime += Math.min(clockwise, counterClockwise);
            currentPos = target;
        }

        return currentTime;
    }

    public static void main(String[] args) {
        // Test Case 1 (from the image)
        List<Integer> requested1 = Arrays.asList(2, 3, 3, 1);
        List<Integer> transition1 = Arrays.asList(3, 2, 1);
        System.out.println("Test Case 1: " + minRequestTime(requested1, transition1));  // Expected: 6

        // Test Case 2
        List<Integer> requested2 = Arrays.asList(2, 4);
        List<Integer> transition2 = Arrays.asList(1, 2, 3, 4);
        System.out.println("Test Case 2: " + minRequestTime(requested2, transition2));  // Expected: 3

        // Test Case 3
        List<Integer> requested3 = Arrays.asList(3, 1, 4);
        List<Integer> transition3 = Arrays.asList(5, 5, 5, 5);
        System.out.println("Test Case 3: " + minRequestTime(requested3, transition3));  // Expected: 15

        // Test Case 4
        List<Integer> requested4 = Arrays.asList(5, 1);
        List<Integer> transition4 = Arrays.asList(1, 1, 1, 1, 1);
        System.out.println("Test Case 4: " + minRequestTime(requested4, transition4));  // Expected: 2

        // Test Case 5
        List<Integer> requested5 = Arrays.asList(4, 2, 1);
        List<Integer> transition5 = Arrays.asList(2, 1, 3, 4);
        System.out.println("Test Case 5: " + minRequestTime(requested5, transition5));  // Expected: 6

        // Test Case 6
        List<Integer> requested6 = Arrays.asList(1, 2, 3, 4);
        List<Integer> transition6 = Arrays.asList(10, 20, 30, 40);
        System.out.println("Test Case 6: " + minRequestTime(requested6, transition6));  // Expected: 60
    }
}

