package org.investovator.neuralNet;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationLinear;
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
        } while (train.getError() > 0.00001);

        double e = network.calculateError(trainingSet);
        System.out.println("Network trained to error: " + e);

        System.out.println("Saving network");
        EncogDirectoryPersistence.saveObject(new File(companyName), network);
    }


    public double predictClosingPrice(String companyName, double [][] inputData)
    {
        System.out.println("Loading network");

        BasicNetwork network = (BasicNetwork)EncogDirectoryPersistence.loadObject(new File(companyName));

        //0.4100006103515625
        double[] input = {0.5555555555555556,0.9473684210526315, 0.55, 0.48314606741573035, 0.1381936887921654, 0.14236926488491553};
        double[] output_arr = new double[1];

        network.compute(input, output_arr);

        return output_arr[0];
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

                /*for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < tData.getMarketData()[0].length; j++) {
                        System.out.print(dataArray[i][j] + "\t");
                    }
                    System.out.println();
                }*/

                rowCount =  normalized.getMarketData().length;
                dataArray = normalized.getMarketData();



                /*for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < tData.getMarketData()[0].length; j++) {
                        System.out.print(dataArray[i][j] + "\t");
                    }
                    System.out.println();
                }*/


            NeuralNetwork nn = new NeuralNetwork();
            nn.trainAnn("sampath",dataArray);

            NormalizationModel usedModel = normalized.getModel();

            double output;
            output = nn.predictClosingPrice("sampath", dataArray);

                System.out.println(output);
                System.out.println(norm.getDenormalizedValue(output));

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
