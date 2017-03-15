package ru.academits.novoselovda.main;

import ru.academits.novoselovda.range.Range;

public class Main {
    private static void testOfRanges(Range range1, Range range2, double userNumber) {
        System.out.println("Два диапазона: " + range1.toString() + " и " + range2.toString());

        Range range3 = range1.getCrossRange(range2);
        if (range3 == null) {
            System.out.println("1) Пересечения нет!");
        } else {
            System.out.println("1) Диапазон пересечения:");
            System.out.println("- " + range3.toString() + ", длина диапазона: " + range3.getLength()
                    + ", число " + userNumber + (range3.isInside(userNumber) ? "" : " не") + " принадлежит диапазону");
        }

        System.out.println("2) Диапазон от сложения:");
        Range[] range4 = new Range[range1.addRange(range2).length];
        for (int i = 0; i < range4.length; i++) {
            range4[i] = range1.addRange(range2)[i];
        }
        for (Range range : range4) {
            System.out.println("- " + range.toString() + ", длина диапазона: " + range.getLength()
                    + ", число " + userNumber + (range.isInside(userNumber) ? "" : " не") + " принадлежит диапазону");
        }

        if (range1.deductRange(range2) == null) {
            System.out.println("3) Диапазон полностью вычли!");
        } else {
            System.out.println("3) Диапазон от вычитания:");
            Range[] range5 = new Range[range1.deductRange(range2).length];
            for (int i = 0; i < range5.length; i++) {
                range5[i] = range1.deductRange(range2)[i];
            }
            for (Range range : range5) {
                System.out.println("- " + range.toString() + ", длина диапазона: " + range.getLength()
                        + ", число " + userNumber + (range.isInside(userNumber) ? "" : " не") + " принадлежит диапазону");
            }
        }
        System.out.println("-------------------------------------------------------");
    }

    public static void main(String[] args) {
        Range range1 = new Range(-0.07, 0.03);
        System.out.println("Для диапазона " + range1.toString() + ": ");
        System.out.println("- границы диапазона: " + range1.getFrom() + " и " + range1.getTo());
        System.out.println("- длина диапазона составляет: " + range1.getLength());
        double userNumber = -0.07000001;
        System.out.println("- число " + userNumber + (range1.isInside(userNumber) ? "" : " не") + " принадлежит диапазону");
        System.out.println("-------------------------------------------------------");

        Range range2 = new Range(-5, 0);
        range1.setFrom(-10);
        range1.setTo(-6);
        userNumber = -0.5;
        testOfRanges(range1, range2, userNumber);
        range1.setFrom(-10);
        range1.setTo(-5);
        testOfRanges(range1, range2, userNumber);
        range1.setFrom(-10);
        range1.setTo(-4);
        testOfRanges(range1, range2, userNumber);
        range1.setFrom(-5);
        range1.setTo(-0);
        testOfRanges(range1, range2, userNumber);
        range1.setFrom(-6);
        range1.setTo(1);
        testOfRanges(range1, range2, userNumber);
        range1.setFrom(-3);
        range1.setTo(-1);
        testOfRanges(range1, range2, userNumber);
        range1.setFrom(-1);
        range1.setTo(3);
        testOfRanges(range1, range2, userNumber);
        range1.setFrom(0);
        range1.setTo(3);
        testOfRanges(range1, range2, userNumber);
        range1.setFrom(1);
        range1.setTo(3);
        testOfRanges(range1, range2, userNumber);
    }
}