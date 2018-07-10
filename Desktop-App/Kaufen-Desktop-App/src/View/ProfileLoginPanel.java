package View;

import Controller.Controller;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileLoginPanel {
    private JPanel mainpanel;
    private JPanel header;
    private JPanel footnote;
    private JPanel middle;
    private JTextField name;
    private JPasswordField password;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JLabel nameLabel;
    private JButton registrationButton;
    private JButton loginButton;
    private JLabel LOGO;
    private View.ViewSwapper vs;


    public ProfileLoginPanel(final View.ViewSwapper vs) {

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("user: " + name.getText() + " pw: " + password.getText());
                if (!Controller.getInstance().isLoggedIn()) {
                    if (Controller.getInstance().logInUser(name.getText(), password.getText())) {
                        Controller.getView().setViewsAsLoggedIn();
                        vs.changeView("searchPanel", null);
                    } else {
                        JOptionPane.showMessageDialog(null, "invalid username or password");

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You are already logged in");
                }
            }
        });
        this.vs = vs;

        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vs.changeView("newUser", 1);
            }
        });
        password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] ps = password.getPassword();
                System.out.println(ps);
                //Si se loggeo deberia setear loggedin en true
                //loggedIn=true;
            }
        });
    }

    public JPasswordField getPassword() {
        return password;
    }

    public JPanel getMainpanel() {
        return mainpanel;
    }

    public JButton getRegistrationButton() {
        return registrationButton;
    }


}
