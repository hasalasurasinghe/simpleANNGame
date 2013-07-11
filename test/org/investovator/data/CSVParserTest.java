package org.investovator.data;

import org.investovator.neuralNet.DataNormalizer;
import org.investovator.neuralNet.DataPreprocessor;
import org.investovator.neuralNet.NormalizedData;
import org.investovator.neuralNet.TrainingData;
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



    @Test
    public void testGetDataHasala() throws Exception {

        CSVParser csvData = new CSVParser();
        InputTypes[] types = {InputTypes.HIGH_PRICE, InputTypes.LOW_PRICE, InputTypes.CLOSING_PRICE, InputTypes.NO_OF_TRADES, InputTypes.SHARES_TRADED, InputTypes.TURNOVER};
        HistoryData data;

        DataNormalizer norm = new DataNormalizer();

        DataPreprocessor prep = new DataPreprocessor();


            data = csvData.getData("sampath", types, 24, "8/1/2011");


            TrainingData tData = prep.prepareData(data.getMarketData(), types, InputTypes.CLOSING_PRICE, 1);
            NormalizedData normalized = norm.getNormalizedData(tData);


            int rowCount = tData.getMarketData().length;

            double [][] dataArray = tData.getMarketData();

            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < tData.getMarketData()[0].length; j++) {
                    System.out.print(dataArray[i][j] + "\t");
                }
                System.out.println();
            }


        for (int k = 0; k < 10; k++) {


            System.out.println();

            dataArray = tData.getMarketData();

            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < tData.getMarketData()[0].length; j++) {
                    System.out.print(dataArray[i][j] + "\t");
                }
                System.out.println();
            }

        }

    }
}
