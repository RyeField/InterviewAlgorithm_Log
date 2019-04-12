package sj.leetcode;

import java.util.Arrays;

/**
 * @author: Jian Shi
 * @email: shijianhzchina@gmail.com
 * @date: 12/04/2019 1:30 AM
 */

public class LeetCode {
    public static void main(String[] args) {
        //for testing usage
        System.out.println(longestPalindrome2("babcd"));
    }

    //1. Two Sum
    public static int[] twoSum(int[] nums, int target) {
        int[] numsCopy = nums.clone();
        //sort the array and find the target
        Arrays.sort(numsCopy);
        int i = 0;
        int j = nums.length - 1;
        int sum;
        int[] res = new int[2];
        int num1 = 0, num2 = 0;
        while (i < j) {
            sum = numsCopy[i] + numsCopy[j];
            if (sum < target) {
                i++;
            } else if (sum > target) {
                j--;
            } else {
                num1 = numsCopy[i];
                num2 = numsCopy[j];
                break;
            }
        }
        //find indices from the original array.
        int count = 0;
        boolean num1exist = true;
        boolean num2exist = true;
        for (int k = 0; k < nums.length; k++) {
            if (nums[k] == num1 && num1exist) {
                res[count] = k;
                num1exist = false;
                count++;
                continue;
            }
            if (nums[k] == num2 && num2exist) {
                res[count] = k;
                num2exist = false;
                count++;
                continue;
            }
            if (count == 2) break;
        }
        return res;
    }

    //2. Add Two Numbers
    // (could be refactor and more concise)
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int tens = 0;
        ListNode head;
        ListNode cur = new ListNode(0);
        if (l1 != null && l2 != null) {
            if (l1.val + l2.val > 9) {
                cur.val = l1.val + l2.val - 10;
                tens = 1;
                l1 = l1.next;
                l2 = l2.next;
            } else {
                cur.val = l1.val + l2.val;
                l1 = l1.next;
                l2 = l2.next;
            }
        } else if (l1 != null) {
            return l1;
        } else {
            return l2;
        }
        head = cur;
        while (l1 != null && l2 != null) {
            if (l1.val + l2.val + tens > 9) {
                ListNode next = new ListNode(l1.val + l2.val + tens - 10);
                cur.next = next;
                tens = 1;
                l1 = l1.next;
                l2 = l2.next;
                cur = cur.next;
            } else {
                ListNode next = new ListNode(l1.val + l2.val + tens);
                cur.next = next;
                tens = 0;
                l1 = l1.next;
                l2 = l2.next;
                cur = cur.next;
            }
        }
        while (l1 != null) {
            if (l1.val + tens > 9) {
                ListNode next = new ListNode(l1.val + tens - 10);
                cur.next = next;
                tens = 1;
                l1 = l1.next;
                cur = cur.next;
            } else {
                ListNode next = new ListNode(l1.val + tens);
                cur.next = next;
                tens = 0;
                l1 = l1.next;
                cur = cur.next;
            }
        }
        while (l2 != null) {
            if (l2.val + tens > 9) {
                ListNode next = new ListNode(l2.val + tens - 10);
                cur.next = next;
                tens = 1;
                l2 = l2.next;
                cur = cur.next;
            } else {
                ListNode next = new ListNode(l2.val + tens);
                cur.next = next;
                tens = 0;
                l2 = l2.next;
                cur = cur.next;
            }
        }

