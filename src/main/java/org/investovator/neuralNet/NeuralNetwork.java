package org.investovator.neuralNet;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;
import org.investovator.data.CSVParser;
import org.investovator.data.HistoryData;
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

    private double [][] inputData = null;
    private double [][] idealData = null;

    public NeuralNetwork()
    {

    }

    private void prepareData(double[][] dataInput)
    {

    }

    /*private static double[][] convertToDouble(float[][] inputArray)
    {
        if (inputArray== null)
            return null;

        double[][] output = new double[inputArray.length][inputArray[0].length];
        for (int i = 0; i < inputArray.length; i++)
        {
            for(int j = 0; j < inputArray[0].length; j++)
            {
               output[i][j] = inputArray[i][j];
            }
        }
        return output;
    }*/


    public void trainAnn(String companyName,double[][] dataInput)
    {

        /*try{
           // normalized = normalizer.getNormalizedData(new TrainingData(null,data.getMarketData(), null) );

            rowCount = normalized.getMarketData().length;
            dataArray = normalized.getMarketData();

        }catch (Exception e){
            e.printStackTrace();
        }*/

        NeuralDataSet trainingSet = new BasicNeuralDataSet(inputData, idealData);

        BasicNetwork network = new BasicNetwork();

        network.addLayer(new BasicLayer(null, true,6));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true,12));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true,8));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false,1));
        network.getStructure().finalizeStructure();
        network.reset();


        // train the neural network
        final MLTrain train = new ResilientPropagation(network, trainingSet);

        do
        {
           train.iteration();
        } while (train.getError() > 0.01);

        double e = network.calculateError(trainingSet);
        System.out.println("Network trained to error: " + e);

        System.out.println("Saving network");
        EncogDirectoryPersistence.saveObject(new File(companyName), network);
    }


    public double predictClosingPrice(String companyName, double [][] inputData)
    {
        float closingPrice = (float)loadAndEvaluate(companyName, inputData);
        return closingPrice;
    }

    private double loadAndEvaluate(String companyName, double [][] inputData) {
        double output = 0;
        System.out.println("Loading network");

        BasicNetwork network = (BasicNetwork)EncogDirectoryPersistence.loadObject(new File(companyName));

        MLDataSet trainingSet = new BasicMLDataSet(inputData, idealData);
        double e = network.calculateError(trainingSet);

        for (MLDataPair pair : trainingSet) {
            MLData input = pair.getInput();
            MLData predictData = network.compute(input);
            output = predictData.getData(0);
        }

        return output;
    }


    public static void main(String[] args) {

        try {
            CSVParser csvData = new CSVParser();
            InputTypes[] types = {InputTypes.DATE, InputTypes.HIGH_PRICE, InputTypes.LOW_PRICE, InputTypes.CLOSING_PRICE, InputTypes.NO_OF_TRADES, InputTypes.SHARES_TRADED, InputTypes.TURNOVER};
            HistoryData data;

            DataNormalizer norm = new DataNormalizer();
            DataPreprocessor prep = new DataPreprocessor();

            try{
                data = csvData.getData("sampath", types, 10, "8/1/2011");


                TrainingData tData = prep.prepareData(data.getMarketData(), types, InputTypes.DATE, 1);
                NormalizedData normalized = norm.getNormalizedData(tData);


                int rowCount = normalized.getMarketData().length;
                double [][] dataArray = normalized.getMarketData();

                InputTypes[] typo = normalized.getInputTypes();

                System.out.println(dataArray[0].length);

                for(int k= 0;k < typo.length;k++)
                {
                    System.out.print(typo[k] + "  ");
                }

                System.out.println();

                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < normalized.getMarketData()[i].length; j++) {

                        System.out.print(dataArray[i][j] + "\t");


                    }
                    System.out.println();
                }


               // NormalizedData normalized = norm.getNormalizedData( new TrainingData(inputTypes, data.getMarketData(), outputTypes));

                System.out.println("Original");


                /*for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < data.getMarketData()[i].length; j++) {

                        System.out.print(dataArray[i][j] + "\t");


                    }
                    System.out.println();
                }*/


               /* NeuralNetwork nn = new NeuralNetwork();
                nn.trainAnn("sampath",dataArray);*/

                /*for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < normalized.getMarketData()[i].length; j++) {

                        System.out.print(dataArray[i][j] + "\t");

                    }
                    System.out.println();
                }*/


            }catch (Exception e){
                e.printStackTrace();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            Encog.getInstance().shutdown();
        }

    }

}
