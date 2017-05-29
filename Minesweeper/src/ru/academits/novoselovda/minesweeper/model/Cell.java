package ru.academits.novoselovda.minesweeper.model;

class Cell {
    private int number;
    private boolean isFlagHere;
    private boolean isShown;

    Cell(){
    }

    Cell(int number){
        this.number = number;
    }

    void setMine() {
        this.number = -1;
    }

    boolean isMineHere() {
        return this.number < 0;
    }

    int getNumber() {
        return this.number;
    }

    void setFlagHere(boolean isFlagHere) {
        this.isFlagHere = isFlagHere;
    }

    boolean isFlagHere() {
        return this.isFlagHere;
    }

    void setShown() {
        this.isShown = true;
    }

    boolean isShown() {
        return this.isShown;
    }
}
