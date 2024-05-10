package Manager;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import net.miginfocom.swing.*;

public class FrameOpenFile {
    private ManagerBoard managerBoard;
    public FrameOpenFile(ManagerBoard managerBoard) {
        this.managerBoard = managerBoard;
        initComponents();
    }

    private void openListener(ActionEvent e) {
        // TODO add your code here
        String fileToOpen = pathTextField.getText();
        File file = new File(fileToOpen);

        if (!file.exists()) {
            txtNotExistField.setVisible(true);
            JOptionPane.showMessageDialog(frameOpenFile, "File does not exist!");
            return;
        }

        managerBoard.openFile(fileToOpen);
        frameOpenFile.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Yuxin Ren
        frameOpenFile = new JFrame();
        pathLabel = new JLabel();
        pathTextField = new JTextField();
        openButton = new JButton();
        txtNotExistField = new JTextField();

        //======== frameOpenFile ========
        {
            var frameOpenFileContentPane = frameOpenFile.getContentPane();
            frameOpenFileContentPane.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[93,fill]" +
                "[278,fill]",
                // rows
                "[64]" +
                "[]" +
                "[18]" +
                "[]" +
                "[]" +
                "[]"));

            //---- pathLabel ----
            pathLabel.setText("Input File Path");
            frameOpenFileContentPane.add(pathLabel, "cell 0 1");
            frameOpenFileContentPane.add(pathTextField, "cell 1 1");

            //---- openButton ----
            openButton.setText("OPEN");
            openButton.addActionListener(e -> openListener(e));
            frameOpenFileContentPane.add(openButton, "cell 1 3");

            //---- txtNotExistField ----
            txtNotExistField.setVisible(false);
            frameOpenFileContentPane.add(txtNotExistField, "cell 0 5 2 1");
            frameOpenFile.pack();
            frameOpenFile.setLocationRelativeTo(null);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Yuxin Ren
    public JFrame frameOpenFile;
    private JLabel pathLabel;
    private JTextField pathTextField;
    private JButton openButton;
    private JTextField txtNotExistField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
