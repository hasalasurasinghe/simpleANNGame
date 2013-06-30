package org.investovator.neuralNet;

import org.investovator.data.HistoryData;
import org.investovator.data.InputTypes;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 6/30/13
 * Time: 8:44 AM
 * To change this template use File | Settings | File Templates.
 */


class TrainingData extends HistoryData {

    private InputTypes[] outputColumns;

    TrainingData(InputTypes[] inputTypes, float[][] marketData, InputTypes[] outputColumns) {
        super(inputTypes, marketData);
        this.outputColumns = outputColumns;
    }

    InputTypes[] getOutputColumns() {
        return outputColumns;
    }
}


class NormalizedData extends TrainingData{

    private NormalizationModel model;


    NormalizedData(InputTypes[] inputTypes, float[][] marketData, InputTypes[] outputColumns, NormalizationModel model) {
        super(inputTypes, marketData, outputColumns);
        this.model = model;
    }

    NormalizationModel getModel() {
        return model;
    }
}
