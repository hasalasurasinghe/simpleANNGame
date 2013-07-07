package org.investovator.neuralNet;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 6/30/13
 * Time: 9:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class NormalizationModel {

    private double oldMax;
    private double newMax;
    private double oldMin;
    private double newMin;

    /*
        MinMax normalization formula

        out = (in - min)/(max - min)*(new_max-new_min) + new_min

     */

    public NormalizationModel(double newMax, double oldMax, double oldMin, double newMin) {
        this.newMax = newMax;
        this.oldMax = oldMax;
        this.oldMin = oldMin;
        this.newMin = newMin;
    }

    public double getNormalizedValue(double data){
         return (data - oldMin)/(oldMax - oldMin)*(newMax-newMin) + newMin;
    }

    public double getDenormalizedValue(double normalizedValue) {
        return (normalizedValue - newMin)*(oldMax - oldMin)/(newMax-newMin) + oldMin;
    }
}
