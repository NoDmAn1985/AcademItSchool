package ru.academits.novoselovda.main;

import ru.academits.novoselovda.cashmachine.CashMachine;
import ru.academits.novoselovda.notes.BundleOfMoney;
import ru.academits.novoselovda.notes.Note;

public class Main {
    public static void main(String[] args) {
 /*
Разработать консольную программу «Банкомат».
Банкомат должен уметь принимать и выдавать деньги, отображать свое состояние.
Купюры могут быть разного достоинства (10, 50, 100, 500 и т.д.).
Банкомат должен иметь ограничение по количеству хранимых в нем купюр.
Перед выдачей купюр банкомат должен спросить у пользователя купюрами какого достоинства произвести выдачу.

 */

        BundleOfMoney[] cashMachineMoney = new BundleOfMoney[Note.Values.values().length];
//        cashMachineMoney[3] = new BundleOfMoney(Note.Values.TEN, 15, null, null);
        cashMachineMoney[1] = new BundleOfMoney(Note.Values.FIFTY, 100, null, null);
//        cashMachineMoney[2] = new BundleOfMoney(Notes.Values.ONE_HUNDRED, 100);
        cashMachineMoney[0] = new BundleOfMoney(Note.Values.FIVE_HUNDREDS, 90, null, null);
        cashMachineMoney[4] = new BundleOfMoney(Note.Values.ONE_THOUSAND, 200, null, null);
//        cashMachineMoney[5] = new BundleOfMoney(Note.Values.FIVE_THOUSANDS, 100, null, null);
        CashMachine vtb24 = new CashMachine(cashMachineMoney);
        vtb24.mainMenu();
    }
}
