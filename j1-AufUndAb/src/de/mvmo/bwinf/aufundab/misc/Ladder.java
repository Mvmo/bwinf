package de.mvmo.bwinf.aufundab.misc;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class Ladder {

    private int fieldA;
    private int fieldB;

    public Ladder(int fieldA, int fieldB) {
        this.fieldA = fieldA;
        this.fieldB = fieldB;
    }

    public boolean hasField(int field) {
        return fieldA == field || fieldB == field;
    }

    public int getField(int field) {
        return fieldA == field ? fieldB : fieldB == field ? fieldA : -1;
    }

}
