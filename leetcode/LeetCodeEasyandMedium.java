package sj.leetcode;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import sj.util.ListNode;
import sj.util.TreeNode;

/**
 * @author: Jian Shi
 * @email: shijianhzchina@gmail.com
 * @date: 12/04/2019 1:30 AM
 */

public class LeetCodeEasyandMedium {
    public static void main(String[] args) {
        //for testing usage
        System.out.println("------------Testing --------------");
        System.out.println(_17_letterCombinations2("234"));

    }

    //1. Two Sum
    public static int[] _1_twoSum(int[] nums, int target) {
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
    public static ListNode _2_addTwoNumbers(ListNode l1, ListNode l2) {
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
    public static int _3_lengthOfLongestSubstring(String s) {
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
    public static String _5_longestPalindrome1(String s) {
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
    public static String _5_longestPalindrome2(String s) {
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
    public static String _5_longestPalindrome3(String s) {
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
    public static String _6_convert(String s, int numRows) {
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
    public static int _7_reverse(int x) {
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
    public static int _8_myAtoi(String str) {
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
    public static boolean _9_isPalindrome1(int x) {
        StringBuilder sb = new StringBuilder(String.valueOf(x));
        return sb.toString().equals(sb.reverse().toString());
    }

    //9.2 convert to String and check from matched index pair.
    public static boolean _9_isPalindrome2(int x) {
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
    public static boolean _9_isPalindrome3(int x) {
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

    //11. Container With Most Water
    //Skip the Brute-Force way(simply two for loop to find the max value)
    //O(n) way of find the max value start from '0' and 'length-1'
    //Basic idea: the max value is limited by the shorter lines
    public static int _11_maxArea(int[] height) {
        int pointer1 = 0;
        int pointer2 = height.length - 1;
        int max = 0;
        int temp = 0;
        while (pointer1 < pointer2) {
            temp = (pointer2 - pointer1) * (Math.min(height[pointer1],
                    height[pointer2]));
            max = Math.max(temp, max);
            if (height[pointer1] > height[pointer2]) {
                pointer2--;
            } else {
                pointer1++;
            }
        }
        return max;
    }

    //12. Integer to Roman
    // silly and annoying way....by my own..
    public static String _12_intToRoman1(int num) {
        int thousand = num / 1000;
        int thoudRemainder = num % 1000;
        int hundred = thoudRemainder / 100;
        int hundredRemainder = thoudRemainder % 100;
        int ten = hundredRemainder / 10;
        int tenRemainder = hundredRemainder % 10;

        StringBuilder sb = new StringBuilder();
        if (thousand >= 1) {
            for (int i = 0; i < thousand; i++) {
                sb.append("M");
            }
        }
        if (hundred == 9) {
            sb.append("CM");
        } else if (hundred == 4) {
            sb.append("CD");
        } else if (hundred <= 3 && hundred >= 1) {
            for (int i = 0; i < hundred; i++) {
                sb.append("C");
            }
        } else if (hundred >= 5) {
            sb.append("D");
            for (int i = 5; i < hundred; i++) {
                sb.append("C");
            }
        }
        if (ten == 9) {
            sb.append("XC");
        } else if (ten == 4) {
            sb.append("XL");
        } else if (ten <= 3 && ten >= 1) {
            for (int i = 0; i < ten; i++) {
                sb.append("X");
            }
        } else if (ten >= 5) {
            sb.append("L");
            for (int i = 5; i < ten; i++) {
                sb.append("X");
            }
        }
        if (tenRemainder == 9) {
            sb.append("IX");
        } else if (tenRemainder == 4) {
            sb.append("IV");
        } else if (tenRemainder <= 3 && tenRemainder >= 1) {
            for (int i = 0; i < tenRemainder; i++) {
                sb.append("I");
            }
        } else if (tenRemainder >= 5) {
            sb.append("V");
            for (int i = 5; i < tenRemainder; i++) {
                sb.append("I");
            }
        }
        return sb.toString();
    }

    //Found a real brilliant and concise way of solving this problem in discuss
    //using the same idea but much more simple way to write!!
    public static String _12_intToRoman2(int num) {
        String M[] = {"", "M", "MM", "MMM"};
        String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return M[num / 1000] + C[(num % 1000) / 100] + X[(num % 100) / 10] + I[num % 10];
    }

    //13. Roman to Integer
    public static int _13_romanToInt(String s) {
        int length = s.length();
        int res = 0;
        char cur;
        char next;
        Map<Character, Integer> all = new HashMap<>();
        all.put('I', 1);
        all.put('V', 5);
        all.put('X', 10);
        all.put('L', 50);
        all.put('C', 100);
        all.put('D', 500);
        all.put('M', 1000);
        for (int i = 0; i < length; i++) {
            cur = s.charAt(i);
            next = i + 1 < length ? s.charAt(i + 1) : 'n';
            if (all.containsKey(next) && all.get(cur) < all.get(next)) {
                res -= all.get(cur);
            } else {
                res += all.get(cur);
            }
        }
        return res;
    }

    //14. Longest Common Prefix
    public static String _14_longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int length = strs.length;
        int index = 0;
        char temp = ' ';
        boolean loop = true;
        while (loop) {
            for (int i = 0; i < length; i++) {
                if (strs[i].length() <= index) {
                    loop = false;
                    break;
                }
                if (i == 0) temp = strs[i].charAt(index);
                if (temp != strs[i].charAt(index)) {
                    loop = false;
                    break;
                }
                if (i == length - 1) {
                    index++;
                    sb.append(temp);
                }
            }
        }
        return sb.toString();
    }

    //15. 3Sum
    // slow way.. found faster and better way in discuss...
    public static List<List<Integer>> _15_threeSum1(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();
        if (nums.length < 3) return new ArrayList<>(res);
        Map<Integer, Integer> numsAndFreq = new HashMap<>();
        for (int num : nums) {
            numsAndFreq.put(num, numsAndFreq.getOrDefault(num, 0) + 1);
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                List<Integer> temp = new ArrayList<>();
                int thirdKey = -nums[i] - nums[j];
                if (numsAndFreq.containsKey(thirdKey)) {
                    if ((thirdKey == nums[i] && thirdKey != nums[j] && numsAndFreq.get(thirdKey) >= 2)
                            || (thirdKey == nums[j] && thirdKey != nums[i] && numsAndFreq.get(thirdKey) >= 2)
                            || (thirdKey != nums[i] && thirdKey != nums[j] && nums[i] != nums[j])
                            || (thirdKey == nums[i] && thirdKey == nums[j] && numsAndFreq.get(thirdKey) >= 3)
                            || (thirdKey != nums[i] && nums[i] == nums[j] && numsAndFreq.get(nums[i]) >= 2)) {
                        temp.add(nums[i]);
                        temp.add(nums[j]);
                        temp.add(thirdKey);
                        Collections.sort(temp);
                        res.add(temp);
                    }
                }
            }
        }
        return new ArrayList<>(res);
    }

    // better way in discussion and intelligent, complexity is O(n^2)
    public static List<List<Integer>> _15_threeSum2(int[] nums) {
        // sort the array from small to large
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>();
        // find all the possible first element in the array
        for (int i = 0; i < nums.length - 2; i++) {
            // once the element has been used, skip the element to find a
            // another new different first element
            if (i == 0 || (i > 0 && nums[i] != nums[i - 1])) {
                // bio-directional 2 sum to find the another 2 elements
                int lo = i + 1, hi = nums.length - 1, sum = 0 - nums[i];
                while (lo < hi) {
                    if (nums[lo] + nums[hi] == sum) {
                        res.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                        //skip the duplicates
                        while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                        while (lo < hi && nums[hi] == nums[hi - 1]) hi--;
                        lo++;
                        hi--;
                    } else if (nums[lo] + nums[hi] < sum) lo++;
                    else hi--;
                }
            }
        }
        return res;
    }

    //16. 3Sum Closest
    public static int _16_threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int minDistance = Integer.MAX_VALUE;
        int finalNum = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            int cloest = 0;
            int distance = 0;
            int low = i + 1, high = nums.length - 1, new_target = target - nums[i];
            while (low < high) {
                if (nums[low] + nums[high] > new_target) {
                    cloest = nums[low] + nums[high];
                    high--;
                } else if (nums[low] + nums[high] < new_target) {
                    cloest = nums[low] + nums[high];
                    low++;
                } else {
                    return target;
                }
                distance = Math.abs(new_target - cloest);
                if (distance < minDistance) {
                    minDistance = distance;
                    finalNum = cloest + nums[i];
                }
            }
        }
        return finalNum;
    }

    //17 Letter Combinations of a Phone Number
    // create the list with the full length of the final result, append each
    // combination in the list until the end of the digits....not quite good..
    public static List<String> _17_letterCombinations1(String digits) {
        List<String> res = new ArrayList<>();
        if(digits.isEmpty()) return res;
        int count_4 = digits.length() - digits.replaceAll("9", "").length();
        count_4 += (digits.length() - digits.replaceAll("7", "").length());
        int count_3 = digits.length() - count_4;
        char[][] alphabet = {{}, {}, {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'},
                {'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x',
                'y', 'z'}};
        int totalLength =
                ((Double) (Math.pow(3, count_3) * Math.pow(4, count_4))).intValue();
        //the time of duplication of one alpha in single loop
        int curLength = totalLength;
        StringBuffer[] sb = new StringBuffer[totalLength];
        for (int i = 0; i < digits.length(); i++) {
            int curDig = Character.getNumericValue(digits.charAt(i));
            char[] curDigArr = alphabet[curDig];
            if (curDig != 7 && curDig != 9) {
                //single alpha, single loop times
                curLength = curLength / 3;
                if (i == 0) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < curLength; k++) {
                            sb[j * curLength + k] =
                                    new StringBuffer(String.valueOf(curDigArr[j]));
                        }
                    }
                } else {
                    //l -> how many loops for the alphas for current digit
                    for (int l = 0; l < totalLength / (curLength * 3); l++) {
                        //j -> for each alpha in the alphas list
                        for (int j = 0; j < 3; j++) {
                            //k-> each alpha should duplicate how many times
                            // in one loop
                            for (int k = 0; k < curLength; k++) {
                                //loop_times * single_loop_length
                                //+ current_looped_alphas
                                //+ current_alpha_looped_time
                                sb[l * curLength * 3 + j*curLength + k].append(curDigArr[j]);
                            }
                        }
                    }
                }
            } else {
                curLength = curLength / 4;
                if (i == 0) {
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < curLength; k++) {
                            sb[j * curLength + k] =
                                    new StringBuffer(String.valueOf(curDigArr[j]));
                        }
                    }
                } else {
                    for (int l = 0; l < totalLength / (curLength * 4); l++) {
                        for (int j = 0; j < 4; j++) {
                            for (int k = 0; k < curLength; k++) {
                                sb[l * curLength * 4 + j*curLength + k].append(curDigArr[j]);
                            }
                        }
                    }

                }
            }
        }
        for (StringBuffer s : sb) {
            res.add(s.toString());
        }
        return res;
    }
    // Using the queue to implement
    public static List<String> _17_letterCombinations2(String digits) {
        List<String> res = new ArrayList<>();
        if(digits.isEmpty()) return res;
        Queue<String> queue = new LinkedList<>();
        char[][] alphabet = {{}, {}, {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'},
                {'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x',
                'y', 'z'}};
        int index = 0;
        queue.add("");
        while(queue.peek().length() != digits.length()){
            String top = "";
            if(!queue.isEmpty()){
                top = queue.poll();
                index = top.length();
            }
            int curDig = Character.getNumericValue(digits.charAt(index));
            char[] curDigArr = alphabet[curDig];
            for(char alpha: curDigArr){
                queue.add(top+alpha);
            }
        }
        res.addAll(queue);
        return res;
    }


    //18. 4Sum
    //reduce k sum to k-1 sum until the 2 sum, using 2 sum to solve
//    public static List<List<Integer>> _18_fourSum(int[] nums, int target) {
//
//    }
}


