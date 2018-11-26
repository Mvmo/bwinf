package de.mvmo.bwinf.widerstand.resistance;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class SingleResistance implements IResistance {

    private double value;

    public SingleResistance(int value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String getString() {
        return String.valueOf(value);
    }
}
