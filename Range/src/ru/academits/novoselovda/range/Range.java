package ru.academits.novoselovda.range;

public class Range {
    private double from;
    private double to;
    private double part1Start;
    private double part1End;
    private double part2Start;
    private double part2End;
    private static final double EPSILON = 1.0e-10;

    Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    Range(Range range1, Range range2) {
        this.part1Start = range1.getFrom();
        this.part1End = range1.getTo();
        this.part2Start = range2.getFrom();
        this.part2End = range2.getTo();
    }

    static Range getCrossRange(Range range1, Range range2) {
        if (range1.getTo() - range2.getFrom() > -EPSILON && range2.getTo() - range1.getFrom() > -EPSILON) {
            return new Range(max(range1.from, range2.from), min(range1.to, range2.to));
        } else {
            return null;
        }
    }

    static Range getSumOfRanges(Range range1, Range range2) {
        if (range1.to - range2.from > -EPSILON && range2.to - range1.from > -EPSILON) {
            return new Range(min(range1.from, range2.from), max(range1.to, range2.to));
        } else {
            return new Range(range1, range2);
        }
    }

    static Range getDiffOfRanges(Range range1, Range range2) {
        //вторая перекрыла первую
        if (range1.from - range2.from > -EPSILON && range2.to - range1.to > -EPSILON) {
            return null;
        }
        //вторая в первой
        if (range2.from - range1.from > EPSILON && range1.to - range2.to > EPSILON) {
            return new Range(new Range(range1.from, range2.from), new Range(range2.to, range1.to));
        }
        //пересекаются
        if (range1.to - range2.from > -EPSILON && range2.to - range1.to > -EPSILON) {
            return new Range(range1.from, range2.from);
        }
        if (range2.to - range1.from > -EPSILON && range1.to - range2.to > -EPSILON) {
            return new Range(range2.to, range1.to);
        }
        //не совпадают
        return range1;
    }


    String getRangeToString() {
        if (part2Start == 0 && part2End == 0) {
            return "[" + from + ", " + to + "]";
        } else {
            return "[" + part1Start + ", " + part1End + "] и [" + part2Start + ", " + part2End + "]";
        }
    }

    double getLength() {
        if (part2Start == 0 && part2End == 0) {
            return to - from;
        } else {
            return part1End - part1Start + (part2End - part2Start);
        }
    }

    boolean isInside(double userNumber) {
        if (part2Start == 0 && part2End == 0) {
            return (userNumber - from > -EPSILON && to - userNumber > -EPSILON);
        } else {
            return ((userNumber - part1Start > -EPSILON && part1End - userNumber > -EPSILON)
                    || (userNumber - part2Start > -EPSILON && part2End - userNumber > -EPSILON));
        }
    }

    double getFrom() {
        if (part2End == 0 && part2Start == 0) {
            return from;
        } else {
            return part1Start;
        }
    }

    double getTo() {
        if (part2End == 0 && part2Start == 0) {
            return to;
        } else {
            return part2End;
        }
    }

    void setFrom(double from) {
        if (part2End == 0 && part2Start == 0) {
            this.from = from;
        }
    }

    void setTo(double to) {
        if (part2End == 0 && part2Start == 0) {
            this.to = to;
        }
    }

    public double getPart1Start() {
        return part1Start;
    }

    public void setPart1Start(double part1Start) {
        this.part1Start = part1Start;
    }

    public double getPart1End() {
        return part1End;
    }

    public void setPart1End(double part1End) {
        if (from == 0 && to == 0) {
            this.part1End = part1End;
        }
    }

    public double getPart2Start() {
        return part2Start;
    }

    public void setPart2Start(double part2Start) {
        if (from == 0 && to == 0) {
            this.part2Start = part2Start;
        }
    }

    public double getPart2End() {
        return part2End;
    }

    public void setPart2End(double part2End) {
        if (from == 0 && to == 0) {
            this.part2End = part2End;
        }
    }

    private static double max(double first, double second) {
        return (first - second > EPSILON) ? first : second;
    }

    private static double min(double first, double second) {
        return (first - second > EPSILON) ? second : first;
    }

}
