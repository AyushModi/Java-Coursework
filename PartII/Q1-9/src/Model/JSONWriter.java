package Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * This class is responsible for writing a dataframe to a JSON
 */
public class JSONWriter {
    private Dataframe dataFrame;
    public JSONWriter(Dataframe dataFrame) {
        this.dataFrame = dataFrame;
    }

    /**
     * This method will write the dataframe to file location as a JSON
     * @param filePath The file path
     * @throws IOException If the file path can not be resolved
     */
    public void writeToJSON(String filePath) throws IOException {
        try (PrintWriter pw = new PrintWriter(filePath)) {
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
        }
    }
}
