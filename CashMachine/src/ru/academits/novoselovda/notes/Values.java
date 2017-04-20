package ru.academits.novoselovda.notes;

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

    public int getCost() {
        return value;
    }
}