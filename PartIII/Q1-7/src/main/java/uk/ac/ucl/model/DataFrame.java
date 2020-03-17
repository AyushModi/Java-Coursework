package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DataFrame {
    private LinkedHashMap<String, Column> columns;

    public DataFrame() {
        columns = new LinkedHashMap<>();
    }

    public void addColumn(String colName) {
        columns.put(colName, new Column(colName));
    }

    public ArrayList<String> getColumnNames() {
        return new ArrayList<>(columns.keySet());
    }

    public int getRowCount() {
        if (columns.values().iterator().hasNext())
            return columns.values().iterator().next().getSize();
        else
            return 0;
    }

    public String getValue(String colName, int row) {
        return columns.get(colName).getRowValue(row);
    }

    public void putValue(String colName, int row, String value) {
        columns.get(colName).setRowValue(row, value);
    }

    public void addValue(String colName, String value) {
        columns.get(colName).addRowValue(value);
    }
}
