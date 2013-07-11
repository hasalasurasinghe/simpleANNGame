package org.investovator.neuralNet;

import org.investovator.data.InputTypes;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class NormalizedData extends TrainingData{

    private NormalizationModel model;


    NormalizedData(InputTypes[] inputTypes, double[][] marketData, InputTypes[] outputColumns, NormalizationModel model) {
        super(inputTypes, marketData, outputColumns);
        this.model = model;
    }

    NormalizationModel getModel() {
        return model;
    }
}