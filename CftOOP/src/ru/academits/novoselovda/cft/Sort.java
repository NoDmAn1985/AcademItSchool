package ru.academits.novoselovda.cft;

import java.util.ArrayList;
import java.util.Comparator;

class Sort {

    static <T extends Comparable<T>> void byInsertion(ArrayList<T> array, boolean isSortingUp) {
        Comparator<T> comparator;
        comparator = (isSortingUp) ? Comparator.naturalOrder() : Comparator.reverseOrder();
        for (int i = 1; i < array.size(); i++) {
            if (comparator.compare(array.get(i), array.get(i - 1)) < 0) {
                T temp = array.get(i);
                int j;
                for (j = i - 1; j >= 0; j--) {
                    if (temp.compareTo(array.get(j)) < 0) {
                        array.set(j + 1, array.get(j));
                    } else {
                        break;
                    }
                }
                array.set(j + 1, temp);
            }
        }
    }
}
