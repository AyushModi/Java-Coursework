package Model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DataFrameLinkedHash implements Dataframe {
    private LinkedHashMap<String, Column> columns;

    public DataFrameLinkedHash() { columns = new LinkedHashMap<>(); }

    @Override
    public void addColumn(String colName) {
        columns.put(colName, new ColumnArrayList(colName));
    }
    @Override
    public ArrayList<String> getColumnNames() {
        return new ArrayList<>(columns.keySet());
    }
    @Override
    public int getRowCount() {
        if (columns.values().iterator().hasNext())
            return columns.values().iterator().next().getSize();
        else
            return 0;
    }
    @Override
    public String getValue(String colName, int row) {
        return columns.get(colName).getRowValue(row);
    }
    @Override
    public void putValue(String colName, int row, String value) {
        columns.get(colName).setRowValue(row, value);
    }
    @Override
    public void addValue(String colName, String value) {
        columns.get(colName).addRowValue(value);
    }

}
