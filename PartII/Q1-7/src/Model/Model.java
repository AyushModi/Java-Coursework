package Model;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class Model {
    private DataFrame dataFrame;
    private ArrayList<Integer> filterList;
    private ArrayList<Integer> allRows;
    public Model() {
        System.out.println("Model load thread: " + Thread.currentThread().getName());
    }

    public void loadFile(File filePath) throws Exception {
        DataLoader dl = new DataLoader();
        dl.readData(filePath);
        dataFrame = dl.getDataFrame();
        createAllRows();
    }
    private void createAllRows() {
        allRows = new ArrayList<>();
        for (int i = 0; i < dataFrame.getRowCount(); allRows.add(i++));
        resetFilterList();
    }
    private void resetFilterList() { filterList = allRows; }

    public String[] getColumnNames() {
        return dataFrame.getColumnNames().toArray(new String[0]);
    }

    public String[] getColumnValues(String columnName) {
        ArrayList<String> returnList = new ArrayList<>();
        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            if (!filterList.contains(i)) continue;
            returnList.add(dataFrame.getValue(columnName, i));
        }
        return returnList.toArray(new String[0]);
    }

    public void search(String searchValue, String[] chosenColumns, Boolean exactMatch) {
        if (searchValue.equals("")) {
            resetFilterList();
            return;
        }
        Function<String, Boolean> checkHasString;
        checkHasString = (exactMatch)
                ? s -> s.equals(searchValue)
                : s -> s.contains(searchValue);

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

    public void search(String searchValue, String[] chosenColumns) {
        search(searchValue, chosenColumns, false);
    }
}
