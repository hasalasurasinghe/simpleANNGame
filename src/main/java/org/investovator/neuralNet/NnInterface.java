package org.investovator.neuralNet;


/**
 * Created with IntelliJ IDEA.
 * User: ishan
 * Date: 6/28/13
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 *
 * Should serve as the interface to the other packages from the ANN
 */


public interface NnInterface {

    /**
     * Once the company name and the data is passed, this method should train the NN and save the trained model for
     * future use.
     *
     * TODO: decide on how to pass the parameters. Parameters are "Company name" and "data"
     *
     */
    public void trainAnn(String companyName, double [][] dataInput);

    /**
     * TODO: decide on the parameters
     *
     * @return The closing price for the next day of the given company
     */
    public double predictClosingPrice(String companyName, String date);
}
