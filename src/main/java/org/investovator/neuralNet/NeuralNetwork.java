package org.investovator.neuralNet;

import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.train.MLTrain;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;
import org.investovator.data.InputTypes;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: hasala
 * Date: 7/2/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class NeuralNetwork implements NnInterface{

    private double [][] inputData ;
    private double [][] idealData;
    DataNormalizer dataNormalizer = null;


    public NeuralNetwork(DataNormalizer dataNormalizer)
    {
             this.dataNormalizer =  dataNormalizer;
    }

    private void prepareData(double[][] dataInput)
    {
        int rowCount = dataInput.length;
        inputData = new double[rowCount][dataInput[0].length - 1];
        idealData = new double[rowCount][1];
        for (int i = 0; i < rowCount; i++) {
           for(int j = 0;j < dataInput[0].length -1 ; j++)
           {
                inputData[i][j] = dataInput[i][j];
           }
                idealData[i][0] = dataInput[i][dataInput[0].length - 1];
        }

    }


    public void trainAnn(String companyName,double[][] dataInput)
    {
        prepareData(dataInput);

        NeuralDataSet trainingSet = new BasicNeuralDataSet(inputData, idealData);

        BasicNetwork network = new BasicNetwork();

        network.addLayer(new BasicLayer(new ActivationLinear(), true,6));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true,10));
        network.addLayer(new BasicLayer(new ActivationTANH(), false,1));
        network.getStructure().finalizeStructure();
        network.reset();

        // train the neural network
        final MLTrain train = new ResilientPropagation(network, trainingSet);

        do
        {
           train.iteration();
           if(train.getIteration() > 100000)
           {
               System.out.println("Neural Network Training Failed");
               System.exit(0);
           }
        } while (train.getError() > 0.001);

        double e = network.calculateError(trainingSet);
        System.out.println("Network trained to error: " + e);

        System.out.println("Saving network");
        EncogDirectoryPersistence.saveObject(new File(companyName), network);
    }


    public double predictClosingPrice(String companyName, String date)
    {
        System.out.println("Loading network");
        InputTypes[] types = {InputTypes.HIGH_PRICE, InputTypes.LOW_PRICE, InputTypes.CLOSING_PRICE, InputTypes.NO_OF_TRADES, InputTypes.SHARES_TRADED, InputTypes.TURNOVER};
        DataRetriever dataRetriever = new DataRetriever();
        double[][] dataArray = dataRetriever.getData(types, companyName, 2, date);
        double[] input = new double[dataArray[0].length - 1];

        BasicNetwork network = (BasicNetwork)EncogDirectoryPersistence.loadObject(new File(companyName));

        for(int j = 0;j < dataArray[0].length - 1; j++){
            input[j] =  dataNormalizer.getNormalizedValue(dataArray[0][j],types[j]);
        }
        double [] output_arr = new double[1];

        network.compute(input, output_arr);

        return output_arr[0];
    }
}
