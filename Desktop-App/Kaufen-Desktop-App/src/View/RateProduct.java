package View;

import Model.Product;
import Model.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RateProduct {
    private JPanel mainpanel;
    private JPanel header;
    private JButton VISIONButton;
    private JButton EQUIPObutton;
    private JButton PERFILButton;
    private JButton CARRITObutton;
    private JButton SEARCHbutton;
    private JPanel footnote;
    private JPanel middle;
    private JTextPane comment;
    private JTextField name;
    private JSlider slider1;
    private JButton Send;
    private JLabel LOGO;
    private Product product;
    private Review review;
    private View.ViewSwapper vs;

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

    public JPanel getMainpanel() {
        return mainpanel;
    }

    public RateProduct(View.ViewSwapper vs) {
        this.vs = vs;
    }

    public void setUpReview(Product product, int idCP) {
        this.product = product;
        this.review = Review.get(idCP);
        cleanView();
        name.setText(product.getName());
        if (review != null) {
            comment.setText(review.getComment());
            slider1.setValue(review.getRating());
        }

        Send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendReview(idCP);
                vs.changeView("searchPanel", null);
            }
        });
    }

    public void sendReview(int idCP) {
        if (review == null) {
            Review.create(idCP, product, comment.getText(), slider1.getValue());
        } else {
            review.setComment(comment.getText());
            review.setRating(slider1.getValue());
            review.save();
        }
    }

    public void cleanView() {
        comment.setText("");
        name.setText("");
        if (Send.getActionListeners().length > 0)
            Send.removeActionListener(Send.getActionListeners()[0]);
    }

}
