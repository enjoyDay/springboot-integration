package com.springbootIntegration.demo.test.algorithm.tree;

import io.swagger.models.auth.In;

import java.util.*;

/**
 * @author liukun
 * @description 自定义二叉树
 * @since 2020/12/13
 */
public class MyTreeNode {
    private static class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;

        TreeNode(int data) {
            this.data = data;
        }
    }

    // 创建二叉树,使用初始化的一种方法
    public static TreeNode createTree(LinkedList<Integer> inputList) {
        TreeNode treeNode = null;
        if (inputList == null || inputList.isEmpty()) {
            return null;
        }

        Integer data = inputList.removeFirst();
        if (data != null) {
            treeNode = new TreeNode(data);
            treeNode.left = createTree(inputList);
            treeNode.right = createTree(inputList);
        }

        return treeNode;
    }

    // 前序遍历(使用递归的方式)
    public static void preOrderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }

        System.out.print(node.data);
        System.out.print(" ");
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }

    // 前序遍历，（使用栈的方式）
    public static void preOrderTraversalWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode treeNode = root;

        while (treeNode != null || !stack.isEmpty()) {
            // 迭代访问节点的左孩子，并入栈
            while (treeNode != null) {
                System.out.print(treeNode.data);
                System.out.print(" ");
                stack.push(treeNode);
                treeNode = treeNode.left;
            }

            if (!stack.isEmpty()) {
                TreeNode pop = stack.pop();
                treeNode = pop.right;
            }
        }
    }

    // 中序遍历（使用递归的方式）
    public static void inOrderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }

        inOrderTraversal(node.left);
        System.out.print(node.data);
        System.out.print(" ");
        inOrderTraversal(node.right);
    }

    // 中序遍历，（使用栈的方式）左根右
    public static void inOrderTraversalWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode treeNode = root;

        while (treeNode != null || !stack.isEmpty()) {
            // 迭代访问节点的左孩子，并入栈
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.left;
            }

            if (!stack.isEmpty()) {
                TreeNode pop = stack.pop();
                System.out.print(pop.data);
                System.out.print(" ");
                treeNode = pop.right;
            }
        }
    }

    // 后序遍历（使用递归的方式）
    public static void postOrderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }

        postOrderTraversal(node.left);
        postOrderTraversal(node.right);
        System.out.print(node.data);
        System.out.print(" ");
    }

    // 后序遍历，（使用栈的方式）左右根
    // 实现方法：
    // 1.根节点入栈
    //2.将根节点的左子树入栈，直到最左，没有左孩子为止
    //3.得到栈顶元素的值，先不访问，判断栈顶元素是否存在右孩子，如果存在并且没有被访问，则将右孩子入栈，否则，就访问栈顶元素
    //	其关键就在于需要一个前驱指针，用于判断是否该节点的右节点被访问过。
    public static void postOrderTraversalWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode treeNode = root;
        // 用于标记之前访问的节点
        TreeNode pre = null;
        // 使用一个标记表示前一个访问的节点
        while (treeNode != null || !stack.isEmpty()) {
            // 迭代访问节点的左孩子，并入栈
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.left;
            }

            if (!stack.isEmpty()) {
                //2.获取栈顶元素值
                treeNode = stack.peek();
                //3.没有右孩子，或者右孩子已经被访问过
                if (treeNode.right == null || treeNode.right == pre) {
                    //则可以访问栈顶元素
                    treeNode = stack.pop();
                    System.out.print(treeNode.data);
                    System.out.print(" ");
                    //标记上一次访问的节点
                    pre = treeNode;
                    treeNode = null;
                } else {
                    // 如果有右孩子，则访问右孩子
                    treeNode = treeNode.right;
                }
            }
        }
    }

    // 层序遍历
    public static void levelTraversal(TreeNode node) {
        if (node == null) {
            return;
        }

        // 使用队列保存先遍历的数值
        LinkedList<TreeNode> queue = new LinkedList<>();

        // 添加元素到尾部
        queue.offer(node);
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            System.out.print(poll.data);
            System.out.print(" ");
            //如果当前节点的左节点不为空，则左节点入队列
            if (poll.left != null) {
                queue.offer(poll.left);
            }
            //如果当前节点的右节点不为空，则右节点入队列
            if (poll.right != null) {
                queue.offer(poll.right);
            }
        }
    }

    // 已知前序和中序，输出后序
    public static void printTail() {
        LinkedList<Integer> preOrder = new LinkedList<>(Arrays.asList(1, 2, 4, 7, 3, 5, 6, 8));
        LinkedList<Integer> inOrder = new LinkedList<>(Arrays.asList(4, 7, 2, 1, 5, 3, 8, 6));

        TreeNode treeNode = constructTree(preOrder, inOrder);

        // 后序遍历
        postOrderTraversal(treeNode);
    }

    private static TreeNode constructTree(LinkedList<Integer> preOrder, LinkedList<Integer> inOrder) {
        if (preOrder == null || inOrder == null || preOrder.size() <= 0 || inOrder.size() <= 0) {
            return null;
        }

        return constrcutCore(preOrder, inOrder);
    }

    private static TreeNode constrcutCore(LinkedList<Integer> preOrder, LinkedList<Integer> inOrder) {
        // 前序遍历第一个点是根节点
        Integer value = preOrder.get(0);
        // 创建一个树的根节点
        TreeNode root = new TreeNode(value);
        root.data = value;
        root.left = root.right = null;

        if (preOrder.size() <= 0) {
            if (inOrder.size() <= 0 && preOrder.getFirst().equals(inOrder.getFirst())) {
                return root;
            }
        }

        // 从中序中找到根节点
        int index = 0;
        while (!inOrder.get(index).equals(value) && index <= inOrder.size()) {
            index++;
        }

        // 将前序拆分为两半
        LinkedList<Integer> preOrderLeft = LinkedListToList(preOrder.subList(1, index + 1));
        LinkedList<Integer> preOrderRight = LinkedListToList(preOrder.subList(index + 1, preOrder.size()));

        // 将后序拆分为两半
        LinkedList<Integer> inOrderLeft = LinkedListToList(inOrder.subList(0, index));
        LinkedList<Integer> inOderRight = LinkedListToList(inOrder.subList(index + 1, inOrder.size()));

        if (index > 0) {
            root.left = constrcutCore(preOrderLeft, inOrderLeft);
        }

        if (preOrder.size() - index - 1 > 0) {
            root.right = constrcutCore(preOrderRight, inOderRight);
        }

        return root;
    }

    private static LinkedList LinkedListToList(List<Integer> list) {
        LinkedList<Integer> linkedList = new LinkedList();
        for (int i = 0; i < list.size(); i++) {
            linkedList.addLast(list.get(i));
        }

        return linkedList;
    }

    // 找中序节点指定节点的下一个节点
    // 后序遍历（使用递归的方式）
    public static void postOrderNext(TreeNode node,int data) {
        ArrayList<TreeNode> arrayList = new ArrayList();

        postOrderNextOne(node, arrayList);

        int index=0;
        while (index<=arrayList.size()-1) {
            if (arrayList.get(index).data==data) {
                break;
            }
            index++;
        }
        if (index<arrayList.size()-1) {
            System.out.println(arrayList.get(index+1).data);
        }
        System.out.println(arrayList.get(0).data);
    }

    private static void postOrderNextOne(TreeNode node, List list) {
        if (node == null) {
            return;
        }

        postOrderNextOne(node.left, list);
        postOrderNextOne(node.right, list);
//        System.out.print(node.data);
//        System.out.print(" ");
        list.add(node);
    }

    public static void main(String[] args) {
        LinkedList<Integer> inputList = new LinkedList<>(Arrays.asList(new Integer[]{3, 2, 9, null, null, 10, null, null, 8, null, 4}));
        TreeNode tree = createTree(inputList);
//
//        System.out.println("前序：");
//        preOrderTraversal(tree);
//        System.out.println("前序带stack：");
//        preOrderTraversalWithStack(tree);
//        System.out.println("中序：");
//        inOrderTraversal(tree);
//        System.out.println("中序带stack：");
//        inOrderTraversalWithStack(tree);
        System.out.println("后序：");
        postOrderNext(tree,3);
//        System.out.println("后序带stack：");
//        postOrderTraversalWithStack(tree);
//        System.out.println("层序遍历：");
//        levelTraversal(tree);

//        MyTreeNode.printTail();
    }
}
