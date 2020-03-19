package Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

public class JSONWriter {
    private Dataframe dataFrame;
    public JSONWriter(Dataframe dataFrame) {
        this.dataFrame = dataFrame;
    }

    public void writeToJSON(String filePath) throws IOException {
        try (PrintWriter pw = new PrintWriter(filePath)) {
            pw.printf("{\n\t\"patients\": [\n");
            for (int i = 0; i < dataFrame.getRowCount(); i++) {
                pw.printf("\t\t{\n");
//                for (var columnName : dataFrame.getColumnNames()) {
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
        }
    }
}
