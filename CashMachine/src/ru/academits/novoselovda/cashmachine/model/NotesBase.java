package ru.academits.novoselovda.cashmachine.model;

import ru.academits.novoselovda.notes.Money;
import ru.academits.novoselovda.notes.Values;

public class NotesBase {
    private int maxNotesCount;
    private int length;
    private int[] arrayOfCounts;
    private Values[] arrayOfValues;
    private int sum;

    public NotesBase(Money[] startMoney, int maxNotesCount) {
        this.maxNotesCount = maxNotesCount;
        this.length = Values.values().length;
        this.arrayOfCounts = new int[length];
        this.arrayOfValues = new Values[length];
        int index = 0;
        for (Values value : Values.values()) {
            arrayOfValues[index] = value;
            ++index;
        }
        add(startMoney);
    }

    public void add(Money[] cashMoney) throws IllegalArgumentException {
        boolean[] isVerified = new boolean[length];
        int[] tempArrayOfCounts = new int[length];
        int tempSum = 0;
        for (Money money : cashMoney) {
            if (money != null && money.getCount() > 0) {
                for (int i = 0; i < this.length; i++) {
                    if (!isVerified[i] && money.getValue() == this.arrayOfValues[i]) {
                        if (arrayOfCounts[i] + money.getCount() > maxNotesCount) {
                            throw new IllegalArgumentException("ОШИБКА: превышен лимит по количеству купюр номиналом "
                                    + money.getValue().getCost());
                        }
                        tempArrayOfCounts[i] += money.getCount();
                        tempSum += money.getCount() * money.getValue().getCost();
                        isVerified[i] = true;
                    }
                }
            }
        }
        for (int i = 0; i < length; ++i) {
            arrayOfCounts[i] += tempArrayOfCounts[i];
        }
        sum += tempSum;
    }

    public int[] subtract(int requiredSum, int requiredNoteNumber) throws IllegalArgumentException {
        int[] tempArrayOfCounts = new int[length];
        int leftSum = requiredSum;
        for (int i = requiredNoteNumber; i > 0; i--) {
            if (this.arrayOfCounts[i] == 0) {
                continue;
            }
            int sumOfNotesThatValue = this.arrayOfCounts[i] * this.arrayOfValues[i].getCost();
            int rest = leftSum % this.arrayOfValues[i].getCost();
            if (leftSum - sumOfNotesThatValue - rest > 0) {
                leftSum -= sumOfNotesThatValue;
                tempArrayOfCounts[i] = this.arrayOfCounts[i];
            } else {
                int requiredCountOfNotesThatValue = leftSum / this.arrayOfValues[i].getCost();
                leftSum -= requiredCountOfNotesThatValue * this.arrayOfValues[i].getCost();
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
            arrayOfCounts[i] -= tempArrayOfCounts[i];
        }
        sum -= requiredSum;
        return tempArrayOfCounts;
    }

    public int getNoteCount(int index) {
        return this.arrayOfCounts[index];
    }

    public int getNoteValue(int index) {
        return this.arrayOfValues[index].getCost();
    }

    public int getSize() {
        return this.arrayOfCounts.length;
    }

    public int getSum() {
        return this.sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("№\tНоминал\n");
        for (int i = 0; i < length; i++) {
            if (this.arrayOfCounts[i] != 0) {
                sb.append(i).append(")\t").append(this.arrayOfValues[i].getCost()).append("\n");
            }
        }
        return sb.toString();
    }

    public void testRequiredSum(int requiredSum) throws IllegalArgumentException {
        if (requiredSum < 0) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемая сумма должна быть положительной");
        }
        Values minNote = arrayOfValues[0];
        for (int i = 0; i < length; i++) {
            if (this.arrayOfCounts[i] > 0) {
                minNote = arrayOfValues[i];
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
        if (number < 0 || number >= length || this.arrayOfCounts[number] == 0) {
            throw new IllegalArgumentException("ОШИБКА: нет такого номера");
        }
        if (requiredSum < this.arrayOfValues[number].getCost()) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемая сумма меньше запрашивемой купюры");
        }
        int sumOfRequiredNotes = this.arrayOfValues[number].getCost() * this.arrayOfCounts[number];
        if (requiredSum > sumOfRequiredNotes ||
                (requiredSum < sumOfRequiredNotes && requiredSum % this.arrayOfValues[number].getCost() != 0)) {
            int cashOpportunity = 0;
            for (int i = 0; i <= number; i++) {
                cashOpportunity += this.arrayOfCounts[i] * this.arrayOfValues[i].getCost();
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
}
