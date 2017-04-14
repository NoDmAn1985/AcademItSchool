package ru.academits.novoselovda.notes;

public class Notes {
    public enum Values {
        TEN(10),
        FIFTY(50),
        ONE_HUNDRED(100),
        FIVE_HUNDREDS(500),
        ONE_THOUSAND(1000),
        FIVE_THOUSANDS(5000);
        private int value;
        Values(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    public static final int VALUE_COUNT = 6;
    private int value;
    private Values name;
    private int count;

    public Notes(Values name, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("ОШИБКА: количество создаваемых денег не может быть отрицательным");
        }
        this.name = name;
        this.value = name.value;
        this.count = count;
    }

    public int getValueInt() {
        return value;
    }

    public Values getValueName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void addCount(int count) {
        this.count += count;
    }

    public void deductCount(int count) {
        this.count = this.count - count;
    }

}
