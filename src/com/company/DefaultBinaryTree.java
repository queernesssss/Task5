package com.company;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public interface DefaultBinaryTree<T> extends Iterable<T> {

    interface TreeNode<T> {
        T getValue();
        default TreeNode<T> getLeft() {
            return null;
        }
        default TreeNode<T> getRight() {
            return null;
        }
    }

    TreeNode<T> getRoot();

    @FunctionalInterface
    interface Visitor<T> {

        public void visit(T value, int level);
    }

    default void byLevelVisit(Visitor<T> visitor) {
        class QueueItem {

            public DefaultBinaryTree.TreeNode<T> node;
            public int level;

            public QueueItem(DefaultBinaryTree.TreeNode<T> node, int level) {
                this.node = node;
                this.level = level;
            }
        }

        if (getRoot() == null) {
            return;
        }
        Queue<QueueItem> queue = new LinkedList<>();
        queue.add(new QueueItem(getRoot(), 0));
        while (!queue.isEmpty()) {
            QueueItem item = queue.poll();
            if (item.node.getLeft() != null) {
                queue.add(new QueueItem(item.node.getLeft(), item.level + 1));
            }
            if (item.node.getRight() != null) {
                queue.add(new QueueItem(item.node.getRight(), item.level + 1));
            }
            visitor.visit(item.node.getValue(), item.level);
        }
    }

    default Iterable<T> inOrderValues() {
        return () -> {
            Stack<TreeNode<T>> stack = new Stack<>();
            TreeNode<T> node = getRoot();
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }

            return new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    return !stack.isEmpty();
                }

                @Override
                public T next() {
                    TreeNode<T> node = stack.pop();
                    T result = node.getValue();
                    if (node.getRight() != null) {
                        node = node.getRight();
                        while (node != null) {
                            stack.push(node);
                            node = node.getLeft();
                        }
                    }
                    return result;
                }
            };
        };
    }

    @Override
    default Iterator<T> iterator() {
        return inOrderValues().iterator();
    }
}
