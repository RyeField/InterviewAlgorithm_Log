package sj.jianzhi;


import sj.util.ListNode;
import sj.util.RandomListNode;
import sj.util.TreeLinkNode;
import sj.util.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: Jian Shi
 * @email: shijianhzchina@gmail.com
 * @date: 3/06/2019 10:55 PM
 */

public class JianZhi {
    public static void main(String[] args) {
        JianZhi jz = new JianZhi();
        //tree 1
//        TreeNode root1 = new TreeNode(1);
//        TreeNode node1 = new TreeNode(2);
//        TreeNode node2 = new TreeNode(3);
//        TreeNode node3 = new TreeNode(4);
//        TreeNode node4 = new TreeNode(5);
//        TreeNode node5 = new TreeNode(6);
//        root1.left = node1;
//        root1.right = node2;
//        node1.left = node3;
//        node1.right = node4;
//        node4.left = node5;
//        //tree2
//        TreeNode root2 = new TreeNode(1);
//        TreeNode node7 = new TreeNode(2);
//        TreeNode node8 = new TreeNode(4);
//        TreeNode node9 = new TreeNode(5);
//        root2.left = node7;
//        node7.left = node8;
//        node7.right = node9;
//        System.out.println(jz._17_HasSubtree(root1,root2));


//        int[] test = {3, 3, 3, 3, 4, 5};
//        System.out.println(jz._37_GetNumberOfK(test, 3));

        System.out.println(jz._49_StrToInt("a33"));
    }

    public void input() {
        Scanner sc = new Scanner(System.in);
        String next = "";
        while (sc.hasNextLine()) {
            next = sc.nextLine();
        }
    }

    public int test(ListNode head) {
        Map<ListNode, Integer> seenNode = new HashMap<>();
        int pos = 0;
        while (head != null) {
            if (seenNode.containsKey(head)) {
                return seenNode.get(head);
            }
            head = head.next;
            seenNode.put(head, pos);
            pos++;
        }
        return -1;
    }

