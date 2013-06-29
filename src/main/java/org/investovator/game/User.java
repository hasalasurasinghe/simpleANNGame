package org.investovator.game;

/**
 * Created with IntelliJ IDEA.
 * User: ishan
 * Date: 6/28/13
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface User {

    //should return the number of shares of the company
    public int getTotalShares();
    //should share the amount of money the investor has
    public float getWealth();
}
