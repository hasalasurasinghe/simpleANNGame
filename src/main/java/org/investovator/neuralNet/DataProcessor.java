package org.investovator.neuralNet;

/**
 * Created with IntelliJ IDEA.
 * User: ishan
 * Date: 6/28/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 *
 * Used to process the data as required by the NN
 */
public interface DataProcessor {

    /**
     * Prepares data to be used by the ANN by shifting the rows.
     * @param inputData The original data set
     * @param dataItemList metadata indicating the order of the data set
     * @param targetDataItem metadata to identify the column that needs to predicted by the ANN
     * @param amountToShift indicates by how many rows should the shift happen
     * @return the prepared data
     */
    public float[][] prepareData(float[][] inputData, Object dataItemList, Object targetDataItem, int amountToShift);
}
