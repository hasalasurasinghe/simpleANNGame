package org.investovator.neuralNet;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 6/30/13
 * Time: 9:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class NormalizationModel implements Serializable {

    private double oldMax;
    private double newMax;
    private double oldMin;
    private double newMin;

   public NormalizationModel(){

   }

    public double getOldMax() {
        return oldMax;
    }

    public void setOldMax(double oldMax) {
        this.oldMax = oldMax;
    }

    public double getNewMax() {
        return newMax;
    }

    public void setNewMax(double newMax) {
        this.newMax = newMax;
    }

    public double getOldMin() {
        return oldMin;
    }

    public void setOldMin(double oldMin) {
        this.oldMin = oldMin;
    }

    public double getNewMin() {
        return newMin;
    }

    public void setNewMin(double newMin) {
        this.newMin = newMin;
    }
}
