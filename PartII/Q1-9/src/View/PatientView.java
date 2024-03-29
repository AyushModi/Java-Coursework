package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class PatientView  extends JFrame {
    private JTable table;
    private Box checkBoxPanel;
    private JButton loadFileBtn;
    private JButton saveToJSONBtn;
    private JTextField searchbox;
    private JCheckBox matchWholeStringBox;
    private JButton searchBtn;

    /**
     * The following chain of functions creates the view when the class is initialised
     */

    public PatientView() {
        setTableProperties();
        setFrameProperties();
        this.setVisible(true);
    }
    private void setTableProperties() {
        table = new JTable();
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
    }
    private void setFrameProperties() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 750);
        this.setLayout(new BorderLayout());
        this.add(createMainPanel(), BorderLayout.CENTER);
        this.add(createSidePanel(), BorderLayout.WEST);
        this.setTitle("PatientView");
    }

    private JScrollPane createMainPanel() {
        return new JScrollPane(table);
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(180,750));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.add(createFileChooseArea());
        sidePanel.add(createSearchArea());
        sidePanel.add(createCheckBoxScroll());
        return sidePanel;
    }

    private JPanel createFileChooseArea() {
        JPanel fileChooseArea = new JPanel();
        fileChooseArea.setMaximumSize(new Dimension(180,70));
        fileChooseArea.setMinimumSize(new Dimension(180,70));
        fileChooseArea.setPreferredSize(new Dimension(180,70));
        loadFileBtn = new JButton("Load New File");
        saveToJSONBtn = new JButton("Save to JSON");
        saveToJSONBtn.setEnabled(false);
        fileChooseArea.add(loadFileBtn);
        fileChooseArea.add(saveToJSONBtn);
        fileChooseArea.setBackground(Color.GREEN);
        return fileChooseArea;
    }

    private JPanel createSearchArea() {
        JPanel searchArea = new JPanel();
        searchArea.setMaximumSize(new Dimension(180,100));
        searchArea.setMinimumSize(new Dimension(180,100));
        searchArea.setPreferredSize(new Dimension(180,100));
        searchbox = new JTextField("",15);
        searchBtn = new JButton("Search");
        matchWholeStringBox = new JCheckBox("Match whole string");
        searchArea.add(searchbox);
        searchArea.add(matchWholeStringBox);
        searchArea.add(searchBtn);
        return searchArea;
    }

    private JScrollPane createCheckBoxScroll() {
        checkBoxPanel = Box.createVerticalBox();
        checkBoxPanel.setPreferredSize(new Dimension(180,500));
        return new JScrollPane(checkBoxPanel);
    }

    /**
     * The following methods are there to allow the controller to modify the items in the view and access their attributes
     */

    public void loadFileBtnEvent(ActionListener e) { loadFileBtn.addActionListener(e); }

    public void addSearchBtnEvent(ActionListener e) { searchBtn.addActionListener(e); }
    public void jsonBtnEvent(ActionListener e) { saveToJSONBtn.addActionListener(e); }

    public void addCheckBox(JCheckBox checkbox) {
        checkbox.setSelected(true);
        this.checkBoxPanel.add(checkbox);
    }
    public void removeCheckboxes() { this.checkBoxPanel.removeAll(); }
    public void setCheckboxVisibility(Boolean isVisible) { this.checkBoxPanel.setVisible(isVisible);}
    public void setJSONAccess(Boolean enabled) { this.saveToJSONBtn.setEnabled(enabled);}
    public void setDtm(DefaultTableModel dtm) { this.table.setModel(dtm); }
    public JTable getTable() {
        return this.table;
    }
    public String getSearchValue() { return this.searchbox.getText(); }
    public Boolean matchWholeString() { return this.matchWholeStringBox.isSelected(); }
    public void disableSearchButton() { this.searchBtn.setEnabled(false); }
    public void enableSearchButton() { this.searchBtn.setEnabled(true); }

}
