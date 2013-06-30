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

    private float oldMax;
    private float newMax;
    private float oldMin;
    private float newMin;

    /*
        MinMax normalization formula

        out = (in - min)/(max - min)*(new_max-new_min) + new_min

     */

    public NormalizationModel(float newMax, float oldMax, float oldMin, float newMin) {
        this.newMax = newMax;
        this.oldMax = oldMax;
        this.oldMin = oldMin;
        this.newMin = newMin;
    }

    public float getNormalizedValue(float data){
         return (data - oldMin)/(oldMax - oldMin)*(newMax-newMin) + newMax;
    }

    public float getDenormalizedValue(float normalizedValue) {
        return (normalizedValue - newMin)*(oldMax - oldMin)/(newMax-newMin) + oldMin;
    }
}
