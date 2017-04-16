package ru.academits.novoselovda.notes;

import ru.academits.novoselovda.cashmachine.NotesBase;

public class Pack {
    private BundleOfMoney firstBundle;
    private BundleOfMoney lastBundle;
    private Pack next;
    private Pack previous;
    private int sum;
    private int count;

    public Pack(BundleOfMoney[] packOfMoney) {
        for (int i = 0; i < packOfMoney.length; i++) {
            if (packOfMoney[i] == null || packOfMoney[i].getCount() == 0) {
                continue;
            }
            if (i > 0 && (packOfMoney[i - 1] == null || packOfMoney[i].getSum() < packOfMoney[i - 1].getSum())) {
                BundleOfMoney temp = packOfMoney[i];
                int j;
                for (j = i - 1; j >= 0; j--) {
                    if (packOfMoney[j] == null || temp.getSum() < packOfMoney[j].getSum()) {
                        packOfMoney[j + 1] = packOfMoney[j];
                    } else {
                        break;
                    }
                }
                packOfMoney[j + 1] = temp;
            }
        }
        BundleOfMoney previousBundle = null;
        boolean isFirstIsFound = false;
        this.count = 0;
        this.sum = 0;
        for (BundleOfMoney bundle : packOfMoney) {
            if (bundle == null || bundle.getCount() == 0) {
                break;
            }
            if (!isFirstIsFound) {
                this.firstBundle = bundle;
                isFirstIsFound = true;
            }
            this.lastBundle = bundle;
            bundle.setPrevious(previousBundle);
            if (previousBundle != null) {
                previousBundle.setNext(bundle);
            }
            previousBundle = bundle;
            sum += bundle.getSum();
            ++this.count;
        }
        if (this.firstBundle == null) {
            throw new IllegalArgumentException("ОШИБКА: Вы ничего не передали");
        }
        this.lastBundle.setNext(null);
    }

    public Pack getNext() {
        return next;
    }

    public void setNext(Pack next) {
        this.next = next;
    }

    public Pack getPrevious() {
        return previous;
    }

    public void setPrevious(Pack previous) {
        this.previous = previous;
    }

    public BundleOfMoney getFirstBundle() {
        return firstBundle;
    }

    public BundleOfMoney getLastBundle() {
        return lastBundle;
    }

    public void setFirstBundle(BundleOfMoney firstBundle) {
        if (firstBundle != null) {
            firstBundle.setPrevious(null);
        }
        this.firstBundle = firstBundle;
    }

    public void setLastBundle(BundleOfMoney lastBundle) {
        if (lastBundle != null) {
            lastBundle.setNext(null);
        }
        this.lastBundle = lastBundle;
    }

    public int getSum() {
        return sum;
    }

    public int getCount() {
        return this.count;
    }

    public void add(Pack userPurse) {
        sum += userPurse.getSum();
        this.count += userPurse.count;
        BundleOfMoney nextU;
        BundleOfMoney temp;
        for (BundleOfMoney u = userPurse.getFirstBundle(), m = this.getFirstBundle(); u != null; u = nextU) {
            nextU = u.getNext();
            temp = userPurse.subtractFirstBundle();
            while (m.getValueInt() < temp.getValueInt()) {
                if (this.lastBundle == m) {
                    break;
                }
                m = m.getNext();
            }
            if (m.getValueInt() == temp.getValueInt()) {
                m.add(temp);
            } else if (m.getValueInt() > temp.getValueInt()) {
                if (this.getFirstBundle() == m) {
                    insertBundle(temp, null, m);
                    this.setFirstBundle(temp);
                } else {
                    insertBundle(temp, m.getPrevious(), m);
                }
            } else if (this.lastBundle == m) {
                insertBundle(temp, m, null);
                this.setLastBundle(temp);
            }
        }
        userPurse.setLastBundle(null);
    }

    private BundleOfMoney subtractFirstBundle() {
        BundleOfMoney bundle = this.getFirstBundle();
        this.setFirstBundle(this.getFirstBundle().getNext());
        bundle.setPrevious(null);
        bundle.setNext(null);
        --this.count;
        return bundle;
    }

    private void insertBundle(BundleOfMoney bundleToInsert, BundleOfMoney previous, BundleOfMoney next) {
        if (next != null) {
            next.setPrevious(bundleToInsert);
        }
        if (previous != null) {
            previous.setNext(bundleToInsert);
        }
        bundleToInsert.setPrevious(previous);
        bundleToInsert.setNext(next);
        ++this.count;
    }

    public BundleOfMoney[] subtract(NotesBase notesBase, int requiredSum, Note.Values name) {
        BundleOfMoney[] cashOut = new BundleOfMoney[notesBase.getSize()];
        boolean isStartIsFound = false;
        int index = 0;
        for (BundleOfMoney m = this.getLastBundle(); m != null; m = m.getPrevious()) {
            if (!isStartIsFound && m.getValueName() == name) {
                isStartIsFound = true;
            }
            if (isStartIsFound) {
                cashOut[cashOut.length - 1 - index] = m.subtract(notesBase.getCount(m.getValueName()));
                if (m.getCount() == 0) {
                    if (this.getFirstBundle() == m) {
                        this.setFirstBundle(m.getNext());
                    } else if (this.getLastBundle() == m) {
                        this.setLastBundle(m.getPrevious());
                    } else {
                        m.getPrevious().setNext(m.getNext());
                    }
                    --this.count;
                }
            }
        }
        sum -= requiredSum;
        return cashOut;
    }

    public void printPackForDebug() {
        if (this.firstBundle != null) {
            System.out.println("Первый элемент - " + this.getFirstBundle() + "("
                    + this.getFirstBundle().getValueInt() + ", " + this.getFirstBundle().getCount() + ")");
        } else {
            System.out.println("Первый элемент - " + null);
        }
        if (this.lastBundle != null) {
            System.out.println("Последний элемент - " + this.getLastBundle() + "("
                    + this.getLastBundle().getValueInt() + ", " + this.getLastBundle().getCount() + ")");
        } else {
            System.out.println("Последний элемент - " + null);
        }
        System.out.println("Количество элементов - " + this.getCount());
        int index = 0;
        for (BundleOfMoney p = this.getFirstBundle(); p != null; p = p.getNext()) {
            System.out.println(index + ") " + p + "(" + p.getValueInt() + ", " + p.getCount() + ")");
            ++index;
        }
    }
}
