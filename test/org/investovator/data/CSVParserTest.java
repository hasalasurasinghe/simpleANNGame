package org.investovator.data;

import org.junit.Test;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class CSVParserTest {
    @Test
    public void testGetData() throws Exception {

        InputTypes[] types = {InputTypes.HIGH_PRICE, InputTypes.LOW_PRICE, InputTypes.CLOSING_PRICE, InputTypes.NO_OF_TRADES, InputTypes.SHARES_TRADED, InputTypes.TURNOVER};


        CSVParser parser = new CSVParser();

        HistoryData data;

        try{

            for (int j = 0; j < 10; j++) {



                data = parser.getData("sampath", types, 24, "8/1/2011");

                int length = data.getMarketData()[0].length;

                for (int i = 0; i < length; i++) {
                    System.out.print(data.getMarketData()[0][i] + "\t" );
                }

                System.out.println("");

            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
