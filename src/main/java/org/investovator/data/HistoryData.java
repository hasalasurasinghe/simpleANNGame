package org.investovator.data;

/**
 * @author rajith
 * @version $Revision$
 */
public class HistoryData {

    private InputTypes[] inputTypes;
    private float[][] marketData;

    public HistoryData(InputTypes[] inputTypes, float[][] marketData) {
        this.inputTypes = inputTypes;
        this.marketData = marketData;
    }

    public InputTypes[] getInputTypes() {
        return inputTypes;
    }

    public float[][] getMarketData() {
        return marketData;
    }
}
