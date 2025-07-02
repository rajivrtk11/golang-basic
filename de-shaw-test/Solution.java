import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Solution {
    // Class to represent a ride with start time and duration
    static class Ride implements Comparable<Ride> {
        int startTime;
        int duration;
        int finishTime;
        
        public Ride(int startTime, int duration) {
            this.startTime = startTime;
            this.duration = duration;
            this.finishTime = startTime + duration;
        }
        
        @Override
        public int compareTo(Ride other) {
            return Integer.compare(this.finishTime, other.finishTime);
        }
    }
    
    public static int earliestFinishTime(List<Integer> landStartTime, List<Integer> landDuration, 
                                      List<Integer> waterStartTime, List<Integer> waterDuration) {
        // Edge case checks
        if (landStartTime == null || landDuration == null || waterStartTime == null || waterDuration == null ||
            landStartTime.isEmpty() || landDuration.isEmpty() || waterStartTime.isEmpty() || waterDuration.isEmpty() ||
            landStartTime.size() != landDuration.size() || waterStartTime.size() != waterDuration.size()) {
            throw new IllegalArgumentException("Invalid input lists");
        }
        
        // Convert input to Ride objects
        List<Ride> landRides = new ArrayList<>();
        for (int i = 0; i < landStartTime.size(); i++) {
            landRides.add(new Ride(landStartTime.get(i), landDuration.get(i)));
        }
        
        List<Ride> waterRides = new ArrayList<>();
        for (int i = 0; i < waterStartTime.size(); i++) {
            waterRides.add(new Ride(waterStartTime.get(i), waterDuration.get(i)));
        }
        
        // Sort rides by finish time - O(n log n)
        Collections.sort(landRides);
        Collections.sort(waterRides);
        
        // Find earliest finish time for each single ride category
        int landEarliestFinish = landRides.get(0).finishTime;
        int waterEarliestFinish = waterRides.get(0).finishTime;
        
        // Scenario 1: Land ride first, then water ride
        int landFirstThenWater = Integer.MAX_VALUE;
        
        // Custom sort water rides based on potential finish time after land ride
        List<Ride> waterAfterLand = new ArrayList<>(waterRides);
        Collections.sort(waterAfterLand, (a, b) -> {
            int startA = Math.max(landEarliestFinish, a.startTime);
            int startB = Math.max(landEarliestFinish, b.startTime);
            return Integer.compare(startA + a.duration, startB + b.duration);
        });
        
        if (!waterAfterLand.isEmpty()) {
            Ride bestWaterAfterLand = waterAfterLand.get(0);
            int waterStart = Math.max(landEarliestFinish, bestWaterAfterLand.startTime);
            landFirstThenWater = waterStart + bestWaterAfterLand.duration;
        }
        
        // Scenario 2: Water ride first, then land ride
        int waterFirstThenLand = Integer.MAX_VALUE;
        
        // Custom sort land rides based on potential finish time after water ride
        List<Ride> landAfterWater = new ArrayList<>(landRides);
        Collections.sort(landAfterWater, (a, b) -> {
            int startA = Math.max(waterEarliestFinish, a.startTime);
            int startB = Math.max(waterEarliestFinish, b.startTime);
            return Integer.compare(startA + a.duration, startB + b.duration);
        });
        
        if (!landAfterWater.isEmpty()) {
            Ride bestLandAfterWater = landAfterWater.get(0);
            int landStart = Math.max(waterEarliestFinish, bestLandAfterWater.startTime);
            waterFirstThenLand = landStart + bestLandAfterWater.duration;
        }
        
        // Return the minimum of the two scenarios
        return Math.min(landFirstThenWater, waterFirstThenLand);
    }
    
    public static void main(String[] args) {
        // Test Case 1 from the image
        List<Integer> landStartTime1 = Arrays.asList(1, 2, 3);
        List<Integer> landDuration1 = Arrays.asList(3, 3, 3);
        List<Integer> waterStartTime1 = Arrays.asList(1, 2, 3);
        List<Integer> waterDuration1 = Arrays.asList(1, 2, 3);
        
        int result1 = earliestFinishTime(landStartTime1, landDuration1, waterStartTime1, waterDuration1);
        System.out.println("Test Case 1 Result: " + result1); // Expected: 5
        
        // Example from original problem
        List<Integer> landStartTime2 = Arrays.asList(1, 4);
        List<Integer> landDuration2 = Arrays.asList(3, 2);
        List<Integer> waterStartTime2 = Arrays.asList(5, 2);
        List<Integer> waterDuration2 = Arrays.asList(2, 2);
        
        int result2 = earliestFinishTime(landStartTime2, landDuration2, waterStartTime2, waterDuration2);
        System.out.println("Test Case 2 Result: " + result2); // Expected: 6
    }
}