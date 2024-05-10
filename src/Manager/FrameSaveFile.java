package Manager;

import java.awt.event.*;
import javax.swing.*;
import net.miginfocom.swing.*;

public class FrameSaveFile {

    private ManagerBoard managerBoard;
    public FrameSaveFile(ManagerBoard managerBoard) {
        this.managerBoard = managerBoard;
        initComponents();
    }

    private void saveListener(ActionEvent e) {
        // TODO add your code here
        String name = nameTextFile.getText();
        String type = (String) typeComboBox.getSelectedItem();
        managerBoard.saveFile(name);
        managerBoard.saveImage(name, type);
        frameSaveAs.dispose();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Yuxin Ren
        frameSaveAs = new JFrame();
        nameLabel = new JLabel();
        nameTextFile = new JTextField();
        typeLabel = new JLabel();
        typeComboBox = new JComboBox<>();
        saveButton = new JButton();

        //======== frameSaveAs ========
        {
            var frameSaveAsContentPane = frameSaveAs.getContentPane();
            frameSaveAsContentPane.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[203,fill]" +
                "[314,fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[30]" +
                "[]"));

            //---- nameLabel ----
            nameLabel.setText("File name: ");
            frameSaveAsContentPane.add(nameLabel, "cell 1 2");

            //---- nameTextFile ----
            nameTextFile.setText("white_board");
            frameSaveAsContentPane.add(nameTextFile, "cell 2 2");

            //---- typeLabel ----
            typeLabel.setText("File type: ");
            frameSaveAsContentPane.add(typeLabel, "cell 1 3");

            //---- typeComboBox ----
            typeComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                ".jpg",
                ".png"
            }));
            frameSaveAsContentPane.add(typeComboBox, "cell 2 3");

            //---- saveButton ----
            saveButton.setText("SAVE");
            saveButton.addActionListener(e -> saveListener(e));
            frameSaveAsContentPane.add(saveButton, "cell 2 5");
            frameSaveAs.pack();
            frameSaveAs.setLocationRelativeTo(null);
            frameSaveAs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameSaveAs.setVisible(true);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Yuxin Ren
    public JFrame frameSaveAs;
    private JLabel nameLabel;
    private JTextField nameTextFile;
    private JLabel typeLabel;
    private JComboBox<String> typeComboBox;
    private JButton saveButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
