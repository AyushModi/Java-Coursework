package uk.ac.ucl.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class CSVWriter {
    private DataFrame dataFrame;
    public CSVWriter(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    public void writeToCSV(OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
            for (Iterator<String> it = dataFrame.getColumnNames().iterator(); it.hasNext();) {
                writer.write(it.next());
                if (it.hasNext()) {
                    writer.write(',');
                }
            }
            writer.newLine();
            for (int rowIndex = 0; rowIndex < dataFrame.getRowCount(); rowIndex++) {
                for (Iterator<String> it = dataFrame.getColumnNames().iterator(); it.hasNext();) {
                    writer.write(dataFrame.getValue(it.next(), rowIndex));
                    if (it.hasNext()) {
                        writer.write(',');
                    }
                }
                writer.newLine();
            }

        writer.close();
    }
}
