package Model;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * This class is responsible for dealing with the data side of this application
 */
public class Model {
    private Dataframe dataFrame;
    private ArrayList<Integer> filterList;

    /**
     * This method will load the dataframe from a csv
     * @param filePath The file location of the csv
     * @throws IOException If the file location cannot be resolved
     */
    public void loadFile(File filePath) throws IOException {
        DataLoader dl = new DataLoader();
        dl.readData(filePath);
        dataFrame = dl.getDataFrame();
        filterList = null;
    }

    private void resetFilterList() { filterList = null; }

    /**
     * This method will return the array of column names
     * @return String[] with the column names
     */
    public String[] getColumnNames() {
        return dataFrame.getColumnNames().toArray(new String[0]);
    }

    /**
     * Returns the column. Rows can be filtered out if a search has taken place
     * @param columnName The column name
     * @return String[] with the column values
     */
    public String[] getColumnValues(String columnName) {
        ArrayList<String> returnList = new ArrayList<>();
        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            if (filterList != null && !filterList.contains(i)) continue;
            returnList.add(dataFrame.getValue(columnName, i));
        }
        return returnList.toArray(new String[0]);
    }

    /**
     * This method will carry out the search and filter rows that match with the search.
     * @param searchValue The value to search for
     * @param chosenColumns The columns to search in
     * @param exactMatch Whether the search value should match an entire cell (exact match),
     *                   or the search value should be contained in a cell ignoring case (not exact match).
     */
    public void search(String searchValue, String[] chosenColumns, Boolean exactMatch) {
        if (searchValue.equals("")) {
            resetFilterList();
            return;
        }
        Function<String, Boolean> checkHasString;
        checkHasString = (exactMatch)
                ? s -> s.equals(searchValue)
                : s -> s.toLowerCase().contains(searchValue.toLowerCase());

        filterList = new ArrayList<>();
        int rowCount = dataFrame.getRowCount();
        for (var columnName : chosenColumns) {
            for (int i = 0; i < rowCount; i++) {
                if (checkHasString.apply(dataFrame.getValue(columnName, i))) {
                    filterList.add(i);
                }
            }
        }
    }

    /**
     * Writes the dataframe to a JSON at the specified file path
     * @param filePath The file path
     * @throws IOException If the file path is not resolved
     */
    public void writeToJSON(File filePath) throws IOException {
        JSONWriter jw = new JSONWriter(dataFrame);
        if (!filePath.toString().contains(".json"))
            jw.writeToJSON(filePath.toString() +".json");
        else
            jw.writeToJSON(filePath.toString());
    }
}
