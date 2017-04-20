package ru.academits.novoselovda.notes;

public class Money {
    private int count;
    private Values value;

    public Money(Values value, int count) {
        this.count = count;
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public Values getValue() {
        return value;
    }

    public void add(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("ОШИБКА: передаваемая сумма не может быть < 0");
        }
        this.count += count;
    }

    public void subtract(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("ОШИБКА: вычитаемая сумма не может быть < 0");
        }
        this.count -= count;
    }

    @Override
    public String toString() {
        return "[ " + value.getCost() + " x " + count + " шт. ]";
    }
}
