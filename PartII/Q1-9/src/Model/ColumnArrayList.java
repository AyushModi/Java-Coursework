package Model;

import java.util.ArrayList;

public class ColumnArrayList implements Column {
    private String name;
    private ArrayList<String> rows;

    public ColumnArrayList(String name) {
        this.name = name;
        rows = new ArrayList<>();
    }

    @Override
    public String getName() { return name; }

    @Override
    public int getSize() { return rows.size(); }

    @Override
    public String getRowValue(int rowIndex) { return rows.get(rowIndex); }

    @Override
    public void setRowValue(int index, String value) { rows.set(index, value); }

    @Override
    public void addRowValue(String value) { rows.add(value); }
}