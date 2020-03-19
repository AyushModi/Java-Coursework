package uk.ac.ucl.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Model
{
  private final Search search = new Search(this);
  private final Analytics analytics = new Analytics(this);
  // The example code in this class should be replaced by your Model class code.
  // The data should be stored in a DataFrame.
   private DataFrame dataFrame;

  public List<Patient> getPatientsSummary()
  {
    List<Patient> names = new ArrayList<>();
    for (int i = 0; i < dataFrame.getRowCount(); i++) {
      names.add(getPatient(i));
    }
    return names;
  }
  public Patient getPatient(int index) {
    return new Patient(dataFrame.getValue("FIRST", index) + " " +
            dataFrame.getValue("LAST", index),
            dataFrame.getValue("ID", index));
  }
  public Optional<Patient> getPatientByID(String id) {
    for (int i = 0; i < dataFrame.getRowCount(); i++) {
      if (dataFrame.getValue("ID",i).equals(id)) {
        Patient pat = getPatient(i);
        for (var col : dataFrame.getColumnNames()) {
          pat.addColumn(col, dataFrame.getValue(col,i));
        }
        return Optional.of(pat);
      }
    }
    return Optional.empty();
  }

  public void readFile(File file) throws IOException
  {
    readFile(new FileInputStream(file));
  }
  public void readFile(InputStream stream) throws IOException
  {
    // Read a patient .csv data file and create the DataFrame.
    DataLoader dl = new DataLoader();
    dl.readData(stream);
    dataFrame = dl.getDataFrame();
  }
  // This also returns dummy data. The real version should use the keyword parameter to search
  // the patient data and return a list of matching items.

  public List<Patient> searchFor(SearchRequestHolder srh) {

    return search.searchFor(srh);
  }
  public ArrayList<String> getColumns() { return dataFrame.getColumnNames(); }

  public AnalyticsData getAnalytics() {
    return analytics.getAnalytics();
  }

  public void writeCSV(OutputStream output) throws IOException {
    new CSVWriter(dataFrame).writeToCSV(output);
  }

  public void writeJSON(OutputStream output) {
    new JSONWriter(dataFrame).writeToJSON(output);
  }

  public DataFrame getDataFrame() {
    return dataFrame;
  }
}
