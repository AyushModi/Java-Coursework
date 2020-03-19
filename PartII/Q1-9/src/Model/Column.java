package Model;

public interface Column {
    String getName();

    int getSize();

    String getRowValue(int rowIndex);

    void setRowValue(int index, String value);

    void addRowValue(String value);
}
