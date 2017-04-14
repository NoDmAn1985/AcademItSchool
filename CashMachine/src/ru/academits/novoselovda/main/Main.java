package ru.academits.novoselovda.main;

import ru.academits.novoselovda.cashmachine.CashMachine;
import ru.academits.novoselovda.notes.Notes;

public class Main {
    public static void main(String[] args) {
 /*
Разработать консольную программу «Банкомат».
Банкомат должен уметь принимать и выдавать деньги, отображать свое состояние.
Купюры могут быть разного достоинства (10, 50, 100, 500 и т.д.).
Банкомат должен иметь ограничение по количеству хранимых в нем купюр.
Перед выдачей купюр банкомат должен спросить у пользователя купюрами какого достоинства произвести выдачу.

 */

        Notes[] cashMachineMoney = new Notes[6];
        cashMachineMoney[3] = new Notes(Notes.Values.TEN, 15);
        cashMachineMoney[1] = new Notes(Notes.Values.FIFTY, 100);
//        cashMachineMoney[2] = new Notes(Notes.Values.ONE_HUNDRED, 100);
        cashMachineMoney[0] = new Notes(Notes.Values.FIVE_HUNDREDS, 90);
        cashMachineMoney[4] = new Notes(Notes.Values.ONE_THOUSAND, 200);
        cashMachineMoney[5] = new Notes(Notes.Values.FIVE_THOUSANDS, 100);
        CashMachine vtb24 = new CashMachine(cashMachineMoney);
        vtb24.mainMenu();
    }
}
