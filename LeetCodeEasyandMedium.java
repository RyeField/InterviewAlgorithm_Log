package sj.leetcode;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Jian Shi
 * @email: shijianhzchina@gmail.com
 * @date: 12/04/2019 1:30 AM
 */

public class LeetCodeEasyandMedium {
    public static void main(String[] args) {
        //for testing usage

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

    //6. ZigZag Conversion
    public static String convert(String s, int numRows) {
        int length = s.length();
        if (numRows == 1) {
            return s;
        }
        StringBuilder conversedS = new StringBuilder();
        int sectionLength = 2 * numRows - 2;
        int sections = length / sectionLength;
        // scan row by row
        for (int i = 0; i < numRows; i++) {
            //first row and last row do not have inner column
            if (i == 0 || i == numRows - 1) {
                for (int j = 0; j <= sections; j++) {
                    int index = i + j * sectionLength;
                    if (index < length) {
                        conversedS.append(s.charAt(index));
                    }
                }
            } else {
                for (int j = 0; j <= sections; j++) {
                    // main row location
                    int firIndex = i + j * sectionLength;
                    // inner row location
                    int sedIndex = sectionLength - i + j * sectionLength;
                    if (firIndex < length) {
                        conversedS.append(s.charAt(firIndex));
                    }
                    if (sedIndex < length) {
                        conversedS.append(s.charAt(sedIndex));
                    }
                }
            }
        }
        return conversedS.toString();
    }

    //7. Reverse Integer
    public static int reverse(int x) {
        if (x == 0) return 0;
        int posX = Math.abs(x);
        StringBuilder sb = new StringBuilder();
        while (posX > 0) {
            sb.append(posX % 10);
            posX /= 10;
        }
        int res = 0;
        try {
            res = Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
        return x >= 0 ? res : -res;
    }

    //8. String to Integer (atoi)
    // Used regular expression (need import "Matcher" and "Pattern" in leetcode)
    public static int myAtoi(String str) {
        if (str.trim().length() == 0) {
            return 0;
        }
        String regx = "[+|\\-]?[0-9]+";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(str);
        int conInt = 0;
        while (matcher.find()) {
            String res = matcher.group();
            //the substring should be the start of the trimmed string
            if (str.trim().indexOf(res) != 0) {
                return 0;
            }
            try {
                conInt = Integer.parseInt(res);
            } catch (NumberFormatException e) {
                if (res.substring(0, 1).equals("-")) {
                    conInt = Integer.MIN_VALUE;
                } else {
                    conInt = Integer.MAX_VALUE;
                }
            }
            //only return the first matched string
            break;
        }
        return conInt;
    }

    //9. Palindrome Number
    //Have written similar function in 5th problem.
    //9.1 convert to StringBuilder and use built-in function
    public static boolean isPalindrome1(int x) {
        StringBuilder sb = new StringBuilder(String.valueOf(x));
        return sb.toString().equals(sb.reverse().toString());
    }

    //9.2 convert to String and check from matched index pair.
    public static boolean isPalindrome2(int x) {
        String s = String.valueOf(x);
        int start = 0;
        int end = s.length() - 1;
        while (start < end) {
            if (s.charAt(start++) != s.charAt(end--)) {
                return false;
            }
        }
        return true;
    }

    //9.3 Remain integer (copied from solution)
    public static boolean isPalindrome3(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int revertedNumber = 0;
        while (x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }

        return x == revertedNumber || x == revertedNumber / 10;
    }

    //

}


