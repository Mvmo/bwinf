package de.mvmo.bwinf.widerstand.resistance;

import de.mvmo.bwinf.widerstand.resistance.connection.ConnectionType;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class MultipleResistance implements IResistance {

    private IResistance left;
    private IResistance right;

    private ConnectionType type;

    public MultipleResistance(IResistance left, IResistance right, ConnectionType type) {
        this.left = left;
        this.right = right;

        this.type = type;
    }

    @Override
    public double getValue() {
        double aVal = left.getValue();
        double bVal = right.getValue();
        if (this.type == ConnectionType.PARALLEL)
            return (aVal * bVal) / (aVal + bVal);

        return aVal + bVal;
    }

    @Override
    public String getString() {
        return ("(" + left.getString() + type.getSymbol() + right.getString() + ")");
    }
}
