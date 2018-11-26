package de.mvmo.bwinf.widerstand.resistance.connection;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public enum ConnectionType {

    PARALLEL("||"),
    SERIAL("+");

    private String symbol;

    ConnectionType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
