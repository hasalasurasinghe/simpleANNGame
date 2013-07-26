package org.investovator.neuralNet;

import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import org.investovator.data.CSVParser;
import org.investovator.data.HistoryData;
import org.investovator.data.InputTypes;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 6/30/13
 * Time: 9:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataNormalizer {

    int rowCount = 0;
    double [][] dataArray = null;
    double min = 0;
    double max = 0;
    TrainingData trainingData = null;

    public  NormalizedData getNormalizedData(TrainingData data){

        trainingData = data;
        rowCount = data.getMarketData().length;
        dataArray = data.getMarketData();
        double [][] normalizedData = new double[dataArray.length][dataArray[0].length];

        /*temporary min-max*/
        for (int j = 0; j < data.getMarketData()[0].length; j++) {
            max = 0;
            for (int i = 0; i < rowCount; i++) {

                 double tmp = dataArray[i][j];
                 if (max < tmp) max=tmp;

            }

            min = max;

            for (int i = 0; i < rowCount; i++) {

                double tmp = dataArray[i][j];
                if (min > tmp) min=tmp;

            }

            NormalizedField norm = new NormalizedField(NormalizationAction.Normalize,null,max,min,1,0);

            for (int i = 0; i < rowCount; i++) {


                 normalizedData[i][j] = norm.normalize(dataArray[i][j]);

            }
        }
        return new NormalizedData(data.getInputTypes(),normalizedData, data.getOutputColumns());
    }

    public double getDenormalizedValue(double normalizedValue){

            List<InputTypes> list = Arrays.asList(trainingData.getInputTypes());
            int targetIndex= list.indexOf(InputTypes.CLOSING_PRICE);
            max = 0;
            for (int i = 0; i < rowCount; i++) {

                double tmp = dataArray[i][targetIndex];
                if (max < tmp) max=tmp;

            }

            min = max;

            for (int i = 0; i < rowCount; i++) {

                double tmp = dataArray[i][targetIndex];
                if (min > tmp) min=tmp;

            }

        NormalizedField norm = new NormalizedField(NormalizationAction.Normalize,null,max,min,1,0);

        return norm.deNormalize(normalizedValue);
}


    //Test main method - we should add JUnit :(|)
    /*public static void main(String args[]){

        CSVParser csvData = new CSVParser();
        InputTypes[] types = {InputTypes.HIGH_PRICE, InputTypes.CLOSING_PRICE,InputTypes.LOW_PRICE};
        HistoryData data;

        DataNormalizer norm = new DataNormalizer();

        try{
            data = csvData.getData("sampath", types, 10, "1/1/2011");

            NormalizedData normalized = norm.getNormalizedData( new TrainingData(null,data.getMarketData(), null) );

            System.out.println("Original");

            int rowCount = data.getMarketData().length;
            double[][] dataArray = data.getMarketData();

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

                    System.out.print( norm.getDenormalizedValue(dataArray[i][j]) + "\t");

                }
                System.out.println();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }
*/

}
