package Model;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DataLoader {
    private DataFrame dataFrame;

    public DataLoader() { dataFrame = new DataFrame(); }

    public void readData(File fileName) throws Exception {
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
                        throw new Exception("Number of column in a row don't match total columns");
                    }
                    int i = 0;
                    for (var colName : colHeaders)
                        dataFrame.addValue(colName, values[i++]);
                }
            }
        }
    }

    public DataFrame getDataFrame() { return dataFrame; }
}
