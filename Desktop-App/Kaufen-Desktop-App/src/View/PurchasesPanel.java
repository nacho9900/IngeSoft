package View;

import Controller.Controller;
import Model.Product;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class PurchasesPanel {
    private JButton EQUIPObutton;
    private JButton CARRITObutton;
    private JButton SEARCHbutton;
    private JPanel mainpanel;
    private JLabel resultsLabel;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JTextArea textArea5;
    private JButton comentarButton1;
    private JButton comentarButton2;
    private JButton comentarButton3;
    private JButton comentarButton4;
    private JButton comentarButton5;
    private JButton anteriorButton;
    private JButton siguienteButton;
    private JPanel footnote;
    private JPanel middle;
    private JPanel header;
    private JButton VISIONbutton;
    private JButton PERFILbutton;
    private JLabel LOGO;
    private View.ViewSwapper vs;
    private int offset;
    private static final int PAGESIZE = 5;
    private ArrayList<Product> products;
    private HashMap<Product, Integer> ids;


    public PurchasesPanel(final View.ViewSwapper vs, final Controller controller) {
        this.vs = vs;
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
                drawListItems();

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
                drawListItems();

            }
        });
    }

    public void setLoggedInConfigurations() {
        offset = 0;
        this.ids = Product.listBoughtProducts(Controller.getInstance().getCurrentUser());
        this.products = new ArrayList<>(ids.keySet());
        System.out.println(products);

        anteriorButton.setEnabled(false);
        if (products.size() > PAGESIZE) {
            siguienteButton.setEnabled(true);
        } else {
            siguienteButton.setEnabled(false);
        }
        drawListItems();
    }

    public void drawListItems() {
        cleanPanel();

        if (products.size() > offset) {
            comentarButton1.setEnabled(true);
            textArea1.setText(products.get(offset).getName());
            if (comentarButton1.getActionListeners().length > 0) {
                comentarButton1.removeActionListener(comentarButton1.getActionListeners()[0]);
            }
            comentarButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Product curr = products.get(offset);
                    RateProduct rateProduct = View.getRateProduct();
                    rateProduct.setUpReview(curr, ids.get(curr));
                    vs.changeView("rateProductPanel", null);
                }
            });
        } else {
            comentarButton1.setEnabled(false);
        }
        if (products.size() > 1 + offset) {
            comentarButton2.setEnabled(true);
            textArea2.setText(products.get(1 + offset).getName());
            if (comentarButton2.getActionListeners().length > 0) {
                comentarButton2.removeActionListener(comentarButton2.getActionListeners()[0]);
            }
            comentarButton2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Product curr = products.get(1 + offset);
                    RateProduct rateProduct = View.getRateProduct();
                    rateProduct.setUpReview(curr, ids.get(curr));
                    vs.changeView("rateProductPanel", null);
                }
            });
        } else {
            comentarButton2.setEnabled(false);
        }
        if (products.size() > 2 + offset) {
            comentarButton3.setEnabled(true);
            textArea3.setText(products.get(offset + 2).getName());
            if (comentarButton3.getActionListeners().length > 0) {
                comentarButton3.removeActionListener(comentarButton3.getActionListeners()[0]);
            }
            comentarButton3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Product curr = products.get(offset + 2);
                    RateProduct rateProduct = View.getRateProduct();
                    rateProduct.setUpReview(curr, ids.get(curr));
                    vs.changeView("rateProductPanel", null);

                }
            });
        } else {
            comentarButton3.setEnabled(false);
        }
        if (products.size() > 3 + offset) {
            comentarButton4.setEnabled(true);
            textArea4.setText(products.get(offset + 3).getName());
            if (comentarButton4.getActionListeners().length > 0) {
                comentarButton4.removeActionListener(comentarButton4.getActionListeners()[0]);
            }
            comentarButton4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Product curr = products.get(offset + 3);
                    RateProduct rateProduct = View.getRateProduct();
                    rateProduct.setUpReview(curr, ids.get(curr));
                    vs.changeView("rateProductPanel", null);
                }
            });
        } else {
            comentarButton4.setEnabled(false);
        }
        if (products.size() > 4 + offset) {
            comentarButton5.setEnabled(true);
            textArea5.setText(products.get(offset + 4).getName());
            if (comentarButton5.getActionListeners().length > 0) {
                comentarButton5.removeActionListener(comentarButton5.getActionListeners()[0]);
            }
            comentarButton5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Product curr = products.get(offset + 4);
                    RateProduct rateProduct = View.getRateProduct();
                    rateProduct.setUpReview(curr, ids.get(curr));
                    vs.changeView("rateProductPanel", null);
                }
            });
        } else {
            comentarButton5.setEnabled(false);
        }

    }


//    public void printResults(final ArrayList<Product> prods){
//        cleanPanel();
//        offset=0;
//        if(prods.size()>PAGESIZE){
//            siguienteButton.setEnabled(true);
//        }
//        refresh();
//    }
//    private void refresh(){
//        ArrayList<Product> productList = new ArrayList<>();
//        cleanPanel();
//        int size = products.size();
//        if(size >= 1 + offset) {
//            Product prod = products.get(offset);
//            comentarButton1.setVisible(true);
//
//        }
//        if(size>=2+offset) {
//            Product prod = products.get(offset+1);
//            comentarButton2.setVisible(true);
//        }
//        if(size>=3+offset) {
//            Product prod = products.get(offset+2);
//            comentarButton3.setVisible(true);
//        }
//        if(size>=4+offset) {
//            Product prod = products.get(offset+3);
//            comentarButton4.setVisible(true);
//
//        }
//        if(size>=5+offset) {
//            Product prod = products.get(offset+4);
//            comentarButton5.setVisible(true);
//
//        }
//    }

    private void cleanPanel() {
        textArea1.setText("");
        textArea2.setText("");
        textArea3.setText("");
        textArea4.setText("");
        textArea5.setText("");

    }

    public JPanel getMainpanel() {
        return mainpanel;
    }

    public JButton getEQUIPObutton() {
        return EQUIPObutton;
    }

    public JButton getVISIONbutton() {
        return VISIONbutton;
    }

    public JButton getPERFILbutton() {
        return PERFILbutton;
    }

    public JButton getCARRITObutton() {
        return CARRITObutton;
    }

    public JButton getSEARCHbutton() {
        return SEARCHbutton;
    }

    public JButton getComentarButton1() {
        return comentarButton1;
    }

    public JButton getComentarButton2() {
        return comentarButton2;
    }

    public JButton getComentarButton3() {
        return comentarButton3;
    }

    public JButton getComentarButton4() {
        return comentarButton4;
    }

    public JButton getComentarButton5() {
        return comentarButton5;
    }

}
