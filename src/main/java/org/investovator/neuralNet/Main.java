package org.investovator.neuralNet;

import org.encog.Encog;
import org.investovator.data.InputTypes;

import java.util.Random;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: hasala
 * Date: 8/15/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) {

        DataRetriever dataRetriever = new DataRetriever();
        DataNormalizer dataNormalizer = new DataNormalizer();
        InputTypes[] types = {InputTypes.HIGH_PRICE, InputTypes.LOW_PRICE, InputTypes.CLOSING_PRICE, InputTypes.NO_OF_TRADES, InputTypes.SHARES_TRADED, InputTypes.TURNOVER};
        NeuralNetwork nn = new NeuralNetwork(dataNormalizer);

        System.out.println("1. Train Neural Network");
        System.out.println("2. Predict bids/offers");
        System.out.println("Please enter your preference....");

        int num = 0;
        Scanner in = new Scanner(System.in);
        num = in.nextInt();

        try {

        switch (num){
            case 1:{
                double [][]dataArray = dataRetriever.getData(types,"sampath",200,"1/4/2012");
                nn.trainAnn("sampath",dataArray);
                break;
            }

            case 2:{
                double closingPrice = nn.predictClosingPrice("sampath", "9/10/2012");
                double [] predictedValues = new double[8];

                closingPrice = dataNormalizer.getDenormalizedValue(closingPrice, InputTypes.CLOSING_PRICE);
                closingPrice = Math.round(closingPrice * 100);
                closingPrice = closingPrice / 100;
                Random random = new Random();

                System.out.println("Predicted next day closing price : " + closingPrice);


                InputTypes closing[] = {InputTypes.CLOSING_PRICE};

                double[][] dataArray = dataRetriever.getData(closing, "sampath", 2, "9/7/2012");


                double lastClosingPrice = dataArray[0][0];

                System.out.println("Sample Bids");

                double sampleSpread = 2;

                System.out.println("Sample matching range : " + (closingPrice-sampleSpread/2) + " - "+ (closingPrice+sampleSpread/2));


                int range = 2;

                //Sample Offers
                System.out.println("Offers");
                for (int i = 0; i < 5; i++) {
                    System.out.println(closingPrice-sampleSpread/2 - random.nextDouble()*range);
                }


                //Sample Bids
                System.out.println("Bids");
                for (int i = 0; i < 5; i++) {
                    System.out.println(closingPrice+sampleSpread/2 + random.nextDouble()*range);
                }


//                System.out.println("Please enter an order value....");
//                double value = in.nextDouble();
//
//                for(int i =0 ; i < 8; i++){
//                      predictedValues[i] = closingPrice * (random.nextInt(5) + 99) / 100;
//                      if(predictedValues[i] >= value)
//                        System.out.println("Entered order value matches with buy order :"+predictedValues[i]);
//                      else
//                          System.out.println("Entered order value matches with sell order :"+predictedValues[i]);
//                }

                //System.out.println("Predicted CLosing Price: " + closingPrice);
                break;
            }

            default:{
                System.out.println("Incorrect Preference....");
            }
        }

        } finally {
            Encog.getInstance().shutdown();
        }

    }


}
