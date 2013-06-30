package org.investovator.data;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 6/29/13
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public enum InputTypes {
    DATE,
    STARTING_PRICE,
    CLOSING_PRICE,
    LOW_PRICE,
    HIGH_PRICE,
    SHARES_TRADED,
    TURNOVER,
    NO_OF_TRADES;

    /*This should be removed outside this class later*/
    public static String getCSVName(InputTypes inputType) {

        switch (inputType) {
            case STARTING_PRICE:
                return "opening price";
            case CLOSING_PRICE:
                return "closing price";
            case LOW_PRICE:
                return "Low";
            case HIGH_PRICE:
                return "High";
            case SHARES_TRADED:
                return "No. of Shares";
            case TURNOVER:
                return "Turnover";
            case NO_OF_TRADES:
                return "No. of Trades";
            case DATE:
                return "Day";
        }
        return null;
    }
}
