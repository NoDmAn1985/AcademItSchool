package ru.academits.novoselovda.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public Range getCrossRange(Range range) {
        if (isRangeCrossThis(range)) {
            return new Range(Math.max(this.from, range.from), Math.min(this.to, range.to));
        } else {
            return null;
        }
    }

    public Range[] unionWith(Range range) {
        if (isRangeCrossThis(range)) {
            return new Range[]{new Range(Math.min(this.from, range.from), Math.max(this.to, range.to))};
        } else {
            return new Range[]{new Range(this.from, this.to), new Range(range.from, range.to)};
        }
    }

    public Range[] deductRange(Range range) {
        //вторая перекрыла первую
        if (range.from <= this.from && range.to >= this.to) {
            return new Range[]{};
        }
        //вторая в первой
        if (this.from < range.from && this.to > range.to) {
            return new Range[]{new Range(this.from, range.from), new Range(range.to, this.to)};
        }
        //пересекаются
        if (isRangeCrossThis(range)) {
            if (this.from < range.from) {
                return new Range[]{new Range(this.from, range.from)};
            } else {
                return new Range[]{new Range(range.to, this.to)};
            }
            //не совпадают
        } else {
            return new Range[]{new Range(this.from, this.to)};
        }
    }

    @Override
    public String toString() {
        return "(" + from + ", " + to + ")";
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double userNumber) {
        return (userNumber >= from && to >= userNumber);
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    private boolean isRangeCrossThis(Range range) {
        return (this.from < range.to && this.to > range.from);
    }

}