    //从左上角不断缩小最大列的限制，（其实从左下角最佳，小了向右，大了向上）
    public boolean _1_Find(int target, int[][] array) {
        int row = array.length;
        int col = array[0].length;
        int maxColumn = col;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < maxColumn; j++) {
                if (array[i][j] == target) {
                    return true;
                } else if (array[i][j] > target) {
                    maxColumn = j;
                    break;
                }
            }
            if (maxColumn == 0) {
                break;
            }
        }
        return false;
    }

    //直接用replace也可以。。不懂什么意义
    public String _2_replaceSpace(StringBuffer str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char curChar = str.charAt(i);
            if (curChar == ' ') {
                sb.append("%20");
            } else {
                sb.append(curChar);
            }
        }
        return sb.toString();
    }

    public ArrayList<Integer> _3_printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> reveList = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        while (listNode != null) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        while (!stack.empty()) {
            reveList.add(stack.pop());
        }
        return reveList;
    }

    //递归的应用。。做Tree相关一直习惯递归了。。
    public TreeNode _4_reConstructBinaryTree(int[] pre, int[] in) {
        if (pre.length != 0 && in.length != 0) {
            TreeNode root = new TreeNode(pre[0]);
            findSubTree(Arrays.stream(in).boxed().collect(Collectors.toList()),
                    Arrays.stream(pre).boxed().collect(Collectors.toList()), root);
            return root;
        } else {
            return null;
        }
    }

    public void findSubTree(List<Integer> in, List<Integer> pre,
                            TreeNode root) {
        List<Integer> leftin = new ArrayList<>();
        List<Integer> rightin = new ArrayList<>();
        boolean left_done = false;
        for (int curVal : in) {
            if (curVal != root.val) {
                if (!left_done) {
                    leftin.add(curVal);
                } else {
                    rightin.add(curVal);
                }
            } else {
                left_done = true;
            }
        }
        int leftSize = leftin.size();
        int rightSize = rightin.size();
        List<Integer> leftpre = pre.subList(1, 1 + leftSize);
        List<Integer> rightpre = pre.subList(1 + leftSize, 1 + leftSize + rightSize);
        if (leftSize > 0) {
            TreeNode left_root = new TreeNode(leftpre.get(0));
            root.left = left_root;
            findSubTree(leftin, leftpre, left_root);
        }
        if (rightSize > 0) {
            TreeNode right_root = new TreeNode(rightpre.get(0));
            root.right = right_root;
            findSubTree(rightin, rightpre, right_root);
        }
    }

    public class _5_QueueByStack {
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        public void push(int node) {
            stack1.push(node);
        }

        public int pop() {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            int popEle = stack2.pop();
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            return popEle;
        }
    }

    public int _6_minNumberInRotateArray_1(int[] array) {
        if (array.length == 0) return 0;
        int cur = array[0];
        for (int val : array) {
            if (cur > val) {
                return val;
            }
            cur = val;
        }
        return cur;
    }

    //binary search的变形
    public int _6_minNumberInRotateArray_2(int[] array) {
        if (array.length == 0) return 0;
        int low = 0, high = array.length - 1;
        while (low < high) {
            int mid = (low + high) / 2;
            //cannot use left and mid here, by experiment.
            if (array[mid] > array[high]) {
                low = mid + 1;
            } else if (array[mid] == array[high]) {
                high = high - 1;
            } else {
                high = mid;
            }
        }
        return Math.min(array[low], array[high]);
    }

    //基础斐波那契数列
    public int _7_Fibonacci(int n) {
        int fir = 1;
        int second = 1;
        int temp = 2;
        if (n == 0) {
            return 0;
        } else if (n < 3) {
            return 1;
        } else {
            while (n >= 3) {
                temp = fir + second;
                fir = second;
                second = temp;
                n--;
            }
        }
        return temp;
    }

    //斐波那契数列应用
    public int _8_JumpFloor(int target) {
        int fir = 1;
        int sed = 2;
        int cur = 3;
        if (target == 0) {
            return 0;
        } else if (target < 3) {
            return target;
        } else {
            while (target >= 3) {
                cur = fir + sed;
                fir = sed;
                sed = cur;
                target--;
            }
        }
        return cur;
    }

    public long factorial(int n) {
        if (n < 0) {
            return -1;
        }

        if (n == 0) {
            return 1;
        }

        long result = 1;
        for (int i = n; i > 0; i--) {
            result *= i;
        }

        return result;
    }

    public int _9_JumpFloorII_1(int target) {
        int combination = 1;
        for (int k = 1; k < target; k++) {
            combination +=
                    factorial(target - 1) / (factorial(k) * factorial(target - 1 - k));
        }
        return combination;
    }

    //永远都是之前的两倍，排列组合的证明 就是1里面的公式
    public int _9_JumpFloorII_2(int target) {
        int a = 1;
        return a << (target - 1); //位运算
    }

    //斐波那契数列应用
    public int _10_RectCover(int target) {
        int fir = 1;
        int sed = 2;
        int cur = 3;
        if (target == 0) {
            return 0;
        } else if (target < 3) {
            return target;
        } else {
            while (target >= 3) {
                cur = fir + sed;
                fir = sed;
                sed = cur;
                target--;
            }
        }
        return cur;
    }

    //位操作的运用，前两个Java内置API
    public int _11_NumberOf1(int n) {
//        return Integer.toBinaryString(n).replace("0","").length();
//        return Integer.bitCount(n);
        int count = 0, flag = 1;
        for (int i = 0; i < 32; i++) {
            //不断右移，和1与操作
            if ((n >> i & flag) == 1) {
                count++;
            }
        }
        return count;
    }

    //平方求幂exponentiating by squaring 或 快速幂 的思想
    public double _12_Power(double base, int exponent) {
        boolean flag = true;
        double res = 1;
        if (exponent == 0) {
            return 1;
        } else if (exponent < 0) {
            flag = false;
            exponent = -exponent;
        }
        //不断右移exponent，在base不断递增时，exponent当前位为1，则base*res
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                res *= base;
            }
            base *= base;
            exponent >>= 1;
        }
        return flag ? res : 1 / res;
    }

    //类似于插入排序，不然就是在建立一个数组存偶数放最后
    public void _13_reOrderArray(int[] array) {
        int min = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 1 && i > 0) {
                int temp = array[i];
                for (int k = i; k > min; k--) {
                    array[k] = array[k - 1];
                }
                array[min] = temp;
                min++;
            } else if (array[i] % 2 == 1 && i == 0) {
                min = 1;
            }
        }
    }

    //双指针，一个领先k步
    public ListNode _14_FindKthToTail(ListNode head, int k) {
        ListNode nodeFaster = head;
        ListNode nodeSlower = head;
        int index = 0;
        while (nodeFaster.next != null) {
            nodeFaster = nodeFaster.next;
            if (index >= k) nodeSlower = nodeSlower.next;
            index++;
        }
        return index < k ? null : nodeSlower;
    }

    //迭代解法，也可以递归。。不是太习惯。
    public ListNode _15_ReverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        ListNode next = head;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    //迭代解法
    public ListNode _16_Merge(ListNode list1, ListNode list2) {
        ListNode head, cur;
        if (list1 == null && list2 == null) {
            return null;
        } else if (list1 != null && list2 == null) {
            return list1;
        } else if (list1 == null) {
            return list2;
        } else {
            if (list1.val < list2.val) {
                head = list1;
                cur = list1;
                list1 = list1.next;
            } else {
                head = list2;
                cur = list2;
                list2 = list2.next;
            }
        }

        while (true) {
            if (list1 != null && list2 != null) {
                if (list1.val < list2.val) {
                    cur.next = list1;
                    list1 = list1.next;
                } else {
                    cur.next = list2;
                    list2 = list2.next;
                }
                cur = cur.next;
            } else {
                if (list1 == null) {
                    cur.next = list2;
                } else {
                    cur.next = list1;
                }
                break;
            }
        }
        return head;
    }

    //重新回顾了下 inOrder 和 levelOrder 的写法
    public List<Integer> inOrder(TreeNode root) {
        List<Integer> in = new ArrayList<>();
        inOrderHelper(root, in);
        return in;
    }

    public void inOrderHelper(TreeNode root, List<Integer> in) {
        if (root != null) {
            inOrderHelper(root.left, in);
            in.add(root.val);
            inOrderHelper(root.right, in);
        }
    }

    public List<Integer> layerOrder(TreeNode root) {
        List<Integer> layer = new ArrayList<>();
        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            TreeNode top = nodes.poll();
            if (top.left != null) {
                nodes.add(top.left);
            }
            if (top.right != null) {
                nodes.add(top.right);
            }
            layer.add(top.val);
        }
        return layer;
    }

    public boolean _17_HasSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) return false;
        Queue<TreeNode> nodes1 = new LinkedList<>();
        nodes1.add(root1);
        //level order 来一层层扫下去每个node
        while (!nodes1.isEmpty()) {
            TreeNode top = nodes1.poll();
            if (top.left != null) {
                nodes1.add(top.left);
            }
            if (top.right != null) {
                nodes1.add(top.right);
            }
            //如果node值一样，比较root2是不是和这个node下面所有的node一样
            if (top.val == root2.val) {
                if (isSubtree(top, root2)) {
                    return true;
                }
            }
        }
        return false;
    }

    //level order 一层层迭代比较node确定是否包含
    public boolean isSubtree(TreeNode root1, TreeNode root2) {
        Queue<TreeNode> nodes1 = new LinkedList<>();
        nodes1.add(root1);
        Queue<TreeNode> nodes2 = new LinkedList<>();
        nodes2.add(root2);
        while (!nodes2.isEmpty()) {
            TreeNode top = nodes1.poll();
            TreeNode top2 = nodes2.poll();
            if (top.val != top2.val) {
                return false;
            } else {
                if (top2.left != null) {
                    nodes2.add(top2.left);
                    //只有B树的子节点非null，才去考虑A树的子节点
                    if (top.left != null) nodes1.add(top.left);
                }
                if (top2.right != null) {
                    nodes2.add(top2.right);
                    if (top.right != null) nodes1.add(top.right);
                }
            }
        }
        return true;
    }

    //数的递归的应用
    public void _18_Mirror(TreeNode root) {
        if (root != null) {
            if (root.left != null && root.right != null) {
                TreeNode temp = root.left;
                root.left = root.right;
                root.right = temp;
                _18_Mirror(root.left);
                _18_Mirror(root.right);
            } else if (root.left != null) {
                root.right = root.left;
                root.left = null;
                _18_Mirror(root.right);
            } else if (root.right != null) {
                root.left = root.right;
                root.right = null;
                _18_Mirror(root.left);
            }
        }
    }

    //不断绕圈往里缩小，每到matrix的角，进行缩小边界
    public ArrayList<Integer> _19_printMatrix(int[][] matrix) {
        int maxRow = matrix.length - 1;
        int maxCol = matrix[0].length - 1;
        int minRow = 0;
        int minCol = 0;
        ArrayList<Integer> res = new ArrayList<>();
        while (true) {
            res.addAll(Arrays.stream(matrix[minRow], minCol, maxCol + 1).boxed().collect(Collectors.toList()));
            minRow++;
            for (int i = minRow; i <= maxRow; i++) {
                int col1 = matrix[i][maxCol];
                res.add(col1);
            }
            maxCol--;
            if (minRow <= maxRow) {
                List<Integer> bottomRow = Arrays.stream(matrix[maxRow], minCol,
                        maxCol + 1).boxed().collect(Collectors.toList());
                Collections.reverse(bottomRow);
                res.addAll(bottomRow);
                maxRow--;
            }
            if (minCol <= maxCol) {
                for (int i = maxRow; i >= minRow; i--) {
                    int col2 = matrix[i][minCol];
                    res.add(col2);
                }
                minCol++;
            }
            if (minRow > maxRow) break;
            if (minCol > maxCol) break;
        }
        return res;
    }

    //辅助栈存储当前栈中最小值
    public class _20_MyStack {

        Stack<Integer> stack = new Stack<>();
        Stack<Integer> minStack = new Stack<>();

        public void push(int node) {
            stack.push(node);
            if (minStack.isEmpty()) {
                minStack.push(node);
            } else {
                if (node < minStack.peek()) {
                    minStack.push(node);
                }
            }
        }

        public void pop() {
            if (stack.peek() == minStack.peek()) minStack.pop();
            stack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int min() {
            return minStack.peek();
        }
    }

    //辅助stack模拟pop过程
    public boolean _21_IsPopOrder(int[] pushA, int[] popA) {
        if (pushA.length == 0 || popA.length == 0) return false;
        int index = 0;
        Stack<Integer> auxStack = new Stack<>();
        for (int i = 0; i < pushA.length; i++) {
            auxStack.push(pushA[i]);
            while (!auxStack.isEmpty() && auxStack.peek() == popA[index]) {
                index++;
                auxStack.pop();
            }
        }
        return index == popA.length;
    }

    //一层层打印树，用Queue,弹出一个node，加入他的children
    public ArrayList<Integer> _22_PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        Queue<TreeNode> nodes = new LinkedList<>();
        if (root == null) return res;
        nodes.add(root);
        while (!nodes.isEmpty()) {
            TreeNode cur = nodes.poll();
            if (cur.left != null) {
                nodes.add(cur.left);
            }
            if (cur.right != null) {
                nodes.add(cur.right);
            }
            res.add(cur.val);
        }
        return res;
    }

    //递归解法
    public boolean _23_VerifySquenceOfBST(int[] sequence) {
        if (sequence.length == 0) return false;
        if (sequence.length <= 2) return true;
        return verifySquenceOfBSTHelper(sequence);
    }

    //最后一个元素是root，前半部分list小于root，后半部分list必须都大于root才是true
    public boolean verifySquenceOfBSTHelper(int[] sequence) {
        if (sequence.length <= 2) return true;
        int curRoot = sequence[sequence.length - 1];
        boolean shouldLarger = false;
        int splitIndex = 0;
        for (int i = 0; i < sequence.length - 1; i++) {
            if (!shouldLarger && sequence[i] > curRoot) {
                splitIndex = i;
                shouldLarger = true;
            }
            if (shouldLarger && sequence[i] < curRoot) return false;
        }
        return verifySquenceOfBSTHelper(Arrays.copyOfRange(sequence, 0,
                splitIndex)) & verifySquenceOfBSTHelper(Arrays.copyOfRange(sequence, splitIndex, sequence.length - 1));
    }

    //非递归解法，三个队列，BFS的思路，不断更新，符合条件的路径放入最后结果
    //考虑路径长的放在前面，因为是BFS，所以每次拿到符合的路径，放在0位置
    public ArrayList<ArrayList<Integer>> _24_FindPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Queue<ArrayList<Integer>> path = new LinkedList<>(); //记录路径
        Queue<TreeNode> nodes = new LinkedList<>(); //记录节点
        Queue<Integer> sum = new LinkedList<>(); //记录路径总和
        ArrayList<Integer> initial = new ArrayList<>();
        initial.add(root.val);
        path.add(initial);
        nodes.add(root);
        sum.add(root.val);
        while (!nodes.isEmpty()) {
            int curLayerSize = nodes.size();
            for (int i = 0; i < curLayerSize; i++) {
                int preSum = sum.poll();
                TreeNode preNode = nodes.poll();
                ArrayList<Integer> prePath = path.poll();
                if (preSum < target) {
                    if (preNode.left != null) {
                        nodes.add(preNode.left);
                        sum.add(preSum + preNode.left.val);
                        ArrayList<Integer> curPath = new ArrayList<>(prePath);
                        curPath.add(preNode.left.val);
                        path.add(curPath);
                    }
                    if (preNode.right != null) {
                        nodes.add(preNode.right);
                        sum.add(preSum + preNode.right.val);
                        ArrayList<Integer> curPath = new ArrayList<>(prePath);
                        curPath.add(preNode.right.val);
                        path.add(curPath);
                    }
                } else if (preSum == target) {
                    if (preNode.left == null && preNode.right == null) {
                        res.add(0, prePath);
                    }
                }
            }
        }
        return res;
    }

    //hashMap 先clone每一个node，并设置next关系
    //再遍历一遍补上random关系
    //讨论中还看到一个三步法，在原链表中进行操作
    public RandomListNode _25_Clone(RandomListNode pHead) {
        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode curNode = pHead;
        RandomListNode preCloneNode = new RandomListNode(0);
        //遍历node，clone,一一对应
        while (curNode != null) {
            RandomListNode cloneNode = new RandomListNode(curNode.label);
            preCloneNode.next = cloneNode;
            map.put(curNode, cloneNode);
            preCloneNode = cloneNode;
            curNode = curNode.next;
        }
        preCloneNode.next = null; //point the last node to null
        //补上random关系
        Iterator<Map.Entry<RandomListNode, RandomListNode>> iterator =
                map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<RandomListNode, RandomListNode> entry = iterator.next();
            entry.getValue().random = map.get(entry.getKey().random);
        }
        return map.get(pHead);
    }

    //基于Binary Tree的非递归中序遍历（等同于排序）
    //https://leetcode.com/problems/binary-tree-inorder-traversal/solution/
    public TreeNode _26_Convert(TreeNode pRootOfTree) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode preNode = null;
        TreeNode curNode = pRootOfTree;
        TreeNode first = null;
        while (curNode != null || !stack.isEmpty()) {
            while (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }
            curNode = stack.pop();
            //操作curNode和preNode，互相关联
            curNode.left = preNode;
            if (preNode != null) {
                preNode.right = curNode;
            }
            if (preNode == null) {
                first = curNode; //start of the list
            }
            preNode = curNode;
            curNode = curNode.right;
        }
        return first;
    }

    /**
     * 字符串的全排列问题
     * 递归的算法，在固定不同的prefix的前提下（不断swap当前index的字符和后面的每一个字符来生成一个prefix）
     * 对每一个这样的prefix，再继续递归后续的字符，直到到达最后一个字符
     * 对于重复问题，如果在当前循环里，这个字符出现过，则不swap
     * 还有一种迭代算法，巧妙的找下一个最接近的排序，直到最后一个排序
     * https://www.nowcoder.com/questionTerminal/fe6b651b66ae47d7acce78ffdd9a96c7
     * 中“天天502”的答案解释了2种方法
     */

    public ArrayList<String> _27_Permutation(String str) {
        ArrayList<String> list = new ArrayList<>();
        if (str != null && str.length() > 0) {
            PermutationHelper(str.toCharArray(), 0, list);
        }
        Collections.sort(list);
        return list;
    }

    public void PermutationHelper(char[] strChar, int startIndex,
                                  ArrayList<String> list) {
        if (startIndex == strChar.length - 1) {
            StringBuilder sb = new StringBuilder();
            for (Character ch : strChar) sb.append(ch);
            list.add(sb.toString());
//            String.valueOf(strChar); //更简单的Char数组转换为String
        } else {
            Set<Character> set = new HashSet<>();
            for (int i = startIndex; i < strChar.length; i++) {
                if (i == startIndex || !set.contains(strChar[i])) {
                    set.add(strChar[i]);
                    swapSingleChar(strChar, startIndex, i);
                    PermutationHelper(strChar, startIndex + 1, list);
                    swapSingleChar(strChar, startIndex, i);
                }
            }
        }

    }

    public void swapSingleChar(char[] strChar, int indexFir, int indexSed) {
        char temp = strChar[indexFir];
        strChar[indexFir] = strChar[indexSed];
        strChar[indexSed] = temp;
    }

    //Hashmap可以较简单解出，剑指Offer的答案较复杂，考虑别的技巧
    public int _28_MoreThanHalfNum_Solution1(int[] array) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : array) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.get(num) > array.length / 2) return num;
        }
        return 0;
    }

    /**
     * 还有一个Misra-Gries Algorithm(研究生Stream课教的，可以寻找frequent elements)
     * 对于一个Stream，长度为m，寻找frequency大于m/k的元素，只需要追踪k-1个元素，
     * 符合这个frequency的一定会返回，也存在不符合的元素返回了(有误差)
     * 适合Stream长度非常长，内存放不下，只能流读取一次，可以忍受一定偏差的时候
     * 算法思路：
     * 建立追踪记录表（length为k-1），记录被追踪元素和相应计数器，Xs->Counter, 对每一个新的元素x
     * IF x 在被追踪 -> Counter += 1
     * ELSE x 没被追踪
     * IF 追踪记录表还有空余位置 -> 则加入x且赋予Counter = 1
     * ELSE 追踪记录表已满(且x不在表中) -> 对表中每个元素的Counter -= 1，弹出那些Counter = 0的元素
     * <p>
     * 对于本题，要求返回出现次数超过一半的数字
     * K=2(frequency大于stream length的一半)，则返回1个元素或0个元素（有误差）
     **/
    public int _28_MoreThanHalfNum_Solution2(int[] array) {
        Map<Integer, Integer> map = new HashMap<>();
        int trackedEle = 1;
        for (int num : array) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                if (map.size() < trackedEle) {
                    map.put(num, 1);
                } else {
                    Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<Integer, Integer> entry = iterator.next();
                        int counter = entry.getValue();
                        counter -= 1;
                        if (counter == 0) {
                            iterator.remove();
                        } else {
                            map.put(entry.getKey(), counter);
                        }
                    }
                }
            }
        }
        if (map.isEmpty()) {
            return 0;
        } else {
            int finalNum = 0;
            int count = 0;
            for (int key : map.keySet()) {
                finalNum = key;
            }
            //复查是否符合，读了两遍array，其实有违算法初衷
            for (int num : array) {
                if (num == finalNum) {
                    count += 1;
                }
            }
            if (count > array.length / 2) {
                return finalNum;
            } else {
                return 0;
            }
        }
    }

    //用siftdown自己维护了heap的性质，实质上用PriorityQueue即可
    public ArrayList<Integer> _29_GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        //建立一个ksize大小的最大堆（找最小的k个数）
        if (k > input.length || k == 0) {
            return result;
        }
        int[] maxHeap = new int[k];
        for (int i = 0; i < input.length; i++) {
            if (i < k - 1) {
                maxHeap[i] = input[i];
            } else if (i == k - 1) {
                maxHeap[i] = input[i];
                //初始化堆
                for (int j = k / 2 - 1; j >= 0; j--) {
                    siftDown(maxHeap, j, k);
                }
            } else {
                //比root大的无视，比root小的和root交换，恢复堆的性质
                if (input[i] < maxHeap[0]) {
                    maxHeap[0] = input[i];
                    siftDown(maxHeap, 0, k);
                }
            }
        }
        for (int l = 0; l < k; l++) {
            result.add(maxHeap[l]);
        }
        return result;
    }

    private static void siftDown(int[] heap, int keyIndex, int heapSize) {
        int keyValue = heap[keyIndex];
        boolean isHeap = false;
        while (!isHeap && keyIndex < heapSize / 2) {
            //左孩子的index
            int childIndex = 2 * keyIndex + 1;
            //如果有右孩子的情况
            if (childIndex < heapSize - 1) {
                //比较两个child值的大小，把index值设为大的那个（最大堆中）
                if (heap[childIndex] < heap[childIndex + 1]) {
                    childIndex++;
                }
            }
            //递归比较子节点的大小和keyValue的大小来决定是否满足heap的属性
            if (heap[childIndex] <= keyValue) {
                isHeap = true;
            } else {
                //child节点的值上移
                heap[keyIndex] = heap[childIndex];
                keyIndex = childIndex;
            }
        }
        //把原本的KeyValue放到最终的位置，形成一个heap
        heap[keyIndex] = keyValue;
    }

    //自己的思路，基本思路是负和抛弃从0开始
    public int _30_FindGreatestSumOfSubArray1(int[] array) {
        int sum = Integer.MIN_VALUE;
        int cur = 0;
        int maxNegative = Integer.MIN_VALUE;
        int maxSum = Integer.MIN_VALUE;
        int result;
        boolean firstPositive = true;
        for (int i = 0; i < array.length; i++) {
            cur = array[i];
            if (cur < 0) {
                //更新最大sum
                if (sum > maxSum) {
                    maxSum = sum;
                }
                //当前是负数，更新最大负数，最后总和为负时用
                if (maxNegative < cur) {
                    maxNegative = cur;
                }
            } else {
                //遇到第一个非负数，开始从0开始累加
                if (firstPositive) {
                    sum = 0;
                    firstPositive = false;
                }
            }
            //遇到第一个非负数之后
            if (!firstPositive) {
                //遇到负和抛弃之前的结果，重新积累, 不然继续积累
                if (sum + cur <= 0) {
                    if (sum > maxSum) {
                        maxSum = sum;
                    }
                    sum = 0;
                } else {
                    sum = sum + cur;
                }
            }
        }
        if (sum > maxSum) {
            maxSum = sum;
        }
        result = maxSum >= 0 ? maxSum : maxNegative;
        return result;
    }

    //动态规划的思路
    //记录以每个index结尾的subarray最大的和
    public int _30_FindGreatestSumOfSubArray2(int[] array) {
        int sumMax = array[0];
        for (int i = 1; i < array.length; i++) {
            array[i] = Math.max(array[i - 1] + array[i], array[i]);
            sumMax = Math.max(array[i], sumMax);
        }
        return sumMax;
    }

    //归纳法
    //依次归纳个位，十位，百位总结出合理的通用公式
    //https://www.nowcoder.com/questionTerminal/bd7f978302044eee894445e244c7eee6?f=discussion
    //用户 Duqcuid 总结的好棒
    public int _31_NumberOf1Between1AndN_Solution(int n) {
        if (n == 0) return 0;
        int count = 0;
        for (int i = 1; i <= n; i *= 10) {
            int divider = 10 * i;
            //if-else的写法，太长
            /*count += (n / divider * i);
            int left = n % divider;
            if (left >= i && left <= 2 * i - 1) {
                count += (left - i + 1);
            } else if (left > 2 * i - 1) {
                count += i;
            }*/
            //在Duqcuidd的答案中，用min max巧妙地避开了if-else
            count += (n / divider) * i + Math.min(Math.max(n % divider - i + 1, 0), i);
        }
        return count;
    }

    public String _32_PrintMinNumber(int[] numbers) {
        Integer[] numbersWapper = Arrays.stream(numbers)
                .boxed().toArray(Integer[]::new);
        //重写Comparator来按题目定义排序数字
        Arrays.sort(numbersWapper, new Comparator<Integer>() {
            @Override
            //对数字o1，o2来说，两者组成的新数字要尽可能的小，以此来决定o1，o2的大小（相对位置）
            public int compare(Integer o1, Integer o2) {
                String string1 = o1 + "" + o2;
                String string2 = o2 + "" + o1;
                return string1.compareTo(string2);
            }
        });
        //对排序好的数组
        StringBuilder sb = new StringBuilder();
        for (int num : numbersWapper) {
            sb.append(num);
        }
        return sb.toString();
    }

    /**
     * https://www.nowcoder.com/questionTerminal/6aa9e04fc3794f68acf8778237ba065b?f=discussion
     * 用户 "事无巨细，悉究本末" 的答案C++版本的改写
     * 思路就是 丑数 = 2^x * 3^y * 5^z , 即丑数*2， *3， *5都会生成新丑数
     * 所以维系3个队列来存储每一个新丑数*2，*3，*5 所生成的潜在丑数，
     * 每一个独立队列都是递增，所以只需要比较队列最前端找出最小的潜在丑数加入最终队列即可，
     * 但队列之间会有重复，需要同时弹出。
     * 在实现时，通过改变3个指针在在最终丑数队列上的位置来表示3个队列到了哪个位置，不需要存储3个队列
     **/
    public int _33_GetUglyNumber_Solution(int index) {
        //前6个数字都是丑数
        if (index < 7) return index;
        List<Integer> uglyNums = new ArrayList<>();
        //3个指针指向最终丑数数组代替3个不同队列相应头位置
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        uglyNums.add(1);
        while (uglyNums.size() < index) {
            //找新丑数
            int twoUgly = uglyNums.get(p1) * 2;
            int threeUgly = uglyNums.get(p2) * 3;
            int fiveUgly = uglyNums.get(p3) * 5;
            int nextNum = Math.min(twoUgly, Math.min(threeUgly, fiveUgly));
            //改变指针位置，指向3个不同的位置,代替数组(代表每个数组的头元素)
            if (nextNum == twoUgly) p1++;
            if (nextNum == threeUgly) p2++;
            if (nextNum == fiveUgly) p3++;
            uglyNums.add(nextNum);
        }
        return uglyNums.get(index - 1);
    }

    public int _34_FirstNotRepeatingChar(String str) {
        char[] strArray = str.toCharArray();
        //hash的思想，先存出现次数，找到第一个一次的元素，然后遍历str返回位置
        Map<Character, Integer> map = new LinkedHashMap();
        for (char ch : strArray) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return str.indexOf(entry.getKey());
            }
        }
        return -1;
        //另一种小变形，先存出现次数，直接遍历str找到对应char在hash中value为1
        /*Map<Character, Integer> map = new HashMap<>();
        for (char ch : strArray) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        for (int i = 0; i< strArray.length; i++){
            if(map.get(strArray[i]) == 1) {
                return i;
            }
        }
        return  -1;*/
    }

    /**
     * 基于Bottom-up的归并排序，只添加两行，其余不变
     * 原始归并排序代码在自己的Blog中
     * https://ryefield.github.io/algorithm/Sorting-Algorithm-Comparison-Sorts/#merge-sort%E5%BD%92%E5%B9%B6%E6%8E%92%E5%BA%8F
     * 只需要考虑每次遇到left的数组中比right的数组中大的数字的时候
     * 则证明在left数组中之后的所有元素也比这个right数组中的这个数大
     * 所以使用count += (mid - leftIndex)
     */
    public int _35_InversePairs(int[] array) {
        int[] temp = new int[array.length];
        int count = 0;
        for (int i = 2; i < 2 * array.length; i *= 2) {
            for (int j = 0; j < (array.length + i) / i; j++) {
                int left = i * j;
                int mid = Math.min(left + i / 2, array.length);
                int right = Math.min(i * (j + 1), array.length);
                int leftIndex = left, rightIndex = mid, mergedIndex = left;
                while (leftIndex < mid && rightIndex < right) {
                    if (array[leftIndex] <= array[rightIndex]) {
                        temp[mergedIndex++] = array[leftIndex++];
                    } else {
                        //下面两行是在Bottom-up MergeSort上加的代码
                        //统计每次的inverse pair
                        count += (mid - leftIndex);
                        count %= 1000000007;
                        temp[mergedIndex++] = array[rightIndex++];
                    }
                }
                if (leftIndex == mid) {
                    System.arraycopy(array, rightIndex, temp, mergedIndex, right - rightIndex);
                } else {
                    System.arraycopy(array, leftIndex, temp, mergedIndex, mid - leftIndex);
                }
                System.arraycopy(temp, left, array, left, right - left);
            }
        }
        return count;
    }

    /**
     * leetcode原题 https://leetcode.com/problems/intersection-of-two-linked-lists/
     * 简单解法，无需在意长度
     * 长度相同有公共结点，第一次就遍历到；没有公共结点，走到尾部NULL相同结束，返回NULL
     * 长度不同有公共结点，第一遍结束到另一个list开头，等同于弥补不同长度
     * 有公共结点，第二遍一起到公共结点；没有公共，一起到结尾NULL，返回NULL
     */
    public ListNode _36_FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) return null;
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;

        while (p1 != p2) {
            p1 = p1 == null ? pHead2 : p1.next;
            p2 = p2 == null ? pHead1 : p2.next;
        }
        return p1;
    }

    /**
     * 排序数组查找元素，考察Binary Search的变形，O(log n)复杂度
     */
    public int _37_GetNumberOfK(int[] array, int k) {
        if (array.length == 0) return 0;
        int firstIndex = getFirstK(array, 0, array.length - 1, k);
        int lastIndex = getLastK(array, 0, array.length - 1, k);
        if (firstIndex != -1 && lastIndex != -1) {
            return lastIndex - firstIndex + 1;
        }
        return 0;
    }

    //递归
    public int getFirstK(int[] array, int lo, int hi, int k) {
        if (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (array[mid] < k) {
                return getFirstK(array, mid + 1, hi, k);
            } else if (array[mid] > k) {
                return getFirstK(array, lo, mid - 1, k);
            } else if (mid - 1 >= 0 && array[mid - 1] == k) {
                return getFirstK(array, lo, mid - 1, k);
            } else {
                return mid;
            }
        }
        return -1;
    }

    //迭代
    public int getLastK(int[] array, int lo, int hi, int k) {
        int mid = (lo + hi) / 2;
        while (lo <= hi) {
            if (array[mid] < k) {
                lo = mid + 1;
            } else if (array[mid] > k) {
                hi = mid - 1;
            } else if (mid + 1 < array.length && array[mid + 1] == k) {
                lo = mid + 1;
            } else {
                return mid;
            }
            mid = (lo + hi) / 2;
        }
        return -1;
    }

    /**
     * Leetcode原题https://leetcode.com/problems/maximum-depth-of-binary-tree/
     * 递归解法或者Level Order层次遍历
     */
    public int _38_TreeDepth1(TreeNode root) {
        if (root == null) return 0;
        return Math.max(_38_TreeDepth1(root.left), _38_TreeDepth1(root.right)) + 1;
    }

    public int _38_TreeDepth2(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);
        int depth = 0;
        while (!nodes.isEmpty()) {
            int levelSize = nodes.size();
            depth += 1;
            for (int i = 0; i < levelSize; i++) {
                TreeNode cur = nodes.poll();
                if (cur.left != null) nodes.add(cur.left);
                if (cur.right != null) nodes.add(cur.right);
            }
        }
        return depth;
    }

    //自底向上
    public boolean _39_IsBalanced_Solution(TreeNode root) {
        return getDepth(root) == -1;
    }

    public int getDepth(TreeNode root) {
        if (root == null) return 0;
        int leftDepth = getDepth(root.left);
        //如果遇到子节点不符合的，直接返回-1
        if (leftDepth == -1) return -1;
        int rightDepth = getDepth(root.right);
        if (rightDepth == -1) return -1;
        //不符合平衡二叉树，返回-1，符合则返回最大深度+1
        return Math.abs(leftDepth - rightDepth) > 1 ? -1 :
                1 + Math.max(leftDepth, rightDepth);
    }

    //num1,num2分别为长度为1的数组。传出参数
    //将num1[0],num2[0]设置为返回结果
    //考察异或？数字自己和自己异或为0..
    //自己的解法就是简单的HashSet
    public void _40_FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        Set<Integer> nums = new HashSet<>();
        for (int value : array) {
            if (!nums.contains(value)) {
                nums.add(value);
            } else {
                nums.remove(value);
            }
        }
        boolean first = true;
        for (int res : nums) {
            if (first) {
                num1[0] = res;
                first = false;
            } else {
                num2[0] = res;
            }
        }
    }

    //双指针思想
    public ArrayList<ArrayList<Integer>> _41_FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        int lo = 1;
        int hi = 2;
        while (lo < hi) {
            int curSum = (lo + hi) * (hi - lo + 1) / 2;
            if (curSum > sum) {
                lo++;
            } else if (curSum < sum) {
                hi++;
            } else {
                ArrayList<Integer> cur = new ArrayList<>();
                for (int i = lo; i <= hi; i++) {
                    cur.add(i);
                }
                res.add(cur);
                lo++;
            }
        }
        return res;
    }

    public ArrayList<Integer> _42_FindNumbersWithSum(int[] array, int sum) {
        int lo = 0;
        int hi = array.length - 1;
        ArrayList<Integer> res = new ArrayList<>();
        while (lo < hi) {
            if (array[lo] + array[hi] < sum) {
                lo++;
            } else if (array[lo] + array[hi] > sum) {
                hi--;
            } else {
                //距离最远，乘积最小
                res.add(array[lo]);
                res.add(array[hi]);
                break;
            }
        }
        return res;
    }

    public String _43_LeftRotateString(String str, int n) {
        if (str.length() < n) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = n - 1; i < str.length(); i++) {
            sb.append(str.charAt(i));
        }
        for (int i = 0; i < n; i++) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }


    //用了Java的方式处理,题目本意可能是要求反转每个word，再反转整个句子。
    public String _44_ReverseSentence1(String str) {
        if (str.trim().equals("")) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        String[] words = str.split(" ");
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]);
            if (i != 0) sb.append(" ");
        }
        return sb.toString();
    }

    //反转每个word，再反转整个句子
    public String _44_ReverseSentence2(String str) {
        char[] strArray = str.toCharArray();
        int startIndex = 0;
        int endIndex = 0;
        //反转words
        for (int i = 0; i < strArray.length; i++) {
            if (strArray[i] == ' ' && i != strArray.length - 1 && i != 0) {
                endIndex = i - 1;
                swapCharInterval(strArray, startIndex, endIndex);
                startIndex = i + 1;
            }
        }
        //反转最后一个word
        if (startIndex != strArray.length - 1) {
            swapCharInterval(strArray, startIndex, strArray.length - 1);
        }
        //反转sentence
        swapCharInterval(strArray, 0, strArray.length - 1);
        return String.valueOf(strArray);
    }

    public void swapCharInterval(char[] array, int startIndex, int endIndex) {
        while (startIndex < endIndex) {
            char temp = array[startIndex];
            array[startIndex++] = array[endIndex];
            array[endIndex--] = temp;
        }
    }

    public boolean _45_isContinuous(int[] numbers) {
        if (numbers.length < 5) return false;
        int[] allNums = new int[14];
        int startIndex = 14;
        int endIndex = -1;
        for (int num : numbers) {
            //略过大小王
            if (num != 0) {
                //更新头尾index
                startIndex = Math.min(num, startIndex);
                endIndex = Math.max(num, endIndex);
                if (allNums[num] == 0) {
                    allNums[num] = 1;
                } else {
                    //重复出现两次同一个数字，不可能会有连续了
                    return false;
                }
            }
        }
        //因为大小王随意算，所以尾-头index要小于5
        return endIndex - startIndex < 5;
    }


    /**
     * 约瑟夫问题 即由n个人坐成一圈，按顺时针由1开始给他们编号。然后由第一个人开始报数，
     * 数到m的人出局。现在需要求的是最后一个出局的人的编号。
     * 解法，一种是数学推导，得出递推公式;第二种是用数组模拟弹出。
     *
     * @param n n个人
     * @param m 由第一个人开始报数，数到m的人出局。
     * @return 最后出局的人的Index
     */
    public int _46_LastRemaining_Solution1(int n, int m) {
        if (n <= 0 || m <= 0) {
            return -1;
        }
        int last = 0;
        //从size = 2 开始递推回去
        for (int i = 2; i <= n; ++i) {
            last = (last + m) % i;
        }
        return last;
    }

    public int _46_LastRemaining_Solution2(int n, int m) {
        if (n == 0) return -1;
        List<Integer> people = new LinkedList<>();
        //初始化整个list为n个人
        for (int i = 0; i < n; i++) {
            people.add(i);
        }
        int curIndex = 0;
        //不断弹出每轮淘汰的人的index
        while (people.size() > 1) {
            curIndex = (curIndex + m - 1) % people.size();
            people.remove(curIndex);
        }
        return people.get(0);
    }

    //巧用逻辑与的短路性质
    public int _47_Sum_Solution(int n) {
        int sum = n;
        boolean flag = (n > 1) && ((sum = _47_Sum_Solution(n - 1) + sum) > 0);
        return sum;
    }

    //巧用位运算,用两数相与和两数异或来模拟两数和运算，
    //两数相与（&），并左移，相当于求进位；两数异或，相当于忽略进位的和
    //不断进行相与和异或运算，直到进位值为0，运算结束
    public int _48_Add(int num1, int num2) {
        int next = (num1 & num2) << 1;
        int cur = num1 ^ num2;
        num1 = next;
        num2 = cur;
        while (num1 != 0) {
            next = (num1 & num2) << 1;
            cur = num1 ^ num2;
            num1 = next;
            num2 = cur;
        }
        return cur;
    }

    public int _49_StrToInt(String str) {
        if (str.trim().equals("")) return 0;
        boolean positive = true;
        int start = 0;
        if (str.charAt(0) == '-') {
            positive = false;
            start = 1;
        } else if (str.charAt(0) == '+') {
            start = 1;
        } else if ((int) str.charAt(0) < 48 || (int) str.charAt(0) > 57) {
            return 0;
        }
        int res = 0;
        for (int i = start; i < str.length(); i++) {
            if ((int) str.charAt(i) < 48 || (int) str.charAt(i) > 57) {
                return 0;
            }
            res = res * 10 + (str.charAt(i) - '0');
        }

        return positive ? res : -res;
    }

    /**
     * --题目描述--
     * 在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，但不知道
     * 有几个数字是重复的。也不知道每个数字重复几次。请找出数组中任意一个重复的数字。 例如，
     * 如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是第一个重复的数字2。
     * <p>
     * --解题思路--
     * 不考虑数组内数字范围，简单使用HashSet，占用了额外的空间
     *
     * @param numbers     an array of integers
     * @param length      the length of array numbers
     * @param duplication (Output) the duplicated number in the array number,
     *                    length of duplication array is 1,so using duplication[0]
     * @return true if the input is valid, and there are some duplications
     * in the array number otherwise false.
     */
    public boolean _50_duplicate1(int[] numbers, int length, int[] duplication) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < length; i++) {
            if (set.contains(numbers[i])) {
                duplication[0] = numbers[i];
                return true;
            } else {
                set.add(numbers[i]);
            }
        }
        return false;
    }

    /**
     * --解题思路--
     * 考虑到length=n且数组内元素大小范围为0-(n-1)，使用原数组来标记，交换当前index下的元素
     * 到index=元素value的对应位置，如果交换去的位置上存在index=value的元素，则重复。
     */
    public boolean _50_duplicate2(int[] numbers, int length, int[] duplication) {
        for (int i = 0; i < length; i++) {
            int cur = numbers[i];
            //如果当前位置的Index和Value不匹配，那么就去要交换位置
            if (i != cur) {
                //交换去的位置value不匹配，则进行交换
                if (numbers[cur] != cur) {
                    numbers[i] = numbers[cur];
                    numbers[cur] = cur;
                } else {
                    //交换去的位置已经存在一样的value，说明有重复
                    duplication[0] = numbers[i];
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * --题目描述--
     * 给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],其中B中的
     * 元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。不能使用除法。
     * <p>
     * --解题思路--
     * https://www.nowcoder.com/questionTerminal/94a4d381a68b47b7a8bed86f2975db46?f=discussion
     * 用户 披萨大叔的答案，
     * 利用矩阵，B数组元素为每一行，A数组元素为每一列，当行=列时候（对角线），矩阵值为1，
     * 可以很明显的将B数组看作矩阵中每行的乘积，对角线分割为2部分，上三角，下三角，利用A元素在
     * B数组中的重叠部分进行反复利用一些中间值，将复杂度降低为O(n)
     */
    public int[] _51_multiply(int[] A) {
        int[] B = new int[A.length];
        B[0] = 1;
        //下三角
        for (int i = 1; i < A.length; i++) {
            B[i] = B[i - 1] * A[i - 1];
        }
        int temp = 1;
        //上三角
        for (int j = A.length - 1; j >= 1; j--) {
            temp *= A[j];
            B[j - 1] *= temp;
        }
        return B;
    }

    /**
     * --题目描述--
     * 请实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而
     * '*'表示它前面的字符可以出现任意次（包含0次）。 在本题中，匹配是指字符串的所有字符匹配
     * 整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配
     * <p>
     * --解题思路--
     * Leetcode原题 https://leetcode.com/problems/regular-expression-matching/
     * 用动态规划解决
     */
    public boolean _51_match(char[] str, char[] pattern) {
        int stringLength = str.length;
        int patternLength = pattern.length;
        int[][] lenMatrix = new int[patternLength + 1][stringLength + 1];
        boolean isMatched;
        lenMatrix[0][0] = 1;
        for (int i = 0; i < patternLength; i++) {
            if (pattern[i] == '*' && lenMatrix[i - 1][0] == 1) {
                lenMatrix[i + 1][0] = 1;
            }
        }
        //iterative the matrix
        for (int i = 1; i <= patternLength; i++) {
            for (int j = 1; j <= stringLength; j++) {
                //当前pattern为'.'，肯定是要比之前长度+1
                if (pattern[i - 1] == '.') {
                    lenMatrix[i][j] = lenMatrix[i - 1][j - 1] + 1;
                    //当前Pattern为'*',分4种情况考虑
                } else if (pattern[i - 1] == '*') {
                    //x* only counts as empty，之前的Pattern不为'.'，且上一个字符不匹配
                    if (pattern[i - 2] != str[j - 1] && pattern[i - 2] != '.') {
                        lenMatrix[i][j] = lenMatrix[i - 2][j];
                        //上一个pattern匹配string字符的情况下，或者pattern为'.'
                    } else {
                        //x* counts as multiple a,矩阵中同行递增
                        if (lenMatrix[i][j - 1] == j) {
                            lenMatrix[i][j] = lenMatrix[i][j - 1] + 1;
                        }
                        //x* counts as single a，矩阵中同列递增
                        if (lenMatrix[i - 1][j] == j + 1) {
                            lenMatrix[i][j] = lenMatrix[i - 1][j];
                        }
                        //x* counts as empty，回溯到2列前的值
                        if (lenMatrix[i - 2][j] == j + 1) {
                            lenMatrix[i][j] = lenMatrix[i - 2][j];
                        }
                    }
                    //当前Pattern为普通字符
                } else {
                    if (pattern[i - 1] == str[j - 1]) {
                        lenMatrix[i][j] = lenMatrix[i - 1][j - 1] + 1;
                    }
                }
            }
        }
        //最终结果最后那格子的数字必定是原长度+1，否则不匹配
        isMatched = lenMatrix[patternLength][stringLength] == stringLength + 1;
        return isMatched;
    }

    /**
     * --题目描述--
     * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100","5e2"
     * ,"-123","3.1416"和"-1E-16"都表示数值。 但是"12e","1a3.14","1.2.3","+-5"和
     * "12e+4.3"都不是。
     * <p>
     * --解题思路--
     * 正则表达式
     */
    public boolean _52_isNumeric(char[] str) {
        String s = String.valueOf(str);
        //Java中 \\ 才表示其他语言中的一个 \
        return s.matches("[+-]?\\d*(\\.\\d+)?([eE][-+]?\\d+)?");
    }


    /**
     * --题目描述--
     * 请实现一个函数用来找出字符流中第一个只出现一次的字符。例如，当从字符流中只读出前两个
     * 字符"go"时，第一个只出现一次的字符是"g"。当从该字符流中读出前六个字符“google"时，
     * 第一个只出现一次的字符是"l"。
     * <p>
     * --解题思路--
     * LinkedHashMap按添加顺序存储元素,最后遍历到第一个value为1为结果
     * <p>
     * 还可以用一个队列来存储当前情况下的FirstAppearingOnce的元素，每次添加的时候，不断弹出
     * 队列首位的元素，让队列首位的元素保持为最终结果。
     */
    Map<Character, Integer> map = new LinkedHashMap<>();

    //Insert one char from stringstream
    public void _53_Insert(char ch) {
        map.put(ch, map.getOrDefault(ch, 0) + 1);
    }

    //return the first appearance once char in current stringstream
    public char _53_FirstAppearingOnce() {
        Iterator<Map.Entry<Character, Integer>> iterator =
                map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Character, Integer> entry = iterator.next();
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return '#';
    }

    /**
     * --题目描述--
     * 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
     * <p>
     * --解题思路--
     * 双指针思想，快指针速度V2是慢指针V1的2倍(2V1 = V2)，相遇时耗费时间相同t,则慢指针走过
     * 的路程为S1 = V1*t,快指针走过的路程为S2 = V2*t,因为相遇，所以快指针比慢指针多走了n
     * 圈环，假设环的长度为Sc,则S1 + n*Sc = S2,化简后得n*Sc = S1,设环之前的长度为Ss，
     * 慢指针在环上走了Sa,离环入口距离Sb (Sa + Sb = Sc),
     * 则 n*Sc = Ss + Sa => n*Sc = Ss + Sc - Sb =>（n-1)*Sc + Sb = Ss
     * 则环前面的距离为数个环的长度加上当前慢指针离环入口的距离，所以在第一次相遇后，让慢指针
     * 继续走，同时在开始点也让一个和慢指针同样速度的指针开始走，他们最终相遇点即在环入口处。
     */
    public ListNode _54_EntryNodeOfLoop(ListNode pHead) {
        if (pHead == null) return null;
        ListNode p1 = pHead;
        ListNode p2 = pHead;
        while (p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
            if (p1 == p2) {
                p2 = pHead;
                while (p1 != p2) {
                    p1 = p1.next;
                    p2 = p2.next;
                }
                return p1;
            }
        }
        return null;
    }

    /**
     * --题目描述--
     * 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，
     * 返回链表头指针。 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
     * <p>
     * --解题思路--
     * 双指针思想，记录重复结点开始的index和结束的index，去除所有重复结点
     */
    public ListNode _55_deleteDuplication(ListNode pHead) {
        if (pHead == null) return null;
        ListNode res = new ListNode(0);
        res.next = pHead;
        ListNode cur = res;
        ListNode end = pHead;
        while (end != null) {
            if (end.next != null && cur.next.val == end.next.val) {
                while (end.next != null && cur.next.val == end.next.val) {
                    end = end.next;
                }
                end = end.next;
                cur.next = end;
            } else {
                cur = cur.next;
                end = end.next;
            }
        }
        return res.next;
    }

    /**
     * --题目描述--
     * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，
     * 树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
     * <p>
     * --解题思路--
     * 画图分析，可得在中序遍历中，当前节点的下一个节点可分为两种情况，
     * 1.当前节点有right child，找right child的left child的left child....直到
     * left child 为 null
     * 2.当前节点没right child，找parent,且当前节点必须是这个parent的left child.不断
     * 递归找parent直到满足为left child或者不满足返回null
     */
    public TreeLinkNode _56_GetNext(TreeLinkNode pNode) {
        if (pNode.right != null) {
            pNode = pNode.right;
            while (pNode.left != null) {
                pNode = pNode.left;
            }
        } else {
            while (true) {
                if (pNode.next == null) {
                    return null;
                } else if (pNode == pNode.next.left) {
                    pNode = pNode.next;
                    break;
                } else {
                    pNode = pNode.next;
                }
            }
        }
        return pNode;
    }

    /**
     * --题目描述--
     * 请实现一个函数，用来判断一颗二叉树是不是对称的。注意，如果一个二叉树同此二叉树
     * 的镜像是同样的，定义其为对称的。
     * <p>
     * --解题思路--
     * 1.递归
     * 2.迭代,BFS的思路，同一层镜像位置同时入队，同时出队
     */
    boolean _57_isSymmetrical(TreeNode pRoot) {
        if (pRoot == null) return false;
        Queue<TreeNode> level = new LinkedList<>();
        level.offer(pRoot.left);
        level.offer(pRoot.right);
        while (!level.isEmpty()) {
            TreeNode left = level.poll();
            TreeNode right = level.poll();
            if (left == null && right == null) {
                continue;
            } else if (left == null && right != null) {
                return false;
            } else if (left != null && right == null) {
                return false;
            } else if (left.val != right.val) {
                return false;
            } else {
                level.offer(left.left);
                level.offer(right.right);
                level.offer(left.right);
                level.offer(right.left);
            }

        }
        return true;
    }

    /**
     * --题目描述--
     * 请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右至
     * 左的顺序打印，第三行按照从左到右的顺序打印，其他行以此类推。
     * <p>
     * --解题思路--
     * 两个Stack分别存奇数和偶数行的节点，一层层的输出stack即可，遇到偶数行，先存当前节点
     * 的right child 再存 left child使输出顺序正常
     */
    public ArrayList<ArrayList<Integer>> _58_Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (pRoot == null) return res;
        //存偶数行的节点
        Stack<TreeNode> stackEven = new Stack<>();
        //存奇数行的节点
        Stack<TreeNode> stackOdd = new Stack<>();
        stackOdd.push(pRoot);
        int levelCount = 0;
        while (!stackOdd.isEmpty() || !stackEven.isEmpty()) {
            levelCount++;
            int levelSize = (levelCount & 1) == 1 ? stackOdd.size() : stackEven.size();
            ArrayList<Integer> curLevel = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode cur = (levelCount & 1) == 1 ? stackOdd.pop() : stackEven.pop();
                if ((levelCount & 1) == 1) {
                    if (cur.left != null) stackEven.push(cur.left);
                    if (cur.right != null) stackEven.push(cur.right);
                } else {
                    if (cur.right != null) stackOdd.push(cur.right);
                    if (cur.left != null) stackOdd.push(cur.left);
                }
                curLevel.add(cur.val);
            }
            res.add(curLevel);
        }
        return res;
    }

    /**
     * --题目描述--
     * 从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。
     * <p>
     * --解题思路--
     * 比上一题简单，用一个Queue做BFS的level Order
     */
    public ArrayList<ArrayList<Integer>> _59_Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (pRoot == null) return res;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(pRoot);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            ArrayList<Integer> curLevel = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode cur = queue.poll();
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
                curLevel.add(cur.val);
            }
            res.add(curLevel);
        }
        return res;
    }

    /**
     * --题目描述--
     * 请实现两个函数，分别用来序列化和反序列化二叉树
     * 二叉树的序列化是指：把一棵二叉树按照某种遍历方式的结果以某种格式保存为字符串，从而使
     * 得内存中建立起来的二叉树可以持久保存。序列化可以基于先序、中序、后序、层序的二叉树
     * 遍历方式来进行修改，序列化的结果是一个字符串，序列化时通过 某种符号表示空节点
     * （#），以 ！ 表示一个结点值的结束（value!）。
     * 二叉树的反序列化是指：根据某种遍历顺序得到的序列化字符串结果str，重构二叉树。
     * <p>
     * --解题思路--
     */
    /*public String _60_Serialize(TreeNode root) {

    }

    public TreeNode _60_Deserialize(String str) {

    }*/

    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        if (num == null || num.length == 0 || size <= 0 || num.length < size) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> result = new ArrayList<>();
        //双端队列，用来记录每个窗口的最大值下标
        LinkedList<Integer> qmax = new LinkedList<>();
        for (int i = 0; i < num.length; i++) {
            //把前面比当前index元素小的元素都弹出，存在的必定比他大
            while (!qmax.isEmpty() && num[qmax.peekLast()] < num[i]) {
                qmax.pollLast();
            }
            //加入自己的下标
            qmax.addLast(i);
            //判断队首元素是否过期
            if (qmax.peekFirst() == i - size) {
                qmax.pollFirst();
            }
            //向result列表中加入元素
            if (i >= size - 1) {
                result.add(num[qmax.peekFirst()]);
            }
        }
        return result;
    }

}
