package com.company;

public class Main {

    public static void main(String[] args) throws Exception {
        BinaryTree<Integer> tree1 = new BinaryTree<>(s -> Integer.parseInt(s));
        BinaryTree<Integer> tree2 = new BinaryTree<>(s -> Integer.parseInt(s));
        tree1.fromBracketNotation("80(10(1,11),90)");
        tree2.fromBracketNotation("50(40(35,45),60(55,65))");
        int maxLevel1 = tree1.maxLevel();
        int maxLevel2 = tree2.maxLevel();
        System.out.println(maxLevel1);
        System.out.println(maxLevel2);
    }
}
