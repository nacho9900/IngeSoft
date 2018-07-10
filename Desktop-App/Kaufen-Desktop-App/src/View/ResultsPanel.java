package View;

import Controller.Controller;
import Model.Cart;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ResultsPanel {
    private JPanel mainpanel;
    private JPanel header;
    private JButton VISIONButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JButton CARRITObutton;
    private JPanel footnote;
    private JButton SEARCHbutton;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JTextArea textArea5;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton viewButton;
    private JButton viewButton1;
    private JButton viewButton2;
    private JButton viewButton3;
    private JButton viewButton4;
    private JButton anteriorButton;
    private JButton siguienteButton;
    private JTextArea textArea7;
    private JTextArea textArea8;
    private JTextArea textArea9;
    private JTextArea textArea10;
    private JTextArea textArea6;
    private JLabel resultsLabel;
    private JButton ordenarPorPrecioButton;
    private JButton ordenarPorPuntuaciónButton;
    private JTextArea textArea11;
    private JTextArea textArea12;
    private JTextArea textArea13;
    private JTextArea textArea14;
    private JTextArea textArea15;
    private JLabel LOGO;
    private JTable table1;
    private Controller controller;
    private static final int PAGESIZE = 5;
    private int offset;
    private ArrayList<Product> prods;
    private ArrayList<JButton> buttons;

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

    public ResultsPanel(Controller controller) {
        buttons = new ArrayList<>();
        initButtons();

        this.controller = controller;
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
                refresh();
            }
        });
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = prods.size();
                if (size - offset > PAGESIZE) {
                    offset += PAGESIZE;
                    anteriorButton.setEnabled(true);
                }
                if (size - offset <= PAGESIZE) {
                    siguienteButton.setEnabled(false);
                }
                refresh();
            }
        });
        ordenarPorPrecioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prods.sort(new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return (int) Math.round(o1.getPrice() - o2.getPrice());
                    }
                });
                resetOffset();
                refresh();
            }
        });
        ordenarPorPuntuaciónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prods.sort(new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return Math.round(o2.getRating() - o1.getRating());
                    }
                });
                resetOffset();
                refresh();
            }
        });
    }

    public void printResults(final ArrayList<Product> prods) {
        cleanPanel();
        this.prods = prods;
        resetOffset();
        refresh();
    }

    public void resetOffset() {
        offset = 0;
        if (prods.size() > PAGESIZE) {
            siguienteButton.setEnabled(true);
        }
        anteriorButton.setEnabled(false);
    }

    private void refresh() {
        ArrayList<Product> productList = new ArrayList<>();
        cleanPanel();
        int size = prods.size();
        if (size >= 1 + offset) {
            Product prod = prods.get(offset);
            textArea1.setText(prod.getName() + "\nRate: " + prod.getRating());
            textArea6.setText("$" + prod.getPrice());
            textArea11.setText(prod.getStock() + " in stock");
            productList.add(prod);
            button1.setVisible(true);
            viewButton.setVisible(true);
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (controller.getCurrentUser() == null) return;
                    Cart.getOrCreate(controller.getCurrentUser()).addProduct(prod, 1);
                }
            });

        }
        if (size >= 2 + offset) {
            Product prod = prods.get(offset + 1);
            textArea2.setText(prod.getName() + "\nRate: " + prod.getRating());
            textArea7.setText("$" + prod.getPrice());
            textArea12.setText(prod.getStock() + " in stock");
            productList.add(prod);
            button2.setVisible(true);
            viewButton1.setVisible(true);
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (controller.getCurrentUser() == null) return;
                    Cart.getOrCreate(controller.getCurrentUser()).addProduct(prod, 1);
                }
            });
        }
        if (size >= 3 + offset) {
            Product prod = prods.get(offset + 2);
            textArea3.setText(prod.getName() + "\nRate: " + prod.getRating());
            textArea8.setText("$" + prod.getPrice());
            textArea13.setText(prod.getStock() + " in stock");
            productList.add(prod);
            button3.setVisible(true);
            viewButton2.setVisible(true);
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (controller.getCurrentUser() == null) return;
                    Cart.getOrCreate(controller.getCurrentUser()).addProduct(prod, 1);
                }
            });
        }
        if (size >= 4 + offset) {
            Product prod = prods.get(offset + 3);
            textArea4.setText(prod.getName() + "\nRate: " + prod.getRating());
            textArea9.setText("$" + prod.getPrice());
            textArea14.setText(prod.getStock() + " in stock");
            productList.add(prod);
            button4.setVisible(true);
            viewButton3.setVisible(true);
            button4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (controller.getCurrentUser() == null) return;
                    Cart.getOrCreate(controller.getCurrentUser()).addProduct(prod, 1);
                }
            });
        }
        if (size >= 5 + offset) {
            Product prod = prods.get(offset + 4);
            textArea5.setText(prod.getName() + "\nRate: " + prod.getRating());
            textArea10.setText("$" + prod.getPrice());
            textArea15.setText(prod.getStock() + " in stock");
            productList.add(prod);
            button5.setVisible(true);
            viewButton4.setVisible(true);
            button5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (controller.getCurrentUser() == null) return;
                    Cart.getOrCreate(controller.getCurrentUser()).addProduct(prod, 1);
                }
            });
        }
        Controller.getView().updateViewProductButtons(productList);

    }

    private void cleanPanel() {
        textArea1.setText("");
        textArea2.setText("");
        textArea3.setText("");
        textArea4.setText("");
        textArea5.setText("");
        textArea6.setText("");
        textArea7.setText("");
        textArea8.setText("");
        textArea9.setText("");
        textArea10.setText("");
        textArea11.setText("");
        textArea12.setText("");
        textArea13.setText("");
        textArea14.setText("");
        textArea15.setText("");


        if (viewButton.getActionListeners().length > 0)
            viewButton.removeActionListener(viewButton.getActionListeners()[0]);

        if (viewButton1.getActionListeners().length > 0)
            viewButton1.removeActionListener(viewButton1.getActionListeners()[0]);

        if (viewButton2.getActionListeners().length > 0)
            viewButton2.removeActionListener(viewButton2.getActionListeners()[0]);

        if (viewButton3.getActionListeners().length > 0)
            viewButton3.removeActionListener(viewButton3.getActionListeners()[0]);

        if (viewButton4.getActionListeners().length > 0)
            viewButton4.removeActionListener(viewButton4.getActionListeners()[0]);


        if (button1.getActionListeners().length > 0)
            button1.removeActionListener(button1.getActionListeners()[0]);

        if (button2.getActionListeners().length > 0)
            button2.removeActionListener(button2.getActionListeners()[0]);

        if (button3.getActionListeners().length > 0)
            button3.removeActionListener(button3.getActionListeners()[0]);

        if (button4.getActionListeners().length > 0)
            button4.removeActionListener(button4.getActionListeners()[0]);

        if (button5.getActionListeners().length > 0)
            button5.removeActionListener(button5.getActionListeners()[0]);

        for (JButton b : buttons) {
            b.setVisible(false);
        }
    }

    public JButton getViewButton() {
        return viewButton;
    }

    public JButton getViewButton1() {
        return viewButton1;
    }

    public JButton getViewButton2() {
        return viewButton2;
    }

    public JButton getViewButton3() {
        return viewButton3;
    }

    public JButton getViewButton4() {
        return viewButton4;
    }

    public void initButtons() {
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);

        buttons.add(viewButton1);
        buttons.add(viewButton2);
        buttons.add(viewButton3);
        buttons.add(viewButton4);
        buttons.add(viewButton);
    }

}
