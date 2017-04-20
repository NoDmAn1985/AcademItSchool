package ru.academits.novoselovda.cashmachine;

import ru.academits.novoselovda.notes.Money;
import ru.academits.novoselovda.notes.Values;

import java.util.ArrayList;
import java.util.Arrays;

class Storage {
    private Money[] machinesDeposit;
    private final int maxValueCount;

    Storage(int maxValueCount) {
        this.machinesDeposit = new Money[Values.values().length];
        this.maxValueCount = maxValueCount;
        int index = 0;
        for (Values value : Values.values()) {
            machinesDeposit[index] = new Money(value, 0);
            ++index;
        }
    }

    void initStartDeposit(Money[] startMoney) {
        add(startMoney);
    }

    void add(Money[] cashMoney) {
        boolean[] isVerified = new boolean[cashMoney.length];
        Arrays.fill(isVerified, false);
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

    ArrayList<Money> subtract(int[] requiredSum) {
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

    int getCount(int index) {
        return machinesDeposit[index].getCount();
    }
}
