package org.investovator.neuralNet;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.mathutil.randomize.ConsistentRandomizer;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.util.simple.EncogUtility;
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


    public NeuralNetwork()
    {

    }


    public void trainAnn(String companyName,double[][] inputData)
    {
        //BasicNetwork network = EncogUtility.simpleFeedForward(6, 3, 0, 1, false);

        BasicNetwork network = new BasicNetwork();

        network.addLayer(new BasicLayer(new ActivationSigmoid(), true,6));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true,12));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true,8));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true,1));
        network.getStructure().finalizeStructure();
        network.reset();

        NeuralDataSet trainingSet = new BasicNeuralDataSet(XOR_INPUT, XOR_IDEAL);



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


    public float predictClosingPrice(String companyName)
    {

        loadAndEvaluate(companyName);
        float closingPrice = 0;
        return closingPrice;
    }

    private void loadAndEvaluate(String companyName) {
        System.out.println("Loading network");

        BasicNetwork network = (BasicNetwork)EncogDirectoryPersistence.loadObject(new File(companyName));

        MLDataSet trainingSet = new BasicMLDataSet(XOR_INPUT, XOR_IDEAL);
        double e = network.calculateError(trainingSet);
        System.out.println("Loaded network's error is(should be same as above): " + e);

        MLDataSet output = network.compute(input);
    }


    public static void main(String[] args) {
        try {
            NeuralNetwork nw = new NeuralNetwork();

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            Encog.getInstance().shutdown();
        }

    }

}
