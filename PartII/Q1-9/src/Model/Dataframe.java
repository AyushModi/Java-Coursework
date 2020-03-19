package Model;

import java.util.ArrayList;

public interface Dataframe {
    void addColumn(String colName);

    ArrayList<String> getColumnNames();

    int getRowCount();

    String getValue(String colName, int row);

    void putValue(String colName, int row, String value);

    void addValue(String colName, String value);
}
