package org.investovator.neuralNet;

import org.apache.commons.lang.ArrayUtils;
import org.investovator.data.InputTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ishan
 * Date: 6/29/13
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataPreprocessor implements DataProcessorInterface {
    /**
     * Prepares data to be used by the ANN by shifting the rows.
     *
     * @param inputData      The original data set
     * @param dataItemList   metadata indicating the order of the data set
     * @param targetDataItem metadata to identify the column that needs to predicted by the ANN
     * @param amountToShift  indicates by how many rows should the shift happen
     * @return the prepared data
     */
    @Override
    public float[][] prepareData(double[][] inputData, InputTypes[] dataItemList, InputTypes targetDataItem, int amountToShift) {

        //get the index of the target column
        int targetIndex= Arrays.asList(dataItemList).indexOf(targetDataItem);

        //get the particular column from the array and remove "amountToShift" number of items
        List<Double> itemsList = new LinkedList<Double>(Arrays.asList(ArrayUtils.toObject(inputData[targetIndex])));

        //double[] column=inputData[targetIndex];

        //List<Double> column= ArrayUtils.

        for (int i=0;i<amountToShift;i++){
            itemsList.remove(0);

            System.out.println();
            //Arrays.toString(itemsList.toArray());

            //delete the last row of the inputData array to match the lengths


        }
//        itemsList.remove(0);
                return new float[0][];


    }

    public static void main(String[] args) {

        //test dataset
        double[][] testSet=new double[3][2];
        testSet[0][0]=0.0f;
        testSet[0][1]=0.1f;
        testSet[1][0]=1.0f;
        testSet[1][1]=1.1f;
        testSet[2][0]=2.0f;
        testSet[2][1]=2.1f;

        //enum data array
        InputTypes[] dataTypes=new InputTypes[2];
        dataTypes[0]=InputTypes.HIGH_PRICE;
        dataTypes[1]=InputTypes.CLOSING_PRICE;


        DataPreprocessor dp=new DataPreprocessor();
        dp.prepareData(testSet,dataTypes,InputTypes.CLOSING_PRICE,1);


    }
//
//    public void printArr(float[][] arr){
//
//    }


}
