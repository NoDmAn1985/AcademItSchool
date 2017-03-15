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
            return new Range(max(this.from, range.getFrom()), min(this.to, range.getTo()));
        } else {
            return null;
        }
    }

    public Range[] addRange(Range range) {
        if (isRangeCrossThis(range)) {
            Range[] newRange = new Range[1];
            newRange[0] = new Range(min(this.from, range.getFrom()), max(this.to, range.getTo()));
            return newRange;
        } else {
            Range[] rangeParts = new Range[2];
            rangeParts[0] = new Range(this.from, this.to);
            rangeParts[1] = new Range(range.getFrom(), range.getTo());
            return rangeParts;
        }
    }

    public Range[] deductRange(Range range) {
        //вторая перекрыла первую
        if (range.getFrom() <= this.from && range.getTo() >= this.to) {
            return null;
        }
        //вторая в первой
        if (this.from < range.getFrom() && this.to > range.getTo()) {
            Range[] rangeParts = new Range[2];
            rangeParts[0] = new Range(this.from, range.getFrom());
            rangeParts[1] = new Range(range.getTo(), this.to);
            return rangeParts;
        }
        //пересекаются
        Range[] newRange = new Range[1];
        if (isRangeCrossThis(range)) {
            if (this.from < range.getFrom()) {
                newRange[0] = new Range(this.from, range.getFrom());
            } else {
                newRange[0] = new Range(range.getTo(), this.to);
            }
            //не совпадают
        } else {
            newRange[0] = new Range(this.from, this.to);
        }
        return newRange;
    }

    @Override
    public String toString() {
        return "(" + from + ", " + to + ")";
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double userNumber) {
        return (userNumber > from && to > userNumber);
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

    private double max(double first, double second) {
        return (first > second) ? first : second;
    }

    private double min(double first, double second) {
        return (first < second) ? first : second;
    }

    private boolean isRangeCrossThis(Range range) {
        return (this.from < range.getTo() && this.to > range.getFrom());
    }

}
