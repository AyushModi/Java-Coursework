package Controller;

import Model.Model;
import View.PatientView;

import javax.swing.*;
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
        view.addSearchBtnEvent(e -> {
            String[] chosenColumns = new String[view.getTable().getColumnCount()];
            for (int i = 0; i<view.getTable().getColumnCount();i++) {
                chosenColumns[i] =  view.getTable().getColumnName(i);
            }
            model.search(view.getSearchValue(), chosenColumns, view.matchWholeString());
            setTable(new ArrayList<>(Arrays.asList(chosenColumns)));

        });
    }

    public SwingWorker<Void, Void> loadFile(File fileName) {
             return new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        view.getTable().setVisible(false);
                        view.disableSearchButton();
                        view.setTitle("Loading file: " + fileName.getAbsolutePath());

                        model.loadFile(fileName);
                        setTable();

                        view.setTitle("PatientView: " + fileName.getName());
                        view.getTable().setVisible(true);
                        view.enableSearchButton();
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

                 @Override
                 protected void done() {
                     super.done();
                     fileLoader = null;
                 }
             };
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
            tableModel.removeColumn(cb);
        } else {
            tableModel.addColumnAtCorrectIndex(cb, model.getColumnValues(cb.getText()));
        }
    }

    private class MyTableModel extends DefaultTableModel {
        public void removeColumn(JCheckBox column) {
            int viewIndex = getJCheckIndex(column);
            columnIdentifiers.remove(column.getText());
            for (var row : dataVector) {
                row.remove(viewIndex);
            }
            fireTableStructureChanged();
            view.revalidate();
            view.repaint();
        }

        @SuppressWarnings("unchecked")
        public void addColumnAtCorrectIndex(JCheckBox column, String[] columnValues) {
            SwingWorker<Void, Void> columnWorker = new SwingWorker<>() {

                private void setCheckboxesAccess(Boolean enabled) {
                    for (var checkbox : checkboxes)
                        checkbox.setEnabled(enabled);
                }

                @Override
                protected Void doInBackground() throws Exception {
                    System.out.println("Moving column thread: " + Thread.currentThread().getName());
                    setCheckboxesAccess(false);
                    addColumn(column.getText(), columnValues);
                    int index = getJCheckIndex(column);
                    if (index == getColumnCount() - 1) return null;
                    columnIdentifiers.remove(getColumnCount() - 1);
                    columnIdentifiers.add(index, column.getText());
                    for (int i =0; i < columnValues.length; i++) {
                        Vector<Object> row = dataVector.elementAt(i);
                        row.removeElementAt(getColumnCount() - 1);
                        row.add(index, columnValues[i]);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    super.done();
                    view.setDtm(tableModel);
                    setCheckboxesAccess(true);
                }
            };
            view.setDtm(new MyTableModel());
            columnWorker.execute();
            fireTableStructureChanged();
            view.revalidate();
            view.repaint();
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
}

