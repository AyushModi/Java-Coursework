import Controller.PatientController;
import Model.DataFrame;
import Model.DataLoader;
import Model.Model;
import View.PatientView;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class Main {
    public static void main(String[] args) {
//        DataLoader dl = new DataLoader();
//        dl.readData("COMP0004Data/allergies100.csv");
//        DataFrame df = dl.getDataFrame();
//        Model model;
        SwingUtilities.invokeLater(()->{
            try {
                PatientController pc = new PatientController(new Model(), new PatientView());
            } catch (Exception e) {
                System.out.println("Exception caught\n" + e.toString());
            }
        });
    }
}
