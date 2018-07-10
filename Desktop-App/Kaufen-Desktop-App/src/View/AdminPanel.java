package View;

import Controller.Controller;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class AdminPanel {
    private final Controller controller;
    private JPanel mainpanel;
    private JPanel header;
    private JButton VISIONButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JButton CARRITObutton;
    private JPanel footnote;
    private JButton SEARCHbutton;
    private JPanel middle;
    private JLabel resultsLabel;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JTextArea textArea5;
    private JButton desactivarButton;
    private JButton desactivarButton1;
    private JButton desactivarButton2;
    private JButton desactivarButton3;
    private JButton desactivarButton4;
    private JButton searchButton;
    private JTextField textField1;
    private JButton anteriorButton;
    private JButton siguienteButton;
    private JTextArea textArea6;
    private JTextArea textArea7;
    private JTextArea textArea8;
    private JTextArea textArea9;
    private JTextArea textArea10;
    private JTextArea textArea11;
    private JTextArea textArea12;
    private JTextArea textArea13;
    private JTextArea textArea14;
    private JTextArea textArea15;
    private JLabel LOGO;
    private JPanel find;
    private ArrayList<JButton> buttons;
    private ArrayList<JTextArea> textAreas;
    private int offset;
    private ArrayList<User> list;
    private static final int PAGESIZE = 5;

    private void printNoCriteria(ArrayList<User> currlist) {
        clearItems();
        Iterator<JButton> buttonIterator = buttons.iterator();
        Iterator<JTextArea> jTextAreaIterator = textAreas.iterator();
        for (int i = offset; i < 5 + offset && i < list.size(); i++) {
            final User curr = currlist.get(i);
            final JButton currButton = buttonIterator.next();
            jTextAreaIterator.next().setText(curr.getUsername());
            jTextAreaIterator.next().setText(curr.getEmail());
            jTextAreaIterator.next().setText(curr.getName() + curr.getSurname());
            if (curr.isEnabled()) {
                currButton.setText("Disable");
            } else {
                currButton.setText("Enable");
            }
            currButton.setVisible(true);

            currButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    curr.setEnabled(!curr.isEnabled());
                    if (curr.isEnabled()) {
                        currButton.setText("Disable");
                        JOptionPane.showMessageDialog(null, "User Enabled!");
                    } else {
                        currButton.setText("  Enable  ");
                        JOptionPane.showMessageDialog(null, "User Disabled!");
                    }
                    curr.save();
                }
            });
        }
    }

    public boolean printUsers(String criteria) {
        clearItems();
        offset = 0;
        if (criteria != null) {
            User user = controller.getCurrentUser();
            if (user != null && user.isAdmin()) {
                list = User.search(criteria);
            } else {
                JOptionPane.showMessageDialog(null, "Access denied");
                return false;
            }
        } else {
            list = User.search("");
        }

        if (list.size() > PAGESIZE) {
            siguienteButton.setEnabled(true);
        } else {
            siguienteButton.setEnabled(false);
        }
        printNoCriteria(list);
        return true;
    }

    public AdminPanel(final Controller controller) {
        this.controller = controller;
        this.offset = 0;
        buttons = new ArrayList<>();
        buttons.add(desactivarButton);
        buttons.add(desactivarButton1);
        buttons.add(desactivarButton2);
        buttons.add(desactivarButton3);
        buttons.add(desactivarButton4);
        textAreas = new ArrayList<>();
        textAreas.add(textArea1);
        textAreas.add(textArea6);
        textAreas.add(textArea11);
        textAreas.add(textArea2);
        textAreas.add(textArea7);
        textAreas.add(textArea12);
        textAreas.add(textArea3);
        textAreas.add(textArea8);
        textAreas.add(textArea13);
        textAreas.add(textArea4);
        textAreas.add(textArea9);
        textAreas.add(textArea14);
        textAreas.add(textArea5);
        textAreas.add(textArea10);
        textAreas.add(textArea15);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printUsers(textField1.getText());
            }
        });
        anteriorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (offset >= PAGESIZE) {
                    offset -= PAGESIZE;
                    siguienteButton.setEnabled(true);
                }
                if (offset < PAGESIZE) {
                    anteriorButton.setEnabled(false);
                }
                printNoCriteria(list);
            }
        });
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = list.size();
                if (size - offset > PAGESIZE) {
                    offset += PAGESIZE;
                    anteriorButton.setEnabled(true);
                }
                if (size - offset <= PAGESIZE) {
                    siguienteButton.setEnabled(false);
                }
                printNoCriteria(list);
            }
        });
    }

    private void clearItems() {
        for (JTextArea jTextArea : textAreas) {
            jTextArea.setText("");
        }
        for (JButton curr : buttons) {
            curr.setVisible(false);
            if (curr.getActionListeners().length != 0)
                curr.removeActionListener(curr.getActionListeners()[0]);
        }
    }

    public JPanel getMainpanel() {
        return mainpanel;
    }

    public JButton getSEARCHbutton() {
        return SEARCHbutton;
    }

    public JButton getCARRITObutton() {
        return CARRITObutton;
    }

    public JButton getPERFILButton() {
        return PERFILButton;
    }

    public JButton getVISIONButton() {
        return VISIONButton;
    }

    public JButton getEQUIPObutton() {
        return EQUIPObutton;
    }

}
