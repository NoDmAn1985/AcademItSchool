package ru.academits.novoselovda.cashmachine.controller;

import ru.academits.novoselovda.cashmachine.model.CashMachine;
import ru.academits.novoselovda.notes.Money;
import ru.academits.novoselovda.notes.Values;

import java.util.ArrayList;

public class Controller {
    private CashMachine cashMachine;
    private static final int NOTES_MAX_COUNT = 200;

    public Controller(Money[] startMoney) throws IllegalArgumentException, NullPointerException {
        this.cashMachine = new CashMachine(startMoney, NOTES_MAX_COUNT);
    }

    public Money[] getCashMachineDeposit() {
        return this.cashMachine.getDeposit();
    }

    public int getCashMachineSum() {
        return this.cashMachine.getSum();
    }

    public void addMoney(Money[] userMoney) throws IllegalArgumentException {
        this.cashMachine.add(userMoney);
    }

    public ArrayList<Money> getCashOut(int requiredSum, Values value) throws IllegalArgumentException {
        return this.cashMachine.subtract(requiredSum, value);
    }

    public void testRequiredSum(int requiredSum) throws IllegalArgumentException {
        this.cashMachine.testRequiredSum(requiredSum);
    }

    public void testRequiredNote(int requiredSum, int requiredNoteNumber) throws IllegalArgumentException, ArithmeticException {
        this.cashMachine.testRequiredNote(requiredSum, requiredNoteNumber);
    }
}