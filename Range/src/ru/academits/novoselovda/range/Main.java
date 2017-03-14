package ru.academits.novoselovda.range;

public class Main {
    private static void testOfRanges(Range range1, Range range2, double userNumber) {
        System.out.println("Два диапозонов: " + new Range(range1, range2).getRangeToString());

        Range range3 = Range.getCrossRange(range1, range2);
        if (range3 == null) {
            System.out.println("1) Пересечения нет!");
        } else {
            System.out.println("1) Диапазон пересечения: " + range3.getRangeToString());
            System.out.println("- длина нового диапозона: " + range3.getLength());
            System.out.println("- число " + userNumber + (range3.isInside(userNumber) ? "" : " не") + " принадлежит диапозону");
        }

        Range range4 = Range.getSumOfRanges(range1, range2);
        System.out.println("2) Суммарный диапозон: " + range4.getRangeToString());
        System.out.println("- длина нового диапозона: " + range4.getLength());
        System.out.println("- число " + userNumber + (range4.isInside(userNumber) ? "" : " не") + " принадлежит диапозону");

        Range range5 = Range.getDiffOfRanges(range1, range2);
        if (range5 == null) {
            System.out.println("3) Диапозоны полностью вычлись!");
        } else {
            System.out.println("3) Разность диапозонов: " + range5.getRangeToString());
            System.out.println("- длина нового диапозона: " + range5.getLength());
            System.out.println("- число " + userNumber + (range5.isInside(userNumber) ? "" : " не") + " принадлежит диапозону");
        }
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        Range range1 = new Range(-0.07, 0.03);
        System.out.println("Для диапозона " + range1.getRangeToString() + ": ");
        System.out.println("- границы диапозона: " + range1.getFrom() + " и " + range1.getTo());
        System.out.println("- длина диапозона составляет: " + range1.getLength());
        double userNumber = -0.07000001;
        System.out.println("- число " + userNumber + (range1.isInside(userNumber) ? "" : " не") + " принадлежит диапозону");
        System.out.println("--------------------------------------------------");
        Range range2 = new Range(-5, 0);
        range1.setFrom(-10);
        range1.setTo(-6);
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