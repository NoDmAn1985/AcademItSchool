package ru.academits.novoselovda.factory.common;

public enum Workers {
    PROVIDER_A("поставщик кузовов", Items.PART_A, true),
    PROVIDER_B("поставщик двигателей", Items.PART_B, true),
    PROVIDER_C("поставщик аксессуаров", Items.PART_C, true),
    PRODUCER("сбощик", Items.CAR, false),
    DEALER("продавец", null, false),
    CONTROLLER("котролёр", null, false),
    NULL("никто", null, false);
    private String name;
    private Items item;
    private boolean isProvider;

    Workers(String name, Items item, boolean isProvider) {
        this.name = name;
        this.item = item;
        this.isProvider = isProvider;
    }

    public Items getItem() {
        return item;
    }

    public boolean isProvider() {
        return isProvider;
    }

    @Override
    public String toString() {
        return name;
    }
}
