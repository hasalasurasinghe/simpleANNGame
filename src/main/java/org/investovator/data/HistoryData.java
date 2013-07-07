package org.investovator.data;

/**
 * @author rajith
 * @version $Revision$
 */
public class HistoryData {

    private InputTypes[] inputTypes;
    private double[][] marketData;

    public HistoryData(InputTypes[] inputTypes, double[][] marketData) {
        this.inputTypes = inputTypes;
        this.marketData = marketData;
    }

    public InputTypes[] getInputTypes() {
        return inputTypes;
    }

    public double[][] getMarketData() {
        return marketData;
    }
}
