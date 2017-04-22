package ru.academits.novoselovda.cashmachine.model;

import ru.academits.novoselovda.notes.Money;
import ru.academits.novoselovda.notes.Values;

import java.util.ArrayList;
import java.util.Arrays;

public class Storage {
    private Money[] machinesDeposit;
    private final int maxValueCount;

    public Storage(int maxValueCount) {
        this.machinesDeposit = new Money[Values.values().length];
        this.maxValueCount = maxValueCount;
        int index = 0;
        for (Values value : Values.values()) {
            machinesDeposit[index] = new Money(value, 0);
            ++index;
        }
    }

    public void initStartDeposit(Money[] startMoney) {
        add(startMoney);
    }

    public void add(Money[] cashMoney) throws IllegalArgumentException {
        boolean[] isVerified = new boolean[cashMoney.length];
        for (Money deposit : machinesDeposit) {
            for (int i = 0; i < cashMoney.length; ++i) {
                if (!isVerified[i] && cashMoney[i] != null && deposit.getValue() == cashMoney[i].getValue()) {
                    if (deposit.getCount() + cashMoney[i].getCount() > maxValueCount) {
                        throw new IllegalArgumentException("ОШИБКА: превышен лимит по количеству купюр номиналом "
                                + deposit.getValue());
                    } else {
                        deposit.add(cashMoney[i].getCount());
                        cashMoney[i].subtract(cashMoney[i].getCount());
                        isVerified[i] = true;
                        break;
                    }
                }
            }
        }
    }

    public ArrayList<Money> subtract(int[] requiredSum) throws IllegalArgumentException {
        ArrayList<Money> cashOut = new ArrayList<>();
        for (int i = 0; i < machinesDeposit.length; ++i) {
            if (requiredSum[i] > 0) {
                if (machinesDeposit[i].getCount() - requiredSum[i] < 0) {
                    throw new IllegalArgumentException("ОШИБКА: недостаточно купюр номиналом "
                            + machinesDeposit[i].getValue());
                } else {
                    machinesDeposit[i].subtract(requiredSum[i]);
                    cashOut.add(new Money(machinesDeposit[i].getValue(), requiredSum[i]));
                }
            }
        }
        return cashOut;
    }

    public int getCount(int index) {
        return machinesDeposit[index].getCount();
    }

    public Money[] sort(Money[] money) {
        int nullCount = 0;
        if (money[0] == null || money[0].getCount() == 0) {
            ++nullCount;
        }
        int i = 1;
        while (money[i] == null) {
            if (money[i] == null || money[i].getCount() == 0) {
                ++nullCount;
            }
            ++i;
        }
        for (; i < money.length; ++i) {
            if (money[i] == null || money[i].getCount() == 0) {
                ++nullCount;
                continue;
            }
            if (money[i - 1] == null || money[i - 1].getCount() == 0 ||
                    money[i].getValue().getCost() < money[i - 1].getValue().getCost()) {
                Money temp = money[i];
                int j = i - 1;
                while (j >= 0) {
                    if (money[j] == null || money[j].getCount() == 0 ||
                            temp.getValue().getCost() < money[j].getValue().getCost()) {
                        money[j + 1] = money[j];
                    } else {
                        break;
                    }
                    --j;
                }
                money[j + 1] = temp;
            }
        }
        if (nullCount > 0) {
            money = Arrays.copyOf(money, money.length - nullCount);
        }
        return money;
    }
}
