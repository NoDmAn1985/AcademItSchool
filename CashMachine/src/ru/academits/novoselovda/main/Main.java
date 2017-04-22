package ru.academits.novoselovda.main;

import ru.academits.novoselovda.cashmachine.controller.Controller;
import ru.academits.novoselovda.cashmachine.view.ConsoleMainMenu;
import ru.academits.novoselovda.notes.Money;
import ru.academits.novoselovda.notes.Values;

public class Main {
    public static void main(String[] args) {
 /*
Разработать консольную программу «Банкомат».
Банкомат должен уметь принимать и выдавать деньги, отображать свое состояние.
Купюры могут быть разного достоинства (10, 50, 100, 500 и т.д.).
Банкомат должен иметь ограничение по количеству хранимых в нем купюр.
Перед выдачей купюр банкомат должен спросить у пользователя купюрами какого достоинства произвести выдачу.

 */

        Money[] cashMachineMoney = new Money[Values.values().length];
//        cashMachineMoney[3] = new Money(Values.TEN, 15);
        cashMachineMoney[1] = new Money(Values.FIFTY, 100);
//        cashMachineMoney[2] = new Money(Values.ONE_HUNDRED, 100);
        cashMachineMoney[0] = new Money(Values.FIVE_HUNDREDS, 90);
        cashMachineMoney[4] = new Money(Values.ONE_THOUSAND, 200);
//        cashMachineMoney[5] = new Money(Values.FIVE_THOUSANDS, 100);

        Money[] userMoney = new Money[Values.values().length];
        userMoney[3] = new Money(Values.TEN, 15);
        userMoney[2] = new Money(Values.ONE_HUNDRED, 90);
        userMoney[0] = new Money(Values.FIVE_HUNDREDS, 90);

        try {
            Controller controller = new Controller(cashMachineMoney, userMoney);
            ConsoleMainMenu cashMachine = new ConsoleMainMenu(controller);
            cashMachine.show();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
