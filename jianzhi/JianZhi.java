package sj.jianzhi;


import sj.util.ListNode;
import sj.util.RandomListNode;
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
//        TreeNode root1 = new TreeNode(8);
//        TreeNode node1 = new TreeNode(8);
//        TreeNode node2 = new TreeNode(7);
//        TreeNode node3 = new TreeNode(9);
//        TreeNode node4 = new TreeNode(2);
//        TreeNode node5 = new TreeNode(4);
//        TreeNode node6 = new TreeNode(7);
//        root1.left = node1;
//        root1.right = node2;
//        node1.left = node3;
//        node1.right = node4;
//        node4.left = node5;
//        node4.right = node6;
//        //tree2
//        TreeNode root2 = new TreeNode(8);
//        TreeNode node7 = new TreeNode(9);
//        TreeNode node8 = new TreeNode(2);
//        root2.left = node7;
//        root2.right = node8;
//        System.out.println(jz._15_HasSubtree(root1,root2));

//        int[][] test1 = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}};
//        int[][] test2 = {{1}, {2}, {3}, {4}, {5}};
//        int[][] test3 = {{1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10}};
//        int[][] test4 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
//        int[][] test5 = {{1, 2, 3, 4, 5}};
//        System.out.println(jz._17_printMatrix(test4));

//        int[] test1 = {1, 3, 2, 5, 7, 6, 4};
//        System.out.println(jz._21_VerifySquenceOfBST(test1));

        TreeNode root1 = new TreeNode(10);
        TreeNode node1 = new TreeNode(5);
        TreeNode node2 = new TreeNode(12);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(7);
        root1.left = node1;
        root1.right = node2;
        node1.left = node3;
        node1.right = node4;
        System.out.println(jz._22_FindPath(root1, 22));
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

    //从左上角不断缩小最大列的限制，（其实从左下角最佳，小了向左，大了向上）
    public boolean _1_Find(int target, int[][] array) {
        int row = array.length;
        int col = array[0].length;
        int maxRow = 0;
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
    public void _11_reOrderArray(int[] array) {
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
    public ListNode _12_FindKthToTail(ListNode head, int k) {
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
    public ListNode _13_ReverseList(ListNode head) {
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
    public ListNode _14_Merge(ListNode list1, ListNode list2) {
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
        List<Integer> pre = new ArrayList<>();
        inOrderHelper(root, pre);
        return pre;
    }

    public void inOrderHelper(TreeNode root, List<Integer> pre) {
        if (root != null) {
            inOrderHelper(root.left, pre);
            pre.add(root.val);
            inOrderHelper(root.right, pre);
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
            } else if (top.right != null) {
                nodes.add(top.right);
            }
            layer.add(top.val);
        }
        return layer;
    }

    public boolean _15_HasSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) return false;
        Queue<TreeNode> nodes1 = new LinkedList<>();
        nodes1.add(root1);
        //level order 来一层层扫下去每个node
        while (!nodes1.isEmpty()) {
            TreeNode top = nodes1.poll();
            if (top.left != null) {
                nodes1.add(top.left);
            } else if (top.right != null) {
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
                if (top.left != null) nodes1.add(top.left);
                if (top.right != null) nodes1.add(top.right);
                if (top2.left != null) nodes2.add(top2.left);
                if (top2.right != null) nodes2.add(top2.right);
            }
        }
        return true;
    }

    //数的递归的应用
    public void _16_Mirror(TreeNode root) {
        if (root != null) {
            if (root.left != null && root.right != null) {
                TreeNode temp = root.left;
                root.left = root.right;
                root.right = temp;
                _16_Mirror(root.left);
                _16_Mirror(root.right);
            } else if (root.left != null) {
                root.right = root.left;
                root.left = null;
                _16_Mirror(root.right);
            } else if (root.right != null) {
                root.left = root.right;
                root.right = null;
                _16_Mirror(root.left);
            }
        }
    }

    //不断绕圈往里缩小，每到matrix的角，进行缩小边界
    public ArrayList<Integer> _17_printMatrix(int[][] matrix) {
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
    public class _18_MyStack {

        Stack<Integer> stack = new Stack<>();
        Stack<Integer> minStack = new Stack<>();

        public void push(int node) {
            stack.push(node);
            if (minStack.isEmpty()) {
                minStack.push(node);
            } else {
                if (node < minStack.peek()) minStack.push(node);
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
    public boolean _19_IsPopOrder(int[] pushA, int[] popA) {
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
    public ArrayList<Integer> _20_PrintFromTopToBottom(TreeNode root) {
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
    public boolean _21_VerifySquenceOfBST(int[] sequence) {
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
    public ArrayList<ArrayList<Integer>> _22_FindPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if(root == null) return res;
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
                if(preSum<target){
                    if(preNode.left != null){
                        nodes.add(preNode.left);
                        sum.add(preSum+preNode.left.val);
                        ArrayList<Integer> curPath = new ArrayList<>(prePath);
                        curPath.add(preNode.left.val);
                        path.add(curPath);
                    }
                    if(preNode.right != null){
                        nodes.add(preNode.right);
                        sum.add(preSum+preNode.right.val);
                        ArrayList<Integer> curPath = new ArrayList<>(prePath);
                        curPath.add(preNode.right.val);
                        path.add(curPath);
                    }
                }else if (preSum == target){
                    if(preNode.left == null && preNode.right == null){
                        res.add(0,prePath);
                    }
                }
            }
        }
        return res;
    }


    public RandomListNode _23_Clone(RandomListNode pHead) {

    }
}
