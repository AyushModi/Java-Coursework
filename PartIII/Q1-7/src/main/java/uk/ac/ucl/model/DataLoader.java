package uk.ac.ucl.model;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DataLoader {
    private DataFrame dataFrame;

    public DataLoader() { dataFrame = new DataFrame(); }

    public void readData(InputStream inputStream) throws IOException, IllegalStateException  {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String rowLine;
        String[] colHeaders = null;
        while((rowLine = br.readLine()) != null && !rowLine.contains(","));
        if (rowLine == null)
            return;
        do {
            String[] values = rowLine.split(",", -1);
            if (colHeaders == null) {
                colHeaders = values;
                for (var colName : colHeaders) {
                    dataFrame.addColumn(colName);
                }
            } else {
                if (values.length != colHeaders.length) {
                    throw new IllegalStateException("Number of column in a row don't match total columns");
                }
                int i = 0;
                for (var colName : colHeaders)
                    dataFrame.addValue(colName, values[i++]);
            }
        } while ((rowLine = br.readLine()) != null && rowLine.contains(","));
        br.close();
        if (!dataFrame.getColumnNames().contains("FIRST") || !dataFrame.getColumnNames().contains("LAST") || !dataFrame.getColumnNames().contains("ID"))
            throw new IllegalStateException("CSV must contain 'FIRST', 'LAST' and 'ID' columns");
    }

    public DataFrame getDataFrame() { return dataFrame; }
}
