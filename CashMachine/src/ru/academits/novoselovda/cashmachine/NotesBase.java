package ru.academits.novoselovda.cashmachine;

import ru.academits.novoselovda.notes.BundleOfMoney;
import ru.academits.novoselovda.notes.Note;

import java.util.Arrays;

public class NotesBase {
    private int noteMaxCount;
    private int sum;
    private int length = Note.Values.values().length;
    private int[] arrayOfCounts = new int[this.length];
    private Note.Values[] arrayOfNames = Arrays.copyOf(Note.Values.values(), this.length);

    NotesBase(int noteMaxCount) {
        this.noteMaxCount = noteMaxCount;
        this.sum = 0;
    }

    NotesBase(NotesBase mainBase, int requiredSum, Note.Values requiredNote) {
        boolean isStartFound = false;
        int leftSum = requiredSum;
        for (int i = this.length - 1; i >= 0; i--) {
            if (!isStartFound && this.arrayOfNames[i].getValue() == requiredNote.getValue()) {
                isStartFound = true;
            }
            if (isStartFound) {
                if (this.arrayOfNames[i].getValue() * mainBase.arrayOfCounts[i] >= leftSum ) {
                    this.arrayOfCounts[i] = leftSum / this.arrayOfNames[i].getValue();
                } else if (this.arrayOfNames[i].getValue() * this.noteMaxCount < leftSum) {
                    this.arrayOfCounts[i] = mainBase.arrayOfCounts[i];
                }
                leftSum -= this.arrayOfNames[i].getValue() * this.arrayOfCounts[i];
            }
        }
        System.out.println(this);
        System.out.println(leftSum);
    }

    NotesBase(BundleOfMoney[] purse, int noteMaxCount) {
        this.noteMaxCount = noteMaxCount;
        int index = 0;
        sum = 0;
        for (Note.Values note : Note.Values.values()) {
            for (BundleOfMoney bundle : purse) {
                if (bundle != null && bundle.getValueInt() == note.getValue()) {
                    this.arrayOfCounts[index] = bundle.getCount();
                    break;
                }
            }
            this.arrayOfNames[index] = note;
            this.sum += this.arrayOfCounts[index] * this.arrayOfNames[index].getValue();
            ++index;
        }
    }

    void add(NotesBase userBase) {
        for (int i = 0; i < this.length; i++) {
            if (userBase.arrayOfCounts[i] != 0) {
                if (this.arrayOfCounts[i] + userBase.arrayOfCounts[i] > noteMaxCount) {
                    throw new IllegalArgumentException("ОШИБКА: максимальное количество хранимых купюр - " + noteMaxCount);
                }
                this.sum += userBase.arrayOfCounts[i] * userBase.arrayOfNames[i].getValue();
                this.arrayOfCounts[i] += userBase.arrayOfCounts[i];
            }
        }
    }

    void subtract(NotesBase userBase) {
        for (int i = 0; i < this.length; i++) {
            if (userBase.arrayOfCounts[i] != 0) {
                this.sum -= userBase.arrayOfCounts[i] * userBase.arrayOfNames[i].getValue();
                this.arrayOfCounts[i] -= userBase.arrayOfCounts[i];
            }
        }
    }

    void setCount(int index, int count) {
        arrayOfCounts[index] = count;
    }

    int getCount(int index) {
        return arrayOfCounts[index];
    }

    public int getCount(Note.Values name) {
        for (int i = 0; i < length; i++) {
            if (this.arrayOfNames[i] == name) {
                return this.arrayOfCounts[i];
            }
        }
        return -1;
    }

    Note.Values getValueName(int index) {
        return arrayOfNames[index];
    }

    public int getSize() {
        return length;
    }

    int getSum() {
        return sum;
    }

    void clear() {
        Arrays.fill(arrayOfCounts, 0);
    }

    BundleOfMoney[] toBundle() {
        BundleOfMoney[] newMoney = new BundleOfMoney[length];
        for (int i = 0; i < length; i++) {
            if (this.getCount(i) == 0) {
                newMoney[i] = null;
            } else {
                newMoney[i] = new BundleOfMoney(this.arrayOfNames[i], this.arrayOfCounts[i], null, null);
            }
        }
        return newMoney;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (this.arrayOfCounts[i] != 0) {
                sb.append(i).append(") купюры достоинством ")
                        .append(this.arrayOfNames[i].getValue()).append("\n");
            }
        }
        return sb.toString();
    }

    String toStringWithCount() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(i).append(") купюры достоинством ")
                    .append(this.arrayOfNames[i].getValue()).append(" - ")
                    .append(this.arrayOfCounts[i]).append(" шт.\n");
        }
        return sb.toString();
    }

    void testRequiredSum(int requiredSum) throws IllegalArgumentException {
        if (requiredSum < 0) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемая сумма должна быть положительной");
        }
        if (requiredSum % this.arrayOfNames[0].getValue() > 0) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемая сумма должна быть кратна "
                    + this.arrayOfNames[0].getValue());
        }
        if (requiredSum > this.sum) {
            throw new IllegalArgumentException("ОШИБКА: в банкомате недостаточно средств");
        }
    }

    void testRequiredNote(int requiredSum, int number) throws IllegalArgumentException {
        if (number < 0 || number >= length || this.arrayOfCounts[number] == 0) {
            throw new IllegalArgumentException("ОШИБКА: нет такого номера");
        }
        if (requiredSum < this.arrayOfNames[number].getValue()) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемая сумма меньше запрашивемой купюры");
        }
    }

    void testOnOpportunity(int requiredSum, int number) throws IllegalArgumentException, RuntimeException {
        int sumOfRequiredNotes = this.arrayOfNames[number].getValue() * this.arrayOfCounts[number];
        if (requiredSum > sumOfRequiredNotes) {
            int cashOpportunity = 0;
            for (int i = 0; i <= number; i++) {
                cashOpportunity += this.arrayOfCounts[i] * this.arrayOfNames[i].getValue();
            }
            if (requiredSum > cashOpportunity) {
                throw new IllegalArgumentException("ОШИБКА: данных купюр недостаточно");
            }
            if (requiredSum < cashOpportunity) {
                throw new RuntimeException("ОШИБКА: данных купюр недостаточно");
            }
        }
    }
}
