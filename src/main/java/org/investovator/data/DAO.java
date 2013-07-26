package org.investovator.data;

import org.investovator.exeptions.DAOException;

/**
 * Created with IntelliJ IDEA.
 * User: ishan
 * Date: 6/28/13
 * Time: 10:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DAO {

    /**
     * Parameters : Company, enum values for the data items, number of rows needed, start date
     *
     * @return HistoryData
     */
    public HistoryData getData(String company, InputTypes[] inputTypes, int numOfRows, String startDate) throws DAOException;
}
