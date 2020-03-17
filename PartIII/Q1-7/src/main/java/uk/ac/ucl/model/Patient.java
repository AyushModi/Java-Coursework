package uk.ac.ucl.model;

import java.util.ArrayList;

public class Patient {
    private final String name;
    private final String ID;
    private int colCount = 0;
    private ArrayList<String> columnValues = new ArrayList<>();
    private ArrayList<String> columnNames = new ArrayList<>();
    public Patient(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }
    public void addColumn(String colName, String data) {
        colCount++;
        columnValues.add(data);
        columnNames.add(colName);
    }
    public ArrayList<String> getColumnNames() {return columnNames;}
    public ArrayList<String> getColumnValues() {return columnValues;}

    public int getColCount() {
        return colCount;
    }
}