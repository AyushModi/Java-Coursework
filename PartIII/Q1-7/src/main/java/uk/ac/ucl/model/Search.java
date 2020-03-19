package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Search {
    private final Model model;

    public Search(Model model) {
        this.model = model;
    }

    public List<Patient> searchFor(SearchRequestHolder srh) {
        List<Patient> result = new ArrayList<Patient>();
        int rowCount = model.getDataFrame().getRowCount();
        int colIndex = 0;
        List<Integer> matchedRows = IntStream.range(0, rowCount).boxed().collect(Collectors.toList());
        for (var columnName : srh.getColumnNames()) {
            String requiredValue = srh.getColumnValue(colIndex);
            Boolean matchWhole = srh.getMatchWhole(colIndex);
            colIndex++;

            Function<String, Boolean> checkHasString;
            checkHasString = (matchWhole)
                    ? s -> s.equals(requiredValue)
                    : s -> s.contains(requiredValue);
            ArrayList<Integer> unmatched = new ArrayList<Integer>();
            for (Integer rowIndex : matchedRows) {
                if (!checkHasString.apply(model.getDataFrame().getValue(columnName, rowIndex))) {
                    unmatched.add(rowIndex);
                }
            }
            matchedRows.removeAll(unmatched);
        }
        for (int rowIndex : matchedRows) {
            result.add(model.getPatient(rowIndex));
        }

        return result;
    }
}