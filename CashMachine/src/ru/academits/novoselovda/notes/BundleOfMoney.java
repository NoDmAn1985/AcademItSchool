package ru.academits.novoselovda.notes;

public class BundleOfMoney {
    private int sum;
    private Note.Values name;
    private int value;
    private Note firstNote;
    private Note lastNote;
    private int count;
    private BundleOfMoney previous;
    private BundleOfMoney next;

    public BundleOfMoney(Note.Values name, int count, BundleOfMoney previous, BundleOfMoney next) {
        Note[] notes = new Note[count];
        notes[0] = new Note(name, null, null);
        for (int i = 1; i < notes.length; i++) {
            notes[i] = new Note(name, notes[i - 1], null);
            notes[i - 1].setNextNote(notes[i]);
        }
        this.count = count;
        this.name = name;
        this.value = name.getValue();
        this.sum = name.getValue() * count;
        this.firstNote = notes[0];
        this.lastNote = notes[this.count - 1];
        this.previous = previous;
        this.next = next;
    }

    private BundleOfMoney(Note.Values name, int count, Note firstNote, Note lastNote, BundleOfMoney previous, BundleOfMoney next) {
        this.name = name;
        this.sum = name.getValue() * this.getCount();
        this.count = count;
        this.firstNote = firstNote;
        this.lastNote = lastNote;
        this.previous = previous;
        this.next = next;
    }
    public void add(BundleOfMoney notesToAdd) {
        this.lastNote.setNextNote(notesToAdd.firstNote);
        this.count += notesToAdd.count;
    }

    public BundleOfMoney subtract(int subtractCount) {
        int index = 1;
        Note subtractFirstNote = this.firstNote;
        for (Note p = firstNote; p != null; p = p.getNextNote()) {
            if (index == subtractCount) {
                this.firstNote = p.getNextNote();
                this.count -= subtractCount;
                return new BundleOfMoney(name, subtractCount, subtractFirstNote, p, null, null);
            }
            ++index;
        }
        return null;
    }

    public Note getFirstNote() {
        return firstNote;
    }

    public void setFirstNote(Note firstNote) {
        this.firstNote = firstNote;
    }

    public Note getLastNote() {
        return lastNote;
    }

    public void setLastNote(Note lastNote) {
        this.lastNote = lastNote;
    }

    public BundleOfMoney getPrevious() {
        return previous;
    }

    public void setPrevious(BundleOfMoney previous) {
        this.previous = previous;
    }

    public BundleOfMoney getNext() {
        return next;
    }

    public void setNext(BundleOfMoney next) {
        this.next = next;
    }

    public int getSum() {
        return sum;
    }

    public int getValueInt() {
        return this.value;
    }

    public Note.Values getValueName() {
        return name;
    }

    public int getCount() {
        return this.count;
    }

}
