package sj.leetcode;

import sj.util.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: Jian Shi
 * @email: shijianhzchina@gmail.com
 * @date: 12/26/2018 16:01
 */

public class TreeOperations {
    public static void main(String[] args) {
        //tree 1
        TreeNode root1 = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);
        root1.left = node1;
        root1.right = node2;
        node2.left = node3;
        node2.right = node4;
        int[] pre = {1,2,4,7,3,5,6,8};
        int[] in = {4,7,2,1,5,3,8,6};

        //System.out.println(maxDepth(root1));
        //System.out.println(inorderTraversal(root1));
        //System.out.println(levelOrder(root1));
//        System.out.println(findComParent(root1, node3, node4).val);
        System.out.println(postOrderTraversal(reConstructBinaryTree(pre, in)));
    }

    public static void printTree(TreeNode root) {

    }

    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inhelper(root, res);
        return res;
    }
    public static void inhelper(TreeNode root, List<Integer> res) {
        if (root != null) {
            inhelper(root.left, res);
            res.add(root.val);
            inhelper(root.right, res);
        }
    }

    public static List<Integer> postOrderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        poshelper(root, res);
        return res;
    }
    public static void poshelper(TreeNode root, List<Integer> res) {
        if (root != null) {
            poshelper(root.left, res);
            poshelper(root.right, res);
            res.add(root.val);
        }
    }

    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            res.add(curr.val);
            curr = curr.right;
        }
        return res;
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int layerSize = queue.size();
            List<Integer> layer = new ArrayList<>();
            for (int i = 0; i < layerSize; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                layer.add(node.val);
            }
            res.add(layer);
        }
        return res;
    }

    public static TreeNode findComParent(TreeNode root, TreeNode node1,
                                                           TreeNode node2) {
        if (root == null) {
            return null;
        }
        if (root == node1 || root == node2) {
            return root;
        }
        TreeNode left = findComParent(root.left, node1, node2);
        TreeNode right = findComParent(root.right, node1, node2);
        if (left != null && right != null) {
            return root;
        } else if (left != null) {
            return left;
        } else if (right != null) {
            return right;
        } else {
            return null;
        }
    }

    public static TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre.length != 0 && in.length != 0) {
            TreeNode root = new TreeNode(pre[0]);
            findSubTree(Arrays.stream(in).boxed().collect(Collectors.toList()),
                    Arrays.stream(pre).boxed().collect(Collectors.toList()), root);
            return root;
        } else {
            return null;
        }
    }

    public static void findSubTree(List<Integer> in, List<Integer> pre,
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
        List<Integer> leftpre = pre.subList(1,1+leftSize);
        List<Integer> rightpre = pre.subList(1+leftSize,1+leftSize+rightSize);
        if(leftSize > 0) {
            TreeNode left_root = new TreeNode(leftpre.get(0));
            root.left = left_root;
            findSubTree(leftin, leftpre, left_root);
        }
        if(rightSize > 0) {
            TreeNode right_root = new TreeNode(rightpre.get(0));
            root.right = right_root;
            findSubTree(rightin, rightpre, right_root);
        }
    }
}
