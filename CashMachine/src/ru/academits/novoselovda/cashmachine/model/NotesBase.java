package ru.academits.novoselovda.cashmachine.model;

import ru.academits.novoselovda.notes.Money;
import ru.academits.novoselovda.notes.Values;

import java.util.ArrayList;
import java.util.Arrays;

public class NotesBase {
    private int maxNotesCount;
    private int length;
    private Money[] machinesDeposit;
    private int sum;


    public NotesBase(Money[] startMoney, int maxNotesCount) {
        this.maxNotesCount = maxNotesCount;
        this.length = Values.values().length;
        this.machinesDeposit = new Money[length];
        int index = 0;
        for (Values value : Values.values()) {
            machinesDeposit[index] = new Money(value, 0);
            ++index;
        }
        initStartDeposit(sort(startMoney));
    }

    private void initStartDeposit(Money[] startMoney) {
        add(startMoney);
    }

    public void add(Money[] cashMoney) throws IllegalArgumentException {
        boolean[] isVerified = new boolean[length];
        int[] tempArrayOfCounts = new int[length];
        int tempSum = 0;
        for (Money money : cashMoney) {
            if (money != null && money.getCount() > 0) {
                for (int i = 0; i < this.length; i++) {
                    if (!isVerified[i] && money.getValue() == this.machinesDeposit[i].getValue()) {
                        if (machinesDeposit[i].getCount() + money.getCount() > maxNotesCount) {
                            throw new IllegalArgumentException("ОШИБКА: превышен лимит по количеству купюр номиналом "
                                    + money.getValue().getCost());
                        }
                        tempArrayOfCounts[i] = money.getCount();
                        tempSum += money.getCount() * money.getValue().getCost();
                        money.subtract(tempArrayOfCounts[i]);
                        isVerified[i] = true;
                    }
                }
            }
        }
        for (int i = 0; i < this.length; ++i) {
            machinesDeposit[i].add(tempArrayOfCounts[i]);
        }
        sum += tempSum;
    }

    public ArrayList<Money> subtract(int requiredSum, int requiredNoteNumber) throws IllegalArgumentException {
        ArrayList<Money> cashOut = new ArrayList<>();
        int[] tempArrayOfCounts = new int[length];
        int leftSum = requiredSum;
        for (int i = requiredNoteNumber; i > 0; i--) {
            if (this.machinesDeposit[i].getCount() == 0) {
                continue;
            }
            int sumOfNotesThatValue = this.machinesDeposit[i].getCount() * this.machinesDeposit[i].getValue().getCost();
            int rest = leftSum % this.machinesDeposit[i].getValue().getCost();
            if (leftSum - sumOfNotesThatValue - rest > 0) {
                leftSum -= sumOfNotesThatValue;
                tempArrayOfCounts[i] = this.machinesDeposit[i].getCount();
            } else {
                int requiredCountOfNotesThatValue = leftSum / this.machinesDeposit[i].getValue().getCost();
                leftSum -= requiredCountOfNotesThatValue * this.machinesDeposit[i].getValue().getCost();
                tempArrayOfCounts[i] = requiredCountOfNotesThatValue;
            }
            if (leftSum == 0) {
                break;
            }
        }
        if (leftSum > 0) {
            throw new IllegalArgumentException("ОШИБКА: недостаточно средств");
        }
        if (leftSum < 0) {
            throw new IllegalArgumentException("ОШИБКА: ОШИБКА В РАСЧЁТАХ");
        }
        for (int i = 0; i < length; ++i) {
            if (tempArrayOfCounts[i] > 0) {
                this.machinesDeposit[i].subtract(tempArrayOfCounts[i]);
                cashOut.add(new Money(machinesDeposit[i].getValue(), tempArrayOfCounts[i]));
            }
        }
        this.sum -= requiredSum;
        return cashOut;
    }

    public int getNoteCount(int index) {
        return this.machinesDeposit[index].getCount();
    }

    public int getNoteValue(int index) {
        return this.machinesDeposit[index].getValue().getCost();
    }

    public int getSize() {
        return this.length;
    }

    public int getSum() {
        return this.sum;
    }

    public void testRequiredSum(int requiredSum) throws IllegalArgumentException {
        if (requiredSum < 0) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемая сумма должна быть положительной");
        }
        Values minNote = this.machinesDeposit[0].getValue();
        for (int i = 0; i < length; i++) {
            if (this.machinesDeposit[0].getCount() > 0) {
                minNote = this.machinesDeposit[0].getValue();
                break;
            }
        }
        if (requiredSum % minNote.getCost() > 0) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемая сумма должна быть кратна "
                    + minNote.getCost());
        }
        if (requiredSum > this.sum) {
            throw new IllegalArgumentException("ОШИБКА: в банкомате недостаточно средств");
        }
    }

    public void testRequiredNote(int requiredSum, int number) throws IllegalArgumentException {
        if (number < 0 || number >= length || this.machinesDeposit[number].getCount() == 0) {
            throw new IllegalArgumentException("ОШИБКА: нет такого номера");
        }
        if (requiredSum < this.machinesDeposit[number].getValue().getCost()) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемая сумма меньше запрашивемой купюры");
        }
        int sumOfRequiredNotes = this.machinesDeposit[number].getValue().getCost() *
                this.machinesDeposit[number].getCount();
        if (requiredSum > sumOfRequiredNotes || (requiredSum < sumOfRequiredNotes &&
                requiredSum % this.machinesDeposit[number].getValue().getCost() != 0)) {
            int cashOpportunity = 0;
            for (int i = 0; i <= number; i++) {
                cashOpportunity += this.machinesDeposit[i].getCount() * this.machinesDeposit[i].getValue().getCost();
            }
            if (requiredSum > cashOpportunity) {
                throw new IllegalArgumentException("ОШИБКА: данных купюр недостаточно");
            }
            if (requiredSum < cashOpportunity) {
                throw new IllegalArgumentException("ОШИБКА: данных купюр недостаточно, но остаток " +
                        "можно выдать купюрами более низкого номинала");
            }
        }
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
