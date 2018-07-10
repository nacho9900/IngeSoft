package View;

import Controller.Controller;
import Model.Picture;
import Model.Product;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyProductsPanel {
    private Controller controller;
    private JPanel mainpanel;
    private JPanel header;
    private JButton VISIONButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JButton CARRITObutton;
    private JPanel footnote;
    private JPanel middle;
    private JButton SEARCHbutton;
    private JLabel resultsLabel;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JTextArea textArea5;
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
    private JButton viewButton;
    private JButton viewButton1;
    private JButton viewButton2;
    private JButton viewButton3;
    private JButton viewButton4;
    private JButton removerButton;
    private JButton removerButton1;
    private JButton removerButton2;
    private JButton removerButton3;
    private JButton removerButton4;
    private JButton anteriorButton;
    private JButton siguienteButton;
    private JLabel LOGO;
    private ArrayList<JTextArea> textAreas0;
    private ArrayList<JTextArea> textAreas1;
    private ArrayList<JTextArea> textAreas2;
    private Iterator<Product> productIterator;
    private int offset;
    private static final int PAGESIZE = 5;
    private ArrayList<Product> list;
    private ArrayList<JButton> viewButtons;
    private ArrayList<JButton> removeButtons;
    private View view;


    public MyProductsPanel(Controller controller, View view) {
        this.view = view;
        this.offset = 0;
        this.controller = controller;
        textAreas0 = new ArrayList<>();
        textAreas0.add(textArea1);
        textAreas0.add(textArea2);
        textAreas0.add(textArea3);
        textAreas0.add(textArea4);
        textAreas0.add(textArea5);
        textAreas1 = new ArrayList<>();
        textAreas1.add(textArea6);
        textAreas1.add(textArea7);
        textAreas1.add(textArea8);
        textAreas1.add(textArea9);
        textAreas1.add(textArea10);
        textAreas2 = new ArrayList<>();
        textAreas2.add(textArea11);
        textAreas2.add(textArea12);
        textAreas2.add(textArea13);
        textAreas2.add(textArea14);
        textAreas2.add(textArea15);
        productIterator = null;
        viewButtons = new ArrayList<>();
        viewButtons.add(viewButton);
        viewButtons.add(viewButton1);
        viewButtons.add(viewButton2);
        viewButtons.add(viewButton3);
        viewButtons.add(viewButton4);
        removeButtons = new ArrayList<>();
        removeButtons.add(removerButton);
        removeButtons.add(removerButton1);
        removeButtons.add(removerButton2);
        removeButtons.add(removerButton3);
        removeButtons.add(removerButton4);
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
                printItems();
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
                printItems();
            }
        });
    }

    public void printItems() {
        clearItems();
        if (productIterator == null) {
            User user = controller.getCurrentUser();
            if (user != null) {
                list = Product.getByUser(user);
            }
            if (list != null && list.size() - offset > PAGESIZE) {
                siguienteButton.setEnabled(true);
            } else {
                siguienteButton.setEnabled(false);
            }
        }
        if (list != null && !list.isEmpty()) {
            Iterator<JButton> viewButtonIterator = viewButtons.iterator();
            Iterator<JButton> removeButtonIterator = removeButtons.iterator();
            Iterator<JTextArea> textAreas0Iterator = textAreas0.iterator();
            Iterator<JTextArea> textAreas1Iterator = textAreas1.iterator();
            Iterator<JTextArea> textAreas2Iterator = textAreas2.iterator();
            for (int i = offset; i < 5 + offset && i < list.size(); i++) {
                final Product curr = list.get(i);
                JButton currViewButton = viewButtonIterator.next();
                JButton currRemoveButton = removeButtonIterator.next();
                textAreas0Iterator.next().setText(curr.getName());
                textAreas1Iterator.next().setText(curr.getSold() + " items sold!");
                textAreas2Iterator.next().setText("You had " + curr.getVisits() + " visits");
                currViewButton.setVisible(true);
                currRemoveButton.setVisible(true);


                currRemoveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        curr.delete();
                        curr.save();
                        JOptionPane.showMessageDialog(null, "Product eliminated!");
                        printItems();
                    }
                });
                currViewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Picture> pics = Picture.getProdcutPicture(curr.getId());
                        view.viewProduct.printProduct(curr, pics);
                        view.cardLayout = (CardLayout) view.cards.getLayout();

                        view.reviewsPanel.setProduct(curr);
                        if (view.viewProduct.getREVIEWSButton().getActionListeners().length > 0) {
                            view.viewProduct.getREVIEWSButton().removeActionListener(
                                    view.viewProduct.getREVIEWSButton().getActionListeners()[0]);
                        }
                        view.viewProduct.getREVIEWSButton().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                view.cardLayout.show(view.cards, "reviewsPanel");
                            }
                        });
                        view.cardLayout.show(view.cards, "viewProductPanel");
                        view.cards.revalidate();
                    }
                });
            }
        }
    }

    private void clearItems() {
        Iterator<JButton> viewButtonIterator = viewButtons.iterator();
        Iterator<JButton> removeButtonIterator = removeButtons.iterator();
        Iterator<JTextArea> textArea1Iterator = textAreas1.iterator();
        Iterator<JTextArea> textArea2Iterator = textAreas2.iterator();
        JButton currView;
        JButton currRemove;
        JTextArea currText;
        for (JTextArea jTextArea : textAreas0) {
            jTextArea.setText("");
            currText = textArea1Iterator.next();
            currText.setText("");
            currText = textArea2Iterator.next();
            currText.setText("");
            currRemove = removeButtonIterator.next();
            currView = viewButtonIterator.next();
            currRemove.setVisible(false);
            currView.setVisible(false);
            if (currRemove.getActionListeners().length != 0) {
                currRemove.removeActionListener(currRemove.getActionListeners()[0]);
            }
            if (currView.getActionListeners().length != 0) {
                currView.removeActionListener(currView.getActionListeners()[0]);
            }
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
