package View;

import Controller.Controller;
import Model.Cart;
import Model.Product;
import View.View.CartSwapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingcartPanel {
    private JPanel mainpanel;
    private JPanel header;
    private JButton VISIONButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JButton CARRITObutton;
    private JButton SEARCHbutton;
    private JPanel footnote;
    private JPanel middle;
    private JTextArea total;
    private JButton checkoutButton;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton anteriorButton;
    private JButton siguienteButton;
    private JLabel stock1;
    private JLabel stock2;
    private JLabel stock3;
    private JLabel stock4;
    private JLabel totalStock;
    private JLabel LOGO;
    private JTextArea textArea;
    private View.ViewSwapper vs;
    private Controller controller;
    private Cart cart;
    private CartSwapper cs;
    private int offset;
    private HashMap<Product, Integer> amounts;
    private ArrayList<Product> products;
    private Integer totalStockValue = 0;

    private static final int PAGESIZE = 4;


    public ShoppingcartPanel(final View.ViewSwapper vs, Controller controller, final CartSwapper cs) {
        this.vs = vs;
        this.cs = cs;
        this.controller = controller;


        mainpanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                loadCart();
                writeCart();
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
                writeCart();
            }
        });
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = products.size();
                if (size - offset > PAGESIZE) {
                    offset += PAGESIZE;
                    anteriorButton.setEnabled(true);
                }
                if (size - offset <= PAGESIZE) {
                    siguienteButton.setEnabled(false);
                }
                writeCart();
            }
        });
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] options = {"Pay with card",
                        "Cancel"};
                int n = JOptionPane.showOptionDialog(mainpanel,
                        "How would you like to proceed?",
                        "",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]);
                if (n == JOptionPane.YES_OPTION) {
                    if (cart != null && cart.getProducts().size() > 0) {
                        vs.changeView("paymentOptionsPanel", null);
                        System.out.println("Sent to payment with cart:" + cart.getId());
                        cs.swapCard(cart);


                    } else {
                        JOptionPane.showMessageDialog(null, "No products in cart");
                    }
                }


            }
        });
        cleanCart();
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

    public JTextArea getTextArea() {
        return textArea;
    }

    private void loadCart() {
        offset = 0;
        if (controller.getCurrentUser() != null) {
            cart = Cart.getUserCart(controller.getCurrentUser());
            if (cart != null) {
                amounts = cart.getProducts();
                if (amounts == null) {
                    cart = null;
                    return;
                }
                products = new ArrayList<>(amounts.keySet());
                if (products.size() > PAGESIZE) {
                    siguienteButton.setEnabled(true);
                }
            }
        } else {
            cart = null;
        }
    }

    private void removeProduct(Product product) {
        products.remove(product);
        int size = products.size();
        if (offset >= PAGESIZE && size - offset <= 0) {
            offset -= PAGESIZE;
        }
        if (offset < PAGESIZE) {
            anteriorButton.setEnabled(false);
        }
        if (size - offset <= PAGESIZE) {
            siguienteButton.setEnabled(false);
        }
    }

    private void writeCart() {
        cleanCart();

        if (cart != null) {

            int size = products.size();

            if (size >= 1 + offset) {
                final Product product = products.get(offset);
                textArea1.setText(product.getName());
                button1.setVisible(true);
                stock1.setText("x " + amounts.get(product).toString());
                totalStockValue += amounts.get(product);
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (cart.removeProduct(product)) {
                            removeProduct(product);
                            writeCart();
                        }
                    }
                });
            } else
                textArea1.setText("");

            if (size >= 2 + offset) {
                final Product product = products.get(offset + 1);
                textArea2.setText(product.getName());
                button2.setVisible(true);
                stock2.setText("x " + amounts.get(product).toString());
                totalStockValue += amounts.get(product);
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (cart.removeProduct(product)) {
                            removeProduct(product);
                            writeCart();
                        }
                    }
                });
            } else
                textArea2.setText("");

            if (size >= 3 + offset) {
                final Product product = products.get(offset + 2);
                textArea3.setText(product.getName());
                button3.setVisible(true);
                stock3.setText("x " + amounts.get(product).toString());
                totalStockValue += amounts.get(product);
                button3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (cart.removeProduct(product)) {
                            removeProduct(product);
                            writeCart();
                        }
                    }
                });
            } else
                textArea3.setText("");

            if (size >= 4 + offset) {
                final Product product = products.get(offset + 3);
                textArea4.setText(product.getName());
                System.out.println();
                button4.setVisible(true);
                stock4.setText("x " + amounts.get(product).toString());
                totalStockValue += amounts.get(product);
                button4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (cart.removeProduct(product)) {
                            removeProduct(product);
                            writeCart();
                        }
                    }
                });
            } else
                textArea4.setText("");

            totalStock.setText("x " + totalStockValue.toString() + "    ");
            total.setText("Total:    $" + cart.getSubTotal());
        } else {
            total.setText("Empty cart");
            checkoutButton.setEnabled(false);
        }
    }

    public void cleanCart() {
        textArea1.setText("");
        textArea2.setText("");
        textArea3.setText("");
        textArea4.setText("");
        total.setText("");

        button1.setVisible(false);
        if (button1.getActionListeners().length > 0) {
            button1.removeActionListener(button1.getActionListeners()[0]);
        }
        button2.setVisible(false);
        if (button2.getActionListeners().length > 0) {
            button2.removeActionListener(button2.getActionListeners()[0]);
        }
        button3.setVisible(false);
        if (button3.getActionListeners().length > 0) {
            button3.removeActionListener(button3.getActionListeners()[0]);
        }
        button4.setVisible(false);
        if (button4.getActionListeners().length > 0) {
            button4.removeActionListener(button4.getActionListeners()[0]);
        }

        stock1.setText("");
        stock2.setText("");
        stock3.setText("");
        stock4.setText("");
        totalStock.setText("");
        totalStockValue = 0;
    }

}
