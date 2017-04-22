package ru.academits.novoselovda.cashmachine.controller;

import ru.academits.novoselovda.cashmachine.model.NotesBase;
import ru.academits.novoselovda.cashmachine.model.Storage;
import ru.academits.novoselovda.notes.Money;

import java.util.ArrayList;

public class Controller {
    private Money[] userMoney;
    private NotesBase cashMachineBase;
    private Storage storage;
    private final int NOTES_MAX_COUNT = 200;

    public Controller(Money[] startMoney, Money[] userMoney) throws IllegalArgumentException {
        this.userMoney = userMoney;
        for (Money money : startMoney) {
            if (money != null && money.getCount() > NOTES_MAX_COUNT) {
                throw new IllegalArgumentException("ОШИБКА: максимальное количество хранимых купюр - " + NOTES_MAX_COUNT);
            }
        }
        storage = new Storage(NOTES_MAX_COUNT);
        this.userMoney = storage.sort(userMoney);
        startMoney = storage.sort(startMoney);
        cashMachineBase = new NotesBase(startMoney, NOTES_MAX_COUNT);
        storage.initStartDeposit(startMoney);
    }

    public String showUserMoney() {
        StringBuilder sb = new StringBuilder();
        for (Money money : this.userMoney) {
            sb.append(money).append("\n");
        }
        return sb.toString();
    }

    public String status() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("№\t%5s%8s%8s%n", "номинал", "в базе", "в банк."));
        for (int i = 0; i < this.cashMachineBase.getSize(); i++) {
            sb.append(String.format("%d)\t%5d%8d%8d%n", i, this.cashMachineBase.getNoteValue(i),
                    this.cashMachineBase.getNoteCount(i), this.storage.getCount(i)));
        }
        sb.append("Итого в банкомате: ").append(this.cashMachineBase.getSum());
        return sb.toString();
    }

    public void addMoney() throws IllegalArgumentException {
        this.cashMachineBase.add(this.userMoney);
        storage.add(this.userMoney);
    }

    public ArrayList<Money> getCashOut(int requiredSum, int requiredNoteNumber) throws IllegalArgumentException {
        return storage.subtract(this.cashMachineBase.subtract(requiredSum, requiredNoteNumber));
    }

    public void testRequiredSum(int requiredSum) throws IllegalArgumentException {
        this.cashMachineBase.testRequiredSum(requiredSum);
    }

    public String getNotesList() {
        return this.cashMachineBase.toString();
    }

    public void testRequiredNote(int requiredSum, int requiredNoteNumber) throws IllegalArgumentException {
        this.cashMachineBase.testRequiredNote(requiredSum, requiredNoteNumber);
    }
}