package org.investovator.neuralNet;

import org.investovator.data.CSVParser;
import org.investovator.data.HistoryData;
import org.investovator.data.InputTypes;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 6/30/13
 * Time: 9:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataNormalizer {

    public  NormalizedData getNormalizedData(TrainingData data){

        int rowCount = data.getMarketData().length;
        float[][] dataArray = data.getMarketData();

        float min = 0;
        float max = 0;


        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < data.getMarketData()[i].length; j++) {

                System.out.print(dataArray[i][j] + "\t");

            }
            System.out.println();
        }


        /*temporary min-max*/
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < data.getMarketData()[i].length; j++) {

                 float tmp = dataArray[i][j];
                 if (max < tmp) max=tmp;

            }
        }

        min = max;

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < data.getMarketData()[i].length; j++) {

                float tmp = dataArray[i][j];
                if (min > tmp) min=tmp;

            }
        }

        NormalizationModel model = new NormalizationModel(1,-1, min, max);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < data.getMarketData()[i].length; j++) {

                dataArray[i][j] = model.getNormalizedValue(dataArray[i][j]);

            }
        }

        return new NormalizedData(data.getInputTypes(),dataArray, data.getOutputColumns(), model );
    }

    public float getDenormalizedValue(float normalizedValue, NormalizationModel model){

        throw new NotImplementedException();
    }

    //Test main method - we should add JUnit :(|)
    public static void main(String args[]){

        CSVParser csvData = new CSVParser();
        InputTypes[] types = {InputTypes.HIGH_PRICE, InputTypes.CLOSING_PRICE};
        HistoryData data;

        try{
            data = csvData.getData("sampath", types, 10, "1/1/2011");
            DataNormalizer norm = new DataNormalizer();
            norm.getNormalizedData( new TrainingData(null,data.getMarketData(), null) );

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
