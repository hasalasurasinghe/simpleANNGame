package org.investovator.neuralNet;

import org.investovator.data.CSVParser;
import org.investovator.data.CSVParserTest;
import org.investovator.data.HistoryData;
import org.investovator.data.InputTypes;
import org.junit.Test;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class DataPreprocessorTest {
    @Test
    public void testPrepareData() throws Exception {

        InputTypes[] types = {InputTypes.HIGH_PRICE, InputTypes.LOW_PRICE, InputTypes.CLOSING_PRICE, InputTypes.NO_OF_TRADES, InputTypes.SHARES_TRADED, InputTypes.TURNOVER};


        CSVParser parser = new CSVParser();
        DataPreprocessor processor = new DataPreprocessor();

        HistoryData data;
        TrainingData training_data;

        try{

            data = parser.getData("sampath", types, 24, "8/1/2011");

            for (int j = 0; j < 100; j++) {



                training_data =  processor.prepareData(data.getMarketData(), types, InputTypes.CLOSING_PRICE, 1);

                int length = training_data.getMarketData()[0].length;

                for (int i = 0; i < length; i++) {
                    System.out.print(training_data.getMarketData()[0][i] + "\t" );
                }

                System.out.println("");

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
