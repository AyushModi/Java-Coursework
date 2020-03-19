package Controller;

import Model.Model;
import View.PatientView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class PatientController {
    private PatientView view;
    private Model model;
    private MyTableModel tableModel;
    private ArrayList<JCheckBox> checkboxes;
    private SwingWorker<Void, Void> fileLoader;

    public PatientController(Model model, PatientView view) {
        System.out.println("Controller load thread: " + Thread.currentThread().getName());
        this.view = view;
        this.model = model;
        addEventListeners();
    }

    private void addEventListeners() {
        addLoadFileBtnEvent();
        addSearchBtnEvent();
        addJSONBtnEvent();
    }

    private void addJSONBtnEvent() {
        view.jsonBtnEvent(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("/home/me/Documents"));
            chooser.setFileFilter(new FileNameExtensionFilter("JSON file","json"));
            int returnVal = chooser.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                writeToJSON(chooser.getSelectedFile()).execute();
            }
        });
    }

    private void addSearchBtnEvent() {
        view.addSearchBtnEvent(e -> {
            search().execute();
        });
    }

    private void addLoadFileBtnEvent() {
        view.loadFileBtnEvent(e -> {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(view.getTable());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                view.setDtm(new MyTableModel());
                if (fileLoader != null)
                    fileLoader.cancel(true);
                fileLoader = loadFile(fc.getSelectedFile());
                fileLoader.execute();
            }
        });
    }

    public SwingWorker<Void, Void> writeToJSON(File filePath) {
        return new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                String title = view.getTitle();
                view.setTitle("Writing to JSON...");
                model.writeToJSON(filePath);
                view.setTitle(title);
                return null;
            }
        };
    }
    private ArrayList<String> chosenColumnNames() {
        ArrayList<String> chosenColumns = new ArrayList<>();
        for (int i = 0; i<view.getTable().getColumnCount();i++) {
            chosenColumns.add(view.getTable().getColumnName(i));
        }
        return chosenColumns;
    }
    public SwingWorker<Void, Void> search() {
        return new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                String title = view.getTitle();
                view.setTitle("Searching dataframe...");
                ArrayList<String> colList = chosenColumnNames();
                String[] chosenColumns = new String[colList.size()];
                chosenColumns = colList.toArray(chosenColumns);
                model.search(view.getSearchValue(), chosenColumns, view.matchWholeString());
                setTable(new ArrayList<>(Arrays.asList(chosenColumns)));
                view.setTitle(title);
                return null;
            }
        };
    }

    public SwingWorker<Void, Void> loadFile(File fileName) {
        return new LoadFileWorker(fileName);
    }

    private void setTable(ArrayList<String> chosenColumns) {
        tableModel = new MyTableModel();
        if (chosenColumns == null) {
            checkboxes = new ArrayList<>();
            view.removeCheckboxes();
        }

        for(var columnName : model.getColumnNames()) {
            if (chosenColumns == null || chosenColumns.contains(columnName))
                tableModel.addColumn(columnName, model.getColumnValues(columnName));

            if (chosenColumns == null) {
                JCheckBox newCheckBox = new JCheckBox(columnName);
                newCheckBox.addActionListener(this::checkToggled);
                view.addCheckBox(newCheckBox);
                checkboxes.add(newCheckBox);
            }
        }
        view.setDtm(tableModel);

        view.revalidate();
        view.repaint();
    }
    private void setTable() { setTable(null); }

    private void checkToggled(ActionEvent e) {
        JCheckBox cb = (JCheckBox) e.getSource();
        if (!cb.isSelected()) {
            tableModel.removeColumn(cb).execute();
        } else {
            tableModel.addColumnAtCorrectIndex(cb).execute();
        }
    }

    // below are two inner classes

    private class MyTableModel extends DefaultTableModel {

        private void setCheckboxesAccess(Boolean enabled) {
            for (var checkbox : checkboxes)
                checkbox.setEnabled(enabled);
        }

        public SwingWorker<Void, Void> removeColumn(JCheckBox column) {
            return new SwingWorker<>() {
                private String title;
                @Override
                protected Void doInBackground() throws Exception {
                    setCheckboxesAccess(false);
                    title = view.getTitle();
                    view.setTitle("Removing column...");
                    int viewIndex = getJCheckIndex(column);
                    columnIdentifiers.remove(column.getText());
                    for (var row : dataVector) {
                        row.remove(viewIndex);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    fireTableStructureChanged();
                    setCheckboxesAccess(true);
                    view.setTitle(title);
                    view.revalidate();
                    view.repaint();
                    super.done();
                }
            };
        }
        @SuppressWarnings("unchecked")
        public SwingWorker<Void, Void> addColumnAtCorrectIndex(JCheckBox column) {
            return new SwingWorker<>() {
                private String title;


                @Override
                protected Void doInBackground() throws Exception {
                    title = view.getTitle();
                    view.setTitle("Adding column...");
                    setCheckboxesAccess(false);
                    String[] columnValues = model.getColumnValues(column.getText());
                    // default addColumn method adds column at the right end of table
                    addColumn(column.getText(), columnValues);

                    // check if column being added is meant to be at the right end of the table
                    // if not move the column
                    int index = getJCheckIndex(column);
                    if (index == getColumnCount() - 1) return null;
                    moveColumn(columnValues, index);
                    return null;
                }

                private void moveColumn(String[] columnValues, int index) {
                    columnIdentifiers.remove(getColumnCount() - 1);
                    columnIdentifiers.add(index, column.getText());
                    for (int i =0; i < columnValues.length; i++) {
                        Vector<Object> row = dataVector.elementAt(i);
                        row.removeElementAt(getColumnCount() - 1);
                        row.add(index, columnValues[i]);
                    }
                }

                @Override
                protected void done() {
                    super.done();
                    setCheckboxesAccess(true);
                    view.setTitle(title);
                    fireTableStructureChanged();
                    view.revalidate();
                    view.repaint();
                }
            };
        }
        private int getJCheckIndex(JCheckBox inputCheckBox) {
            int index = 0;
            for (var checkbox : checkboxes) {
                if (checkbox == inputCheckBox)
                    return index;
                if (checkbox.isSelected()) {
                    index++;
                }
            }
            return 0;
        }
    }
    private class LoadFileWorker extends SwingWorker<Void,Void> {
        private File fileName;
        public LoadFileWorker(File fileName) {
            this.fileName = fileName;
        }
        @Override
        protected Void doInBackground() {
            try {
                beforeLoad();
                model.loadFile(fileName);
                setTable();
                view.setJSONAccess(true);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "The file could not be found\nFile path: " + fileName.getAbsolutePath());
            } catch (NullPointerException e) {
                System.out.println("Null pointer: due to switching files during file load");
            }
            catch (Exception e) {
                System.out.println("Caught Error\n" + e.toString());
                JOptionPane.showMessageDialog(null, "Error occurred while opening file");

            }
            return null;
        }

        private void beforeLoad() {
            view.setJSONAccess(false);
            view.getTable().setVisible(false);
            view.setCheckboxVisibility(false);
            view.disableSearchButton();
            view.setTitle("Loading file: " + fileName.getAbsolutePath());
        }

        @Override
        protected void done() {
            super.done();
            view.setTitle("PatientView: " + fileName.getName());
            view.getTable().setVisible(true);
            view.setCheckboxVisibility(true);
            view.enableSearchButton();
            fileLoader = null;
        }
    }
}

