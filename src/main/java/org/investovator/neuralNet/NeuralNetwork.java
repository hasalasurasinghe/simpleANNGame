package org.investovator.neuralNet;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataPair;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;
import org.investovator.data.CSVParser;
import org.investovator.data.HistoryData;
import org.investovator.data.InputTypes;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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

    public NeuralNetwork()
    {

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

        network.addLayer(new BasicLayer(null, true,6));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true,16));
        network.addLayer(new BasicLayer(new ActivationTANH(), false,1));
        network.getStructure().finalizeStructure();
        network.reset();


        // train the neural network
        final MLTrain train = new ResilientPropagation(network, trainingSet);

        int count = 0;
        do
        {
            System.out.println(count);
           train.iteration();
           count ++;
        } while (train.getError() > 0.00000001);

        double e = network.calculateError(trainingSet);
        System.out.println("Network trained to error: " + e);

        System.out.println("Saving network");
        EncogDirectoryPersistence.saveObject(new File(companyName), network);
    }


    public double predictClosingPrice(String companyName, double [][] inputData)
    {
        float closingPrice = 0;
        return closingPrice;
    }

    private double loadAndEvaluate(String companyName, double [][] inputDataArray) {
        double output = 0;
        int inputCount = 0;

        System.out.println("Loading network");

        //prepareData(inputDataArray);

        double[][] input = {{244,239,240.5,50,16800.00,4066720.00}};
        double[][] ideal = {{239.1}};

        BasicNetwork network = (BasicNetwork)EncogDirectoryPersistence.loadObject(new File(companyName));

        NeuralDataSet trainingSet = new BasicNeuralDataSet(input, ideal);
        //double e = network.calculateError(trainingSet);

        for (MLDataPair pair : trainingSet) {
            MLData inputI = pair.getInput();
            MLData predictData = network.compute(inputI);
            output = predictData.getData(0);
        }



        return output;
    }


    public static void main(String[] args) {

        try {
            CSVParser csvData = new CSVParser();
            InputTypes[] types = {InputTypes.HIGH_PRICE, InputTypes.LOW_PRICE, InputTypes.CLOSING_PRICE, InputTypes.NO_OF_TRADES, InputTypes.SHARES_TRADED, InputTypes.TURNOVER};
            HistoryData data;

            DataNormalizer norm = new DataNormalizer();
            DataPreprocessor prep = new DataPreprocessor();

            try{
                data = csvData.getData("sampath", types, 24, "8/1/2011");


                TrainingData tData = prep.prepareData(data.getMarketData(), types, InputTypes.CLOSING_PRICE, 1);
                NormalizedData normalized = norm.getNormalizedData(tData);


                int rowCount = tData.getMarketData().length;
                double [][] dataArray = tData.getMarketData();

                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < tData.getMarketData()[0].length; j++) {
                        System.out.print(dataArray[i][j] + "\t");
                    }
                    System.out.println();
                }

                rowCount =  normalized.getMarketData().length;
                dataArray = normalized.getMarketData();

            NeuralNetwork nn = new NeuralNetwork();
            //nn.trainAnn("sampath",dataArray);

            double output;
            output = nn.loadAndEvaluate("sampath",dataArray);

                System.out.println(norm.getDenormalizedValue(output,normalized.getModel()));
                /*for(int i = 0; i < dataArray.length; i++ )
                {
                    System.out.print(norm.getDenormalizedValue(output[i][0], normalized.getModel()));
                    System.out.println(" - "+norm.getDenormalizedValue(output[i][1],normalized.getModel()));
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
