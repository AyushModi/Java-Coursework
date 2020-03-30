import Controller.PatientController;
import Model.Model;
import View.PatientView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            try {
                PatientController pc = new PatientController(new Model(), new PatientView());
            } catch (Exception e) {
                System.out.println("Exception caught\n" + e.toString());
            }
        });
    }
}
