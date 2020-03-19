package uk.ac.ucl.model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

public class JSONWriter {
    private DataFrame dataFrame;
    public JSONWriter(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    public void writeToJSON(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        pw.printf("{\n\t\"patients\": [\n");
        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            pw.printf("\t\t{\n");
            Iterator<String> it = dataFrame.getColumnNames().iterator();
            while (it.hasNext()){
                String colName = it.next();
                if (it.hasNext())
                    pw.printf("\t\t\t\"%s\":\"%s\",\n", colName, dataFrame.getValue(colName, i));
                else
                    pw.printf("\t\t\t\"%s\":\"%s\"\n", colName, dataFrame.getValue(colName, i));
            }
            if (i+1<dataFrame.getRowCount())
                pw.printf("\t\t},\n");
            else
                pw.printf("\t\t}\n\t]\n");
        }
        pw.printf("}\n");
        pw.close();
    }
}
