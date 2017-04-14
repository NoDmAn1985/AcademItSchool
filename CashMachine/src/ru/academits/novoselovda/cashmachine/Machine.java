package ru.academits.novoselovda.cashmachine;

import ru.academits.novoselovda.notes.Notes;

import java.util.ArrayList;
import java.util.Arrays;

class Machine {
    private final int NOTES_MAX_COUNT = 200;
    final int MIN_NOTES_VALUE = 10;
    private Notes[] machinePurse;

    Machine(Notes[] moneyStart) throws IllegalArgumentException {
        for (int i = 1; i < moneyStart.length; i++) {
            if (moneyStart[i] == null) {
                Notes[] temp = Arrays.copyOf(moneyStart, moneyStart.length);
                moneyStart = new Notes[temp.length - 1];
                System.arraycopy(temp, 0, moneyStart, 0, i);
                System.arraycopy(temp, i + 1, moneyStart, i, temp.length - (i + 1));
            }
            if (moneyStart[i].getCount() > NOTES_MAX_COUNT) {
                throw new IllegalArgumentException("ОШИБКА: максимальное количество хранимых купюр - " + NOTES_MAX_COUNT);
            }
            if (moneyStart[i].getValueInt() < moneyStart[i - 1].getValueInt()) {
                Notes temp = moneyStart[i];
                int j;
                for (j = i - 1; j >= 0; j--) {
                    if (temp.getValueInt() < moneyStart[j].getValueInt()) {
                        moneyStart[j + 1] = moneyStart[j];
                    } else {
                        break;
                    }
                }
                moneyStart[j + 1] = temp;
            }
        }
        this.machinePurse = moneyStart;
    }

    void cashIn(Notes[] userMoney) throws IllegalArgumentException {
        Notes[] tempUserPurse = Arrays.copyOf(userMoney, userMoney.length);
        for (int i = 0; i < tempUserPurse.length; i++) {
            for (int j = 0; j < machinePurse.length; j++) {
                if (tempUserPurse[i].getValueInt() == machinePurse[j].getValueInt()) {
                    int maximumNotes = NOTES_MAX_COUNT - machinePurse[i].getCount();
                    if (tempUserPurse[i].getCount() > maximumNotes) {
                        throw new IllegalArgumentException("ОШИБКА: невозможно внести всю сумму, купюры достоинством \""
                                + tempUserPurse[i].getValueInt() + "\" можно внести только " + maximumNotes + " шт.");
                    }
                    machinePurse[i].addCount(tempUserPurse[i].getCount());
                    break;
                }
                if (tempUserPurse[i].getValueInt() < machinePurse[j].getValueInt()) {
                    Notes[] tempMachinePurse = Arrays.copyOf(machinePurse, machinePurse.length);
                    machinePurse = new Notes[tempMachinePurse.length + 1];
                    System.arraycopy(tempMachinePurse, 0, machinePurse, 0, j);
                    machinePurse[j] = new Notes(tempUserPurse[i].getValueName(), tempUserPurse[i].getCount());
                    System.arraycopy(tempMachinePurse, j, machinePurse, j + 1, tempMachinePurse.length - j);
                    break;
                }
            }
        }
    }

    ArrayList<Notes> cashOut(int requiredSum, Notes.Values name) throws IllegalArgumentException {
        if (requiredSum < MIN_NOTES_VALUE) {
            throw new IllegalArgumentException("ОШИБКА: введена слишком маленькая сумма");
        }
        ArrayList<Notes> cash = new ArrayList<>();
        Notes[] tempMachinePurse = Arrays.copyOf(machinePurse, machinePurse.length);
        int leftSum = requiredSum;
        boolean isRequiredValueFound = false;
        for (int i = tempMachinePurse.length - 1; i >= 0; i--) {
            if (!isRequiredValueFound && tempMachinePurse[i].getValueName() != name) {
                continue;
            }
            isRequiredValueFound = true;
            int notesCount = leftSum / tempMachinePurse[i].getValueInt();
            if (notesCount <= tempMachinePurse[i].getCount()) {
                cash.add(new Notes(tempMachinePurse[i].getValueName(), notesCount));
                leftSum = leftSum - notesCount * tempMachinePurse[i].getValueInt();
                tempMachinePurse[i].deductCount(notesCount);
                if (leftSum == 0) {
                    break;
                }
            } else {
                cash.add(new Notes(tempMachinePurse[i].getValueName(), tempMachinePurse[i].getCount()));
                leftSum = leftSum - tempMachinePurse[i].getCount() * tempMachinePurse[i].getValueInt();
                tempMachinePurse[i].deductCount(tempMachinePurse[i].getCount());
            }
        }
        if (leftSum > 0) {
            if (leftSum == requiredSum) {
                throw new IllegalArgumentException("ОШИБКА: в банкомате нет данных купюр");
            } else {
                throw new IllegalArgumentException("ОШИБКА: в банкомате недостаточно средств");
            }
        } else {
            machinePurse = Arrays.copyOf(tempMachinePurse, tempMachinePurse.length);
            return cash;
        }
    }

    void status() {
        System.out.println("-----< Статус банкомата >----------------------------");
        int sum = 0;
        for (Notes notes : machinePurse) {
            System.out.println("- купюр достоинством " + notes.getValueInt() + " - " + notes.getCount() + " шт.;");
            sum += notes.getValueInt() * notes.getCount();
        }
        System.out.println("ИТОГО в банкомате: " + sum);
    }

    Notes[] getMachineNotes() {
        return machinePurse;
    }

}
