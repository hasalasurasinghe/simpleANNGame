package org.investovator.neuralNet;

import org.investovator.data.CSVParser;
import org.investovator.data.HistoryData;
import org.investovator.data.InputTypes;

/**
 * Created with IntelliJ IDEA.
 * User: hasala
 * Date: 8/15/13
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataRetriever {

    private double [][] dataArray = null;

    public DataRetriever(){

    }

    public double[][] getData(InputTypes[] types, String companyName, int numOfRecords, String date){
        try {
            CSVParser csvData = new CSVParser();
            HistoryData data;
            DataNormalizer norm = new DataNormalizer();
            DataPreprocessor prep = new DataPreprocessor();

                data = csvData.getData(companyName, types, numOfRecords, date);


                TrainingData tData = prep.prepareData(data.getMarketData(), types, InputTypes.CLOSING_PRICE, 1);


                int rowCount = tData.getMarketData().length;
                dataArray = tData.getMarketData();

                if(numOfRecords == 2)
                    return dataArray;

                /*for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < tData.getMarketData()[0].length; j++) {
                        System.out.print(dataArray[i][j] + "\t");
                    }
                    System.out.println();
                }*/
                NormalizedData normalized = norm.getNormalizedData(tData, types);

                rowCount =  normalized.getMarketData().length;
                dataArray = normalized.getMarketData();



                /*for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < tData.getMarketData()[0].length; j++) {
                        System.out.print(dataArray[i][j] + "\t");
                    }
                    System.out.println();
                }*/

    } catch (Throwable t) {
        t.printStackTrace();
    }
    return dataArray;
    }
}
