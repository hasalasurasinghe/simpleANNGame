package org.investovator.neuralNet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: hasala
 * Date: 8/14/13
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class NormalizationModelSerializer {

    private NormalizationModel normalizationModel;

    public NormalizationModelSerializer(){
    }

    public void saveModel(NormalizationModel model, String fileName){

        // Save the model to file
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(model);

            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public NormalizationModel readModel(String fileName){

        // Read the model from file
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            normalizationModel = (NormalizationModel) in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return normalizationModel;
    }
}
