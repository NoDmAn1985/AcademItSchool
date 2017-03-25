package ru.academits.novoselovda.cft;

import java.util.Comparator;

public class IntegerComparatorUp implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return o1.compareTo(o2);
    }
}
