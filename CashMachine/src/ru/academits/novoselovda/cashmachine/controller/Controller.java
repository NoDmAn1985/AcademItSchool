package ru.academits.novoselovda.cashmachine.controller;

import ru.academits.novoselovda.cashmachine.model.NotesBase;
import ru.academits.novoselovda.notes.Money;

import java.util.ArrayList;

public class Controller {
    private Money[] userMoney;
    private NotesBase cashMachineBase;
    private final int NOTES_MAX_COUNT = 200;

    public Controller(Money[] startMoney, Money[] userMoney) throws IllegalArgumentException {
        this.userMoney = userMoney;
        for (Money money : startMoney) {
            if (money != null && money.getCount() > NOTES_MAX_COUNT) {
                throw new IllegalArgumentException("ОШИБКА: максимальное количество хранимых купюр - " + NOTES_MAX_COUNT);
            }
        }
        cashMachineBase = new NotesBase(startMoney, NOTES_MAX_COUNT);
        this.userMoney = cashMachineBase.sort(userMoney);
    }

    public int getCashMachineNoteCount(int index) {
        return this.cashMachineBase.getNoteCount(index);
    }

    public int getCashMachineNoteValue(int index) {
        return this.cashMachineBase.getNoteValue(index);
    }

    public int getCashMachineNotesCount() {
        return this.cashMachineBase.getSize();
    }

    public int getCashMachineSum() {
        return this.cashMachineBase.getSum();
    }

    public int getUserNoteCount(int index) {
        return this.userMoney[index].getCount();
    }

    public int getUserNoteValue(int index) {
        return this.userMoney[index].getValue().getCost();
    }

    public int getUserNotesCount() {
        return this.userMoney.length;
    }

    public void addMoney() throws IllegalArgumentException {
        this.cashMachineBase.add(this.userMoney);
    }

    public ArrayList<Money> getCashOut(int requiredSum, int requiredNoteNumber) throws IllegalArgumentException {
        return this.cashMachineBase.subtract(requiredSum, requiredNoteNumber);
    }

    public void testRequiredSum(int requiredSum) throws IllegalArgumentException {
        this.cashMachineBase.testRequiredSum(requiredSum);
    }

    public void testRequiredNote(int requiredSum, int requiredNoteNumber) throws IllegalArgumentException {
        this.cashMachineBase.testRequiredNote(requiredSum, requiredNoteNumber);
    }
}