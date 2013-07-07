package org.investovator.neuralNet;

import org.investovator.data.InputTypes;

import java.util.Arrays;

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
    public TrainingData prepareData(double[][] inputData, InputTypes[] dataItemList, InputTypes targetDataItem, int amountToShift) {

        InputTypes[] output = {InputTypes.CLOSING_PRICE};

        //get the index of the target column
        int targetIndex= Arrays.asList(dataItemList).indexOf(targetDataItem);

        //create an array with the shifted elements
        double[] shiftedElements = new double[inputData.length-amountToShift];
        for(int row = amountToShift; row <= shiftedElements.length; row++)
        {
            shiftedElements[row-1] = inputData[row][targetIndex];
        }

        //delete the final rows of the inputData array to match the lengths
        int numOfRows = inputData.length-amountToShift;
        double[][] target = new double[numOfRows][inputData[0].length+1];
        for (int i = 0; i < numOfRows; i++) {
            System.arraycopy(inputData[i], 0, target[i], 0, inputData[i].length);
            //copy the shifted values to the output array
            target[i][inputData[i].length]=shiftedElements[i];
        }

        return new TrainingData(dataItemList,target,output);

    }

   /*// Use the main method for testing

    public static void main(String[] args) {

        //test dataset
        double[][] testSet=new double[4][3];
        testSet[0][0]=0.0f;
        testSet[0][1]=0.1f;
        testSet[0][2]=0.2f;
        testSet[1][0]=1.0f;
        testSet[1][1]=1.1f;
        testSet[1][2]=1.2f;
        testSet[2][0]=2.0f;
        testSet[2][1]=2.1f;
        testSet[2][2]=2.2f;
        testSet[3][0]=3.0f;
        testSet[3][1]=3.1f;
        testSet[3][2]=3.2f;

        //enum data array
        InputTypes[] dataTypes=new InputTypes[3];
        dataTypes[0]=InputTypes.HIGH_PRICE;
        dataTypes[1]=InputTypes.CLOSING_PRICE;
        dataTypes[2]=InputTypes.DATE;


        DataPreprocessor dp=new DataPreprocessor();
        double[][] hh=dp.prepareData(testSet,dataTypes,InputTypes.DATE,1);
        System.out.println(hh[0][2]);

    }*/


}
