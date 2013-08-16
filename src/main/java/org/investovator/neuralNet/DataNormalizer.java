package org.investovator.neuralNet;

import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import org.investovator.data.InputTypes;

/**
 * Created with IntelliJ IDEA.
 * User: hasala
 * Date: 6/30/13
 * Time: 9:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataNormalizer {

    int rowCount = 0;
    double [][] dataArray = null;
    double min = 0;
    double max = 0;
    TrainingData trainingData = null;

    public  NormalizedData getNormalizedData(TrainingData data,InputTypes[] inputTypes){

        trainingData = data;
        rowCount = data.getMarketData().length;
        dataArray = data.getMarketData();
        double [][] normalizedData = new double[dataArray.length][dataArray[0].length];

        NormalizationModelSerializer serializer = new NormalizationModelSerializer();
        int columnCount = data.getMarketData()[0].length;

        /*temporary min-max*/
        for (int j = 0; j < columnCount; j++) {

            NormalizationModel model = new NormalizationModel();
            max = 0;
            for (int i = 0; i < rowCount; i++) {

                 double tmp = dataArray[i][j];
                 if (max < tmp) max=tmp;

            }

            min = max;

            for (int i = 0; i < rowCount; i++) {

                double tmp = dataArray[i][j];
                if (min > tmp) min=tmp;

            }

            model.setOldMax(max);
            model.setOldMin(min);

            if(j < columnCount - 1)
            serializer.saveModel(model, String.valueOf(inputTypes[j]));

            for (int i = 0; i < rowCount; i++) {


                 normalizedData[i][j] = getNormalizedValue(dataArray[i][j]);

            }
        }
        return new NormalizedData(data.getInputTypes(), normalizedData, data.getOutputColumns());
    }

    private double getNormalizedValue(double value){

        NormalizedField norm = new NormalizedField(NormalizationAction.Normalize,null,max,min,1,0);

        return norm.normalize(value);
    }

    public double getNormalizedValue(double value, InputTypes inputTypes){
        NormalizationModel model = null;
        NormalizationModelSerializer serializer = new NormalizationModelSerializer();

        if(inputTypes == InputTypes.CLOSING_PRICE)
        {
            model = serializer.readModel(String.valueOf(InputTypes.CLOSING_PRICE));
        }
        else if (inputTypes == InputTypes.HIGH_PRICE){
            model = serializer.readModel(String.valueOf(InputTypes.HIGH_PRICE));
        }
        else if (inputTypes == InputTypes.LOW_PRICE){
            model = serializer.readModel(String.valueOf(InputTypes.LOW_PRICE));
        }
        else if(inputTypes == InputTypes.NO_OF_TRADES){
            model = serializer.readModel(String.valueOf(InputTypes.NO_OF_TRADES));
        }
        else if(inputTypes == InputTypes.SHARES_TRADED){
            model = serializer.readModel(String.valueOf(InputTypes.SHARES_TRADED));
        }
        else if (inputTypes == InputTypes.TURNOVER){
            model = serializer.readModel(String.valueOf(InputTypes.TURNOVER));
        }

        NormalizedField norm = new NormalizedField(NormalizationAction.Normalize,null,model.getOldMax(),model.getOldMin(),1,0);

        return norm.normalize(value);
    }

    public double getDenormalizedValue(double normalizedValue, InputTypes inputTypes){

        NormalizationModel model = null;
            /*List<InputTypes> list = Arrays.asList(trainingData.getInputTypes());
            int targetIndex= list.indexOf(InputTypes.CLOSING_PRICE);
            max = 0;
            for (int i = 0; i < rowCount; i++) {

                double tmp = dataArray[i][targetIndex];
                if (max < tmp) max=tmp;

            }

            min = max;

            for (int i = 0; i < rowCount; i++) {

                double tmp = dataArray[i][targetIndex];
                if (min > tmp) min=tmp;

            }*/

        NormalizationModelSerializer serializer = new NormalizationModelSerializer();

        if(inputTypes == InputTypes.CLOSING_PRICE)
        {
            model = serializer.readModel(String.valueOf(InputTypes.CLOSING_PRICE));
        }

        NormalizedField norm = new NormalizedField(NormalizationAction.Normalize,null,model.getOldMax(),model.getOldMin(),1,0);

        return norm.deNormalize(normalizedValue);
    }

}
