package sj.leetcode;

/**
 * @author: Jian Shi
 * @email: shijianhzchina@gmail.com
 * @date: 14/04/2019 11:43 AM
 */

public class LeetCodeHard {
    public static void main(String[] args) {
//         for testing purpose
    }

    // 4. Median of Two Sorted Arrays
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int nums1Length = nums1.length;
        int nums2Length = nums2.length;
        int totalLength = nums1Length + nums2Length;
        double res = 0.0;
        if (nums1Length == 0) {
            return nums2Length % 2 == 0 ? (nums2[nums2Length / 2] + nums2[nums2Length / 2 - 1]) / 2.00
                    : nums2[nums2Length / 2];
        } else if (nums2Length == 0) {
            return nums1Length % 2 == 0 ? (nums1[nums1Length / 2] + nums1[nums1Length / 2 - 1]) / 2.00
                    : nums1[nums1Length / 2];
        } else {
            // first num1 size should be smaller than second num2 size
            if (nums1Length > nums2Length) {
                int[] temp = nums1;
                nums1 = nums2;
                nums2 = temp;
            }
            nums1Length = nums1.length;
            nums2Length = nums2.length;
            int low = 0;
            int high = nums1Length;
            double maxLeft = 0;
            double minRight = 0;
            while (low <= high) {
                int firIndex = (low + high) / 2;
                // Why use "+1" here in the sedIndex
                // 1.1 If m + n is even, then split the elements evenly into
                // the left and right part, so i + j = m + n - i - j.
                // (Clearly the 1st middle number is in the left part and the
                // 2nd middle number is in the right part), thus we have i =
                // (m + n)/2 - j (1)
                // 1.2 If m + n is odd, then put the median in the left part,
                // so the number of elements in the left part is one more
                // than that of elements in the right part. That's where + 1
                // comes in the formula: i + j = m + n - i - j + 1.
                // Thus we have j = (m + n + 1)/2 - i (2)
                // Notice that if a number num is even, then num/2 = (num +
                // 1)/2, for example 4/2 = (4 + 1)/2 = 2. So (m + n)/2 is
                // equal to (m + n + 1)/2 in (1). Thus we can merge them to
                // (2). That's the reason why we use j = (m + n + 1)/2 - i through our code.
                int sedIndex = (nums1Length + nums2Length + 1) / 2 - firIndex;
                // situation 1 firIndex is valid, but need larger index
                // System.out.println("a"+firIndex);
                if (firIndex < nums1Length && nums2[sedIndex - 1] > nums1[firIndex]) {
                    low = firIndex + 1;
                    // situation 2 firIndex is valid, but need smaller index
                } else if (firIndex > 0 && nums1[firIndex - 1] > nums2[sedIndex]) {
                    high = firIndex - 1;
                    // situation 3 firIndex is alright, judge the max value of
                    // left list
                } else {
                    // the first list do not have value in left part
                    if (firIndex == 0) {
                        maxLeft = nums2[sedIndex - 1];
                        // the second list do not have value in the left part
                    } else if (sedIndex == 0) {
                        maxLeft = nums1[firIndex - 1];
                        // have cross
                    } else {
                        maxLeft = Math.max(nums1[firIndex - 1], nums2[sedIndex - 1]);
                    }
                    // System.out.println(firIndex + " " + sedIndex);
                    // find 2 index
                    if (totalLength % 2 == 0) {
                        // first list have no value in right part
                        if (firIndex == nums1Length) {
                            minRight = nums2[sedIndex];
                            // second list do not have value in the right part
                        } else if (sedIndex == nums2Length) {
                            minRight = nums1[firIndex];
                            // have cross
                        } else {
                            minRight = Math.min(nums1[firIndex], nums2[sedIndex]);
                        }
                        // System.out.println(maxLeft + " " + minRight);
                        res = (maxLeft + minRight) / 2;
                        break;
                    } else { // find 1 index
                        res = maxLeft;
                        break;
                    }
                }
            }
        }
        return res;
    }

    // 10. Regular Expression Matching
    public static boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        if (s.equals(p)) {
            return true;
        }
        int stringLength = s.length();
        int patternLength = p.length();
        int[][] lenMatrix = new int[patternLength + 1][stringLength + 1];
        boolean isMatched;
        lenMatrix[0][0] = 1;
        for (int i = 0; i < patternLength; i++) {
            if (p.charAt(i) == '*' && lenMatrix[i - 1][0] == 1) {
                lenMatrix[i + 1][0] = 1;
            }
        }
        //iterative the matrix
        for (int i = 1; i <= patternLength; i++) {
            for (int j = 1; j <= stringLength; j++) {
                if (p.charAt(i - 1) == '.') {
                    lenMatrix[i][j] = lenMatrix[i - 1][j - 1] + 1;
                } else if (p.charAt(i - 1) == '*') {
                    //x* only counts as empty
                    if (p.charAt(i - 2) != s.charAt(j - 1) && p.charAt(i - 2) != '.') {
                        lenMatrix[i][j] = lenMatrix[i - 2][j];
                    } else {
                        //x* counts as multiple a
                        if (lenMatrix[i][j - 1] == j) {
                            lenMatrix[i][j] = lenMatrix[i][j - 1] + 1;
                        }
                        //x* counts as single a
                        if (lenMatrix[i - 1][j] == j + 1) {
                            lenMatrix[i][j] = lenMatrix[i - 1][j];
                        }
                        //x* counts as empty
                        if (lenMatrix[i - 2][j] == j + 1) {
                            lenMatrix[i][j] = lenMatrix[i - 2][j];
                        }
                    }
                } else {
                    if (p.charAt(i - 1) == s.charAt(j - 1)) {
                        lenMatrix[i][j] = lenMatrix[i - 1][j - 1] + 1;
                    }
                }
            }
        }
        // The testing matrix
        /*for (int i = 0; i <= patternLength + 1; i++) {
            for (int j = 0; j <= stringLength + 1; j++) {
                if (i == 0 && j == 0 || i == 0 && j == 1 || i == 1 && j == 0) {
                    System.out.print("0 ");
                } else if (i == 0) {
                    System.out.print(s.charAt(j - 2) + " ");
                } else if (j == 0) {
                    System.out.print(p.charAt(i - 2) + " ");
                } else {
                    System.out.print(lenMatrix[i - 1][j - 1] + " ");
                }
            }
            System.out.println();
        }*/

        isMatched = lenMatrix[patternLength][stringLength] == stringLength + 1;
        return isMatched;
    }

}
