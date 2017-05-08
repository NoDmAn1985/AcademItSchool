package ru.academits.novoselovda.tree;

import java.util.*;

/*
Про деревья - надо сделать класс бинарного дерева поиска.
Надо реализовать методы:
- вставка значения
- проверка, что есть узел с таким значением
- обход в ширину с печатью
- обход в глубину с печатью. Дополнительно - сделать без рекурсии при помощи стека
- удаление значения
 */
public class Tree<T extends Comparable<T>> {
    private int length;
    private TreeNode<T> root;
    private Comparator<T> comparator;

    private static class TreeNode<T> {
        private TreeNode<T> leftChild;
        private TreeNode<T> rightChild;
        private T value;

        TreeNode(T value) {
            this.value = value;
            this.leftChild = null;
            this.rightChild = null;
        }

        @Override
        public String toString() {
            return "{" + value + (leftChild == null ? "" : ", left = " + leftChild.value) +
                    (rightChild == null ? "" : ", right = " + rightChild.value) + "}";
        }
    }

    public Tree() {
        this.length = 0;
        this.comparator = new MyComparator<>();
    }

    public boolean add(T value) {
        if (this.length == 0) {
            this.root = new TreeNode<>(value);
            ++this.length;
            return true;
        }
        TreeNode<T> p = this.root;
        while (p != null) {
            int compare = this.comparator.compare(p.value, value);
            if (compare > 0) {
                if (p.leftChild == null) {
                    p.leftChild = new TreeNode<>(value);
                    ++this.length;
                    return true;
                } else {
                    p = p.leftChild;
                }
            } else {
                if (p.rightChild == null) {
                    p.rightChild = new TreeNode<>(value);
                    ++length;
                    return true;
                } else {
                    p = p.rightChild;
                }
            }
        }
        return false;
    }

    public boolean contains(T value) {
        if (this.length == 0) {
            return false;
        }
        TreeNode<T> p = this.root;
        while (p != null) {
            int compare = this.comparator.compare(p.value, value);
            if (compare == 0) {
                return true;
            } else {
                if (compare > 0) {
                    p = p.leftChild;
                } else {
                    p = p.rightChild;
                }
            }
        }
        return false;
    }

    public void breadthFirstSearch() {
        if (this.length == 0) {
            throw new NoSuchElementException();
        }
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            TreeNode<T> node = queue.remove();
            System.out.println(node);
            if (node.leftChild != null) {
                queue.add(node.leftChild);
            }
            if (node.rightChild != null) {
                queue.add(node.rightChild);
            }
        }
    }

    public void recursiveDepthFirstSearch() {
        if (this.length == 0) {
            throw new NoSuchElementException();
        }
        visitNode(this.root);
    }

    private void visitNode(TreeNode node) {
        System.out.println(node);
        if (node.leftChild != null) {
            visitNode(node.leftChild);
        }
        if (node.rightChild != null) {
            visitNode(node.rightChild);
        }
    }

    public void stackDepthFirstSearch() {
        if (this.length == 0) {
            throw new NoSuchElementException();
        }
        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> node = this.root;
        while (node != null || !stack.isEmpty()) {
            if (!stack.isEmpty()) {
                node = stack.pop();
            }
            while (node != null) {
                System.out.println(node);
                if (node.rightChild != null) {
                    stack.push(node.rightChild);
                }
                node = node.leftChild;
            }
        }
    }

    public T remove(T value) {
        if (this.length == 0) {
            return null;
        }
        if (this.length == 1 &&
                (this.root.value == null || this.comparator.compare(this.root.value, value) == 0)) {
            this.root = null;
            --length;
            return null;
        }
        TreeNode<T> q = null;
        TreeNode<T> p = this.root;
        while (p != null) {
            int compare = this.comparator.compare(p.value, value);
            if (compare == 0) {
                T tempData = p.value;
                removeChild(q, p);
                --this.length;
                return tempData;
            } else {
                q = p;
                if (compare > 0) {
                    p = p.leftChild;
                } else {
                    p = p.rightChild;
                }
            }
        }
        return null;
    }

    private void removeChild(TreeNode<T> fatherNode, TreeNode<T> removingNode) {
        if (fatherNode != null && (fatherNode.rightChild == null || fatherNode.leftChild == null)) {
            fatherNode.leftChild = removingNode.leftChild;
            fatherNode.rightChild = removingNode.rightChild;
        } else if (removingNode.leftChild != null) {
            TreeNode<T> tempRightChild = removingNode.rightChild;
            if (fatherNode == null) {
                this.root = removingNode.leftChild;
            } else {
                if (fatherNode.leftChild == removingNode) {
                    fatherNode.leftChild = removingNode.leftChild;
                } else {
                    fatherNode.rightChild = removingNode.leftChild;
                }
            }
            if (removingNode.leftChild.rightChild != null && tempRightChild != null) {
                TreeNode<T> node = removingNode.leftChild.rightChild;
                while (true) {
                    if (node.rightChild == null) {
                        node.rightChild = tempRightChild;
                        break;
                    }
                    node = node.rightChild;
                }
            } else {
                removingNode.leftChild.rightChild = tempRightChild;
            }
        } else if (removingNode.rightChild != null) {
            if (fatherNode == null) {
                this.root = removingNode.rightChild;
            } else {
                fatherNode.rightChild = removingNode.rightChild;
            }
        } else {
            if (fatherNode == null) {
                this.root = null;
            } else {
                if (fatherNode.leftChild == removingNode) {
                    fatherNode.leftChild = null;
                } else {
                    fatherNode.rightChild = null;
                }
            }
        }
    }
}
