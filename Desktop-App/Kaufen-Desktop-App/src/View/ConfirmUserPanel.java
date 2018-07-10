package View;

import Controller.InputController;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmUserPanel {
    private JPanel header;
    private JLabel LOGO;
    private JPanel footnote;
    private JPanel middle;
    private JTextArea username;
    private JTextArea notification;
    private JTextField code;
    private JButton verifyButton;
    private JPanel mainpanel;

    public ConfirmUserPanel(final View.ViewSwapper vs, final InputController inputController) {

        if (inputController.getController().getCurrentUser() != null)
            username.setText("Welcome " + inputController.getController().getCurrentUser().getName());

        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User currentUser = inputController.getController().getCurrentUser();
                System.out.println(currentUser);
                if(currentUser.validate(new Integer(code.getText())))
                    vs.changeView("searchPanel", null);
                else
                    JOptionPane.showMessageDialog(null, "Wrong code, try again");

            }
        });
    }

    public JPanel getMainpanel() {
        return mainpanel;
    }
}
