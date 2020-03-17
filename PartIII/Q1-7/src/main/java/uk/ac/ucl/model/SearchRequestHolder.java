package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;

public class SearchRequestHolder {
    private List<String> filledColumnNames = new ArrayList<>();
    private List<String> filledColumnValues = new ArrayList<>();
    private List<Boolean> matchWhole = new ArrayList<>();

    public void addItem(String colName, String colValue, Boolean matchWhole) {
        this.filledColumnNames.add(colName);
        this.filledColumnValues.add(colValue);
        this.matchWhole.add(matchWhole);
    }

    public List<String> getColumnNames() { return filledColumnNames; }
    public String getColumnValue(int index) { return filledColumnValues.get(index); }
    public Boolean getMatchWhole(int index) { return matchWhole.get(index); }
}