        if (l1 == null && l2 == null) {
            if (tens == 1) {
                ListNode next = new ListNode(1);
                cur.next = next;
            }
        }
        return head;
    }

    //3. Longest Substring Without Repeating Characters
    public static int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        int pointer1 = 0;
        int pointer2 = 0;
        int length = 1;
        // 2 pointers
        // pointer2 from start to end of the string
        // pointer1 moves whenever this have a duplication of the character.
        for (; pointer2 < s.length(); pointer2++) {
            int firIndex = s.indexOf(s.charAt(pointer2), pointer1);
            if (pointer2 != firIndex) {
                pointer1 = firIndex + 1;
            }
            length = pointer2 - pointer1 + 1;
            if (length > maxLength) {
                maxLength = length;
            }
            maxLength = Math.max(length, maxLength);
        }
        return maxLength;
    }

    //4. Median of Two Sorted Arrays
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int nums1Length = nums1.length;
        int nums2Length = nums2.length;
        int totalLength = nums1Length + nums2Length;
        double res = 0.0;
        if (nums1Length == 0) {
            return nums2Length % 2 == 0 ?
                    (nums2[nums2Length / 2] + nums2[nums2Length / 2 - 1]) / 2.00 :
                    nums2[nums2Length / 2];
        } else if (nums2Length == 0) {
            return nums1Length % 2 == 0 ?
                    (nums1[nums1Length / 2] + nums1[nums1Length / 2 - 1]) / 2.00 :
                    nums1[nums1Length / 2];
        } else {
            //first num1 size should be smaller than second num2 size
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
                //System.out.println("a"+firIndex);
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
                        maxLeft = Math.max(nums1[firIndex - 1],
                                nums2[sedIndex - 1]);
                    }
                    //System.out.println(firIndex + " " + sedIndex);
                    //find 2 index
                    if (totalLength % 2 == 0) {
                        //first list have no value in right part
                        if (firIndex == nums1Length) {
                            minRight = nums2[sedIndex];
                            //second list do not have value in the right part
                        } else if (sedIndex == nums2Length) {
                            minRight = nums1[firIndex];
                            // have cross
                        } else {
                            minRight = Math.min(nums1[firIndex],
                                    nums2[sedIndex]);
                        }
                        //System.out.println(maxLeft + " " + minRight);
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

    //5. Longest Palindromic Substring
    //5.1 Brute Force
    public static String longestPalindrome1(String s) {
        int length = s.length();
        int longest = 0;
        String res = "";
        for (int i = 0; i < length; i++) {
            for (int k = 0; k < length; k++) {
                if (isPalindrome(s, i, k)) {
                    int curLength = k - i + 1;
                    if (curLength > longest) {
                        longest = curLength;
                        res = s.substring(i, k + 1);
                    }
                }
            }
        }
        return res;
    }

    public static boolean isPalindrome(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i++) != s.charAt(j--)) {
                return false;
            }
        }
        return true;
    }

    //5.2 Longest Common Substring
    public static String longestPalindrome2(String s) {
        //String sReverse = new StringBuffer(s).reverse().toString();
        StringBuffer sReverse = new StringBuffer(s).reverse();
        //check the longestCommonSubstring between s and sReserve, however
        //need to check the index of substring to fit to the original position
        int sLen = s.length();
        int rLen = sReverse.length();
        int longestLen = 0;
        String lcs = "";
        int[][] lenMatrix = new int[sLen + 1][rLen + 1];
        for (int i = 0; i <= sLen; i++) {
            for (int j = 0; j <= rLen; j++) {
                if (i == 0 || j == 0) {
                    lenMatrix[i][j] = 0;
                } else if (s.charAt(i - 1) == sReverse.charAt(j - 1)) {
                    int curLen = lenMatrix[i - 1][j - 1] + 1;
                    lenMatrix[i][j] = curLen;
                    if (curLen > longestLen) {
                        // check index
                        if (i + j - curLen == sLen) {
                            longestLen = curLen;
                            lcs = s.substring(i - curLen, i);
                        }
                    }
                } else {
                    lenMatrix[i][j] = 0;
                }
            }
        }
        return lcs;
    }

    // Derived from this problem 5 (dynamic problem way to solve LCS)
    public static String longestCommonSubstring(String s1, String s2) {
        int s1Len = s1.length();
        int s2Len = s2.length();
        int longestLen = 0;
        String lcs = "";
        // matrix to store the length of common substring, first row and
        // column is filled with 0 for further process, row present each
        // character of one word and column represent each character of
        // the other word.
        int[][] lenMatrix = new int[s1Len + 1][s2Len + 1];
        for (int i = 0; i <= s1Len; i++) {
            for (int j = 0; j <= s2Len; j++) {
                // fill first row and column with 0
                if (i == 0 || j == 0) {
                    lenMatrix[i][j] = 0;
                    // if character match at "word index" i and j (extra row and
                    // column in matrix make "-1" in word charAt() function)
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    //check the "matrix index" "i-1" and "j-1", add 1 to it
                    int curLen = lenMatrix[i - 1][j - 1] + 1;
                    lenMatrix[i][j] = curLen;
                    //update the longest length and lcs
                    if (curLen > longestLen) {
                        longestLen = curLen;
                        lcs = s1.substring(i - curLen, i);
                    }
                } else {
                    lenMatrix[i][j] = 0;
                }
            }
        }
        return lcs;
    }

    //5.3 Expand Around Center
    // (copied from solution, faster than my previous solutions)
    public static String longestPalindrome3(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }
    
}

