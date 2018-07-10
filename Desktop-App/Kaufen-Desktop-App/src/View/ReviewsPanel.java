package View;

import Controller.Controller;
import Model.Product;
import Model.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class ReviewsPanel {
    private JPanel header;
    private JButton MISPRODUCTOSButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JLabel KWIKlabel;
    private JButton CARRITObutton;
    private JButton SEARCHbutton;

    public JButton getMISPRODUCTOSButton() {
        return MISPRODUCTOSButton;
    }

    public JButton getEQUIPObutton() {
        return EQUIPObutton;
    }

    public JButton getPERFILButton() {
        return PERFILButton;
    }

    public JLabel getKWIKlabel() {
        return KWIKlabel;
    }

    public JButton getCARRITObutton() {
        return CARRITObutton;
    }

    public JButton getSEARCHbutton() {
        return SEARCHbutton;
    }

    private JPanel footnote;
    private JPanel middle;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JTextArea textArea5;
    private JButton backButton;
    private JButton anteriorButton;
    private JButton siguienteButton;
    private JLabel rating1;
    private JLabel rating2;
    private JLabel rating5;
    private JLabel rating4;
    private JLabel rating3;
    private JPanel mainpanel;
    private JLabel LOGO;
    private Product product;
    private int offset;
    private ArrayList<Review> reviews;

    private static final int PAGESIZE = 5;

    public ReviewsPanel() {
        setActionListners();
        mainpanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                loadReviews();
                writeComments();
            }
        });
        anteriorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (offset >= PAGESIZE) {
                    offset -= PAGESIZE;
                    siguienteButton.setEnabled(true);
                } else {
                    anteriorButton.setEnabled(false);
                }
                writeComments();
            }
        });
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = reviews.size();
                if (size - offset > PAGESIZE) {
                    offset += PAGESIZE;
                    anteriorButton.setEnabled(true);
                } else {
                    siguienteButton.setEnabled(false);
                }
                writeComments();
            }
        });


    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private void writeComments() {
        cleanReviews();
        int size = reviews.size();
        System.out.println(reviews.size());

        if (size > offset) {
            final String comment = reviews.get(offset).getComment();
            final Integer rating = reviews.get(offset).getRating();
            textArea1.setText(comment);
            rating1.setText(rating.toString());

        }
        if (size > 1 + offset) {
            final String comment = reviews.get(offset + 1).getComment();
            final Integer rating = reviews.get(offset + 1).getRating();
            textArea2.setText(comment);
            rating2.setText(rating.toString());

        }
        if (size > 2 + offset) {
            final String comment = reviews.get(offset + 2).getComment();
            final Integer rating = reviews.get(offset + 2).getRating();
            textArea3.setText(comment);
            rating3.setText(rating.toString());

        }
        if (size > 3 + offset) {
            final String comment = reviews.get(offset + 3).getComment();
            final Integer rating = reviews.get(offset + 3).getRating();
            textArea4.setText(comment);
            rating4.setText(rating.toString());

        }
        if (size > 4 + offset) {
            final String comment = reviews.get(offset + 4).getComment();
            final Integer rating = reviews.get(offset + 4).getRating();
            textArea5.setText(comment);
            rating5.setText(rating.toString());

        }
    }

    private void setActionListners() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().getView().changeProduct();
            }
        });
    }

        private void loadReviews() {
        offset = 0;
        reviews = product.getReviews();
        if (reviews.size() > PAGESIZE) {
            siguienteButton.setEnabled(true);
        }
    }

    public void cleanReviews() {
        textArea1.setText("");
        textArea2.setText("");
        textArea3.setText("");
        textArea4.setText("");
        textArea5.setText("");

        rating1.setText("");
        rating2.setText("");
        rating3.setText("");
        rating4.setText("");
        rating5.setText("");
    }

    public JPanel getMainpanel() {
        return mainpanel;
    }

}
