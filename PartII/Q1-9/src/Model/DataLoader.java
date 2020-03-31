package Model;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This class is responsible for reading a csv file into a dataframe
 */
public class DataLoader {
    private Dataframe dataFrame;

    public DataLoader() { dataFrame = new DataFrameLinkedHash(); }

    /**
     * Reads a csv file
     * @param fileName The location of the csv file to read from
     * @throws IOException When file not found or the csv file is in incorrect format.
     */
    public void readData(File fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String rowLine;
            String[] colHeaders = null;
            while ((rowLine = br.readLine()) != null) {
                String[] values = rowLine.split(",", -1);
                if (colHeaders == null) {
                    colHeaders = values;
                    for (var colName : colHeaders)
                        dataFrame.addColumn(colName);
                } else {
                    if (values.length != colHeaders.length) {
                        throw new IOException("Either file in incorrect format or number of column in a row don't match total columns");
                    }
                    int i = 0;
                    for (var colName : colHeaders)
                        dataFrame.addValue(colName, values[i++]);
                }
            }
        }
    }

    /**
     * Returns the dataframe read
     * @return The dataframe
     */
    public Dataframe getDataFrame() { return dataFrame; }
}
