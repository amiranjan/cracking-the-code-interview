import java.util.Arrays;
import java.util.PriorityQueue;

public class Practice {
    public static void main(String[]args){
        int[] arr = {900, 940, 950, 1100, 1500, 1800};
        int[] dep = {910, 1200, 1120, 1130, 1900, 2000};
        System.out.println("Min number of platforms needed = " + minPlatforms(arr, dep));
        arr = new int[]{100, 300, 500};
        dep = new int[]{900, 400, 600};
        System.out.println("Min number of platforms needed = " + minPlatforms(arr, dep));

        System.out.println("4th smallest number in {10, 5, 4, 3, 48, 6, 2, 33, 53, 10} is " + kthSmallest(new int[]{10, 5, 4, 3, 48, 6, 2, 33, 53, 10}, 4));

        System.out.println("isRotatedPalindrome(aab) = " + isRotatedPalindrome("aab"));
        System.out.println("isRotatedPalindrome(abcde) = " + isRotatedPalindrome("abcde"));
        System.out.println("isRotatedPalindrome(aaaad) = " + isRotatedPalindrome("aaaad"));
    }

    private static int minPlatforms(int[] arr, int[] dep) {
        int platform = 1;
        TrainSchedule[] trainSchedules = new TrainSchedule[arr.length];
        for (int i = 0; i < arr.length; i ++)
            trainSchedules[i] = new TrainSchedule(arr[i], dep[i]);
        Arrays.sort(trainSchedules);

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(trainSchedules[0].depTime);
        for (int i = 1; i < trainSchedules.length; i++) {
            System.out.println(pq);
            if (pq.peek() >= trainSchedules[i].arrTime)
                platform++;
            else
                pq.poll();
            pq.offer(trainSchedules[i].depTime);
        }
        return platform;
    }

    private static class TrainSchedule implements Comparable<TrainSchedule> {
        int arrTime;
        int depTime;
        public TrainSchedule(int arrTime, int depTime) {
            this.arrTime = arrTime;
            this.depTime = depTime;
        }

        @Override
        public int compareTo(TrainSchedule t) {
            return this.arrTime - t.arrTime;
        }
    }

    private static int kthSmallest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for (int i = 0; i < nums.length; i++) {
            pq.offer(nums[i]);
            System.out.println(pq);
            if (pq.size() > k)
                pq.poll();
        }
        return pq.peek();
    }

    private static boolean isRotatedPalindrome(String str) {
        for (int i = 0; i < str.length() - 1; i++) {
            if (isPalindrome(str.substring(i + 1) + str.substring(0, i + 1)))
                return true;
        }
        return false;
    }

    private static boolean isPalindrome(String str) {
        int start = 0, end = str.length() - 1;
        while (start < end) {
            if (str.charAt(start++) != str.charAt(end--))
                return false;
        }
        return true;
    }
}
