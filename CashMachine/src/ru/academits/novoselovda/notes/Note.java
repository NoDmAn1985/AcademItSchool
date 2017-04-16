package ru.academits.novoselovda.notes;

public class Note {
    public enum Values {
        TEN(10),
        FIFTY(50),
        ONE_HUNDRED(100),
        FIVE_HUNDREDS(500),
        ONE_THOUSAND(1000),
        FIVE_THOUSANDS(5000);
        private int value;
        Values(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    private int value;
    private Values name;
    private Note previousNote;
    private Note nextNote;



    public Note(Values name, Note previousNote, Note nextNote) {
        this.previousNote = previousNote;
        this.nextNote = nextNote;
        this.name = name;
        this.value = name.value;
    }

    public int getValueInt() {
        return value;
    }

    public Values getValueName() {
        return name;
    }

    public Note getPreviousNote() {
        return previousNote;
    }

    public void setPreviousNote(Note previousNote) {
        this.previousNote = previousNote;
    }

    public Note getNextNote() {
        return nextNote;
    }

    public void setNextNote(Note nextNote) {
        this.nextNote = nextNote;
    }
}
