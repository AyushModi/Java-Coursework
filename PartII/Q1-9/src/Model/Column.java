package Model;

/**
 *  This interface defines the public methods that all column classes have.
 */
public interface Column {
    /**
     * Get the name of the Column
     * @return The column name
     */
    String getName();

    /**
     * Get the number of items in the column (equates to the number of rows in column)
     * @return the number of rows
     */
    int getSize();

    /**
     * Returns the value stored at the specified in the column
     * @param rowIndex The index to get the item from
     * @return the value at that row in the column
     */
    String getRowValue(int rowIndex);

    /**
     * Sets item in the column at a specific index to the specific value
     * @param index The index to set the item
     * @param value The value to set the column cell to.
     */
    void setRowValue(int index, String value);

    /**
     * Adds a new row to the column
     * @param value The value to add.
     */
    void addRowValue(String value);
}
