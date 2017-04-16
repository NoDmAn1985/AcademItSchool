package ru.academits.novoselovda.cashmachine;

import ru.academits.novoselovda.notes.BundleOfMoney;
import ru.academits.novoselovda.notes.Note;
import ru.academits.novoselovda.notes.Pack;

class Machine {
    private Pack machinePurse;

    Machine(BundleOfMoney[] startMoney) throws IllegalArgumentException {
        machinePurse = new Pack(startMoney);
    }

    void cashIn(BundleOfMoney[] userMoney) throws IllegalArgumentException {
        Pack userPurse = new Pack(userMoney);
        machinePurse.add(userPurse);
    }

    BundleOfMoney[] cashOut(NotesBase notesBase, int requiredSum, Note.Values name) throws IllegalArgumentException {
        return machinePurse.subtract(notesBase, requiredSum, name);
    }

    void status() {
        System.out.println("-----< Статус механизма >----------------------------");
        int sum = 0;
        for (BundleOfMoney p = machinePurse.getFirstBundle(); p != null; p = p.getNext()) {
            System.out.println("- купюр достоинством " + p.getValueInt() + " - " + p.getCount() + " шт.;");
            sum += p.getValueInt() * p.getCount();
        }
        System.out.println("ИТОГО в банкомате: " + sum);
    }
}
