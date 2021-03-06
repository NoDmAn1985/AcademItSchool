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
            return;
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
            return;
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
            return;
        }
        Stack<TreeNode<T>> stack = new Stack<>();
        stack.push(this.root);
        while (!stack.isEmpty()) {
            TreeNode<T> tempNode = stack.pop();
            System.out.println(tempNode);
            if (tempNode.rightChild != null) {
                stack.push(tempNode.rightChild);
            }
            if (tempNode.leftChild != null) {
                stack.push(tempNode.leftChild);
            }
        }
    }

    public boolean remove(T value) {
        if (this.length == 0) {
            return false;
        }

        if (this.length == 1) {
            if (this.comparator.compare(this.root.value, value) == 0) {
                this.root = null;
                --length;
                return true;
            }
            return false;
        }

        TreeNode<T> q = null;
        TreeNode<T> p = this.root;
        while (p != null) {
            int compare = this.comparator.compare(p.value, value);
            if (compare == 0) {
                removeChild(q, p);
                --this.length;
                return true;
            } else {
                q = p;
                if (compare > 0) {
                    p = p.leftChild;
                } else {
                    p = p.rightChild;
                }
            }
        }
        return false;
    }

    private void removeChild(TreeNode<T> fatherNode, TreeNode<T> removingNode) {
        //если у отца только один ребёнок
        if (fatherNode != null && (fatherNode.rightChild == null || fatherNode.leftChild == null)) {
            fatherNode.leftChild = removingNode.leftChild;
            fatherNode.rightChild = removingNode.rightChild;
            return;
        }

        //удаление листа
        if (fatherNode != null && removingNode.leftChild == null && removingNode.rightChild == null) {
            if (removingNode == fatherNode.leftChild) {
                fatherNode.leftChild = null;
            } else {
                fatherNode.rightChild = null;
            }
            return;
        }

        //удаление элемента с одним ребёнком
        if (removingNode.leftChild == null) {
            if (fatherNode == null) {
                this.root = removingNode.rightChild;
            } else if (removingNode == fatherNode.leftChild) {
                fatherNode.leftChild = removingNode.rightChild;
            } else {
                fatherNode.rightChild = removingNode.rightChild;
            }
            return;
        }
        if (removingNode.rightChild == null) {
            if (fatherNode == null) {
                this.root = removingNode.leftChild;
            } else if (removingNode == fatherNode.leftChild) {
                fatherNode.leftChild = removingNode.leftChild;
            } else {
                fatherNode.rightChild = removingNode.leftChild;
            }
            return;
        }

        //удаление элемента с двумя детьми
        TreeNode<T> tempRightChild = removingNode.rightChild;
        if (fatherNode == null) {
            this.root = removingNode.leftChild;
        } else {
            if (removingNode == fatherNode.leftChild) {
                fatherNode.leftChild = removingNode.leftChild;
            } else {
                fatherNode.rightChild = removingNode.leftChild;
            }
        }
        if (removingNode.leftChild.rightChild != null) {
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
    }
}
