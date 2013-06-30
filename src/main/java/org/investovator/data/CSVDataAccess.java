package org.investovator.data;

import au.com.bytecode.opencsv.CSVReader;
import org.investovator.exeptions.DAOException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;


/**
 * @author rajith
 * @version $Revision$
 */
public class CSVDataAccess implements DAO {

    public static String DATE_FORMAT = "MM/dd/yyyy";
    public static String FILE_PATH = "." + File.separator + "src" + File.separator + "main" + File.separator
            + "resources" + File.separator + "trainingdata" + File.separator + "sampath_daily.csv";


    @Override
    public HistoryData getData(String company, InputTypes[] inputTypes, int numOfRows,
                               String startDate) throws DAOException {
        float[][] marketData = parseUsingOpenCSV(FILE_PATH, inputTypes, numOfRows, startDate);
        return new HistoryData(inputTypes, marketData);
    }


    private float[][] parseUsingOpenCSV(String filePath, InputTypes[] inputTypes,
                                        int numOfRows, String date) throws DAOException {

        float[][] csvData = null;
        int addedRows = 0;
        ArrayList<InputTypes> columnNames = new ArrayList<InputTypes>(Arrays.asList(inputTypes));
        columnNames.add(InputTypes.DATE);

        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));

            String[] nextLine;
            int rowNumber = 0;
            HashMap<InputTypes, Integer> inputTypesHashMap = new HashMap<InputTypes, Integer>();

            while ((nextLine = reader.readNext()) != null) {
                if (rowNumber == 0) {
                    for (InputTypes columnName : columnNames) {
                        for (int i = 0; i < nextLine.length; i++) {
                            if (InputTypes.getCSVName(columnName).equals(nextLine[i].trim())) {
                                inputTypesHashMap.put(columnName, i);
                            }
                        }
                    }
                    csvData = new float[numOfRows][inputTypesHashMap.size()];
                    addedRows = 0;
                    rowNumber++;
                } else {
                    if (isDateBefore(DATE_FORMAT, date, nextLine[inputTypesHashMap.get(InputTypes.DATE)])) {
                        if (addedRows < numOfRows) {
                            int j = 0;
                            for (int i : inputTypesHashMap.values()) {
                                if (i != inputTypesHashMap.get(InputTypes.DATE)) {
                                    csvData[addedRows][j] = Float.valueOf(nextLine[i]);
                                    j++;
                                }
                            }
                            addedRows++;
                        }
                    }
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

    /**
     * Evaluate to true if reference date is before the CSV date
     * column's date (ex: refDate = 1/27/2012 , csvDate 1/31/2012)
     *
     * @param dateFormat date format of the csv file
     * @param refDate    reference date
     * @param csvDate    csv column's date
     * @return true if the reference date is before the CSV date
     */
    private boolean isDateBefore(String dateFormat, String refDate, String csvDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date date1 = sdf.parse(refDate);
            Date date2 = sdf.parse(csvDate);
            return date1.compareTo(date2) <= 0;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Evaluate to true if reference date is after the CSV date
     * column's date (ex: refDate = 1/31/2012 , csvDate 1/27/2012)
     *
     * @param dateFormat date format of the csv file
     * @param refDate    reference date
     * @param csvDate    csv column's date
     * @return true if the reference date is after the CSV date
     */
    private boolean isDateAfter(String dateFormat, String refDate, String csvDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date date1 = sdf.parse(refDate);
            Date date2 = sdf.parse(csvDate);
            return date1.compareTo(date2) >= 0;
        } catch (ParseException e) {
            return false;
        }
    }
}