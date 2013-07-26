package org.investovator.data;

import au.com.bytecode.opencsv.CSVReader;
import org.investovator.data.utils.DateUtils;
import org.investovator.exeptions.DAOException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * @author rajith
 * @version $Revision$
 */
public class CSVParser implements DAO {

    public static String DATE_FORMAT = "MM/dd/yyyy";
    public static String DIRECTORY_PATH = "." + File.separator + "src" + File.separator + "main" + File.separator
            + "resources" + File.separator + "trainingdata" + File.separator;


    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryData getData(String company, InputTypes[] inputTypes, int numOfRows,
                               String startDate) throws DAOException {

        String filePath = DIRECTORY_PATH + company.toLowerCase() + "_daily.csv";
        double [][] marketData = parseUsingOpenCSV(filePath, inputTypes, numOfRows, startDate);
        return new HistoryData(inputTypes, marketData);
    }

    /**
     * Return the float [][] array of required columns and rows
     *
     * @param filePath   filePath
     * @param inputTypes required column names
     * @param numOfRows  required num of rows
     * @param date       from date
     * @return float [][] of required market data
     * @throws DAOException
     */
    private double [][] parseUsingOpenCSV(String filePath, InputTypes[] inputTypes,
                                        int numOfRows, String date) throws DAOException {

        double [][] csvData = null;
        int addedRows = 0;
        ArrayList<InputTypes> columnNames = new ArrayList<InputTypes>(Arrays.asList(inputTypes));
        columnNames.add(InputTypes.DATE);

        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));

            String[] nextLine;
            int rowNumber = 0;
            HashMap<InputTypes, Integer> inputTypesHashMap = new HashMap<InputTypes, Integer>();

            while (((nextLine = reader.readNext()) != null) && (addedRows != numOfRows)) {
                if (rowNumber == 0) {
                    for (InputTypes columnName : columnNames) {
                        for (int i = 0; i < nextLine.length; i++) {
                            if (InputTypes.getCSVName(columnName).equals(nextLine[i].trim())) {
                                inputTypesHashMap.put(columnName, i);
                            }
                        }
                    }

                    csvData = new double[numOfRows][inputTypes.length];
                    columnNames.remove(InputTypes.DATE);

                    rowNumber++;
                } else if (DateUtils.isDateBefore(DATE_FORMAT, date, nextLine[inputTypesHashMap.get(InputTypes.DATE)])) {
                    int j = 0;
                    for (InputTypes columnName : columnNames) {
                        csvData[addedRows][j] = Float.valueOf(nextLine[inputTypesHashMap.get(columnName)]);
                        j++;
                    }
                    addedRows++;
                }
            }
            reader.close();
            return csvData;
        } catch (FileNotFoundException e) {
            throw new DAOException("CSV file not found: " + e.getMessage());
        } catch (IOException e) {
            throw new DAOException("I/O exception:: " + e.getMessage());
        }
    }
}
