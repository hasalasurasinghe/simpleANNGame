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
        double [][] dataArray = data.getMarketData();
        double [][] normalizedData = new double[dataArray.length][];

        double min = 0;
        double max = 0;


        /*temporary min-max*/
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < data.getMarketData()[i].length; j++) {

                 double tmp = dataArray[i][j];
                 if (max < tmp) max=tmp;

            }
        }

        min = max;

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < data.getMarketData()[i].length; j++) {

                double tmp = dataArray[i][j];
                if (min > tmp) min=tmp;

            }
        }

        NormalizationModel model = new NormalizationModel(1,max, min, -1);

        for (int i = 0; i < rowCount; i++) {
            normalizedData[i] = new double[data.getMarketData()[i].length];

            for (int j = 0; j < data.getMarketData()[i].length; j++) {

                normalizedData[i][j] = model.getNormalizedValue(dataArray[i][j]);

            }
        }

        return new NormalizedData(data.getInputTypes(),normalizedData, data.getOutputColumns(), model );
    }

    public double getDenormalizedValue(double normalizedValue, NormalizationModel model){

        return  model.getDenormalizedValue(normalizedValue);
    }


           /*
    //Test main method - we should add JUnit :(|)
    public static void main(String args[]){

        CSVParser csvData = new CSVParser();
        InputTypes[] types = {InputTypes.HIGH_PRICE, InputTypes.CLOSING_PRICE};
        HistoryData data;

        DataNormalizer norm = new DataNormalizer();

        try{
            data = csvData.getData("sampath", types, 10, "1/1/2011");

            NormalizedData normalized = norm.getNormalizedData( new TrainingData(null,data.getMarketData(), null) );

            System.out.println("Original");

            int rowCount = data.getMarketData().length;
            float[][] dataArray = data.getMarketData();

            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < data.getMarketData()[i].length; j++) {

                    System.out.print(dataArray[i][j] + "\t");

                }
                System.out.println();
            }


            System.out.println("\nNormalized");

            rowCount = normalized.getMarketData().length;
            dataArray = normalized.getMarketData();

            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < normalized.getMarketData()[i].length; j++) {

                    System.out.print(dataArray[i][j] + "\t");

                }
                System.out.println();
            }



            System.out.println("\nDenormalized from normalized");
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < data.getMarketData()[i].length; j++) {

                    System.out.print( norm.getDenormalizedValue(dataArray[i][j], normalized.getModel()) + "\t");

                }
                System.out.println();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    */
}
