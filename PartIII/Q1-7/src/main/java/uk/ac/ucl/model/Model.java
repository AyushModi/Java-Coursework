package uk.ac.ucl.model;

import javax.naming.NameNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Model
{
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
  private Patient getPatient(int index) {
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

  public void readFile(File file) throws Exception
  {
    // Read a patient .csv data file and create the DataFrame.
    DataLoader dl = new DataLoader();
    dl.readData(file);
    dataFrame = dl.getDataFrame();
  }

  // This also returns dummy data. The real version should use the keyword parameter to search
  // the patient data and return a list of matching items.

  public List<Patient> searchFor(SearchRequestHolder srh) {
//    if (searchValue.equals(""))
//      return getPatientsSummary();
//    Function<String, Boolean> checkHasString;
//    checkHasString = (exactMatch != null && exactMatch.equals("on"))
//            ? s -> s.equals(searchValue)
//            : s -> s.contains(searchValue);

    List<Patient> result = new ArrayList<>();
    int rowCount = dataFrame.getRowCount();
    int colIndex = 0;
    for (var columnName : srh.getColumnNames()) {
      String requiredValue = srh.getColumnValue(colIndex);
      Boolean matchWhole = srh.getMatchWhole(colIndex);
      colIndex++;

      Function<String, Boolean> checkHasString;
      checkHasString = (matchWhole)
            ? s -> s.equals(requiredValue)
            : s -> s.contains(requiredValue);
      for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
        if (checkHasString.apply(dataFrame.getValue(columnName, rowIndex))) {
          result.add(getPatient(rowIndex));
        }
      }
    }
    return result;
  }
  public ArrayList<String> getColumns() { return dataFrame.getColumnNames(); }
}
