package Model;

import java.util.ArrayList;
/**
 *  This interface defines the public methods that all Dataframe classes have.
 */
public interface Dataframe {
    /**
     * Add a column with the specified column name to the dataframe
     * @param colName The column name
     */
    void addColumn(String colName);

    /**
     * Returns a list with all the names of the columns in the dataframe
     * @return
     */
    ArrayList<String> getColumnNames();

    /**
     * Returns the number of rows in the dataframe
     * @return
     */
    int getRowCount();

    /**
     * Gets the value of cell, given the column name and row index
     * @param colName The column name
     * @param row The row index
     * @return The value in that cell
     */
    String getValue(String colName, int row);

    /**
     * Edits the value of cell
     * @param colName The column name
     * @param row The row index
     * @param value The value to set
     */
    void putValue(String colName, int row, String value);

    /**
     * Add a value to the end of a column
     * @param colName The column name
     * @param value The value to add
     */
    void addValue(String colName, String value);
}
