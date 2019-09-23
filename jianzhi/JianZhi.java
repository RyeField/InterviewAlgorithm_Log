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
    }

    public void input() {
        Scanner sc = new Scanner(System.in);
        String next = "";
        while (sc.hasNextLine()) {
            next = sc.nextLine();
        }
    }

    /**
     * --题目描述--
     * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列
     * 都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数
     * 组中是否含有该整数。
     * <p>
     * --解题思路--
     * 从左上角不断缩小最大列的限制
     * 最佳方案：从左下角开始匹配，小了向右，大了向上
     */
    public boolean _1_Find1(int target, int[][] array) {
        int rows = array.length;
        int maxColumn = array[0].length;
        for (int i = 0; i < rows; i++) {
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

    public boolean _1_Find2(int target, int[][] array) {
        int row = array.length - 1;
        int col = 0;
        while (row >= 0 && col < array[0].length) {
            if (array[row][col] < target) {
                col++;
            } else if (array[row][col] > target) {
                row--;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * --题目描述--
     * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.
     * 则经过替换之后的字符串为We%20Are%20Happy。
     * <p>
     * --解题思路--
     * 1.直接用把StringBuffer转换为String调用replace
     * 2.用了空间申请一个StringBuilder或StringBuffer对象，遍历一遍，不断追加
     * 3.源字符串先遍历一遍得出空格的多少来进行扩容，扩容后从后往前遍历，移动旧char到新的位置
     * 或者改变 ' ' 为 '%' '2' '0'
     */
    public String _2_replaceSpace(StringBuffer str) {
        StringBuilder sb = new StringBuilder();
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

    /**
     * --题目描述--
     * 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
     * <p>
     * --解题思路--
     * 1.递归的思想
     * 2.借助Stack先进后出
     */
    public ArrayList<Integer> _3_printListFromTailToHead1(ListNode listNode) {
        ArrayList<Integer> reveList = new ArrayList<>();
        if (listNode != null) {
            tailListHelper(listNode.next, reveList);
            reveList.add(listNode.val);
        }
        return reveList;
    }

    private void tailListHelper(ListNode listNode, ArrayList<Integer> reveList) {
        if (listNode != null) {
            tailListHelper(listNode.next, reveList);
            reveList.add(listNode.val);
        }
    }

    public ArrayList<Integer> _3_printListFromTailToHead2(ListNode listNode) {
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

    /**
     * --题目描述--
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历
     * 的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列
     * {4,7,2,1,5,3,8,6}，则重建二叉树并返回。
     * <p>
     * --解题思路--
     * 递归的应用。做Tree相关一直习惯递归了。
     * 前序遍历的首个元素即为root，根据root在中序遍历中的位置把Tree分成left，right两部分，
     * 根据中序遍历得到的size来把前序遍历分成left,right两部分，每个部分的首元素即为新root
     * 递归左右两部分，最终得到解
     * 自己写的这个太过复杂，牛客网上有精炼的答案，引用了“前进的路上”的答案作为参考
     * https://www.nowcoder.com/questionTerminal/8a19cbe657394eeaac2f6ea9b0f6fcf6?f=discussion
     */
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

    private void findSubTree(List<Integer> in, List<Integer> pre,
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

    public TreeNode _4_reConstructBinaryTree2(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0) {
            return null;
        }
        TreeNode node = new TreeNode(pre[0]);
        for (int i = 0; i < in.length; i++) {
            //pre[0] 即为root, root在inOrder的位置得到了left和right的size，再去分割preOder
            if (pre[0] == in[i]) {
                node.left = _4_reConstructBinaryTree2(Arrays.copyOfRange(pre, 1,
                        i + 1), Arrays.copyOfRange(in, 0, i));
                node.right = _4_reConstructBinaryTree2(Arrays.copyOfRange(pre,
                        i + 1, pre.length), Arrays.copyOfRange(in, i + 1, in.length));
            }
        }
        return node;
    }

    /**
     * --题目描述--
     * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
     * <p>
     * --解题思路--
     * 正常push到第一个stack中去，
     * pop的时候，先将第一个stack中的所有元素依次pop，再push到第二个stack中，此时，第二个
     * stack的顶部即为模拟queue要pop的元素，pop后，再倒回第一个stack
     */
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

    /**
     * --题目描述--
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
     * 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
     * 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
     * NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
     * <p>
     * --解题思路--
     * 1.因为是非递减数组的旋转，所以遇到的第一个比array[0]小的元素就是本来的首元素，即最小值
     * 2.用二分搜索，Binary search的变形
     */
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

    public int _6_minNumberInRotateArray_2(int[] array) {
        if (array.length == 0) return 0;
        int low = 0, high = array.length - 1;
        //因为是旋转数组，首元素必定大于末尾元素
        while (low < high) {
            int mid = (low + high) / 2;
            if (array[mid] > array[high]) {
                //最小值在右边部分,如8>2,min=0,[4,5,6,7,'8',9,"0",1,'2']
                low = mid + 1;
            } else if (array[mid] == array[high]) {
                //含有相同数字
                high = high - 1;
            } else {
                //最小值在左边部分,如2<5,min=1,[6,7,"1",'2',3,4,'5']
                high = mid;
            }
        }
        return Math.min(array[low], array[high]);
    }

    /**
     * --题目描述--
     * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项
     * （从0开始，第0项为0）。n<=39
     * <p>
     * --解题思路--
     * 迭代求解斐波那契数列
     */
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

    /**
     * --题目描述--
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法
     * （先后次序不同算不同的结果）。
     * <p>
     * --解题思路--
     * 斐波那契数列应用。找规律，或者理论推导得出F(n) = F(n-1) + F(n-2)即跳上第n阶台阶可
     * 以从n-1跳一步或者从n-2跳一步，即两者相加，不断递推得出斐波那契数列规律，用迭代解
     */
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

    private long factorial(int n) {
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

    /**
     * --题目描述--
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台
     * 阶总共有多少种跳法。
     * <p>
     * --解题思路--
     * 1.排列组合，很耗时
     * 2.永远都是之前的两倍，排列组合的证明 就是1里面的公式
     */
    public int _9_JumpFloorII_1(int target) {
        int combination = 1;
        for (int k = 1; k < target; k++) {
            combination +=
                    factorial(target - 1) / (factorial(k) * factorial(target - 1 - k));
        }
        return combination;
    }

    public int _9_JumpFloorII_2(int target) {
        return 1 << (target - 1); //位运算，a*2^(n-1)
    }

    /**
     * --题目描述--
     * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一
     * 个2*n的大矩形，总共有多少种方法？
     * <p>
     * --解题思路--
     * 斐波那契数列应用
     */
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

    /**
     * --题目描述--
     * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
     * <p>
     * --解题思路--
     * 位操作的运用，前两个Java内置API
     */
    public int _11_NumberOf1(int n) {
//        return Integer.toBinaryString(n).replace("0","").length();
//        return Integer.bitCount(n);
        int count = 0, flag = 1;
        for (int i = 0; i < 32; i++) {
            //不断右移，和1与操作 1&1 = 1, 0&1 = 0
            if ((n >> i & flag) == 1) {
                count++;
            }
        }
        return count;
    }

    /**
     * --题目描述--
     * 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
     * 保证base和exponent不同时为0
     * <p>
     * --解题思路--
     * 平方求幂(exponentiation by squaring) 或 快速幂 的思想
     * 快速幂 https://oi-wiki.org/math/quick-pow/
     */
    public double _12_Power(double base, int exponent) {
        //flag确立正负
        boolean flag = true;
        double res = 1;
        if (exponent == 0) {
            return 1;
        } else if (exponent < 0) {
            flag = false;
            //负的改为正的，最后求倒数
            exponent = -exponent;
        }
        //不断右移exponent，在base不断递增时，exponent当前位为1，则base*res
        //比如3^5=243，base=3, exponent=5->101.
        //1.res=1*3=3, base=3*3=9, exponent>>1=10;
        //2.res=3不变, base=9*3=27, exponent>>1=1;
        //3.res=3*27=243, base=27*3=243, exponent>>1=0; 结束
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                res *= base;
            }
            base *= base;
            exponent >>= 1;
        }
        return flag ? res : 1 / res;
    }

    /**
     * --题目描述--
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部
     * 分，所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
     * <p>
     * --解题思路--
     * 类似于插入排序，不然就是在建立一个数组存偶数放最后
     */
    public void _13_reOrderArray(int[] array) {
        //记录已经放好的奇数
        int min = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 1 && i > 0) {
                int temp = array[i];
                //把当前index之前所有的偶数后移一位
                for (int k = i; k > min; k--) {
                    array[k] = array[k - 1];
                }
                //放好奇数，数量+1
                array[min] = temp;
                min++;
            } else if (array[i] % 2 == 1 && i == 0) {
                min = 1;
            }
        }
    }

    /**
     * --题目描述--
     * 输入一个链表，输出该链表中倒数第k个结点。
     * <p>
     * --解题思路--
     * 双指针，一个领先k步
     */
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

    /**
     * --题目描述--
     * 输入一个链表，反转链表后，输出新链表的表头。
     * <p>
     * --解题思路--
     * 迭代解法，也可以递归。。不是太习惯。
     */
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

    /**
     * --题目描述--
     * 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不
     * 减规则
     * <p>
     * --解题思路--
     * 迭代解法
     */

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
    private List<Integer> inOrder(TreeNode root) {
        List<Integer> in = new ArrayList<>();
        inOrderHelper(root, in);
        return in;
    }

    private void inOrderHelper(TreeNode root, List<Integer> in) {
        if (root != null) {
            inOrderHelper(root.left, in);
            in.add(root.val);
            inOrderHelper(root.right, in);
        }
    }

    private List<Integer> levelOrder(TreeNode root) {
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

    /**
     * --题目描述--
     * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
     * <p>
     * --解题思路--
     * level order一层层扫，当A树当前node的value和B树Root的value一样时候，调用下一个函数，
     * 继续用level order的思想比较两棵树是否相同
     * 牛客网上答案很多递归解法
     */
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
    private boolean isSubtree(TreeNode root1, TreeNode root2) {
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

    /**
     * --题目描述--
     * 操作给定的二叉树，将其变换为源二叉树的镜像。
     * <p>
     * --解题思路--
     * 树的递归的应用
     */
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

    /**
     * --题目描述--
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，
     * 如果输入如下4 X 4 矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
     * 则依次打印出数字 1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
     * <p>
     * --解题思路--
     * 不断绕圈往里缩小，每到matrix的角，进行缩小边界
     */
    public ArrayList<Integer> _19_printMatrix(int[][] matrix) {
        int maxRow = matrix.length - 1;
        int maxCol = matrix[0].length - 1;
        int minRow = 0;
        int minCol = 0;
        ArrayList<Integer> res = new ArrayList<>();
        //0.0点开始转圈到中心
        while (true) {
            //从row 0 开始，row不变，col到max结束，row Min++
            res.addAll(Arrays.stream(matrix[minRow], minCol, maxCol + 1).boxed().collect(Collectors.toList()));
            minRow++;
            //从col Max开始，col不变，row到max结束，col Max--
            for (int i = minRow; i <= maxRow; i++) {
                int col1 = matrix[i][maxCol];
                res.add(col1);
            }
            maxCol--;
            //从row Max开始(rowMin<=rowMax的情况下), row不变，col到min结束,row Max--
            if (minRow <= maxRow) {
                List<Integer> bottomRow = Arrays.stream(matrix[maxRow], minCol,
                        maxCol + 1).boxed().collect(Collectors.toList());
                Collections.reverse(bottomRow);
                res.addAll(bottomRow);
                maxRow--;
            }
            //从col Min开始(colMin<=colMax的情况下)，col不变，row到max结束，col Min++
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

    /**
     * --题目描述--
     * 定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。
     * <p>
     * --解题思路--
     * 辅助栈存储当前栈中最小值,加入辅助栈的数字必定比当前辅助栈的栈顶小
     * 弹出时，辅助栈和普通栈顶元素相同时，弹出，不然辅助栈不动
     */
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
            if (stack.peek().equals(minStack.peek())) minStack.pop();
            stack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int min() {
            return minStack.peek();
        }
    }

    /**
     * --题目描述--
     * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。
     * 假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，序列4,5,3,2,1是该
     * 压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。
     * （注意：这两个序列的长度是相等的）
     * <p>
     * --解题思路--
     * 辅助stack模拟pop过程，用pushA数组的顺序压入辅助Stack，当Stack顶和popB数组元素相同
     * 时，弹出Stack顶元素，且popA的指针前移一位，直到pushA数组全部入栈一次且结束循环，
     * 如果最后指针index没有走到最后，则说明popA不是pushA的一个栈弹出顺序
     */
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

    /**
     * --题目描述--
     * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
     * <p>
     * --解题思路--
     * 层序遍历二叉树，一层层打印树，用Queue,弹出一个node，加入他的children
     * （第60题是本题加强版本，加入了每一个level的size，最后输出List of List）
     */
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

    /**
     * --题目描述--
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则
     * 输出No。假设输入的数组的任意两个数字都互不相同。
     * <p>
     * --解题思路--
     * 递归解法
     * 最后一个元素是root，前半部分list小于root，后半部分list必须都大于root才是true
     */
    public boolean _23_VerifySquenceOfBST(int[] sequence) {
        if (sequence.length == 0) return false;
        if (sequence.length <= 2) return true;
        return verifySequenceOfBASTHelper(sequence);
    }

    private boolean verifySequenceOfBASTHelper(int[] sequence) {
        if (sequence.length <= 2) return true;
        int curRoot = sequence[sequence.length - 1];
        boolean shouldLarger = false;
        int splitIndex = 0;
        for (int i = 0; i < sequence.length - 1; i++) {
            //找到splitIndex，之后的元素都应该大于Root
            if (!shouldLarger && sequence[i] > curRoot) {
                splitIndex = i;
                shouldLarger = true;
            }
            if (shouldLarger && sequence[i] < curRoot) return false;
        }
        //递归求解
        return verifySequenceOfBASTHelper(Arrays.copyOfRange(sequence, 0,
                splitIndex)) & verifySequenceOfBASTHelper(Arrays.copyOfRange(sequence, splitIndex, sequence.length - 1));
    }

    /**
     * --题目描述--
     * 输入一颗二叉树的根节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。路径定
     * 义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
     * (注意: 在返回值的list中，数组长度大的数组靠前)
     * <p>
     * --解题思路--
     * 非递归解法，三个队列，BFS的思路层序遍历，不断更新，符合条件的路径放入最后结果ArrayList
     * 考虑路径长的放在前面，因为是BFS，所以每次拿到符合的路径（肯定比之前的长），放在0位置
     */
    public ArrayList<ArrayList<Integer>> _24_FindPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        //三个队列是同步 入队 和 出队 操作
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
                //弹出的这个节点Node，之前的到这个node的Path，之前的Path的Sum
                int preSum = sum.poll();
                TreeNode preNode = nodes.poll();
                ArrayList<Integer> prePath = path.poll();
                if (preSum < target) {
                    //入队leftChild相关属性的3个队列
                    if (preNode.left != null) {
                        nodes.add(preNode.left);
                        sum.add(preSum + preNode.left.val);
                        ArrayList<Integer> curPath = new ArrayList<>(prePath);
                        curPath.add(preNode.left.val);
                        path.add(curPath);
                    }
                    //入队rightChild相关属性的3个队列
                    if (preNode.right != null) {
                        nodes.add(preNode.right);
                        sum.add(preSum + preNode.right.val);
                        ArrayList<Integer> curPath = new ArrayList<>(prePath);
                        curPath.add(preNode.right.val);
                        path.add(curPath);
                    }
                } else if (preSum == target) {
                    //如果之前这个节点没有left和right child，则是叶子节点
                    //如果符合target的和，则把path加入到最后的res中(放在index=0位置)
                    if (preNode.left == null && preNode.right == null) {
                        res.add(0, prePath);
                    }
                }
            }
        }
        return res;
    }

    /**
     * --题目描述--
     * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针
     * 指向任意一个节点），返回结果为复制后复杂链表的head。
     * （注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
     * <p>
     * --解题思路--
     * hashMap 先 clone 每一个 node，并设置 next 关系
     * 再遍历一遍补上random关系
     * 讨论中还看到一个三步法，在原链表中进行操作
     */
    public RandomListNode _25_Clone(RandomListNode pHead) {
        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode curNode = pHead;
        //对clone node创建一个新的head
        RandomListNode preCloneNode = new RandomListNode(0);
        //遍历node，把clone node放入HashMap中且每个clone有next label变量值
        while (curNode != null) {
            //根据当前给定的cur node的label来创建一个新的clone node
            RandomListNode cloneNode = new RandomListNode(curNode.label);
            //前一个 pre clone node指向当前的clone node
            preCloneNode.next = cloneNode;
            //以curNode为key，放入clone node来方便后续补充random关系
            map.put(curNode, cloneNode);
            preCloneNode = cloneNode;
            curNode = curNode.next;
        }
        preCloneNode.next = null; //point the last node to null
        //遍历HashMap补上random关系
        Iterator<Map.Entry<RandomListNode, RandomListNode>> iterator =
                map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<RandomListNode, RandomListNode> entry = iterator.next();
            entry.getValue().random = map.get(entry.getKey().random);
        }
        return map.get(pHead);
    }

    /**
     * --题目描述--
     * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，
     * 只能调整树中结点指针的指向。
     * <p>
     * --解题思路--
     * 基于Binary Tree的非递归中序遍历（等同于排序）
     * https://leetcode.com/problems/binary-tree-inorder-traversal/solution/
     */
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
     * --题目描述--
     * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由
     * 字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
     * <p>
     * --解题思路--
     * 字符串的全排列问题
     * 递归的算法，在固定不同的prefix的前提下（不断swap当前index的字符和后面的每一个字符来生成一个prefix）
     * 对每一个这样的prefix，再继续递归后续的字符，直到到达最后一个字符
     * 对于重复问题，如果在当前循环里，这个字符出现过，则不swap
     * 还有一种迭代算法，巧妙的找下一个最接近的排序，直到最后一个排序
     * https://www.nowcoder.com/questionTerminal/fe6b651b66ae47d7acce78ffdd9a96c7
     * 中“天天502”的答案解释了2种方法
     * 非递归字典序全排列算法在下面网址
     * https://www.cnblogs.com/pmars/archive/2013/12/04/3458289.html
     */
    public ArrayList<String> _27_Permutation(String str) {
        ArrayList<String> list = new ArrayList<>();
        if (str != null && str.length() > 0) {
            PermutationHelper(str.toCharArray(), 0, list);
        }
        Collections.sort(list);
        return list;
    }

    private void PermutationHelper(char[] strChar, int startIndex,
                                   ArrayList<String> list) {
        if (startIndex == strChar.length - 1) {
            /*StringBuilder sb = new StringBuilder();
            for (Character ch : strChar) sb.append(ch);
            list.add(sb.toString());*/
            list.add(String.valueOf(strChar)); //更简单的Char数组转换为String
        } else {
            // Set 放在这里防止出现重复，如果已经相同的元素出现过且与之前的元素交换过，
            // 则不需要再进行交换.不加这个set，可以在上面list.add语句之前加个判断，
            // list是否包含相同的String串，如果是，则不add.
            Set<Character> set = new HashSet<>();
            for (int i = startIndex; i < strChar.length; i++) {
                if (!set.contains(strChar[i])) {
                    set.add(strChar[i]);
                    swapSingleChar(strChar, startIndex, i);
                    PermutationHelper(strChar, startIndex + 1, list);
                    swapSingleChar(strChar, startIndex, i);
                }
            }
        }

    }

    private void swapSingleChar(char[] strChar, int indexFir, int indexSed) {
        char temp = strChar[indexFir];
        strChar[indexFir] = strChar[indexSed];
        strChar[indexSed] = temp;
    }

    /**
     * --题目描述--
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数
     * 组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。
     * 如果不存在则输出0。
     * <p>
     * --解题思路--
     * HashMap可以较简单解出
     */
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
            //复查是否符合，读了两遍array，其实有违算法初衷,但还是O(n)的复杂度
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

    /**
     * --题目描述--
     * 输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
     * <p>
     * --解题思路--
     * 维护一个最大堆即可，用siftdown函数自己维护了heap的性质，
     * 实质上用PriorityQueue即可,Java中默认PriorityQueue是Natural
     * Order，需要重写一下Comparator里的compare函数
     */
    public ArrayList<Integer> _29_GetLeastNumbers_Solution1(int[] input, int k) {
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

    public ArrayList<Integer> _29_GetLeastNumbers_Solution2(int[] input, int k) {
        if (k > input.length || k == 0) return new ArrayList<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int value : input) {
            if (maxHeap.size() < k) {
                maxHeap.offer(value);
            } else {
                if (value < maxHeap.peek()) {
                    maxHeap.poll();
                    maxHeap.offer(value);
                }
            }
        }
        ArrayList<Integer> res = new ArrayList<>(maxHeap);
        return res;
    }

    /**
     * --题目描述--
     * HZ偶尔会拿些专业问题来忽悠那些非计算机专业的同学。今天测试组开完会后,他又发话了:在古
     * 老的一维模式识别中,常常需要计算连续子向量的最大和,当向量全为正数的时候,问题很好解决。
     * 但是,如果向量中包含负数,是否应该包含某个负数,并期望旁边的正数会弥补它呢？
     * 例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)。
     * 给一个数组，返回它的最大连续子序列的和，你会不会被他忽悠住？(子向量的长度至少是1)
     * <p>
     * --解题思路--
     * 1.自己的思路，基本思路是负和抛弃然后从0开始
     * 2.动态规划的思路，记录以每个index结尾的subarray最大的和
     */
    public int _30_FindGreatestSumOfSubArray1(int[] array) {
        int sum = Integer.MIN_VALUE;
        int cur;
        int maxNegative = Integer.MIN_VALUE;
        int maxSum = Integer.MIN_VALUE;
        boolean firstPositive = true;
        for (int i = 0; i < array.length; i++) {
            cur = array[i];
            if (cur < 0) {
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
                    sum = 0;
                } else {
                    sum = sum + cur;
                    maxSum = Math.max(maxSum, sum);
                }
            }
        }
        maxSum = Math.max(maxSum, sum);
        return maxSum >= 0 ? maxSum : maxNegative;
    }


    public int _30_FindGreatestSumOfSubArray2(int[] array) {
        int sumMax = array[0];
        for (int i = 1; i < array.length; i++) {
            array[i] = Math.max(array[i - 1] + array[i], array[i]);
            sumMax = Math.max(array[i], sumMax);
        }
        return sumMax;
    }

    /**
     * --题目描述--
     * 求出1~13的整数中1出现的次数,并算出100~1300的整数中1出现的次数？为此他特别数了一下
     * 1~13中包含1的数字有1、10、11、12、13因此共出现6次,但是对于后面问题他就没辙了。
     * ACMer希望你们帮帮他,并把问题更加普遍化,可以很快的求出任意非负整数区间中1出现的次数
     * （从1 到 n 中1出现的次数）。
     * <p>
     * --解题思路--
     * 归纳法
     * 依次归纳个位，十位，百位总结出合理的通用公式
     * https://www.nowcoder.com/questionTerminal/bd7f978302044eee894445e244c7eee6?f=discussion
     * 用户 Duqcuid 总结的好棒
     */
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

    /**
     * --题目描述--
     * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的
     * 一个。例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
     * <p>
     * --解题思路--
     * 重新定义Comparator来进行对数组里数字的排序，让两者组合成为的新数字最小为条件，
     * 来判断O1，O2应该怎么做相对应的排序。
     */
    public String _32_PrintMinNumber(int[] numbers) {
        Integer[] numbersWapper = Arrays.stream(numbers).boxed().toArray(Integer[]::new);
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
     * --题目描述--
     * 把只包含质因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为
     * 它包含质因子7。 习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。
     * <p>
     * --解题思路--
     * https://www.nowcoder.com/questionTerminal/6aa9e04fc3794f68acf8778237ba065b?f=discussion
     * 用户 "事无巨细，悉究本末" 的答案C++版本的改写
     * 思路就是 丑数 = 2^x * 3^y * 5^z , 即丑数*2， *3， *5都会生成新丑数
     * 所以维护3个队列来存储每一个新丑数*2，*3，*5 所生成的潜在丑数，
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
            //最新的丑数对应的队列肯定是会把指针指向最终数组的末尾
            if (nextNum == twoUgly) p1++;
            if (nextNum == threeUgly) p2++;
            if (nextNum == fiveUgly) p3++;
            uglyNums.add(nextNum);
        }
        return uglyNums.get(index - 1);
    }

    /**
     * --题目描述--
     * 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返
     * 回它的位置, 如果没有则返回 -1（需要区分大小写）.
     * <p>
     * --解题思路--
     * 1.hash的思想，先存出现次数，找到第一个一次的元素，然后遍历str返回位置
     * 2.另一种小变形，先存出现次数，直接遍历str找到对应char在hash中value为1
     */
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
     * --题目描述--
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一
     * 个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。
     * 即输出P%1000000007
     * <p>
     * --解题思路--
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
     * --题目描述--
     * 输入两个链表，找出它们的第一个公共结点。
     * <p>
     * --解题思路--
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
     * --题目描述--
     * 统计一个数字在排序数组中出现的次数。
     * <p>
     * --解题思路--
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
    private int getFirstK(int[] array, int lo, int hi, int k) {
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
    private int getLastK(int[] array, int lo, int hi, int k) {
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
     * --题目描述--
     * 输入一棵二叉树，求该树的深度。从根结点到叶结点依次经过的结点（含根、叶结点）形成树的
     * 一条路径，最长路径的长度为树的深度。
     * <p>
     * --解题思路--
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

    /**
     * --题目描述--
     * 输入一棵二叉树，判断该二叉树是否是平衡二叉树。
     * <p>
     * --解题思路--
     * 自底向上
     */
    public boolean _39_IsBalanced_Solution(TreeNode root) {
        return getDepth(root) == -1;
    }

    private int getDepth(TreeNode root) {
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

    /**
     * --题目描述--
     * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。
     * 请写程序找出这两个只出现一次的数字。
     * <p>
     * --解题思路--
     * num1,num2分别为长度为1的数组。传出参数
     * 将num1[0],num2[0]设置为返回结果
     * 考察异或？数字自己和自己异或为0..，不同数字异或二进制当中肯定有1位是1，根据这1位1将
     * 原数组分成两个数组，再分别异或，最后两个数组最后留下的元素就是两个只出现一次的数字
     * 自己的解法就是简单的HashSet
     */
    public void _40_FindNumsAppearOnce(int[] array, int[] num1, int[] num2) {
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

    /**
     * --题目描述--
     * 小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。
     * 但是他并不满足于此,他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。没多久,
     * 他就得到另一组连续正数和为100的序列:18,19,20,21,22。现在把问题交给你,你能不能也很快
     * 的找出所有和为S的连续正数序列? Good Luck!
     * 输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
     * <p>
     * --解题思路--
     * 双指针思想
     */
    public ArrayList<ArrayList<Integer>> _41_FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        int lo = 1;
        int hi = 2;
        while (lo < hi) {
            //求和公式
            int curSum = (lo + hi) * (hi - lo + 1) / 2;
            if (curSum > sum) {
                lo++;
            } else if (curSum < sum) {
                hi++;
            } else {
                //相等时候，将区间内所有数字加入最后结果集合，但是lo指针只往前移动一位
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

    /**
     * --题目描述--
     * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对
     * 数字的和等于S，输出两个数的乘积最小的。
     * <p>
     * --解题思路--
     * 首位双指针，乘积最小的，相距也最远，所以就是第一组遇到的数字
     * 证明：
     * 作者：马客(Mark)
     * 链接：https://www.nowcoder.com/questionTerminal/390da4f7a00f44bea7c2f3d19491311b?f=discussion
     * 来源：牛客网
     * 找到的第一组（相差最大的）就是乘积最小的。可以这样证明：考虑x+y=C（C是常数），x*y的大小。
     * 不妨设y>=x，y-x=d>=0，即y=x+d, 2x+d=C, x=(C-d)/2, x*y=x(x+d)=(C-d)(C+d)/4=(C^2-d^2)/4，
     * 也就是x*y是一个关于变量d的二次函数，对称轴是y轴，开口向下。d是>=0的，d越大, x*y也就越小。
     */
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

    /**
     * --题目描述--
     * 汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个
     * 指令的运算结果。对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。例如，字符
     * 序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。
     * <p>
     * --解题思路--
     * 用了额外的空间解决
     * 剑指原理:利用字符串翻转。
     * 假设字符串abcdef，n=3，设X=abc，Y=def，所以字符串可以表示成XY，如题干，问如何求得YX。
     * 假设X的翻转为XT，XT=cba，同理YT=fed，那么YX=(XTYT)T，三次翻转后可得结果。
     */
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


    /**
     * --题目描述--
     * 牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。同事Cat
     * 对Fish写的内容颇感兴趣，有一天他向Fish借来翻看，但却读不懂它的意思。
     * 例如，“student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，正确的句子
     * 应该是“I am a student.”。Cat对一一的翻转这些单词顺序可不在行，你能帮助他么？
     * <p>
     * --解题思路--
     * 1.用了Java的方式处理
     * 2.题目本意可能是要求反转每个word，再反转整个句子。
     */
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

    private void swapCharInterval(char[] array, int startIndex, int endIndex) {
        while (startIndex < endIndex) {
            char temp = array[startIndex];
            array[startIndex++] = array[endIndex];
            array[endIndex--] = temp;
        }
    }

    /**
     * --题目描述--
     * LL今天心情特别好,因为他去买了一副扑克牌,发现里面居然有2个大王,2个小王(一副牌原本是54
     * 张^_^)...他随机从中抽出了5张牌,想测测自己的手气,看看能不能抽到顺子,如果抽到的话,他决
     * 定去买体育彩票,嘿嘿！！“红心A,黑桃3,小王,大王,方片5”,“Oh My God!”不是顺子.....LL
     * 不高兴了,他想了想,决定大\小 王可以看成任何数字,并且A看作1,J为11,Q为12,K为13。上面的
     * 5张牌就可以变成“1,2,3,4,5”(大小王分别看作2和4),“So Lucky!”。LL决定去买体育彩票啦。
     * 现在,要求你使用这幅牌模拟上面的过程,然后告诉我们LL的运气如何，如果牌能组成顺子就输出
     * true，否则就输出false。为了方便起见,你可以认为大小王是0。
     * <p>
     * --解题思路--
     * 总范围为1-14，且大小王可算作任意值（输入为0），则说明找到输入数组里除了大小王（0）之
     * 外的最大值，最小值，最大值-最小值 < 5则证明为连续（不得有重复值）
     */
    public boolean _45_isContinuous(int[] numbers) {
        if (numbers.length < 5) return false;
        int[] allNums = new int[14];
        int startIndex = 14;
        int endIndex = -1;
        for (int num : numbers) {
            //略过大小王
            if (num != 0) {
                //更新头尾index（找最大值，最小值）
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
     * --题目描述--
     * 每年六一儿童节,牛客都会准备一些小礼物去看望孤儿院的小朋友,今年亦是如此。HF作为牛客的
     * 资深元老,自然也准备了一些小游戏。其中,有个游戏是这样的:首先,让小朋友们围成一个大圈。
     * 然后,他随机指定一个数m,让编号为0的小朋友开始报数。每次喊到m-1的那个小朋友要出列唱首
     * 歌,然后可以在礼品箱中任意的挑选礼物,并且不再回到圈中,从他的下一个小朋友开始,
     * 继续0...m-1报数....这样下去....直到剩下最后一个小朋友,可以不用表演,并且拿到牛客名贵
     * 的“名侦探柯南”典藏版(名额有限哦!!^_^)。请你试着想下,哪个小朋友会得到这份礼品呢？
     * (注：小朋友的编号是从0到n-1) 如果没有小朋友，请返回-1
     * <p>
     * --解题思路--
     * 约瑟夫问题 即由n个人坐成一圈，按顺时针由1开始给他们编号。然后由第一个人开始报数，
     * 数到m的人出局。现在需要求的是最后一个出局的人的编号。
     * 解法，
     * 1.一种是数学推导，得出递推公式
     * 2. 第二种是用数组模拟弹出。
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

    /**
     * --题目描述--
     * 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及
     * 条件判断语句（A?B:C）。
     * <p>
     * --解题思路--
     * 巧用逻辑与的短路性质
     */
    public int _47_Sum_Solution(int n) {
        int sum = n;
        boolean flag = (n > 1) && ((sum = _47_Sum_Solution(n - 1) + sum) > 0);
        return sum;
    }

    /**
     * --题目描述--
     * 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。
     * <p>
     * --解题思路--
     * 巧用位运算,用两数相与和两数异或来模拟两数和运算，
     * 两数相与（&），二进制每一位都是与操作，并左移，相当于求进位；
     * 两数异或，相当于忽略进位的和
     * 不断进行相与和异或运算，直到进位值为0，运算结束（每一步两个二进制数和都是最终答案）
     */
    public int _48_Add(int num1, int num2) {
        int next = (num1 & num2) << 1; //进位值
        int cur = num1 ^ num2; //当前值
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

    /**
     * --题目描述--
     * 将一个字符串转换成一个整数(实现Integer.valueOf(string)的功能，但是string不符合数
     * 字要求时返回0)，要求不能使用字符串转换整数的库函数。
     * 数值为0或者字符串不是一个合法的数值则返回0。
     * <p>
     * --解题思路--
     * 利用了ASCII Table，数字0-9在对应的Char是48-57
     */
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
    public boolean _52_match(char[] str, char[] pattern) {
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
                if (pattern[i - 1] == '.') {
                    //当前pattern为'.'，肯定是要比之前长度+1
                    lenMatrix[i][j] = lenMatrix[i - 1][j - 1] + 1;
                } else if (pattern[i - 1] == '*') {
                    //当前Pattern为'*',分4种情况考虑
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
                } else {
                    //当前Pattern为普通字符
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
    public boolean _53_isNumeric(char[] str) {
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
     * 因为是字符Character(16bits，0-65536)
     * Java采用Unicode编码集--其中包括了ASCII(0-127)和别的Characters
     * 在本题可以创建一个128位长度的array来存出现次数（ASCII character table在本地中足够用）
     */
    Map<Character, Integer> map = new LinkedHashMap<>();

    //Insert one char from stringstream
    public void _54_Insert(char ch) {
        map.put(ch, map.getOrDefault(ch, 0) + 1);
    }

    //return the first appearance once char in current stringstream
    public char _54_FirstAppearingOnce() {
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
    public ListNode _55_EntryNodeOfLoop(ListNode pHead) {
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
    public ListNode _56_deleteDuplication(ListNode pHead) {
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
    public TreeLinkNode _57_GetNext(TreeLinkNode pNode) {
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
    public boolean _58_isSymmetrical(TreeNode pRoot) {
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
    public ArrayList<ArrayList<Integer>> _59_Print(TreeNode pRoot) {
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
    public ArrayList<ArrayList<Integer>> _60_Print(TreeNode pRoot) {
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
     * 用了层序遍历，将二叉树想象为完全二叉树，空节点完全补充为#来序列化
     * 反序列化则先将非空节点new为节点，根据完全二叉树父子节点之间index的关系来链接
     */
    public String _61_Serialize(TreeNode root) {
        if (root == null) return "#";
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        StringBuilder sb = new StringBuilder();
        //全为null的叶子节点的size
        int finalLevelSize = 0;
        while (!queue.isEmpty()) {
            //用Queue层序遍历稍作变形
            int levelSize = queue.size();
            //记录当前level是否含有真实节点，如果没有的话结束层序遍历
            int realValueCount = 0;
            for (int i = 0; i < levelSize; i++) {
                TreeNode curNode = queue.poll();
                if (curNode == null) {
                    sb.append("#").append("!");
                    queue.offer(null);
                    queue.offer(null);
                } else {
                    realValueCount++;
                    sb.append(curNode.val).append("!");
                    queue.offer(curNode.left);
                    queue.offer(curNode.right);
                }
            }
            if (realValueCount == 0) {
                finalLevelSize = levelSize;
                break;
            }
        }
        //返回值中删除最后的全为null的那一层节点
        return sb.delete(sb.length() - 2 * finalLevelSize, sb.length()).toString();
    }

    public TreeNode _61_Deserialize(String str) {
        String[] value = str.split("!");
        TreeNode[] nodeArray = new TreeNode[value.length];
        //把所有节点生成完毕
        for (int i = 0; i < value.length; i++) {
            if (!value[i].equals("#")) {
                nodeArray[i] = new TreeNode(Integer.parseInt(value[i]));
            } else {
                nodeArray[i] = null;
            }
        }
        //根据index之间关系将节点之间关系进行连接
        for (int j = 0; j <= (value.length - 3) / 2; j++) {
            if (nodeArray[j] != null) {
                int leftIndex = 2 * j + 1;
                int rightIndex = 2 * j + 2;
                nodeArray[j].left = nodeArray[leftIndex];
                nodeArray[j].right = nodeArray[rightIndex];
            }
        }
        return nodeArray[0];
    }


    /**
     * --题目描述--
     * 给定一棵二叉搜索树，请找出其中的第k小的结点。例如， （5，3，7，2，4，6，8）
     * 中，按结点数值大小顺序第三小结点的值为4。
     * <p>
     * --解题思路--
     * 对二叉树而言，中序遍历(1.递归/2.Stack)即把元素从小到大排列，取第k小即可
     */
    private int count = 0;

    public TreeNode _62_KthNode1(TreeNode pRoot, int k) {
        if (pRoot != null) {
            TreeNode nodeLeft = _62_KthNode1(pRoot.left, k);
            if (nodeLeft != null) return nodeLeft;
            if (++count == k) return pRoot;
            TreeNode nodeRight = _62_KthNode1(pRoot.right, k);
            if (nodeRight != null) return nodeRight;
        }
        return null;
    }


    public TreeNode _62_KthNode2(TreeNode pRoot, int k) {
        int count = 0;
        Stack<TreeNode> stack = new Stack<>();
        while (pRoot != null || !stack.isEmpty()) {
            while (pRoot != null) {
                stack.push(pRoot);
                pRoot = pRoot.left;
            }
            pRoot = stack.pop();
            if (++count == k) return pRoot;
            pRoot = pRoot.right;
        }
        return null;
    }

    /**
     * --题目描述--
     * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排
     * 序之后位于中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中
     * 间两个数的平均值。我们使用Insert()方法读取数据流，使用GetMedian()方法获取当前读取
     * 数据的中位数。
     * <p>
     * --解题思路--
     * 一个最大堆，一个最小堆，不断调整，偶数为两个堆顶平均数，奇数取最大堆顶
     */
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    });
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private int maxCount = 0;
    private int minCount = 0;

    public void _63_Insert(Integer num) {
        if (maxCount == minCount) {
            if (maxHeap.isEmpty() || (!minHeap.isEmpty() && minHeap.peek() > num)) {
                maxHeap.offer(num);
            } else {
                maxHeap.offer(minHeap.poll());
                minHeap.offer(num);
            }
            maxCount++;
        } else {
            if (!maxHeap.isEmpty() && maxHeap.peek() < num) {
                minHeap.offer(num);
            } else {
                minHeap.offer(maxHeap.poll());
                maxHeap.offer(num);
            }
            minCount++;
        }
    }

    public Double _63_GetMedian() {
        if (((maxCount + minCount) & 1) == 1) {
            return maxHeap.peek().doubleValue();
        } else {
            return (maxHeap.peek().doubleValue() + minHeap.peek().doubleValue()) / 2;
        }
    }

    /**
     * --题目描述--
     * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组
     * {2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为
     * {4,4,6,6,6,5}； 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
     * {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}，
     * {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
     * <p>
     * --解题思路--
     * 双端队列，首尾都能弹出，因为是滑动窗口，每个元素都必须入队一次（考虑原数组递减的情况理解）
     * 元素入队时，要保证队列中别的元素都比他大，由后往前将比他小的元素弹出，因为队列中的元素
     * 呈现降序排列，队首元素最大，由后往前不需要遍历多余元素。
     * 元素入队完成后，考虑队首元素是否超出窗口（因为考虑窗口大小，所以队列中记录元素下标），
     * 如果超出，弹出队首元素，下一个队首元素即当前窗口的最大值（队列中元素呈降序排列）
     */
    public ArrayList<Integer> _64_maxInWindows(int[] num, int size) {
        ArrayList<Integer> result = new ArrayList<>();
        if (num.length == 0 || size <= 0 || num.length < size) {
            return result;
        }
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
            if (qmax.peekFirst() <= i - size) {
                qmax.pollFirst();
            }
            //向result列表中加入元素
            if (i >= size - 1) {
                result.add(num[qmax.peekFirst()]);
            }
        }
        return result;
    }

    /**
     * --题目描述--
     * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从
     * 矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。如果一
     * 条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。 例如 a b c e s f c s a
     * d e e 矩阵中包含一条字符串"bccced"的路径，但是矩阵中不包含"abcb"路径，因为字符串的
     * 第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。
     * <p>
     * --解题思路--
     * 回溯算法，具体见代码
     */
    public boolean _65_hasPath(char[] matrix, int rows, int cols, char[] str) {
        //visited数组代表当前坐标是否被访问过
        boolean[] visited = new boolean[matrix.length];
        //以matrix中每个点为起点，如果有success的，返回true，否则最后返回false
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (subPath(matrix, rows, cols, str, 1, i, j, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean subPath(char[] matrix, int rows, int cols, char[] str,
                            int subPathLen, int row, int col, boolean[] visited) {
        int curIndex = row * cols + col;
        if (row >= 0 && row < rows && col >= 0 && col < cols && !visited[curIndex]) {
            //递归出口的条件
            if (subPathLen == str.length) {
                return matrix[curIndex] == str[str.length - 1];
            } else {
                visited[curIndex] = true;
                //匹配当前位置是否符合，并且递归找上下左右四个位置
                if (matrix[curIndex] == str[subPathLen - 1] &&
                        (subPath(matrix, rows, cols, str, subPathLen + 1,
                                row, col - 1, visited) ||
                                subPath(matrix, rows, cols, str, subPathLen + 1,
                                        row, col + 1, visited) ||
                                subPath(matrix, rows, cols, str, subPathLen + 1,
                                        row - 1, col, visited) ||
                                subPath(matrix, rows, cols, str, subPathLen + 1,
                                        row + 1, col, visited))) {
                    return true;
                }
                //始终只有一个visited的标记数组，当前位置不能走下去，就要把当前位置重置为
                //false代表可以从别的路径走到这个位置
                visited[curIndex] = false;
                return false;
            }
        }
        return false;
    }

    /**
     * --题目描述--
     * 地上有一个m行和n列的方格。一个机器人从坐标0, 0的格子开始移动，每一次只能向左，右，上，
     * 下四个方向移动一格，但是不能进入行坐标和列坐标的数位之和大于k的格子。 例如，当k为18
     * 时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。但是，它不能进入方格（35,38），
     * 因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
     * <p>
     * --解题思路--
     * 回溯算法，具体见代码
     */
    public int _66_movingCount(int threshold, int rows, int cols) {
        //visited数组代表当前坐标是否被访问过
        boolean[] visited = new boolean[rows * cols];
        return countHelper(threshold, rows, cols, 0, 0, visited);
    }

    private int countHelper(int threshold, int rows, int cols, int row,
                            int col, boolean[] visited) {
        if (isValidPos(threshold, rows, cols, row, col, visited)) {
            visited[row * cols + col] = true;
            //递归求和
            return 1 + countHelper(threshold, rows, cols, row + 1, col, visited)
                    + countHelper(threshold, rows, cols, row - 1, col, visited)
                    + countHelper(threshold, rows, cols, row, col + 1, visited)
                    + countHelper(threshold, rows, cols, row, col - 1, visited);
        }
        return 0;
    }

    private boolean isValidPos(int threshold, int rows, int cols, int row,
                               int col, boolean[] visited) {
        int curIndex = row * cols + col;
        //判断点是否超出矩阵范围或者被访问过
        if (row < 0 || row >= rows || col < 0 || col >= cols || visited[curIndex]) {
            return false;
        } else {
            //按题目要求计算每位的和
            int sum = 0;
            while (row > 0) {
                sum += row % 10;
                row /= 10;
            }
            while (col > 0) {
                sum += col % 10;
                col /= 10;
            }
            return sum <= threshold;
        }
    }

    /**
     * --题目描述--
     * 给你一根长度为n的绳子，请把绳子剪成m段（m、n都是整数，n>1并且m>1），每段绳子的长度记
     * 为k[0],k[1],...,k[m]。请问k[0]xk[1]x...xk[m]可能的最大乘积是多少？例如，当绳子的
     * 长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
     * <p>
     * --解题思路--
     * 动态规划思想，因为必须截断成2段以上，长度为2，3的情况单独考虑（自己列了几个例子后总结
     * 出的情况），后面的情况可以统一考虑。
     */
    public int _67_cutRope(int target) {
        int[] res = new int[target + 1];
        if(target == 2) return 1;
        if(target == 3) return 2;
        res[1] = 1;
        res[2] = 2;
        res[3] = 3;
        for (int i = 2; i <= target; i++) {
            for (int j = 1; j <= i/2; j++) {
                res[i] = Math.max(res[i], res[j]*res[i-j]);
            }
        }
        return res[target];
    }
}
